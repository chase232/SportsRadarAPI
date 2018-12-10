<%@ include file="/WEB-INF/layouts/include.jsp"%>
<c:url var="linkToHome" value="/" />

<div class="container-error">
	<h1><spring:message code="error.header" /></h1>
	<p><spring:message code="error.body" arguments="${linkToHome}" /></p>
</div>
