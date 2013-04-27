(function( $ ){
        
        var methods = {         
                init : function( options ){
                        var _paginator = {
				total: true,
                                itemsPerPage: 20,
                                totalRecords : -1,
                                currentPageId : -1,
                                hashTag: "!/__id__",
                                head: true,
                                tail: true,
                                jump: true,
                                prev: true,
                                next: true,
                                panel: false,
                                headText: "首页",
                                tailText: "尾页",
                                prevText: "上页",
                                nextText: "下页"
                        };
                        
                        $.extend( _paginator, options );
			this.data("paginator", _paginator); 
                        return this.each(function(index, element){
				this.pageCount = function() {
                			return Math.ceil(_paginator.totalRecords / _paginator.itemsPerPage);            
			        };
	        		this.pageIdCal = function(){
			                if(_paginator.currentPageId > this.pageCount()){
			                        _paginator.currentPageId = this.pageCount();
			                }
			                if(_paginator.currentPageId < 1){
			                        _paginator.currentPageId = 1;
			                }
			        };
				this.getPrevPage = function() {
                			return _paginator.currentPageId - 1 <= 1 ? 1 : _paginator.currentPageId - 1;
			        };
				this.getNextPage = function() {
                			return new Number(_paginator.currentPageId) + 1 > this.pageCount() ? this.pageCount() : new Number(_paginator.currentPageId) + 1;
			        };
				this.selectPage = function(pageId){
					if(_paginator.totalRecords == -1){
						_paginator.totalRecords = _paginator.currentPageId * _paginator.itemsPerPage;	
					}

					var org = _paginator.currentPageId;
		        	        _paginator.currentPageId = pageId;
		                	this.pageIdCal();

					if(org != _paginator.currentPageId){
						this.draw();
					}

 		                       return this;
				};
                                this.draw = function(){
                                        $(this).empty();
					if( _paginator.total ){
						// 生成统计信息
						$(this).append('<span class="num">共 ' + this.pageCount() + ' 页， ' + _paginator.totalRecords + ' 个项目</span>');
					}
					var pageLinks = $(document.createElement("span"));
					pageLinks.addClass("pagelinks");

                                        if( _paginator.head ){
                                                // 生成首页按钮
                                                $(document.createElement("a"))
                                                        .attr("pageId", 1)
                                                        .attr("href", "#" + _paginator.hashTag.replace(/__id__/, 1))
							.addClass("first")
                                                        .text(_paginator.headText)
                                                        .appendTo(pageLinks);
                                        }
                                        if( _paginator.prev ){
                                                // 生成上一页链接
                                                $(document.createElement("a"))
                                                        .attr("pageId", this.getPrevPage())
                                                        .attr("href", "#" + _paginator.hashTag.replace(/__id__/, this.getPrevPage()))
							.addClass("prev")
                                                        .text(_paginator.prevText)
                                                        .appendTo(pageLinks);
                                        }
                                        
                                        if(_paginator.jump){
                                                //生成跳转
						var span = $(document.createElement('span'))
								.addClass("pageinput")
								.append("第 ");						
                                                var jumpInput = $(document.createElement("input"));
                                                jumpInput
							.val(_paginator.currentPageId)
                                                        .keydown(function(e){if(e.which!=8&&e.which != 13 && ((e.which<96||(e.which>=106)))&&((e.which <48) || (e.which >57))){e.preventDefault();}})
                                                        .keypress(function(event){
								if(event.which == 13){
									location.href = "#" + _paginator.hashTag.replace(/__id__/, $(this).val());
								}
							})
                                                        .width("2em")
							.appendTo(span);
						span.append(" 页 ")
                                                pageLinks.append(span);
                                        }
                                        
                                        if( _paginator.next ){
                                                // 生成下一页链接
                                                $(document.createElement("a"))
                                                        .attr("pageId", this.getNextPage())
                                                        .attr("href", "#" + _paginator.hashTag.replace(/__id__/, this.getNextPage()))
							.addClass("next")
                                                        .text(_paginator.nextText)
                                                        .appendTo(pageLinks);
                                        }
                                        if( _paginator.tail ){
                                                // 生成尾页链接
                                                var lastPageId = (this.pageCount() <= 1 ? 1 : this.pageCount());
                                                $(document.createElement("a"))
                                                        .attr("pageId", lastPageId)
                                                        .attr("href", "#" + _paginator.hashTag.replace(/__id__/, lastPageId))
							.addClass("last")
                                                        .text(_paginator.tailText)
                                                        .appendTo(pageLinks);
                                        }

					$(this).append(pageLinks);                                        
                                };
				if(_paginator.totalRecords != -1){
	                               	 this.draw();
				}
                        });
                },
                selectPage : function( pageId ){ 
			var paginator = this.data("paginator");
			this.each(function(index, element){
				this.selectPage(pageId);
			});
			if(paginator.currentPageId != pageId){
				location.href="#" + this.data("paginator").hashTag.replace(/__id__/, this.data("paginator").currentPageId);
			}
			return this;
                },
		hashTag : function( hashTag ){
			if(hashTag){
				this.data("paginator").hashTag = hashTag;
				this.each(function(){
					this.draw();
				});
				return this;
			}
			else{
				return this.data("paginator").hashTag;
			}
		},
		redraw : function(){
			return this.each(function(){
				this.draw();
			});
		},
                totalRecords : function( totalRecords ){
                        if( totalRecords || totalRecords == 0){
				var paginator = this.data("paginator");
                                var oldPageId = paginator.currentPageId;
                                paginator.totalRecords = totalRecords;
				if(paginator.currentPageId > 0){
					this.each(function(){
						this.pageIdCal();
						this.selectPage(paginator.currentPageId, oldPageId != paginator.currentPageId);
					});
				}
                                return this;
                        }
                        else{
                                return this.data("paginator").totalRecords;
                        }                       
                }
        }

        $.fn.paginator = function( method ){
                if(methods[method]){			
                        return methods[ method ].apply( this, Array.prototype.slice.call( arguments, 1 ));
                } else if ( typeof method == 'object' || !method ){
                        return methods.init.apply( this, arguments );
                } else {
                        $.error( 'Mehthod ' + method + ' does not exist on jQuery.paginator.');
                }               
        };
})( jQuery );
