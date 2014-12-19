<%-- 
    Document   : user-form
    Created on : 12.12.2014, 16:41:58
    Author     : Karolina Burska
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" session="false"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<custom:layout title="Edit user">
    <jsp:attribute name="content">
        <div class="alert alert-danger alert-dismissable" style="visibility:hidden;">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
                &times;
            </button>
        </div>
        <form class="form-horizontal">
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
                    <input id="lastNameInput" ype="text" name="lastName" class="form-control"/>
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
            <div class="col-sm-offset-2 col-sm-10">                
                <button onclick="editUser()" type="button" class="btn btn-success">Edit user</button>
                <button type="button" class="btn btn-default" onclick="window.location.href='/pa165/client/user'">Cancel</button>
            </div>
        </form>
    </jsp:attribute>
    <jsp:attribute name="ajaxScript">
        <script type="text/javascript">
            urlId = document.URL.substring(0,url.lastIndexOf("/edit"));
            var ID = urlId.substring(urlId.lastIndexOf("/")+1);
            
            $(document).ready(function(){
                $(".alert-danger").hide();
                $.ajax({
                    type: "GET",
                    dataType:"json",
                    url: "http://localhost:8080/pa165/rest/offices",
                    success: function(data){
                        var sel = $("#officeIdInput");
                        $.each(data,function(i,data) {   
                            var opt = document.createElement('option');
                            opt.innerHTML = data.address;
                            opt.value = data.id;
                            sel.append(opt);
                        });
                    },
                    error: function(errorThrown) {
                        alert(JSON.stringify(errorThrown));
                    }
                });
                
                $.ajax({
                    type: "GET",
                    dataType:"json",
                    url: "http://localhost:8080/pa165/rest/users/"+ID,
                    success: function(data){
                        $("#firstNameInput").val(data.firstName);
                        $("#lastNameInput").val(data.lastName);
                        $("#birthNumberInput").val(data.birthNumber);
                        $("#addressInput").val(data.address);
                        $("#officeIdInput").val(data.office.id);
                    },
                    error: function(errorThrown) {
                        alert(JSON.stringify(errorThrown));
                    }
                });
            });
            
            function editUser(){
                var username = $("#usernameInput").val();
                var password = $("#passwordInput").val();
                var firstName = $("#firstNameInput").val();
                var lastName = $("#lastNameInput").val();
                var address = $("#addressInput").val();
                var birthNumber = $("#birthNumberInput").val();
                var idOffice = $("#officeIdInput").val();
                
                $.ajax({
                    type: "PUT",
                    data: JSON.stringify({"idOffice": idOffice,"username": username,"password": password,"firstName": firstName,"lastName": lastName,"address": address,"birthNumber": birthNumber}),
                    contentType: "application/json",
                    url: "http://localhost:8080/pa165/rest/users/"+ID,
                    success: function(){
                        window.location.href='/pa165/client/user';
                    },
                    error: function(xhr){
                        alert(JSON.stringify(xhr));
                      if (xhr.status === 400)
                          $(".alert-danger").show().append("1");
                      if (xhr.status === 409)
                          $(".alert-danger").show().append("2");
                      if (xhr.status === 404)
                          window.location.href='/pa165/client/404';
                    },
                    fail: function(errorThrown){
                        $(".alert-danger").show().append("User couldn't be created."+errorThrown);
                    }                    
                });
            };   
        </script>
    </jsp:attribute>
</custom:layout>
