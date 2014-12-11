<%-- 
    Document   : office-list
    Created on : 24.11.2014, 12:57:54
    Author     : Karolina Burska
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" session="false"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<title>Offices</title>
<custom:layout title="${title}">    
    <jsp:attribute name="content">
        <div class="row">
            <a href="<c:url value="/auth/office/add" />" class="btn btn-success">Add</a>
            <hr class="divider" />
            <c:if test="${not empty error}" >
                <div class="alert alert-danger alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
                        &times;
                    </button>
                    ${error}
                </div>
            </c:if>


            <c:if test="${not empty msg}" >
                <div class="alert alert-success alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
                        &times;
                    </button>
                    ${msg}
                </div>
            </c:if>
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Address</th>
                        <th>Manager</th>
                        <th>Employees</th>
                        <th>Cars</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${offices}" var="office" varStatus="status">
                        <tr>
                            <td>${office.ID}</td>
                            <td>${office.address}</td>
                            <td>${office.manager.firstName} ${office.manager.lastName}
                                <!--
                                <a href="#" class="btn btn-link" data-toggle="modal" data-target="#managerDetails"
                                        data-manager-firstName="${office.manager.firstName}" 
                                        data-manager-lastName="${office.manager.lastName}" 
                                >Details of manager</a>-->
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
                                <a href='<c:url value="/auth/office/${office.ID}/edit" />' class="btn btn-info"><span class="glyphicon glyphicon-edit" /></a>
                                <form action="<c:url value="/auth/office/${office.ID}/delete" />" method="POST" class="form-inline" style="display: inline-block;">
                                    <button type="submit" name="delete" class="btn btn-danger"><span class="glyphicon glyphicon-remove" /></button>
                                </form>                            
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <custom:delete-dialog key="office"></custom:delete-dialog>

        <custom:modal-dialog dialogId="managerDetails" dialogTitleKey="managerDetails.title">
            <div class="form-horizontal">

                <div class="form-group">
                    <label class="control-label col-sm-4">First name of manager:</label>
                    <div class="col-sm-8">
                        <p name="firstName" class="form-control-static"></p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-4">Last name of manager:</label>
                    <div class="col-sm-8">
                        <p name="lastName" class="form-control-static"></p>
                    </div>
                </div>    

            </div>
        </custom:modal-dialog>  
    </jsp:attribute>
    <jsp:attribute name="ajaxGetter">
        <script type="text/javascript">
            $(document).ready(function(){
                $.ajax({
                    type: "GET",
                    url: "localhost:8080/pa165/rest/office",
                    success: function(data)){
                        alert(data);
                    }
                });
            });

        </script>
    </jsp:attribute>
</custom:layout>