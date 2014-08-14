/* This DDL is for use in local dev, to create a carrier pull table
   for local use only.
*/
USE master;
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