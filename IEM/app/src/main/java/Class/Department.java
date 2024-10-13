package Class;

public class Department {
    private String name;
    private String header;
    private int amount_employee;
    private int amount_project;
    private int imageResource;

    public Department(String name, String header, int amount_employee, int amount_project, int imageResource)
    {
        this.name = name;
        this.header = header;
        this.amount_employee = amount_employee;
        this.amount_project = amount_project;
        this.imageResource = imageResource;
    }
    public String getName() {return name;}
    public String getHeader() {return header;}
    public int getAmount_employee() {return amount_employee;}
    public int getAmount_project() {return amount_project;}
    public int getImageResource() {return imageResource;}

    public void setName(String name){this.name = name;}
    public void setHeader(String header){this.header = header;}
    public void setAmount_employee(int amount_employee){this.amount_employee = amount_employee;}
    public void setAmount_project(int amount_project){this.amount_project = amount_project;}
    public void setImageResource(int imageResource){this.imageResource = imageResource;}
}
