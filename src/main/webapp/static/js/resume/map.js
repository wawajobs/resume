/** 
 * 为Array对象添加remove方法，删除制定值得元素 
 * @param  {String} 数组元素值 
 */  
Array.prototype.remove = function(s) {       
    for (var i = 0; i < this.length; i++) {       
        if (s == this[i])       
            this.splice(i, 1);       
    }       
} ;

/** 
 * 定义类型Java中的Map，存放键值对 
 */  
function Map(){  
    /** 
     * 存放键的数组 
     * @type {Array} 
     */  
    this.keys = new Array();  
    /** 
     * 存放数据 
     * @type {Object} 
     */  
    this.data = new Object();  
    /** 
     * 放入一个键值对 
     * @param  {String} key 
     * @param  {Object} value 
     */  
    this.put = function(key,value){  
        if(this.data[key] == null){  
            this.keys.push(key);  
        }  
        this.data[key] = value;  
    };  
    /** 
     * 获取某键对应的值 
     * @param  {String} key 
     * @return {Object} value 
     */  
    this.get = function(key){  
        return this.data[key];  
    };  
    /** 
     * 删除一个键值对 
     * @param  {String} key 
     */  
    this.remove = function(key){  
        this.keys.remove(key);  
        this.data[key] = null;  
    };  
    /** 
     * 置空
     * 
     */  
    this.empty = function(){
    	for (var i = 0; i < this.keys.length; i++) {
			var key = keys[i];
    		this.keys.remove(key);  
    		this.data[key] = null;  
		}
    };  
    /** 
     * 遍历Map，执行处理函数 
     * @param  {Function} 
     */  
    this.each = function(fn){  
        if(typeof fn != 'function'){  
            return;  
        }  
        var len = this.keys.length;  
        for(var i = 0;i < len;i++){  
            var k = this.keys[i];  
            fn(k,this.data[k],i);  
        }  
    };  
    /** 
     * 返回键值对数组(类似Java的entrySet) 
     * @return {Object} 键值对对象{key,value}的数组 
     */  
    this.entrys = function(){  
        var len = this.keys.length;  
        var entrys = new Array(len);  
        for(var i = 0;i < len;i++){  
            var tmp = this.keys[i];  
            entrys[i] = {  
                key : tmp,  
                value : this.data[tmp]  
            };  
        }  
        return entrys;  
    };  
    /** 
     * 判断Map是否为空 
     * @return {Boolean} 
     */  
    this.isEmpty = function(){  
        return this.keys.length == 0;  
    };  
    /** 
     * 获取键值对数量 
     * @return {Number} 
     */  
    this.size = function(){  
        return this.keys.length;  
    };  
    /** 
     * 重写toString 
     * @return {String} 
     */  
    this.toString = function(){  
        var s = "{";  
        for(var i = 0;i < this.keys.length;i++,s+=','){  
            var k = this.keys[i];  
            s += k + "=" + this.data[k];  
        }  
        s += "}";  
        return s;  
    };  
} 