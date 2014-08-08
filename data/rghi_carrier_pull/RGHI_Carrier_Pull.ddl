/* This DDL is for use in local dev, to create a carrier pull table
   for local use only.
*/
USE master;
GO
CREATE TABLE RGH.dbo.RGHI_Carrier_Pull
   (WHSE varchar(4) NOT NULL,
    SHIPTO_ZIP varchar(11) NULL,
	SHIP_VIA varchar(4) NOT NULL,
	PULL_TRLR_CODE varchar(8) NULL,
	PULL_TIME time(0) NULL,
	ANYTEXT1 varchar(10) NULL,
	ANYNBR1 int,
	CREATE_DATE_TIME date,
	MOD_DATE_TIME date,
	USER_ID varchar(15)
	)
GO
