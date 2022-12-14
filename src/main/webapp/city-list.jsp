<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>
<c:set var="pageTitle" value="City list" scope="application" />
<c:set var="pageUrl" value="/city" scope="page" />
<t:wrapper>
    <main>
        <section class="weather-calendar">
            <h1 class="weather-calendar__title">All list</h1>
    
            <table class="weather__table">
                <thead class="weather-table__head">
                    <tr class="table-head__item">
                         <th class="table-head__element"><mytaglib:sort-link pageUrl="${pageUrl}" column="id">ID</mytaglib:sort-link></th>
                         <th class="table-head__element">City</th>
                         <th class="table-head__element">Country</th>
                         <th>actions</th>
                    </tr>
                </thead>
    
                <tbody class="weather-table__body">
                <c:forEach var="entity" items="${list}" varStatus="loopCounter">
                    <tr class="table-body__item">
                        <td class="table-body__element"><c:out value="${entity.id}" /></td>
                        <td class="table-body__element"><c:out value="${entity.name}" /></td>
                        <td class="table-body__element"><c:out value="${entity.countryName}" /></td>
                        <td><a class="btn-small btn-floating waves-effect waves-light blue" title="редактировать" href="/city?view=edit&id=${entity.id}"><i
							class="material-icons">edit</i></a><a class="btn-small btn-floating waves-effect waves-light red" title="удалить"
						onclick="sendHTTPDelete('/city?id=${entity.id}')"><i class="material-icons">delete</i></a></td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="weather-calendar__add-container">
                <a href="/city?view=edit" class="weather-calendar__add">
                    <span class="visually-hidden">Add</span>
                    +
                </a>
            </div>
        </section>
    </main>
    <t:paging />
</t:wrapper>