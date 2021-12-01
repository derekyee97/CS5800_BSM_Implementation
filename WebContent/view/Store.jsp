<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>BSM_Store_Menu</title>
	</head>

	<style>
		body {
			background-color: lightblue;
		}

		body {
			margin: 0;
			font-family: Arial, Helvetica, sans-serif;
		}
		
		.BSM-store-title {
			text-align: center;
			padding-top: 20px;
			font-size: 30px;
			font-weight: bold;
		}
		
		.buttons {
			text-align: left;
			padding-top: 50px;
		}
		
		.sequential_buttons {
			text-align: left;
			padding-top: 10px;
		}
		
		.button-checkout {
			text-align: right;
			padding-top: 250px;
		}
		
		.buttons #shoe-button {
			height: 50px;
			width: 75px;
			border-radius: 15px;
			border: 2px solid #000000;
		}
		
		.sequential_buttons #tops-button {
			height: 50px;
			width: 75px;
			border-radius: 15px;
			border: 2px solid #000000;
		}
		
		.sequential_buttons #bottoms-button {
			height: 50px;
			width: 75px;
			border-radius: 15px;
			border: 2px solid #000000;
		}
		
		.sequential_buttons #stationary-button {
			height: 50px;
			width: 75px;
			border-radius: 15px;
			border: 2px solid #000000;
		}
		
		.sequential_buttons #books-button {
			height: 50px;
			width: 75px;
			border-radius: 15px;
			border: 2px solid #000000;
		}
		
		.button-checkout #checkout-button {
			height: 50px;
			width: 75px;
			border-radius: 15px;
			border: 2px solid #000000;
		}
		
	</style>
	
	<div class = "BSM-store-title">
		<p>STORE</p>
	</div>

	<body>
		<div>
			<div class = "buttons">
				<a href="Shoes.jsp">
					<button name="shoe-button" type="submit">Shoes</button>
				</a>
				<div class = "sequential_buttons">
					<a href="Tops.jsp">
						<button name="tops-button" type="submit">Tops</button>
					</a>
				</div>
				<div class = "sequential_buttons">
					<a href="Bottoms.jsp">
						<button name="bottoms-button" type="submit">Bottoms</button>
					</a>
				</div>
				<div class = "sequential_buttons">
					<a href="Stationary.jsp">
						<button name="stationary-button" type="submit">Stationary</button>
					</a>
				</div>
				<div class = "sequential_buttons">
					<a href="Books.jsp">
						<button name="books-button" type="submit">Books</button>
					</a>
				</div>
			</div>
			
			<div class = "button-checkout">
				<a>
					<button name="checkout-button" type="submit">Checkout</button>
				</a>
			</div>
		</div>
	</body>
</html>