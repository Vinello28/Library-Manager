package prj.library.database.DAO;

import prj.library.models.Customer;

import java.util.List;

/**
 * Interface for the CustomerDAO class
 */
public interface CustomerDAOInterface {

    /**
     * Creates a new customer
     * @param customer the customer to be created
     */
    void createCustomer (Customer customer);

    /**
     * Updates a customer
     * @param customer the customer to be updated
     */
    void updateCustomer (Customer customer);

    /**
     * Deletes a customer
     * @param customer the customer to be deleted
     */

    void deleteCustomer (Customer customer);

    /**
     * Finds a customer by their ID
     * @param id the ID of the customer
     * @return the customer with the given ID
     */
    Customer readCustomer (int id);

    /**
     * Finds a customer by their name
     * @param name the name of the customer
     * @return the customer with the given name
     */
    List<Customer> searchCustomerByName (String name);

    /**
     * Finds a customer by their phone number
     * @param phoneNumber the phone number of the customer
     * @return the customer with the given phone number
     */
    List<Customer> searchCustomerByPhoneNumber (String phoneNumber);

    /**
     * Finds a customer by their email
     * @param email the email of the customer
     * @return the customer with the given email
     */
    List<Customer> searchCustomerByEmail (String email);

    /**
     * Finds all customers
     * @return a list of all customers
     */
    List<Customer> readAllCustomers ();

    /**
     * Finds customers by address
     * @param address the address of the customer
     * @return a list of customers with the given address
     */
    List<Customer> searchCustomerByAddress (String address);

    /**
     * Finds customers by name and phone number
     * @param name the name of the customer
     * @param phoneNumber the phone number of the customer
     * @return a list of customers with the given name and phone number
     */
    List<Customer> searchCustomerByNameAndPhoneNumber (String name, String phoneNumber);

    /**
     * Finds customers by name and email
     * @param name the name of the customer
     * @param email the email of the customer
     * @return a list of customers with the given name and email
     */
    List<Customer> searchCustomerByNameAndEmail (String name, String email);

    /**
     * Finds customers by name and address
     * @param name the name of the customer
     * @param address the address of the customer
     * @return a list of customers with the given name and address
     */
    List<Customer> searchCustomerByNameAndAddress (String name, String address);

    /**
     * Finds customers by phone number and email
     * @param phoneNumber the phone number of the customer
     * @param email the email of the customer
     * @return a list of customers with the given phone number and email
     */
    List<Customer> searchCustomerByPhoneNumberAndEmail (String phoneNumber, String email);

    /**
     * Finds customers by phone number and address
     * @param phoneNumber the phone number of the customer
     * @param address the address of the customer
     * @return a list of customers with the given phone number and address
     */
    List<Customer> searchCustomerByPhoneNumberAndAddress (String phoneNumber, String address);

    /**
     * Finds customers by email and address
     * @param email the email of the customer
     * @param address the address of the customer
     * @return a list of customers with the given email and address
     */
    List<Customer> searchCustomerByEmailAndAddress (String email, String address);

    /**
     * Finds customers by name, phone number and email
     * @param name the name of the customer
     * @param phoneNumber the phone number of the customer
     * @param email the email of the customer
     * @return a list of customers with the given name, phone number and email
     */
    List<Customer> searchCustomerByNameAndPhoneNumberAndEmail (String name, String phoneNumber, String email);

    /**
     * Finds customers by name, phone number and address
     * @param name the name of the customer
     * @param phoneNumber the phone number of the customer
     * @param address the address of the customer
     * @return a list of customers with the given name, phone number and address
     */
    List<Customer> searchCustomerByNameAndPhoneNumberAndAddress (String name, String phoneNumber, String address);

    /**
     * Finds customers by name, email and address
     * @param name the name of the customer
     * @param email the email of the customer
     * @param address the address of the customer
     * @return a list of customers with the given name, email and address
     */
    List<Customer> searchCustomerByNameAndEmailAndAddress (String name, String email, String address);

    /**
     * Finds customers by phone number, email and address
     * @param phoneNumber the phone number of the customer
     * @param email the email of the customer
     * @param address the address of the customer
     * @return a list of customers with the given phone number, email and address
     */
    List<Customer> searchCustomerByPhoneNumberAndEmailAndAddress (String phoneNumber, String email, String address);

    /**
     * Finds customers by name, phone number, email and address
     * @param name the name of the customer
     * @param phoneNumber the phone number of the customer
     * @param email the email of the customer
     * @param address the address of the customer
     * @return a list of customers with the given name, phone number, email and address
     */
    List<Customer> searchCustomerByAll (String name, String phoneNumber, String email, String address);

}
