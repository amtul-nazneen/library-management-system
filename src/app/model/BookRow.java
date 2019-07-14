package app.model;

public class BookRow {
    String isbn;
    String title;
    String author;
    String status;
    int availability;

    public BookRow(String isbn, String title, String author, String status, int availability) {
	this.isbn = isbn;
	this.title = title;
	this.author = author;
	this.status = status;
	this.availability = availability;

    }

    public BookRow(String isbn, String title) {
	this.isbn = isbn;
	this.title = title;
    }

    public String getIsbn() {
	return isbn;
    }

    public void setIsbn(String isbn) {
	this.isbn = isbn;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getAuthor() {
	return author;
    }

    public void setAuthor(String author) {
	this.author = author;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public int getAvailability() {
	return availability;
    }

    public void setAvailability(int availability) {
	this.availability = availability;
    }

}
