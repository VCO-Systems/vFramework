/**
 * This class is the main view for the application. It is specified in app.js as the
 * "autoCreateViewport" property. That setting automatically applies the "viewport"
 * plugin to promote that instance of this class to the body element.
 *
 * TODO - Replace this content of this view to suite the needs of your application.
 */

Ext.define('vfw.view.carton.CartonInquiryController', {
    extend: 'Ext.app.ViewController',

    requires: [
        'Ext.MessageBox',
        'Ext.Array'
    ],

    alias: 'controller.cartoninquiry',
    refs: [{
        mainTabs: '#mainTabs'
    }],
    
    init: function() {
    	this.control({
    		'tabpanel[cls=mainTabPanel]': {
    			tabchange: function(tabPanel,newTab,oldTab,eOpts) {
    				// When focus changes to the 'List tab, update the grid's data
    				
    			}
    		},
    		'[xtype="form"] field': {
    			// On field change, call the onFieldChange function
    			change: function(field, newVal, oldVal) {
    				this.onFieldChange(field,newVal,oldVal);
    			},
    			// Pressing ENTER on any criteria field triggers search
    			specialkey: function(field,e) {
    				if (e.getKey() == e.ENTER) {
    					console.log("ENTER");
    					this.onList();
    				}
    			}
    		}
    	});
    },
    
    onFieldChange: function(field,newVal,oldVal) {
//    	console.log('[[OnFieldChange]] Field [' + field.reference + "] changed to: " + newVal);
    	var store = this.lookupReference('mainGrid').getStore();
//    	store.clearFilter();
    	
		// See if this filter already exists for the store
    	var filterExists=false;
    	var existingStoreFilters = store.getFilters().items;
    	var filterToRemove;
    	for (var idx in existingStoreFilters) {
    		var filter = existingStoreFilters[idx];
    		if (filter.getProperty()==field.name) {
    			filterExists=true;
    			filterToRemove=filter;
    		}
    	}
    	
    	// Add/remove/update the filter
    	if (filterExists) {
    		if (!newVal) {  // an existing filter has been set to null/empty
    			// Todo: remove filter that has been nullified
    			console.log('removing filter: ' + field.name);
    			store.removeFilter(filterToRemove);
    		}
    		else {
    			// Update the filter (is this right, or does this make a dup?)
    			var newFilter = new Ext.util.Filter({
    	    	    property: field.name,
    	    	    value   : newVal
    	    	});
    			store.addFilter(newFilter);
    			console.log('modifying existing filter: ' + field.name)
    		}
    	}
    	else {  // filter does not exist on store
    		// Add the filter
    		if (newVal) {
    			var newFilter = new Ext.util.Filter({
    	    	    property: field.name,
    	    	    value   : newVal
    	    	});
    			store.addFilter(newFilter);
    			console.log('Adding filter for field: ' + field.name)
    		}
    	}
    },
    
    handleNode: function(node) {
//		console.log(rec.getProperty() + " / " + ageFilter.getProperty());
		console.debug(rec);
		if (rec.getProperty()==ageFilter.getProperty()) {
			filterExists=true;
		}
	
    	
    },
    
    onList: function () {
//    	var barcode=this.lookupReference('fldBarcode').getValue();
//    	var desc   =this.lookupReference('fldStyle').getValue();
//    	if (barcode.length>0 || desc.length>0 ) {
//    		this.lookupReference('mainTabs').setActiveTab(1);  // Switch to the 'List' tab to display data
//    	}
//    	else {
//    		Ext.Msg.alert('Information', 'Please enter search criteria');
//    	}
//    	console.log(barcode,desc);
    	
    	var st = this.lookupReference('mainGrid').getStore();
    	st.load();
    	this.lookupReference('mainTabs').setActiveTab(1);  // Switch to the 'List' tab to display data
    },
    
    onPrintCartonLabel: function () {
    	// Make sure at least one carton has been selected in the datagrid
    	var retval = {};
    	var records =[];
    	retval["records"]=records;
    	
    	// Get a list of "checked" items in the datagrid
    	var selections = this.lookupReference('mainGrid').getSelectionModel().getSelection();
    	
    	if (selections.length > 0 ) {
    		// Insert details of each selected record into the JSON
    		Ext.each(selections, function(item, index, allItems) {
    			records.push({"carton_nbr": item.data.carton_nbr});
    		});
    		
    		// send the json to the server
    		Ext.Ajax.request({
    		    url: 'printCartonLabels',
    		    method: 'POST',          
    		    headers: {'Content-Type': 'application/json','Accept':'application/json'},
    		    waitTitle: 'Connecting',
    		    waitMsg: 'Sending data...',                                     
    		    paramsAsJson: true,
    		    jsonData: Ext.encode(retval),
    		    writer: "json",
    		    scope:this,
    		    success: this.received,                                    
    		    failure: function(){console.log('failure');}
    		});
    	}
    	else {
    		alert('At least one carton must be selected.');
    	}
    },
    
    received: function(data) {
    	console.debug(data);
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
