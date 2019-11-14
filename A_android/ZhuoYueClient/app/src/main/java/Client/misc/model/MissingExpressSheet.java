package Client.misc.model;

public class MissingExpressSheet {

    private UserInfo userInfo;
    private ExpressSheet expressSheet;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public ExpressSheet getExpressSheet() {
        return expressSheet;
    }

    public void setExpressSheet(ExpressSheet expressSheet) {
        this.expressSheet = expressSheet;
    }

    @Override
    public String toString() {
        return "MissingExpressSheet{" +
                "userInfo=" + userInfo +
                ", expressSheet=" + expressSheet +
                '}';
    }
}
