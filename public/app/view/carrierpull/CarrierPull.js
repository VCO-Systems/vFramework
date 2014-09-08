
/**
 * The view model for carrier pull.
 */
Ext.define('CarrierPullViewModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.CarrierPullViewModel', // connects to viewModel/type below

    data: {
        whse: '',
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
        clicksToEdit: 2,
        autoCancel: true,
        pluginId: 'rowEditingPlugin',
        listeners: {
        	beforeedit: function(editor,context,opts) {
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
        		
        		// To revert changes to row: editor.cancelEdit();
        		
        		// Validate any field values, then return true
        		// to commit change in UI, close editor, and
        		// mark changed fields dirty
        		this.fireEvent('saveRecordEvent');
        		return true;
        	},
        	canceledit: function ( editor, context, eOpts ) {
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
		            handler: 'onList'
		        },
		        {   
		        	text: 'Actions',
		        	menu: 
		            {
				        xtype : 'menu',
				        items:[
		                    {text: "Import"},
		                    {text: "Export"},
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
							}
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
								  allowBlank: true,
								  maxLength:11,
								  enforceMaxLength: true
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
//											if(!self.getValue() || self.getValue().length==0) {
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
								  allowBlank: true,
								  maxLength: 8,
								  enforceMaxLength: true,
								}
						    ]
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
                listeners: {
                	itemclick: function( view, record, item, index, e, eOpts ) {
                		console.log(record.data.anyNbr1);
                	}
                },
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
                enableColumnHide: false,
                enableColumnMove: false,
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
                  plugins: [rowEditing],
                  
//                
//                  selModel: Ext.create('Ext.selection.CheckboxModel', {
////                  selModel: Ext.create('vfw.patch.Ext.selection.CheckboxModel', {
//                  	mode: 'SIMPLE',
//                  	enableKeyNav: true
//                  }),
//                  modelValidation: true,
                //fieldLabel: 'Ship Via'
                columns: [
                    { xtype : 'checkcolumn', text : '', dataIndex : 'isSelected', stopSelection: false 
                    	,listeners: {
                    		'checkchange': function( thisItem, rowIndex, checked, eOpts ) {
                				var grid = thisItem.ownerCt.grid;
//                				grid.getSelectionModel().selectAll();
                    			var selectedRows = grid.getStore().getRange(rowIndex,rowIndex);
                				// Clear the dirty marker from the row
                				Ext.each(selectedRows,function(item) {
                					item.commit();
                					console.log('setting row ' + rowIndex + " as selected");
                					grid.getSelectionModel().select(item,true); 
                				});
                				// Mark this row as selected
//                				console.log(grid.selModel);
                			}
                    	}},
                    { text: 'Ship Via',  dataIndex: 'shipVia', header: 'Ship Via', sortable: true,editor: {  name: 'RGHICarrierPull.Add.shipVia',
																											  xtype: 'combobox',
																											  queryMode: 'local', 
																											  allowBlank: false,
																											  forceSelection: true,
																											  triggerAction: 'all',
																											  maxWidth: 100,
																											  displayField : 'shipVia',
																											  valueField : 'shipVia',
																											  store : 'ShipViaStore'} },
                    { text: 'Ship Via Description',  dataIndex: 'shipViaDesc', header: 'Ship Via Description', sortable: true, width: 200 },
                    { text: 'Pull Trailer Code',  dataIndex: 'pullTrlrCode', header: 'Pull Trailer Code', sortable: true
                    	,editor: {
                    		allowBlank: true,
                    		validator: function(value) {
                    			if (value.length>8) return 'max length: 8 characters';
                    			return true;
                    		}
                    	}},
                    { text: 'Pull Time',  dataIndex: 'pullTime', header: 'Pull Time', sortable: true,
                    		editor: {
                    			allowBlank: true,
                    			validator: function(value) {
                    				if (value.length>5) return 'max length: 5 characters';
                        			return true;
                    			}}
                    			},
                    { text: 'Pull Time AMPM',  dataIndex: 'pullTimeAMPM', header: 'Pull Time AM/PM', sortable: true 
                    	, editor: {
                    		allowBlank: true,
                    		validator: function(value) {
                				if (value.length>2) return 'max length: 2 characters';
                    			return true;
                			}
                    	}},
                    { text: 'Ship To Zip',  dataIndex: 'shipToZip', header: 'Ship To Zip', sortable: true
                		,editor: {
                			validator: function(value) {
                				if ((value.length<5) ||(value.length>11)) return 'must be 5-11 characters';
                    			return true;
                			}
                    		,allowBlank: false
                		}
                    	
                    },
                    { text: 'Any Text1',  dataIndex: 'anyText1', header: 'Any Text1', sortable: true
                    	,maxLength: 10, enforceMaxLength: true
                    	, editor: {
                    		validator: function(value) {
                				if (value.length>10) return 'max length: 10 characters';
                    			return true;
                			}
                    	}  
                    },
                    { text: 'Any Nbr1',  dataIndex: 'anyNbr1', header: 'Any Nbr1', sortable: true
                    	, editor: {
                    		allowBlank: true,
                    		validator: function(value) {
                    			if (value && (isNaN(value) || value.indexOf(".")!=-1)){
                    				return 'Must be an integer value.';
                    			}
                    			return true;
                    		}
                    	}  
                    },
                    { text: 'Create Date/Time',  dataIndex: 'createDateTime',header: 'Create Date/Time', sortable: true, width: 100  },
                    { text: 'Mod Date/Time',  dataIndex: 'modDateTime',header: 'Mod Date/Time', sortable: true, width: 100  },
                    { text: 'User Id',  dataIndex: 'userId',header: 'User Id', sortable: true  }
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
                    emptyMsg: "No items to display"
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

