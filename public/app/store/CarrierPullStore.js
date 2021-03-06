// The store for items
Ext.define('vfw.store.CarrierPullStore',{
	extend: 'Ext.data.Store',
	model: 'vfw.view.carrierpull.CarrierPullModel',
	storeId: 'carrierPullStore',
	pageSize: 25,
	remoteFilter: true,
	remoteSort: true,
	remoteGroup: true,
	simpleFilter: false,
	simpleSort: false,
	autoFilter: false,
	proxy: {
		type: 'ajax',
		url: 'getCarrierPull',
		filterParam: 'filters',
		limitParam: 'pageSize',
		paramsAsJson: true,
		actionMethods: {
			create: 'POST',
			read: 'POST',
			update: 'POST',
			destroy: 'POST'
		},
		headers: {
	        'Content-Type': 'application/json',
	        'Accept': 'application/json'
	    },
		reader: {
			type: 'json',
			rootProperty: 'data',
			totalProperty: 'totalrows'
		},
		writer: {
			type: 'json',
			allowSingle: true
		}
	}
});

