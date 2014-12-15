<%-- 
    Document   : user-list
    Created on : 12.12.2014, 16:41:46
    Author     : Karolina Burska
--%>
 
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" session="false"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<custom:layout title="Users">
    <jsp:attribute name="content">
        <div id="tableContent" class="row">
            <a href="/pa165/client/user/add" class="btn btn-success">Add user</a>
            <hr class="divider" />
            <custom:delete-dialog key="user"></custom:delete-dialog>
            <custom:modal-dialog dialogId="managerDetails" dialogTitleKey="managerDetails.title">
                <div class="form-horizontal">
                    
                </div>
            </custom:modal-dialog>
        </div>
    </jsp:attribute>
    <jsp:attribute name="ajaxScript">
        <script type="text/javascript">    
            $(document).ready(function(){
                var spinner = getSpinner();
                $.ajax({
                    type: "GET",
                    dataType:"json",
                    url: "http://localhost:8080/pa165/rest/users",
                    success: function(data){
                        var table = $(

                            "<table class=\"table table-hover\">\n"+
                            "    <thead>\n"+
                            "        <tr>\n"+
                            "            <th>ID</th>\n"+
                            "            <th>First name</th>\n"+
                            "            <th>Last name</th>\n"+
                            "            <th>Address</th>\n"+
                            "            <th></th>\n"+
                            "        </tr>\n"+
                            "    </thead>\n"+
                            "    <tbody>"
                        ).appendTo($('#tableContent'));
                        $.each(data, function(i, user){
                            var actRow = $('<tr/>').appendTo(table)
                            .append($('<td/>').text(user.Id))
                            .append($('<td/>').text(user.firstName))
                            .append($('<td/>').text(user.lastName))
                            .append($('<td/>').text(user.address));

                            actRow.append($('<td/>').append(
                                "<a href="+"http://localhost:8085/pa165/client/user/"+user.Id+"/edit"+" class='btn btn-info'><span class='glyphicon glyphicon-edit' /></a>"+
                                "<form action='http://localhost:8085/pa165/client/user/delete' method='GET' class='form-inline' style='display: inline-block;'>"+
                                "    <button type='submit' onclick='deleteUser("+user.Id+")' name='delete' class='btn btn-danger'><span class='glyphicon glyphicon-remove' /></button>"+
                                "</form>"     
                            ));
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
            
            function deleteUser(id)
            {
                var spinner = getSpinner();
                $.ajax({
                    type: "DELETE",
                    dataType:"json",
                    url: "http://localhost:8080/pa165/rest/users/"+id,
                    success: function(){
                        $(".alert-success").show().append("User with id "+id+" was deleted.");
                        spinner.remove();
                    },  
                    error: function(errorThrown){
                        spinner.remove();
                        $(".alert-danger").show().append("User with id "+id+" couldn't be deleted. "+errorThrown);
                    }
                    
                });
            };
        </script>
    </jsp:attribute>
</custom:layout>
