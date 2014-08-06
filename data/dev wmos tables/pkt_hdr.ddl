CREATE TABLE "VBATCH"."PKT_HDR" 
   (	"PKT_CTRL_NBR" VARCHAR2(10 CHAR) NOT NULL ENABLE, 
	"WHSE" VARCHAR2(3 CHAR), 
	"PKT_NBR" VARCHAR2(11 CHAR), 
	"PKT_SFX" VARCHAR2(3 CHAR), 
	"ORD_NBR" VARCHAR2(8 CHAR), 
	"ORD_SFX" VARCHAR2(3 CHAR), 
	"ORD_TYPE" VARCHAR2(2 CHAR), 
	"SHIPTO" VARCHAR2(10 CHAR), 
	"SHIPTO_NAME" VARCHAR2(35 CHAR), 
	"SHIPTO_ADDR_1" VARCHAR2(75 CHAR), 
	"SHIPTO_ADDR_2" VARCHAR2(75 CHAR), 
	"SHIPTO_ADDR_3" VARCHAR2(75 CHAR), 
	"SHIPTO_CITY" VARCHAR2(40 CHAR), 
	"SHIPTO_STATE" VARCHAR2(3 CHAR), 
	"SHIPTO_ZIP" VARCHAR2(11 CHAR), 
	"SHIPTO_CNTRY" VARCHAR2(4 CHAR), 
	"SOLDTO" VARCHAR2(10 CHAR), 
	"SOLDTO_NAME" VARCHAR2(35 CHAR), 
	"SOLDTO_ADDR_1" VARCHAR2(75 CHAR), 
	"SOLDTO_ADDR_2" VARCHAR2(75 CHAR), 
	"SOLDTO_ADDR_3" VARCHAR2(75 CHAR), 
	"SOLDTO_CITY" VARCHAR2(40 CHAR), 
	"SOLDTO_STATE" VARCHAR2(3 CHAR), 
	"SOLDTO_ZIP" VARCHAR2(11 CHAR), 
	"SOLDTO_CNTRY" VARCHAR2(4 CHAR), 
	"TEL_NBR" VARCHAR2(15 CHAR), 
	"DC_CTR_NBR" VARCHAR2(8 CHAR), 
	"XFER_WHSE" VARCHAR2(3 CHAR), 
	"ACCT_RCVBL_ACCT_NBR" VARCHAR2(10 CHAR), 
	"ACCT_RCVBL_CODE" VARCHAR2(2 CHAR), 
	"CUST_PO_NBR" VARCHAR2(26 CHAR), 
	"CUST_DEPT" VARCHAR2(8 CHAR), 
	"PRO_NBR" VARCHAR2(20 CHAR), 
	"APPT_NBR" VARCHAR2(15 CHAR), 
	"NBR_OF_TIMES_APPT_CHGD" NUMBER(1,0) DEFAULT 0 NOT NULL ENABLE, 
	"APPT_DATE" DATE, 
	"APPT_MADE_BY_ID" VARCHAR2(3 CHAR), 
	"STORE_NBR" VARCHAR2(10 CHAR), 
	"STORE_TYPE" VARCHAR2(2 CHAR), 
	"ADVT_CODE" VARCHAR2(2 CHAR), 
	"ADVT_DATE" DATE, 
	"MERCH_CODE" VARCHAR2(2 CHAR), 
	"MERCH_CLASS" VARCHAR2(2 CHAR), 
	"MERCH_DIV" VARCHAR2(2 CHAR), 
	"VENDOR_NBR" VARCHAR2(12 CHAR), 
	"ORIG_SHIP_VIA" VARCHAR2(4 CHAR), 
	"SHIP_VIA" VARCHAR2(4 CHAR), 
	"SLSPRSN_NBR" VARCHAR2(3 CHAR), 
	"PRTY_CODE" VARCHAR2(2 CHAR), 
	"PRTY_SFX" VARCHAR2(2 CHAR), 
	"TERR_CODE" VARCHAR2(3 CHAR), 
	"MANG_TERR" VARCHAR2(3 CHAR), 
	"SALE_GRP" VARCHAR2(6 CHAR), 
	"MARK_FOR" VARCHAR2(25 CHAR), 
	"BACK_ORD_FLAG" VARCHAR2(1 CHAR), 
	"ORD_DATE" DATE, 
	"PKT_GENRTN_DATE_TIME" DATE, 
	"START_SHIP_DATE" DATE, 
	"STOP_SHIP_DATE" DATE, 
	"SCHED_DLVRY_DATE" DATE, 
	"STOP_SHIP_RULE" VARCHAR2(2 CHAR), 
	"TERMS_CODE" VARCHAR2(2 CHAR), 
	"TERMS_DESC" VARCHAR2(30 CHAR), 
	"TERMS_PCNT" NUMBER(5,2) DEFAULT 0 NOT NULL ENABLE, 
	"PRE_STIKR_CODE" VARCHAR2(1 CHAR), 
	"SHIP_W_CTRL_NBR" VARCHAR2(15 CHAR), 
	"SWC_NBR_SEQ" NUMBER(3,0) DEFAULT 0 NOT NULL ENABLE, 
	"ORD_NBR_FOR_SWC" NUMBER(5,0) DEFAULT 0 NOT NULL ENABLE, 
	"NBR_ORDS_FOR_SWC" NUMBER(5,0) DEFAULT 0 NOT NULL ENABLE, 
	"SHPMT_NBR" VARCHAR2(20 CHAR), 
	"PNH_CTRL_NBR" VARCHAR2(15 CHAR), 
	"PNH_FLAG" VARCHAR2(1 CHAR), 
	"CARTON_CUBNG_INDIC" NUMBER(2,0) DEFAULT 0 NOT NULL ENABLE, 
	"CARTON_LABEL_TYPE" VARCHAR2(3 CHAR), 
	"NBR_OF_LABEL" NUMBER(3,0) DEFAULT 0 NOT NULL ENABLE, 
	"CONTNT_LABEL_TYPE" VARCHAR2(3 CHAR), 
	"NBR_OF_CONTNT_LABEL" NUMBER(3,0) DEFAULT 0 NOT NULL ENABLE, 
	"WAVE_LABEL_TYPE" VARCHAR2(2 CHAR), 
	"NBR_OF_PAKNG_SLIPS" NUMBER(3,0) DEFAULT 0 NOT NULL ENABLE, 
	"PKT_TYPE" VARCHAR2(2 CHAR), 
	"PACK_SLIP_PRT_CNT" NUMBER(2,0) DEFAULT 0 NOT NULL ENABLE, 
	"SEL_RULE_PRTY" NUMBER(5,0) DEFAULT 0 NOT NULL ENABLE, 
	"AUTO_INVC_STAT_CODE" NUMBER(2,0) DEFAULT 0 NOT NULL ENABLE, 
	"REPL_FLAG" VARCHAR2(1 CHAR), 
	"INVN_SHRTG_FLAG" VARCHAR2(1 CHAR), 
	"CUST_RTE" VARCHAR2(1 CHAR), 
	"RTE_GUIDE_NBR" VARCHAR2(10 CHAR), 
	"CONSOL_NBR" VARCHAR2(10 CHAR), 
	"RTE_ATTR" VARCHAR2(30 CHAR), 
	"RTE_TO" VARCHAR2(10 CHAR), 
	"RTE_TYPE_1" VARCHAR2(2 CHAR), 
	"RTE_TYPE_2" VARCHAR2(2 CHAR), 
	"RTE_ZIP" VARCHAR2(11 CHAR), 
	"EST_WT_BRIDGED" NUMBER(13,4) DEFAULT 0 NOT NULL ENABLE, 
	"EST_WT" NUMBER(13,4) DEFAULT 0 NOT NULL ENABLE, 
	"EST_CARTON_BRIDGED" NUMBER(7,0) DEFAULT 0 NOT NULL ENABLE, 
	"EST_VOL" NUMBER(13,4) DEFAULT 0 NOT NULL ENABLE, 
	"TOTAL_DLRS_UNDISC" NUMBER(11,2) DEFAULT 0 NOT NULL ENABLE, 
	"TOTAL_DLRS_DISC" NUMBER(11,2) DEFAULT 0 NOT NULL ENABLE, 
	"DISC_DATE" DATE, 
	"NBR_OF_HNGR" NUMBER(3,0) DEFAULT 0 NOT NULL ENABLE, 
	"ADDR_CODE" VARCHAR2(2 CHAR), 
	"BOL" VARCHAR2(20 CHAR), 
	"BOL_TYPE" VARCHAR2(1 CHAR), 
	"TRLR_STOP_SEQ_NBR" NUMBER(3,0) DEFAULT 0 NOT NULL ENABLE, 
	"MANIF_NBR" VARCHAR2(20 CHAR), 
	"MANIF_TYPE" VARCHAR2(1 CHAR), 
	"PROD_VALUE" NUMBER(11,2) DEFAULT 0 NOT NULL ENABLE, 
	"CARTON_ASN_REQD" VARCHAR2(1 CHAR), 
	"MAX_CARTON_WT" NUMBER(13,4) DEFAULT 0 NOT NULL ENABLE, 
	"MAX_CARTON_UNITS" NUMBER(9,2) DEFAULT 0 NOT NULL ENABLE, 
	"FIRST_ZONE" VARCHAR2(4 CHAR), 
	"LAST_ZONE" VARCHAR2(4 CHAR), 
	"NBR_OF_ZONES" NUMBER(3,0) DEFAULT 0 NOT NULL ENABLE, 
	"PLTZ_ORD" VARCHAR2(1 CHAR), 
	"SHIPTO_WHSE" VARCHAR2(3 CHAR), 
	"PARTL_CARTON_MIN_WT" NUMBER(13,4) DEFAULT 0 NOT NULL ENABLE, 
	"PARTL_CARTON_OPTN" VARCHAR2(1 CHAR) DEFAULT 'P', 
	"MAJOR_PKT_GRP_ATTR" VARCHAR2(50 CHAR), 
	"PKT_CONSOL_PROF" VARCHAR2(3 CHAR), 
	"PACK_SLIP_TYPE" VARCHAR2(2 CHAR), 
	"SKU_INVN_VERF" VARCHAR2(1 CHAR), 
	"THRD_PARTY_BILL" VARCHAR2(20 CHAR), 
	"WHSE_XFER_FLAG" VARCHAR2(1 CHAR), 
	"SPL_INSTR_CODE_1" VARCHAR2(2 CHAR), 
	"SPL_INSTR_CODE_2" VARCHAR2(2 CHAR), 
	"SPL_INSTR_CODE_3" VARCHAR2(2 CHAR), 
	"SPL_INSTR_CODE_4" VARCHAR2(2 CHAR), 
	"SPL_INSTR_CODE_5" VARCHAR2(2 CHAR), 
	"SPL_INSTR_CODE_6" VARCHAR2(2 CHAR), 
	"SPL_INSTR_CODE_7" VARCHAR2(2 CHAR), 
	"SPL_INSTR_CODE_8" VARCHAR2(2 CHAR), 
	"SPL_INSTR_CODE_9" VARCHAR2(2 CHAR), 
	"SPL_INSTR_CODE_10" VARCHAR2(2 CHAR), 
	"CREATE_DATE_TIME" DATE, 
	"MOD_DATE_TIME" DATE, 
	"USER_ID" VARCHAR2(15 CHAR), 
	"ADDR_VALID" VARCHAR2(1 CHAR), 
	"PO_TYPE" VARCHAR2(4 CHAR), 
	"SHIPTO_CONTACT" VARCHAR2(30 CHAR), 
	"PLAN_SHPMT_NBR" VARCHAR2(20 CHAR), 
	"PLAN_LOAD_NBR" VARCHAR2(20 CHAR), 
	"BOL_BREAK_ATTR" VARCHAR2(3 CHAR), 
	"FREIGHT_TERMS" VARCHAR2(1 CHAR), 
	"EST_VOL_BRIDGED" NUMBER(13,4) DEFAULT 0 NOT NULL ENABLE, 
	"EST_CARTON" NUMBER(7,0) DEFAULT 0 NOT NULL ENABLE, 
	"EARLIEST_DLVRY_TIME" VARCHAR2(4 CHAR), 
	"CONS_ACCT_NBR" VARCHAR2(10 CHAR), 
	"IMPORTER_DEFN" VARCHAR2(1 CHAR), 
	"FRT_FORWARDER_ACCT_NBR" VARCHAR2(10 CHAR), 
	"INCO_TERMS" VARCHAR2(3 CHAR) DEFAULT '0', 
	"BILL_ACCT_NBR" VARCHAR2(10 CHAR), 
	"DOCS_ONLY_SHPMT" VARCHAR2(1 CHAR), 
	"PARTIES_RELATED" VARCHAR2(1 CHAR), 
	"COD_FUNDS" VARCHAR2(1 CHAR), 
	"FTSR_NBR" VARCHAR2(32 CHAR), 
	"CUST_BROKER_ACCT_NBR" VARCHAR2(10 CHAR), 
	"PLAN_BOL" VARCHAR2(20 CHAR), 
	"PLAN_MASTER_BOL" VARCHAR2(20 CHAR), 
	"RTE_ID" VARCHAR2(10 CHAR), 
	"RTE_STOP_SEQ" NUMBER(3,0) DEFAULT 0 NOT NULL ENABLE, 
	"INTL_GOODS_DESC" VARCHAR2(35 CHAR), 
	"DUTY_AND_TAX" NUMBER(9,4) DEFAULT 0 NOT NULL ENABLE, 
	"PORT_OF_LOADING" VARCHAR2(19 CHAR), 
	"PORT_OF_DISCHARGE" VARCHAR2(19 CHAR), 
	"RTE_LOAD_SEQ" NUMBER(5,0) DEFAULT 0 NOT NULL ENABLE, 
	"PLT_CUBNG_INDIC" NUMBER(2,0) DEFAULT 0 NOT NULL ENABLE, 
	"SRL_NBR_TRK_FLAG" NUMBER(1,0) DEFAULT 0 NOT NULL ENABLE, 
	"SNGL_UNIT_FLAG" VARCHAR2(1 CHAR), 
	"PLAN_ID" VARCHAR2(11 CHAR), 
	"SCHED_DLVRY_DATE_END" DATE, 
	"TRLR_SIZE" VARCHAR2(3 CHAR), 
	"TRLR_TYPE" VARCHAR2(3 CHAR), 
	"IS_HAZMAT_FLAG" VARCHAR2(1 CHAR), 
	"TMS_PROC" VARCHAR2(2 CHAR), 
	"TMS_PO_FLAG" VARCHAR2(1 CHAR), 
	"LANG_ID" VARCHAR2(3 CHAR), 
	"BUSN_UNIT_CODE" VARCHAR2(10 CHAR), 
	"DSGNATED_MODE_CODE" VARCHAR2(4 CHAR), 
	"DSGNATED_SERV_LVL" VARCHAR2(8 CHAR), 
	"SHIPTO_CNTY" VARCHAR2(40 CHAR), 
	"DEST_DOCK_DOOR" VARCHAR2(20 CHAR), 
	"IS_PERISH_FLAG" VARCHAR2(1 CHAR), 
	"SHPR_ORD_ID" VARCHAR2(20 CHAR), 
	"FRT_CLASS" VARCHAR2(5 CHAR), 
	"TEMP_ZONE" VARCHAR2(1 CHAR), 
	"TRANSPRT_RESP_CODE" VARCHAR2(10 CHAR), 
	"ZONE_SKIP" VARCHAR2(1 CHAR), 
	"PATH_ID" NUMBER(9,0) DEFAULT 0 NOT NULL ENABLE, 
	"INIT_SHIP_VIA" VARCHAR2(4 CHAR), 
	"SHPR_ID" VARCHAR2(20 CHAR), 
	"HUB_ID" NUMBER(9,0) DEFAULT -1 NOT NULL ENABLE, 
	"DEST_AIRPORT" VARCHAR2(20 CHAR), 
	"DEPART_AIRPORT" VARCHAR2(20 CHAR), 
	"ORGN_DOCK" VARCHAR2(4 CHAR), 
	"EST_PALLET" NUMBER(7,0) DEFAULT 0 NOT NULL ENABLE, 
	"EST_PALLET_BRIDGED" NUMBER(7,0) DEFAULT 0 NOT NULL ENABLE, 
	"EST_CARTON_PER_PALLET_BRIDGED" NUMBER(7,0) DEFAULT 0 NOT NULL ENABLE, 
	"PRE_PACK_FLAG" NUMBER(1,0) DEFAULT 0 NOT NULL ENABLE, 
	"PROC_RTL_RTE_RULE" VARCHAR2(1 CHAR), 
	"CURR_CODE" VARCHAR2(10 CHAR), 
	"PARENT_ORD_ID" NUMBER(9,0) DEFAULT 0 NOT NULL ENABLE, 
	"MONETARY_VALUE" NUMBER(13,2) DEFAULT 0 NOT NULL ENABLE, 
	"MV_CURRENCY_CODE" VARCHAR2(3 CHAR), 
	"INCOTERM_ID" VARCHAR2(100 CHAR), 
	"COMMODITY_CODE" VARCHAR2(35 CHAR), 
	"UN_NBR" VARCHAR2(16 CHAR), 
	"TMS_ORD_TYPE" VARCHAR2(32 CHAR), 
	"PROD_SCHED_REF_NBR" VARCHAR2(32 CHAR), 
	"IS_CUST_PICKUP_FLAG" VARCHAR2(1 CHAR), 
	"PLANNING_ORGN" VARCHAR2(16 CHAR), 
	"PLANNING_DEST" VARCHAR2(16 CHAR), 
	"IS_PARTL_PLAN_FLAG" VARCHAR2(1 CHAR), 
	"IS_DIRECT_ALLOW_FLAG" VARCHAR2(1 CHAR), 
	"PRTY_TYPE" VARCHAR2(50 CHAR), 
	"PLT_CONTNT_LABEL_TYPE" VARCHAR2(9 CHAR), 
	"PRIMARY_MAXI_ADDR_NBR" VARCHAR2(10 CHAR), 
	"SECONDARY_MAXI_ADDR_NBR" VARCHAR2(8 CHAR), 
	"GLOBAL_LOCN_NBR" VARCHAR2(13 CHAR), 
	"CD_MASTER_ID" NUMBER(9,0) NOT NULL ENABLE, 
	"PRINT_COO" CHAR(1 CHAR), 
	"PRINT_INV" CHAR(1 CHAR), 
	"PRINT_SED" CHAR(1 CHAR), 
	"PRINT_CANADIAN_CUST_INVC_FLAG" CHAR(1 CHAR), 
	"PRINT_DOCK_RCPT_FLAG" CHAR(1 CHAR), 
	"PRINT_NAFTA_COO_FLAG" CHAR(1 CHAR), 
	"PRINT_OCEAN_BOL_FLAG" CHAR(1 CHAR), 
	"PRINT_PKG_LIST_FLAG" CHAR(1 CHAR), 
	"PRINT_SHPR_LTR_OF_INSTR_FLAG" CHAR(1 CHAR), 
	"DUTY_TAX_ACCT_NBR" VARCHAR2(10 CHAR), 
	"DUTY_TAX_PAYMENT_TYPE" NUMBER(1,0) DEFAULT 0 NOT NULL ENABLE, 
	 CONSTRAINT "PK_PKT_HDR" PRIMARY KEY ("PKT_CTRL_NBR")
  USING INDEX PCTFREE 20 INITRANS 15 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 4194304 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "VBATCH_DEV"  ENABLE,
--	 CONSTRAINT "FK_PKT_HDR_TO_TRLR_EQUIP" FOREIGN KEY ("TRLR_TYPE", "TRLR_SIZE")
--	  REFERENCES "VBATCH"."TRLR_EQUIP" ("TRLR_TYPE", "TRLR_SIZE") DISABLE, 
--	 CONSTRAINT "FK_PKT_HDR_TO_SHIP_VIA" FOREIGN KEY ("SHIP_VIA")
--	  REFERENCES "VBATCH"."SHIP_VIA" ("SHIP_VIA") DISABLE, 
--	 CONSTRAINT "FK_PKT_HDR_TO_OUTBD_SHPMT" FOREIGN KEY ("PLAN_SHPMT_NBR")
--	  REFERENCES "VBATCH"."OUTBD_SHPMT" ("SHPMT_NBR") DISABLE, 
	 CONSTRAINT "FK_PKT_HDR_TO_OUTBD_LOAD" FOREIGN KEY ("PLAN_LOAD_NBR")
	  REFERENCES "VBATCH"."OUTBD_LOAD" ("LOAD_NBR") DISABLE 
--	 CONSTRAINT "FK_PKT_HDR_TO_ORD_CONSOL_PROF_" FOREIGN KEY ("WHSE", "PKT_CONSOL_PROF")
--	  REFERENCES "VBATCH"."PKT_CONSOL_PROF_HDR" ("WHSE", "PKT_CONSOL_PROF") DISABLE, 
--	 CONSTRAINT "FK_PKT_HDR_TO_CD_MASTER" FOREIGN KEY ("CD_MASTER_ID")
--	  REFERENCES "VBATCH"."CD_MASTER" ("CD_MASTER_ID") DISABLE
   ) PCTFREE 20 PCTUSED 40 INITRANS 10 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 4194304 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "VBATCH_DEV" ;
 
  CREATE INDEX "VBATCH"."FK_PKT_HDR_TO_CD_MASTER" ON "VBATCH"."PKT_HDR" ("CD_MASTER_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "VBATCH_DEV" ;
 
  CREATE INDEX "VBATCH"."FK_PKT_HDR_TO_ORD_CONSOL_PROF_" ON "VBATCH"."PKT_HDR" ("WHSE", "PKT_CONSOL_PROF") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "VBATCH_DEV" ;
 
  CREATE INDEX "VBATCH"."FK_PKT_HDR_TO_OUTBD_LOAD" ON "VBATCH"."PKT_HDR" ("PLAN_LOAD_NBR") 
  PCTFREE 20 INITRANS 15 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 4194304 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "VBATCH_DEV" ;
 
  CREATE INDEX "VBATCH"."FK_PKT_HDR_TO_TRLR_EQUIP" ON "VBATCH"."PKT_HDR" ("TRLR_TYPE", "TRLR_SIZE") 
  PCTFREE 20 INITRANS 15 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 4194304 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "VBATCH_DEV" ;
 
  CREATE INDEX "VBATCH"."PKT_HDR_IND_10" ON "VBATCH"."PKT_HDR" ("SHIP_W_CTRL_NBR") 
  PCTFREE 25 INITRANS 20 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "VBATCH_DEV" ;
 
  CREATE INDEX "VBATCH"."PKT_HDR_IND_3" ON "VBATCH"."PKT_HDR" ("SHPMT_NBR", "TRLR_STOP_SEQ_NBR") 
  PCTFREE 20 INITRANS 15 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 4194304 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "VBATCH_DEV" ;
 
  CREATE INDEX "VBATCH"."PKT_HDR_IND_5" ON "VBATCH"."PKT_HDR" ("BOL") 
  PCTFREE 20 INITRANS 15 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 4194304 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "VBATCH_DEV" ;
 
  CREATE INDEX "VBATCH"."PKT_HDR_IND_6" ON "VBATCH"."PKT_HDR" ("SHIP_VIA", "SHIPTO", "ORD_NBR") 
  PCTFREE 20 INITRANS 15 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 4194304 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "VBATCH_DEV" ;
 
  CREATE INDEX "VBATCH"."PKT_HDR_IND_7" ON "VBATCH"."PKT_HDR" ("STORE_NBR", "WHSE") 
  PCTFREE 20 INITRANS 15 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 4194304 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "VBATCH_DEV" ;
 
  CREATE INDEX "VBATCH"."PKT_HDR_IND_8" ON "VBATCH"."PKT_HDR" ("PLAN_SHPMT_NBR", "CARTON_CUBNG_INDIC") 
  PCTFREE 20 INITRANS 15 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 4194304 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "VBATCH_DEV" ;
 
  CREATE INDEX "VBATCH"."PKT_HDR_IND_9" ON "VBATCH"."PKT_HDR" ("PLAN_ID") 
  PCTFREE 20 INITRANS 15 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 4194304 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "VBATCH_DEV" ;
 
--  CREATE UNIQUE INDEX "VBATCH"."PK_PKT_HDR" ON "VBATCH"."PKT_HDR" ("PKT_CTRL_NBR") 
--  PCTFREE 20 INITRANS 15 MAXTRANS 255 COMPUTE STATISTICS 
--  STORAGE(INITIAL 4194304 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
--  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
--  TABLESPACE "VBATCH_DEV" ;
 
--  ALTER TABLE "VBATCH"."PKT_HDR" ADD CONSTRAINT "PK_PKT_HDR" PRIMARY KEY ("PKT_CTRL_NBR")
--  USING INDEX PCTFREE 20 INITRANS 15 MAXTRANS 255 COMPUTE STATISTICS 
--  STORAGE(INITIAL 4194304 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
--  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
--  TABLESPACE "VBATCH_DEV"  ENABLE;
 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("PKT_CTRL_NBR" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("NBR_OF_TIMES_APPT_CHGD" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("TERMS_PCNT" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("SWC_NBR_SEQ" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("ORD_NBR_FOR_SWC" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("NBR_ORDS_FOR_SWC" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("CARTON_CUBNG_INDIC" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("NBR_OF_LABEL" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("NBR_OF_CONTNT_LABEL" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("NBR_OF_PAKNG_SLIPS" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("PACK_SLIP_PRT_CNT" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("SEL_RULE_PRTY" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("AUTO_INVC_STAT_CODE" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("EST_WT_BRIDGED" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("EST_WT" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("EST_CARTON_BRIDGED" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("EST_VOL" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("TOTAL_DLRS_UNDISC" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("TOTAL_DLRS_DISC" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("NBR_OF_HNGR" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("TRLR_STOP_SEQ_NBR" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("PROD_VALUE" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("MAX_CARTON_WT" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("MAX_CARTON_UNITS" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("NBR_OF_ZONES" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("PARTL_CARTON_MIN_WT" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("EST_VOL_BRIDGED" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("EST_CARTON" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("RTE_STOP_SEQ" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("DUTY_AND_TAX" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("RTE_LOAD_SEQ" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("PLT_CUBNG_INDIC" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("SRL_NBR_TRK_FLAG" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("PATH_ID" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("HUB_ID" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("EST_PALLET" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("EST_PALLET_BRIDGED" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("EST_CARTON_PER_PALLET_BRIDGED" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("PRE_PACK_FLAG" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("PARENT_ORD_ID" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("MONETARY_VALUE" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("CD_MASTER_ID" NOT NULL ENABLE);
-- 
--  ALTER TABLE "VBATCH"."PKT_HDR" MODIFY ("DUTY_TAX_PAYMENT_TYPE" NOT NULL ENABLE);
-- 
--  GRANT SELECT ON "VBATCH"."PKT_HDR" TO "FGV_VBATCH";
-- 
--  GRANT SELECT ON "VBATCH"."PKT_HDR" TO "RPT_VBATCH";
-- 
--  GRANT DELETE ON "VBATCH"."PKT_HDR" TO "WMS_VBATCH" WITH GRANT OPTION;
-- 
--  GRANT INSERT ON "VBATCH"."PKT_HDR" TO "WMS_VBATCH" WITH GRANT OPTION;
-- 
--  GRANT SELECT ON "VBATCH"."PKT_HDR" TO "WMS_VBATCH" WITH GRANT OPTION;
-- 
--  GRANT UPDATE ON "VBATCH"."PKT_HDR" TO "WMS_VBATCH" WITH GRANT OPTION;
-- 
--  GRANT SELECT ON "VBATCH"."PKT_HDR" TO "UPS_REPORTS";
-- 
--  GRANT DELETE ON "VBATCH"."PKT_HDR" TO "APP_ADMIN_SUPPORT";
-- 
--  GRANT INSERT ON "VBATCH"."PKT_HDR" TO "APP_ADMIN_SUPPORT";
-- 
--  GRANT UPDATE ON "VBATCH"."PKT_HDR" TO "APP_ADMIN_SUPPORT";