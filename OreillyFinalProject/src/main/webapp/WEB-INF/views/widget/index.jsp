<%@ include file="/WEB-INF/layouts/include.jsp"%>
<style>
	body {
		background-image: url("basketball.jpg");
		background: basketball.jpg;
		background-color: #3071A9;
	}
	h1 {
		color: #FB0007;
		padding: 10px;
		font-size: 28px;
	}
	.container {
		background-color: #cccccc;
	}
	#description {
		font-size: 14px;
	}
	label, button{
		font-size: 16px;
	}
	
	#phoneNumber, #gameDate, input {
		font-size:  16px;
	}
	
</style>
<body>
<div class="container">
<h1>SPORTRADAR API NCAAM</h1>
<div class="well">
	<div class="row">
		<div class="col-lg-12">
			<div>
				<h2>Description</h2>
					<div id="description">This webpage will return data from the sportradar API. The data that will be returned is a daily schedule from
				 		a specific date in NCAAM Basketball. 
					</div>
			</div>
		</div>
</div> 	
</div>
<div id="message"></div>	
	<div class="row">
		<div class="col-sm-4">
			<label class="control-label">Please enter in a valid phone number </label>
		</div>	
        <div class="col-sm-3"></div>
	   		<label class="form-label col-sm-3" for="startDate">Pick a date to view schedule</label>
	</div>
	<div class="row">
		<div class="col-sm-2">
			<div class="form-group">			
			   <input class="form-control" 
			   			id="phoneNumber"
			   			placeholder="+15736945653"
        				data-dojo-type="dijit/form/ValidationTextBox" 
        				<%-- data-dojo-props="regExp:'[^[0][1-9]\d{9}$|^[1-9]\d{9}$]'" --%>/> 
			</div>
		</div>
		<div class="col-sm-5"></div>
		<div class="form-group">
				<fieldset>							
				    <div class="col-sm-3">
					    <input type="date" id="gameDate" name="gameDate"
							               value="2018-12-15"
							               min="2018-10-01" max="2019-03-15" />
					</div>							
				</fieldset>
		</div>
	</div>
	<div class="row">	
			<div class="col-sm-2">
	            <button class="btn btn-primary"
	             		data-dojo-id="formSubmit"
	                    data-dojo-type="oreilly/types/form/Button"
	                    data-dojo-props="spinOnClick: true" id="checkedButton" type="submit">
	                    Send
	            </button>
       		</div>
       		<div class="col-sm-5"></div>
       		<div class="col-sm-2">
	            <button class="btn btn-primary"
	             		data-dojo-id="setDate"
	                    data-dojo-type="oreilly/types/form/Button"
	                    data-dojo-props="spinOnClick: true" id="setDate" type="submit">
	                    Set Date
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
</div>
</body>
<script>
 	require([ 'dojo/request', 
 		'dijit/registry', 
 		'dojo/ready',
 		'dojo/dom'], function(request, registry, ready, dom) {
		ready(function() {
			var grid = registry.byId("sport");
			var store = registry.byId("widgetSport");
			var buttonText = registry.byId("checkedButton");
			var buttonDate = registry.byId("setDate");
			var alertManager = registry.byId('alertManager');
			
			request('<c:url value="/finalproject/getGames"/>').then(function(data) {
				console.log(data);
				grid.store.setData(JSON.parse(data));
				grid.refresh();
			}, function(err) {
				console.log("Error: " + err);
			});
			grid.refresh();
			// Button Event
	        buttonText.on('click',function() {
	        	   var phoneNumber = registry.byId("phoneNumber").value;
	        	   buttonText.stopSpinner();
	               let btn = this;
	               let checkedArray = grid.getChecked();
	               let output = dom.byId("output");
	               var textInformation = "";
	               var ids = "";
	               for (var i = 0; i < checkedArray.length; i++) {
	            	   checkedArray[i].enable = false;
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
							alertManager.hide();
							var msg;
							console.log(data);
							let json = JSON.parse(data);
							btn.stopSpinner();
							if (json.error == false) {							
								msg = "Text Sent Successfully to " + phoneNumber + " with Ids " + ids;
								alertManager.addSuccess({message: msg, position: 'message', hide : false});
								alertManager.addError({ hide : true});
							} else {								
								msg = (json.errorMessage == '' || json.errorMessage == "undefined") ? 'Unknown Exception' : json.errorMessage;
								alertManager.addError({message: msg, position: 'message' , hide : false});
								alertManager.addSuccess({ hide : true});
							}
						}, function(err) {
							console.log("Error: " + err);
						}, function(err) {
							buttonText.stopSpinner();
							console.log("Error: " + err);
					});
				});
			
	        buttonDate.on('click',function() {
	        	buttonDate.stopSpinner();
	        	let btn = this;
	        	var date = document.getElementById("gameDate").value;
	        	console.log(date);
	        	date = new Date(date);
				var dd = date.getDate() + 1;
				var mm = date.getMonth() + 1;
				var y = date.getFullYear();
				date = y + '/'+ mm + '/'+dd;
				var newDate = y + '-'+ mm + '-'+dd;
	        	console.log(date);
	        	// Load the Table after the DOM is ready
				   request('<c:url value="/finalproject/postDate" />', {
				   		method : 'POST',
						handleAs : "json",
						data : {
								'dateOne' : date,
								'dateTwo' : newDate
							}
						}).then(function(data) {
							//grid.store.setData(JSON.parse(data));
							grid.store.setData(data);
							grid.refresh();
							var msg;
							console.log(data);
							let json = JSON.parse(data);
							btn.stopSpinner();
	        			});
				   /* request('<c:url value="/finalproject/getGamesByDate" />', {
					    method : 'POST',
						handleAs : "json",
						data : {
								'date' : newDate
							}
				   }).then(function(data) {
						grid.store.setData(JSON.parse(data));
						grid.refresh();
					}, function(err) {
						console.log("Error: " + err);
					});
					grid.refresh(); */
			});
		});
 	});
</script>

















