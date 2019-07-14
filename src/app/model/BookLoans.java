package app.model;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class BookLoans {
    int loanId;
    String isbn;
    int cardId;
    Date dateOut;
    Date dateDue;
    Date dateIn;
    Fines fine;

    public Fines getFine() {
	return fine;
    }

    public void setFine(Fines fine) {
	this.fine = fine;
    }

    public int getLoanId() {
	return loanId;
    }

    public void setLoanId(int loanId) {
	this.loanId = loanId;
    }

    public String getIsbn() {
	return isbn;
    }

    public void setIsbn(String isbn) {
	this.isbn = isbn;
    }

    public int getCardId() {
	return cardId;
    }

    public void setCardId(int cardId) {
	this.cardId = cardId;
    }

    public Date getDateOut() {
	return dateOut;
    }

    public void setDateOut(Date dateOut) {
	this.dateOut = dateOut;
    }

    public Date getDateDue() {
	return dateDue;
    }

    public void setDateDue(Date dateDue) {
	this.dateDue = dateDue;
    }

    public Date getDateIn() {
	return dateIn;
    }

    public void setDateIn(Date dateIn) {
	this.dateIn = dateIn;
    }
}
