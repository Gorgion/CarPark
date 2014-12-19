<%-- 
    Document   : error
    Created on : 22.11.2014, 16:30:58
    Author     : Jiri Dockal
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" session="false"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<custom:error title="400">
    <jsp:attribute name="content">
        <div class="error">        
            <h2>400 Bad request</h2><br/>
            <p>The request could not be understood by the server due to malformed syntax.</p>
        
        <button type="button" class="btn btn-warning" onclick="window.location.replace('/pa165/client')">Return to homepage</button>
        </div>
    </jsp:attribute>
</custom:error>  
 
