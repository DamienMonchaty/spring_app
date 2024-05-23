<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Person Form</title>
<style>
.error {
	color: red;
}
</style>
</head>
<body>
	<form:form modelAttribute="person" action="/person" method="post">
		<form:label path="firstName">FirstName :</form:label>
		<br />
		<form:input path="firstName" />
		<br />
		<form:errors path="firstName" cssClass="error" />
		<br />
		<form:label path="lastName">LastName :</form:label>
		<br />
		<form:input path="lastName" />
		<br />
		<form:errors path="lastName" cssClass="error" />
		<br />
		<form:label path="age">Age :</form:label>
		<br />
		<form:input path="age" />
		<br />
		<form:errors path="age" cssClass="error" />
		<br />
		<input type="submit" value="Enregistrer">
	</form:form>
</body>
</html>