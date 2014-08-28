Ext.define('vfw.patch.Ext.selection.RowModel', {
	override: 'Ext.selection.RowModel',
	listeners: {
		beforedeselect: function(me,record,index,opts) {
//			console.log("beforedeselect");
		},
		onSelectChange: function(record, isSelected, suppressEvent, commitFn) {
//			console.log('onSelectChange()');
		}
	}
});