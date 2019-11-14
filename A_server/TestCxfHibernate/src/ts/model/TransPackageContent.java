package ts.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="TransPackageContent")
@XmlRootElement(name="TransPackageContent")
public class TransPackageContent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2696910600791838998L;

	public TransPackageContent() {
	}
	
	@Column(name="SN", nullable=false)	
	@Id	
	@GeneratedValue(generator="MODEL_TRANSPACKAGECONTENT_SN_GENERATOR")	
	@org.hibernate.annotations.GenericGenerator(name="MODEL_TRANSPACKAGECONTENT_SN_GENERATOR", strategy="native")	
	private int SN;
	
	@ManyToOne(targetEntity=ExpressSheet.class, fetch=FetchType.LAZY)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="ExpressID", referencedColumnName="ID", nullable=false) })	
	private ExpressSheet express;
	
	@ManyToOne(targetEntity=TransPackage.class, fetch=FetchType.LAZY)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="PackageID", referencedColumnName="ID", nullable=false) })	
	private TransPackage pkg;

	@Column(name="Status", nullable=false, length=10)	
	private int status;

	public void setSN(int value) {
		this.SN = value;
	}
	
	public int getSN() {
		return SN;
	}
	
	public int getORMID() {
		return getSN();
	}
	
	public void setExpress(ExpressSheet value) {
		this.express = value;
	}
	
	public ExpressSheet getExpress() {
		return express;
	}
	
	public void setPkg(TransPackage value) {
		this.pkg = value;
	}
	
	public TransPackage getPkg() {
		return pkg;
	}
	
	public void setStatus(int value) {
		this.status = value;
	}
	
	public int getStatus() {
		return status;
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
			sb.append("TransPackageContent[ ");
			sb.append("SN=").append(getSN()).append(" ");
			if (getExpress() != null)
				sb.append("Express.Persist_ID=").append(getExpress().toString(true)).append(" ");
			else
				sb.append("Express=null ");
			if (getPkg() != null)
				sb.append("Pkg.Persist_ID=").append(getPkg().toString(true)).append(" ");
			else
				sb.append("Pkg=null ");
			sb.append("Status=").append(getStatus()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}
	
	public static final class STATUS{
		public static final int STATUS_ACTIVE = 0;
		public static final int STATUS_OUTOF_PACKAGE = 1;
	}
}
