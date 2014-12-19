<%-- 
    Document   : error
    Created on : 22.11.2014, 16:30:58
    Author     : Jiri Dockal
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" session="false"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<custom:error title="404">
    <jsp:attribute name="content">
        <div class="error">        
            <h2>404 Page not found</h2><br/>
            <p>The requested resource could not be found but may be available again in the future. Subsequent requests by the client are permissible.</p>
        
        <button type="button" class="btn btn-warning" onclick="window.location.replace('/pa165/client')">Return to homepage</button>
        </div>
    </jsp:attribute>
</custom:error>    
 
