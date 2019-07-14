package app.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.model.Borrower;
import app.utils.MySQLConnect;

public class BorrowerDAO {

    public void createBorrower(String fname, String lname, String email, String ssn, String address, String city,
	    String state, String phone) throws Exception {
	try {
	    MySQLConnect dbConn = MySQLConnect.getDbCon();
	    String insertQuery = " insert into borrower ( first_name ,  last_name ,  email ,  ssn, address, city, state, phone)"
		    + " values (?, ?, ?,?, ? , ? ,? , ?)";
	    PreparedStatement pstmt = dbConn.conn.prepareStatement(insertQuery);
	    pstmt.setString(1, fname);
	    pstmt.setString(2, lname);
	    pstmt.setString(3, email);
	    pstmt.setString(4, ssn);
	    pstmt.setString(5, address);
	    pstmt.setString(6, city);
	    pstmt.setString(7, state);
	    pstmt.setString(8, phone);
	    boolean status = pstmt.execute();
	    System.out.println("Insert status: " + status);
	} catch (Exception e) {
	    throw new Exception(e.getMessage());
	}

    }

    /*
     * public void createBorrowerHibernate(String fname, String lname, String email,
     * String ssn, String address, String city, String state, String phone) throws
     * HibernateException { try { Configuration configuration = new
     * Configuration().configure(); SessionFactory sessionFactory =
     * configuration.buildSessionFactory(); Session session =
     * sessionFactory.openSession(); Transaction transaction =
     * session.beginTransaction();
     * 
     * Borrower borrower = new Borrower(); borrower.setFname(fname);
     * borrower.setLname(lname); borrower.setEmail(email); borrower.setSsn(ssn);
     * borrower.setAddress(address); borrower.setCity(city);
     * borrower.setState(state); borrower.setPhone(phone);
     * System.out.println(borrower); session.save(borrower);
     * 
     * transaction.commit();
     * 
     * System.out.println("\n\n  Borrower Details Added \n");
     * 
     * } catch (HibernateException e) { System.out.println(e.getMessage());
     * e.printStackTrace(); System.out.println("Error in addBorrowerDetails()");
     * throw new HibernateException(e); }
     * 
     * }
     * 
     * 
     * public void sampleCheck(Transaction tx, Session session) {
     * System.out.println("In sampleCheck() .. "); String hql = " from Fines";
     * String mainQ =
     * "select sum(f.fine_Amt) as fineAmount, f.paid as paid, b.card_Id as cardId" +
     * " from  Fines f, Book_Loans b" + " where f.loan_Id=b.loan_Id and f.paid <> 1"
     * + " group by b.card_Id,f.paid"; System.out.println(mainQ); // Query
     * query=(Query) session.createSQLQuery(mainQ).setResultTransformer( //
     * Transformers.aliasToBean(FineResult.class)).list();// //
     * query.setResultTransformer(new MyResultTransformer()); List employees =
     * session.createSQLQuery(mainQ).addScalar("fineAmount").addScalar("paid").
     * addScalar("cardId")
     * .setResultTransformer(Transformers.aliasToBean(FineResult.class)).list();
     * System.out.println("SIZE: " + employees.size());
     * System.out.println("Printing results: ");
     * 
     * FineResult fine = (FineResult) employees.get(0);
     * 
     * // for(FineResult fine: employees) // { System.out.println("Amount: " +
     * fine.getFineAmount()); System.out.println("CardId: " + fine.getCardId());
     * System.out.println("Paid: " + fine.getPaid()); // }
     * 
     * // tx.commit();
     * 
     * System.out.println("End fo sampleCheck()..");
     * 
     * }
     */

    public String getCreatedBorrowerId(String ssn) throws SQLException {
	System.out.println("In getCreatedBorrowerId(): " + " SSN: " + ssn);
	MySQLConnect dbConn = MySQLConnect.getDbCon();
	String query = "select card_id from borrower where ssn=" + "'" + ssn.trim() + "'";
	ResultSet rs = dbConn.query(query);
	int cardId = 0;
	System.out.println("Search Query: " + query);
	while (rs.next()) {
	    cardId = rs.getInt("card_id");
	}
	System.out.println("Returning cardId: " + cardId);
	int digits = getDigits(cardId);
	String cardIdNew = getPaddedCardId(digits, cardId);
	return cardIdNew;
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
