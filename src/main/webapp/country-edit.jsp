<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<c:set var="pageTitle" value="Edit country" scope="application" />
<t:wrapper>
<c:choose>
		<c:when test="${empty dto.id}">
			<h1>Create country</h1>
		</c:when>
		<c:otherwise>
			<h1>Edit country #${dto.id}</h1>
		</c:otherwise>
	</c:choose>
<div class="section no-pad-bot" id="index-banner">
	<div class="container">
		<div class="row">
			<form class="col s12" method="post" action="/country">
				<div class="row">
			<input type="hidden" name="id" value="${dto.id}" />
			<div class="row">
				<div class="input-field col s12">  
					<input type="text" name="name" value="${dto.name}" ${empty dto.id ? '' : 'enabled'} > <label for="name">country</label>
				</div>
			</div>				
			</div>
		</div>
		<div class="row">
			<div class="col s12 input-field center-align">
				<a class="btn waves-effect waves-light red" href="/country"><i class="material-icons left">list</i>back</a>&nbsp;
				<button class="btn waves-effect waves-light" type="submit">
					<i class="material-icons left">save</i>save
				</button>
			</div>
		</div>
			</form>
		</div>
</div>

<!-- Compiled and minified JavaScript -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
</t:wrapper>