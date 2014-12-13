<%-- 
    Document   : user-list
    Created on : 22.11.2014, 12:37:55
    Author     : Tomas Vasicek
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<fmt:message var="title" key="user.list.title"/>
<custom:layout title="${title}">    
    <jsp:attribute name="content">        
        <div class="row">
            <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_BUILT_IN_ADMIN')">
                <a href="<c:url value="/auth/user/add" />" class="btn btn-success"><fmt:message key="user.add"/></a>
                <hr class="divider" />
            </sec:authorize>
            <c:if test="${not empty error}" >
                <div class="alert alert-danger alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
                        &times;
                    </button>
                    <fmt:message key="${error}" />
                </div>
            </c:if>


            <c:if test="${not empty msg}" >
                <div class="alert alert-success alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
                        &times;
                    </button>
                    <fmt:message key="${msg}" />
                </div>
            </c:if>

            <table class="table table-hover table-responsive">
                <thead>
                    <tr>
                        <th><fmt:message key="user.id" /></th>
                        <th><fmt:message key="rentals" /></th>
                        <th><fmt:message key="user.firstName" /></th>
                        <th><fmt:message key="user.lastName" /></th>
                        <th><fmt:message key="user.birthNumber" /></th>
                        <th><fmt:message key="user.address" /></th>
                            <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_BUILT_IN_ADMIN')">
                            <th></th>
                            </sec:authorize>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${users}" var="user">
                        <tr>                  
                            <td>${user.id}</td>
                            <td>
                                <a href="<c:url value="/auth/user/${user.id}/rental" />" class="btn-link"><fmt:message key="show"/></a>
                            </td>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>${user.birthNumber}</td>
                            <td>${user.address}</td>
                            <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_BUILT_IN_ADMIN')">
                                <td>
                                    <a href="<c:url value="/auth/user/${user.id}/edit" />" class="btn btn-info"><span class="glyphicon glyphicon-edit" /></a>
                                    <form action="<c:url value="/auth/user/${user.id}/delete" />" method="POST" class="form-inline" style="display: inline-block;">
                                        <button type="submit" name="delete" class="btn btn-danger"><span class="glyphicon glyphicon-remove" /></button>
                                    </form>                            
                                </td>
                            </sec:authorize>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>  
        </div>
        <custom:delete-dialog key="user"></custom:delete-dialog>
    </jsp:attribute>        
</custom:layout>
