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
        <div class="alert alert-danger alert-dismissable" style="display:none;">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
                &times;
            </button>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">Address:</label>
            <div class="col-sm-5">
                <input type="text" name="address" cssClass="form-control"/>
                    <p class="help-block">Address&nbsp;</p>

            </div>
        </div>
        <div class="col-sm-offset-2 col-sm-10">                
            <button onclick="addOffice()" type="button" class="btn btn-success">Add</button>
            <button type="button" class="btn btn-default" onclick="window.location.href='/pa165/client/office'">Cancel</button>
        </div>
     </jsp:attribute>
    <jsp:attribute name="ajaxScript">
        <script type="text/javascript">
            function addOffice(){
                /*var spinner = getSpinner();
                $.ajax({
                    type: "POST",
                    dataType:"json",
                    url: "http://localhost:8080/pa165/rest/offices"+,
                    success: function(data){
                        spinner.remove();
                    }
                });*/
                alert("add ");
            };
            
        </script>
    </jsp:attribute>
</custom:layout>           

