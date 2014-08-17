/**
 * This class is the main view for the application. It is specified in app.js as the
 * "autoCreateViewport" property. That setting automatically applies the "viewport"
 * plugin to promote that instance of this class to the body element.
 *
 */

Ext.define('vfw.view.shipVia.ShipViaController', {
    extend: 'Ext.app.ViewController',

    requires: [
        'Ext.MessageBox',
        'Ext.Array'
    ],

    alias: 'controller.shipviactrl'
        
});
