<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
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
  <br>
<div class="jumbotron text-center" style="margin-bottom:0">
  <!--h1 class="display-4">Once Upon a Book Library</h1-->
  <h1 class="display-4">Cover to Cover Library</h1>
     <blockquote class="blockquote text-right">
    <p class="lead"><em>"I have always imagined that paradise will be a kind of library."</em></p>
    <footer class="font-italic">Jorge Luis Borges</footer>
  </blockquote>
  <!--h4>Welcome to the Eugene McDermott Library! Please go through the tabs below to perform the necessary actions.</h4-->
  <!--p class="font-weight-light">Welcome to the Eugene McDermott Library! </p-->
</div>
     <ul class="nav nav-pills nav-justified">
    <li class="active"><a href="#">Home</a></li>
    <li><a href="BookSearch.jsp">Book Search</a></li>
    <li><a href="CheckIn.jsp">Check In Books</a></li>
    <li><a href="CreateBorrower.jsp">Manage Borrowers</a></li>
    <li><a href="Fines.jsp">Manage Fines</a></li>
  </ul>
   <img class="img-responsive" src="books1.jpg" alt="Books" width="100%" height="1"> 
</div>


</body>
</html>