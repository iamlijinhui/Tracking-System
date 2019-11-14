package ts.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="position")
@XmlRootElement(name="Position")
public class Location {
	public Location() {
	}
	
	@Column(name="LocCode", nullable=false)	
	@Id	
	@GeneratedValue(generator="MODEL_Location_POSCODE_GENERATOR")	
	@org.hibernate.annotations.GenericGenerator(name="MODEL_Location_POSCODE_GENERATOR", strategy="native")	
	private int LocCode;
	
	@Column(name="pkgid", nullable=false, length=10)
	private String pkgid;
	
	@Column(name="Lat", nullable=false, length=10)	
	private float lat;
	
	@Column(name="Lng", nullable=false, length=10)	
	private float lng;
	
	
	
	public String getPkgid() {
		return pkgid;
	}

	public void setPkgid(String pkgid) {
		this.pkgid = pkgid;
	}
	
	public int getLocCode() {
		return LocCode;
	}

	public void setLocCode(int locCode) {
		LocCode = locCode;
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLng() {
		return lng;
	}

	public void setLng(float lng) {
		this.lng = lng;
	}

	public String toString() {
		return toString(false);
	}
	
	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(getLocCode());
		}
		else {
			StringBuffer sb = new StringBuffer();
			sb.append("Location[ ");
			sb.append("LocCode=").append(getLocCode()).append(" ");
			sb.append("Lat=").append(getLat()).append(" ");
			sb.append("Lng=").append(getLng()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}
}
