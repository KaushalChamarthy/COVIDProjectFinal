public class card {

    private boolean flipped = false;
    private String value = null;
    private int x;
    private int y;

    public card(boolean flipped, String value)
    {
        this.flipped = flipped;
        this.value =  value;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isFlipped() {
        return flipped;
    }

    public String getValue() {
        return value;
    }

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
