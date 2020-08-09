import java.io.*;
import java.util.*;

public class Person {
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    private String address;

    public Person()
    {}

    public Person(String firstName, String lastName, String email, String userName, String password, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.address = address;
    }

    public Person(LinkedHashMap<String, String> usr) {
        for(Map.Entry itm:usr.entrySet()){
            //lists out data for person every time created
            //System.out.println(itm.getKey()+" "+itm.getValue());

            if(itm.getKey().equals("firstName"))    this.firstName  = (String)itm.getValue();
            if(itm.getKey().equals("lastName"))     this.lastName   = (String)itm.getValue();
            if(itm.getKey().equals("email"))        this.email      = (String)itm.getValue();
            if(itm.getKey().equals("userName"))     this.userName   = (String)itm.getValue();
            if(itm.getKey().equals("password"))     this.password   = (String)itm.getValue();
            if(itm.getKey().equals("address"))      this.address    = (String)itm.getValue();
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        sb.append("firstName").append(":").append(this.firstName).append(",");
        sb.append("lastName").append(":").append(this.lastName).append(",");
        sb.append("email").append(":").append(this.email).append(",");
        sb.append("userName").append(":").append(this.userName).append(",");
        sb.append("password").append(":").append(this.password).append(",");
        sb.append("address").append(":").append(this.address).append(",");
        sb.append("}");
        return sb.toString();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
