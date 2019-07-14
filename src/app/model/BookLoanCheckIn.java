package app.model;

public class BookLoanCheckIn {
    String isbn;
    int cardId;
    String name;
    String title;
    int loanId;

    public BookLoanCheckIn(String isbn, int cardId, String name, String title, int loanId) {
	super();
	this.isbn = isbn;
	this.cardId = cardId;
	this.name = name;
	this.title = title;
	this.loanId = loanId;
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

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

}
