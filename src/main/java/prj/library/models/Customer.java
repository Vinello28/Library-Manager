package prj.library.models;

import java.io.Serializable;

/**
 * Customer class that represents a customer object
 */
public class Customer implements Serializable {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String address;

    /**
     * Constructor for the Customer class without id
     * @param name name of the customer
     * @param email email of the customer
     * @param phone phone number of the customer
     * @param address address of the customer
     */
    public Customer(String name, String email, String phone, String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    /**
     * Constructor for the Customer class with id
     * @param id id of the customer
     * @param name name of the customer
     * @param email email of the customer
     * @param phone phone number of the customer
     * @param address address of the customer
     */
    public Customer(int id, String name, String email, String phone, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    /**
     * Default constructor
     */
    public Customer() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return " " +
                "ID: " + id +
                ", '" + name + '\'' +
                ", '" + email + '\'' +
                ", cell: '" + phone + '\'' +
                ", address: '" + address + '\'' +
                ' ';
    }
}
