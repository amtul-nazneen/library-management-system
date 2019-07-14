package app.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.dao.BookRowDAO;
import app.model.BookRow;

public class BookSearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	System.out.println("in BookSearchServlet() ");
	try {
	    if (request.getParameter("searchBook") != null) {
		BookRowDAO bookDAO = new BookRowDAO();
		List<BookRow> booksList = null;
		String input = request.getParameter("input");
		input = input.replace("'", "''");
		System.out.println("Input after replacing: " + input);
		booksList = bookDAO.searchBooksByInput(input);
		request.setAttribute("books", booksList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("BookSearch.jsp");
		if (dispatcher != null) {
		    dispatcher.forward(request, response);
		}
	    }
	    if (request.getParameter("checkOut") != null) {
		String[] selectedBooks = request.getParameterValues("selectedBooks");
		if (selectedBooks != null) {
		    System.out.println("Print the selected books from BookSearchServlet: ");
		    for (int i = 0; i < selectedBooks.length; i++)
			System.out.println(selectedBooks[i]);

		    BookRowDAO bookDAO2 = new BookRowDAO();
		    List<BookRow> booksList2, booksListTemp = null;
		    booksListTemp = bookDAO2.searchBooksByIsbn(selectedBooks);
		    booksList2 = bookDAO2.getUniqueBooks(booksListTemp);
		    request.setAttribute("booksToCheckOut", booksList2);

		    request.getSession().setAttribute("checkOutList", selectedBooks);
		    RequestDispatcher dispatcher = request.getRequestDispatcher("CheckOut.jsp");
		    if (dispatcher != null) {
			dispatcher.forward(request, response);
		    }
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    RequestDispatcher dispatcher = request.getRequestDispatcher("BookSearchError.jsp");
	    if (dispatcher != null) {
		dispatcher.forward(request, response);
	    }
	}
    }
}
