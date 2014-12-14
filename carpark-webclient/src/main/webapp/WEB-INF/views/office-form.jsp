<%-- 
    Document   : office-form
    Created on : 12.12.2014
    Author     : Jiri Dockal
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" session="false"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<custom:layout title="Add office">    
    <jsp:attribute name="content">
        <div class="alert alert-danger alert-dismissable" style="visibility:hidden;">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
                &times;
            </button>
        </div>
        <form class="form-horizontal">
            <div class="form-group">
                <label class="col-sm-2 control-label">Address:</label>
                <div class="col-sm-5">
                    <input id="addressInput" type="text" name="address" class="form-control"/>
                        <div class="help-block">Address&nbsp;</div>

                </div>
            </div>
            <div class="col-sm-offset-2 col-sm-10">                
                <button onclick="addOffice()" type="button" class="btn btn-success">Add</button>
                <button type="button" class="btn btn-default" onclick="window.location.href='/pa165/client/office'">Cancel</button>
            </div>
        </form>
     </jsp:attribute>
    <jsp:attribute name="ajaxScript">
        <script type="text/javascript">
            function addOffice(){
                var spinner = getSpinner();
                
                var address = $("#addressInput").val();
                $.ajax({
                    type: "POST",
                    data: JSON.stringify({"address": address}),
                    //dataType: 'json',
                    contentType: "application/json",
                    url: "http://localhost:8080/pa165/rest/offices",
                    success: function(){
                        window.location.href='/pa165/client/office';
                    },
                    error: function(xhr,status,error){
                      if (xhr.status == 400)
                          $(".alert-danger").show().append("Address can't be blank!");
                      if (xhr.status == 409)
                          $(".alert-danger").show().append("Office already exists!");
                      if (xhr.status == 404)
                          window.location.href='/pa165/client/404';
                    },
                    fail: function(xhr,textStatus,errorThrown){
                        $(".alert-danger").show().append("Office couldn't be created because of:\n."+errorThrown);
                    },   
                    complete: function(){
                        spinner.remove();
                    }
                    
                });
            };
            
        </script>
    </jsp:attribute>
</custom:layout>           

