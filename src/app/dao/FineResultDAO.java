package app.dao;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.model.BookRow;
import app.model.FineResult;
import app.utils.MySQLConnect;

public class FineResultDAO {

    public List<FineResult> searchFinesByCardId(String cardIdInput) throws SQLException {
	System.out.println("In searchFinesByCardId(): " + " Card: " + cardIdInput);
	List<FineResult> finesList = new ArrayList<FineResult>();
	MySQLConnect dbConn = MySQLConnect.getDbCon();
	String select = "select sum(f.fine_Amt) as fineAmount, f.paid as paid, b.card_Id as cardId ";
	String from = "from  Fines f, Book_Loans b ";
	String where = "where f.loan_Id=b.loan_Id and f.paid <> 1 and b.card_id = " + cardIdInput;
	String groupBy = " group by b.card_Id,f.paid";
	String query = select + from + where + groupBy;
	ResultSet rs = dbConn.query(query);
	System.out.println("Search Query: " + query);
	while (rs.next()) {
	    BigDecimal fineAmount = rs.getBigDecimal("fineAmount");
	    int cardId = rs.getInt("cardId");
	    int paid = rs.getInt("paid");
	    String paidStatus = getPaidStatus(paid);
	    FineResult fine = new FineResult(fineAmount, paid, cardId, paidStatus);
	    System.out.println(" FineAmount: " + fineAmount + " CardId: " + cardId + " Paid: " + paid);
	    finesList.add(fine);

	}
	return finesList;
    }

    public List<FineResult> payFinesByCardIdOld(String cardIdInput) throws SQLException {
	List<FineResult> fines = null;
	System.out.println("In payFinesByCardId(): " + " Card: " + cardIdInput);
	MySQLConnect dbConn = MySQLConnect.getDbCon();
	String updateFinesQuery = "UPDATE fines " + "SET paid = 1 " + "WHERE loan_id = ?";
	PreparedStatement pstmt = dbConn.conn.prepareStatement(updateFinesQuery);
	int loan_id = 2;
	pstmt.setInt(1, loan_id);
	int rowAffected = pstmt.executeUpdate();
	System.out.println(String.format("Updated rows: ", rowAffected));
	fines = searchFinesByCardId(cardIdInput);
	return fines;
    }

    public HashMap<String, List<BookRow>> payFinesByCardId(String cardIdInput) throws SQLException {
	List<FineResult> fines = null;
	List<String> isbnUnpaid = new ArrayList<String>();
	List<BookRow> fail = new ArrayList<BookRow>();
	List<BookRow> succ = new ArrayList<BookRow>();
	HashMap<List<FineResult>, List<String>> payFinesResult = new HashMap<List<FineResult>, List<String>>();
	HashMap<String, List<BookRow>> fineSummary = new HashMap<String, List<BookRow>>();

	System.out.println("In payFinesByCardId(): " + " Card: " + cardIdInput);
	MySQLConnect dbConn = MySQLConnect.getDbCon();
	String updateToPaidQuery = "UPDATE fines " + "SET paid = 1 " + "WHERE loan_id = ?";
	String select = "select bl.date_in, bl.card_id, bl.Isbn, f.loan_id, b.title ";
	String from = "from Fines f, Book_Loans bl, book b ";
	String where = "where f.loan_Id=bl.loan_Id and b.isbn=bl.isbn  and bl.card_id=" + cardIdInput;
	String searchForFines = select + from + where;
	ResultSet rs = dbConn.query(searchForFines);
	while (rs.next()) {
	    Date dateIn = rs.getDate("date_in");
	    int loanId = rs.getInt("loan_id");
	    String isbn = rs.getString("isbn");
	    String title = rs.getString("title");
	    if (dateIn != null) {
		PreparedStatement pstmt = dbConn.conn.prepareStatement(updateToPaidQuery);
		pstmt.setInt(1, loanId);
		int rowAffected = pstmt.executeUpdate();
		System.out.println(String.format("Updated rows: ", rowAffected));
		BookRow b1 = new BookRow(isbn, title);
		succ.add(b1);
	    } else {
		isbnUnpaid.add(isbn);
		BookRow b2 = new BookRow(isbn, title);
		fail.add(b2);
	    }
	}
	System.out.println("Displaying unpaid books: ");
	for (int i = 0; i < isbnUnpaid.size(); i++)
	    System.out.println("ISBN: " + isbnUnpaid.get(i));
	fines = searchFinesByCardId(cardIdInput);
	payFinesResult.put(fines, isbnUnpaid);
	fineSummary.put("Success", succ);
	fineSummary.put("Fail", fail);

	return fineSummary;
    }

    public List<FineResult> refreshAllFines() throws SQLException {
	List<FineResult> fine = null;
	System.out.println("In refreshAllFines(): ");
	MySQLConnect dbConn = MySQLConnect.getDbCon();
	String query = "select  loan_id, date_out, date_due, date_in from book_loans";
	String sysDateQuery = "select  sysdate() from dual";
	Date sysDate = null;
	ResultSet rs = dbConn.query(query);
	ResultSet sysDateSet = dbConn.query(sysDateQuery);
	while (sysDateSet.next()) {
	    sysDate = sysDateSet.getDate("sysdate()");
	}

	System.out.println("System date: " + sysDate.getTime());
	while (rs.next()) {
	    int loanId = rs.getInt("loan_id");
	    Date dateDue = rs.getDate("date_due");
	    Date dateOut = rs.getDate("date_out");
	    Date dateIn = rs.getDate("date_in");
	    long diff = 0;
	    double fineAmount = 0;
	    if ((dateIn == null) && (sysDate.getTime() > dateDue.getTime())) {
		System.out.println("Diffing sysdate-dueDate");
		diff = (sysDate.getTime() - dateDue.getTime()) / (24 * 60 * 60 * 1000);
		fineAmount = diff * 0.25;
	    } else if (dateIn != null && (dateIn.getTime() > dateDue.getTime())) {
		System.out.println("Diffing dateIn-dueDate");
		diff = (dateIn.getTime() - dateDue.getTime()) / (24 * 60 * 60 * 1000);
		fineAmount = diff * 0.25;
	    }
	    System.out.println("No of days: " + diff + " Amount: " + fineAmount);
	    insertOrUpdateFines(loanId, fineAmount);

	    fine = displayAllUnpaidFines();
	}
	return fine;
    }

    private List<FineResult> displayAllUnpaidFines() throws SQLException {
	System.out.println("In displayAllFines(): ");
	List<FineResult> finesList = new ArrayList<FineResult>();
	MySQLConnect dbConn = MySQLConnect.getDbCon();
	String select = "select sum(f.fine_Amt) as fineAmount, f.paid as paid, b.card_Id as cardId ";
	String from = "from  Fines f, Book_Loans b ";
	String where = "where f.loan_Id=b.loan_Id and f.paid <> 1";
	String groupBy = " group by b.card_Id,f.paid";
	String query = select + from + where + groupBy;
	ResultSet rs = dbConn.query(query);
	System.out.println("Search Query: " + query);
	while (rs.next()) {
	    BigDecimal fineAmount = rs.getBigDecimal("fineAmount");
	    int cardId = rs.getInt("cardId");
	    String cardIdString = rs.getString("cardId");
	    System.out.println(cardIdString);
	    int paid = rs.getInt("paid");
	    String paidStatus = getPaidStatus(paid);
	    FineResult fine = new FineResult(fineAmount, paid, cardId, paidStatus);
	    System.out.println(" FineAmount: " + fineAmount + " CardId: " + cardId + " Paid: " + paid);
	    finesList.add(fine);

	}
	return finesList;
    }

    private void insertFines(int loanId, double fineAmount) throws SQLException {
	if (fineAmount != 0.0) {
	    System.out.println("In insertFines(): " + " LoanId: " + loanId + " fineAmount: " + fineAmount);
	    MySQLConnect dbConn = MySQLConnect.getDbCon();
	    String insertFinesQuery = " insert into fines ( Loan_id ,  Fine_amt ,  Paid )" + " values (?, ?, ?)";
	    PreparedStatement pstmt = dbConn.conn.prepareStatement(insertFinesQuery);
	    pstmt.setInt(1, loanId);
	    pstmt.setDouble(2, fineAmount);
	    pstmt.setInt(3, 0);
	    boolean status = pstmt.execute();
	    System.out.println("insert is: " + status);
	} else {
	    System.out.println("FineAmount is 0, so not inserting a fine");
	}

    }

    private void insertOrUpdateFines(int loanId, double fineAmount) throws SQLException {
	System.out.println("In insertOrUpdateFines(): " + " LoanId: " + loanId + " fineAmount: " + fineAmount);
	MySQLConnect dbConn = MySQLConnect.getDbCon();
	String updateFinesQuery = "UPDATE fines " + "SET fine_amt = ? " + "WHERE paid <> 1 and loan_id = ?";
	String getCountQuery = "Select count(*) as total from fines where loan_id = " + loanId;
	PreparedStatement pstmt = dbConn.conn.prepareStatement(updateFinesQuery);
	double fine_amount = fineAmount;
	int loan_id = loanId;
	pstmt.setDouble(1, fine_amount);
	pstmt.setInt(2, loan_id);

	int rowAffected = pstmt.executeUpdate();
	System.out.println(String.format("Updated rows: ", rowAffected));

	if (rowAffected == 0) {
	    ResultSet rs = dbConn.query(getCountQuery);
	    boolean exists = false;
	    while (rs.next()) {
		int total = rs.getInt("total");
		if (total != 0)
		    exists = true;
	    }
	    if (!exists) {
		System.out.println("No record exists for the loanId: " + loan_id + " inserting new row.");
		insertFines(loanId, fineAmount);
	    } else {
		System.out.println("The record already exists with loadId " + loan_id + " and paid status as 0.");

	    }
	}
    }

    public List<FineResult> displayAllFines() throws SQLException {
	System.out.println("In displayAllFines(): ");
	List<FineResult> finesList = new ArrayList<FineResult>();
	MySQLConnect dbConn = MySQLConnect.getDbCon();
	String select = "select sum(f.fine_Amt) as fineAmount, f.paid as paid, b.card_Id as cardId ";
	String from = "from  Fines f, Book_Loans b ";
	String where = "where f.loan_Id=b.loan_Id";
	String groupBy = " group by b.card_Id,f.paid";
	String query = select + from + where + groupBy;
	ResultSet rs = dbConn.query(query);
	System.out.println("Search Query: " + query);
	while (rs.next()) {
	    BigDecimal fineAmount = rs.getBigDecimal("fineAmount");
	    int cardId = rs.getInt("cardId");
	    String cardIdString = rs.getString("cardId");
	    System.out.println(cardIdString);
	    int paid = rs.getInt("paid");
	    String paidStatus = getPaidStatus(paid);
	    FineResult fine = new FineResult(fineAmount, paid, cardId, paidStatus);
	    System.out.println(" FineAmount: " + fineAmount + " CardId: " + cardId + " Paid: " + paid);
	    finesList.add(fine);

	}
	return finesList;
    }

    public List<FineResult> displayUnpaidFines() throws SQLException {
	System.out.println("In displayUnpaidFines(): ");
	List<FineResult> finesList = new ArrayList<FineResult>();
	MySQLConnect dbConn = MySQLConnect.getDbCon();
	String select = "select sum(f.fine_Amt) as fineAmount, f.paid as paid, b.card_Id as cardId ";
	String from = "from  Fines f, Book_Loans b ";
	String where = "where f.loan_Id=b.loan_Id and f.paid=0";
	String groupBy = " group by b.card_Id,f.paid";
	String query = select + from + where + groupBy;
	ResultSet rs = dbConn.query(query);
	System.out.println("Search Query: " + query);
	while (rs.next()) {
	    BigDecimal fineAmount = rs.getBigDecimal("fineAmount");
	    int cardId = rs.getInt("cardId");
	    String cardIdString = rs.getString("cardId");
	    System.out.println(cardIdString);
	    int paid = rs.getInt("paid");
	    String paidStatus = getPaidStatus(paid);
	    FineResult fine = new FineResult(fineAmount, paid, cardId, paidStatus);
	    System.out.println(" FineAmount: " + fineAmount + " CardId: " + cardId + " Paid: " + paid);
	    finesList.add(fine);

	}
	return finesList;
    }

    private String getPaidStatus(int paid) {
	String status = "";
	if (paid == 0)
	    status = "Not Paid";
	if (paid == 1)
	    status = "Paid";
	return status;
    }
}
