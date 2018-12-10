<%@ include file="/WEB-INF/layouts/include.jsp" %>

<div class="well">
<div class="row">
	<form id="loginForm" name="loginForm" action="<c:url value='/loginProcess'/>" method="POST">
		<input type="text" name="username" id="username"/>
		<input type="password" name="password" id="password"/>
		<button type="submit">Submit</button>
	</form>
</div>
</div>
