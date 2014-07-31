// The store for items
Ext.define('vfw.view.LabelReprint.LabelReprintStore',{
	extend: 'Ext.data.Store',
	storeId: 'labelReprintStore',
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
		url: 'getLabelReprintData',
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
	model: 'vfw.view.LabelReprint.LabelReprintModel'
});

