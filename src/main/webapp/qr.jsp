<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>QR Code Generator</title>
</head>
<style>
	body {color: White; background-color: DodgerBlue;}
</style>
<body>      
	<center>
	<h1>QR Code Generator</h1>
	<p>Insert the text for the QR-Code image generation:<p>
	<form action="displayqr">
	    <textarea name="qrText" cols="40" rows="5"></textarea><br>
		<input type="submit"><br>
	</form>
	</center>
</body>
</html>