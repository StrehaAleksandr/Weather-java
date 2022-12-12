<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>
<c:set var="pageTitle" value="Weather list" scope="application" />
<c:set var="pageUrl" value="/weather" scope="page" />
<t:wrapper>
    <main>
        <section class="weather-calendar">
            <h1 class="weather-calendar__title">All list</h1>
    
            <table class="weather__table">
                <thead class="weather-table__head">
                    <tr class="table-head__item">
                         <th class="table-head__element"><mytaglib:sort-link pageUrl="${pageUrl}" column="id">ID</mytaglib:sort-link></th>
                         <th class="table-head__element"><mytaglib:sort-link pageUrl="${pageUrl}" column="date">date</mytaglib:sort-link></th>
                         <th class="table-head__element">City</th>
                         <th class="table-head__element"><mytaglib:sort-link pageUrl="${pageUrl}" column="tempreture">Tempreture</mytaglib:sort-link></th>
                         <th class="table-head__element"><mytaglib:sort-link pageUrl="${pageUrl}" column="weather">Weather</mytaglib:sort-link></th>
                         <th class="table-head__element">Creator</th>
                         <th>actions</th>
                    </tr>
                </thead>
    
                <tbody class="weather-table__body">
                <c:forEach var="entity" items="${list}" varStatus="loopCounter">
                    <tr class="table-body__item">
                        <td class="table-body__element"><c:out value="${entity.id}" /></td>
                        <td class="table-body__element"><fmt:formatDate pattern="yyyy-MM-dd" value="${entity.date}" /></td>
                        <td class="table-body__element"><c:out value="${entity.cityName}" /></td>
                        <td class="table-body__element"><c:out value="${entity.tempreture}" /></td>
                        <td class="table-body__element"><c:out value="${entity.weather}" /></td>
                        <td class="table-body__element"><c:out value="${entity.creatorLogin}" /></td>
                        <td><a class="btn-small btn-floating waves-effect waves-light blue" title="редактировать" href="/weather?view=edit&id=${entity.id}"><i
							class="material-icons">edit</i></a><a class="btn-small btn-floating waves-effect waves-light red" title="удалить"
						onclick="sendHTTPDelete('/weather?id=${entity.id}')"><i class="material-icons">delete</i></a></td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="weather-calendar__add-container">
                <a href="/weather?view=edit" class="weather-calendar__add">
                    <span class="visually-hidden">Add</span>
                    +
                </a>
            </div>
        </section>
    </main>
    <t:paging />
</t:wrapper>