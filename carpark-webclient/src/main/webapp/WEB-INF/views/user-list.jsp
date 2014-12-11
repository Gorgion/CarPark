<%-- 
    Document   : user-list
    Created on : 22.11.2014, 12:37:55
    Author     : Tomas Vasicek
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<title>Users</title>
<custom:layout title="${title}">    
    <jsp:attribute name="content">
        <div class="row">
            <a href="<c:url value="/auth/user/add" />" class="btn btn-success">Add user</a>
            <hr class="divider" />

            <c:if test="${not empty error}" >
                <div class="alert alert-danger alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
                        &times;
                    </button>
                    ${error}
                </div>
            </c:if>


            <c:if test="${not empty msg}" >
                <div class="alert alert-success alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
                        &times;
                    </button>
                    ${msg}
                </div>
            </c:if>

            <table class="table table-hover table-responsive">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Rentals</th>
                        <th>First name</th>
                        <th>Last name</th>
                        <th>Birth number</th>
                        <th>Address</th>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${users}" var="user">
                        <tr>                  
                            <td>${user.id}</td>
                            <td>
                                <a href="<c:url value="/auth/user/${user.id}/rental" />" class="btn-link">Show</a>
                            </td>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>${user.birthNumber}</td>
                            <td>${user.address}</td>
                            <td>
                                <a href="<c:url value="/auth/user/${user.id}/edit" />" class="btn btn-info"><span class="glyphicon glyphicon-edit" /></a>
                                <form action="<c:url value="/auth/user/${user.id}/delete" />" method="POST" class="form-inline" style="display: inline-block;">
                                    <button type="submit" name="delete" class="btn btn-danger"><span class="glyphicon glyphicon-remove" /></button>
                                </form>                            
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>  
        </div>
        <custom:delete-dialog key="user"></custom:delete-dialog>
    </jsp:attribute>        
</custom:layout>
