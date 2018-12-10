//>>built
define("bootstrap/Modal",["./Support","dojo/_base/declare","dojo/query","dojo/_base/lang","dojo/_base/window","dojo/on","dojo/dom-class","dojo/dom-construct","dojo/dom-attr","dojo/dom-style","dojo/request","dojo/NodeList-dom","dojo/NodeList-traverse","dojo/NodeList-manipulate","dojo/domReady!"],function(_1,_2,_3,_4,_5,on,_6,_7,_8,_9,_a){"use strict";var _b="[data-toggle=modal]";var _c="[data-dismiss=modal]";var _d=_2([],{defaultOptions:{backdrop:true,keyboard:true,show:true},constructor:function(_e,_f){this.options=_4.mixin(_4.clone(this.defaultOptions),(_f||{}));var elm=this.domNode=_e;on(this.domNode,on.selector(_c,"click"),_4.hitch(this,this.hide));if(this.options.remote){_a(this.options.remote).then(function(_10){_3(".modal-body",elm).html(_10);});}},toggle:function(){return this[!this.isShown?"show":"hide"]();},show:function(e){var _11=this;on.emit(this.domNode,"show.bs.modal",{bubbles:false,cancelable:false});if(this.isShown&&e.defaultPrevented){return;}this.isShown=true;_12.call(this);_13.call(this,function(){var _14=_1.trans&&_6.contains(_11.domNode,"fade");if(!_3(_11.domNode).parent().length){_7.place(_11.domNode,_5.body());}_9.set(_11.domNode,"display","block");if(_14){_11._offsetWidth=_11.domNode.offsetWidth;}_6.add(_11.domNode,"in");_8.set(_11.domNode,"aria-hidden",false);_25.call(_11);if(_14){on.once(_11.domNode,_1.trans.end,function(){_11.domNode.focus();on.emit(_11.domNode,"shown.bs.modal",{bubbles:false,cancelable:false});});}else{_11.domNode.focus();on.emit(_11.domNode,"shown.bs.modal",{bubbles:false,cancelable:false});}});},hide:function(e){var _15=this;on.emit(this.domNode,"hide.bs.modal",{bubbles:false,cancelable:false});if(e){e.preventDefault();}if(!this.isShown&&e.defaultPrevented){return;}this.isShown=false;_12.call(this);if(this.focusInEvent){this.focusInEvent.remove();}_6.remove(this.domNode,"in");_8.set(_15.domNode,"aria-hidden",true);if(_1.trans&&_6.contains(this.domNode,"fade")){_16.call(this);}else{_17.call(this);}}});function _18(_19){var _1a=_8.get(_19,"data-target");if(!_1a){_1a=_1.hrefValue(_19);}return (!_1a)?"":_1a;};function _16(){var _1b=this;var _1c=setTimeout(function(){if(_1b.hideEvent){_1b.hideEvent.remove();}_17.call(_1b);},500);_1b.hideEvent=_1.trans?on.once(_1b.domNode,_1.trans.end,function(){clearTimeout(_1c);_17.call(_1b);}):null;};function _17(){var _1d=this;_9.set(_1d.domNode,"display","none");on.emit(_1d.domNode,"hidden.bs.modal",{bubbles:false,cancelable:false});_13.call(_1d);};function _13(_1e){var _1f=this;var _20=_6.contains(_1f.domNode,"fade")?"fade":"";if(_1f.isShown&&_1f.options.backdrop){var _21=_1.trans&&_20;_1f.backdropNode=_7.place("<div class=\"modal-backdrop "+_20+"\" />",_5.body());on(_1f.backdropNode,"click",_1f.options.backdrop!=="static"?_4.hitch(_1f.domNode,"focus"):_4.hitch(_1f,"hide"));if(_21){_1f.backdropNode.offsetWidth;}_6.add(_1f.backdropNode,"in");if(_21){on.once(_1f.backdropNode,_1.trans.end,_1e);}else{_1e();}}else{if(!_1f.isShown&&_1f.backdropNode){_6.remove(_1f.backdropNode,"in");if(_1.trans&&_6.contains(_1f.domNode,"fade")){on.once(_1f.backdropNode,_1.trans.end,_4.hitch(_1f,_22));}else{_22.call(_1f);}}else{if(_1e){_1e();}}}};function _22(){var _23=this;_7.destroy(_23.backdropNode);_23.backdropNode=null;};function _12(){var _24=this;if(_24.isShown&&_24.options.keyboard){_24.keyupEvent=on(_5.body(),"keyup",function(e){if(e.which===27){_24.hide();}});}else{if(!_24.isShown){if(_24.keyupEvent){_24.keyupEvent.remove();}}}};function _25(){var _26=this;_26.focusInEvent=on(document,on.selector(".modal","focusin"),function(e){if(_26.domNode!==this&&!_3(this,_26.domNode).length){_26.domNode.focus();}});};_4.extend(_3.NodeList,{modal:function(_27){return this.forEach(function(_28){var _29=_4.mixin({},_4.mixin(_1.getData(_28),_4.isObject(_27)&&_27));var _2a=_1.getData(_28,"modal");if(!_2a){_1.setData(_28,"modal",(_2a=new _d(_28,_29)));}if(_4.isString(_27)){_2a[_27].call(_2a);}else{if(_2a&&_2a.options.show){_2a.show();}}});}});on(_5.body(),on.selector(_b,"click"),function(e){var _2b=_3(_18(this));if(_2b[0]!==undefined){var _2c=_8.get(this,"href");var _2d=_1.getData(_2b,"modal")?"toggle":_4.mixin({remote:!/#/.test(_2c)&&_2c},_4.mixin(_1.getData(_2b),_1.getData(this)));if(_2d==="toggle"){_4.mixin(_1.getData(_2b,"modal").options,_1.getData(this));}_2b.modal(_2d);on.once(_2b[0],"hide",function(){_2b[0].focus();});}if(e){e.preventDefault();}});return _d;});