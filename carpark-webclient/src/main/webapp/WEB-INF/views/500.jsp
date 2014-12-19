<%-- 
    Document   : error
    Created on : 22.11.2014, 16:30:58
    Author     : Jiri Dockal
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" session="false"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<custom:error title="500">
    <jsp:attribute name="content">
        <div class="error">   
            <h2>500 Internal server error</h2><br/>
            <p>A generic error message, given when an unexpected condition was encountered and no more specific message is suitable.</p>
        <button type="button" class="btn btn-warning" onclick="window.location.replace('/pa165/client')">Return to homepage</button>
        </div>
    </jsp:attribute>
</custom:error>
 
