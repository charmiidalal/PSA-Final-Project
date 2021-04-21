package City;

/* This is the City class which is intialized when program is started */
public class City {
    private int centerX;
    private int centerY;

    public City(int centerX, int centerY) {
        //Defined center of city
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }
}
