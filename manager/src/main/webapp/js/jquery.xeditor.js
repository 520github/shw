(function( $ ){
	
	var settings = {
		appUrl: "api/app/unprocessed/{id}",
		lockUrl: null,
		note: "remove",
		button: "process"
	}

	function approve(){
		var appId = $("#xeditor").attr("appId");
		if(!appId){
			return;
		}
		$.ajax({
			url: "api/app/awaiting/" + appId + "/note",
			async: false,
			type: "put",
			contentType: "application/json",
			data: $("#xeditor textarea[name=note]").val(),
			success: function(){}
		});

		$.ajax({
			url:"api/app/awaiting/" + appId + "/status",
			type: "put",
			async: false,
			contentType: "application/json",
			data: "published",
			success: function(){
				$("#xeditor").trigger("complete");
				$("#xeditor").dialog("close");
			}
		});
	}
	function refuse(){
		var appId = $("#xeditor").attr("appId");
		if(!appId){
			return;
		}	
		$.ajax({
			url: "api/app/awaiting/" + appId + "/note",
			async: false,
			type: "put",
			contentType: "application/json",
			data: $("#xeditor textarea[name=note]").val(),
			success: function(){}
		});
		$.ajax({
			url:"api/app/awaiting/" + appId + "/status",
			async: false,
			type: "put",
			contentType: "application/json",
			data: "refused",
			success: function(){
				$("#xeditor").trigger("complete");
				$("#xeditor").dialog("close");
			}
		});
	}

	function render( resp ){
		var tr = $("#xeditor table tbody tr");
		var xeditorOptions = $(document).data("xeditorOptions");
		tr.empty();
		tr.append($(document.createElement("td")).text(resp.id));
		
		var html = function(data){
				var htm = '<ul class="rowUl">';
				for(var d in data.applicationMarketDetails){
					htm+='<li><a target="_blank" href="'+data.applicationMarketDetails[d].url+'">'+d+'</a>';
					if(data.detail.market==d){
						htm+='<b>&nbsp;√</b>';
					}
					htm+='</li>';
				}
				htm+='</ul>'
				return htm;
		}(resp);
		
		tr.append($(document.createElement("td")).html(html));
		
		html = function(data){
				var htm = '<ul class="rowUl">';
				for(var d in data.applicationMarketDetails){
					htm+='<li>'+data.applicationMarketDetails[d].version+'</li>';		
				}
				htm+='</ul>'
				return htm;
		}(resp);
		tr.append($(document.createElement("td")).html(html));
		html = function(data){
				var htm = '<ul class="rowUl">';
				for(var d in data.applicationMarketDetails){
					htm+='<li>'+data.applicationMarketDetails[d].lastUpdate+'</li>';		
				}
				htm+='</ul>'
				return htm;
		}(resp);
		tr.append($(document.createElement("td")).html(html));
		
		html = function(data){
				var htm = '<ul class="rowUl">';
				for(var d in data.applicationMarketDetails){
					htm+='<li>'+data.applicationMarketDetails[d].wholeVersionScore.vote+"/"+data.applicationMarketDetails[d].wholeVersionScore.star+'</li>';		
				}
				htm+='</ul>'
				return htm;
		}(resp);
		
		tr.append($(document.createElement("td")).html(html));
		
		
		html = function(){
			var htm = '';
				for(var d in resp.applicationMarketDetails){
					htm+='<span style="font-weight:bold">在<a target="_blank" href="'+resp.applicationMarketDetails[d].url+'">'+d+'</a>商店的简介：</span><br>';
					htm+='<li>'+resp.applicationMarketDetails[d].originalDescription+'</li>';
					htm+='<hr>';		
				}
			return htm;
		}(resp)
		$("#xeditor .intro .inside").html(html);

		$("#xeditor form").attr("action", xeditorOptions.appUrl.replace("{id}", resp.id));
		$("#xeditor textarea, #xeditor input[name=introduction]").val("");
		$("#xeditor input[name=group]").val("");
		$("#xeditor textarea[name=note]").val(resp.note);
		
		if(resp.introduction){
			$("#xeditor input[name=introduction]").val(resp.introduction);
		}
		if(resp.review){
			$("#xeditor textarea[name=review]").val(resp.review);
		}else if(resp.originalDescription){
			var od = $("#xeditor .inside").text();
			console.log(od);
			var rv = od.length>80?od.substring(0,80):od;
			$("#xeditor textarea[name=review]").val(rv);
		}
		if(resp.description){
			$("#xeditor textarea[name=description]").val(resp.description);
		}else{
			$("#xeditor textarea[name=description]").val(resp.detail.originalDescription.replace(/\n/g,"").replace(/\t/g, "").replace(/<(.*?)>/g,"").trim());
		}
		if(resp.group){
			$("#xeditor input[name=group]").val(resp.group);
		}
		
		

		if($(document).data("xeditorOptions").button == "audit"){
			$("#xeditor textarea, #xeditor input, #xeditor select").attr("disabled", true);
			$("#xeditor form #submitAndEditButton").hide();
			$("#xeditor form #submitButton").hide();
			$("#xeditor form #removeButton").hide();
			
			if(resp.keywords){
				$("#xeditor input[name=keywords]").val(resp.keywords.join(";"));
			}else{
				$("#xeditor input[name=keywords]").attr("placeholder","");
			}
			
			$("#catalogSelect").val(resp.catalog);
			$("#catalogSelectBtn").css({"display":"none"});
		}
		else{
			$("#xeditor #approveButton").hide();
			$("#xeditor #refuseButton").hide();
			if($(document).data("xeditorOptions").button == "edit"){
				$("#xeditor form #submitButton").hide();
	                        $("#xeditor form #removeButton").hide();
				$("#xeditor form #submitAndEditButton").text("保存");
			}
			//keywords input
			$("#xeditor input[name=keywords]").tagit({
					removeConfirmation:true
			});
			$("#xeditor input[name=keywords]").tagit("removeAll");
			if(resp.keywords){
				for(var i in resp.keywords){
					$("#xeditor input[name=keywords]").tagit("createTag", resp.keywords[i]);
				} 
			}
			checkCount = 1;
			buildCatalogCheckbox(resp.catalog);
			if(resp.catalog){
				$("#catalogSelect").val(resp.catalog.join(";"));
			}
		}
		$("#xeditor textarea[name=note]").removeAttr("disabled");
		if(xeditorOptions.note != "enable"){
			if(xeditorOptions.note == "disable"){
				$("#xeditor textarea[name=note]").attr("disabled", "true");
			}
			else{
				$("#xeditor textarea[name=note]").parent("li").remove();
			}
		}
		$("#xeditor").dialog("open");
		                   
	}
	
	function buildCatalogCheckbox(checkedCatalogs){
			if(catalogCheckbox)catalogCheckbox.sourceElement.dialog("destroy");
			catalogCheckbox=null;
			var catalogs = [];
			$.ajax({
				url: "api/catalog/?parent=null",
				type: "get",
				dataType: "json",
				async:false,
				success: function(resp){
					catalogs = resp.result;
				}
			});	
			
			var html = '';
			for(var i in catalogs){
				var contained = false;
				for(var c in checkedCatalogs){
						if(catalogs[i].name==checkedCatalogs[c]){
							contained = true;
							break;
						}
				}
				if(contained){
					html+= '<fieldset class="ui-widget ui-widget-content"><legend>'+catalogs[i].name+'<input type="checkbox" checked value="'+catalogs[i].name+'"></legend>';
				}else{
					html+='<fieldset class="ui-widget ui-widget-content"><legend>'+catalogs[i].name+'<input type="checkbox" value="'+catalogs[i].name+'"></legend>';	
				}
				for(var j in catalogs[i].children){
					var checked = false;
					for(var c in checkedCatalogs){
						if(catalogs[i].children[j]==checkedCatalogs[c]){
							checked = true;
							
							break;
						}
					}
					var checkedHtml = checked==true?"checked":"";
					html+='<input type="checkbox" '+checkedHtml+' id="check'+checkCount+'" value="'+catalogs[i].children[j]+'" /><label for="check'+checkCount+'">'+catalogs[i].children[j]+'</label>'
					checkCount++;
				}
				html+='</fieldset>'
			}
			var catalogOptions = {title:"分类选择",sourceElement:$('.catalog-checkbox'),outputElement:$("#catalogSelect"),renderHtml:html};
			catalogCheckbox = new xCheckbox(catalogOptions);
			
	}
	var catalogCheckbox;
	var checkCount = 1;
	function unique(array){
		var newArray=[];
	    var provisionalTable = {};
	    for (var i = 0, item; (item= array[i]) != null; i++) {
	        if (!provisionalTable[item]) {
	            newArray.push(item);
	            provisionalTable[item] = true;
	        }
	    }
   		return newArray;
	}
	function xCheckbox(_options,_dialogOptions){
		var opts = _options;
		var dialogOptions = {title:opts.title,autoOpen:false,height:510,width:700,draggable:false,show:"slide",modal: true};
		if(_dialogOptions){
			dialogOptions=_dialogOptions;
		}
		this.sourceElement = opts.sourceElement;
		opts.sourceElement.find(".checkbox-div").html(opts.renderHtml);
		var checkedValue = [];
		opts.sourceElement.dialog(dialogOptions);
		opts.sourceElement.find("button").button();
		opts.sourceElement.find('.ok').click(function(){
			checkedValue = [];
			opts.sourceElement.find("input[type='checkbox']").each(function(){
				if($(this).attr("checked")){
					checkedValue.push($(this).val());
				}
			})
			opts.outputElement.val(unique(checkedValue).join(';'));
			opts.sourceElement.dialog("close");
		})
		opts.sourceElement.find('.clear').click(function(){
			opts.sourceElement.find("input[type='checkbox']").each(function(){
				if($(this).attr("checked")){
					$(this).removeAttr("checked");
					checkedValue = [];
				}
			})
		})
		opts.sourceElement.find("input[type='checkbox']").click(function(){
			var checkedVal = '';
			checkedVal = $(this).val();
			if($(this).attr("checked")){
				opts.sourceElement.find("input[type='checkbox']").each(function(){
					if(checkedVal==$(this).val()){
							$(this).attr("checked","checked");					
					}
				})
			}else{
				opts.sourceElement.find("input[type='checkbox']").each(function(){
					if(checkedVal==$(this).val()){
							$(this).removeAttr("checked");					
					}
				})
			}
		});
		this.open = function(){
			opts.sourceElement.dialog("open");
		}
	};
	
	
	
	var methods = {
		init: function( xeditorOptions ){
			xeditorOptions = $.extend( settings, xeditorOptions );
			this.data("xeditorOptions", xeditorOptions);

			$.ajax({
				url: "js/xeditor.html",
				type: "get",
				dataType: "html",
				async: false,
				cache: false,
				success: function(resp){
					$(document.body).append(resp);
				}
			});
			$("#xeditor").dialog({
		                title: "编辑新App", 
		                resizable: false,
		                modal: true,
		                width: $("#xeditor").width(),
		                autoOpen: false
			});
			
			
			$("#xeditor form").validate({
				rules:{
					catalogSelect:{
						required: true,
						maxlength: 150
					},
					introduction:{
						required: true,
						maxlength: 15
					},
					description: {
						required: true,
						maxlength: 1500
					},
					review: {
						required: true,
						maxlength: 80
					}
				},
				messages: {
					catalogSelect: {
						required: "此项不能留空哟。",
						maxlength: "太长了，不能超过150个字符哦。"
					},
					introduction: {
						required: "此项不能留空哟。",
						maxlength: "太长了，不能超过15个字符哦。"
					},
					description: {
						required: "填上一些内容吧。",
						maxlength: "这里不可以超过1500个字符的。"
					},
					review: {
						required: "这里也不可以留空哦。",
						maxlength: "最多写80个字就够啦。"
					}
				},
				submitHandler: function(form){
					var data = {
						introduction: $("#xeditor form input[name=introduction]").val(),
						description: $("#xeditor form textarea[name=description]").val(),
						review: $("#xeditor form textarea[name=review]").val(),
						catalog: $("#xeditor form #catalogSelect").val().split(";"),
						keywords: $("#xeditor form input[name=keywords]").tagit("assignedTags"),
						group:$("#xeditor form input[name=group]").val()
					};
					if(data.keywords.length>4){
						alert("描述标签不能多于4个！")
						return;
					}
					$.ajax({
						url: $("#xeditor form").attr("action"),
						type: "put",
						data: JSON.stringify(data),
						dataType: "json",
						contentType: "application/json",
						success: function(resp){
							if($(form).attr("con")){
								$("#xeditor").dialog("close");
								loadData();
							}
							else{
								location.href="editapp.html";
							}
						}
					});

					return false;
				}
			});
			$("#xeditor form #submitAndEditButton").click(function(){$("#xeditor form").attr("con", true).submit();});
			$("#xeditor form #submitButton").click(function(){$("#xeditor form").removeAttr("con").submit();});
			$("#xeditor form #removeButton").click(function(){remove($("#xeditor").attr("appId"));});
			$("#xeditor #cancelButton").click(function(){$("#xeditor").dialog("close");releaseEdit($("#xeditor").attr("appId"));});
			$("#xeditor #approveButton").click(function(){approve();});
			$("#xeditor #refuseButton").click(function(){refuse();});
			$("#xeditor #catalogSelectBtn").click(function(){if(catalogCheckbox)catalogCheckbox.sourceElement.dialog("open")})
		},
		edit: function( appId ){
			$("#xeditor").attr("appId", appId);
			var xeditorOptions = this.data("xeditorOptions");
			if(xeditorOptions.lockUrl){
				$.ajax({
					url: xeditorOptions.lockUrl.replace("{id}", appId),
					type: "put",
					dataType: "json",
					success: function(resp){
						render(resp);
					},
					error: function(jqXHR, textStatus, errorThrown){
						if(jqXHR.status == 409){
							alert('这个App已经被其他编辑锁定。');
						}
						else{
							alert('加载数据出错，请稍后再试。');
						}
					}
				});
			}
			else {
				$.ajax({
					url: xeditorOptions.appUrl.replace("{id}", appId),
					type: "get",
					dataType: "json",
					success: function(resp){
						render(resp);
					},
					error: function(jqXHR, textStatus, errorThrown){
						alert('加载数据出错，请稍后再试。');
					}
				});
	
			}
		}
	};

        $.fn.xeditor = function( method ){
                if( methods[method] ){
                        return methods[ method ].apply( this, Array.prototype.slice.call( arguments, 1 ));
                } else if ( typeof method === 'object' || ! method ) {
                      return methods.init.apply( this, arguments );
                } else {
                       $.error( 'Method ' +  method + ' does not exist on jQuery.tooltip' );
                }
        };
})( jQuery );

function releaseEdit(appId){};
