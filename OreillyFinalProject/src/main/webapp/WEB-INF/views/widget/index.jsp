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
<div class="well">
	<div class="row">
	<div>
		<h3>Description</h3>
			<div>This webpage will return data from the sportradar API. The data that will be returned is a daily schedule from
		 		a specific date in NCAAM Basketball. 
			</div>
	</div>
</div> 	
</div>
<form method="post" id="contactUsForm"
		action="<c:url value='/finalproject/text'/>">
	<div class="row">
		<div class="col-sm-3">
			<label class="control-label">Please enter in a valid phone number: </label>
		</div>
		<div class="col-sm-3">
			<div class="form-group">			
			   <input class="form-control" 
        				data-dojo-type="dijit/form/ValidationTextBox" 
        				data-dojo-props="regExp:'[\\w]+'" />
			</div>
		</div>
       <div class="col-sm-3">
             <button class="btn btn-primary"
             		data-dojo-id="formSubmit"
                    data-dojo-type="oreilly/types/form/Button"
                    data-dojo-props="spinOnClick: true" id="checkedButton" type="submit">
                    Send
             </button>
       </div>
	</div>	
</form> 	

<div class="row">
	<div class="col-sm-12">
    	<div class="mt10" id="objectMapperTable">
			<div data-dojo-id="widgetSport" data-dojo-type="dojo/store/Memory"
				data-dojo-props="data: [], idProperty: 'eventID'">
			</div>

			<div id="objectMapperGrid" class="span12">
				<table id="sport" class="table table-striped table-bordered"
					data-dojo-type="oreilly/types/dgrid/PagingGridCheckBox"
					data-dojo-props="store: widgetSport, loadDataOnStartup: true">
					<thead>
						<tr>
							<th class="hyperlink" data-dgrid-column='{field:"eventID", name:"eventID"}'>Event ID</th>					
							<th class="hyperlink" data-dgrid-column='{field:"eventType", name:"eventType"}'>League</th>
							<th class="hyperlink" data-dgrid-column='{field:"dateTimeString", name:"dateTimeString"}'>Event Date</th>
							<th class="hyperlink" data-dgrid-column='{field:"eventLocation", name:"eventLocation"}'>Event Location</th>
							<th class="hyperlink" data-dgrid-column='{field:"game", name:"game"}'>Teams</th>
					</thead>
				</table>
			</div>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-sm-6"><div id="output"></div></div>
</div>
<script>
 	require([ 'dojo/request', 
 		'dijit/registry', 
 		'dojo/ready',
 		'dojo/dom'], function(request, registry, ready, dom) {
		ready(function() {
			var grid = registry.byId("sport");
			var store = registry.byId("widgetSport");
			let button = registry.byId("checkedButton");
			
            //var alertManager = registry.byId('alertManager');
			request('<c:url value="/finalproject/getGames"/>').then(function(data) {
				grid.store.setData(JSON.parse(data));
				grid.refresh();
			}, function(err) {
				console.log("Error: " + err);
			});
			grid.refresh();
			// Button Event
	        button.on('click',function() {
	               button.stopSpinner();
	               let btn = this;
	               let checkedArray = grid.getChecked();
	               let html = "IDs Selected: ";
	               let output = dom.byId("output");              
	               for (var i = 0; i < checkedArray.length; i++) {
	                      html += "{id:" + checkedArray[i].eventID + " name:"
	                                     + checkedArray[i].game + "} ";
	               }           
	               output.innerHTML = html;
	        },
	        function(err) {
	               button.stopSpinner();
	               console.log("Error: "+ err);
	        });
		});
	}); 
</script>