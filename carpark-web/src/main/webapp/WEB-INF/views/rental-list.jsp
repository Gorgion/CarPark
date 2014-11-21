<%-- 
    Document   : rental-list
    Created on : 17.11.2014, 20:24:20
    Author     : Tomas Svoboda
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" session="false"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:message var="title" key="rental.list.title"/>
<custom:layout title="${title}">
    <jsp:attribute name="content">
        <div class="row">
            <a href="<c:url value="/auth/user/${userId}/rental/add" />" class="btn btn-success"><fmt:message key="rental.add"/></a>
        </div>
        <hr class="divider" />
        <c:if test="${msg}">
            <div class="alert alert-success alert-dismissable">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
                    &times;
                </button>
                <fmt:message key="${msg}" />
            </div>
        </c:if>
        <c:if test="${error}">
            <div class="alert alert-danger alert-dismissable">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
                    &times;
                </button>
                <fmt:message key="${error}"/>
            </div>
        </c:if>
        <table class="table table-hover">
            <thead>
                <tr>
                    <th><fmt:message key="rental.id" /></th>
                    <th><fmt:message key="rental.state" /></th>
                    <th><fmt:message key="rental.fromDate" /></th>
                    <th><fmt:message key="rental.toDate" /></th>
                    <th><fmt:message key="rental.car" /></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="rentals" var="rental">
                    <tr>
                        <td>${rental.id}</td>
                        <td>${rental.rentalState}</td>
                        <td>${rental.fromDate}</td>
                        <td>${rental.toDate}</td>
                        <td><a href="#" class="btn btn-link">${rental.car.id}</a></td>
                        <td><a href="#" class="btn btn-default"><fmt:message key="edit" /></a></td>
                        <td><a href="#" class="btn btn-danger"><fmt:message key="delete" /></a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </jsp:attribute>
</custom:layout>
