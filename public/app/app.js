/*
 * This file is generated and updated by Sencha Cmd. You can edit this file as
 * needed for your application, but these edits will have to be merged by
 * Sencha Cmd when upgrading.
 */
Ext.application({
    name: 'vfw',

    extend: 'vfw.Application',
    
    //autoCreateViewport: 'vfw.view.main.Main',
//    autoCreateViewport: 'vfw.view.carton.CartonInquiry',
    autoCreateViewport: 'vfw.view.carrierpull.CarrierPull',
    appFolder: '/assets/app',
//    requires: ['vfw.patch.ExtJs501Patch'] // Overrides and bug fixes for ExtJS 5.0.1
    
	
    //-------------------------------------------------------------------------
    // Most customizations should be made to extjs.Application. If you need to
    // customize this file, doing so below this section reduces the likelihood
    // of merge conflicts when upgrading to new versions of Sencha Cmd.
    //-------------------------------------------------------------------------
});
