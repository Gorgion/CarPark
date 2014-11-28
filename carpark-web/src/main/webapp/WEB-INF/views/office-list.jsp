<%-- 
    Document   : office-list
    Created on : 24.11.2014, 12:57:54
    Author     : Karolina Burska
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" session="false"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:message var="title" key="office.list.title"/>
<custom:layout title="${title}">    
    <jsp:attribute name="content">
        <div class="row">
            <a href="<c:url value="/auth/office/add" />" class="btn btn-success"><fmt:message key="office.add"/></a>
            <hr class="divider" />
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
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${offices}" var="office" varStatus="status">
                    <tr>
                        <td>${office.ID}</td>
                        <td>${office.address}</td>
                        <td>${office.manager.firstName} ${office.manager.lastName}
                            <!--<a href="#" class="btn btn-link" data-toggle="modal" data-target="#carDetails"
                             <c:forEach items="${office.employees}" var="of" varStatus="status"> 

                                    data-car-brand-${of.id}="${of.id}" 
 
                             </c:forEach>  
                            >
                            <fmt:message key="car.details"/></a>-->
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
                        <td>
                            <a href='<c:url value="/auth/office/${office.ID}/edit" />' class="btn btn-default"><fmt:message key="edit" /></a>
                        </td>
                            
                        <td>
                            <form action="<c:url value="/auth/office/${office.ID}/delete" />" method="POST" class="form-inline" >
                                    <button type="submit" name="delete" class="btn btn-danger"><fmt:message key="delete" /></button>
                            </form>                           
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        </div>
        <custom:delete-dialog key="office"></custom:delete-dialog>
        <custom:modal-dialog dialogId="carDetails" dialogTitleKey="carDetails.title">
            <div class="form-horizontal">
            <c:forEach items="${offices}" var="of" varStatus="status">
            <div class="form-group">
                <label class="control-label col-sm-4"><fmt:message key="car.brand-1"/>:</label>
                <div class="col-sm-8">
                    <p name="brand-1" class="form-control-static"></p>
                </div>
            </div>
                </c:forEach>
            </div>
        </custom:modal-dialog>  
    </jsp:attribute>        
</custom:layout>