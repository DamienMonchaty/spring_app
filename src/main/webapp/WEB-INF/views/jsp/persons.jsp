<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<title>Persons</title>
</head>
<body>
	<c:if test="${ not empty person }">
		<h1>Salut ${ person.firstName }</h1>
		<a href="/clearSession">Clear Session</a>
	</c:if>
	<div class="list-group">
		<c:forEach items="${ persons }" var="p">
			<div class="list-group-item">
				<c:out value="${ p.firstName }" />
				<c:out value="${ p.lastName }" />
				<c:out value="${ p.age }" />
			</div>
		</c:forEach>
	</div>
</body>
</html>