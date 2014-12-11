<%-- 
    Document   : user-form
    Created on : 23.11.2014, 20:24:05
    Author     : Tomas Vasicek
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" session="false"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url var="actionUrl" value="/auth/user/add" />
<title>Add user</title>
<c:if test="${action == 'edit'}">  
    <title>Edit user</title>
    <c:url var="actionUrl" value="/auth/user/edit" /> 
</c:if>

<custom:layout title="${title}">
    <jsp:attribute name="content">
        <c:if test="${msg}">
            <div class="alert alert-success alert-dismissable">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
                    &times;
                </button>
                ${msg}
            </div>
        </c:if>
        <c:if test="${error}">
            <div class="alert alert-danger alert-dismissable">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
                    &times;
                </button>
                ${error}
            </div>
        </c:if>

        
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
                    <form:label class="col-sm-2 control-label" path="firstName">First name:</form:label>
                    <div class="col-sm-5">
                    <form:input path="firstName" cssClass="form-control" required=""/>
                    <c:if test="${not empty firstNameError}">
                        <p class="text-danger">First name&nbsp;<form:errors path="firstName" /></p>
                    </c:if>
                    </div>  
                </div>         
                <c:if test="${not empty lastNameError}">
                    <c:set var="lastNameStyle" value="has-error has-feedback" />
                </c:if>             
                <div class="form-group ${lastNameStyle}">
                    <form:label class="col-sm-2 control-label" path="lastName">Last name:</form:label>
                    <div class="col-sm-5">
                    <form:input path="lastName" cssClass="form-control" required=""/>
                    <c:if test="${not empty lastNameError}">
                        <p class="text-danger">Last name&nbsp;<form:errors path="lastName" /></p>
                    </c:if>
                    </div>  
                </div> 

                <c:if test="${not empty birthNumberError}">
                    <c:set var="birthNumberStyle" value="has-error has-feedback" />
                </c:if>  
                <div class="form-group ${birthNumberStyle}">
                    <form:label class="col-sm-2 control-label" path="birthNumber">Birth numeber:</form:label>
                    <div class="col-sm-5">
                    <form:input path="birthNumber" cssClass="form-control" required=""/>
                    <c:set var="birthNumberError"><form:errors path="birthNumber" /></c:set>
                    <c:if test="${not empty birthNumberError}">
                        <p class="text-danger">Birth number&nbsp;<form:errors path="birthNumber" /></p>
                    </c:if>
                    </div> 
                </div>
                <div class="form-group">
                    <form:label class="col-sm-2 control-label" path="address">Address:</form:label>
                    <div class="col-sm-5">
                    <form:input path="address" cssClass="form-control" required=""/>
                    </div> 
                </div>  
                <div class="form-group">
                    <form:label class="col-sm-2 control-label" path="idOffice">Office:</form:label>
                    <div class="col-sm-5">
                    <form:select path="idOffice" cssClass="form-control">
                        <c:forEach var="item" items="${offices}">
                            <c:choose>
                                <c:when test="${selectedOfficeId == item.ID}">
                                    <form:option selected="true" value="${item.ID}"><c:out value="${item.address}" /></form:option>
                                </c:when>
                                <c:otherwise>
                                    <form:option value="${item.ID}"><c:out value="${item.address}" /></form:option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </form:select>
                    </div> 
                </div>   
                <div class="col-sm-offset-2 col-sm-10"> 
                    <button type="submit" class="btn btn-success">Confirm</button>
                    <button type="button" class="btn btn-default" onclick="window.location.href = '/pa165/auth/user'">
                        Cancel
                    </button>
                </div>
            </form:form>
       
    </jsp:attribute>
</custom:layout>
