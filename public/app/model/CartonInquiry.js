Ext.define('vfw.model.CartonInquiry',{
    extend: 'Ext.data.Model',
    fields: [
        // CARTON_HDR fields
        'carton_nbr',
        'pkt_ctrl_nbr',
        'whse',
        'wave_nbr',
        // CARTON_DTL fields
        'carton_seq_nbr',
        'units_pakd',
        'to_be_pakd_units',
        // ITEM_MASTER fields
        'size_desc',
        'style',
        'style_sfx',
        'sku_barcd',
        'sku_desc',
        'color',
        'color_sfx',
        'sec_dim',
        'qual',
        'season',
        'season_yr'
	]
});