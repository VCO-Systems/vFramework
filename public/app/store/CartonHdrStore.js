// The store for items
Ext.define('vfw.store.CartonHdrStore',{
	extend: 'Ext.data.Store',
	autoload: true,
	pageSize: 10,
	proxy: {
		type: 'ajax',
		url: 'getCartons',
		reader: {
			type: 'json',
			rootProperty: 'items',
			totalProperty: 'total'
		}
	},
	model: 'vfw.model.CartonHdr'
});

