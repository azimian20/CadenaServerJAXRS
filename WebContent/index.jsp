<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Spring Security 5</title>
</head>
<body>
<h1>Cadena Server</h1>
<h2>${message}</h2>

<form:form action="logoutAction" method="POST">
	<input value="Logout" type="submit">
</form:form>
</body>
</html>