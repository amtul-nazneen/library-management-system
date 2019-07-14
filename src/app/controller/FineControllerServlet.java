package app.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.dao.FineResultDAO;
import app.model.BookRow;
import app.model.FineResult;

public class FineControllerServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	try {
	    System.out.println(request.getParameter("cardNo"));
	    System.out.println(request.getParameter("refreshFines"));
	    System.out.println(request.getParameter("searchFines"));
	    System.out.println(request.getParameter("payFines"));

	    FineResultDAO fineDAO = new FineResultDAO();
	    List<FineResult> finesList = null;
	    List<String> isbnUnpaid = null;
	    HashMap<List<FineResult>, List<String>> payFinesResult = null;
	    HashMap<String, List<BookRow>> fineSummary = null;

	    String cardNo = request.getParameter("cardNo");
	    System.out.println("CardId entered is: " + cardNo);

	    Integer cardId = null;
	    if (cardNo != null && !cardNo.isEmpty()) {
		cardId = Integer.parseInt(cardNo);
		// System.out.println("Converted cardId: " + cardId);
	    }
	    if (request.getParameter("searchFines") != null) {
		finesList = fineDAO.searchFinesByCardId(cardNo);
	    }
	    if (request.getParameter("payFines") != null) {
		fineSummary = fineDAO.payFinesByCardId(cardNo);
	    }
	    if (request.getParameter("refreshFines") != null) {
		finesList = fineDAO.refreshAllFines();
	    }
	    if (request.getParameter("displayFines") != null) {
		finesList = fineDAO.displayAllFines();
	    }
	    if (request.getParameter("displayUnpaidFines") != null) {
		finesList = fineDAO.displayUnpaidFines();
	    }

	    if (request.getParameter("payFines") != null) {
		request.setAttribute("fail", fineSummary.get("Fail"));
		request.setAttribute("succ", fineSummary.get("Success"));
		RequestDispatcher dispatcher = request.getRequestDispatcher("FineSuccess.jsp");
		if (dispatcher != null) {
		    dispatcher.forward(request, response);
		}
	    } else {
		List<FineResult> finesList2 = FineResult.modifyList(finesList);
		request.setAttribute("finesByCardId", finesList2);
		RequestDispatcher dispatcher = request.getRequestDispatcher("Fines.jsp");
		if (dispatcher != null) {
		    dispatcher.forward(request, response);
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    RequestDispatcher dispatcher = request.getRequestDispatcher("FineError.jsp");
	    if (dispatcher != null) {
		dispatcher.forward(request, response);
	    }
	}
    }

}
