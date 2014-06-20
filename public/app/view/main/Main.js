/**
 * This class is the main view for the application. It is specified in app.js as the
 * "autoCreateViewport" property. That setting automatically applies the "viewport"
 * plugin to promote that instance of this class to the body element.
 *
 * TODO - Replace this content of this view to suite the needs of your application.
 */


Ext.define('vfw.view.main.Main', {
    extend: 'Ext.container.Container',

    xtype: 'app-main',
    requires: [
               'vfw.view.main.MainController'],
    controller: 'mainly',
    viewModel: {
        type: 'main'
    },

    layout: {
        type: 'border'
    },

    items: [{
        xtype: 'panel',
        region: 'north',
        html: '<h4>Item</h4>',
        width: 250,
        split: true,
        tbar: [{
            text: 'List',
            handler: 'showList',
        },
        {
            text: 'Links',                      
            menu: {
                xtype: 'menu',                          
                items: [{
                        text: 'C_VAS',
                        iconCls: 'edit'
                    },
                    {
                    	text: 'Carton Inquiry',
                    	iconCls: 'cartonInquiry'
                    }]                         
            }
        },
        {
            text: 'Actions',                      
            menu: {
                xtype: 'menu',                          
                items: [{
                        text: 'Add',
                        iconCls: 'edit'
                    },
                    {
                        text: 'Change',
                        iconCls: 'edit'
                    },
                    {
                        text: 'Delete',
                        iconCls: 'edit'
                    }]                          
            }
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
						layout: 'form',
						collapsible: true,
						id: 'simpleForm',
						url: 'save-form.php',
						frame: true,
						title: 'Simple Form',
						bodyPadding: '5 5 0',
						width: 350,
						fieldDefaults: {
						    msgTarget: 'side',
						    labelWidth: 75,
						    labelAlign: 'top',
						},
						defaultType: 'textfield',
						items: [{
				            fieldLabel: 'Style Desc:',
				            name: 'txtStyleDesc',
				            allowBlank:true,
				            reference: 'fldStyle'
				        }, {
				            fieldLabel: 'Barcode:',
				            name: 'txtBarcode',
				            allowBlank:true,
				            reference: 'fldBarcode'
				        }],
                    }
                  ]
        
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
                store: 'CartonHdrStore',
                columns: [
                    { text: 'Carton Nbr',  dataIndex: 'carton_nbr' },
                    { text: 'Pkt Ctrl Nbr', dataIndex: 'pkt_ctrl_nbr'},
                    { text: 'whse', dataIndex: 'whse' },
                    { text: 'Seq Rule Prty', dataIndex: 'seq_rule_prty' },
                    { text: 'stat_code', dataIndex: 'stat_code' },
                    { text: 'Stage Indic', dataIndex: 'stage_indic' },
                    { text: 'sngl_sku_flag', dataIndex: 'sngl_sku_flag' },
                    { text: 'SKU id', dataIndex: 'sku_id' },
                ],
             // paging bar on the bottom
                bbar: { // Ext.create('Ext.toolbar.Paging', {  // PagingToolbar', {
                    xtype: 'pagingtoolbar',
                	store: 'CartonHdrStore',
                	pageSize: 10,
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

