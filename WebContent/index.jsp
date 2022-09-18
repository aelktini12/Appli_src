<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Recrutement</title>
<style>
	p.w {
		padding-left:20px;
		padding-top: 40px;
	}
</style>
</head>

<body>
<%@ include file="WEB-INF/Menu.jsp" %>
	<div style="text-align:center">
	<h3 style="padding-top: 20px;" >
		<%
			String variable = (String) request.getAttribute("variable");
			out.println(variable);
		%>
	</h3>
	</div>
	<p class="w">Welcome on this page!</p>
</body>
</html>