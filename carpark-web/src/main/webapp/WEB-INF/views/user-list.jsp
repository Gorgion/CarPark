<%-- 
    Document   : user-list
    Created on : 22.11.2014, 12:37:55
    Author     : Tomas Vasicek
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:message var="title" key="user.list.title"/>
<custom:layout title="${title}">    
    <jsp:attribute name="content">
        <div class="row">
            <a href="<c:url value="/auth/user/add" />" class="btn btn-success"><fmt:message key="user.add"/></a>
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
                    <th><fmt:message key="user.id" /></th>
                    <th><fmt:message key="user.firstName" /></th>
                    <th><fmt:message key="user.lastName" /></th>
                    <th><fmt:message key="user.birthNumber" /></th>
                    <th><fmt:message key="user.address" /></th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>                  
                        <td>${user.id}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.birthNumber}</td>
                        <td>${user.address}</td>
                        <td><a href="<c:url value="/auth/user/${user.id}/edit" />" class="btn btn-default"><fmt:message key="edit" /></a></td>
                        <td>
                            <form action="<c:url value="/auth/user/${user.id}/delete" />" method="POST" class="form-inline">
                                <button type="submit" name="delete" class="btn btn-danger"><fmt:message key="delete" /></button>
                            </form>                            
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>      
        <custom:delete-dialog key="user"></custom:delete-dialog>
    </jsp:attribute>        
</custom:layout>
