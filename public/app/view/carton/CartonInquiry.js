/**
 * This class is the main view for the application. It is specified in app.js as the
 * "autoCreateViewport" property. That setting automatically applies the "viewport"
 * plugin to promote that instance of this class to the body element.
 *
 * TODO - Replace this content of this view to suite the needs of your application.
 */


Ext.define('vfw.view.carton.CartonInquiry', {
    extend: 'Ext.container.Container',

    xtype: 'app-main',
    requires: [
               'vfw.view.carton.CartonInquiryController',
               'vfw.model.CartonInquiry'
//               'vfw.store.CartonInquiryStore'
               ],
    controller: 'cartoninquiry',
    viewModel: {
        type: 'main'
    },

    layout: {
        type: 'border'
    },

    items: [{
        xtype: 'panel',
        region: 'north',
        html: '<h4>Carton Inquiry</h4>',
        width: 250,
        split: true,
        tbar: [{
        	xtype: 'button',
            text: 'List',
            handler: 'onList',
        },
        {
            text: 'Print Carton Label',
            handler: 'onPrintCartonLabel',
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
							title: 'Carton Hdr',
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
								{ fieldLabel: 'From Carton:',
								  name: 'from_CartonHdr.carton_nbr',
								  allowBlank: true,disabled: true
								},
								{ fieldLabel: 'To Carton:',
								  name: 'to_CartonHdr.carton_nbr',
								  allowBlank:true,
								  disabled: true
								},
								{ fieldLabel: 'Carton Nbr:',
								  name: 'CartonHdr.carton_nbr',
								  allowBlank: true
								},
								{ fieldLabel: 'Pickticket Ctrl Nbr:',
								  name: 'CartonHdr.pkt_ctrl_nbr',
								  allowBlank:true, disabled: true
								},
								{ fieldLabel: 'Wave:',
								  name: 'CartonHdr.wave_nbr',
								  allowBlank:true, disabled: true
								},
								{ fieldLabel: 'Load:',
								  name: 'CartonHdr.load_nbr',
								  allowBlank:true, disabled: true
								},
								{ fieldLabel: 'Outbound Shipment:',
								  name: 'CartonHdr.shpmt_nbr',
								  allowBlank:true, disabled: true
								},
								{ fieldLabel: 'From Status:',
								  name: 'from_CartonHdr.stat_code',
								  allowBlank:true, disabled: true
								},
								{ fieldLabel: 'To Status:',
								  name: 'to_CartonHdr.stat_code',
								  allowBlank:true, disabled: true
								}
							        ],
						},  // end fieldset
						{
							xtype: 'fieldset',
							title: 'Carton Detail',
							defaultType: 'textfield',
							layout: 'column',
							defaults: {
								labelAlign: 'top',
								margin: '0 0 0 10px',
								columnWidth: 0.25,
							},
							items: [
								{ fieldLabel: 'Size Desc:',
								  name: 'ItemMaster.size_desc',
								  allowBlank:true, disabled: true
								},
								{ fieldLabel: 'Style:',
								  name: 'ItemMaster.style',
								  allowBlank:true, disabled: true
								},
								{ fieldLabel: 'Style Sfx:',
								  name: 'ItemMaster.style_sfx',
								  allowBlank:true, disabled: true
								},
								{ fieldLabel: 'Color:',
								  name: 'ItemMaster.color',
								  allowBlank:true, disabled: true
								},
								{ fieldLabel: 'Color Sfx:',
								  name: 'ItemMaster.color_sfx',
								  allowBlank:true, disabled: true
								},
								{ fieldLabel: 'Sec Dim:',
								  name: 'txtSecDim',
								  allowBlank:true, disabled: true,
								  reference: 'fldSecDim'
								},
								{ fieldLabel: 'Quality:',
								  name: 'txtQuality',
								  allowBlank:true, disabled: true,
								  reference: 'fldQuality'
								},
								{ fieldLabel: 'Season:',
								  name: 'txtSeason',
								  allowBlank:true, disabled: true,
								  reference: 'fldSeason'
								},
								{ fieldLabel: 'Year:',
								  name: 'txtYear',
								  allowBlank:true, disabled: true,
								  reference: 'fldYear'
								}
							        ],
						}  // end fieldset 
						] // form items,
                    }  
                  ] // Criteria tab items
        	
        },
        {
            title: 'List',
            //layout: 'fit',
            items: [ {// Ext.create('Ext.grid.Panel',{
                xtype: 'grid',
                maxHeight: 550,
                title: 'SKU',
                reference: 'mainGrid',
                selType: 'checkboxmodel',
                selModel: {
                	checkOnly: true,
                	injectCheckbox: 0
                },
                store: 'CartonInquiryStore',
                bufferedRenderer: false,
                columns: [
                    { text: 'Carton Nbr',  dataIndex: 'carton_nbr' },
                    { text: 'Carton Seq Nbr',  dataIndex: 'carton_seq_nbr' },
                    { text: 'Carton Creation Code', dataIndex: 'carton_creation_code'},
                    { text: 'PKT_CTRL_NBR',  dataIndex: 'pkt_ctrl_nbr' },
                    { text: 'WHSE',  dataIndex: 'whse' },
                    { text: 'Wave Nbr',  dataIndex: 'wave_nbr' },
                    { text: 'TO_BE_PAKD_UNITS',  dataIndex: 'to_be_pakd_units' },
                    { text: 'Units PKD',  dataIndex: 'units_pakd' },
                    { text: 'Size Desc',  dataIndex: 'size_desc'},
                    { text: 'Style',  dataIndex: 'style' },
                    { text: 'Style SFX',  dataIndex: 'style_sfx' },
                    { text: 'Color',  dataIndex: 'color' },
                    { text: 'Color SFX',  dataIndex: 'color_sfx' },
                    { text: 'SKU Brcd',  dataIndex: 'sku_brcd' },
                    { text: 'SKU Desc',  dataIndex: 'sku_desc' },
                    { text: 'sec_dim',  dataIndex: 'sec_dim' },
                    { text: 'Qual',  dataIndex: 'qual' },
                    { text: 'season',  dataIndex: 'season' },
                    { text: 'season_yr',  dataIndex: 'season_yr' }
                    
                ],
             // paging bar on the bottom
                bbar: { // Ext.create('Ext.toolbar.Paging', {  // PagingToolbar', {
                    xtype: 'pagingtoolbar',
                	store: 'CartonInquiryStore',
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

