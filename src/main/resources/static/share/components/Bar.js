var vBar = Vue.extend({
	props:{
		//标题栏标题
		title:{
			type:String,
			required:true
		}
	},
    template: '<header class="mui-bar mui-bar-nav">' +
    	'<slot name="left"></slot>'+
        '<h1 class="title mui-title">{{title}}</h1>' +
        '<slot name="right"></slot>'+    
        '</header>'
});
