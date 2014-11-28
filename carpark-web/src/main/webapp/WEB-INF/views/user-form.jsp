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

<c:url var="actionUrl" value="/auth/user/add" />
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
                <c:set var="firstNameError"><form:errors path="firstName" /></c:set>
                <c:set var="lastNameError"><form:errors path="lastName" /></c:set>
                <c:set var="addressError"><form:errors path="address" /></c:set>
                <c:set var="birthNumberError"><form:errors path="birthNumber" /></c:set>

                <c:if test="${not empty firstNameError}">
                    <c:set var="firstNameStyle" value="has-error has-feedback" />
                </c:if>             
                <div class="form-group ${firstNameStyle}">
                    <form:label path="firstName"><fmt:message key="user.firstName" /></form:label>
                    <form:input path="firstName" cssClass="form-control input-md" required=""/>
                    <c:if test="${not empty firstNameError}">
                        <p class="text-danger"><fmt:message key="user.firstName" />&nbsp;<form:errors path="firstName" /></p>
                    </c:if>
                </div>  

                <c:if test="${not empty lastNameError}">
                    <c:set var="lastNameStyle" value="has-error has-feedback" />
                </c:if>             
                <div class="form-group ${lastNameStyle}">
                    <form:label path="lastName"><fmt:message key="user.lastName" /></form:label>
                    <form:input path="lastName" cssClass="form-control input-md" required=""/>
                    <c:if test="${not empty lastNameError}">
                        <p class="text-danger"><fmt:message key="user.lastName" />&nbsp;<form:errors path="lastName" /></p>
                    </c:if>
                </div>  

                <c:if test="${not empty birthNumberError}">
                    <c:set var="birthNumberStyle" value="has-error has-feedback" />
                </c:if>  
                <div class="form-group ${birthNumberStyle}">
                    <form:label path="birthNumber"><fmt:message key="user.birthNumber" /></form:label>
                    <form:input path="birthNumber" cssClass="form-control input-md" required=""/>
                    <c:set var="birthNumberError"><form:errors path="birthNumber" /></c:set>
                    <c:if test="${not empty birthNumberError}">
                        <p class="text-danger"><fmt:message key="user.birthNumber" />&nbsp;<form:errors path="birthNumber" /></p>
                    </c:if>
                </div>
                <div class="form-group">
                    <form:label path="address"><fmt:message key="user.address" /></form:label>
                    <form:input path="address" cssClass="form-control input-md" required=""/>
                </div>  
                <div class="form-group">
                    <form:label class="control-label" path="idOffice"><fmt:message key="office"/>:</form:label>
                    <form:select path="idOffice" cssClass="form-control input-md">
                        <c:forEach var="item" items="${offices}">
                            <c:choose>
                                <c:when test="${selectedOfficeId == item.ID}">
                                    <form:option selected="true" value="${item.ID}" label="${item.address}"></form:option>
                                </c:when>
                                <c:otherwise>
                                    <form:option value="${item.ID}" label="${item.address}"></form:option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </form:select>
                </div>   
                <div class="form-group"> 
                    <button type="submit" class="btn btn-success"><fmt:message key="user.form.confirm" /></button>
                    <button type="button" class="btn btn-danger" onclick="window.location.href = '/pa165/auth/user'">
                        <fmt:message key="user.form.cancel" />
                    </button>
                </div>
            </form:form>
        </div>
    </jsp:attribute>
</custom:layout>
