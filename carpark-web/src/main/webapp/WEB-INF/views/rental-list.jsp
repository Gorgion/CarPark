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
        <c:if test="${not empty msg}">
            <div class="alert alert-success alert-dismissable">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
                    &times;
                </button>
                <fmt:message key="${msg}" />
            </div>
        </c:if>
        <c:if test="${not empty error}">
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
                <c:forEach items="${rentals}" var="rental">
                    <tr>
                        <td><c:out value="${rental.id}"/></td>
                        <td><fmt:message key="rental.states.${rental.rentalState}"/></td>
                        <td><fmt:formatDate value="${rental.fromDate}" type="DATE"/></td>
                        <td><fmt:formatDate value="${rental.toDate}" type="DATE"/></td>
                        <td>
                            <a href="#" class="btn btn-link" data-toggle="modal" data-target="#carDetails"
                               data-car-id="${rental.car.ID}" data-car-brand="<fmt:message key="car.brand.${rental.car.brand}"/>" data-car-type="<fmt:message key="car.type.${rental.car.type}" />"
                               data-car-engine="<fmt:message key="car.engine.${rental.car.engine}" />" 
                               data-car-licencePlate="${rental.car.licencePlate}"><fmt:message key="car.details"/></a>
                        </td>
                        <td>
                            <a href='<c:url value="/auth/user/${userId}/rental/${rental.id}/edit" />' class="btn btn-default"><fmt:message key="edit" /></a>
                        </td>
                        <td>
                            <form action="<c:url value='/auth/user/${userId}/rental/${rental.id}/delete' />" method="POST" class="form-inline">
                                <button type="submit" name="delete" class="btn btn-danger"><fmt:message key="delete" /></button>
                            </form>                            
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>      
        <custom:delete-dialog key="rental"></custom:delete-dialog>
        <custom:modal-dialog dialogId="carDetails" dialogTitleKey="carDetails.title">
            <div class="form-horizontal">
            <div class="form-group">
                <label class="control-label col-sm-4"><fmt:message key="car.brand"/>:</label>
                <div class="col-sm-8">
                    <p name="brand" class="form-control-static"></p>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-4"><fmt:message key="car.type"/>:</label>
                <div class="col-sm-8">
                    <p name="type" class="form-control-static"></p>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-4"><fmt:message key="car.engine"/>:</label>
                <div class="col-sm-8">
                    <p name="engine" class="form-control-static"></p>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-4"><fmt:message key="car.licencePlate"/>:</label>
                <div class="col-sm-8">                    
                    <p name="licencePlate" class="form-control-static"></p>
                </div>
            </div>
            </div>
        </custom:modal-dialog>        
    </jsp:attribute>        
</custom:layout>
