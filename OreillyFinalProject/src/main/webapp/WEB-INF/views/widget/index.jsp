<%@ include file="/WEB-INF/layouts/include.jsp"%>

<h1>SPORTRADAR API NCAAM</h1>
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
<div id="message"></div>
	<div class="row">
		<div class="col-sm-3">
			<label class="control-label">Please enter in a valid phone number: </label>
		</div>
		<div class="col-sm-3">
			<div class="form-group">			
			   <input class="form-control" 
			   			id="phoneNumber"
			   			placeholder="+15736945653"
        				data-dojo-type="dijit/form/ValidationTextBox" 
        				<%-- data-dojo-props="regExp:'[^[0][1-9]\d{9}$|^[1-9]\d{9}$]'" --%>/> 
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
			var button = registry.byId("checkedButton");			
			var alertManager = registry.byId('alertManager');
			
			request('<c:url value="/finalproject/getGames"/>').then(function(data) {
				grid.store.setData(JSON.parse(data));
				grid.refresh();
			}, function(err) {
				console.log("Error: " + err);
			});
			grid.refresh();
			// Button Event
	        button.on('click',function() {
	        	   var phoneNumber = registry.byId("phoneNumber").value;
	               button.stopSpinner();
	               let btn = this;
	               let checkedArray = grid.getChecked();
	               let output = dom.byId("output");
	               var textInformation = "";
	               var ids = "";
	               for (var i = 0; i < checkedArray.length; i++) {
	            	   ids += checkedArray[i].eventID + ", ";
	            	   textInformation += "Event ID: " + checkedArray[i].eventID + "\n Game: "
	                                     + checkedArray[i].game + "\n ";
	               }           
	               output.innerHTML = textInformation;
	               
	               // Load the Table after the DOM is ready
				   request('<c:url value="/finalproject/text" />', {
				   		method : 'POST',
						handleAs : "json",
						data : {
								'phoneNumber' : phoneNumber,
								'textInformation' : textInformation
							}
						}).then(function(data) {
							var msg;
							console.log(data);
							let json = JSON.parse(data);
							btn.stopSpinner();
							if (json.error == false) {
								msg = (json.successMessage.length > 0) ? json.message : "Text Sent Successfully to " + 
										+ phoneNumber + " with Ids " + ids;
								alertManager.addSuccess({message: msg, position: 'message'});
							} else {
								msg = (json.errorMessage == '' || json.errorMessage == "undefined") ? 'Unknown Exception' : json.errorMessage;
								alertManager.addError({message: msg, position: 'message'});
							}
						}, function(err) {
							console.log("Error: " + err);
						}, function(err) {
							button.stopSpinner();
							console.log("Error: " + err);
					});
				});
			});
 	});
</script>