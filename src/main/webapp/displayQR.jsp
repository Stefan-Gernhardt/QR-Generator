<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>QR Code Generator</title>
</head>
<style>
	body {color: White;
	      background-color: DodgerBlue;}
</style>
<body>      
	<center>
	<h1>QR Code Generator</h1>
	<p>Generated image:</p>
	
	<!--- <img src="/imageqrcode/${qrtext}" alt="place holder for image""> --->
	<p>${svg}</p>
	
	<p>${qrtext}</p>
	</center>
	<a href="/downloadsvg/${qrtext}" download>download (svg scalable graphic)</a><br>
	<script>
	   function submitForm(x){
	      if(x.id=='backButton'){
	         document.getElementById('hid1').value='backButton';
	      }
	      document.forms[0].submit();
	   }
	</script>	
	<form action=/>
	    <th>
	    	<br>
	    	<input type="button" id=backButton value="back" name="backButton" onClick='submitForm(this)'/></th> 
		<input type='hidden' id='hid1'  name='hid1'>
	</form>
	
</body>
</html>