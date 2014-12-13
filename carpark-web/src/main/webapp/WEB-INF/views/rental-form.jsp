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
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<fmt:message var="title" key="rental.form.title"/>




<custom:layout title="${title}">
    <jsp:attribute name="content">
        <sec:authorize access="!hasRole('ROLE_BUILT_IN_ADMIN')">
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

        <c:url var="addUrl" value="/auth/user/${userId}/rental/add" />
        <form:form action="${addUrl}" method="POST" role="form" modelAttribute="rentalForm" class="form-horizontal">
            <c:set var="fromErrors"><form:errors path="from"/></c:set>
            <c:set var="toErrors"><form:errors path="to"/></c:set>

                <div class="${not empty fromErrors ? 'has-error' : ''} form-group"> 
                <form:label path="from" cssClass="col-sm-2 control-label"><fmt:message key="rental.fromDate" />:</form:label>
                    <div class="col-sm-5">                    
                        <form:input type="date" path="from" cssClass="form-control" autofocus="true"/>
                </div>
                <div class="col-sm-5 help-block">${fromErrors}</div>                
            </div>
            <div class="${not empty toErrors ? 'has-error' : ''} form-group"> 
                <form:label path="to" cssClass="col-sm-2 control-label"><fmt:message key="rental.toDate" />:</form:label>
                    <div class="col-sm-5">
                        <form:input type="date" path="to" cssClass="form-control"/>                    
                </div>
                <div class="col-sm-5 help-block">${toErrors}</div>                
                </div>
            <%--<c:if test="${phase == 0}">--%>  
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-success"><fmt:message key="car.findFree" /></button>
                    <a href="<c:url value="/auth/user/${userId}/rental"/>" class="btn btn-default"><fmt:message key="btn.cancel" /></a>
                </div>
            </div>
            <%--</c:if>--%>
            <%--<c:if test="${phase != 0}">--%>                        
            <!--<button type="submit" class="btn btn-success" disabled="disabled">
            <%--<fmt:message key="car.findFree" />--%>
            </button>-->
            <%--</c:if>--%>
        </form:form>

        <c:if test="${phase == 1}">
            <h2><fmt:message key="rental.chooseCar" /></h2>
            <c:forEach items="${cars}" var="car" varStatus="status">
                <div class="row">
                    <div class="col-sm-4">
                        <fmt:message key="car.brand.${car.brand}"/>
                    </div>
                    <div class="col-sm-4">
                        <fmt:message key="car.type.${car.type}"/>
                    </div>
                    <div class="col-sm-4">

                        <c:url var="add1Url" value="/auth/user/${userId}/rental/add/1" />
                        <form:form action="${add1Url}" method="POST" modelAttribute="rentalForm" class="form-inline">                                           
                            <form:hidden path="from"  />
                            <form:hidden path="to" />

                            <form:hidden path="carId" value="${car.id}"/>
                            <button type="submit" class="btn btn-success"><fmt:message key="btn.rentCar" /></button>
                        </form:form>
                    </div>
                </div>
                <c:if test="${!status.last}">
                    <hr class="divider" />
                </c:if>
            </c:forEach>
        </c:if>
        </sec:authorize>                    
    </jsp:attribute>
</custom:layout>
