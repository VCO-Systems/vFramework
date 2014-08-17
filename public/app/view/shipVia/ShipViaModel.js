/**
 * This class is the view model for ShipVia.
 */
Ext.define('vfw.view.shipVia.ShipViaModel', {
    extend: 'Ext.data.Model',

    alias: 'viewmodel.shipVia',

    data: {
        //name: 'Hello World'
    },
    
    fields: ['shipVia', 
             'shipViaDesc', 
             'carrId', 
             'servType', 
             'labelType', 
             'billShipVia', 
             'insurCoverCode', 
             'servLevelIndic', 
             'icon', 
             'tmsCarrCode', 
             'frtBasedOnNbrOfCarton', 
             'modeOfTransport', 
             'insurType', 
             'createDateTime', 
             'modDateTime', 
             'userId' 
            ]

    //TODO - add data, formulas and/or methods to support your view
});