var tableRowCheckboxToggleClasses=new Array('odd_row', 'even_row');
var tableRowCheckboxCheckedClass='marked';
var listSize = 1;
var QueryList = function (listID, tableType){
	var self = this;
	this.pager = null;
	this.header = null;
	this.listID = listID;
	this.headerID = listID + "header" + listSize;
	this.contentID = listID + "content" + listSize;;
	this.pagerID = listID + "pager" + listSize;;
	listSize ++;
	
	this.tableType = tableType;
	this.selectedValues = new Array;
	this.binded = true;
	this.selectedAll = false;
	this.pageSize = 15;
	
	this.fill = true;
	this.getSelected = function(){
		var selected = new Array;
		for (var i = 0; i < self.selectedValues.length; i++) {
			if(self.selectedValues[i] != undefined && self.selectedValues[i] != null){
				selected[selected.length] = self.selectedValues[i];
			}
		}
		return selected;
	};
	this.getPageIndex = function(){
		return (this.pager.index - 1) * this.pageSize;
	};
	this.getPage = function(){
		return this.pager.index;
	}
	this.listeners = new Array();
	this.addClickListener = function(clickFunction){
		if(clickFunction != undefined && $.isFunction(clickFunction)){
			this.listeners[this.listeners.length] = clickFunction;
		}
	}
	this.createTable = function(){
		var column = tableType == undefined ? self.header.length : (self.header.length + 1);
		$("#" + self.listID).html("<table border='0' cellspacing='0' cellpadding='0' class='dataTable'>"
		+ "<thead id = '" + self.headerID+ "'></thead>"
		+ "<tbody id = '" + self.contentID+ "'></tbody>"
		+ "<tr><td id='" + self.pagerID+ "' colspan = '" + column + "'></td></tr>"
		+ "</table>");
		
		self.createHeader();
	};
	this.createHeader = function(){
		var content = "<tr>";
		if(isCheckBox(self.tableType)){
			content += "<th class='dataTableHeader'><input type='checkBox'/></th>";
		}
		if(isRadio(self.tableType)){
			content += "<th class='dataTableHeader'>&nbsp;</th>";
		}
		
		for(var i = 0; i< self.header.length ; i++){
			content += "<th class='dataTableHeader'>" + self.header[i] + "</th>";
		}
		content += "<tr/>";
		$("#" + self.headerID).html(content);
		
		if(isCheckBox(self.tableType)){
			addCheckBoxHeaderEvent(self);
		}
	};
	this.createPager = function(page, totalNum){
		this.pager = new PagerView(this.pagerID);
		this.pager.index = page;
		this.pager.size = this.pageSize;
		this.pager.itemCount = totalNum;
		this.pager.onclick = function(index) {
			var inx = (index - 1) * self.pageSize;
			self.getData(index, self.pageSize);
		};
		this.pager.render();
	};
	this.getData = function(index, pageSize){
		return true;
	};
	this.callback = function(datas){
		for (var i = 0; i < this.selectedValues.length; i++) {
			 this.selectedValues[i] = null;
		}
		var content = '';
		for(var i = 0; i< datas.length ; i++){
			var lineDate = datas[i];
			content += "<tr class='" + tableRowCheckboxToggleClasses[i%tableRowCheckboxToggleClasses.length] + "'>";
			if(isRadio(this.tableType)){
				content += "<td><input type='" + this.tableType + "' id='"+lineDate[0]+"' name='" + this.contentID +"'";
				if(this.binded){
					content += " value = '" + lineDate[0] + "'";;	
				}
				content +="/></td>";
			}
			else if(isCheckBox(this.tableType)){
				content += "<td><input type='checkbox'";
				if(this.binded){
					content += " value = '" + lineDate[0] + "'";;	
				}
				content += "/></td>";
			}
			else {
				content += "<td style='display:none'><input  type='radio' name='" + this.contentID +"'";
				if(this.binded){
					content += " value = '" + lineDate[0] + "'";
				}
				content +="/></td>";
			}
			for(var j = this.binded ? 1 : 0; j< lineDate.length; j++){
				content += "<td>" + lineDate[j] + "</td>";
			}
			content += "</tr>";
		}
		if(this.fill){
			for (var i = datas.length; i < this.pageSize; i++) {
				var length = this.tableType != undefined ? this.header.length + 1 : this.header.length;
				content += "<tr>";
				for(var j = 0; j< length; j++){
					content += "<td>&nbsp;</td>";
				}
				content += "</tr>";
			}
		}
		$("#" + this.contentID).html(content);
		if(isCheckBox(this.tableType)){
			addCheckTableEvent(self);
		}else{
			addRadioTableEvent(self);
		}
	};
};

function isRadio(type){
	if(type == undefined){
		return false;
	}
	return $.trim(type).toLowerCase() == 'radio';
}
function isCheckBox(type){
	if(type == undefined){
		return false;
	}
	return $.trim(type).toLowerCase() == 'checkbox';
}

function addCheckBoxHeaderEvent(list){
	$("#" + list.headerID + " > tr").click(function(){
		list.selectedAll = !list.selectedAll;
		$("#" + list.headerID + " > tr :checkbox")[0].checked = list.selectedAll;
		$("#" + list.contentID + " > tr").each(function(i,row) {
			$.each(tableRowCheckboxToggleClasses, function(j,tableRowCheckboxToggleClass) {
				if($(row).hasClass(tableRowCheckboxToggleClass)) {
					var checkbox = $("#" + list.contentID + " > tr:eq("+i+") :checkbox")[0];
					if(list.selectedAll){
						$(row).addClass(tableRowCheckboxCheckedClass);
						list.selectedValues[i] = checkbox.value;
					}else{
						$(row).removeClass(tableRowCheckboxCheckedClass);
						list.selectedValues[i] = null;
					}
					checkbox.checked = list.selectedAll;
				} 
			});
		});
	});
}
function addRadioTableEvent(list){
	var selectRow;
	$("#" + list.contentID + " > tr").each(function(i,row) {
		$.each(tableRowCheckboxToggleClasses, function(j,tableRowCheckboxToggleClass) {
			if($(row).hasClass(tableRowCheckboxToggleClass)) {
				hasRadio = false;
				$(row).click(function() {
					uniqueId = '' + i;
					var radio = $("#" + list.contentID + " > tr:eq("+i+") :radio")[0];
					if(selectRow != undefined){
						selectRow.removeClass(tableRowCheckboxCheckedClass);
					}
					if (!$(row).hasClass(tableRowCheckboxCheckedClass)) {
						$(row).addClass(tableRowCheckboxCheckedClass);
						selectRow = $(row);
						if(list.binded && radio != undefined) {
							var v = null;
							if(radio != undefined)
								v= radio.value;
							
							list.selectedValues[0] = v;
							fireClickListener(list.listeners, v);
						}
					} else {
						$(row).removeClass(tableRowCheckboxCheckedClass);
					}
					if(radio != undefined){
						radio.checked = $(row).hasClass(tableRowCheckboxCheckedClass);
					}
				}); 
				
			} 
		});
	});
}
function addCheckTableEvent(list) {
	list.selectedAll = false;
	$("#" + list.headerID + " > tr :checkbox")[0].checked = false;
	$("#" + list.contentID + " > tr").each(function(i,row) {
		$.each(tableRowCheckboxToggleClasses, function(j,tableRowCheckboxToggleClass) {
			if($(row).hasClass(tableRowCheckboxToggleClass)) {
				$(row).click(function() {
					var checkbox = $("#" + list.contentID + " > tr:eq("+i+") :checkbox")[0];
					checkbox.checked = !$(row).hasClass(tableRowCheckboxCheckedClass);
					if (checkbox.checked) {
						$(row).addClass(tableRowCheckboxCheckedClass);
						list.selectedValues[i] = checkbox.value;
						fireClickListener(list.listeners, checkbox.value);
					} else {
						$(row).removeClass(tableRowCheckboxCheckedClass);
						list.selectedValues[i] = null;
					}
				}); 

			} 
		});
	});
}

function fireClickListener(listeners, value){
	for ( var i = 0; i < listeners.length; i++) {
		listeners[i](value);
	}
}
var PagerView = function(id){
 	var self = this;
	this.id = id;

	this._pageCount = 0; // 总页数
	this._start = 1; // 起始页码
	this._end = 1; // 结束页码

	/**
	 * 当前控件所处的HTML节点引用.
	 * @type DOMElement
		 */
	this.container = null;
	/**
	 * 当前页码, 从1开始
	 * @type int
	 */
	this.index = 1;
	/**
	 * 每页显示记录数
	 * @type int
		 */
	this.size = 15;
	/**
	 * 显示的分页按钮数量
	 * @type int
	 */
	this.maxButtons = 9;
	/**
	 * 记录总数
	 * @type int
     */
	this.itemCount = 0;

	/**
	 * 控件使用者重写本方法, 获取翻页事件, 可用来向服务器端发起AJAX请求.
	 * 如果要取消本次翻页事件, 重写回调函数返回 false.
	 * @param {int} index: 被点击的页码.
	 * @returns {Boolean} 返回false表示取消本次翻页事件.
	 * @event
	 */
	this.onclick = function(index){
 		return true;
	};
	/**
	 * 总条数、总页数，每页条数
	 */
	this.setProperties = function(count,pages,size){
		self.itemCount = count;
		self._pageCount = pages;
		self.size = size;
	};
	/**
	 * 内部方法.
	 */
	this._onclick = function(index){
 		var old = self.index;

		self.index = index;
		if(self.onclick(index) !== false){
 			self.render();
		}else{
 			self.index = old;
		}
	};

	/**
	 * 在显示之前计算各种页码变量的值.
		 */
	this._calculate = function(){
 		self._pageCount = parseInt(Math.ceil(self.itemCount / self.size));
		self.index = parseInt(self.index);
		if(self.index > self._pageCount){
 			self.index = self._pageCount;
		}
		if(self.index < 1){
 			self.index = 1;
		}

		self._start = Math.max(1, self.index - parseInt(self.maxButtons/2));
		self._end = Math.min(self._pageCount, self._start + self.maxButtons - 1);
		self._start = Math.max(1, self._end - self.maxButtons + 1);
	};

	/**
	 * 获取作为参数的数组落在相应页的数据片段.
		 * @param {Array[Object]} rows
	 * @returns {Array[Object]}
		 */
	this.page = function(rows){
		self._calculate();

		var s_num = (self.index - 1) * self.size ;
		var e_num = self.index * self.size;

		return rows.slice(s_num, e_num);	
	};

	/**
	 * 渲染控件.
	 */
	this.render = function(){
		var div = document.getElementById(self.id);
		div.view = self;
		self.container = div;

		self._calculate();

		var str = '';
		str += '<div class="PagerView">\n';
		if(self._pageCount > 1){
			if(self.index != 1){
				str += '<a href="javascript://1"><span>|<</span></a>';
				str += '<a href="javascript://' + (self.index-1) + '"><span><<</span></a>';
			}else{
				str += '<span>|<</span>';
				str += '<span><<</span>';
			}
			for(var i=self._start; i<=self._end; i++){
				if(i == this.index){
					str += '<span class="on">' + i + "</span>";
				}else{
					str += '<a href="javascript://' + i + '"><span>' + i + '</span></a>';
				}
			}
			if(self._pageCount > 1){
				if(self.index != self._pageCount){
					str += '<a href="javascript://' + (self.index+1) + '"><span>>></span></a>';
					str += '<a href="javascript://' + self._pageCount + '"><span>>|</span></a>';
				}else{
					str += '<span>>></span>';
					str += '<span>>|</span>';
				}
			}
		}
		str += ' Total pages ' + self._pageCount + ', Total ' + self.itemCount + ' ';
		str += '</div><!-- /.pagerView -->\n';
		self.container.innerHTML = str;
		var a_list = self.container.getElementsByTagName('a');
		for(var i=0; i<a_list.length; i++){
			a_list[i].onclick = function(){
				var index = this.getAttribute('href');
				if(index != undefined && index != ''){
					index = parseInt(index.replace('javascript://', ''));
					self._onclick(index)
				}
			return false;
		};
	}
};

};

