<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<body>
	
	<div class="container">
		
		<div class="content">
			<p class="chatName">WATERFALL'S CHAT 1.0 ALPHA</p>
			
			<iframe src="ChatController?command=SHOWCHATINFRAME" style="height:500px;width:385px;" >
			
			</iframe>
			
			<div class="input">
				<form action="ChatController" method="POST">
					<input type="hidden" name="command" value="SENDMSG">
					<input type="hidden" name="author" value="${nickname}">
					
					<input type="text" name="message" autofocus required>
					<input type="submit" value="Send">
				</form>
			<p>${nickname}</p>
			</div>
			<div id="json">
				<form action="JsonController" method="POST">
					<input type="submit" value="Messages to JSON">
				</form>
			</div>	
		</div>	
	</div>
</body>

</html>
