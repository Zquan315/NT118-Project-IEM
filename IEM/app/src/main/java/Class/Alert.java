package Class;

public class Alert
{
    String id, title;
    public Alert(String id, String title) {
        this.id = id;
        this.title = title;
    }
    public String getId() {return  id;}
    public String getTitle() {return  title;}
    public void setId(String id) {this.id = id;}
    public void setTitle(String title) {this.title = title;}
}
