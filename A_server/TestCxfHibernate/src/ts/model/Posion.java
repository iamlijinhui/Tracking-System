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
@Table(name="Posion")
@XmlRootElement(name="Posion")
public class Posion implements Serializable {
	public Posion() {
	}
	
	@Column(name="PosCode", nullable=false)	
	@Id	
	@GeneratedValue(generator="MODEL_POSION_POSCODE_GENERATOR")	
	@org.hibernate.annotations.GenericGenerator(name="MODEL_POSION_POSCODE_GENERATOR", strategy="native")	
	private int posCode;
	
	@Column(name="X", nullable=false, length=10)	
	private float x;
	
	@Column(name="Y", nullable=false, length=10)	
	private float y;
	
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
			sb.append("Posion[ ");
			sb.append("PosCode=").append(getPosCode()).append(" ");
			sb.append("X=").append(getX()).append(" ");
			sb.append("Y=").append(getY()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}
	
}
