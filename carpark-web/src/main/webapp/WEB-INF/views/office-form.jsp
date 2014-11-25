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


        <c:url var="addOfficeUrl" value="/auth/office/add" />
        <form:form action="${addUrl}" method="POST" modelAttribute="officeForm" class="form-horizontal">
            <div class="form-group"> 
                <form:label path="address" cssClass="col-sm-2"><fmt:message key="office.address" /></form:label>
                <form:input path="address" cssClass="form-control"/>
            </div>
            <div class="form-group"> 
                <form:label path="manager" cssClass="col-sm-2"><fmt:message key="office.manager" /></form:label>
                <form:select path="manager" cssClass="form-control">
                    <c:forEach items="${employees}" var="empl">     
                        <form:option path = "${empl.id}" value="${empl.id}" />
                    </c:forEach>
                </form:select>
            </div>
           
            <button type="submit" class="btn btn-success"><fmt:message key="office.add" /></button>
        </form:form>

