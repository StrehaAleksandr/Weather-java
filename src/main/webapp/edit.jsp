<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<c:set var="pageTitle" value="Edit calendar" scope="application" />
<t:wrapper>
<c:choose>
		<c:when test="${empty dto.id}">
			<h1>Create weather</h1>
		</c:when>
		<c:otherwise>
			<h1>Edit weather #${dto.id}</h1>
		</c:otherwise>
	</c:choose>
<div class="section no-pad-bot" id="index-banner">
	<div class="container">
		<div class="row">
			<form class="col s12" method="post" action="/weather">
				<div class="row">
			<input type="hidden" name="id" value="${dto.id}" />
			<div class="row">
				<div class="input-field col s12">  
					<input type="text" name="tempreture" value="${dto.tempreture}" ${empty dto.id ? '' : 'enabled'} > <label for="tempreture">tempreture</label>
				</div>
			</div>
			<div class="row">
				<div class="input-field col s12">  
					<input type="text" name="weather" value="${dto.weather}" ${empty dto.id ? '' : 'enabled'} > <label for="weather">weather</label>
				</div>
			</div>
			<div class="row">
				<div class="input-field col s6">
					<input type="text" name="creatorId" value="${dto.creatorId}"> <label for="creatorId">Creator ID</label>
				</div>
				<div class="col s6">
					<label for="cityId">City ID</label> 
					<select name="cityId" class="browser-default" required>
						<option value="">--select city--</option>
						<c:forEach items="${allCityes}" var="city">
							<option value="${city.id}" <c:if test="${city.id eq dto.cityId}">selected="selected"</c:if>>${city.name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col s6">
					<label for="countryId">Country ID</label> 
					<select name="countryId" class="browser-default" required>
						<option value="">--select country--</option>
						<c:forEach items="${allCountryes}" var="country">
							<option value="${country.id}" <c:if test="${country.id eq dto.cityId}">selected="selected"</c:if>>${country.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col s12 input-field center-align">
				<a class="btn waves-effect waves-light red" href="/weather"><i class="material-icons left">list</i>back</a>&nbsp;
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