//>>built
(function(_1){var _2,_3=/[-+,> ]/;_1("put-selector/put",[],_2=function(_4,_5){"use strict";_3=_5||_3;var _6=/(?:\s*([-+ ,<>]))?\s*(\.|!\.?|#)?([-\w\u00A0-\uFFFF%$|]+)?(?:\[([^\]=]+)=?['"]?([^\]'"]*)['"]?\])?/g,_7,_8,_9=false,_4=_4||document,_a=typeof _4.createElement=="object";function _b(_c,_d){_c.appendChild(_4.createTextNode(_d));};function _e(_f){var _10,_11,_12,_13,_14,_15=arguments,_16=_15[0];function _17(){if(_14&&_13&&_14!=_13){(_13==_f&&(_10||(_10=_3.test(_18)&&_4.createDocumentFragment()))?_10:_13).insertBefore(_14,_12||null);}};for(var i=0;i<_15.length;i++){var _18=_15[i];if(typeof _18=="object"){_11=false;if(_18 instanceof Array){_14=_4.createDocumentFragment();for(var key=0;key<_18.length;key++){_14.appendChild(_e(_18[key]));}_18=_14;}if(_18.nodeType){_14=_18;_17();_13=_18;_12=0;}else{for(var key in _18){_14[key]=_18[key];}}}else{if(_11){_11=false;_b(_14,_18);}else{if(i<1){_f=null;}_11=true;var _19=_18.replace(_6,function(t,_1a,_1b,_1c,_1d,_1e){if(_1a){_17();if(_1a=="-"||_1a=="+"){_13=(_12=(_14||_13)).parentNode;_14=null;if(_1a=="+"){_12=_12.nextSibling;}}else{if(_1a=="<"){_13=_14=(_14||_13).parentNode;}else{if(_1a==","){_13=_f;}else{if(_14){_13=_14;}}_14=null;}_12=0;}if(_14){_13=_14;}}var tag=!_1b&&_1c;if(tag||(!_14&&(_1b||_1d))){if(tag=="$"){_b(_13,_15[++i]);}else{tag=tag||_e.defaultTag;var _1f=_a&&_15[i+1]&&_15[i+1].name;if(_1f){tag="<"+tag+" name=\""+_1f+"\">";}_14=_9&&~(_8=tag.indexOf("|"))?_4.createElementNS(_9[tag.slice(0,_8)],tag.slice(_8+1)):_4.createElement(tag);}}if(_1b){if(_1c=="$"){_1c=_15[++i];}if(_1b=="#"){_14.id=_1c;}else{var _20=_14.className;var _21=_20&&(" "+_20+" ").replace(" "+_1c+" "," ");if(_1b=="."){_14.className=_20?(_21+_1c).substring(1):_1c;}else{if(_18=="!"){var _22;if(_a){_e("div",_14,"<").innerHTML="";}else{if(_22=_14.parentNode){_22.removeChild(_14);}}}else{_21=_21.substring(1,_21.length-1);if(_21!=_20){_14.className=_21;}}}}}if(_1d){if(_1e=="$"){_1e=_15[++i];}if(_1d=="style"){_14.style.cssText=_1e;}else{var _23=_1d.charAt(0)=="!"?(_1d=_1d.substring(1))&&"removeAttribute":"setAttribute";_1e=_1e===""?_1d:_1e;_9&&~(_8=_1d.indexOf("|"))?_14[_23+"NS"](_9[_1d.slice(0,_8)],_1d.slice(_8+1),_1e):_14[_23](_1d,_1e);}}return "";});if(_19){throw new SyntaxError("Unexpected char "+_19+" in "+_18);}_17();_13=_16=_14||_13;}}}if(_f&&_10){_f.appendChild(_10);}return _16;};_e.addNamespace=function(_24,uri){if(_4.createElementNS){(_9||(_9={}))[_24]=uri;}else{_4.namespaces.add(_24,uri);}};_e.defaultTag="div";_e.forDocument=_2;return _e;});})(function(id,_25,_26){_26=_26||_25;if(typeof define==="function"){define([],function(){return _26();});}else{if(typeof window=="undefined"){require("./node-html")(module,_26);}else{put=_26();}}});