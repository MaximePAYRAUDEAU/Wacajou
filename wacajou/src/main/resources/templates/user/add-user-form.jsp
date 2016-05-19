<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form:form method="POST" commandName="user"
		action="${pageContext.request.contextPath}/user/add/process.html" >
		<table>
			<tbody>
				<tr>
					<td>Login:</td>
					<td><form:input path="login"></form:input></td>
				</tr>
				<tr>
					<td>Promo:</td>
					<td><form:input path="promo"></form:input></td>
				</tr>
				<tr>
					<td>Statur:</td>
					<td><form:input path="statut"></form:input></td>
				</tr>
				<tr>
					<td><input type="submit" value="Add"></td>
					<td></td>
				</tr>
			</tbody>
		</table>
	</form:form>

</body>
</html>