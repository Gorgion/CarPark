<%-- 
    Document   : user-form
    Created on : 23.11.2014, 20:24:05
    Author     : Tomas Vasicek
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" session="false"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url var="actionUrl" value="/auth/user/addUser" />
<fmt:message var="title" key="user.form.title.add"/>
<c:if test="${action == 'edit'}">  
    <fmt:message var="title" key="user.form.title.edit"/>
    <c:url var="actionUrl" value="/auth/user/edit" /> 
</c:if>

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

        <div class="col-md-4">
            <form:form action="${actionUrl}" method="POST" modelAttribute="userForm" class="form-horizontal">
                <form:hidden path="id"/>
                <div class="form-group">
                    <form:label path="firstName"><fmt:message key="user.firstName" /></form:label>
                    <form:input path="firstName" cssClass="form-control input-md" required=""/>
                </div>  
                <div class="form-group">
                    <form:label path="lastName"><fmt:message key="user.lastName" /></form:label>
                    <form:input path="lastName" cssClass="form-control input-md" required=""/>
                </div>
                <div class="form-group">
                    <form:label path="birthNumber"><fmt:message key="user.birthNumber" /></form:label>
                    <form:input path="birthNumber" cssClass="form-control input-md" required=""/>
                </div>
                <div class="form-group">
                    <form:label path="address"><fmt:message key="user.address" /></form:label>
                    <form:input path="address" cssClass="form-control input-md" required=""/>
                </div>  
                <div class="form-group"> 
                    <button type="submit" class="btn btn-success"><fmt:message key="user.form.confirm" /></button>
                    <button type="cancel" class="btn btn-danger"><fmt:message key="user.form.cancel" /></button>
                </div>
            </form:form>
        </div>
    </jsp:attribute>
</custom:layout>
