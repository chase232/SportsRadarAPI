<%@ include file="/WEB-INF/layouts/include.jsp"%>
<h1>Sodas Ajax Style</h1>
<div class="row">
	<div data-dojo-id="widgetSoda"
		data-dojo-type="dojo/store/Memory"
		data-dojo-props="data: [], idProperty: 'id'">
	</div>
	
	<div id="table-container4" class="span12">
		<table id="sodas"
			class="table table-striped table-bordered"
			data-dojo-type="oreilly/types/dgrid/PagingGrid"
			data-dojo-props="store: widgetSoda, loadDataOnStartup: true">
			
			<thead>
				<tr>
					<th class="hyperlink" data-dgrid-column='{field:"id", name:"id"}'>Soda ID</th>
					<th class="hyperlink" data-dgrid-column='{field:"name", name:"name"}'>Name</th>
					<th class="hyperlink" data-dgrid-column='{field:"color", name:"color"}'>Color</th>
					<th class="hyperlink" data-dgrid-column='{field:"brand", name:"brand"}'>Soda ID</th>
			</thead>
		</table>
	</div>
</div>
<script>
	require([
		'dojo/request',
		'dijit/registry',
		'dojo/ready'
	],
	function(request, registry, ready){
		ready(function(){
			var grid = registry.byId("sodas");
			var store = registry.byId("widgetSoda");
			request('<c:url value="/widgets/getSodas"/>').then(function(data){
				grid.store.setData(JSON.parse(data));
				grid.refresh();
			}, function(err){
					console.log("Error: " + err);
			});
			grid.refresh();
		});
	});
</script>






