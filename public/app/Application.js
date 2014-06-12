/**
 * The main application class. An instance of this class is created by app.js when it calls
 * Ext.application(). This is the ideal place to handle application launch and initialization
 * details.
 */
Ext.define('vfw.Application', {
    extend: 'Ext.app.Application',
    
    name: 'vfw',
    appFolder: 'assets/app',

    views: [
        // TODO: add views here
    ],
    controllers: ['Root',],
    stores: ['CartonHdrStore'],
    models: ['CartonHdr'],
    
    launch: function () {
        // TODO - Launch the application
    	// console.log('launch');
    },
    
});
