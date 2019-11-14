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
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="PackageRoute")
@XmlRootElement(name="PackageRoute")
public class PackageRoute implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -120165903842914592L;

	public PackageRoute() {
	}
	
	
	@Column(name="SN", nullable=false)	
	@Id	
	@GeneratedValue(generator="MODEL_PACKAGEROUTE_SN_GENERATOR")	
	@org.hibernate.annotations.GenericGenerator(name="MODEL_PACKAGEROUTE_SN_GENERATOR", strategy="native")	
	private int SN;
	
	@ManyToOne(targetEntity=TransPackage.class, fetch=FetchType.LAZY)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="PackageID", referencedColumnName="ID", nullable=false) })	
	private TransPackage pkg;
	
	@Column(name="x", nullable=false)	
	private float x;
	
	@Column(name="y", nullable=false)	
	private float y;
	
	@Column(name="tm", nullable=false)	
	private Date tm;
	
	public void setSN(int value) {
		this.SN = value;
	}
	
	public int getSN() {
		return SN;
	}
	
	public int getORMID() {
		return getSN();
	}
	
	public void setX(float value) {
		this.x = value;
	}
	
	public float getX() {
		return x;
	}
	
	public void setY(float value) {
		this.y = value;
	}
	
	public float getY() {
		return y;
	}
	
	public void setTm(Date value) {
		this.tm = value;
	}
	
	public Date getTm() {
		return tm;
	}
	
	public void setPkg(TransPackage value) {
		this.pkg = value;
	}
	
	public TransPackage getPkg() {
		return pkg;
	}
	
	public String toString() {
		return toString(false);
	}
	
	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(getSN());
		}
		else {
			StringBuffer sb = new StringBuffer();
			sb.append("PackageRoute[ ");
			sb.append("SN=").append(getSN()).append(" ");
			if (getPkg() != null)
				sb.append("Pkg.Persist_ID=").append(getPkg().toString(true)).append(" ");
			else
				sb.append("Pkg=null ");
			sb.append("X=").append(getX()).append(" ");
			sb.append("Y=").append(getY()).append(" ");
			sb.append("Tm=").append(getTm()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}
	
}
