/**
 * This class is the main view for the application. It is specified in app.js as the
 * "autoCreateViewport" property. That setting automatically applies the "viewport"
 * plugin to promote that instance of this class to the body element.
 *
 * TODO - Replace this content of this view to suite the needs of your application.
 */


Ext.define('vfw.view.LabelReprint.LabelReprint', {
    extend: 'Ext.container.Container',

    xtype: 'app-main',
    requires: [
               'vfw.view.LabelReprint.LabelReprintController',
               'vfw.view.LabelReprint.LabelReprintModel',
               'vfw.view.LabelReprint.LabelReprintStore'
               ],
    controller: 'labelreprint',
    viewModel: {
        type: 'main'
    },

    layout: {
        type: 'border'
    },

    items: [{
        xtype: 'panel',
        region: 'north',
        html: '<h4>Load Inquiry</h4>',
        width: 250,
        split: true,
        tbar: [{
        	xtype: 'button',
            text: 'List',
            handler: 'onList',
        },
        {
            text: 'Reprint Master Shipping Label',
            handler: 'onReprintMasterShippingLabel',
        }
        ]
    },{
        region: 'center',
        xtype: 'tabpanel',
        reference: 'mainTabs',
        cls: 'mainTabPanel',
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
							title: '',
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
								{ fieldLabel: 'Load Number:',
								  name: 'PktHdr.planLoadNbr',
								  allowBlank: true
								},
								{ fieldLabel: 'From Load:',
									  name: 'from_PktHdr.planLoadNbr',
									  allowBlank: true
								},
								{ fieldLabel: 'To Load:',
									  name: 'from_PktHdr.planLoadNbr',
									  allowBlank: true
								},
								{ fieldLabel: 'Shipment Number:',
									  name: 'PktHdr.planShpmtNbr',
									  allowBlank: true
								},
								{ fieldLabel: 'From Shipment:',
									  name: 'from_PktHdr.planShpmtNbr',
									  allowBlank: true
								},
								{ fieldLabel: 'To Shipment:',
									  name: 'to_PktHdr.planShpmtNbr',
									  allowBlank: true
								},
								{ fieldLabel: 'Pickticket Ctrl Nbr:',
									  name: 'PktHdr.pktCtrlNbr',
									  allowBlank: true
								},
								{ fieldLabel: 'From Status:',
									  name: 'from_PktHdr.pktHdrIntrnl.statCode',
									  allowBlank: true
								},
								{ fieldLabel: 'To Status:',
									  name: 'to_PktHdr.pktHdrIntrnl.statCode',
									  allowBlank: true
								},
								{ fieldLabel: 'Wave:',
									  name: 'PktHdr.pktHdrIntrnl.shipWaveNbr',
									  allowBlank: true
								},
								
							        ],
						},  // end fieldset
						
						] // form items,
                    }  
                  ] // Criteria tab items
        	
        },
        {
            title: 'List',
            //layout: 'fit',
            items: [ {// Ext.create('Ext.grid.Panel',{
                xtype: 'grid',
                maxHeight: 450,
                title: 'SKU',
                reference: 'mainGrid',
                selType: 'checkboxmodel',
                selModel: {
                	checkOnly: true,
                	injectCheckbox: 0
                },
                store: Ext.data.StoreManager.lookup('LabelReprintStore'),
                columns: [
                    { text: 'Load Nbr',  dataIndex: 'plan_load_nbr' },
                    { text: 'Shipment Nbr',  dataIndex: 'plan_shpmt_nbr' },
                    { text: 'WHSE',  dataIndex: 'whse' },
                    { text: 'Nbr of Pkts',  dataIndex: '' },
                    { text: 'Nbr of Cartons',  dataIndex: '' },
                    { text: 'Load Status',  dataIndex: 'stat_code' },
                    
                ],
             // paging bar on the bottom
                bbar: { // Ext.create('Ext.toolbar.Paging', {  // PagingToolbar', {
                    xtype: 'pagingtoolbar',
                	store: Ext.data.StoreManager.lookup('LabelReprintStore'),
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

