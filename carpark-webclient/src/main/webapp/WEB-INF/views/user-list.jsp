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
            function listUsers(){
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
                            .append($('<td/>').text(user.id))
                            .append($('<td/>').text(user.firstName))
                            .append($('<td/>').text(user.lastName))
                            .append($('<td/>').text(user.address));

                            actRow.append($('<td/>').append(
                                "<a href=http://localhost:8085/pa165/client/user/"+user.id+"/edit class='btn btn-info' style='margin-right: 4px'><span class='glyphicon glyphicon-edit' /></a>"+
                                "<button type='button' onclick='deleteUser("+user.id+")' name='delete' class='btn btn-danger'><span class='glyphicon glyphicon-remove' /></button>"  
                            ));
                        });
                        
                        table.append("</tbody></table>");
                    },
                    error: function(errorThrown){       
                        window.location.replace("/pa165/client/500");
                    }
                });
            }
            $(document).ready(function(){
                $(function()
                {
                   listUsers(); 
                });
            });
            
            function deleteUser(id) {
                $.ajax({
                    type: "DELETE",
                    url: "http://localhost:8080/pa165/rest/users/"+id,
                    success: function(){
                        $(function ()
                        {   $('table').remove();
                            listUsers();
                        });
                        $(".alert-success").show().text("User with id "+id+" was deleted.");
                    },  
                    error: function(errorThrown){
                        window.location.replace("/pa165/client/500");
                    }
                    
                });
            };
        </script>
    </jsp:attribute>
</custom:layout>
