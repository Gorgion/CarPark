<%-- 
    Document   : delete-dialog
    Created on : 21.11.2014, 20:25:59
    Author     : Tomas Svoboda
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@tag description="general confirm delete dialog" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="key" required="true"%>

<%-- any content can be specified here e.g.: --%>
<div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel"><fmt:message key="confirm.delete.title"/></h4>
            </div>
            <div class="modal-body">
                <p>
                    <fmt:message key="confirm.delete.body">
                        <fmt:param value="${key}" />
                    </fmt:message>
                </p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal" id="confirm-btn"><fmt:message key="btn.delete" /></button>
                <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="btn.cancel" /></button>
            </div>
        </div>
    </div>
</div>