//时间段
var vSelectPeriod = Vue.extend({
	props:{
		//标题栏标题
		idsuffix:{
			type:String,
			required:true
		}
	},
    template: '<span><select class="mui-btn mui-btn-block" id="select-time{{idsuffix}}">'
		             + '<option value="">时间</option>'
		             + '<option value="1">今天</option>'
		             + '<option value="2">昨天</option>'
		             + '<option value="3">最近三天</option>'
		             + '<option value="4">最近七天</option>'
		             + '<option value="5">最近30天</option>'
		        + '</select></span>'
});
//类型
var vSelectType = Vue.extend({
	props:{
		//标题栏标题
		idsuffix:{
			type:String,
			required:true
		}
	},
    template: '<span><select class="mui-btn mui-btn-block" id="select-type{{idsuffix}}">'
		             + '<option value="">类型</option>'
		             + '<option value={{wst.typecode}} v-for="wst in websitetypes">{{wst.typename}}</option>'
		            + '</select></span>'
});
//属性
var vSelectEmotion = Vue.extend({
	props:{
		//标题栏标题
		idsuffix:{
			type:String,
			required:true
		}
	},
    template: '<span><select class="mui-btn mui-btn-block" id="select-emotion{{idsuffix}}">'
		            + '<option value="">属性</option>'
		            + '<option value="1">正面</option>'
		            + '<option value="0">中性</option>'
		            + '<option value="2">负面</option>'
		            + '</select></span>'
});
//排序
var vSelectOrderby = Vue.extend({
    template: '<span><select class="mui-btn mui-btn-block" id="select-orderby">'
			        + '<option value="">筛选</option>'
			        + '<option value="1">按日期</option>'
			       	+ '</select></span>'
});
//级别
var vSelectGrade = Vue.extend({
	props:{
		//标题栏标题
		idsuffix :{
			type:String,
			required:true
		}
	},
    template: '<span><select class="mui-btn mui-btn-block" id="select-level{{idsuffix}}">'
			        + '<option value="">级别</option>'
			        + '<option value="1">一级预警</option>'
			        + '<option value="2">二级预警</option>'
			        + '<option value="3">三级预警</option>'
			        + '<option value="4">四级预警</option>'
			       	+ '</select></span>'
});
var vSelectKeyword = Vue.extend({
    template: '<span><select class="mui-btn mui-btn-block" id="select-keyword"  style="">'
			        + '<option value="">关键词</option>'
			        + '<option value={{keyword.id}} v-for="keyword in keywordList">{{keyword.name}}</option>'
			       	+ '</select></span>'
});
var vSelectCustomSite = Vue.extend({
    template: '<span><select class="mui-btn mui-btn-block" id="select-customsite"  style="padding-left: 0px;">'
			        + '<option value="">自定义站点</option>'
			       	+ '</select></span>'
});

var vSelectMatchMode = Vue.extend({
    template: '<span><select class="mui-btn mui-btn-block" id="select-matchmode" style="padding-left: 8px;">'
			        + '<option value="">匹配方式</option>'
			        + '<option value="2">全文</option>'
			        + '<option value="1">标题</option>'
			       	+ '</select></span>'
});