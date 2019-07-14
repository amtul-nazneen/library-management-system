<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Registration Form</title>
<link href="additional.css" rel="stylesheet" type="text/css">
</head>
<body>

<div class="container">
  <h2>Manage Borrowers</h2>
  <form action="CreateBorrower" method="post">
    <!-- div class="form-group">
	<label for="borrowerName">Name</label>
      <input type="text" class="form-control" id="borrowerName" placeholder="Enter First and Last Name" name="borrowerName" required>
    </div-->
     <div class="form-group">
      <label for="fname">First Name</label>
      <input type="text" class="form-control" id="fname" placeholder="Enter First Name" name="fname" required>
    </div>
     <div class="form-group">
      <label for="lname">Last Name</label>
      <input type="text" class="form-control" id="lname" placeholder="Enter Last Name" name="lname" required>
    </div>
     <div class="form-group">
      <label for="email">Email</label>
      <input type="email" class="form-control" id="email" placeholder="Enter email" name="email">
    </div>
     <div class="form-group">
      <label for="ssn">SSN</label>
      <input type="text" class="form-control" id="ssn" placeholder="Enter SSN" name="ssn" required>
    </div>
     <div class="form-group">
      <label for="address">Address</label>
      <input type="text" class="form-control" id="address" placeholder="Enter Address" name="address" required>
    </div>
     <div class="form-group">
      <label for="city">City</label>
      <input type="text" class="form-control" id="city" placeholder="Enter City" name="city" required>
    </div>
     <div class="form-group">
      <label for="state">State</label>
      <input type="text" class="form-control" id="state" placeholder="Enter State" name="state" required>
    </div>
     <div class="form-group">
      <label for="phone">Phone</label>
      <input type=text class="form-control" id="phone" placeholder="Enter Phone No." name="phone">
    </div>
    <button type="submit" class="btn btn-primary pull-right">Submit</button>
    <a href="Home.jsp" class="btn btn-info" role="button">Back to Home</a>
</form>
    </div>

</body>
</html>