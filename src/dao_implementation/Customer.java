package dao_implementation;

import dao_framework.DAO_Object;

public class Customer extends DAO_Object {
    
    private String name;
    private int customer_group;
    //für Zugriff KundeDAO
    private CustomerDAO customerDAO = CustomerDAO.getInstance();
    
    public Customer(Long customer_id, String name, int customer_group) {
        super(customer_id);
        this.set_Name(name);
        this.set_CustomerGroup(customer_group);
        
        try{
        	customerDAO.create(this);
        }
        catch(Exception e){
        	e.printStackTrace();
        }
    }

    public Long get_CustomerId() {
        return this.get_id();
    }

    public void set_CustomerId(Long p_customer_id) {
        this.set_id(p_customer_id);
        customerDAO.update(this);
    }

    public String get_Name() {
        return name;
    }

    public void set_Name(String name) {
        this.name = name;
        customerDAO.update(this);
    }

    public int get_CustomerGroup() {
        return this.customer_group;
    }

    public void set_CustomerGroup(int p_customer_group) {
        this.customer_group = p_customer_group;
        customerDAO.update(this);
    }
}
