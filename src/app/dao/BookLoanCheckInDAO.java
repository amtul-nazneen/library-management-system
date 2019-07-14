package app.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.model.BookLoanCheckIn;
import app.utils.MySQLConnect;

public class BookLoanCheckInDAO {

    public List<BookLoanCheckIn> searchBooksForCheckIn(String cardIdInput, String nameInput, String bookIsbn)
	    throws SQLException {
	System.out.println("In searchBooksForCheckIn(): " + " CardId: " + cardIdInput + " nameInput: " + nameInput
		+ " bookIsbn: " + bookIsbn);
	List<BookLoanCheckIn> booksList = new ArrayList<BookLoanCheckIn>();
	MySQLConnect dbConn = MySQLConnect.getDbCon();
	String select = "select bl.isbn, bl.card_id, b.first_name ,b.last_name, bo.title, bl.loan_id ";
	String from = "from book_loans bl, borrower b, book bo ";
	String where = getWhereClause(cardIdInput, nameInput, bookIsbn);
	System.out.println("Retrieved whereClause: " + where);

	String query = select + from + where;
	System.out.println("Search Query: " + query);
	ResultSet rs = dbConn.query(query);
	while (rs.next()) {
	    int cardId = rs.getInt("card_id");
	    int loanId = rs.getInt("loan_id");
	    String name = rs.getString("first_name") + rs.getString("last_name");
	    String title = rs.getString("title");
	    String isbn = rs.getString("isbn");
	    BookLoanCheckIn book = new BookLoanCheckIn(isbn, cardId, name, title, loanId);
	    booksList.add(book);
	}
	return booksList;
    }

    public void checkInSelectedBook(int loanId) throws Exception {
	System.out.println("Selected Book's Loan_ID: " + loanId);
	String updateQuery = "update book_loans set date_in =sysdate() where loan_id=?";
	MySQLConnect dbConn = MySQLConnect.getDbCon();
	PreparedStatement pstmt = dbConn.conn.prepareStatement(updateQuery);
	pstmt.setInt(1, loanId);
	int rowAffected = pstmt.executeUpdate();
	System.out.println(String.format("Updated rows: ", rowAffected));
	String isbn = getIsbnFromLoanId(loanId);
	updateBookAvailabilityToYes(isbn);
    }

    public static String getWhereClause(String cardId, String name, String isbn) {
	String where = "";
	if (cardId.isEmpty()) {
	    System.out.println("emptyyyyyy");
	}
	if (cardId.isEmpty())
	    cardId = null;
	if (name.isEmpty())
	    name = null;
	if (isbn.isEmpty())
	    isbn = null;

	if (cardId == null && name == null && isbn == null) {
	    where = "where b.card_id=bl.card_id and bo.isbn=bl.isbn and bl.date_in is null  ";
	}
	if (cardId != null && name != null && isbn != null) {
	    where = "where b.card_id=bl.card_id and bo.isbn=bl.isbn and bl.date_in is null  ";
	    where += "and b.card_id=" + cardId + " and bo.isbn=" + isbn + " and ( b.first_name like " + "'%" + name
		    + "%'" + " or " + "b.last_name like " + "'%" + name + "%')";
	}
	if (cardId == null && (name != null && isbn != null)) {
	    where = "where b.card_id=bl.card_id and bo.isbn=bl.isbn and bl.date_in is null  ";
	    where += "and bo.isbn=" + isbn + " and ( b.first_name like " + "'%" + name + "%'" + " or "
		    + "b.last_name like " + "'%" + name + "%')";
	}
	if (name == null && (cardId != null && isbn != null)) {
	    where = "where b.card_id=bl.card_id and bo.isbn=bl.isbn and bl.date_in is null  ";
	    where += "and b.card_id=" + cardId + " and bo.isbn=" + isbn;
	}
	if (isbn == null && (cardId != null && name != null)) {
	    where = "where b.card_id=bl.card_id and bo.isbn=bl.isbn and bl.date_in is null  ";
	    where += "and b.card_id=" + cardId + " and ( b.first_name like " + "'%" + name + "%'" + " or "
		    + "b.last_name like " + "'%" + name + "%')";
	}
	if (cardId != null && (name == null && isbn == null)) {
	    where = "where b.card_id=bl.card_id and bo.isbn=bl.isbn and bl.date_in is null  ";
	    where += "and b.card_id=" + cardId;
	}
	if (name != null && (cardId == null && isbn == null)) {
	    where = "where b.card_id=bl.card_id and bo.isbn=bl.isbn and bl.date_in is null  ";
	    where += " and ( b.first_name like " + "'%" + name + "%'" + " or " + "b.last_name like " + "'%" + name
		    + "%')";
	}
	if (isbn != null && (cardId == null && name == null)) {
	    where = "where b.card_id=bl.card_id and bo.isbn=bl.isbn and bl.date_in is null  ";
	    where += "and bo.isbn=" + isbn;
	}
	return where;

    }

    private String getWhereClauseold(String cardId, String name, String isbn) {
	String where = "";
	if (cardId.isEmpty()) {
	    System.out.println("emptyyyyyy");
	}
	if (cardId.isEmpty())
	    cardId = null;
	if (name.isEmpty())
	    name = null;
	if (isbn.isEmpty())
	    isbn = null;

	if (cardId == null && name == null && isbn == null) {
	    where = "where b.card_id=bl.card_id and bo.isbn=bl.isbn and bl.date_in is null  ";
	}
	if (cardId != null && name != null && isbn != null) {
	    where = "where b.card_id=bl.card_id and bo.isbn=bl.isbn and bl.date_in is null  ";
	    where += "and b.card_id=" + cardId + " and bo.isbn=" + isbn + " and b.bname like " + "'%" + name + "%'";
	}
	if (cardId == null && (name != null && isbn != null)) {
	    where = "where b.card_id=bl.card_id and bo.isbn=bl.isbn and bl.date_in is null  ";
	    where += "and bo.isbn=" + isbn + " and b.bname like " + "'%" + name + "%'";
	}
	if (name == null && (cardId != null && isbn != null)) {
	    where = "where b.card_id=bl.card_id and bo.isbn=bl.isbn and bl.date_in is null  ";
	    where += "and b.card_id=" + cardId + " and bo.isbn=" + isbn;
	}
	if (isbn == null && (cardId != null && name != null)) {
	    where = "where b.card_id=bl.card_id and bo.isbn=bl.isbn and bl.date_in is null  ";
	    where += "and b.card_id=" + cardId + " and b.bname like " + "'%" + name + "%'";
	}
	if (cardId != null && (name == null && isbn == null)) {
	    where = "where b.card_id=bl.card_id and bo.isbn=bl.isbn and bl.date_in is null  ";
	    where += "and b.card_id=" + cardId;
	}
	if (name != null && (cardId == null && isbn == null)) {
	    where = "where b.card_id=bl.card_id and bo.isbn=bl.isbn and bl.date_in is null  ";
	    where += "and b.bname like " + "'%" + name + "%'";
	}
	if (isbn != null && (cardId == null && name == null)) {
	    where = "where b.card_id=bl.card_id and bo.isbn=bl.isbn and bl.date_in is null  ";
	    where += "and bo.isbn=" + isbn;
	}
	return where;

    }

    private void updateBookAvailabilityToYes(String isbn) throws SQLException {

	System.out.println("In update availability to yes(): ");
	String updateQuery = "update book set availability=1 where isbn = ?";
	MySQLConnect dbConn = MySQLConnect.getDbCon();
	// for (int i = 0; i < isbn.length; i++) {
	PreparedStatement pstmt = dbConn.conn.prepareStatement(updateQuery);
	pstmt.setString(1, isbn);
	int rowAffected = pstmt.executeUpdate();
	System.out.println(String.format("Updated rows: ", rowAffected));
	// }
    }

    private String getIsbnFromLoanId(int loanId) throws Exception {
	System.out.println("In getIsbnFromLoanId: ");
	String isbn = null;
	String query = "select isbn from book_loans where loan_id=" + loanId;
	MySQLConnect dbConn = MySQLConnect.getDbCon();
	ResultSet rs = dbConn.query(query);
	while (rs.next()) {
	    isbn = rs.getString("isbn");
	}
	System.out.println("returning the isbn: " + isbn);
	return isbn;

    }
}
