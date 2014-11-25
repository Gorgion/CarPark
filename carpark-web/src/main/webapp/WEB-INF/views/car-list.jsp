<%-- 
    Document   : car-list
    Created on : 22.11.2014, 16:30:58
    Author     : Jiri Dockal
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" session="false"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:message var="title" key="car.list.title"/>

<custom:layout title="${title}">
    <jsp:attribute name="content">
        <div class="row">
            <a href="<c:url value="/auth/car/add" />" class="btn btn-success"><fmt:message key="car.add"/></a>
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
        
        <table class="table table-hover table-responsive">
            <thead>
                <tr>
                    <th><fmt:message key="car.id" /></th>
                    <th><fmt:message key="car.brand" /></th>
                    <th><fmt:message key="car.type" /></th>
                    <th><fmt:message key="car.engine" /></th>
                    <th><fmt:message key="car.licencePlate" /></th>
                    <th><fmt:message key="car.VIN" /></th>
                    <th><fmt:message key="car.rented" /></th>
                </tr>
            </thead>
            <tbody>
               <c:forEach items="${cars}" var="car">
                    <tr>
                        <td>${car.ID}</td>
                        <td><fmt:message key="${car.brand}" /></td>
                        <td>${car.type}</td>
                        <td>${car.engine}</td>
                        <td>${car.licencePlate}</td>
                        <td>${car.VIN}</td>
                        <td>${car.rented}</td>
                        <td><a href="<c:url value="/auth/car/${car.ID}/edit" />" class="btn btn-default"><fmt:message key="edit" /></a></td>
                        <td>
                            <form action="<c:url value="/auth/car/${car.ID}/delete" />" method="POST" class="form-inline">
                                <button type="submit" name="delete" class="btn btn-danger"><fmt:message key="delete" /></button>
                            </form>                            
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>      
        <custom:delete-dialog key="car"></custom:delete-dialog>
    </jsp:attribute>
</custom:layout>
 
