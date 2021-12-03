<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>BSM_Homepage</title>
	</head>
	
	<style>
		body {
			background-color: lightblue;
		}
		body {
			margin: 0;
			font-family: Arial, Helvetica, sans-serif;
		}
		
		.BSM-title {
			text-align: center;
			padding-top: 20px;
			font-size: 50px;
			font-weight: bold;
		}
		
		.store-button {
			text-align: center;
			padding-top: 50px;
		}
		
		.store-button #store-button {
			height: 50px;
			width: 75px;
			border-radius: 15px;
			border: 2px solid #000000;
		}
		
	</style>
	
	<div class = "BSM-title">
		<p>BSM Homepage</p>
	</div>

	<body>
		<div class = "store-button">
			<a href="Store.jsp">
				<button name="store-button" type="submit">Store</button>
			</a>
		</div>
	</body>
	
</html>