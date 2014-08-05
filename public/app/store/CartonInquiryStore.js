// The store for items
Ext.define('vfw.store.CartonInquiryStore',{
	extend: 'Ext.data.Store',
	storeId: 'cartonInquiryStore',
	autoload: true,
	pageSize: 20,
	remoteFilter: true,
	remoteSort: true,
	remoteGroup: true,
	simpleFilter: false,
	simpleSort: false,
	autoFilter: false,
	proxy: {
		type: 'ajax',
		url: 'getCartons',
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
			allowSingle: false,
		}
	},
	model: 'vfw.view.carton.CartonInquiryModel'
});

