/**
 * 底部导航栏
 */
var vNav=Vue.extend({
	template:'<nav class="mui-bar mui-bar_bot mui-bar-tab " id="mui-bar-tab">'
			 	+ '<a class="onnav1 mui-tab-item">'
			      + '<span class="mui-icon nav1"></span>'
			      + '<span class="mui-tab-label">舆情</span>'
			 	+ '</a>'
			 	+ '<a class="onnav2 mui-tab-item">'
			 		+ '<span class="mui-icon nav2"></span>'
			 		+ '<span class="mui-tab-label">预警</span>'
			 	+ '</a>'
			 	+ '<a class="onnav3 mui-tab-item">' 
			 		+ '<span class="mui-icon  nav3"></span>' 
			 		+ '<span class="mui-tab-label">发现</span>' 
			 	+ '</a>'
			 	+ '<a class="onnav4 mui-tab-item">'
			 		+ '<span class="mui-icon  nav4"></span>' 
			 		+ '<span class="mui-tab-label">我的</span>' 
			 	+ '</a>'
			+ '</nav>'
					
});