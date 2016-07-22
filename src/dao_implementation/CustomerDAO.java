package dao_implementation;

import dao_framework.DAO;
import dao_framework.DAO_Object;
import java.sql.*;
import java.util.Properties;

public class CustomerDAO extends DAO {
    
	String userName = "**********";
	String password = "**********";
	
    // CustomerDAO is a singleton
    private static CustomerDAO instance = new CustomerDAO();
	public static CustomerDAO getInstance() {
    	return instance;
    }
	
	private ResultSet rs = null;
    
    private CustomerDAO() {
        try {
        	Class.forName("com.mysql.jdbc.Driver").newInstance();
            Properties connectionProps = new Properties();
            connectionProps.put("user", this.userName);
            connectionProps.put("password", this.password);
            db = DriverManager.getConnection("jdbc:mysql://localhost:3306/abis",connectionProps);
            PreparedStatement truncateStatement = db.prepareStatement("TRUNCATE abis.Customer");
            truncateStatement.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private final static String insertStatementString = "INSERT INTO Customer VALUES(?, ?, ?)";

	@Override
	protected void insert(DAO_Object o) {
		Customer c = (Customer) o;
        PreparedStatement insertStatement = null;
        try {
            insertStatement = db.prepareStatement(insertStatementString);
            insertStatement.setLong(1, c.get_CustomerId());
            insertStatement.setString(2, c.get_Name());
            insertStatement.setInt(3, c.get_CustomerGroup());
            insertStatement.execute();
            registry.put(c.get_CustomerId(), c);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

    private final static String updateStatementString = "UPDATE Customer SET Name = ?, CustomerId = ? WHERE CustomerGroup = ?";

    /** Methode zum Aktualisieren eines Kunden in der DB */
    public void update(Customer c) {
        PreparedStatement updateStatement = null;
        try {
            updateStatement = db.prepareStatement(updateStatementString);
            updateStatement.setLong(3, c.get_CustomerId());
            updateStatement.setString(1, c.get_Name());
            updateStatement.setInt(2, c.get_CustomerGroup());
            updateStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final static String findStatementString = "SELECT CustomerId, Name, CustomerGroup FROM Customer WHERE CustomerId=?";
	@Override
	protected DAO_Object load(long p_id) {
        Customer result = (Customer) registry.get(p_id);
        // Zunaechst in der Registry suchen
        if (result != null) {
            return result;
        }
        PreparedStatement findStatement = null;
        rs = null;
        try {
            findStatement = db.prepareStatement(findStatementString);
            findStatement.setLong(1, p_id);
            rs = findStatement.executeQuery();
            rs.next();
            result = doLoad(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
	}
	
    protected Customer doLoad(ResultSet rs) {
    	try {
	        Long id = new Long(rs.getLong(1));
	        Customer result = (Customer) registry.get(id);
	        if (result != null) {
	            return result;
	        }
	        String name = rs.getString(2);
	        int customer_group = rs.getInt(3);
	        result = new Customer(id, name, customer_group);
	        registry.put(id, result);
	        return result;
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    	return null;
    }
}
