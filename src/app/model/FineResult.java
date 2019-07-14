package app.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FineResult {

    BigDecimal fineAmount;
    Integer paid;
    String paidStatus;
    Integer cardId;
    String cardIdModified;

    public String getCardIdModified() {
	return cardIdModified;
    }

    public void setCardIdModified(String cardIdModified) {
	this.cardIdModified = cardIdModified;
    }

    public FineResult(BigDecimal fineAmount, Integer paid, Integer cardId, String paidStatus) {
	this.fineAmount = fineAmount;
	this.paid = paid;
	this.cardId = cardId;
	this.paidStatus = paidStatus;
	this.cardIdModified = "";
    }

    public BigDecimal getFineAmount() {
	return fineAmount;
    }

    public void setFineAmount(BigDecimal fineAmount) {
	this.fineAmount = fineAmount;
    }

    public Integer getPaid() {
	return paid;
    }

    public void setPaid(Integer paid) {
	this.paid = paid;
	if (paid != null) {
	    if (0 == paid)
		setPaidStatus("FALSE");
	    if (1 == paid)
		setPaidStatus("TRUE");
	}
    }

    public String getPaidStatus() {
	return paidStatus;
    }

    public void setPaidStatus(String paidStatus) {
	this.paidStatus = paidStatus;
    }

    public Integer getCardId() {
	return cardId;
    }

    public void setCardId(Integer cardId) {
	this.cardId = cardId;
    }

    public static List<FineResult> modifyList(List<FineResult> finesList) {
	List<FineResult> modifiedList = new ArrayList<FineResult>();
	if (finesList != null) {
	    for (FineResult fine : finesList) {
		FineResult modified = new FineResult(fine.getFineAmount(), fine.getPaid(), fine.getCardId(),
			fine.getPaidStatus());
		System.out.println("Original Card Id: " + fine.getCardId());
		int count = getDigits(fine.getCardId());
		String modifiedCardId = getPaddedCardId(count, fine.getCardId());
		System.out.println("Modified Card Id: " + modifiedCardId);
		modified.setCardIdModified(modifiedCardId);
		modifiedList.add(modified);
	    }
	}
	return modifiedList;
    }

    private static int getDigits(int num) {
	int count = 0;
	while (num != 0) {
	    num /= 10;
	    ++count;
	}

	System.out.println("Number of digits: " + count);
	return count;
    }

    private static String getPaddedCardId(int count, int cardId) {
	String card = String.valueOf(cardId);
	int diff = 6 - count;
	for (int i = 0; i < diff; i++)
	    card = "0".concat(card);
	return card;
    }
}
