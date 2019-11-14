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
import javax.xml.bind.annotation.XmlTransient;
@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="TransPackage")
@XmlRootElement(name="TransPackage")
public class TransPackage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3050390478904210174L;

	public TransPackage() {
	}
	
	@Column(name="ID", nullable=false)	
	@Id	
	@GeneratedValue(generator="MODEL_TRANSPACKAGE_ID_GENERATOR")	
	@org.hibernate.annotations.GenericGenerator(name="MODEL_TRANSPACKAGE_ID_GENERATOR", strategy="assigned")	
	private String ID;
	
	@Column(name="SourceNode", nullable=true, length=8)	
	private String sourceNode;
	
	@Column(name="TargetNode", nullable=true, length=8)	
	private String targetNode;
	
	@Column(name="CreateTime", nullable=true)	
	private Date createTime;
	
	@Column(name="Status", nullable=true, length=4)	
	private Integer status;
	
//	@OneToMany(mappedBy="pkg", targetEntity=PackageRoute.class)	
//	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
//	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.TRUE)	
//	private java.util.Set<PackageRoute> route = new java.util.HashSet<PackageRoute>();
//	
//	@OneToOne(mappedBy="pkg", targetEntity=UsersPackage.class, fetch=FetchType.LAZY)	
//	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
//	private UsersPackage user;
//	
//	@OneToMany(mappedBy="pkg", targetEntity=TransPackageContent.class)	
//	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
//	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.TRUE)	
//	private java.util.Set<TransPackageContent> content = new java.util.HashSet<TransPackageContent>();
//	
//	@OneToMany(mappedBy="pkg", targetEntity=TransHistory.class)	
//	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
//	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.TRUE)	
//	private java.util.Set<TransHistory> history = new java.util.HashSet<TransHistory>();
	
	public void setID(String value) {
		this.ID = value;
	}
	
	public String getID() {
		return ID;
	}
	
	public String getORMID() {
		return getID();
	}
	
	public void setSourceNode(String value) {
		this.sourceNode = value;
	}
	
	public String getSourceNode() {
		return sourceNode;
	}
	
	public void setTargetNode(String value) {
		this.targetNode = value;
	}
	
	public String getTargetNode() {
		return targetNode;
	}
	
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return status;
	}
	
//	public void setRoute(java.util.Set<PackageRoute> value) {
//		this.route = value;
//	}
//	
//	@XmlTransient
//	public java.util.Set<PackageRoute> getRoute() {
//		return route;
//	}
//	
//	
//	public void setUser(UsersPackage value) {
//		this.user = value;
//	}
//	
//	@XmlTransient
//	public UsersPackage getUser() {
//		return user;
//	}
//	
//	public void setContent(java.util.Set<TransPackageContent> value) {
//		this.content = value;
//	}
//	
//	@XmlTransient
//	public java.util.Set<TransPackageContent> getContent() {
//		return content;
//	}
//	
//	
//	public void setHistory(java.util.Set<TransHistory> value) {
//		this.history = value;
//	}
//	
//	@XmlTransient
//	public java.util.Set<TransHistory> getHistory() {
//		return history;
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
			sb.append("TransPackage[ ");
			sb.append("ID=").append(getID()).append(" ");
			sb.append("SourceNode=").append(getSourceNode()).append(" ");
			sb.append("TargetNode=").append(getTargetNode()).append(" ");
			sb.append("CreateTime=").append(getCreateTime()).append(" ");
			sb.append("Status=").append(getStatus()).append(" ");
//			sb.append("Route.size=").append(getRoute().size()).append(" ");
//			if (getUser() != null)
//				sb.append("User.Persist_ID=").append(getUser().toString(true)).append(" ");
//			else
//				sb.append("User=null ");
//			sb.append("Content.size=").append(getContent().size()).append(" ");
//			sb.append("History.size=").append(getHistory().size()).append(" ");
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
	
	public static final class STATUS {
		//public static final int 
		public static final int STATUS_CREATE = 0; // 新建的
        public static final int STATUS_RECEIVED = 1; // 揽收的
        public static final int STATUS_TRANSPORT = 2; // 转运中的
        public static final int STATUS_DELIVERIED = 3; // 派送的
        public static final int STATUS_HISTORY = 4; // 已完成的
        public static final int STATUS_PACKING = 5; //打包中的

	}
}
