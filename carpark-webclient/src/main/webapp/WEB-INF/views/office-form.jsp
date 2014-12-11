<%-- 
    Document   : office-form
    Created on : 24.11.2014, 11:57:34
    Author     : Karolina Burska
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" session="false"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<title>Add office</title>
<custom:layout title="${title}">    
    <jsp:attribute name="content">
        <c:url var="addOfficeUrl" value="/auth/office/add" />
        <c:if test="${not empty msg}">
            <div class="alert alert-success alert-dismissable">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
                    &times;
                </button>
                ${msg}
            </div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissable">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
                    &times;
                </button>
                ${error}
            </div>
        </c:if>
        <form:form action="${addOfficeUrl}" method="POST" modelAttribute="officeForm" class="form-horizontal">
            <c:set var="addressError"><form:errors path="address" /></c:set> 
            <c:if test="${not empty addressError}"><c:set var="addressStyle" value="has-error has-feedback" /></c:if>             
            <div class="form-group ${addressStyle}">
                <form:label path="address" cssClass="col-sm-2 control-label">Address:</form:label>
                <div class="col-sm-5">
                    <form:input path="address" cssClass="form-control"/>
                    <c:if test="${not empty addressError}">
                        <p class="text-danger">Address&nbsp;<form:errors path="address" /></p>
                    </c:if>
                </div>
            </div>
            <div class="col-sm-offset-2 col-sm-10">                
                <button type="submit" class="btn btn-success">Add</button>
                <button type="button" class="btn btn-default" onclick="window.location.href='/pa165/auth/office'">Cancel</button>
            </div>
            </form:form>
     </jsp:attribute>        
</custom:layout>           

