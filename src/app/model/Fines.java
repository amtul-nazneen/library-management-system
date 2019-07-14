package app.model;

public class Fines {
    int loanId;
    double fineAmount;
    int paid;
    BookLoans bookLoan;

    public int getLoanId() {
	return loanId;
    }

    public void setLoanId(int loanId) {
	this.loanId = loanId;
    }

    public double getFineAmount() {
	return fineAmount;
    }

    public void setFineAmount(double fineAmount) {
	this.fineAmount = fineAmount;
    }

    public int getPaid() {
	return paid;
    }

    public void setPaid(int paid) {
	this.paid = paid;
    }

    public BookLoans getBookLoan() {
	return bookLoan;
    }

    public void setBookLoan(BookLoans bookLoan) {
	this.bookLoan = bookLoan;
    }
}
