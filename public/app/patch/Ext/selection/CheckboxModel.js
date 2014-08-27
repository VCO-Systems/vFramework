Ext.define('vfw.patch.Ext.selection.CheckboxModel', {
	extend: 'Ext.selection.CheckboxModel',
	/**
     * Toggle between selecting all and deselecting all when clicking on
     * a checkbox header.
     */
	
    onHeaderClick: function(headerCt, header, e) {
        console.log('onHeaderClick() override');
    	this.callParent(arguments);
    }
   
    
});