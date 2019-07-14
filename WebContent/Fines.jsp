<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<!DOCTYPE html>
<body>
  
      <div class="container">
         <h2>Manage Fines</h2>
   <form class="form-inline" action="Fines" method="post">
   <div class="form-group">
   <input type="text" class="form-control" id="cardNo" placeholder="Enter Card ID" name="cardNo" width="60">
   </div>
   <button type="submit" class="btn btn-primary" name="searchFines">Search Fines</button>
   <button type="submit" class="btn btn-primary" name="payFines">Pay Fines</button>
   
   <div class="btn-group pull-right" role="group" aria-label="Third group">
    <button type="submit" class="btn btn-success pull-right mr-1" name="refreshFines">Refresh Unpaid Fines</button>
  </div>
   <div class="btn-group pull-right" role="group" aria-label="Fourth group">
    <button type="submit" class="btn btn-warning pull-right mr-1" name="displayFines">Display All Fines</button>
  </div>
   <div class="btn-group pull-right" role="group" aria-label="Fifth group">
    <button type="submit" class="btn btn-warning pull-right mr-1" name="displayUnpaidFines">Display Unpaid Fines</button>
  </div> 
   </form>
   
  <h2>Results</h2>  
   <table class="table table-striped">
   <thead>
   <tr>
   <th>Card ID</th>
   <th>Amount</th>
   <th>Paid Status</th>
   </tr>
   </thead>
   <tbody>
   <c:forEach items="${finesByCardId}" var="finesRow">
   <tr>
   <td><c:out value="${finesRow.cardIdModified}" /></td>
   <td><c:out value="${finesRow.fineAmount}" /></td>
   <td><c:out value="${finesRow.paidStatus}" /></td>
   </tr>
   </c:forEach>
   </tbody>
   </table>
   <a href="Home.jsp" class="btn btn-info" role="button">Back to Home</a>
   </div>
</body>