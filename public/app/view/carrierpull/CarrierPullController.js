/**
 * The popup form for uploading a file.
 */
var uploadWindow = new Ext.Window({
    title: 'Import CSV',
    layout: 'fit',
    floating: true,
    autoScroll: false,
    y: 60,
    width: 350,
    height: 250,
    bodyPadding: '10 10 10 10',
    modal: true,
    closeAction: 'hide',
    style: "background-color: #FFF",
    items: [{
    	xtype: 'label',
    	text: 'Please select a .CSV file to upload.',
    	style: 'background-color: #FFF'
    },
    {
    	xtype: 'form',
    	frame: false,
	    bodyStyle: 'background-color: #FFF',
	    items: [{
	        xtype: 'filefield',
	        name: 'document',
	        fieldLabel: 'File',
	        labelWidth: 50,
	        msgTarget: 'side',
	        allowBlank: false,
	        fieldStyle: "display: none",
	        anchor: '100%',
//	        hideLabel: true,
	        buttonText: 'Select File',
	        listeners: {
	        	change: function(thiss,value,eOpts) {
	        		var form = this.up('form').getForm();
	        		if(form.isValid()){
	                    form.submit({
	                        url: 'uploadCSV',
	                        waitMsg: 'Uploading your file...',
	                        success: function(fp, o) {
	                        	if(o.result.success == "true") {
	                        		Ext.Msg.alert('Success', 'Your file "' + o.result.filename + '" was imported successfully.  Rows imported: ' + o.result.rowsImported);
	                        	}
	                        	else {
	                        		Ext.Msg.alert('Error', 
	                        				"Your file '" + o.result.filename + "' encountered one or more errors."
	                        				+ "<br/>Rows imported: " + o.result.rowsImported + "." 
	                        				+ "<br />Rows with problems: " + o.result.rowsFailed
	                        				+ "<br />See logs for more details.");
	                        	}
	                            
	                            uploadWindow.hide();
	                        }
	                    });
	                }
	        	}
			}
	    }]
    
    }
	]
});

/**
 * This class is the main view for the application. It is specified in app.js as the
 * "autoCreateViewport" property. That setting automatically applies the "viewport"
 * plugin to promote that instance of this class to the body element.
 *
 */

Ext.define('vfw.view.carrierpull.CarrierPullController', {
    extend: 'Ext.app.ViewController',

    requires: [
        'Ext.MessageBox',
        'Ext.Array'
    ],

    alias: 'controller.carrierpullctrl',
    refs: [{
        mainTabs: '#mainTabs',
        mainGrid: '#mainGrid'
    }],
    
    init: function() {
    	// Clear any cached warehouse id
    	this.getViewModel().data.whse='';
    	// get warehouse name from cookie
    	var s = Ext.util.Cookies.get("warehouse");
    	// Store the warehouse in view model
    	this.getViewModel().data.whse=s;
    	
    	// Set up event listeners
    	this.control({
    		'tabpanel[cls=mainTabPanel]': {
    			tabchange: function(tabPanel,newTab,oldTab,eOpts) {
    				// When focus changes to the 'List tab, update the grid's data
    				if (newTab.title=="Data"){
//    					this.onList();
    				}
    				
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
    					this.onList();
    				}
    			}
    		},
    		'[xtype="menu"]': {
    			click: function(menu,item, e, opts) {
    				var actionRequested=item.text;
    				switch(actionRequested) {
    				case "Import":
    					this.onImport();
    					break;
    				case "Export":
    					this.onExport();
    					break;
    				case "Delete all records...":
    					this.onDeleteAllRecords();
    					break;
    				}
    			}
    		},
    		'[xtype="grid"]': {
    			'saveRecordEvent': function(e) {
    			},
    			'beforeedit': function(editor,context,options) {
    				var record = context.record;
    				var grid   = context.grid;
    				if (!record.phantom) {  
    					// editing a record - disable editing of key columns
    					grid.getPlugin('rowEditingPlugin').editor.form.findField('shipVia').disable();
    					grid.getPlugin('rowEditingPlugin').editor.form.findField('shipToZip').disable();
    				}
    				else {
    					// adding - enabled editing of key columns
    					grid.getPlugin('rowEditingPlugin').editor.form.findField('shipVia').enable();
    					grid.getPlugin('rowEditingPlugin').editor.form.findField('shipToZip').enable();
    				}
    				
    			}
    		}
    	});
    },
    
    
    handleNode: function(node) {
		if (rec.getProperty()==ageFilter.getProperty()) {
			filterExists=true;
		}
	
    	
    },
    
    onFieldChange: function(field,newVal,oldVal) {
    	var store = this.lookupReference('mainGrid').getStore();
    	
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
    			store.removeFilter(filterToRemove);
    			store.currentPage=1;
    		}
    		else {
    			// Update the filter
    			var newFilter = new Ext.util.Filter({
    	    	    property: field.name,
    	    	    value   : newVal
    	    	});
    			store.addFilter(newFilter);
    			store.currentPage=1;
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
    			store.currentPage=1;
    		}
    	}
    },
    
    handleNode: function(node) {
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
    	
    	var st = this.lookupReference('mainGrid').getStore();
    	
    	st.load({callback: function(records,options,success) {
    	}});
    	this.lookupReference('mainTabs').setActiveTab(1);  // Switch to the 'List' tab to display data
    },
    
    /**
     * User has pressed the "Delete" button to delete any selected Carrier Pull entries.
     */
    onDeleteCarrierPull: function () {
    	// Make sure at least one carton has been selected in the datagrid
    	var retval = {};
    	var records =[];
    	retval["records"]=records;
    	
    	// Get a list of "checked" items in the datagrid
    	var selections = this.lookupReference('mainGrid').getSelectionModel().getSelection();
    	
    	if (selections.length > 0 ) {
    		// Insert details of each selected record into the JSON
    		Ext.each(selections, function(item, index, allItems) {
//    			records.push({"carton_nbr": item.data.carton_nbr});
    			records.push(item.data);
    		});
    		
    		// send the json to the server
    		Ext.Ajax.request({
    		    url: 'deleteCarrierPull',
    		    method: 'POST',          
    		    headers: {'Content-Type': 'application/json','Accept':'application/json'},
    		    waitTitle: 'Connecting',
    		    waitMsg: 'Sending data...',                                     
    		    paramsAsJson: true,
    		    jsonData: Ext.encode(retval),
    		    writer: "json",
    		    scope:this,
    		    success: this.onDeleteCarrierPullResult,                                    
    		    failure: function(){}
    		});
    	}
    	else {
    		Ext.MessageBox.alert('ERROR', 'At least one record must be selected for this action.');
    	}
    },
    
    onDeleteCarrierPullResult: function(data) {
    	var resp = Ext.util.JSON.decode(data.responseText); 
    	if(resp.success=="true"){
    		var errorMsg = resp.message;
    		errorMsg=errorMsg.replace(/\n/g,"<br/>");  // Replace newlines with HTML line break
    		Ext.MessageBox.alert('SUCCESS', errorMsg);
    		
    		// Remove any selected records from the store
    		// so they are removed from UI
    		var store = this.lookupReference('mainGrid').getStore();
    		var selectedRecords = this.lookupReference('mainGrid').getSelectionModel().getSelection();
    		store.remove(selectedRecords);
    		
    		var recordsLeftThisPage = store.getCount();
    		if (recordsLeftThisPage==0) {
    			var currentPage = store.currentPage;
    			if (currentPage>1) {
    				store.currentPage=store.currentPage-1;
//    				store.load();
    			}
    		}
    		store.load();
//    		this.lookupReference('mainGrid').getView().refresh();
//    		var pager = this.lookupReference('pager');
//    		if (pager) {
////    			pager.unbind(store);
////    		    pager.bind(store);
//    			store.setCount(store.getCount()-selectedRecords.length);
//    		}
    	}
    	else if (resp.success=="false") {
    		var errorMsg = resp.message;
    		errorMsg=errorMsg.replace(/\n/g,"<br/>");  // Replace newlines with HTML line break
    		Ext.MessageBox.alert('ERROR (deletion failed)', errorMsg);
    		
    		// Remove any selected records from the store
    		// so they are removed from UI
    		var store = this.lookupReference('mainGrid').getStore();
    		//var selectedRecords = this.lookupReference('mainGrid').getSelectionModel().getSelection();
    		//store.remove(selectedRecords);
    		store.load();
    		this.lookupReference('mainGrid').getView().refresh();
    	}
    },

    /**
     * User has pressed the Import button.
     */
    onImport: function() {
    	uploadWindow.show();
    },
    
    /**
     * User has pressed the Export button.
     */
    onExport: function() {
    	window.open("exportCSV");
    },
    /**
     * User has pressed "Delete all records..."
     */
    onDeleteAllRecords: function() {
    	var this_ctrl=this;
    	Ext.Msg.show({
    	    title: 'WARNING',
    	    message: 'If you press OK, all records for the current warehouse will be deleted.  This action cannot be undone.',
    	    width: 300,
    	    buttons: Ext.Msg.OKCANCEL,
//    	    multiline: true,
    	    fn: function(btn) {
    	    	if(btn=='ok'){
    	    		Ext.Ajax.request({
    	    		    url: 'deleteAllCarrierPull',
    	    		    success: function(result) {
    	    		    	var resp = Ext.util.JSON.decode(result.responseText); 
    	    		    	var status = (resp.success=='true') ? "SUCCEEDED" : "FAILED";
    	    		    	var message = resp.message;
    	    		    	if (message) {
    	    		    		Ext.MessageBox.alert(status, message);
    	    		    		if (resp.success=='true') {
    	    		    			// records were deleted from server,
    	    		    			// so remove them from UI as well
    	    		    			var store = this_ctrl.lookupReference('mainGrid').getStore();
    	    		    			store.load();
    	    		    			this_ctrl.lookupReference('mainGrid').getView().refresh();
    	    		    		}
    	    		    	}
    	    		    },
    	    		});
    	    	}
    	    },
//    	    animateTarget: 'addAddressBtn',
    	    icon: Ext.window.MessageBox.INFO
    	});
    },
    
    onDeleteAllRecordsResult: function(result) {
    },
    
    /**
     * User has pressed Edit btn in the UI
     */
    onEditClick: function() {
    	var grid = this.lookupReference('mainGrid');
    	var selections = grid.getSelectionModel().getSelection();
    	if (selections.length == 1) {
//    		var editManager = this.getPlugin('rowEditor');
        	var ed = grid.editingPlugin;
    		ed.startEdit(selections[0]);
    	}
    	else {
    		Ext.MessageBox.alert("ERROR", "Please select exactly one row for editing.")
    	}
    	
    },
    /**
     * User has pressed Add btn in the UI
     */
    onAddClick: function() {
    	var grid = this.lookupReference('mainGrid');
    	var ed = grid.editingPlugin;
    	ed.cancelEdit();
    	// Create a model instance
        var r = Ext.create('vfw.view.carrierpull.CarrierPullModel', {
            isNew: true,
            whse: "",
            shipToZip: "",
            shipVia: "",
            shipViaDesc: "",
            pullTrlrCode: "",
            pullTime: "",
            pullTimeAMPM: "",
            anyText1: "",
            anyNbr1: "",
            createDateTime: '',
            modDateTime: "",
            userId: ""
        });
        grid.getStore().insert(0, r);
        ed.startEdit(0, 0);
//		ed.startEdit(selections[0]);
    	
    	
    }
    
});
