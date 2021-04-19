package City;

public class RandomMove {
    private int xAxis;
    private int yAxis;
    private boolean reLocated =false;

    public RandomMove(int xAxis, int y) {
        this.xAxis = xAxis;
        this.yAxis = y;
    }

    public int getxAxis() {
        return xAxis;
    }

    public void setxAxis(int xAxis) {
        this.xAxis = xAxis;
    }

    public int getyAxis() {
        return yAxis;
    }

    public void setyAxis(int yAxis) {
        this.yAxis = yAxis;
    }

    public boolean isReLocated() {
        return reLocated;
    }

    public void setReLocated(boolean reLocated) {
        this.reLocated = reLocated;
    }
}
