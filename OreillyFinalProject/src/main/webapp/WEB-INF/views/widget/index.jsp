<%@ include file="/WEB-INF/layouts/include.jsp"%>

<h1>SPORTRADAR API NCAAM</h1>

<%-- 	<div>
		<b>Service: </b>${service}
	</div>
	<div>
		<b>Service URI: </b>${request}
	</div>
 	<div>
		<b>Service: </b>
		<div>${response }</div>
	</div>  --%>

<div>Description will go here</div>

<div class="row">
	<div data-dojo-id="widgetSport" data-dojo-type="dojo/store/Memory"
		data-dojo-props="data: [], idProperty: 'eventID'"></div>

	<div id="table-container4" class="span12">
		<table id="sport" class="table table-striped table-bordered"
			data-dojo-type="oreilly/types/dgrid/PagingGrid"
			data-dojo-props="store: widgetSport, loadDataOnStartup: true">
			<thead>
				<tr>
					<th class="hyperlink" data-dgrid-column='{field:"eventID", name:"eventID"}'>Event ID</th>
					<th class="hyperlink" data-dgrid-column='{field:"dateTime", name:"dateTime"}'>Event Date</th>
					<th class="hyperlink" data-dgrid-column='{field:"eventKey", name:"eventKey"}'>Event Location</th>
					<th class="hyperlink" data-dgrid-column='{field:"eventValue", name:"eventValue"}'>Teams</th>
			</thead>
		</table>
	</div>
</div>
<script>
 	require([ 'dojo/request', 'dijit/registry', 'dojo/ready' ], function(
			request, registry, ready) {
		ready(function() {
			var grid = registry.byId("sport");
			var store = registry.byId("widgetSport");
			request('<c:url value="/finalproject/getGames"/>').then(function(data) {
				grid.store.setData(JSON.parse(data));
				grid.refresh();
			}, function(err) {
				console.log("Error: " + err);
			});
			grid.refresh();
		});
	}); 
</script>