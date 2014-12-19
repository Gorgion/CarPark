<%-- 
    Document   : error
    Created on : 22.11.2014, 16:30:58
    Author     : Jiri Dockal
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" session="false"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<custom:error title="400">
    <jsp:attribute name="content">
        <div class="error">  
            <h2>400 <fmt:message key="error.400.head" /></h2>
            <p><fmt:message key="error.xxx.body" /><fmt:message key="error.400.body" /></p>
        <button type="button" class="btn btn-warning" onclick="history.back()"><fmt:message key="btn.back" /></button>
        </div>
    </jsp:attribute>
</custom:error> 
 
