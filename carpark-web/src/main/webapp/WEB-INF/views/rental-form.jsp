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
                        <form:input  path="from" cssClass="form-control"/>
                    </c:if>
                    <c:if test="${phase != 0}">
                        <form:input  path="from" cssClass="form-control" readonly=""/>
                    </c:if>
                </div>
                <div class="col-sm-5"><form:errors path="from" cssClass="help-block"></form:errors></div>                
            </div>
            <div class="form-group"> 
                <form:label path="to" cssClass="col-sm-2"><fmt:message key="rental.toDate" /></form:label>
                <div class="col-sm-5">
                    <c:if test="${phase == 0}">
                        <form:input  path="to" cssClass="form-control"/>
                    </c:if>
                    <c:if test="${phase != 0}">
                        <form:input  path="to" cssClass="form-control" readonly=""/>
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
        <form:form action="/auth/user/${userId}/rental/${rental.id}/${action}" method="POST" modelAttribute="rentalForm" class="form-horizontal">
            <!--<div class="form-group">-->                
                <form:hidden path="rentalDate" />
            <!--</div>-->
            
            <div class="form-group"> 
                <form:label path="car" cssClass="col-sm-2"><fmt:message key="rental.chooseCar" /></form:label>
                <div class="col-sm-5">
                    <form:select path="car">
                        <c:forEach items="${cars}" var="c">
                            <form:option value="${c}"><fmt:message key="rental.states.${state}"/></form:option>
                        </c:forEach>
                    </form:select>
                </div>
                <div class="col-sm-5"><form:errors path="rentalState" cssClass="help-block"></form:errors></div> 
            </div>
            
<!--            <div class="form-group"> 
                <form:label path="rentalState" cssClass="col-sm-2"><fmt:message key="rental.rentalState" /></form:label>
                <div class="col-sm-5">
                    <form:select path="rentalState">
                        <c:forEach items="${states}" var="state">
                            <form:option value="${state}"><fmt:message key="rental.states.${state}"/></form:option>
                        </c:forEach>
                    </form:select>
                </div>
                <div class="col-sm-5"><form:errors path="rentalState" cssClass="help-block"></form:errors></div>                
            </div>-->
        </form:form>
                </c:if>
    </jsp:attribute>
</custom:layout>
