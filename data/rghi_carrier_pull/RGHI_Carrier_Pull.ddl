/* This DDL is for use in local dev, to create a carrier pull table
   for local use only.
*/
USE master;
GO
CREATE TABLE RGH.dbo.Ship_Via
	(SHIP_VIA varchar(4) NOT NULL,
	 SHIP_VIA_DESC varchar(40) NULL,
	 CARR_ID varchar(4) NULL,
	 SERV_TYPE varchar(4) NULL,
	 LABEL_TYPE varchar(3) NULL,
	 BILL_SHIP_VIA varchar(4) NULL,
	 INSUR_COVER_CODE varchar(1) NULL,
	 SERV_LEVEL_INDIC varchar(3) NULL,
	 ICON varchar(4) NULL,
	 TMS_CARR_CODE varchar(8) NULL,
	 FRT_BASED_ON_NBR_OF_CARTON varchar(1) NULL,
	 MODE_OF_TRANSPORT decimal NOT NULL DEFAULT 0,
	 INSUR_TYPE varchar(2) NULL,
	 CREATE_DATE_TIME date NULL,
	 MOD_DATE_TIME date NULL,
	 USER_ID varchar(15) NULL
	 
	 CONSTRAINT PK_Ship_Via PRIMARY KEY NONCLUSTERED (SHIP_VIA)
	)
GO
CREATE TABLE RGH.dbo.RGHI_Carrier_Pull
   (WHSE varchar(3) NOT NULL,
    SHIPTO_ZIP varchar(11) NOT NULL,
	SHIP_VIA varchar(4)  NOT NULL,
	PULL_TRLR_CODE varchar(8) NULL,
	PULL_TIME varchar(5) NULL,
	PULL_TIME_AMPM varchar(2) NULL,
	ANYTEXT1 varchar(10) NULL,
	ANYNBR1 int NULL,
	CREATE_DATE_TIME date NULL,
	MOD_DATE_TIME date NULL,
	USER_ID varchar(15) NULL
	
	CONSTRAINT PK_RGHI_Carrier_Pull PRIMARY KEY NONCLUSTERED (WHSE,SHIPTO_ZIP,SHIP_VIA)
	)
GO