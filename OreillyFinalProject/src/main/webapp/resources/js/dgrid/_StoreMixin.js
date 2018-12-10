//>>built
define("dgrid/_StoreMixin",["dojo/_base/declare","dojo/_base/lang","dojo/Deferred","dojo/aspect","dojo/on","dojo/when","put-selector/put"],function(_1,_2,_3,_4,on,_5,_6){function _7(_8){if(typeof _8!=="object"){_8=new Error(_8);}else{if(_8.dojoType==="cancel"){return;}}var _9=on.emit(this.domNode,"dgrid-error",{grid:this,error:_8,cancelable:true,bubbles:true});if(_9){console.error(_8);}};return _1(null,{collection:null,_renderedCollection:null,_rows:null,_observerHandle:null,shouldTrackCollection:true,getBeforePut:true,noDataMessage:"",loadingMessage:"",_total:0,constructor:function(){this.dirty={};this._updating={};this._columnsWithSet={};_4.before(this,"configStructure",_2.hitch(this,function(){this._columnsWithSet={};}));},destroy:function(){this.inherited(arguments);if(this._renderedCollection){this._cleanupCollection();}},_configColumn:function(_a){if(_a.set){this._columnsWithSet[_a.field]=_a;}this.inherited(arguments);},_setCollection:function(_b){if(this._renderedCollection){this.cleanup();this._cleanupCollection({shouldRevert:!_b||_b.storage!==this._renderedCollection.storage});}if(_b){var _c=_b;if(this.sort&&this.sort.length>0){_c=_b.sort(this.sort);}if(_c.track&&this.shouldTrackCollection){_c=_c.track();this._rows=[];this._observerHandle=this._observeCollection(_c,this.contentNode,{rows:this._rows});}this._renderedCollection=_c;}this.collection=_b;this.refresh();},_setStore:function(){if(!this.collection){}},_getTotal:function(){return this._total;},_cleanupCollection:function(_d){_d=_d||{};if(this._renderedCollection.tracking){this._renderedCollection.tracking.remove();}if(this._observerHandle){this._observerHandle.remove();this._observerHandle=this._rows=null;}if(_d.shouldRevert!==false){this.dirty={};}this._renderedCollection=this.collection=null;},_applySort:function(){if(this.collection){this.set("collection",this.collection);}else{if(this.store){}}},row:function(){var _e=this.inherited(arguments);if(_e&&_e.data&&typeof _e.id!=="undefined"){_e.id=this.collection.getIdentity(_e.data);}return _e;},refresh:function(){var _f=this.inherited(arguments);if(!this.collection){this.noDataNode=_6(this.contentNode,"div.dgrid-no-data");this.noDataNode.innerHTML=this.noDataMessage;}return _f;},renderArray:function(){var _10=this.inherited(arguments);if(!this.collection){if(_10.length&&this.noDataNode){_6(this.noDataNode,"!");}}return _10;},insertRow:function(_11,_12,_13,i,_14){var _15=this.collection,_16=this.dirty,id=_15&&_15.getIdentity(_11),_17,row;if(id in _16&&!(id in this._updating)){_17=_16[id];}if(_17){_11=_2.delegate(_11,_17);}row=this.inherited(arguments);if(_14&&_14.rows){_14.rows[i]=row;}if(this.noDataNode){_6(this.noDataNode,"!");this.noDataNode=null;}return row;},updateDirty:function(id,_18,_19){var _1a=this.dirty,_1b=_1a[id];if(!_1b){_1b=_1a[id]={};}_1b[_18]=_19;},save:function(){var _1c=this,_1d=this.collection,_1e=this.dirty,dfd=new _3(),_1f=dfd.promise,_20=function(id){var _21;return (_1c.getBeforePut||!(_21=_1c.row(id).data))?function(){return _1d.get(id);}:function(){return _21;};};function _22(id,_23){return function(_24){var _25=_1c._columnsWithSet,_26=_1c._updating,key,_27;if(typeof _24.set==="function"){_24.set(_23);}else{for(key in _23){_24[key]=_23[key];}}for(key in _25){_27=_25[key].set(_24);if(_27!==undefined){_24[key]=_27;}}_26[id]=true;return _5(_1d.put(_24),function(){delete _1e[id];delete _26[id];});};};for(var id in _1e){var put=_22(id,_1e[id]);_1f=_1f.then(_20(id)).then(put);}dfd.resolve();return _1f;},revert:function(){this.dirty={};this.refresh();},_trackError:function(_28){if(typeof _28==="string"){_28=_2.hitch(this,_28);}var _29=this,_2a;try{_2a=_5(_28());}catch(err){var dfd=new _3();dfd.reject(err);_2a=dfd.promise;}_2a.otherwise(function(err){_7.call(_29,err);});return _2a;},removeRow:function(_2b,_2c,_2d){var row={element:_2b};if(!_2c&&this.noDataMessage&&(this.up(row).element===_2b)&&(this.down(row).element===_2b)){this.noDataNode=_6(this.contentNode,"div.dgrid-no-data");this.noDataNode.innerHTML=this.noDataMessage;}var _2e=(_2d&&_2d.rows)||this._rows;if(_2e){delete _2e[_2b.rowIndex];}return this.inherited(arguments);},renderQueryResults:function(_2f,_30,_31){_31=_2.mixin({rows:this._rows},_31);var _32=this;return _5(_2f).then(function(_33){var _34=_32.renderArray(_33,_30,_31);delete _32._lastCollection;return _34;});},_observeCollection:function(_35,_36,_37){var _38=this,_39=_37.rows,row;var _3a=[_35.on("delete, update",function(_3b){var _3c=_3b.previousIndex;var to=_3b.index;if(_3c!==undefined&&_39[_3c]){if("max" in _39&&(to===undefined||to<_39.min||to>_39.max)){_39.max--;}row=_39[_3c];if(row.parentNode===_36){_38.removeRow(row,false,_37);}_39.splice(_3c,1);if(_3b.type==="delete"||(_3b.type==="update"&&(_3c<to||to===undefined))){_39[_3c]&&_39[_3c].rowIndex--;}if(_38._processScroll){_38._processScroll();}}if(_3b.type==="delete"){row=null;}}),_35.on("add, update",function(_3d){var _3e=_3d.previousIndex;var to=_3d.index;var _3f;function _40(){_3f=(_3f.connected||_3f).nextSibling;};if(to!==undefined&&(!("max" in _39)||(to>=_39.min&&to<=_39.max))){if("max" in _39&&(_3e===undefined||_3e<_39.min||_3e>_39.max)){_39.max++;}if(_39.length){_3f=_39[to];if(!_3f){_3f=_39[to-1];if(_3f){_40();}}}else{_3f=_38._getFirstRowSibling&&_38._getFirstRowSibling(_36);}if(row&&_3f&&row.id===_3f.id){_40();}if(_3f&&!_3f.parentNode){_3f=document.getElementById(_3f.id);}_39.splice(to,0,undefined);row=_38.insertRow(_3d.target,_36,_3f,to,_37);_38.highlightRow(row);}row=null;}),_35.on("add, delete, update",function(_41){var _42=(typeof _41.previousIndex!=="undefined")?_41.previousIndex:Infinity,to=(typeof _41.index!=="undefined")?_41.index:Infinity,_43=Math.min(_42,to);_42!==to&&_39[_43]&&_38.adjustRowIndices(_39[_43]);_38._onNotification(_39,_41,_35);if(_35===_38._renderedCollection&&"totalLength" in _41){_38._total=_41.totalLength;}})];return {remove:function(){while(_3a.length>0){_3a.pop().remove();}}};},_onNotification:function(){}});});