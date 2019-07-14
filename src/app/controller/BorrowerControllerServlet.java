package app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.dao.BorrowerDAO;
import app.model.FineResult;

public class BorrowerControllerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	String fname = request.getParameter("fname");
	String lname = request.getParameter("lname");
	String email = request.getParameter("email");
	String ssn = request.getParameter("ssn");
	String address = request.getParameter("address");
	String city = request.getParameter("city");
	String state = request.getParameter("state");
	String phone = request.getParameter("phone");

	phone = phone.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
	ssn = ssn.replaceFirst("(\\d{3})(\\d{2})(\\d+)", "$1-$2-$3");

	System.out.println("Modified Phone number: " + phone);
	System.out.println("Modified SSN number: " + ssn);
	HttpSession session = request.getSession(true);
	try {
	    BorrowerDAO bDAO = new BorrowerDAO();
	    bDAO.createBorrower(fname, lname, email, ssn, address, city, state, phone);
	    String cardId = bDAO.getCreatedBorrowerId(ssn);
	    request.getSession().setAttribute("createdId", cardId);
	    request.getSession().setAttribute("forBorrower", lname + " " + fname);
	    response.sendRedirect("BorrowerSuccess.jsp");
	} catch (Exception e) {
	    e.printStackTrace();
	    response.sendRedirect("BorrowerFailure.jsp");
	}

    }
}
