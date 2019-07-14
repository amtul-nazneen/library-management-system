<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
 <h2>Book Search and Availability</h2>
<form action="BookSearch" method="post">
        <div class="form-group">
      <input type="text" class="form-control input-lg" id="input" placeholder="Search by Book Title, Author or ISBN" name="input" width="100%">
    </div>
             <button type="submit" class="btn btn-primary pull-right" name="searchBook">Search Book</button> 

  <h3>Results</h3>           
  <table class="table table-striped">
    <thead>
      <tr>
      <th>Select</th>
        <th>ISBN</th>
        <th>Title</th>
        <th>Author</th>
        <th>Available</th>
      </tr>
    </thead>
    <tbody>
     <c:forEach items="${books}" var="bookRow">
    <tr>
    <td> <div class="checkbox">
      <label><input type="checkbox" name="selectedBooks" value="${bookRow.isbn}"></label>
    </div>
      <td><c:out value="${bookRow.isbn}" /></td>
      
      <td><c:out value="${bookRow.title}" /></td>
      
      <td><c:out value="${bookRow.author}" /></td>
     
      <td><c:out value="${bookRow.status}" /></td>
    </tr>
  </c:forEach>
    </tbody>
  </table>
    <a href="Home.jsp" class="btn btn-info" role="button">Back to Home</a>
    <button type="submit" class="btn btn-success pull-right" name="checkOut">Check Out</button> 
    </form>
</div>

</body>
</html>