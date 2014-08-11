/**
 * This class is the main view for the application. It is specified in app.js as the
 * "autoCreateViewport" property. That setting automatically applies the "viewport"
 * plugin to promote that instance of this class to the body element.
 *
 */


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
        tbar: [{
        	xtype: 'button',
            text: 'List',
            handler: 'onList',
        }
        ]
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
                selType: 'checkboxmodel',
                selModel: {
                	checkOnly: true,
                	injectCheckbox: 0
                },
                viewConfig: {
                    enableTextSelection: true
                },
//                store: Ext.data.StoreManager.lookup('carrierPullStore'),
                store: 'CarrierPullStore',
                bufferedRenderer: false,
                tbar: [{
                    text: 'Delete',
                    iconCls: 'delete',
                    handler: 'onDeleteCarrierPull'
                    
                }
                ],
                columns: [
                    { text: 'Ship Via',  dataIndex: 'shipVia' },
                    { text: 'Ship Via Description',  dataIndex: 'todo' },
                    { text: 'Pull Trailer Code',  dataIndex: 'pullTrlrCode' },
                    { text: 'Pull Time',  dataIndex: 'pullTime',
                    	renderer: function(value) {
                    		var tm="";
                    		var parts = value.split(":");
                    		tm += ((parts[0] <= 11) ? (parseInt(parts[0])) : (parseInt(parts[0]) - 12) + 1);
                    		tm+= ":" + parts[1];
                    		tm += (parts[0] <= 11) ? " AM" : " PM";
                    		return tm;
                    	} },
                    { text: 'Ship To Zip',  dataIndex: 'shiptoZip' }
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

