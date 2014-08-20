package models;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the WEB_SESSION database table.
 * 
 */
@Entity
@Table(name="WEB_SESSION")
@NamedQueries( {
	@NamedQuery(name="WebSession.findAll", query="SELECT w FROM WebSession w"),
	@NamedQuery(name="WebSession.findById", query="SELECT w FROM WebSession w WHERE w.webSessionId=:sessId")
	})
public class WebSession implements Serializable {
	private static final long serialVersionUID = 287213L;

	@Id
	@Column(name="WEB_SESSION_ID", unique=true, nullable=false, precision=9)
	private long webSessionId;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_DATE_TIME", nullable=false)
	private Date createDateTime;

	@Temporal(TemporalType.DATE)
	@Column(name="LAST_TRANS_TIME")
	private Date lastTransTime;

	@Column(name="LOGIN_USER_ID", nullable=false, length=15)
	private String loginUserId;

	@Temporal(TemporalType.DATE)
	@Column(name="MOD_DATE_TIME", nullable=false)
	private Date modDateTime;

	@Lob
	@Column(name="SESSION_XML", nullable=false)
	private String sessionXml;

	@Column(name="USER_ID", length=15)
	private String userId;

	public WebSession() {
	}

	public long getWebSessionId() {
		return this.webSessionId;
	}

	public void setWebSessionId(long webSessionId) {
		this.webSessionId = webSessionId;
	}

	public Date getCreateDateTime() {
		return this.createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Date getLastTransTime() {
		return this.lastTransTime;
	}

	public void setLastTransTime(Date lastTransTime) {
		this.lastTransTime = lastTransTime;
	}

	public String getLoginUserId() {
		return this.loginUserId;
	}

	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}

	public Date getModDateTime() {
		return this.modDateTime;
	}

	public void setModDateTime(Date modDateTime) {
		this.modDateTime = modDateTime;
	}

	public String getSessionXml() {
		return this.sessionXml;
	}

	public void setSessionXml(String sessionXml) {
		this.sessionXml = sessionXml;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}