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

<fmt:message var="title" key="user.form.title"/>




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
        
        <c:url var="addUserUrl" value="/auth/user/add" />
        <form:form action="${addUserUrl}" method="POST" modelAttribute="userForm" class="form-horizontal">
            <div class="form-group col-sm-5"> 
                <form:label path="firstName"><fmt:message key="user.firstName" /></form:label>
                <form:input path="firstName" cssClass="form-control col-sm-8" />
            </div>
          <div class="form-group col-sm-5"> 
                <form:label path="lastName"><fmt:message key="user.lastName" /></form:label>
                <form:input path="lastName" cssClass="form-control" />
            </div>
          <div class="form-group col-sm-5"> 
                <form:label path="birthNumber"><fmt:message key="user.birthNumber" /></form:label>
                <form:input path="birthNumber" cssClass="form-control" />
            </div>
          <div class="form-group col-sm-5"> 
                <form:label path="address"><fmt:message key="user.address" /></form:label>
                <form:input path="address" cssClass="form-control" />
            </div>
           
            <button type="submit" class="btn btn-success"><fmt:message key="user.add" /></button>
        </form:form>

        <form class="form-horizontal" method="post" action='employee/add.htm' name="userForm" id="userForm">
            <div class="control-group">
                <label class="control-label">Firstname</label>
                <div class="controls">
                    <input type="text" name="firstName" id="firstName" title="First Name" path="firstName">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">Lastname</label>
                <div class="controls">
                    <input type="text" name="lastName" id="lastName" title="Last Name" value="">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">Birth number</label>
                <div class="controls">
                    <input type="text" name="email" id="email" title="Email" value="">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">Address</label>
                <div class="controls">
                    <input type="text" name="email" id="email" title="Email" value="">
                </div>
            </div>
            <br />
            <div class="form-actions">
                <button type="submit" class="btn btn-success">Submit</button>
                <button type="button" class="btn">Cancel</button>
            </div>
        </form>

    </jsp:attribute>
</custom:layout>
