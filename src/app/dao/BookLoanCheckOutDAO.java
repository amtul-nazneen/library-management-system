package app.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import app.utils.MySQLConnect;

public class BookLoanCheckOutDAO {

    public void checkOutSelectedBooks(String cardId, String[] isbn) throws Exception {
	System.out.println("Provided Card Id: " + cardId);
	validateUnavailableBook(isbn);
	validateMoreThan3Books(cardId, isbn);
	MySQLConnect dbConn = MySQLConnect.getDbCon();
	String insertFinesQuery = " insert into book_loans ( Isbn ,  Card_id ,  Date_out ,  Date_Due)"
		+ " values (?, ?, ?,?)";
	Date today = getSysdate();
	Date due = getDueDate(today);
	System.out.println("Difference: " + (due.getTime() - today.getTime()) / (24 * 60 * 60 * 1000));
	for (int i = 0; i < isbn.length; i++) {
	    PreparedStatement pstmt = dbConn.conn.prepareStatement(insertFinesQuery);
	    pstmt.setString(1, isbn[i]);
	    pstmt.setString(2, cardId);
	    pstmt.setDate(3, today);
	    pstmt.setDate(4, due);
	    boolean status = pstmt.execute();
	    System.out.println("insert for isbn: " + isbn[i] + " is: " + status);
	}
	updateAvailabilityToNo(isbn);
    }

    private Date getSysdate() throws SQLException {
	String sysDateQuery = "select  sysdate() from dual";
	MySQLConnect dbConn = MySQLConnect.getDbCon();
	Date sysDate = null;
	ResultSet sysDateSet = dbConn.query(sysDateQuery);
	while (sysDateSet.next()) {
	    sysDate = sysDateSet.getDate("sysdate()");
	}
	return sysDate;
    }

    private Date getDueDate(Date today) {
	Calendar cal = Calendar.getInstance();
	cal.setTime(today);
	cal.add(Calendar.DATE, 14);
	java.sql.Date dueDate = new java.sql.Date(cal.getTimeInMillis());
	return dueDate;
    }

    private void updateAvailabilityToNo(String[] isbn) throws SQLException {

	System.out.println("In update availability to no(): ");
	String updateQuery = "update book set availability=0 where isbn = ?";
	MySQLConnect dbConn = MySQLConnect.getDbCon();
	for (int i = 0; i < isbn.length; i++) {
	    PreparedStatement pstmt = dbConn.conn.prepareStatement(updateQuery);
	    pstmt.setString(1, isbn[i]);
	    int rowAffected = pstmt.executeUpdate();
	    System.out.println(String.format("Updated rows: ", rowAffected));
	}
    }

    private void validateMoreThan3Books(String cardId, String[] isbn) throws Exception {
	if (isbn != null && isbn.length > 3) {
	    throw new Exception("You are allowed to check out not more than three books at a time.");
	}
	if (isbn.length == 0) {
	    throw new Exception("Please select atleast one and atmost three books in order to check out.");
	}
	String currentbooks = "select count(*) as total from book_loans where card_id=" + cardId
		+ " and date_in is null";
	MySQLConnect dbConn = MySQLConnect.getDbCon();
	ResultSet rs = dbConn.query(currentbooks);
	while (rs.next()) {
	    int count = rs.getInt("total");
	    if (count == 3) {
		throw new Exception("You are allowed to check out not more than three books at a time. "
			+ "You already have three books loaned with you at the moment.");
	    } else if ((count + isbn.length) > 3) {
		throw new Exception("You are allowed to check out not more than three books at a time. "
			+ "You already have a few books loaned with you at the moment.");
	    }
	}
    }

    private void validateUnavailableBook(String[] isbn) throws Exception {
	String query = "select availability  from book	where isbn=";
	MySQLConnect dbConn = MySQLConnect.getDbCon();
	int count = 0;
	for (int i = 0; i < isbn.length; i++) {
	    String temp = query + "'" + isbn[i] + "'";
	    System.out.println("The inner query is: " + temp);
	    ResultSet rs = dbConn.query(temp);
	    while (rs.next()) {
		int value = rs.getInt("availability");
		if (value == 0) {
		    count++;
		}
	    }
	}
	if (count > 0) {
	    throw new Exception("Your selection involves " + count + " books that are currently unavailable.");
	}
    }

}
