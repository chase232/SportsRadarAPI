<!DOCTYPE html>
<%@ include file="/WEB-INF/layouts/include.jsp"%>
<html lang="en">

<head>
	<fmt:setBundle basename="app" var="app" />
	<fmt:message key="jenkins.job" bundle="${app}" var="job"/>
	<fmt:message key="jenkins.buildNumber" bundle="${app}" var="buildNumber"/>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="job" content="${job}" />
	<meta name="buildNumber" content="${buildNumber}" />
	
	<title><tiles:getAsString name="title" /></title>

	<fmt:setBundle basename="global" var="global"/>



	<c:set value="/resources/css/oreillybs-3.3.5r2.min.css" var="bootstrapUrl"/>
	<c:set value="/resources/css/oreillybs-dojo-r13.min.css" var="dojoOverrideUrl"/>

	<!-- Bootstrap import - Check https://ui.oreillyauto.com/ui to make sure you have the latest! -->
	<link rel="stylesheet" href="<c:url value='${bootstrapUrl}' />">

	<!--  Dojo Overrides for Bootstrap -->
	<link rel="stylesheet" href="<c:url value='${dojoOverrideUrl}' />">
	
	<!-- Project CSS import -->
	<link type="text/css" href="<c:url value='/resources/css/master.css?build=${buildNumber}' />" rel="stylesheet" />

	<c:url value="/resources/js/" var="dojoUrl"/>
	
	<script type="text/javascript">
		var dojoConfig = {
				async: true,
				dojoBlankHtmlUrl: '${dojoUrl}dojo/resources/blank.html',
				packages: [
					{ name: "dojo", location: "${dojoUrl}dojo" },
					{ name: "dijit", location: "${dojoUrl}dijit" },
					{ name: "oreilly", location: "${dojoUrl}oreilly" },
					{ name: "dgrid", location: "${dojoUrl}dgrid" },
					{ name: "dojox", location: "${dojoUrl}dojox" },
					{ name: "bootstrap", location: "${dojoUrl}bootstrap" },
					{ name: "put-selector", location: "${dojoUrl}put-selector" },
					{ name: "xstyle", location: "${dojoUrl}xstyle" },
					{ name: "dbind", location: "${dojoUrl}dbind" },
					{ name: "dstore", location: "${dojoUrl}dstore" },
					{ name: "local", location: "<c:url value='/resources/js/localDojo'/>"} // Rename this to your local Dojo module folder
				],
				cacheBust: '${buildNumber}',
				locale: 'en',
				deps: ["dojo/parser"],
				parseOnLoad: true
		};
	</script>
	<script src="${dojoUrl}dojo/dojo-common.js?build=${buildNumber}"></script>

</head>

<body class="tundra">
	<!--  Header  -->
	<%-- <tiles:insertAttribute name="header" /> --%>
	
	<div id="bodyContentTile" class="container">
		<!-- <div class="row"> -->
<%-- 			<div class="col-sm-2">
				<!--  Menu?  -->
				<tiles:insertAttribute name="menu" />
			</div> --%>
			<div class="col-sm-10">
				<div id="alertManager" data-dojo-id="alertManager" data-dojo-type="oreilly/types/ui/AlertManager"></div>
				<tiles:insertAttribute name="body" />
			</div>
		<!-- </div> -->
	</div>
	<!--  Footer  -->
	<%-- <tiles:insertAttribute name="footer" /> --%>
</body>

</html>
