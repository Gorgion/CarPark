<%-- 
    Document   : office-form
    Created on : 24.11.2014, 11:57:34
    Author     : Karolina Burska
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" session="false"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<title>Edit Office</title>
<custom:layout title="${title}">    
    <jsp:attribute name="content">
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
        <c:url var="editOfficeUrl" value="/auth/office/${id}/edit" />
        
        <form:form action="${editOfficeUrl}" method="POST" modelAttribute="officeEditForm" class="form-horizontal">
            <c:set var="addressError"><form:errors path="address" /></c:set>
            <c:set var="managerIdError"><form:errors path="managerId"/></c:set>
            <c:if test="${not empty addressError}">
                    <c:set var="addressStyle" value="has-error has-feedback" />
                </c:if> 
            <div class="form-group ${addressStyle}"> 
                <form:label path="address" cssClass="control-label col-sm-2">Address</form:label>
               <div class="col-sm-6">
                <form:input path="address" cssClass="form-control"/>
                <c:if test="${not empty addressError}">
                    <p class="text-danger">Address&nbsp;<form:errors path="address" /></p>
                </c:if>
               </div>
            </div>
            
            <div class="form-group ${not empty managerIdError ? 'has-error' : ''}"> 
                <form:label path="managerId" cssClass="control-label col-sm-2">Manager</form:label>
                    <div class="col-sm-6">
                <form:select path="managerId" class="form-control" id="managerId" >
                        <form:option value="">&nbsp;</form:option>
                        <c:forEach items="${managerId}" var="man">    
                           
                                    <form:option value="${man.id}" label="${man.firstName} ${man.lastName}, ID: ${man.id}"/>
                               

                        </c:forEach>
                    </form:select>
                    <div class="col-sm-5 help-block">${managerIdError}</div>     
                </div>    
            </div>
            <div class="pull-right col-sm-6 ">      
                <button type="submit" class="btn btn-success">Edit</button>
                <button type="button" class="btn btn-default" onclick="window.location.href='/pa165/auth/office'">Cancel</button>
            </div>
        </form:form>
     </jsp:attribute>        
</custom:layout>           
