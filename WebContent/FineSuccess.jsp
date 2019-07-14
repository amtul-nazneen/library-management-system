<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</head>
<body>
 <div class="container">
         <h2>Manage Fines</h2>
         <h3>Fine Payment Summary</h3>
   <form class="form-inline" action="Fines" method="post">
   <div class="form-group">
<div class="alert alert-success" role="alert">
<p></p>
  <!-- h4 class="alert-heading">Well done!</h4-->
  <p>The fines for the below books have been successfully cleared.</p>
  <p>Please ignore if the below section is empty.</p>
   <table class="table table-striped">
   <thead>
   <tr>
   <th>ISBN</th>
   <th>Book Title</th>
   </tr>
   </thead>
   <tbody>
   <c:forEach items="${succ}" var="successRow">
   <tr>
   <td><c:out value="${successRow.isbn}" /></td>
   <td><c:out value="${successRow.title}" /></td>
   </tr>
   </c:forEach>
   </tbody>
   </table>
</div>

<div class="alert alert-danger" role="alert">
<p></p>
  <!--h4 class="alert-heading">Well done!</h4-->
  <p>Check in the below books if you haven't already, in order to clear the fines. </p>
  <p>Please ignore if the below section is empty.</p>
  <table class="table table-striped">
   <thead>
   <tr>
   <th>ISBN</th>
   <th>Book Title</th>
   </tr>
   </thead>
   <tbody>
   <c:forEach items="${fail}" var="failRow">
   <tr>
   <td><c:out value="${failRow.isbn}" /></td>
   <td><c:out value="${failRow.title}" /></td>
   </tr>
   </c:forEach>
   </tbody>
   </table>
</div>
<a href="Fines.jsp" class="btn btn-info" role="button">Back to Manage Fines</a>
<a href="Home.jsp" class="btn btn-info pull-right" role="button">Back to Home</a>
</div>
</form>
</div>
</body>
</html>
