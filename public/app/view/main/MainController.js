/**
 * This class is the main view for the application. It is specified in app.js as the
 * "autoCreateViewport" property. That setting automatically applies the "viewport"
 * plugin to promote that instance of this class to the body element.
 *
 * TODO - Replace this content of this view to suite the needs of your application.
 */

Ext.define('vfw.view.main.MainController', {
    extend: 'Ext.app.ViewController',

    requires: [
        'Ext.MessageBox',
        'vfw.view.main.MainModel'
    ],

    alias: 'controller.mainly',
    refs: [{
        mainTabs: '#mainTabs'
    }],
    
    init: function() {
    	this.control({
    		'tabpanel[cls=mainTabPanel]': {
    			tabchange: function(tabPanel,newTab,oldTab,eOpts) {
    				// When focus changes to the 'List tab, update the grid's data
    				
    			}
    		}
    	});
    },

    showList: function () {
    	var barcode=this.lookupReference('fldBarcode').getValue();
    	var desc   =this.lookupReference('fldStyle').getValue();
    	if (barcode.length>0 || desc.length>0 ) {
    		this.lookupReference('mainTabs').setActiveTab(1);  // Switch to the 'List' tab to display data
    	}
    	else {
    		Ext.Msg.alert('Information', 'Please enter search criteria');
    	}
    	console.log(barcode,desc);
    	// Ext.Msg.confirm('Confirm', 'Are you sure?', 'onConfirm', this);
    	
    	// Tell the grid to load its data
    	var st = this.lookupReference('mainGrid').getStore();
    	//st.load({params: {limit: 10}});
    	st.loadPage(1);
    },

    onConfirm: function (choice) {
        if (choice === 'yes') {
            //
        }
    },
    getItems: function() {
    	alert('[Controller] getItems()');
    }
});
