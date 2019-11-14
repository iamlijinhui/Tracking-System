/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * 
 * This is an automatic generated file. It will be regenerated every time 
 * you generate persistence class.
 * 
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: 
 * License Type: Evaluation
 */
package ts.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="Region")
@XmlRootElement(name="Region")
public class Region implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2531774702426085013L;

	public Region() {
	}
	
	@Column(name="RegionCode", nullable=false)	
	@Id	
	@GeneratedValue(generator="MODEL_REGION_REGIONCODE_GENERATOR")	
	@org.hibernate.annotations.GenericGenerator(name="MODEL_REGION_REGIONCODE_GENERATOR", strategy="native")	
	private String regionCode;
	
	@Column(name="Prv", nullable=true, length=32)	
	private String prv;
	
	@Column(name="Cty", nullable=true, length=32)	
	private String cty;
	
	@Column(name="Twn", nullable=true, length=32)	
	private String twn;
	
	@Column(name="Stage", nullable=false, length=4)	
	private int stage;
	
	public void setRegionCode(String value) {
		this.regionCode = value;
	}
	
	public String getRegionCode() {
		return regionCode;
	}
	
	public String getORMID() {
		return getRegionCode();
	}
	
	public void setPrv(String value) {
		this.prv = value;
	}
	
	public String getPrv() {
		return prv;
	}
	
	public void setCty(String value) {
		this.cty = value;
	}
	
	public String getCty() {
		return cty;
	}
	
	public void setTwn(String value) {
		this.twn = value;
	}
	
	public String getTwn() {
		return twn;
	}
	
	public void setStage(int value) {
		this.stage = value;
	}
	
	public int getStage() {
		return stage;
	}
	
	public String toString() {
		return toString(false);
	}
	
	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(getRegionCode());
		}
		else {
			StringBuffer sb = new StringBuffer();
			sb.append("Region[ ");
			sb.append("RegionCode=").append(getRegionCode()).append(" ");
			sb.append("Prv=").append(getPrv()).append(" ");
			sb.append("Cty=").append(getCty()).append(" ");
			sb.append("Twn=").append(getTwn()).append(" ");
			sb.append("Stage=").append(getStage()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}
	
	@Transient	
	private boolean _saved = false;
	
	public void onSave() {
		_saved=true;
	}
	
	
	public void onLoad() {
		_saved=true;
	}
	
	
	public boolean isSaved() {
		return _saved;
	}
	
	
}
