/**
 * Singleton design pattern implementation to provide user session functionality,
 * global user-model-object scope across multiple scenes and guarantees a single existence of that object.
 */
package quotedecor.sessions;

import quotedecor.model.Company;
import quotedecor.model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Session {

    // static singleInstance holds one instance of the class
    public static Company company;
    public static Customer customer;

    // private constructor-prevents other classes from creating objects
    private Session(){}

    // create the company user session
    public static void createCompany(ResultSet rs) throws SQLException{
        if(company == null){
            System.out.print("CREATING COMPANY USER SESSION...");
            company = new Company(rs);
            customer = null;
            System.out.println("SESSION CREATED!");
        }
    }
    public static void createCustomer(ResultSet rs) throws SQLException {
        if(customer == null){
            System.out.print("CREATING CUSTOMER USER SESSION...");
            customer = new Customer(rs);
            company = null;
            System.out.println("SESSION CREATED!");
        }
    }

    // return the current session status => null = inactive session
    public static Company statusCompany(){
        return company;
    }
    public static Customer statusCustomer(){
        return customer;
    }
    public static void companyDestroy() {
        company = null;
    }
    public static void customerDestroy() {
        customer = null;
    }
    public static boolean isCustomerUser() {
        if(customer != null)
            return true;
        else return false;
    }
    public static boolean isCompanyUser() {
        if(company != null)
            return true;
        else return false;
    }
}
