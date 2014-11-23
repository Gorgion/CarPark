<%-- 
    Document   : rental-form
    Created on : 17.11.2014, 20:24:05
    Author     : Tomas Svoboda
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" session="false"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message var="title" key="rental.form.title"/>




<custom:layout title="${title}">
    <jsp:attribute name="content">
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

        <form:form action="/auth/user/${userId}/rental/add" method="POST" modelAttribute="rentalDate" class="form-horizontal">
            <div class="form-group"> 
                <form:label path="from" cssClass="col-sm-2"><fmt:message key="rental.fromDate" /></form:label>
                    <div class="col-sm-5">
                    <c:if test="${phase == 0}">
                        <form:input type="type" path="from" cssClass="form-control"/>
                    </c:if>
                    <c:if test="${phase != 0}">
                        <form:input type="type" path="from" cssClass="form-control" readonly=""/>
                    </c:if>
                </div>
                <div class="col-sm-5"><form:errors path="from" cssClass="help-block"></form:errors></div>                
                </div>
                <div class="form-group"> 
                <form:label path="to" cssClass="col-sm-2"><fmt:message key="rental.toDate" /></form:label>
                    <div class="col-sm-5">
                    <c:if test="${phase == 0}">
                        <form:input type="type" path="to" cssClass="form-control"/>
                    </c:if>
                    <c:if test="${phase != 0}">
                        <form:input type="type" path="to" cssClass="form-control" readonly=""/>
                    </c:if>
                </div>
                <div class="col-sm-5"><form:errors path="to" cssClass="help-block"></form:errors></div>                
                </div>
            <c:if test="${phase == 0}">                        
                <button type="submit" class="btn btn-success"><fmt:message key="car.findFree" /></button>
            </c:if>
            <c:if test="${phase != 0}">                        
                <button type="submit" class="btn btn-success" disabled="disabled"><fmt:message key="car.findFree" /></button>
            </c:if>
        </form:form>

        <c:if test="${phase == 1}">
            <h2><fmt:message key="rental.chooseCar" /></h2>
            <c:forEach items="${cars}" var="car" varStatus="status">
                <div class="row">
                    <div class="col-sm-3">
                        <c:out value="${car.brand}" escapeXml="true"/>
                    </div>
                    <div class="col-sm-3">
                        <c:out value="${car.model}" escapeXml="true"/>
                    </div>
                    <div class="col-sm-3">
                        <c:out value="${car.type}" escapeXml="true"/>
                    </div>
                    <div class="col-sm-3">

                        <form:form action="/auth/user/${userId}/rental/add/1" method="POST" modelAttribute="rentalForm" class="form-inline">               
                            <form:hidden path="rentalDate" />
                            <form:hidden path="car" />
                            <button type="submit" class="btn btn-success"><fmt:message key="btn.rentCar" /></button>
                        </form:form>
                    </div>
                </div>
                <c:if test="${!status.last}">
                    <hr class="divider" />
                </c:if>
            </c:forEach>
        </c:if>
    </jsp:attribute>
</custom:layout>
