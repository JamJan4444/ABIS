package dao_implementation;

public class DAOTest {
    public static void main(String[] args) {
    		
    	//Get DAO-Instance
    	CustomerDAO customerDAO = CustomerDAO.getInstance();
    	
        System.out.println("Create a customer");
        Customer customer1 = new Customer(4711l, "Rensing", 1);
        
        System.out.println("Setze lokale Variable auf NULL und hole Kunden zurück");
        customer1 = null;
        customer1 = (Customer) customerDAO.read(4711l);
        System.out.println("Kunde ist " + customer1.get_CustomerGroup() + " "
                + customer1.get_Name()+", "+customer1.get_CustomerGroup());
        
        System.out.println("Aktualisiere den Kunden. Setze Gruppe auf 2");
        customer1.set_CustomerGroup(2);
        customer1 = null;
        customer1 = (Customer) customerDAO.read(4711l);
        System.out.println("Kunde hat jetzt Gruppe " + customer1.get_CustomerGroup());

        // Jetzt wird der Kunde gelöscht
        customerDAO.delete(customer1);
        customer1 = null;
        System.out.println("Versuche den Kunden nach Löschung erneut zu lesen:");
        customer1 = (Customer) customerDAO.read(4711l);
        System.out.println(customer1);
    }
}
