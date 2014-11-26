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
        <form:form action="${editOfficeUrl}" method="POST" modelAttribute="officeEditForm" class="form-horizontal">
            <div class="form-group"> 
                <form:label path="address" cssClass="col-sm-2"><fmt:message key="office.address" /></form:label>
                <form:input path="address" cssClass="form-control"/>
            </div>
            
            <div class="form-group"> 
                <form:label path="manager" cssClass="col-sm-2"><fmt:message key="office.manager" /></form:label>
                <form:select path="manager" cssClass="form-control">
                    <c:forEach items="${employees}" var="manager">     
                        <form:option path ="${manager}" value="${manager}" />
                    </c:forEach>
                </form:select>
            </div>
                
            <div class="form-group"> 
                <form:label path="employees" cssClass="col-sm-2"><fmt:message key="office.employees" /></form:label>
                <form:select path="employees" cssClass="form-control">
                    <c:forEach items="${employees}" var="empl" varStatus="status">     
                        <form:option path ="empl[${status.index}]" value="${empl.id}" />
                    </c:forEach>
                </form:select>
            </div>
                
            <div class="form-group"> 
                <form:label path="cars" cssClass="col-sm-2"><fmt:message key="office.cars" /></form:label>
                <form:select path="cars" cssClass="form-control">
                    <c:forEach items="${cars}" var="cars">     
                        <form:option path = "cars[${status.index}]" value="${cars}" />
                    </c:forEach>
                </form:select>
            </div>
           
            <button type="submit" class="btn btn-success"><fmt:message key="office.edit" /></button>
        </form:form>
     </jsp:attribute>        
</custom:layout>           

