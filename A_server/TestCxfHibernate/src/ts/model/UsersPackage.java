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
@Table(name="UsersPackage")
@XmlRootElement(name="UsersPackage")
public class UsersPackage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6753829022427770282L;

	public UsersPackage() {
	}
	
	
	@Column(name="SN", nullable=false)	
	@Id	
	@GeneratedValue(generator="MODEL_USERSPACKAGE_SN_GENERATOR")	
	@org.hibernate.annotations.GenericGenerator(name="MODEL_USERSPACKAGE_SN_GENERATOR", strategy="native")	
	private int SN;
	
	@ManyToOne(targetEntity=UserInfo.class, fetch=FetchType.LAZY)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="UserUID", referencedColumnName="UID", nullable=false) })	
	private UserInfo userU;
	
	@OneToOne(targetEntity=TransPackage.class, fetch=FetchType.LAZY)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="PackageID", nullable=false) })	
	private TransPackage pkg;
	
	public void setSN(int value) {
		this.SN = value;
	}
	
	public int getSN() {
		return SN;
	}
	
	public int getORMID() {
		return getSN();
	}
	
	public void setUserU(UserInfo value) {
		this.userU = value;
	}
	
	public UserInfo getUserU() {
		return userU;
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
			sb.append("UsersPackage[ ");
			sb.append("SN=").append(getSN()).append(" ");
			if (getUserU() != null)
				sb.append("UserU.Persist_ID=").append(getUserU().toString(true)).append(" ");
			else
				sb.append("UserU=null ");
			if (getPkg() != null)
				sb.append("Pkg.Persist_ID=").append(getPkg().toString(true)).append(" ");
			else
				sb.append("Pkg=null ");
			sb.append("]");
			return sb.toString();
		}
	}
	
}
