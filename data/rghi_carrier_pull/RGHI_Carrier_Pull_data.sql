
GO
INSERT INTO RGH.dbo.RGHI_Carrier_Pull
(WHSE, SHIPTO_ZIP,SHIP_VIA,PULL_TRLR_CODE, PULL_TIME, CREATE_DATE_TIME, MOD_DATE_TIME)
VALUES ('OH1','75167','E08','UPS-TRAI',convert(time, '20:15:00'),CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
 ('OH1','75167','E07','NXT-TRAI',convert(time, '23:00:00'),CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
 ('OH1','75167','E09','UPS-TRAI',convert(time, '08:00:00'),CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
GO