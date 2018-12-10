<%@ include file="/WEB-INF/layouts/include.jsp"%>
<div class="container">
<h1><spring:message code="login.form" /></h1>

<div class="well">
<form id="loginForm" name="loginForm" action="<c:url value='/loginProcess' />" method="post">
<div class="row">
<div class="form-group col-sm-4">
    <label class="form-label"><spring:message code="login.username" /></label>
    <input id="user" name="user" class="form-control" 
        data-dojo-type="dijit/form/ValidationTextBox" 
        data-dojo-props="regExp:'[\\w]+'" />
    <label class="form-label"><spring:message code="login.password" /></label>
    <input id="pass" name="pass" class="form-control" 
        data-dojo-type="dijit/form/ValidationTextBox" 
        data-dojo-props="regExp:'[\\w]+'" />
</div>
</div>
<button class="btn btn-primary" id="submit" type="submit" 
    data-dojo-type="oreilly/types/form/Button" 
    data-dojo-props="spinOnClick: true"><spring:message code="login.submit" /></button>
</form>
</div>
</div>