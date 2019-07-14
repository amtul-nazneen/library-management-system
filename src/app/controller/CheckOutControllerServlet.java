package app.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.dao.BookLoanCheckOutDAO;

/**
 * Servlet implementation class CheckOutControllerServlet
 */
@WebServlet("/CheckOutControllerServlet")
public class CheckOutControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	try {
	    System.out.println("In CheckOutControllerServlet:doPost()");
	    String cardId = request.getParameter("cardId");
	    System.out.println("CardID: " + cardId);

	    String[] checkOutList = (String[]) request.getSession().getAttribute("checkOutList");
	    for (int i = 0; i < checkOutList.length; i++)
		System.out.println("Books selected for checkout: " + checkOutList[i]);
	    BookLoanCheckOutDAO checkout = new BookLoanCheckOutDAO();
	    checkout.checkOutSelectedBooks(cardId, checkOutList);
	    RequestDispatcher dispatcher = request.getRequestDispatcher("CheckOutSuccess.jsp");
	    if (dispatcher != null) {
		dispatcher.forward(request, response);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    request.getSession().setAttribute("errorMessage", e.getMessage());
	    RequestDispatcher dispatcher = request.getRequestDispatcher("CheckOutFailure.jsp");
	    if (dispatcher != null) {
		dispatcher.forward(request, response);
	    }
	}
    }

}
