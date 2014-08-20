/**
 * The view model for carrier pull.
 */
Ext.define('CarrierPullViewModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.CarrierPullViewModel', // connects to viewModel/type below

    data: {
        whse: 'test',
        userId: ''
    },

    formulas: {
        // We'll explain formulas in more detail soon.
        name: function (get) {
            var fn = get('firstName'), ln = get('lastName');
            return (fn && ln) ? (fn + ' ' + ln) : (fn || ln || '');
        }
    }
});
/**
 * This class is the main view for the application. It is specified in app.js as the
 * "autoCreateViewport" property. That setting automatically applies the "viewport"
 * plugin to promote that instance of this class to the body element.
 *
 */

var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
        clicksToMoveEditor: 2,
        autoCancel: true,
        pluginId: 'rowEditingPlugin',
        listeners: {
        	beforeedit: function(editor,context,opts) {
//        		console.log('before edit');
        		var grid = context.grid;
//        		grid.reconfigure(null, addModeColumns
//        		);
        		// after grid.reconfigure(), grid loses its reference to this RowEditing plugin
//        		
        		// try to loop over the column objects
//        		for (var idx = 0; idx < context.grid.getColumnModel().length; idx++ ) {
//        			var column = context.grid.getColumnModel()[idx];
////        			column.editable=false;
//        			column.setEditable(idx,false);
//        		}
        		
//        		if (!context.record.phantom && context.column.dataIndex == 'shipVia') {
//                    return false;
//                }
        		return true;
        		
        	},
        	validateedit: function ( editor, context, eOpts ) {
        		console.log('validateedit()');
        		
        		// To revert changes to row: editor.cancelEdit();
        		
        		// Validate any field values, then return true
        		// to commit change in UI, close editor, and
        		// mark changed fields dirty
        		this.fireEvent('saveRecordEvent');
        		return true;
        	},
        	canceledit: function ( editor, context, eOpts ) {
        		console.log('canceledit()');
        		editor.grid.getStore().each(function(record)
        		{
        			if (record.phantom)
        			{
        				editor.grid.getStore().remove(record);
        				return false;
        				
        			}
        		}, this);
        		
        	},        	
        	edit: function(editor, e) {
        		var record = e.record;
        		// send the json to the server
        		Ext.Ajax.request({
        		    url: 'saveCarrierPull',
        		    method: 'POST',          
        		    headers: {'Content-Type': 'application/json','Accept':'application/json'},
        		    waitTitle: 'Connecting',
        		    waitMsg: 'Sending data...',                                     
        		    paramsAsJson: true,
        		    jsonData: Ext.encode(record.data),
        		    writer: "json",
        		    scope:this,
        		    success: function(options, success, response) {
        		    	console.log('Successfully saved record to db.  Refreshing view.');
        		    	var resp = Ext.util.JSON.decode(options.responseText);
        		    	if (resp.success=="false") {
        		    		var errorMsg = resp.message;
        		    		errorMsg=errorMsg.replace(/\n/g,"<br/>");  // Replace newlines with HTML line break
        		    		Ext.MessageBox.alert('ERROR (addedit failed)', errorMsg);
        		    	}
//        		    	Ext.MessageBox.alert('ERROR (addedit failed)', errorMsg);
        		    	editor.grid.getStore().load();
        		    },                                    
        		    failure: function(){
        		    	console.log('failure');
        		    	
        		    	}
        		});
        		
        	}
        }
    });

Ext.define('vfw.view.carrierpull.CarrierPull', {
    extend: 'Ext.container.Container',

    xtype: 'app-main',
    requires: [
               'vfw.view.carrierpull.CarrierPullModel',
//               'vfw.store.CarrierPullStore',
//			   'vfw.model.CarrierPull',
               'vfw.view.carrierpull.CarrierPullController'
               ],
    controller: 'carrierpullctrl',
    viewModel: {
        type: 'CarrierPullViewModel'
    },

    layout: {
        type: 'border'
    },

    items: [{
        xtype: 'panel',
        region: 'north',
//        html: '<h4>Carrier Pull Maintanence</h4>',
        width: 250,
        split: false,
        tbar: [
           {xtype: "toolbar", 
        	   border: false,

        	   items: [
	            {
		        	xtype: 'button',
		            text: 'List',
		            handler: 'onList',
		        },
		        {   
		        	text: 'Actions',
		        	menu: 
		            {
				        xtype : 'menu',
				        
				        items:[
		                    {text: "Import", disabled: true},
		                    {text: "Export", disabled: true},
		                    {xtype: 'menuseparator'},
				            {text : 'Delete all records...'}
				        ]
			    	}
		        } // menu
          ] } // buttongroup
        ] // tbar
    },{
        region: 'center',
        xtype: 'tabpanel',
        reference: 'mainTabs',
        cls: 'mainTabPanel',
        bind: {
        	title: '<h4>Carrier Pull Table ({whse})</h4>'
        },
        items:[{
            title: 'Criteria',
            html: '',
            items: [
                    {
						xtype: 'form',
//						layout: 'form',
						collapsible: false,
						id: 'simpleForm',
						url: 'save-form.php',
						frame: true,
						//title: 'Simple Form',
						fieldDefaults: {
						    //msgTarget: 'side',
//						    labelWidth: 75,
						    //labelAlign: 'left',
						},
						// Listeners
						defaults: {
							listeners: {
								specialkey: function(field,e) {
									if (e.getKey() == e.ENTER) {
									}
								}
							},
						},
						defaultType: 'textfield',
						items: [ {
							xtype: 'fieldset',
							title: 'General',
							defaultType: 'textfield',
							columnWidth: .5,
							layout: 'column',
							defaults: {
								labelAlign: 'top',
								margin: '0 0 0 10',
								columnWidth: 0.25,
								listeners: {
									// VC: this is a valid way to listen to field udpates,
									// however we're doing it in the controller
//									change: function(field, newVal, oldVal) {
//									}
								}
							},
							items: [
								{ fieldLabel: 'Ship to Zip',
								  name: 'RGHICarrierPull.shipToZip',
								  allowBlank: true
								},
								{ fieldLabel: 'Ship Via',
								  name: 'RGHICarrierPull.shipVia',
								  xtype: 'combobox',
								  queryMode: 'local', 
								  allowBlank: true,
								  forceSelection: false,
								  triggerAction: 'all',
								  maxWidth: 100,
								  displayField : 'shipVia',
								  valueField : 'shipVia',
								  store : 'ShipViaStore'
//								  listeners: {
//										// VC: this is a valid way to listen to field udpates,
//										// however we're doing it in the controller
//										change: function(self, newValue, oldValue, eOpts) {
//											console.log(newValue);
//											if(!self.getValue() || self.getValue().length==0) {
//												console.log('attempting to clear combobox');
//												self.reset();
//											}
//										},
//										
//								  },
//								  store: {
//								        autoLoad: true, 
//								        fields: ['id','text'], 
//								        proxy: { 
//								            type: 'ajax', 
//								            url: 'getShipVias' 
//								        }, 
//								        
//								        listeners: {
//											load: function(self,records,successfull,opts) {
//												self.insert(0,{})
//											}
//										}  
//								  
//								  }
								},
								
								{ fieldLabel: 'Pull Trailer Code',
								  name: 'RGHICarrierPull.pullTrlrCode',
								  allowBlank: true
								}
						    ],
						}  // end fieldset
					  ] // form items,
                    }  // end form
                  ] // Criteria tab items
        	
        },
        {
            title: 'Data',
            layout: 'fit',
            items: [ {// Ext.create('Ext.grid.Panel',{
                xtype: 'grid',
//                title: 'SKU',
                reference: 'mainGrid',
                
//                selModel: {
//                	checkOnly: true,
//                	injectCheckbox: 0
//                },
                viewConfig: {
                    enableTextSelection: true
                },
//                store: Ext.data.StoreManager.lookup('carrierPullStore'),
                store: 'CarrierPullStore',
                bufferedRenderer: false,
                sortableColumns: false,
                tbar: [{
                    text: 'Delete',
                    handler: 'onDeleteCarrierPull'
                },
                {
                    text: 'Edit',
                    handler: 'onEditClick'
                },
                {
                    text: 'Add',
                    handler: 'onAddClick'
                }
                ], // tbar items
                
                
//                selType: 'cellmodel',
//                plugins: {
//                    ptype: 'cellediting',
//                    clicksToEdit: 1
//                },
               
                  plugins: [rowEditing],
//                plugins: {
//                    ptype: 'rowediting',
//                    clicksToEdit: 2,
//                    pluginId: 'rowEditor'
//                    	listeners: {
//    	                	beforeedit: function(editor, e, opts) {
//    	                		console.log('beforeedit')
//    	                	}
//    	                }
//                }, // grid plugins
	                
                selType: 'checkboxmodel',
                selModel: {
                	checkOnly: true,
                	injectCheckbox: 0
                },
                //fieldLabel: 'Ship Via'
                columns: [
                    { text: 'Ship Via',  dataIndex: 'shipVia', header: 'Ship Via', sortable: false,editor: {  name: 'RGHICarrierPull.Add.shipVia',
																											  xtype: 'combobox',
																											  queryMode: 'local', 
																											  allowBlank: false,
																											  forceSelection: true,
																											  triggerAction: 'all',
																											  maxWidth: 100,
																											  displayField : 'shipVia',
																											  valueField : 'shipVia',
																											  store : 'ShipViaStore'} },
                    { text: 'Ship Via Description',  dataIndex: 'shipViaDesc', header: 'Ship Via Description', sortable: false, width: 200 },
                    { text: 'Pull Trailer Code',  dataIndex: 'pullTrlrCode', header: 'Pull Trailer Code',editor: {allowBlank: true}, sortable: false  },
                    { text: 'Pull Time',  dataIndex: 'pullTime', header: 'Pull Time', editor: {allowBlank: true}, sortable: false },
                    { text: 'Pull Time AMPM',  dataIndex: 'pullTimeAMPM', header: 'Pull Time AM/PM', editor: {allowBlank: true}, sortable: false },
                    { text: 'Ship To Zip',  dataIndex: 'shipToZip', header: 'Ship To Zip', sortable: false,editor: {allowBlank: false}  },
                    { text: 'Any Text1',  dataIndex: 'anyText1', header: 'Any Text1', sortable: false, editor: {allowBlank: true}  },
                    { text: 'Any Nbr1',  dataIndex: 'anyNbr1', header: 'Any Nbr1', sortable: false, editor: {allowBlank: true}  },
                    { text: 'Create Date/Time',  dataIndex: 'createDateTime',header: 'Create Date/Time', sortable: false, width: 100  },
                    { text: 'Mod Date/Time',  dataIndex: 'modDateTime',header: 'Mod Date/Time', sortable: false, width: 100  },
                    { text: 'User Id',  dataIndex: 'userId',header: 'User Id', sortable: false  }
                ],
             // paging bar on the bottom
                bbar: { // Ext.create('Ext.toolbar.Paging', {  // PagingToolbar', {
                    xtype: 'pagingtoolbar',
                    reference: 'pager',
//                    store: Ext.data.StoreManager.lookup('carrierPullStore'),
                	store: 'CarrierPullStore',
                    pageSize: 25,
                    displayInfo: true,
                    displayMsg: 'Displaying items {0} - {1} of {2}',
                    emptyMsg: "No items to display",
//                    items:[
//                        '-', {
//                        text: 'Show Preview',
//                        //pressed: pluginExpanded,
//                        enableToggle: true,
//                        toggleHandler: function(btn, pressed) {
//                            var preview = Ext.getCmp('gv').getPlugin('preview');
//                            preview.toggleExpanded(pressed);
//                        }
//                    }]
                }
                
            }
            ]}
            
            ] // tabpanel
        }] // outer panel
        
    });

