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
         <h2>Check In Confirmation</h2>
   <form class="form-inline">
<div class="alert alert-success" role="alert">
  <h4 class="alert-heading">Thank You!</h4>
  <p>The selected books have been checked in.</p>
</div>
<a href="CheckIn.jsp" class="btn btn-info" role="button">Back to Check In Books</a>
<a href="Home.jsp" class="btn btn-info pull-right" role="button">Back to Home</a>
</form>
</div>
</body>
</html>
