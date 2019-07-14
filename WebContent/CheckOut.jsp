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
  <h2>Confirmation</h2>
  <h4>Please provide your Card ID to begin the check out.</h4>
  <form action="CheckOut" method="post">
    <div class="form-group">
      <input type="text" class="form-control" id="cardId" placeholder="Enter Card ID" name="cardId">
    </div>
    
  <h3>Selected Books</h3> 
    <table class="table table-striped">
    <thead>
      <tr>
        <th>ISBN</th>
        <th>Title</th>
      </tr>
    </thead>
    <tbody>
     <c:forEach items="${booksToCheckOut}" var="bookRow">
    <tr>
      <td><c:out value="${bookRow.isbn}" /></td>
      
      <td><c:out value="${bookRow.title}" /></td>
      
      <!--td><c:out value="${bookRow.author}" /></td-->
    </tr>
  </c:forEach>
    </tbody>
  </table>

<button type="submit" class="btn btn-primary pull-right">Proceed to Check Out</button>
<a href="BookSearch.jsp" class="btn btn-info" role="button">Back to Book Search</a>
</form>
    </div>

</body>
</html>