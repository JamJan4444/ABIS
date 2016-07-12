package dao_framework;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public abstract class DAO {
	
    private final static String deleteStatementString = "DELETE FROM KUNDEN WHERE ID = ?";
	
    protected HashMap<Long, Object> registry = new HashMap<Long, Object>();
    protected Connection db;
	
    /* CRUD-Methods:
     * (C)reate
     * (R)ead
     * (U)pdate
     * (D)elete
     */
    
    public void create (DAO_Object o) throws SQLException {
		long id = o.get_id();
		if (registry.containsKey(id)) {
			throw new SQLException("Key "+ id + " has already been used!");
		} else {
			insert(o);
			registry.put(id, o);
    	}
	}
    
	public DAO_Object read(long p_id) throws SQLException {
		DAO_Object o = load(p_id);
		return o;
	}

	public void delete(DAO_Object o) throws SQLException {
		long id = o.get_id();
		if (registry.containsKey(id)) {
			remove(o);
			registry.remove(id);
		} else {
			throw new SQLException("Key "+ id + " has already been used!");
    	}
	}
	
	public void update(Object o) throws SQLException {
		update(o);
	}
	
	private void remove(DAO_Object o) {
        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = db.prepareStatement(deleteStatementString);
            deleteStatement.setLong(1, o.get_id());
            deleteStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	protected abstract void insert(DAO_Object o) throws SQLException;
	protected abstract DAO_Object load(long p_id);
}