package ts.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="MissingExpressSheet")
public class MissingExpressSheet {

	private UserInfo userInfo;
	private ExpressSheet expressSheet;
	
	public UserInfo getUserInfo() {
		return userInfo;
	}
	
	@XmlElement
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	public ExpressSheet getExpressSheet() {
		return expressSheet;
	}
	
	@XmlElement
	public void setExpressSheet(ExpressSheet expressSheet) {
		this.expressSheet = expressSheet;
	}
	
	@Override
    public String toString() {
        return "MissingExpressSheet{" +
                "userInfo='" + userInfo.toString() + '\'' +
                ", expressSheet=" + expressSheet.toString() +
                '}';
    }
}
