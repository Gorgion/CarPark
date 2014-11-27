<%-- 
    Document   : modal-dialog
    Created on : 21.11.2014, 22:30:34
    Author     : Tomas Svoboda
--%>

<%@tag description="custom modal dialog" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="dialogId" required="true"%>
<%@attribute name="dialogTitleKey" required="true"%>

<%-- any content can be specified here e.g.: --%>
<div class="modal fade" id="${dialogId}" tabindex="-1" role="dialog" aria-labelledby="${dialogId}ModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="${dialogId}ModalLabel"><fmt:message key="${dialogTitleKey}"/></h4>
            </div>
            <div class="modal-body">
                <jsp:doBody />
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="btn.close" /></button>
            </div>
        </div>
    </div>
</div>