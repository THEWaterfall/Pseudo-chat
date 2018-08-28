<html>
<head>
	<link type="text/css" rel="stylesheet" href="css/style.css">
</head>
<body>
	<div class="container">
		<div class="content">
			<form action="LoginController" method="POST">
				<input type="hidden" name="command" value="LOGGEDIN">		
			
				<label>Enter your nickname: </label>
				<input type="text" name="nickname" required>
				
				<label>Enter your password: </label>
				<input type="password" name="password" required>
				
				<input type="submit" value="Login">
			</form>
		</div>
	</div>
</body>
</html>