// The store for items
Ext.define('vfw.store.ShipViaStore',{
	extend: 'Ext.data.Store',
	model: 'vfw.view.shipVia.ShipViaModel',
	storeId: 'shipViaStore',
	autoLoad : true,
	pageSize: 50,
	//remoteFilter: true,
	//remoteSort: true,
	//remoteGroup: true,
	//simpleFilter: false,
	//simpleSort: false,
	//autoFilter: false,
	proxy: {
		type: 'ajax',
		url: 'getShipVias',
		//filterParam: 'filters',
		//limitParam: 'pageSize',
//		paramsAsJson: true,
//		actionMethods: {
//			create: 'GET',
//			read: 'GET',
//			update: 'GET',
//			destroy: 'GET'
//		},
//		headers: {
//	        'Content-Type': 'application/json',
//	        'Accept': 'application/json'
//	    },
//		reader: {
//			type: 'json',
//			rootProperty: 'data',
//			//totalProperty: 'totalrows'
//		},
//		writer: {
//			type: 'json',
//			allowSingle: true
//		}
	}
});

