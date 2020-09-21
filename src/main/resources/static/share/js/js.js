	var aniShow = "pop-in";
			 //只有ios支持的功能需要在Android平台隐藏；
			if (mui.os.android) {
				var list = document.querySelectorAll('.ios-only');
				if (list) {
					for (var i = 0; i < list.length; i++) {
						list[i].style.display = 'none';
					}
				}
				//Android平台暂时使用slide-in-right动画
				if(parseFloat(mui.os.version)<4.4){
					aniShow = "slide-in-right";
				}
			}
			//初始化，并预加载webview模式的选项卡			
			function preload () {
				mui.preload({
					url:'examples/tab-webview-main.html',
					styles: {
						popGesture: 'hide'
					}
				});
				mui.preload({
					url:"examples/pullrefresh_main.html",
					styles:{
						popGesture:'hide'
					}
				});
			}
			var templates = {};
			var getTemplate = function(name, header, content) {
				var template = templates[name];
				if (!template) {
					//预加载共用父模板；
					var headerWebview = mui.preload({
						url: header,
						id: name + "-main",
						styles: {
							popGesture: "hide",
						},
						extras: {
							mType: 'main'
						}
					});
					//预加载共用子webview
					var subWebview = mui.preload({
						url: !content ? "" : content,
						id: name + "-sub",
						styles: {
							top: '45px',
							bottom: '0px',
						},
						extras: {
							mType: 'sub'
						}
					});
					subWebview.addEventListener('loaded', function() {
						setTimeout(function() {
							subWebview.show();
						}, 50);
					});
					subWebview.hide();
					headerWebview.append(subWebview);
					//iOS平台支持侧滑关闭，父窗体侧滑隐藏后，同时需要隐藏子窗体；
					if (mui.os.ios) { //5+父窗体隐藏，子窗体还可以看到？不符合逻辑吧？
						headerWebview.addEventListener('hide', function() {
							subWebview.hide("none");
						});
					}
					templates[name] = template = {
						name: name,
						header: headerWebview,
						content: subWebview,
					};
				}
				return template;
			};
			var initTemplates = function() {
				getTemplate('default', 'examples/template.html');
			};
			mui.plusReady(function() {
				//读取本地存储，检查是否为首次启动
				var showGuide = plus.storage.getItem("lauchFlag");
				if(showGuide){
					//有值，说明已经显示过了，无需显示；
					//关闭splash页面；
					plus.navigator.closeSplashscreen();
					plus.navigator.setFullscreen(false);
					//初始化模板
					initTemplates(); 
					//预加载
					preload();
				}else{
					//显示启动导航
					mui.openWindow({
						id:'guide',
						url:'examples/guide.html',
						show:{
							aniShow:'none'
						},
						waiting:{
							autoShow:false
						}
					});
					//延迟的原因：优先打开启动导航页面，避免资源争夺
					setTimeout(function () {
						//初始化模板
						initTemplates(); 
						//预加载
						preload();
					},200);
				}
			});
			 
			var index = null; //主页面
			function openMenu() {
				!index && (index = mui.currentWebview.parent());
				mui.fire(index, "menu:open");
			}
			//在android4.4.2中的swipe事件，需要preventDefault一下，否则触发不正常
			window.addEventListener('dragstart', function(e) {
				mui.gestures.touch.lockDirection = true; //锁定方向
				mui.gestures.touch.startDirection = e.detail.direction;
			});
			window.addEventListener('dragright', function(e) {
				if (!mui.isScrolling) {
					e.detail.gesture.preventDefault();
				}
			});
			 //监听右滑事件，若侧滑菜单未显示，右滑要显示菜单；
			window.addEventListener("swiperight", function(e) {
				//默认滑动角度在-45度到45度之间，都会触发右滑菜单，为避免误操作，可自定义限制滑动角度；
				if (Math.abs(e.detail.angle) < 4) {
					openMenu();
				}
			});
			//使用tap事件打开新页面
			(function($) {
//                  $('#topnav').on('tap', '.mui-icon-search', function() {
//                     location='search.html'
//                  });
//				  	$('#topnav').on('tap', '.icon_wenzi', function() {
//                     location='fenleigl.html'
//                  });
//					$('#yuqinglist').on('tap', '.mui-col-xs-10', function() {
//                     location='yqnr.html'
//                  });
					$('#mui-bar-tab').on('tap', '.onnav1', function() {
						cleanSearch();
                       	location='index.jsp'
                    });
					$('#mui-bar-tab').on('tap', '.onnav2', function() {
                       	cleanSearch();
                       	location='warning.jsp'
                    });
					$('#mui-bar-tab').on('tap', '.onnav3', function() {
                       	cleanSearch();
                       	location='find.jsp'
                    });
					$('#mui-bar-tab').on('tap', '.onnav4', function() {
                       	cleanSearch();
                       	location='mine.jsp'
                    });
                    $('.onnav1').on('tap', function() {
                       	cleanSearch();
                       	location='index.jsp'
                    });
					$('.onnav2').on('tap', function() {
                      	cleanSearch();
                       	location='warning.jsp'
                    });
					$('.onnav3').on('tap', function() {
						cleanSearch();
                       	location='find.jsp'
                    });
					$('.onnav4').on('tap', function() {
						cleanSearch();
                       	location='mine.jsp'
                    });
			    	//使用tap事件弹出层
//                 	$('#trys').on('tap', '.xwkk', function() {
//                  	new Function(demo2.innerHTML)(); 
//                  });
//					 $('#trys').on('tap', '.xwkk2', function() {
//                  	new Function(demo1.innerHTML)(); 
//                  });
//					$('#cz_box').on('tap', '.cz_xg', function() {
//                  	new Function(demo2.innerHTML)(); 
//                  });
// 					$('#cz_box').on('tap', '.cz_sc', function() {
//                  	new Function(demo1.innerHTML)(); 
//                  });
               })(mui);
				
				 
			mui.init({
				swipeBack: true //启用右滑关闭功能
			});
			mui('.mui-scroll-wrapper').scroll();
			 
	 
	//获取屏幕最大宽高 
	  
var winWidth = 0;
var winHeight = 0;
function findDimensions() //函数：获取尺寸
{
//获取窗口宽度
if (window.innerWidth)
winWidth = window.innerWidth;
else if ((document.body) && (document.body.clientWidth))
winWidth = document.body.clientWidth;
//获取窗口高度
if (window.innerHeight)
winHeight = window.innerHeight;
else if ((document.body) && (document.body.clientHeight))
winHeight = document.body.clientHeight;
//通过深入Document内部对body进行检测，获取窗口大小
if (document.documentElement && document.documentElement.clientHeight && document.documentElement.clientWidth)
{
winHeight = document.documentElement.clientHeight;
}
//document.getElementById('H').style.height=winHeight + "px";//这里是给我们的H 赋值
//document.getElementById('W').style.width=winWidth + "px";//这里是给我们的W 赋值
}
findDimensions();
//调用函数，获取数值
window.onresize=findDimensions;
//–>
$("#ul-catalog").on('tap',function(e){
	var e = e || window.event;
	var target = e.srcElement || e.target;
	
	var tagName = target.tagName.toLowerCase();
	var allLi = document.getElementById("ul-catalog").getElementsByTagName("li");
	for(var i=0;i<allLi.length;i++){
		if(target == allLi[i]){
			allLi[i].style.background = "url(image/leftmuenbg.png) no-repeat";
		}else{
			allLi[i].style.background = "transparent";
		}
	}
})
