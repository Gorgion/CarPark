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
            <hr class="divider" />

            <c:if test="${not empty errMsg}" >
                <div class="alert alert-danger alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
                        &times;
                    </button>
                    <fmt:message key="${errMsg}" />
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
                        <th><fmt:message key="car.id" /></th>
                        <th><fmt:message key="car.brand" /></th>
                        <th><fmt:message key="car.type" /></th>
                        <th><fmt:message key="car.engine" /></th>
                        <th><fmt:message key="car.licencePlate" /></th>
                        <th><fmt:message key="car.VIN" /></th>
                        <th><fmt:message key="car.rented" /></th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${cars}" var="car">
                        <tr id="${car.id}">
                            <td>${car.id}</td>
                            <td><fmt:message key="car.brand.${car.brand}" /></td>
                            <td><fmt:message key="car.type.${car.type}" /></td>
                            <td><fmt:message key="car.engine.${car.engine}" /></td>
                            <td>${car.licencePlate}</td>
                            <td>${car.VIN}</td>
                            <td><fmt:message key="${car.rented}" /></td>
                            <td>
                                <a href="<c:url value="/auth/car/${car.id}/edit" />" class="btn btn-info"><span class="glyphicon glyphicon-edit" /></a>
                                <form action="<c:url value="/auth/car/${car.id}/delete" />" method="POST" class="form-inline" style="display: inline-block;">
                                    <button type="submit" name="delete" class="btn btn-danger"><span class="glyphicon glyphicon-remove" /></button>
                                </form>                            
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>  
        </div>
        <custom:delete-dialog key="car"></custom:delete-dialog>
    </jsp:attribute>
</custom:layout>

