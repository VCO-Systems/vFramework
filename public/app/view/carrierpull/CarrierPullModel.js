/**
 * This class is the view model for CartonInquiry screen.
 */
Ext.define('vfw.view.carrierpull.CarrierPullModel', {
    extend: 'Ext.data.Model',

    alias: 'viewmodel.carrierpull',

    data: {
        name: 'Hello World'
    },
    fields: ['whse','shiptoZip','shipVia','pullTrlrCode','pullTime']

    //TODO - add data, formulas and/or methods to support your view
});