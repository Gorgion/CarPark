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
        
        <c:url var="editUrl" value="/auth/car/{id}/edit" />
        <form:form action="${editUrl}" method="POST" modelAttribute="carForm" class="form-horizontal">
            <div class="form-group">
                <label class="control-label col-md-2" for="brand"><fmt:message key="car.brand"/>:</label>
                <form:select path="brand" class="form-control col-md-6"  value="${carForm.getBrand}" id="brand" >
                    <form:options items="${brands}"></form:options>
                </form:select>
            </div>
            <div class="form-group">
                <label class="control-label col-md-2" for="type"><fmt:message key="car.type"/>:</label>
                <form:select path="type" class="form-control" value="${carForm.getType}" id="type" >
                    <form:options items="${types}"></form:options>
                </form:select>
            </div>
            <div class="form-group">
                <label class="control-label col-md-2" for="engine"><fmt:message key="car.engine"/>:</label>
                <form:select path="engine" class="form-control" value="${carForm.getEngine}" id="engine" >
                    <form:options items="${engines}"></form:options>
                </form:select>
            </div>
            <div class="form-group">
                <label class="control-label col-md-2" for="VIN"><fmt:message key="car.VIN"/>:</label>
                <form:input path="VIN" value="${carForm.getVIN}" class="form-control" id="VIN" />
            </div>
            <div class="form-group">
                <label class="control-label col-md-2" for="licencePlate"><fmt:message key="car.licencePlate"/>:</label>
                <form:input path="licencePlate" value="${carForm.getLicencePlate}" class="form-control" id="licencePlate" />
            </div>
            <button type="submit" class="btn btn-success"><fmt:message key="car.edit" /></button>
            
        </form:form>
        </jsp:attribute>
</custom:layout>


