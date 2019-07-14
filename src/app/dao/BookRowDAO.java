package app.dao;

import app.model.BookRow;
import app.utils.MySQLConnect;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class BookRowDAO {

    public List<BookRow> searchBooksByInput(String searchInput) throws SQLException {
	System.out.println("In searchBooksByInput(): " + " Search string: " + searchInput);
	HashMap<String, String> isbnAuthors = new HashMap<String, String>();
	HashMap<String, HashMap<String, String>> isbnMisc = new HashMap<String, HashMap<String, String>>();

	// List<BookRow> booksList = new ArrayList<BookRow>();
	List<BookRow> booksList = null;
	MySQLConnect dbConn = MySQLConnect.getDbCon();
	String select = "select  b.isbn, b.title, a.name, b.availability ";
	String from = "from book b, authors a, book_authors ba ";
	String where = "where b.isbn = ba.isbn and ba.author_id=a.author_id";
	String query = select + from + where;
	if (searchInput.isEmpty()) {
	    query = query;
	} else {
	    String temp = getUniversalSearchQuery(searchInput);
	    query += temp;
	}
	ResultSet rs = dbConn.query(query);
	System.out.println("Search Query: " + query);
	while (rs.next()) {
	    String isbn = rs.getString("isbn");
	    String title = rs.getString("title");
	    String author = rs.getString("name");
	    int availability = rs.getInt("availability");
	    String status = getBookAvailabilityStatus(availability);

	    // BookRow fine = new BookRow(isbn, title, author, status, availability);
	    System.out.println(
		    " isbn: " + isbn + " title: " + title + " author: " + author + " availability: " + availability);
	    // booksList.add(fine);

	    if (isbnAuthors.get(isbn) != null) {
		String val = isbnAuthors.get(isbn);
		val += ", " + author;
		isbnAuthors.put(isbn, val);
	    } else {
		isbnAuthors.put(isbn, author);
	    }
	    if (isbnMisc.get(isbn) == null) {
		HashMap<String, String> temp = new HashMap<String, String>();
		temp.put("title", title);
		temp.put("status", status);
		temp.put("avl", String.valueOf(availability));
		isbnMisc.put(isbn, temp);
	    }

	}
	booksList = getBooksList(isbnAuthors, isbnMisc);
	return booksList;
    }

    private List<BookRow> getBooksList(HashMap<String, String> isbnAuthors,
	    HashMap<String, HashMap<String, String>> isbnMisc) {
	List<BookRow> booksList = new ArrayList<BookRow>();
	for (Map.Entry<String, String> entry : isbnAuthors.entrySet()) {
	    String key = entry.getKey();
	    String value = entry.getValue();

	    System.out.println("Key = " + key + ", Value = " + value);
	    String authors = value;
	    String title = isbnMisc.get(key).get("title");
	    String status = isbnMisc.get(key).get("status");
	    String avl = isbnMisc.get(key).get("avl");
	    String isbn = key;
	    BookRow fine = new BookRow(isbn, title, authors, status, Integer.parseInt(avl));
	    booksList.add(fine);
	}
	return booksList;
    }

    private String getBookAvailabilityStatus(int availability) {
	String status = "";
	if (availability == 0)
	    status = "No";
	if (availability == 1)
	    status = "Yes";
	return status;
    }

    public List<BookRow> searchBooksByIsbn(String[] isbnList) throws SQLException {
	System.out.println("In searchBooksByIsbn(): ");
	List<BookRow> booksList = new ArrayList<BookRow>();
	MySQLConnect dbConn = MySQLConnect.getDbCon();
	String select = "select  b.isbn, b.title, a.name, b.availability ";
	String from = "from book b, authors a, book_authors ba ";
	String where = "where b.isbn = ba.isbn and ba.author_id=a.author_id and b.isbn in (";
	for (int i = 0; i < isbnList.length; i++) {
	    if (i == isbnList.length - 1)
		where += "'" + isbnList[i] + "'";
	    else
		where += "'" + isbnList[i] + "'" + " , ";
	}
	String query = select + from + where + ")";
	System.out.println("Search Query: " + query);
	ResultSet rs = dbConn.query(query);
	while (rs.next()) {
	    String isbn = rs.getString("isbn");
	    String title = rs.getString("title");
	    String author = rs.getString("name");
	    int availability = rs.getInt("availability");
	    String status = getBookAvailabilityStatus(availability);

	    BookRow fine = new BookRow(isbn, title, author, status, availability);
	    System.out.println(
		    " isbn: " + isbn + " title: " + title + " author: " + author + " availability: " + availability);
	    booksList.add(fine);

	}
	return booksList;
    }

    private String getUniversalSearchQuery(String input) {
	String[] split = input.split("\\s+");

	String isbn = "(b.isbn like ";
	String title = "(b.title like ";
	String name = "(a.name like ";
	String temp = " and ( ";
	for (int i = 0; i < split.length; i++) {
	    temp += isbn + "'%" + split[i] + "%'" + ")" + " or ";
	    temp += title + "'%" + split[i] + "%'" + ")" + " or ";
	    if (i == split.length - 1) {
		temp += name + "'%" + split[i] + "%'" + ")";
	    } else {
		temp += name + "'%" + split[i] + "%'" + ")" + " or ";
	    }
	}
	temp += " )";
	System.out.println(temp);

	return temp;

    }

    public List<BookRow> getUniqueBooks(List<BookRow> bookList) {
	List<BookRow> books = new ArrayList<BookRow>();
	HashMap<String, String> map = new HashMap<String, String>();
	for (int i = 0; i < bookList.size(); i++) {
	    if (map.get(bookList.get(i).getIsbn()) == null) {
		map.put(bookList.get(i).getIsbn(), bookList.get(i).getTitle());
	    }
	}
	for (Map.Entry<String, String> entry : map.entrySet()) {
	    BookRow book = new BookRow(entry.getKey(), entry.getValue());
	    books.add(book);
	}

	return books;
    }

}
