package app.controller;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.dao.BookLoanCheckInDAO;
import app.model.BookLoanCheckIn;
import app.utils.MySQLConnect;

public class CheckInControllerServlet extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	try {
	    System.out.println("In CheckInControllerServlet() ");
	    String name = request.getParameter("borrowerName");
	    name = name.replace("'", "''");
	    String card = request.getParameter("cardId");
	    String isbn = request.getParameter("isbn");
	    if (request.getParameter("searchBooks") != null) {
		BookLoanCheckInDAO checkIn = new BookLoanCheckInDAO();
		List<BookLoanCheckIn> booksList = checkIn.searchBooksForCheckIn(card, name, isbn);
		request.setAttribute("booksForCheckIn", booksList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("CheckIn.jsp");
		if (dispatcher != null) {
		    dispatcher.forward(request, response);
		}
	    }
	    if (request.getParameter("checkIn") != null) {
		String selectedCheckInBook = request.getParameter("selectedCheckInBook");
		System.out.println("selectedCheckInBook LoanId: " + selectedCheckInBook);
		BookLoanCheckInDAO checkIn = new BookLoanCheckInDAO();
		checkIn.checkInSelectedBook(Integer.parseInt(selectedCheckInBook));
		RequestDispatcher dispatcher = request.getRequestDispatcher("CheckInSuccess.jsp");
		if (dispatcher != null) {
		    dispatcher.forward(request, response);
		}
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	    RequestDispatcher dispatcher = request.getRequestDispatcher("CheckInError.jsp");
	    if (dispatcher != null) {
		dispatcher.forward(request, response);
	    }
	}
    }

}
