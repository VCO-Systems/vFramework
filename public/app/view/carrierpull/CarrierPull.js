/**
 * This class is the main view for the application. It is specified in app.js as the
 * "autoCreateViewport" property. That setting automatically applies the "viewport"
 * plugin to promote that instance of this class to the body element.
 *
 */

var editModeColumns = [
	{ text: 'Ship Via',  dataIndex: 'shipVia', header: 'Ship Via', sortable: false },
	{ text: 'Ship Via Description',  dataIndex: 'todo', header: 'Ship Via Description', sortable: false  },
	{ text: 'Pull Trailer Code',  dataIndex: 'pullTrlrCode', header: 'Pull Trailer Code',editor: {allowBlank: true}, sortable: false  },
	{ text: 'Pull Time',  dataIndex: 'pullTime', header: 'pullTime', editor: {allowBlank: true}, sortable: false },
	{ text: 'Pull Time AMPM',  dataIndex: 'pullTimeAMPM', header: 'Pull Time', editor: {allowBlank: true}, sortable: false },
	{ text: 'Ship To Zip',  dataIndex: 'shipToZip',header: 'shiptoZip', sortable: false  }
];

var addModeColumns = [
                   	{ text: 'Ship Via',  dataIndex: 'shipVia', header: 'shipVia',editor: {allowBlank: true}, sortable: false },
                   	{ text: 'Ship Via Description',  dataIndex: 'todo', header: 'shipViaDescr', sortable: false  },
                   	{ text: 'Pull Trailer Code',  dataIndex: 'pullTrlrCode', header: 'pullTrlrCode',editor: {allowBlank: true}, sortable: false  },
                   	{ text: 'Pull Time',  dataIndex: 'pullTime', header: 'pullTime', editor: {allowBlank: true}, sortable: false },
                   	{ text: 'Pull Time AMPM',  dataIndex: 'pullTimeAMPM', header: 'Pull Time AM/PM', editor: {allowBlank: true}, sortable: false },
                   	{ text: 'Ship To Zip',  dataIndex: 'shipToZip',header: 'Ship To Zip', editor: {allowBlank: false}, sortable: false  }
                   ];

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
        		console.debug("plugins", grid.plugins);
//        		
        		// try to loop over the column objects
//        		console.debug(context.grid.columns);
//        		for (var idx = 0; idx < context.grid.getColumnModel().length; idx++ ) {
//        			var column = context.grid.getColumnModel()[idx];
//        			console.debug(column);
////        			column.editable=false;
//        			column.setEditable(idx,false);
//        		}
        		
//        		console.debug(context.store.getAt(context.rowIdx).get('shipVia')); 
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
        		console.debug(editor.grid);
        		return true;
        	},
        	edit: function(editor, e) {
        		var record = e.record;
        		console.debug(e);
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
        		    	console.debug(editor);
        		    	editor.grid.getStore().load();
        		    },                                    
        		    failure: function(){console.log('failure');}
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
        type: 'main'
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
        title: '<h4>Carrier Pull Table</h4>',
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
//										console.debug(field);
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
//										console.debug(field);
//									}
								}
							},
							items: [
								{ fieldLabel: 'Ship to Zip',
								  name: 'RGHICarrierPull.shiptoZip',
								  allowBlank: true
								},
								{ fieldLabel: 'Ship Via',
								  name: 'RGHICarrierPull.shipVia',
								  xtype: 'combobox',
								  queryMode: 'local', 
								  allowBlank: true,
//								  forceSelection: true,
								  triggerAction: 'all',
								  maxWidth: 100,
								  listeners: {
										// VC: this is a valid way to listen to field udpates,
										// however we're doing it in the controller
										change: function(self, newValue, oldValue, eOpts) {
											console.log(newValue);
											if(!self.getValue() || self.getValue().length==0) {
												console.log('attempting to clear combobox');
												self.reset();
											}
										},
										
								  },
								  store: {
								        autoLoad: true, 
								        fields: ['id','text'], 
								        proxy: { 
								            type: 'ajax', 
								            url: 'getShipVias' 
								        } ,
								        
								        listeners: {
											load: function(self,records,successfull,opts) {
												self.insert(0,{})
											}
										}  
								  
								  }
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
                
                columns: [
                    { text: 'Ship Via',  dataIndex: 'shipVia', header: 'Ship Via', sortable: false,editor: {allowBlank: false} },
                    { text: 'Ship Via Description',  dataIndex: 'todo', header: 'Ship Via Description', sortable: false  },
                    { text: 'Pull Trailer Code',  dataIndex: 'pullTrlrCode', header: 'Pull Trailer Code',editor: {allowBlank: true}, sortable: false  },
                    { text: 'Pull Time',  dataIndex: 'pullTime', header: 'Pull Time', editor: {allowBlank: true}, sortable: false },
                    { text: 'Pull Time AMPM',  dataIndex: 'pullTimeAMPM', header: 'Pull Time AM/PM', editor: {allowBlank: true}, sortable: false },
                    { text: 'Ship To Zip',  dataIndex: 'shipToZip',header: 'Ship To Zip', sortable: false,editor: {allowBlank: false}  }
                ],
             // paging bar on the bottom
                bbar: { // Ext.create('Ext.toolbar.Paging', {  // PagingToolbar', {
                    xtype: 'pagingtoolbar',
//                    store: Ext.data.StoreManager.lookup('carrierPullStore'),
                	store: 'CarrierPullStore',
                    pageSize: 25,
                    displayInfo: true,
                    displayMsg: 'Displaying items {0} - {1} of {2}',
                    emptyMsg: "No items to display",
                    items:[
                        '-', {
                        text: 'Show Preview',
                        //pressed: pluginExpanded,
                        enableToggle: true,
                        toggleHandler: function(btn, pressed) {
                            var preview = Ext.getCmp('gv').getPlugin('preview');
                            preview.toggleExpanded(pressed);
                        }
                    }]
                }
                
            }
            ]}
            
            ] // tabpanel
        }] // outer panel
        
    });

