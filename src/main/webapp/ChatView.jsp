<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<link type="text/css" rel="stylesheet" href="css/style.css">
	
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
</head>

<body>
	
	<div class="container">
		<div class="content">
			<div class="chat">
				<textarea id="area" rows="30" cols="45" disabled>
					<c:forEach var="msg" items="${messages}">
						${msg.author}: ${msg.message}
					</c:forEach>
				</textarea>
				
			</div>
		
		<div id="autorefresh">
			<label>Turn off autorefresh</label><input type="checkbox" id='timercheck' />
		</div>	
		</div>
			
	</div>
</body>

	<script>
	$(function () {

	    $('#timercheck').on('change', function () {
	        if ($(':checked').length > 0) {
	            clearInterval(timer);
	        } else {
	        	 location.reload();
	        }
	    });


	    var timer = setInterval(function () {
	        location.reload();
	    }, 1500);

	});
	</script>

	<script>
		document.getElementById("area").scrollTop = document.getElementById("area").scrollHeight
	</script>
</html>
