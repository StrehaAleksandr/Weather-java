<%@tag description="Page template" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>

<head>
<title><c:out value="${pageTitle}" /></title>

<!-- Compiled and minified CSS -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link rel="stylesheet" href="style.css">

<script src="js/helpers.js"></script>
</head>

<body>
<header class="main-header">
	<nav class="main-header__navigation main-navigation">
	    <a class="main-navigation__logo" href="index.jsp">Weather Calendar</a>
	    <ul class="main-navigation__list">
	        <li class="main-navigation__item">
	            <a href="/weather">List</a>
	        </li>
	        <li class="main-navigation__item">
	            <a href="/weather?view=edit">Edit</a>
	        </li>
	        <li class="active main-navigation__item"><a onclick="sendHTTPDelete('/login')">Logout</a></li>
	    </ul>
	</nav>
</header>

<div class="section no-pad-bot" id="index-banner">
	<div class="container">
		<jsp:doBody />
		<!-- Page body will be here -->
	</div>
</div>


<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
</body>
</html>