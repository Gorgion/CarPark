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
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:url var="actionUrl" value="/auth/user/add" />
<fmt:message var="title" key="user.form.title.add"/>
<c:if test="${action == 'edit'}">  
    <fmt:message var="title" key="user.form.title.edit"/>
    <c:url var="actionUrl" value="/auth/user/${id}/edit" /> 
</c:if>

<custom:layout title="${title}">
    <jsp:attribute name="content">
        <c:if test="${not empty msg}" >
            <div class="alert alert-success alert-dismissable">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
                    &times;
                </button>
                <fmt:message key="${msg}" />
            </div>
        </c:if>
        <c:if test="${not empty error}" >
            <div class="alert alert-danger alert-dismissable">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
                    &times;
                </button>
                <fmt:message key="${error}"/>
            </div>
        </c:if>

        <form:form action="${actionUrl}" method="POST" modelAttribute="userForm" class="form-horizontal">
            <c:set var="firstNameError"><form:errors path="firstName" /></c:set>
            <c:set var="lastNameError"><form:errors path="lastName" /></c:set>
            <c:set var="addressError"><form:errors path="address" /></c:set>
            <c:set var="birthNumberError"><form:errors path="birthNumber" /></c:set>

            <c:if test="${action != 'edit'}">
                <c:set var="passwordError"><form:errors path="password" /></c:set>
                <c:set var="usernameError"><form:errors path="username" /></c:set>
                <c:set var="roleError"><form:errors path="role" /></c:set>

                    <div class="form-group ${not empty usernameError ? 'has-error' : ''}">
                    <form:label class="col-sm-2 control-label" path="username"><fmt:message key="username" />:</form:label>
                        <div class="col-sm-5">
                        <form:input path="username" cssClass="form-control"/>                    
                        <c:if test="${not empty usernameError}">
                            <p class="text-danger"><fmt:message key="username" />&nbsp;<form:errors path="username" /></p>
                        </c:if>
                    </div> 
                </div>
                <div class="form-group ${not empty passwordError ? 'has-error' : ''}">
                    <form:label class="col-sm-2 control-label" path="password"><fmt:message key="password" />:</form:label>
                        <div class="col-sm-5">
                        <form:password path="password" cssClass="form-control" required="true"/>                    
                        <c:if test="${not empty passwordError}">
                            <p class="text-danger"><fmt:message key="password" />&nbsp;<form:errors path="password" /></p>
                        </c:if>
                    </div> 
                </div>
                <div class="form-group ${not empty roleError ? 'has-error' : ''}">
                    <form:label class="col-sm-2 control-label" path="role"><fmt:message key="user.role" />:</form:label>
                        <div class="col-sm-5">
                        <form:select path="role" cssClass="form-control" required="true">
                            <c:forEach items="${userRoles}" var="role">    
                                <form:option value="${role}"><fmt:message key="user.role.${role}"/></form:option>
                            </c:forEach>
                        </form:select>                    
                        <c:if test="${not empty roleError}">
                            <p class="text-danger"><fmt:message key="user.role" />&nbsp;<form:errors path="role" /></p>
                        </c:if>
                    </div> 
                </div>
            </c:if>

            <c:if test="${not empty firstNameError}">
                <c:set var="firstNameStyle" value="has-error has-feedback" />
            </c:if>             
            <div class="form-group ${firstNameStyle}">
                <form:label class="col-sm-2 control-label" path="firstName"><fmt:message key="user.firstName" />:</form:label>
                    <div class="col-sm-5">
                    <form:input path="firstName" cssClass="form-control" required=""/>
                    <c:if test="${not empty firstNameError}">
                        <p class="text-danger"><fmt:message key="user.firstName" />&nbsp;<form:errors path="firstName" /></p>
                    </c:if>
                </div>  
            </div>         
            <c:if test="${not empty lastNameError}">
                <c:set var="lastNameStyle" value="has-error has-feedback" />
            </c:if>             
            <div class="form-group ${lastNameStyle}">
                <form:label class="col-sm-2 control-label" path="lastName"><fmt:message key="user.lastName" />:</form:label>
                    <div class="col-sm-5">
                    <form:input path="lastName" cssClass="form-control" required=""/>
                    <c:if test="${not empty lastNameError}">
                        <p class="text-danger"><fmt:message key="user.lastName" />&nbsp;<form:errors path="lastName" /></p>
                    </c:if>
                </div>  
            </div> 

            <c:if test="${not empty birthNumberError}">
                <c:set var="birthNumberStyle" value="has-error has-feedback" />
            </c:if>  
            <div class="form-group ${birthNumberStyle}">
                <form:label class="col-sm-2 control-label" path="birthNumber"><fmt:message key="user.birthNumber" />:</form:label>
                    <div class="col-sm-5">
                    <form:input path="birthNumber" cssClass="form-control" required=""/>
                    <c:if test="${not empty birthNumberError}">
                        <p class="text-danger"><fmt:message key="user.birthNumber" />&nbsp;<form:errors path="birthNumber" /></p>
                    </c:if>
                </div> 
            </div>
            <div class="form-group">
                <form:label class="col-sm-2 control-label" path="address"><fmt:message key="user.address" />:</form:label>
                    <div class="col-sm-5">
                    <form:input path="address" cssClass="form-control" required=""/>
                </div> 
            </div>  
            <div class="form-group">
                <form:label class="col-sm-2 control-label" path="idOffice"><fmt:message key="office"/>:</form:label>
                    <div class="col-sm-5">
                    <form:select path="idOffice" cssClass="form-control">
                        <c:forEach var="item" items="${offices}">
                            <c:choose>
                                <c:when test="${selectedOfficeId == item.id}">
                                    <form:option selected="true" value="${item.id}"><c:out value="${item.address}" /></form:option>
                                </c:when>
                                <c:otherwise>
                                    <form:option value="${item.id}"><c:out value="${item.address}" /></form:option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </form:select>
                </div> 
            </div>   
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-success"><fmt:message key="user.form.confirm" /></button>
                <button type="button" class="btn btn-default" onclick="window.location.href = '/pa165/auth/user'">
                    <fmt:message key="user.form.cancel" />
                </button>
                </div>
            </div>
        </form:form>

        <c:if test="${action == 'edit'}">
            <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_BUILT_IN_ADMIN')">
            <hr class="divider" />
            <c:url var="credentialsEditUrl" value="/auth/user/${id}/credentials/edit" />
            <form:form action="${credentialsEditUrl}" validate="true" method="POST" modelAttribute="credentialsForm" class="form-horizontal">
                <c:set var="usernameError"><form:errors path="username" /></c:set>
                <c:set var="roleError"><form:errors path="role" /></c:set>

                <div class="form-group ${not empty usernameError ? 'has-error' : ''}">
                    <form:label class="col-sm-2 control-label" path="username"><fmt:message key="username" />:</form:label>
                        <div class="col-sm-5">
                        <form:hidden path="username" required="true"/>                    
                        <p class="form-control-static"><c:out value="${credentialsForm.username}"/></p>
                    </div> 
                </div>                
                <div class="form-group ${not empty roleError ? 'has-error' : ''}">
                    <form:label class="col-sm-2 control-label" path="role"><fmt:message key="user.role" />:</form:label>
                        <div class="col-sm-5">
                        <form:select path="role" cssClass="form-control" required="true">
                            <c:forEach items="${userRoles}" var="role">    
                                <c:choose>
                                <c:when test="${credentialsForm.role == role}">
                                    <form:option selected="true" value="${role}"><fmt:message key="user.role.${role}"/></form:option>
                                </c:when>
                                <c:otherwise>
                                    <form:option value="${role}"><fmt:message key="user.role.${role}"/></form:option>
                                </c:otherwise>
                            </c:choose>                                
                            </c:forEach>
                        </form:select> 
                        <c:if test="${not empty roleError}">
                            <p class="text-danger"><fmt:message key="user.role" />&nbsp;<form:errors path="role" /></p>
                        </c:if>
                    </div> 
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-success"><fmt:message key="user.form.confirm" /></button>
                    <button type="button" class="btn btn-default" onclick="window.location.href = '/pa165/auth/user'">
                        <fmt:message key="user.form.cancel" />
                    </button>
                    </div>
                </div>
            </form:form>
            <hr class="divider" />
            <c:url var="passwordEditUrl" value="/auth/user/${id}/credentials/password/edit" />
            <form:form action="${passwordEditUrl}" validate="true" method="POST" modelAttribute="passwordForm" class="form-horizontal">
                <c:set var="passwordError"><form:errors path="password" /></c:set>
                
                <div class="form-group ${not empty passwordError ? 'has-error' : ''}">
                    <form:label class="col-sm-2 control-label" path="password"><fmt:message key="password" />:</form:label>
                        <div class="col-sm-5">
                        <form:password path="password" cssClass="form-control" required="true"/>                    
                        <c:if test="${not empty passwordError}">
                            <p class="text-danger"><fmt:message key="password" />&nbsp;<form:errors path="password" /></p>
                        </c:if>
                    </div> 
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-success"><fmt:message key="user.form.passwordChange" /></button>                    
                    </div>
                </div>
            </form:form>
            </sec:authorize>
        </c:if>

    </jsp:attribute>
</custom:layout>
