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
@Table(name="CustomerInfo")
@XmlRootElement(name="CustomerInfo")
public class CustomerInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3267943602377867497L;

	public CustomerInfo() {
	}
	
	@Column(name="ID", nullable=false)	
	@Id	
	@GeneratedValue(generator="MODEL_CUSTOMERINFO_ID_GENERATOR")	
	@org.hibernate.annotations.GenericGenerator(name="MODEL_CUSTOMERINFO_ID_GENERATOR", strategy="native")	
	private int ID;
	
	@Column(name="Name", nullable=true, length=16)	
	private String name;
	
	@Column(name="TelCode", nullable=true, length=24)	
	private String telCode;
	
	@Column(name="Department", nullable=true, length=64)	
	private String department;
	
	@Column(name="RegionCode", nullable=true, length=6)	
	private String regionCode;
	
	@Column(name="Address", nullable=true, length=64)	
	private String address;
	
	@Column(name="PostCode", nullable=false, length=10)	
	private int postCode;
	
//	@OneToMany(mappedBy="sender", targetEntity=ExpressSheet.class)	
//	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
//	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.TRUE)	
//	private java.util.Set<ExpressSheet> expressSender = new java.util.HashSet<ExpressSheet>();
//	
//	@OneToMany(mappedBy="recever", targetEntity=ExpressSheet.class)	
//	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
//	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.TRUE)	
//	private java.util.Set<ExpressSheet> expressReceiver = new java.util.HashSet<ExpressSheet>();
	
	public void setID(int value) {
		this.ID = value;
	}
	
	public int getID() {
		return ID;
	}
	
	public int getORMID() {
		return getID();
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}
	
	public void setTelCode(String value) {
		this.telCode = value;
	}
	
	public String getTelCode() {
		return telCode;
	}
	
	public void setDepartment(String value) {
		this.department = value;
	}
	
	public String getDepartment() {
		return department;
	}
	
	public void setRegionCode(String value) {
		this.regionCode = value;
	}
	
	public String getRegionCode() {
		return regionCode;
	}
	
	public void setAddress(String value) {
		this.address = value;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setPostCode(int value) {
		this.postCode = value;
	}
	
	public int getPostCode() {
		return postCode;
	}
	
//	public void setExpressSender(java.util.Set<ExpressSheet> value) {
//		this.expressSender = value;
//	}
//	
//	public java.util.Set<ExpressSheet> getExpressSender() {
//		return expressSender;
//	}
//	
//	
//	public void setExpressReceiver(java.util.Set<ExpressSheet> value) {
//		this.expressReceiver = value;
//	}
//	
//	public java.util.Set<ExpressSheet> getExpressReceiver() {
//		return expressReceiver;
//	}
	
	
	public String toString() {
		return toString(false);
	}
	
	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(getID());
		}
		else {
			StringBuffer sb = new StringBuffer();
			sb.append("CustomerInfo[ ");
			sb.append("ID=").append(getID()).append(" ");
			sb.append("Name=").append(getName()).append(" ");
			sb.append("TelCode=").append(getTelCode()).append(" ");
			sb.append("Department=").append(getDepartment()).append(" ");
			sb.append("RegionCode=").append(getRegionCode()).append(" ");
			sb.append("Address=").append(getAddress()).append(" ");
			sb.append("PostCode=").append(getPostCode()).append(" ");
//			sb.append("ExpressSender.size=").append(getExpressSender().size()).append(" ");
//			sb.append("ExpressReceiver.size=").append(getExpressReceiver().size()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}

	@Transient	
	private String regionString;
	public void setRegionString(String value) {
		this.regionString = value;
	}
	
	public String getRegionString() {
		return regionString;
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
