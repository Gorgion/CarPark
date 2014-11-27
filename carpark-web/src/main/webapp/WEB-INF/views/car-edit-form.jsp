<%-- 
    Document   : car-edit-form
    Created on : 24.11.2014, 11:44:59
    Author     : Jiří Dočkal
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" session="false"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message var="title" key="car.editform.title"/>

<custom:layout title="${title}">
    <jsp:attribute name="content">
        <c:if test="${msg}">
            <div id="alert" class="alert alert-success alert-dismissable">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
                    &times;
                </button>
                <fmt:message key="${msg}" />
            </div>
        </c:if>
        <c:if test="${error}">
            <div id="alert" class="alert alert-danger alert-dismissable">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
                    &times;
                </button>
                <fmt:message key="${error}"/>
            </div>
        </c:if>
        
        <c:url var="editUrl" value="/auth/car/${id}/edit" />
        <form:form action="${editUrl}" method="POST" modelAttribute="carForm" class="form-horizontal">
            <div class="form-group">
                <form:label class="control-label col-md-2" path="brand"><fmt:message key="car.brand"/>:</form:label>
                <div class="col-md-6">
                    <form:select path="brand" class="form-control" id="brand" >
                        <form:options items="${brands}"></form:options>
                    </form:select>
                </div>
            </div>
            <div class="form-group">
                <form:label class="control-label col-md-2" path="type"><fmt:message key="car.type"/>:</form:label>
                <div class="col-md-6">
                    <form:select path="type" class="form-control" id="type" >
                        <form:options items="${types}"></form:options>
                    </form:select>
                </div>
            </div>
            <div class="form-group">
                <form:label class="control-label col-md-2" path="engine"><fmt:message key="car.engine"/>:</form:label>
                <div class="col-md-6">
                    <form:select path="engine" class="form-control" id="engine" >
                        <form:options items="${engines}"></form:options>
                    </form:select>
                </div>
            </div>
            <div class="form-group">
                <form:label class="control-label col-md-2" path="VIN"><fmt:message key="car.VIN"/>:</form:label>
                <div class="col-md-6">
                    <form:input path="VIN" class="form-control" value="${VIN}" id="VIN" />
                </div>
            </div>
            <div class="form-group">
                <form:label class="control-label col-md-2" path="licencePlate"><fmt:message key="car.licencePlate"/>:</form:label>
                <div class="col-md-6">
                <form:input path="licencePlate" class="form-control" value="${licencePlate}" id="licencePlate" />
                </div>
            </div>
            <div class="pull-right col-md-6 ">    
            <button type="submit" class="btn btn-success btn-lg"><fmt:message key="edit" /></button>
            <button type="button" class="btn btn-warning btn-lg" onclick="history.back()"><fmt:message key="btn.cancel" /></button>
            </div>
        </form:form>
        </jsp:attribute>
</custom:layout>


