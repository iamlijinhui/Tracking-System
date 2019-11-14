package Client.misc.model;

public class UsersPackage {
    private UserInfo userU;
    private TransPackage pkg;

    public UserInfo getUserU() {
        return userU;
    }

    public void setUserU(UserInfo userU) {
        this.userU = userU;
    }

    public TransPackage getPkg() {
        return pkg;
    }

    public void setPkg(TransPackage pkg) {
        this.pkg = pkg;
    }

    @Override
    public String toString() {
        return "UsersPackage{" +
                "userU=" + userU +
                ", pkg=" + pkg +
                '}';
    }
}
