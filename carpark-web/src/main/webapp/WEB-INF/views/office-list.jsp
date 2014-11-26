<%-- 
    Document   : office-list
    Created on : 24.11.2014, 12:57:54
    Author     : Karolina Burska
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" session="false"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:message var="title" key="office.list.title"/>
<custom:layout title="${title}">    
    <jsp:attribute name="content">
        <div class="row">
            <a href="<c:url value="/auth/office/add" />" class="btn btn-success"><fmt:message key="office.add"/></a>
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
                    <th><fmt:message key="office.id" /></th>
                    <th><fmt:message key="office.address" /></th>
                    <th><fmt:message key="office.manager" /></th>
                    <th><fmt:message key="office.employees" /></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${offices}" var="office">
                    <tr>
                        <td>${office.ID}</td>
                        <td>${office.address}</td>
                        <td>${office.manager}</td>
                        <td>${office.employees}</td>
                        <td>
                            <a href='<c:url value="/auth/office/${office.ID}/edit" />' class="btn btn-success"><fmt:message key="edit" /></a>
                        </td>
                        <td>
                            <form action="<c:url value="/auth/office/${office.ID}/delete" />" method="POST" class="form-inline">
                                <button type="submit" name="delete" class="btn btn-danger"><fmt:message key="delete" /></button>
                            </form>                            
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        </div>
    </jsp:attribute>        
</custom:layout>