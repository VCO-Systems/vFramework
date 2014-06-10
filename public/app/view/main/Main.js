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
            items: [{
                xtype: 'grid',
                title: 'SKU',
                selType: 'checkboxmodel',
                selModel: {
                	checkOnly: true,
                	injectCheckbox: 0
                },
                store: {
                    fields:['descr', 'barcode', 'si1','si2','si3','si4','si5','si6'],
                    data:[
                        { descr: 'Lisa',  barcode: "AJFOFJ2K13",
                          si1: "555-111-1224"  },
                        { descr: 'Bart',  barcode: "O3I1IJJJFO",
                          si1: "555-222-1234" },
                        { descr: 'Homer', barcode: "FJWOFJ2JR2",
                          si1: "555-222-1244"  },
                        { descr: 'Marge', barcode: "948JAJOF2J",
                          si1: "555-222-1254"  }
                    ],
                    proxy: {
                        type: 'memory'
                    }
                },
                columns: [
                    { text: 'Style Descr',  dataIndex: 'descr' },
                    { text: 'Barcode', dataIndex: 'barcode', flex: 1},
                    { text: 'Special Instr 1', dataIndex: 'si1' },
                    { text: 'Special Instr 2', dataIndex: 'si2' },
                    { text: 'Special Instr 3', dataIndex: 'si3' },
                    { text: 'Special Instr 4', dataIndex: 'si4' },
                    { text: 'Special Instr 5', dataIndex: 'si5' },
                    { text: 'Special Instr 6', dataIndex: 'si6' },
                ],
             // paging bar on the bottom
                bbar: Ext.create('Ext.PagingToolbar', {
                    //store: store,
                    displayInfo: true,
                    displayMsg: 'Displaying topics {0} - {1} of {2}',
                    emptyMsg: "No topics to display",
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
                }),
            }
            
            ]
        }]
    }]
});

