<%@ include file="/WEB-INF/layouts/include.jsp"%>
<body>
	<div class="container">
		<div class="row">
			<div class="col-sm-4 header">
				<img
					src="http://www.bonus.org.uk/wp-content/uploads/2015/10/Sportradar.png"
					alt="srLogo" width="480px" height="260px">
			</div>
		</div>
		<div class="well">
			<div class="row">
				<div class="col-sm-1">
					<img
						src="https://cdn.freebiesupply.com/images/large/2x/nba-logo-transparent.png"
						alt="logo" width="80px" height="180px">
				</div>
				<div class="col-sm-11">
					<div>
						<h2>Description</h2>
						<div id="description">This webpage will return data from the
							sportradar API. The data that will be returned is a daily
							schedule from a specific date in the NBA. Change the date with
							the date selector below to view the game schedule for that date.
							Check the check boxes of a row or rows to send information via
							text to a valid number. Ensure the number has a +1 at the
							beginning followed by ten digits with no spaces or dashes. 
							The send button will then send the selected check
							boxes to the number. Rows can only be sent once.</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div id="messageSuccess" data-dojo-id="messageSuccess" 
    				data-dojo-type="oreilly/types/ui/AlertManager"
    				data-dojo-props="scroll:true"></div>
    			<div id="messageFail" data-dojo-id="messageFail" 
    				data-dojo-type="oreilly/types/ui/AlertManager"
    				data-dojo-props="scroll:true"></div>
    			<div id="messageWarning" data-dojo-id="messageWarning" 
    				data-dojo-type="oreilly/types/ui/AlertManager"
    				data-dojo-props="scroll:true"></div>
			</div>			
		</div>
		<div id="message" data-dojo-id="message" 
    			data-dojo-type="oreilly/types/ui/AlertManager"
    			data-dojo-props="scroll:true"></div>
		<div class="row">
			<div class="col-sm-5">
				<div class="row">
					<label class="control-label col-sm-9">Please enter in a
						valid phone number </label>
				</div>
				<div class="row">
					<div class="form-group col-sm-6">
						<input class="form-control" id="phoneNumber"
							placeholder="+15736945653"
							data-dojo-type="dijit/form/ValidationTextBox" <%-- data-dojo-props="regExp:'[^[0][1-9]\d{9}$|^[1-9]\d{9}$]'" --%>/>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-2">
						<button class="btn btn-primary" data-dojo-id="formSubmit"
							data-dojo-type="oreilly/types/form/Button"
							data-dojo-props="spinOnClick: false" id="checkedButton"
							type="submit">Send</button>
					</div>
				</div>
			</div>
			<div class="col-sm-7">
				<div class="row">
					<label class="form-label col-sm-8" for="startDate">Pick a date to view schedule</label>
				</div>
				<div class="row">
					<div class="form-group">
						<fieldset>
							<div class="col-sm-4">
								<input type="date" id="gameDate" name="gameDate"
									value="2018-12-16" min="2018-08-01" max="2019-07-31" />
							</div>
						</fieldset>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-2">
						<button class="btn btn-primary" data-dojo-id="setDate"
							data-dojo-type="oreilly/types/form/Button"
							data-dojo-props="spinOnClick: false" id="setDate" type="submit">
							Set Date</button>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div class="mt10" id="objectMapperTable">
					<div data-dojo-id="widgetSport" data-dojo-type="dojo/store/Memory"
						data-dojo-props="data: [], idProperty: 'eventID'"></div>

					<div id="objectMapperGrid" class="span12">
						<table id="sport" class="table table-striped table-bordered"
							data-dojo-type="oreilly/types/dgrid/PagingGridCheckBox"
							data-dojo-props="store: widgetSport, loadDataOnStartup: true">
							<thead>
								<tr>
									<th class="hyperlink col1"
										data-dgrid-column='{field:"eventID", name:"eventID"}'>Event ID</th>
									<th class="hyperlink col2"
										data-dgrid-column='{field:"eventType", name:"eventType"}'>League</th>
									<th class="hyperlink col3"
										data-dgrid-column='{field:"dateTimeString", name:"dateTimeString"}'>Event Date</th>
									<th class="hyperlink col4"
										data-dgrid-column='{field:"eventLocation", name:"eventLocation"}'>Event Location</th>
									<th class="hyperlink col5"
										data-dgrid-column='{field:"game", name:"game"}'>Teams</th>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	var sentArray = [];
	require(
			[ 'dojo/request', 'dijit/registry', 'dojo/ready', 'dojo/dom',
					'dojo/dom-construct' ],
			function(request, registry, ready, dom, domConstruct) {
				ready(function() {
					var grid = registry.byId('sport');
					var store = registry.byId('widgetSport');
					var buttonText = registry.byId('checkedButton');
					var buttonDate = registry.byId('setDate');
					var alertManagerSuccess = registry.byId('messageSuccess');
					var alertManagerFail = registry.byId('messageFail');
					var alertManagerWarning = registry.byId('messageWarning');
					//var sentArray = [];
					var idArray = [];
					var header = registry.byId('oap-checkboxheader-sport');
					var numberTab = registry.byId('paginationNodesport');
					//var pag = registry.byId('paginationParent');
					request('<c:url value="/finalproject/getGames"/>').then(
							function(data) {
								grid.store.setData(JSON.parse(data));
								grid.refresh();
							}, function(err) {
								console.log("Error: " + err);
							});
					grid.refresh();
					// Button Event
					buttonText.on('click', function() {
						var phoneNumber = registry.byId("phoneNumber").value;
						buttonText.stopSpinner();
						let btn = this;
						let checkedArray = grid.getChecked();
						//let output = dom.byId("output");
						var textInformation = "";
						var ids = "";
	
						for (var i = 0; i < checkedArray.length; i++) {
							ids += checkedArray[i].eventID + "  ";
							idArray.push(checkedArray[i].eventID);
							textInformation += "Event ID: " + checkedArray[i].eventID + "\n Game: " + checkedArray[i].game + "\n ";
						}
						//grid.refresh();
						//output.innerHTML = textInformation;
						// Load the Table after the DOM is ready
						request('<c:url value="/finalproject/text" />', {
							method : 'POST',
							handleAs : "json",
							data : {
								'phoneNumber' : phoneNumber,
								'textInformation' : textInformation,
								'ids' : idArray
							}
						}).then(function(data) {
							alertManagerSuccess.hide();
							alertManagerFail.hide();
							alertManagerWarning.hide();
							alertManagerSuccess.clear();
							var msg;
							console.log(data);
							let json = JSON.parse(data);
							btn.stopSpinner();							
							if (json.error == false) {
								msg = "Text Sent Successfully to " + phoneNumber + " with Ids " + ids;
								alertManagerSuccess.addSuccess({message : msg, position : 'messageSuccess', hide : false, duration : 10000});
								alertManagerFail.addError({hide : true});
								for (var i = 0; i < checkedArray.length; i++) {
									cb = registry.byId("oap-checkbox-" + checkedArray[i].eventID + "sport");
									cb.setAttribute("aria-checked", false);
									cb.setChecked(false);
									sentArray.push("oap-checkbox-" + checkedArray[i].eventID + "sport");
									cb.setDisabled(true);
									//domConstruct.destroy("oap-checkbox-" + checkedArray[i].eventID + "sport");
								}
							} else {
								msg = (json.errorMessage == '' || json.errorMessage == "undefined") ? 'Unknown Exception' : json.errorMessage;
								alertManagerFail.addError({message : msg, position : 'messageFail', hide : false});
								alertManagerSuccess.addSuccess({hide : true});
								checkBoxes();
							}
						}, function(err) {
							console.log("Error: " + err);
						}, function(err) {
							buttonText.stopSpinner();
							console.log("Error: "+ err);
						});
					});

					buttonDate.on('click', function() {
						//grid.refresh();	        	
						buttonDate.stopSpinner();
						let btn = this;
						var date = document.getElementById("gameDate").value;
						console.log(date);
						date = new Date(date);
						var dd = date.getDate() + 1;
						var mm = date.getMonth() + 1;
						var y = date.getFullYear();
						date = y + '/' + mm + '/' + dd;
						var newDate = y + '-' + mm + '-' + dd;
						var month = mm;
						var year = y;
						var day = dd;

						switch (newDate) {
						case "2018-7-32":
							newDate = "2018-08-01";
							month = "8";
							day = "01";
							break;
						case "2018-8-32":
							newDate = "2018-09-01";
							month = "9";
							day = "01";
							break;
						case "2018-9-31":
							newDate = "2018-10-01";
							month = "10";
							day = "01";
							break;
						case "2018-10-32":
							newDate = "2018-11-01";
							month = "11";
							day = "01";
							break;
						case "2018-11-31":
							newDate = "2018-12-01";
							month = "12";
							day = "01";
							break;
						case "2018-12-32":
							newDate = "2019-01-01";
							month = "1";
							day = "01";
							break;
						case "2019-1-32":
							newDate = "2019-02-01";
							month = "2";
							day = "2";
							break;
						case "2019-2-29":
							newDate = "2019-03-01";
							month = "3";
							day = "01";
							break;
						case "2019-3-32":
							newDate = "2019-04-01";
							month = "4";
							day = "01";
							break;
						case "2019-4-31":
							newDate = "2019-05-01";
							month = "5";
							day = "01";
							break;
						case "2019-5-32":
							newDate = "2019-06-01";
							month = "6";
							day = "01";
							break;
						case "2019-6-31":
							newDate = "2019-07-01";
							month = "7";
							day = "01";
							break;
						}
						console.log(date);
						// Load the Table after the DOM is ready
						request('<c:url value="/finalproject/postDate" />', {
							method : 'POST',
							handleAs : "json",
							data : {
								'dateOne' : date,
								'dateTwo' : newDate,
								'month' : month,
								'year' : y,
								'day' : day
							}
						}).then(function(data) {
							//grid.store.setData(JSON.parse(data));
							grid.store.setData(data);
							grid.refresh();
							//console.log(data);
							//let json = JSON.parse(data);
							alertManagerSuccess.hide();
							alertManagerFail.hide();
							alertManagerWarning.hide();
							alertManagerSuccess.clear();
							alertManagerWarning.clear();
							var msg;
							if(data.length == 0){
								msg = "No games on the date " + newDate;
								alertManagerWarning.addWarning({message: msg, position : 'messageWarning', hide : false, duration : 5000});
							} else {
								msg = "API loaded games for " + newDate;
								alertManagerSuccess.addSuccess({message : msg, position : 'messageSuccess', hide : false, duration : 10000});
								alertManagerFail.addError({hide : true});
							}
							btn.stopSpinner();
							checkBoxes();
						});
					});
					header.on('click', function() {
						checkBoxes();
					});
					grid.on('click', function() {
						checkBoxes();
					});
					numberTab.on('click', function() {
						checkBoxes();
					});
				});
				function checkBoxes(){
					for (let i = 0; i < sentArray.length; i++) {
						let cb = registry.byId(sentArray[i]);
						cb.setAttribute("aria-checked", false);
						cb.setChecked(false);
						cb.setDisabled(true);
					}
				}
			});
</script>





















