function MathQuestion(){
	this.pick = function(){
		var arg1 = Math.floor(Math.random()*100);
		var arg2 = Math.floor(Math.random()*100);
		var arg3 = Math.floor(Math.random()*100);
		var symbols = ["+","*"];
		var sym1 = arg2>arg3?symbols[0]:symbols[1];
		var sym1 = arg1>arg3?symbols[0]:symbols[1];
		var sym2 = arg2>arg1?symbols[1]:symbols[0];
		var sym2 = arg3>arg2?symbols[1]:symbols[0];
		this.content = arg1+" "+sym1+" "+arg2+" "+sym2+" "+arg3;
		return this.content;
	};
	this.key = function(){
		return eval(this.content);		
	}
}
