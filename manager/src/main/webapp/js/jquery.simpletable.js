(function( $ ){
	var opts = {
			cols: [],
			loading: true,
			dataLoader: function(){return false;},
			loadingTr: null
	}

	function setupClickHandler(tr){
		tr.click(function(){
			$(this).siblings().removeClass("selected-row");
			$(this).addClass("selected-row");
			$(this).trigger("selected", [jQuery(this).data()]);
		});
	}

	function setLoading(){
		$(this).children("tbody").empty().append(this.loading);
		return this;
	};

	var methods = {
		init: function( options ){
			opts = $.extend(opts, options || {});
			this.loadingTr = $(this).children("tbody").children(".loading");
			return this;
		},
		loadData: function(){
			var data = opts.dataLoader();
			return this.simpleTable("fillData", data);
		},
		fillData: function(data){
			$(this).children("tbody").empty();
			if(!data){
				return;
			}
			for(var row in data){
				var tr = $(document.createElement("tr"));
				tr.data(data[row]);
				for(var i = 0; i < opts.cols.length; i++){
					var td = $(document.createElement("td"));
					try{
						if(typeof(opts.cols[i]) == "string"){
							td.text(eval("data[row]." + opts.cols[i]));
						}
						else if(typeof(opts.cols[i]) == "function"){
							td.html(opts.cols[i](data[row]));
						}
						else{
							td.text("[unkown parameter.]");
						}
					}
					catch(e){
						td.text("[display error.]");
					}				
					tr.append(td);
				}
				setupClickHandler(tr);
				tr.appendTo($(this).children("tbody"));
			}
			$(this).trigger("load");
			return this;
		},
		clear: function(){
			$(this).children("tbody").empty();
			return this;
		}
	};

	$.fn.simpleTable = function( method ){
		if( methods[method] ){
			return methods[ method ].apply( this, Array.prototype.slice.call( arguments, 1 ));
		} else if ( typeof method === 'object' || ! method ) {
		      return methods.init.apply( this, arguments );
		} else {
		       $.error( 'Method ' +  method + ' does not exist on jQuery.tooltip' );
		}
	};
})( jQuery );
