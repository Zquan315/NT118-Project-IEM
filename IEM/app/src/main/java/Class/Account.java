package Class;

public class Account {
    String id, name, key, depart, phone, email, sex, role;
    Account(String id, String name, String key, String depart, String phone, String email, String sex, String role){
        this.id = id;
        this.name = name;
        this.key = key;
        this.depart = depart;
        this.phone = phone;
        this.email = email;
        this.sex = sex;
        this.role = role;
    }
    public String getId(){ return id;}
    public String getName(){ return name;}
    public String getKey(){ return key;}
    public String getDepart(){ return  depart;}
    public String getPhone(){ return phone;}
    public String getEmail(){ return email;}
    public String getSex(){ return sex;}
    public String getRole(){ return  role;}

    public void setId(String id){ this.id = id;}
    public void setName(String name){ this.name = name;}
    public void setKey(String key){ this.key = key;}
    public void setDepart(String depart){this.depart = depart;}
    public void setPhone(String phone){ this.phone = phone;}
    public void setEmail(String email){ this.email = email;}
    public void setSex(String sex){ this.sex = sex;}
    public void setRole(String role){this.role = role;}
}
