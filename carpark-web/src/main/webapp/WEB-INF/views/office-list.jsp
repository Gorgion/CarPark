<%-- 
    Document   : office-list
    Created on : 24.11.2014, 12:57:54
    Author     : Karolina Burska
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" session="false"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<fmt:message var="title" key="office.list.title"/>
<custom:layout title="${title}">    
    <jsp:attribute name="content">
        <div class="row">            
            <sec:authorize access="hasAnyRole('ROLE_BUILT_IN_ADMIN', 'ROLE_ADMIN')">
                <a href="<c:url value="/auth/office/add" />" class="btn btn-success"><fmt:message key="office.add"/></a>
                <hr class="divider" />
            </sec:authorize>
            <c:if test="${not empty error}" >
                <div class="alert alert-danger alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
                        &times;
                    </button>
                    <fmt:message key="${error}" />
                </div>
            </c:if>


            <c:if test="${not empty msg}" >
                <div class="alert alert-success alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
                        &times;
                    </button>
                    <fmt:message key="${msg}" />
                </div>
            </c:if>
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th><fmt:message key="office.id" /></th>
                        <th><fmt:message key="office.address" /></th>
                        <th><fmt:message key="office.manager" /></th>
                        <th><fmt:message key="office.employees" /></th>
                        <th><fmt:message key="office.cars" /></th>
                            <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <th></th>
                            </sec:authorize>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${offices}" var="office" varStatus="status">
                        <tr>
                            <td>${office.id}</td>
                            <td>${office.address}</td>
                            <td>${office.manager.firstName} ${office.manager.lastName}
                            </td>
                            <td>
                                <c:forEach items="${office.employees}" var="of" varStatus="status">
                                    <p>${of.firstName} ${of.lastName}  </p>
                                </c:forEach>
                            </td>

                            <td>
                                <c:forEach items="${office.cars}" var="of" varStatus="status">
                                    <p><fmt:message key="car.brand.${of.brand}"/> <fmt:message key="car.type.${of.type}"/></p>
                                </c:forEach>
                            </td>
                            <sec:authorize access="hasAnyRole('ROLE_BUILT_IN_ADMIN', 'ROLE_ADMIN')">
                                <td>
                                    <a href='<c:url value="/auth/office/${office.id}/edit" />' class="btn btn-info"><span class="glyphicon glyphicon-edit" /></a>
                                    <form action="<c:url value="/auth/office/${office.id}/delete" />" method="POST" class="form-inline" style="display: inline-block;">
                                        <button type="submit" name="delete" class="btn btn-danger"><span class="glyphicon glyphicon-remove" /></button>
                                    </form>                            
                                </td>
                            </sec:authorize>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <custom:delete-dialog key="office.deleteDialog"></custom:delete-dialog>

        <custom:modal-dialog dialogId="managerDetails" dialogTitleKey="managerDetails.title">
            <div class="form-horizontal">

                <div class="form-group">
                    <label class="control-label col-sm-4"><fmt:message key="manager.firstName"/>:</label>
                    <div class="col-sm-8">
                        <p name="firstName" class="form-control-static"></p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-4"><fmt:message key="manager.lastName"/>:</label>
                    <div class="col-sm-8">
                        <p name="lastName" class="form-control-static"></p>
                    </div>
                </div>    

            </div>
        </custom:modal-dialog>  
    </jsp:attribute>        
</custom:layout>