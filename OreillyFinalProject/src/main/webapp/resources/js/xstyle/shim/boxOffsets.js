//>>built
define("xstyle/shim/boxOffsets",{onProperty:function(_1,_2){if(_1=="bottom"){if(_2!=="auto"){if(_2.match(/px$/)){return "height: expression(cssx_boxOffsets_checkBoxHeight(this, "+parseInt(_2)+"));";}else{return "height: expression(cssx_boxOffsets_checkBoxHeight(this)); bottom: expression(\""+_2+"\");";}}}else{if(_2!=="auto"){if(_2.match(/px$/)){return "width: expression(cssx_boxOffsets_checkBoxWidth(this, "+parseInt(_2)+"));";}else{return "width: expression(cssx_boxOffsets_checkBoxWidth(this)); right: expression(\""+_2+"\");";}}}}});function cssx_boxOffsets_checkBoxHeight(_3,_4){setTimeout(function(){var _5=_3.parentNode;var _6=_5.onresize;_5.onresize=function(){_7();if(_6){_6.call(this);}};},10);_7();function _7(){if(_4==null){_4=_3.style.pixelBottom;}_3.runtimeStyle.bottom="0px";var _8=_3.currentStyle,_9=_3.offsetParent,_a=_3.ownerDocument;if(_9&&_8.top!="auto"&&_8.position=="absolute"||_8.position=="fixed"){var _b=_9==_a.body?_a.body.clientHeight:_9.offsetHeight-(_3.offsetHeight-_3.clientHeight)-parseInt(_8.paddingTop)-parseInt(_8.paddingBottom);_b=_b-_3.offsetTop-_4+"px";}else{_b="";}_3.runtimeStyle.height=_b;};};function cssx_boxOffsets_checkBoxWidth(_c,_d){setTimeout(function(){var _e=_c.parentNode;var _f=_e.onresize;_e.onresize=function(){_10();if(_f){_f.call(this);}};},10);_10();function _10(){if(_d==null){_d=_c.style.pixelRight;}_c.runtimeStyle.right="0px";var _11=_c.currentStyle,_12=_c.offsetParent,doc=_c.ownerDocument;if(_12&&_11.left!="auto"&&_11.position=="absolute"||_11.position=="fixed"){var _13=(_12==doc.body?doc.body.clientWidth:_12.offsetWidth)-(_c.offsetWidth-_c.clientWidth)-parseInt(_11.paddingLeft)-parseInt(_11.paddingRight);_13=_13-_c.offsetLeft-_d+"px";}else{_13="";}_c.runtimeStyle.width=_13;};};