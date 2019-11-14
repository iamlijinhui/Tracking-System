package Client.misc.model;

import com.google.gson.annotations.Expose;

public class Location {

    @Expose private String packageId;
    @Expose private float x;
    @Expose private float y;

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Location{" +
                "packageId='" + packageId + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
