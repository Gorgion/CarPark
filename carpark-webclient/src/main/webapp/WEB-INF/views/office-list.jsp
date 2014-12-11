<%-- 
    Document   : office-list
    Created on : 11.12.2014
    Author     : Jiri Dockal
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" session="false"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="req" value="${pageContext.request}" />

<custom:layout title="Offices">    
    <jsp:attribute name="content">
        <div id="tableContent" class="row">
            <a href="<c:url value="/office/add" />" class="btn btn-success">Add</a>
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
                var spinner = getSpinner();
                $.ajax({
                    type: "GET",
                    dataType:"json",
                    url: "http://localhost:8080/pa165/rest/offices",
                    success: function(data){
                        var table = $(

                            "<table class=\"table table-hover\">\n"+
                            "    <thead>\n"+
                            "        <tr>\n"+
                            "            <th>ID</th>\n"+
                            "            <th>Address</th>\n"+
                            "            <th>Manager</th>\n"+
                            "            <th>Employees</th>\n"+
                            "            <th></th>\n"+
                            "        </tr>\n"+
                            "    </thead>\n"+
                            "    <tbody>"
                        ).appendTo($('#tableContent'));
                        $.each(data, function(i, office){
                            var actRow = $('<tr/>').appendTo(table)
                            .append($('<td/>').text(office.id))
                            .append($('<td/>').text(office.address))
                            .append($('<td/>').text(office.manager !== null ? office.manager.lastName : ""));
                            
                            $.each(office.employees,function(j,employee){
                                $('<td/>').text(employee.lastName).appendTo(actRow);
                            });
                            
                            actRow.append($('<td/>').append(
                                "<a href="+"http://localhost:8085/pa165/client"+"/office/"+office.id+"/edit"+" class='btn btn-info'><span class='glyphicon glyphicon-edit' /></a>"+
                                "<form action="+"http://localhost:8085/pa165/client"+"/office/"+office.id+"/delete"+" method='POST' class='form-inline' style='display: inline-block;'>"+
                                "    <button type='submit' name='delete' class='btn btn-danger'><span class='glyphicon glyphicon-remove' /></button>"+
                                "</form>"     
                            ));//.appendTo(actRow);
                        });
                        
                        table.append("</tbody></table>");
                        spinner.remove();
                    },
                    error: function(xhr,textStatus,errorThrown){
                        spinner.remove();                       
                        alert("fail\n"+errorThrown);
                    }
                });
            });

        </script>
    </jsp:attribute>
</custom:layout>