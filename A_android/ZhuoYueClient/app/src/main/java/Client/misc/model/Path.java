package Client.misc.model;

import java.util.Date;
import java.util.List;

public class Path {
    String userInfo;
    private int status;
    private String start;
    private String end;
    private String nodeName;
    private String nextNodeName;
    private CustomerInfo receiver;
    private List<Location> positons;

    public String getNextNodeName() {
        return nextNodeName;
    }

    public void setNextNodeName(String nextNodeName) {
        this.nextNodeName = nextNodeName;
    }

    public List<Location> getLocations() {
        return positons;
    }

    public void setLocations(List<Location> locations) {
        this.positons = locations;
    }

    public CustomerInfo getReceiver() {
        return receiver;
    }

    public void setReceiver(CustomerInfo receiver) {
        this.receiver = receiver;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public int getStatus() {
        return status;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getNodeName() {
        return nodeName;
    }


    public void setStatus(int status) {
        this.status = status;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    @Override
    public String toString() {
        return "Path{" +
                "userInfo='" + userInfo + '\'' +
                ", status=" + status +
                ", start=" + start +
                ", end=" + end +
                ", nodeName='" + nodeName + '\'' +
                ", nextNodeName='" + nextNodeName + '\'' +
                ", receiver=" + receiver +
                ", positons=" + positons +
                '}';
    }
}
