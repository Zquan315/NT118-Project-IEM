package Class;

public class Employee {
    private  String name;
    private  String depart;
    private  String role;
    private  String id;
    private  String key;
    private  String phone;
    private  String email;
    private  String gender;

    public Employee() {
    }
    public Employee(String name, String id, String key, String phone, String email, String department, String gender, String role) {
        this.name = name;
        this.id = id;
        this.key = key;
        this.phone = phone;
        this.email = email;
        this.depart = department;
        this.gender = gender;
        this.role = role;
    }


    public String getName() {
        return name;
    }
    public String getDepart() {
        return depart;
    }
    public String getRole() {return role;}
    public String getId() {return id;}
    public String getKey() {return key;}
    public String getPhone() {return phone;}
    public String getEmail() {return email;}
    public String getGender() {return gender;}


}
