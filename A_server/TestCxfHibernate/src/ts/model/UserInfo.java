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
import javax.xml.bind.annotation.XmlTransient;
@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="UserInfo")
@XmlRootElement(name="UserInfo")
public class UserInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6899152987896840262L;

	public UserInfo() {
	}
	
	@Column(name="UID", nullable=false)	
	@Id	
	@GeneratedValue(generator="MODEL_USERINFO_UID_GENERATOR")	
	@org.hibernate.annotations.GenericGenerator(name="MODEL_USERINFO_UID_GENERATOR", strategy="native")	
	private int UID;
	
	@Column(name="PWD", nullable=true, length=8)	
	private String PWD;
	
	@Column(name="Name", nullable=true, length=16)	
	private String name;
	
	@Column(name="URull", nullable=true, length=4)	
	private Integer URull;
	
	@Column(name="TelCode", nullable=true, length=24)	
	private String telCode;
	
	@Column(name="Status", nullable=true, length=4)	
	private Integer status;
	
	@Column(name="DptID", nullable=true, length=16)	
	private String dptID;
	
	@Column(name="ReceivePackageID", nullable=true, length=24)	
	private String receivePackageID;
	
	@Column(name="DelivePackageID", nullable=true, length=24)	
	private String delivePackageID;
	
	@Column(name="TransPackageID", nullable=true, length=24)	
	private String transPackageID;
	
//	@OneToMany(mappedBy="userU", targetEntity=UsersPackage.class)	
//	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
//	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.TRUE)	
//	private java.util.Set<UsersPackage> usersPackage = new java.util.HashSet<UsersPackage>();
	
	@Transient
	private String userToken;
	
	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	
	public void setUID(int value) {
		this.UID = value;
	}
	
	public int getUID() {
		return UID;
	}
	
	public int getORMID() {
		return getUID();
	}
	
	public void setPWD(String value) {
		this.PWD = value;
	}
	
	public String getPWD() {
		return PWD;
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}
	
	public void setURull(Integer value) {
		this.URull = value;
	}
	
	public Integer getURull() {
		return URull;
	}
	
	public void setTelCode(String value) {
		this.telCode = value;
	}
	
	public String getTelCode() {
		return telCode;
	}
	
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setDptID(String value) {
		this.dptID = value;
	}
	
	public String getDptID() {
		return dptID;
	}
	
	public void setReceivePackageID(String value) {
		this.receivePackageID = value;
	}
	
	public String getReceivePackageID() {
		return receivePackageID;
	}
	
	public void setDelivePackageID(String value) {
		this.delivePackageID = value;
	}
	
	public String getDelivePackageID() {
		return delivePackageID;
	}
	
	public void setTransPackageID(String value) {
		this.transPackageID = value;
	}
	
	public String getTransPackageID() {
		return transPackageID;
	}
	
//	public void setUsersPackage(java.util.Set<UsersPackage> value) {
//		this.usersPackage = value;
//	}
//	
//	@XmlTransient
//	public java.util.Set<UsersPackage> getUsersPackage() {
//		return usersPackage;
//	}
	
	
	public String toString() {
		return toString(false);
	}
	
	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(getUID());
		}
		else {
			StringBuffer sb = new StringBuffer();
			sb.append("UserInfo[ ");
			sb.append("UID=").append(getUID()).append(" ");
			sb.append("PWD=").append(getPWD()).append(" ");
			sb.append("Name=").append(getName()).append(" ");
			sb.append("URull=").append(getURull()).append(" ");
			sb.append("TelCode=").append(getTelCode()).append(" ");
			sb.append("Status=").append(getStatus()).append(" ");
			sb.append("DptID=").append(getDptID()).append(" ");
			sb.append("ReceivePackageID=").append(getReceivePackageID()).append(" ");
			sb.append("DelivePackageID=").append(getDelivePackageID()).append(" ");
			sb.append("TransPackageID=").append(getTransPackageID()).append(" ");
//			sb.append("UsersPackage.size=").append(getUsersPackage().size()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}
	
	public static final class URULL{
		public static final int URULL_STAFF = 0; // 配送员 and 揽件员  分拣员
        public static final int URULL_MANAGER = 1; // 经理 管理员
//        public static final int URULL_STAFF = 2; // 配送员 and 揽件员
        public static final int URULL_DRIVER = 2; // 汽车司机
//        public static final int URULL_SORTER = 4; // 分拣员
    }

}
