package Class;

public class Resource {
    private final String name;
    private int num;
    private boolean isHardware;;


    public Resource(String name, int num, boolean isHardware) {
        this.name = name;
        this.num = num;
        this.isHardware = isHardware;

    }
    public String getName() {
        return name;
    }
    public int getNum() {
        return num;
    }
    public boolean getType() {
        return isHardware;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public void setType(boolean isHardware) {
        this.isHardware = isHardware;
    }
}
