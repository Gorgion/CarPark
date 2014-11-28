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
               
        <c:url var="editUrl" value="/auth/car/${id}/edit" />
        <form:form action="${editUrl}" method="POST" modelAttribute="carForm" class="form-horizontal">
            <div class="form-group">
                <form:label class="control-label col-md-2" path="brand"><fmt:message key="car.brand"/>:</form:label>
                <div class="col-md-6">
                    <form:select path="brand" class="form-control" id="brand" >
                        <c:forEach items="${brands}" var="brand">    
                            <form:option value="${brand}"><fmt:message key="car.brand.${brand}"/></form:option>
                        </c:forEach>
                    </form:select>
                </div>
            </div>
            <div class="form-group">
                <form:label class="control-label col-md-2" path="type"><fmt:message key="car.type"/>:</form:label>
                <div class="col-md-6">
                    <form:select path="type" class="form-control" id="type" >
                        <c:forEach items="${types}" var="type">    
                            <form:option value="${type}"><fmt:message key="car.type.${type}"/></form:option>
                        </c:forEach>
                    </form:select>
                </div>
            </div>
            <div class="form-group">
                <form:label class="control-label col-md-2" path="engine"><fmt:message key="car.engine"/>:</form:label>
                <div class="col-md-6">
                    <form:select path="engine" class="form-control" id="engine" >
                        <c:forEach items="${engines}" var="engine">    
                            <form:option value="${engine}"><fmt:message key="car.engine.${engine}"/></form:option>
                        </c:forEach>
                    </form:select>
                </div>
            </div>
            <div class="${not empty fromErrors ? 'has-error' : ''} form-group">
                <form:label class="control-label col-md-2" path="VIN"><fmt:message key="car.VIN"/>:</form:label>
                <div class="col-md-6">
                    <form:input path="VIN" class="form-control" value="${VIN}" id="VIN" />
                </div>
            </div>
            <div class="${not empty fromErrors ? 'has-error' : ''} form-group">
                <form:label class="control-label col-md-2" path="licencePlate"><fmt:message key="car.licencePlate"/>:</form:label>
                <div class="col-md-6">
                <form:input path="licencePlate" class="form-control" value="${licencePlate}" id="licencePlate" />
                </div>
            </div>
            <div class="form-group">
                <form:label class="control-label col-sm-2" path="idOffice"><fmt:message key="office"/>:</form:label>
                <div class="col-sm-6">
                    <form:select path="idOffice" class="form-control" >
                        <c:forEach items="${offices}" var="off">    
                            <form:option path="idOffice" value="${off.ID}" label="${off.address}" />
                        </c:forEach>
                    </form:select>
                </div>
            </div>     
            <div class="pull-right col-md-6 ">    
            <button type="submit" class="btn btn-success"><fmt:message key="edit" /></button>
            <button type="button" class="btn btn-warning" onclick="window.location.href='/pa165/auth/car'"><fmt:message key="btn.cancel" /></button>
            </div>
        </form:form>
        </jsp:attribute>
</custom:layout>


