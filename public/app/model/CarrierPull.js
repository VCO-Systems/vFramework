/**
 * This class is the view model for CartonInquiry screen.
 */
Ext.define('vfw.model.CarrierPull', {
    extend: 'Ext.data.Model',

    alias: 'viewmodel.carrierpull',

    data: {
        name: 'Hello World'
    },
    fields: ['whse','shiptoZip','shipVia']

    //TODO - add data, formulas and/or methods to support your view
});