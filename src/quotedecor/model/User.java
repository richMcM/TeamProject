package quotedecor.model;

import java.util.ArrayList;

public abstract class User extends AppObject {
    // user properties
    protected String email, password, address;
    protected boolean isCompany;

    // composites
    public ArrayList<Quote> quotes;  // for customer quote search results and accepted quotes retrieve
    public ArrayList<Review> reviews;  // for user review retrieve
    public ArrayList<Project> projects;  // for project DB retrieve

    public User() {
        this.quotes = new ArrayList<>();
        this.reviews = new ArrayList<>();
        this.projects = new ArrayList<>();
    }
    // login init constructor
    public User(boolean isCompany) {
        this.email = null;
        this.password = null;
        this.address = null;
        this.isCompany = isCompany;
        this.quotes = new ArrayList<>();
        this.reviews = new ArrayList<>();
        this.projects = new ArrayList<>();
    }
    // initialization constructor
    public User(String email, String password, String address, boolean isCompany) {
        this.email = email;
        this.password = password;
        this.address = address;
        this.isCompany = isCompany;
        this.quotes = new ArrayList<>();
        this.reviews = new ArrayList<>();
        this.projects= new ArrayList<>();
    }

    // getter & setters
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
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
    public boolean isCompany() {
        return isCompany;
    }
    public void setCompany(boolean company) {
        isCompany = company;
    }
    public ArrayList<Quote> getQuotes() {
        return quotes;
    }
    public void setQuotes(ArrayList<Quote> quotes) {
        this.quotes = quotes;
    }
    public ArrayList<Review> getReviews() {
        return reviews;
    }
    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }
    public ArrayList<Project> getProjects() {
        return projects;
    }
    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }

    public abstract void retrieveProjects();
    public abstract void retrieveReviews();
    public abstract void retrieveAcceptedQuotes();
}
