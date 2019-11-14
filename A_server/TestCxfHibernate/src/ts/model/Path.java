package ts.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

@XmlRootElement(name="Path")
public class Path {
    private String userInfo;
    private int status;
    private String start;
    private String end;
    private String nodeName;
    private CustomerInfo receiver;
    private String nextNodeName;
    private List<Position> positons;

    public String getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

	public int getStatus() {
		return status;
	}
    
    @XmlElement
	public void setStatus(int status) {
		this.status = status;
	}

    public String getStart() {
		return start;
	}

    @XmlElement
	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	@XmlElement
	public void setEnd(String end) {
		this.end = end;
	}

	public String getNodeName() {
        return nodeName;
    }
    
    @XmlElement
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

	public CustomerInfo getReceiver() {
		return receiver;
	}

	@XmlElement
	public void setReceiver(CustomerInfo receiver) {
		this.receiver = receiver;
	}
	
	public String getNextNodeName() {
		return nextNodeName;
	}
	@XmlElement
	public void setNextNodeName(String nextNodeName) {
		this.nextNodeName = nextNodeName;
	}

	public List<Position> getPositons() {
		return positons;
	}
	@XmlElement
	public void setPositons(List<Position> positons) {
		this.positons = positons;
	}

	public Path() {
    }

    @Override
    public String toString() {
        return "Path{" +
                "userInfo='" + userInfo + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", nodeName='" + nodeName + '\'' +
                '}';
    }
}
