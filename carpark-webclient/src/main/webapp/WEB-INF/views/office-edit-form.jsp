<%-- 
    Document   : office-form
    Created on : 12.12.2014
    Author     : Jiri Dockal
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" session="false"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<custom:layout title="Edit Office">    
    <jsp:attribute name="content">
        <div class="alert alert-danger alert-dismissable">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
                &times;
            </button>
        </div>
        
        <form class="form-horizontal">
            <div class="form-group"> 
                <label class="control-label col-sm-2">Address: <span style="color:red;">*</span></label>
                <div class="col-sm-6">
                    <input id="address" class="form-control" required="true"/>
                </div>
            </div>
            <br />
            <div class="form-group"> 
                <label class="control-label col-sm-2">Manager: <span style="color:red;">*</span></label>
                <div class="col-sm-6">
                    <select class="form-control" id="managerId" required="true"></select>
                </div>    
            </div>
            <div class="col-sm-offset-2 col-sm-10">      
                <button type="button" class="btn btn-success" onclick="editOffice()">Edit</button>
                <button type="button" class="btn btn-default" onclick="window.location.href='/pa165/client/office'">Cancel</button>
            </div>
        </form>
    </jsp:attribute>  
    <jsp:attribute name="ajaxScript">
        <script type="text/javascript">
            $(".alert-danger").hide();
            url = document.URL;
            urlId = url.substring(0,url.lastIndexOf("/edit"));
            var ID = urlId.substring(urlId.lastIndexOf("/")+1);
            
            $(document).ready(function(){
                $.ajax({
                    type: "GET",
                    dataType:"json",
                    url: "http://localhost:8080/pa165/rest/offices/"+ID,
                    success: function(data){
                        $("#address").val(data.address);
                        
                        var sel = $("#managerId");
                        $.each(data.employees,function(i,employee)
                        {   
                            var opt = document.createElement('option');
                            opt.innerHTML = employee.firstName + " " + employee.lastName;
                            
                            if(data.manager !== null)
                            {
                                if(data.manager.id === employee.id)
                                    opt.selected = "selected";
                            }
                            opt.value = employee.id;
                            sel.append(opt);
                        });
                    },
                    error: function(xhr,textStatus,errorThrown){                     
                        window.location.replace("/pa165/client/500");
                    }
                });
            });
            
            
            function editOffice(){
                
                var address = $("#address").val();
                var managerId = $("#managerId").val();
                
                $.ajax({
                    type: "PUT",
                    data: JSON.stringify({"address": address,"managerId": managerId}),
                    contentType: "application/json",
                    url: "http://localhost:8080/pa165/rest/offices/"+ID,
                    success: function(){
                        window.location.href='/pa165/client/office';
                    },
                    error: function(xhr){
                        if (xhr.status === 400)
                        {
                            $.each(xhr.responseJSON.fieldErrors,function(i,field)
                            {
                                $("#"+field.field.toString()).parent().parent().addClass("has-error");
                            });
                            $(".alert-danger").show().append("Can't be blank!");
                        }
                        if (xhr.status === 500)
                        {
                            window.location.href='/pa165/client/500';
                        }
                    },
                    fail: function(xhr,textStatus,errorThrown){
                        window.location.replace("/pa165/client/500");
                    }
                });
            };
            
        </script>
    </jsp:attribute>
</custom:layout>           

