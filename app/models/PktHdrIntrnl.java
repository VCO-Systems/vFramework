package models;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PKT_HDR_INTRNL database table.
 * 
 */
@Entity
@Table(name="PKT_HDR_INTRNL")
@NamedQuery(name="PktHdrIntrnl.findAll", query="SELECT p FROM PktHdrIntrnl p")
public class PktHdrIntrnl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PKT_CTRL_NBR")
	private String pktCtrlNbr;

	@Column(name="ADDR_ONLY_PKT")
	private BigDecimal addrOnlyPkt;

	@Temporal(TemporalType.DATE)
	@Column(name="CANCEL_DATE_TIME")
	private Date cancelDateTime;

	@Column(name="CHUTE_ASSIGN_TYPE")
	private String chuteAssignType;

	@Column(name="CHUTE_ID")
	private String chuteId;

	@Column(name="CONSOL_LOCN_ID")
	private String consolLocnId;

	@Column(name="CONSOL_PROFL_ID")
	private String consolProflId;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_DATE_TIME")
	private Date createDateTime;

	@Column(name="HNDL_CHRG")
	private BigDecimal hndlChrg;

	@Column(name="INSUR_CHRG")
	private BigDecimal insurChrg;

	@Column(name="LOAD_CREATED_WAVE")
	private String loadCreatedWave;

	@Column(name="MAJOR_MINOR_PKT")
	private String majorMinorPkt;

	@Column(name="MAJOR_PKT_CTRL_NBR")
	private String majorPktCtrlNbr;

	@Column(name="MHE_FLAG")
	private String mheFlag;

	@Column(name="MHE_ORD_STATE")
	private String mheOrdState;

	@Column(name="MISC_CHRG")
	private BigDecimal miscChrg;

	@Temporal(TemporalType.DATE)
	@Column(name="MOD_DATE_TIME")
	private Date modDateTime;

	@Column(name="OBSHPMT_CREATED_WAVE")
	private String obshpmtCreatedWave;

	@Column(name="PACK_WAVE_NBR")
	private String packWaveNbr;

	@Column(name="PICK_WAVE_NBR")
	private String pickWaveNbr;

	@Column(name="PKT_GRP_CODE")
	private String pktGrpCode;

	@Temporal(TemporalType.DATE)
	@Column(name="PKT_PRT_DATE_TIME")
	private Date pktPrtDateTime;

	@Column(name="REF_CONSOL_NBR")
	private String refConsolNbr;

	@Column(name="RTE_CONSOL_NBR")
	private String rteConsolNbr;

	@Column(name="RTE_SWC_NBR")
	private String rteSwcNbr;

	@Column(name="RTE_WAVE_NBR")
	private String rteWaveNbr;

	@Column(name="RTL_PKT_FLAG")
	private String rtlPktFlag;

	@Column(name="SEL_RULE_ID")
	private BigDecimal selRuleId;

	@Temporal(TemporalType.DATE)
	@Column(name="SHIP_DATE_TIME")
	private Date shipDateTime;

	@Column(name="SHIP_WAVE_NBR")
	private String shipWaveNbr;

	@Column(name="SHPNG_CHRG")
	private BigDecimal shpngChrg;

	@Column(name="STAGE_INDIC")
	private BigDecimal stageIndic;

	@Column(name="STAT_CODE")
	private BigDecimal statCode;

	@Column(name="TAX_CHRG")
	private BigDecimal taxChrg;

	@Column(name="TMS_FLAG")
	private String tmsFlag;

	@Column(name="TMS_STAT_CODE")
	private BigDecimal tmsStatCode;

	@Column(name="TMS_TO_ID")
	private String tmsToId;

	@Column(name="TOTAL_CARTON")
	private BigDecimal totalCarton;

	@Column(name="TOTAL_NBR_OF_PLT")
	private BigDecimal totalNbrOfPlt;

	@Column(name="TOTAL_NBR_OF_UNITS")
	private BigDecimal totalNbrOfUnits;

	@Column(name="TOTAL_WT")
	private BigDecimal totalWt;

	@Column(name="USER_ID")
	private String userId;

	@Column(name="VAS_INDIC")
	private BigDecimal vasIndic;

	@Column(name="WAVE_SEQ_NBR")
	private BigDecimal waveSeqNbr;

	@Column(name="WAVE_STAT_CODE")
	private BigDecimal waveStatCode;

	//bi-directional one-to-one association to PktHdr
	@OneToOne(mappedBy="pktHdrIntrnl")
	private PktHdr pktHdr;

	public PktHdrIntrnl() {
	}

	public String getPktCtrlNbr() {
		return this.pktCtrlNbr;
	}

	public void setPktCtrlNbr(String pktCtrlNbr) {
		this.pktCtrlNbr = pktCtrlNbr;
	}

	public BigDecimal getAddrOnlyPkt() {
		return this.addrOnlyPkt;
	}

	public void setAddrOnlyPkt(BigDecimal addrOnlyPkt) {
		this.addrOnlyPkt = addrOnlyPkt;
	}

	public Date getCancelDateTime() {
		return this.cancelDateTime;
	}

	public void setCancelDateTime(Date cancelDateTime) {
		this.cancelDateTime = cancelDateTime;
	}

	public String getChuteAssignType() {
		return this.chuteAssignType;
	}

	public void setChuteAssignType(String chuteAssignType) {
		this.chuteAssignType = chuteAssignType;
	}

	public String getChuteId() {
		return this.chuteId;
	}

	public void setChuteId(String chuteId) {
		this.chuteId = chuteId;
	}

	public String getConsolLocnId() {
		return this.consolLocnId;
	}

	public void setConsolLocnId(String consolLocnId) {
		this.consolLocnId = consolLocnId;
	}

	public String getConsolProflId() {
		return this.consolProflId;
	}

	public void setConsolProflId(String consolProflId) {
		this.consolProflId = consolProflId;
	}

	public Date getCreateDateTime() {
		return this.createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public BigDecimal getHndlChrg() {
		return this.hndlChrg;
	}

	public void setHndlChrg(BigDecimal hndlChrg) {
		this.hndlChrg = hndlChrg;
	}

	public BigDecimal getInsurChrg() {
		return this.insurChrg;
	}

	public void setInsurChrg(BigDecimal insurChrg) {
		this.insurChrg = insurChrg;
	}

	public String getLoadCreatedWave() {
		return this.loadCreatedWave;
	}

	public void setLoadCreatedWave(String loadCreatedWave) {
		this.loadCreatedWave = loadCreatedWave;
	}

	public String getMajorMinorPkt() {
		return this.majorMinorPkt;
	}

	public void setMajorMinorPkt(String majorMinorPkt) {
		this.majorMinorPkt = majorMinorPkt;
	}

	public String getMajorPktCtrlNbr() {
		return this.majorPktCtrlNbr;
	}

	public void setMajorPktCtrlNbr(String majorPktCtrlNbr) {
		this.majorPktCtrlNbr = majorPktCtrlNbr;
	}

	public String getMheFlag() {
		return this.mheFlag;
	}

	public void setMheFlag(String mheFlag) {
		this.mheFlag = mheFlag;
	}

	public String getMheOrdState() {
		return this.mheOrdState;
	}

	public void setMheOrdState(String mheOrdState) {
		this.mheOrdState = mheOrdState;
	}

	public BigDecimal getMiscChrg() {
		return this.miscChrg;
	}

	public void setMiscChrg(BigDecimal miscChrg) {
		this.miscChrg = miscChrg;
	}

	public Date getModDateTime() {
		return this.modDateTime;
	}

	public void setModDateTime(Date modDateTime) {
		this.modDateTime = modDateTime;
	}

	public String getObshpmtCreatedWave() {
		return this.obshpmtCreatedWave;
	}

	public void setObshpmtCreatedWave(String obshpmtCreatedWave) {
		this.obshpmtCreatedWave = obshpmtCreatedWave;
	}

	public String getPackWaveNbr() {
		return this.packWaveNbr;
	}

	public void setPackWaveNbr(String packWaveNbr) {
		this.packWaveNbr = packWaveNbr;
	}

	public String getPickWaveNbr() {
		return this.pickWaveNbr;
	}

	public void setPickWaveNbr(String pickWaveNbr) {
		this.pickWaveNbr = pickWaveNbr;
	}

	public String getPktGrpCode() {
		return this.pktGrpCode;
	}

	public void setPktGrpCode(String pktGrpCode) {
		this.pktGrpCode = pktGrpCode;
	}

	public Date getPktPrtDateTime() {
		return this.pktPrtDateTime;
	}

	public void setPktPrtDateTime(Date pktPrtDateTime) {
		this.pktPrtDateTime = pktPrtDateTime;
	}

	public String getRefConsolNbr() {
		return this.refConsolNbr;
	}

	public void setRefConsolNbr(String refConsolNbr) {
		this.refConsolNbr = refConsolNbr;
	}

	public String getRteConsolNbr() {
		return this.rteConsolNbr;
	}

	public void setRteConsolNbr(String rteConsolNbr) {
		this.rteConsolNbr = rteConsolNbr;
	}

	public String getRteSwcNbr() {
		return this.rteSwcNbr;
	}

	public void setRteSwcNbr(String rteSwcNbr) {
		this.rteSwcNbr = rteSwcNbr;
	}

	public String getRteWaveNbr() {
		return this.rteWaveNbr;
	}

	public void setRteWaveNbr(String rteWaveNbr) {
		this.rteWaveNbr = rteWaveNbr;
	}

	public String getRtlPktFlag() {
		return this.rtlPktFlag;
	}

	public void setRtlPktFlag(String rtlPktFlag) {
		this.rtlPktFlag = rtlPktFlag;
	}

	public BigDecimal getSelRuleId() {
		return this.selRuleId;
	}

	public void setSelRuleId(BigDecimal selRuleId) {
		this.selRuleId = selRuleId;
	}

	public Date getShipDateTime() {
		return this.shipDateTime;
	}

	public void setShipDateTime(Date shipDateTime) {
		this.shipDateTime = shipDateTime;
	}

	public String getShipWaveNbr() {
		return this.shipWaveNbr;
	}

	public void setShipWaveNbr(String shipWaveNbr) {
		this.shipWaveNbr = shipWaveNbr;
	}

	public BigDecimal getShpngChrg() {
		return this.shpngChrg;
	}

	public void setShpngChrg(BigDecimal shpngChrg) {
		this.shpngChrg = shpngChrg;
	}

	public BigDecimal getStageIndic() {
		return this.stageIndic;
	}

	public void setStageIndic(BigDecimal stageIndic) {
		this.stageIndic = stageIndic;
	}

	public BigDecimal getStatCode() {
		return this.statCode;
	}

	public void setStatCode(BigDecimal statCode) {
		this.statCode = statCode;
	}

	public BigDecimal getTaxChrg() {
		return this.taxChrg;
	}

	public void setTaxChrg(BigDecimal taxChrg) {
		this.taxChrg = taxChrg;
	}

	public String getTmsFlag() {
		return this.tmsFlag;
	}

	public void setTmsFlag(String tmsFlag) {
		this.tmsFlag = tmsFlag;
	}

	public BigDecimal getTmsStatCode() {
		return this.tmsStatCode;
	}

	public void setTmsStatCode(BigDecimal tmsStatCode) {
		this.tmsStatCode = tmsStatCode;
	}

	public String getTmsToId() {
		return this.tmsToId;
	}

	public void setTmsToId(String tmsToId) {
		this.tmsToId = tmsToId;
	}

	public BigDecimal getTotalCarton() {
		return this.totalCarton;
	}

	public void setTotalCarton(BigDecimal totalCarton) {
		this.totalCarton = totalCarton;
	}

	public BigDecimal getTotalNbrOfPlt() {
		return this.totalNbrOfPlt;
	}

	public void setTotalNbrOfPlt(BigDecimal totalNbrOfPlt) {
		this.totalNbrOfPlt = totalNbrOfPlt;
	}

	public BigDecimal getTotalNbrOfUnits() {
		return this.totalNbrOfUnits;
	}

	public void setTotalNbrOfUnits(BigDecimal totalNbrOfUnits) {
		this.totalNbrOfUnits = totalNbrOfUnits;
	}

	public BigDecimal getTotalWt() {
		return this.totalWt;
	}

	public void setTotalWt(BigDecimal totalWt) {
		this.totalWt = totalWt;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getVasIndic() {
		return this.vasIndic;
	}

	public void setVasIndic(BigDecimal vasIndic) {
		this.vasIndic = vasIndic;
	}

	public BigDecimal getWaveSeqNbr() {
		return this.waveSeqNbr;
	}

	public void setWaveSeqNbr(BigDecimal waveSeqNbr) {
		this.waveSeqNbr = waveSeqNbr;
	}

	public BigDecimal getWaveStatCode() {
		return this.waveStatCode;
	}

	public void setWaveStatCode(BigDecimal waveStatCode) {
		this.waveStatCode = waveStatCode;
	}

	public PktHdr getPktHdr() {
		return this.pktHdr;
	}

	public void setPktHdr(PktHdr pktHdr) {
		this.pktHdr = pktHdr;
	}

}