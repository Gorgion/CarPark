<%-- 
    Document   : office-form
    Created on : 24.11.2014, 11:57:34
    Author     : Karolina Burska
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" session="false"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message var="title" key="office.edit.title"/>
<custom:layout title="${title}">    
    <jsp:attribute name="content">
        <c:url var="editOfficeUrl" value="/auth/office/${id}/edit" />
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
        
        <form:form action="${editOfficeUrl}" method="POST" modelAttribute="officeEditForm" class="form-horizontal">
            <div class="form-group"> 
                <form:label path="address" cssClass="col-sm-2"><fmt:message key="office.address" /></form:label>
                <form:input path="address" cssClass="form-control"/>
            </div>
            
            <div class="form-group"> 
                <form:label path="manager" cssClass="col-sm-2"><fmt:message key="office.manager" /></form:label>
                    <!--<form:select path="manager" class="form-control" id="manager" >
                        <form:option value="">&nbsp;</form:option>
                        <c:forEach items="${managers}" var="manager">    
                            <form:option value="${manager}" label="${manager.firstName} ${manager.lastName}"/>
                        </c:forEach>
                    </form:select>-->
                    <form:select path="manager" class="form-control" id="manager" >
                        <form:options items="${managers}"></form:options>
                    </form:select>
            </div>
                
            <div class="form-group"> 
                <form:label path="employees" cssClass="col-sm-2"><fmt:message key="office.employees" /></form:label>
                <form:select path="employees" multiple="true" cssClass="form-control">
                    <form:option value="">&nbsp;</form:option>
                    <c:forEach items="${employees}" var="empl">    
                        <form:option path ="${empl}" value="${empl.id}" />
                    </c:forEach>
                </form:select>
            </div>
                
            <div class="form-group"> 
                <form:label path="cars" cssClass="col-sm-2"><fmt:message key="office.cars" /></form:label>
                <form:select path="cars" cssClass="form-control">
                    <form:option value="">&nbsp;</form:option>
                    <c:forEach items="${cars}" var="cars">     
                        
                        <form:option path = "${cars}" value="${cars}" />
                    </c:forEach>
                </form:select>
            </div>
           
            <button type="submit" class="btn btn-success"><fmt:message key="office.edit" /></button>
        </form:form>
        
     </jsp:attribute>        
</custom:layout>           

