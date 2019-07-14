<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Check In</title>
</head>
<body>

<div class="container">
  <h2>Check In Books</h2>
  <form action="CheckIn" method="post">
    <div class="form-group">
	<label for="borrowerName">Name</label>
      <input type="text" class="form-control" id="borrowerName" placeholder="Enter Name" name="borrowerName">
    </div>
     <div class="form-group">
      <label for="cardId">Card ID</label>
      <input type="text" class="form-control" id="cardId" placeholder="Enter Card ID" name="cardId">
    </div>
     <div class="form-group">
      <label for="bookName">ISBN</label>
      <input type="text" class="form-control" id="bookName" placeholder="Enter Book ISBN" name="isbn">
    </div>
    <button type="submit" class="btn btn-primary pull-right" name="searchBooks">Search</button>
    <h3>Results</h3>
     <table class="table table-striped">
    <thead>
      <tr>
      <th>Select</th>
        <th>ISBN</th>
        <th>Title</th>
      </tr>
    </thead>
    <tbody>
     <c:forEach items="${booksForCheckIn}" var="bookRow">
    <tr>
    <td> <div class="checkbox">
      <label><input type="radio" name="selectedCheckInBook" value="${bookRow.loanId}"></label>
    </div>
      <td ><c:out value="${bookRow.isbn}" /></td>
      
      <td><c:out value="${bookRow.title}" /></td>
    </tr>
  </c:forEach>
    </tbody>
  </table>
  
    <button type="submit" class="btn btn-success pull-right" name="checkIn">Check In</button> 
    <a href="Home.jsp" class="btn btn-info" role="button">Back to Home</a>
</form>
    </div>

</body>
</html>