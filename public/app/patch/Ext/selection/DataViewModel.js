Ext.define('vfw.patch.Ext.selection.DataViewModel', {
	override: 'Ext.selection.DataViewModel',
	// Allow the GridView to update the UI by
    // adding/removing a CSS class from the row.
    onSelectChange: function(record, isSelected, suppressEvent, commitFn) {
//    	console.log('overridden DataViewModel.onSelectChange()');
    	this.callParent(arguments);
//        
//        }
    },
	listeners: {
		beforeselect: function(me,record,index) {
//			console.log("beforeSelect()");
		},
		beforedeselect: function(me,record,index,opts) {
//			console.log("beforeDeselect()");
		},
		onSelectChange: function(record, isSelected, suppressEvent, commitFn) {
//			console.log('onSelectChange()');
		}
	}
});