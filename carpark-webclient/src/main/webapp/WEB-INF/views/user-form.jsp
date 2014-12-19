<%-- 
    Document   : user-form
    Created on : 12.12.2014, 16:41:58
    Author     : Karolina Burska
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" session="false"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<custom:layout title="Add user">
    <jsp:attribute name="content">
        <div class="alert alert-danger alert-dismissable">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
                    &times;
                </button>
        </div>
        <form class="form-horizontal">
            <div class="form-group">
                <label class="col-sm-2 control-label">Username: </label>
                <div class="col-sm-5">
                    <input id="usernameInput" type="text" name="username" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">Password: </label>
                <div class="col-sm-5">
                    <input id="passwordInput" type="password" name="password" class="form-control"/>
                </div>
            </div>
            <hr class="divider" />
            <div class="form-group">
                <label class="col-sm-2 control-label">First name: </label>
                <div class="col-sm-5">
                    <input id="firstNameInput" type="text" name="firstName" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">Last name: </label>
                <div class="col-sm-5">
                    <input id="lastNameInput" type="text" name="lastName" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">Address: </label>
                <div class="col-sm-5">
                    <input id="addressInput" type="address" name="address" class="form-control"/>
                </div> 
            </div>  
            <div class="form-group">
                <label class="col-sm-2 control-label">Birth number: </label>
                <div class="col-sm-5">
                    <input id="birthNumberInput" type="birthNumber" name="birthNumber" class="form-control"/>
                </div> 
            </div>
            <hr class="divider" />
            <div class="form-group"> 
                <label class="control-label col-sm-2">Office: </label>
                <div class="col-sm-5">
                    <select class="form-control" id="officeIdInput" ></select>
                </div>    
            </div>
            <div class="form-group" hidden="true"> 
                <div class="col-sm-5">
                    <input id="userRoleInput" type="text" name="userRole" class="form-control" value="USER"/>
                </div>     
            </div>
            <div class="col-sm-offset-2 col-sm-10">                
                <button onclick="addUser()" type="button" class="btn btn-success">Add user</button>
                <button type="button" class="btn btn-default" onclick="window.location.href='/pa165/client/user'">Cancel</button>
            </div>
        </form>
    </jsp:attribute>
    <jsp:attribute name="ajaxScript">
        <script type="text/javascript">
            $(document).ready(function(){
                $(".alert-danger").hide();
                $.ajax({
                    type: "GET",
                    dataType:"json",
                    url: "http://localhost:8080/pa165/rest/offices/",
                    success: function(data){
                        var sel = $("#officeIdInput");
                        $.each(data,function(i,data)
                        {   
                            var opt = document.createElement('option');
                            opt.innerHTML = data.address;
                            opt.value = data.id;
                            sel.append(opt);
                        });
                    },
                    error: function(errorThrown){
                        $(".alert-danger").show().text("Data could not be loaded."+errorThrown);
                    }
                });
            });
            
            function addUser(){
                var username = $("#usernameInput").val();
                var password = $("#passwordInput").val();
                var confirmPassword = $("#passwordInput").val();
                var firstName = $("#firstNameInput").val();
                var lastName = $("#lastNameInput").val();
                var address = $("#addressInput").val();
                var birthNumber = $("#birthNumberInput").val();
                var role = $("#userRoleInput").val();
                var idOffice = $("#officeIdInput").val();
                $.ajax({
                    type: "POST",
                    data: JSON.stringify({"role": role, "confirmPassword": confirmPassword, "idOffice": idOffice,"username": username,"password": password,"firstName": firstName,"lastName": lastName,"address": address,"birthNumber": birthNumber}),
                    contentType: "application/json",
                    url: "http://localhost:8080/pa165/rest/users",
                    success: function(){
                        window.location.href='/pa165/client/user';
                    },
                    error: function(xhr){   
                      if (xhr.status === 400) {
                          $(".alert-danger").show().text("Fields can not be blank." &times);
                      }
                      else if (xhr.status === 404) {
                          window.location.href='/pa165/client/404';
                      }
                      else
                          $(".alert-danger").show().text("User already exists.");
                    },
                    fail: function(errorThrown){
                        $(".alert-danger").show().text("User couldn't be created."+errorThrown);
                    }                    
                });
            };   
        </script>
    </jsp:attribute>
</custom:layout>
