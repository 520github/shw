function AppGrid(){
		var size = 0;
		var step = 10;
		var markup = "<div class='item'><div class='p_pic'><img src='${logo}' /><b><a href='${go}' target='_blank'></a></b></div><h3><a href='#' target='_blank'>${name}</a></h3><div class='star'><i style='width:${star}px;'></i></div><p>${wholeVersionScore.vote}评分</p></div>";
		function append(data){
			for(var i in data){
				if(!data[i].wholeVersionScore){
					data[i].wholeVersionScore = {"star":0,"vote":0};
					data[i].star = 0; 
				}else{
					data[i].star = data[i].wholeVersionScore.star/5*75;
				}
				data[i].go = 'app.html#!/'+data[i].id;
			}
			$.template( "gridTemplate", markup );
			$.tmpl( "gridTemplate", data).appendTo( "[class='content clearfix']" );
			size+=data.length;
			if(size>step){
				location.href = "#pullbutton";
			}
			if(data.length<step){
				$("[name='pullbutton']").css("display","none");
			}
		}
		this.drag = function(){
			var param = new Array();
                param.push({name: "offset", value: size});
                param.push({name: "limit",  value: step});
	        $("#loading").css("display","block");
	        $("[name='pullbutton']").attr("class","bu_box bu_box_dis").unbind("click");
			$.ajax({
				url:"/service/app/best",
				dataType: "json",
				data:param,
	        	type: "get",
	        	sync:false,
	        	success: function(resp){
	        		$("#loading").css("display","none")
	        		append(resp);
	        		$("[name='pullbutton']").attr("class","bu_box").attr("href","#pullbutton").bind("click",function(){contentGrid.drag();return false;});
	        	},
	        	error: function(){
	        		//$("#loading").css("display","none")
				}
			});
		}
		this.drag();
}
function App(id){
	var markup = $(".wrap").html();
	$.ajax({
		url:"/service/app/"+id,
		dataType: "json",
    	type: "get",
    	sync:false,
    	success: function(resp){
    		fillData(resp);
    	},
    	error: function(){

		}
	});
	function fillData(data){
		console.log(data)
		if(data.catalog){
			data.catalog = data.catalog.join(" ");
		}
		if(!data.wholeVersionScore){
				data.wholeVersionScore = {"star":0,"vote":0};
				data.star = 0;
				data.vote = 0; 
		}else{
				data.star = data.wholeVersionScore.star/5*75;
				data.vote = data.wholeVersionScore.vote;
		}
		$.template( "appTemplate", markup );
		$(".wrap").html($.tmpl( "appTemplate", data));
		//console.log($.tmpl( "appTemplate", data));
		if(data.screenshotsForPhone){
			var html = "";
			var i = 0
			for(i in data.screenshotsForPhone){
				html += "<img src='"+data.screenshotsForPhone[i]+"' />";
			}
			$(".pics").html(html);
			$(".pics").css("width",234*(i+1))
		}
		$("title").html(data.name);
	}
}