package ts.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="position")
@XmlRootElement(name="Position")
public class Position implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 697344356324711538L;

	public Position() {
	}
	
	@Column(name="PosCode", nullable=false)	
	@Id	
	@GeneratedValue(generator="MODEL_Position_POSCODE_GENERATOR")	
	@org.hibernate.annotations.GenericGenerator(name="MODEL_Position_POSCODE_GENERATOR", strategy="native")	
	private int posCode;
	
	@Column(name="X", nullable=false, length=10)	
	private float x;
	
	@Column(name="Y", nullable=false, length=10)	
	private float y;
	
	@Column(name="packageId", nullable=true, length=60)	
	private String packageId;
	
	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	private void setPosCode(int value) {
		this.posCode = value;
	}
	
	public int getPosCode() {
		return posCode;
	}
	
	public int getORMID() {
		return getPosCode();
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
	
	
	
	public String toString() {
		return toString(false);
	}
	
	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(getPosCode());
		}
		else {
			StringBuffer sb = new StringBuffer();
			sb.append("Position[ ");
			sb.append("PosCode=").append(getPosCode()).append(" ");
			sb.append("X=").append(getX()).append(" ");
			sb.append("Y=").append(getY()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}
	
}
