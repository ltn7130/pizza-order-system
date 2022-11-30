/*
Le Nguyen
Shivam Patel
 */

package src;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Formatter;
import java.util.Date;

/*
 * This file is where most of your code changes will occur You will write the code to retrieve
 * information from the database, or save information to the database
 * 
 * The class has several hard coded static variables used for the connection, you will need to
 * change those to your connection information
 * 
 * This class also has static string variables for pickup, delivery and dine-in. If your database
 * stores the strings differently (i.e "pick-up" vs "pickup") changing these static variables will
 * ensure that the comparison is checking for the right string in other places in the program. You
 * will also need to use these strings if you store this as boolean fields or an integer.
 * 
 * 
 */

/**
 * A utility class to help add and retrieve information from the database
 */

public final class DBNinja {
	private static Connection conn;

	// Change these variables to however you record dine-in, pick-up and delivery,
	// and sizes and
	// crusts
	public final static String pickup = "pickup";
	public final static String delivery = "delivery";
	public final static String dine_in = "dinein";

	public final static String size_s = "small";
	public final static String size_m = "medium";
	public final static String size_l = "Large";
	public final static String size_xl = "XLarge";

	public final static String crust_thin = "Thin";
	public final static String crust_orig = "Original";
	public final static String crust_pan = "Pan";
	public final static String crust_gf = "Gluten-Free";

	public DBNinja(){}
	/**
	 * This function will handle the connection to the database
	 * 
	 * @return true if the connection was successfully made
	 * @throws SQLException
	 * @throws IOException
	 */
	private static boolean connect_to_db() throws SQLException, IOException {

		try {
			conn = DBConnector.make_connection();
			return true;
		} catch (SQLException e) {
			return false;
		}

	}

	/**
	 *
	 * @param o order that needs to be saved to the database
	 * @throws SQLException
	 * @throws IOException
	 * @requires o is not NULL. o's ID is -1, as it has not been assigned yet. The
	 *           pizzas do not exist in the database yet, and the topping inventory
	 *           will allow for these pizzas to be made
	 * @ensures o will be assigned an id and added to the database, along with all
	 *          of its pizzas. Inventory levels will be updated appropriately
	 */
	public static void addOrder(Order o) throws SQLException, IOException {
		/*
		 * add code to add the order to the DB. Remember that we're not just
		 * adding the order to the order DB table, but we're also recording
		 * the necessary data for the delivery, dinein, and pickup tables
		 */
		connect_to_db();
		String query = "insert into orderinfo\n" + "values(?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement myStmt = conn.prepareStatement(query);
		myStmt.setInt(1,  o.getOrderID());
		myStmt.setInt(2, o.getCustID());
		myStmt.setString(3, o.getOrderType() );
		myStmt.setString(4, o.getDate());
		myStmt.setDouble(5, o.getCustPrice());
		myStmt.setDouble(6, o.getBusPrice());
		myStmt.setInt(7, o.getIsComplete());
		myStmt.executeUpdate();
		conn.close();
	}

	public static void addPickup(int orderID) throws SQLException, IOException{
		connect_to_db();
		String query = "insert into pickup\n" +
				"values (?);";
		PreparedStatement myStmt = conn.prepareStatement(query);
		myStmt.setInt(1,  orderID);
		myStmt.executeUpdate();
		conn.close();
	}

	public static void addDelivery(int orderID, String address) throws SQLException, IOException{
		connect_to_db();
		String query = "insert into delivery\n" +
				"values (?, ?);";
		PreparedStatement myStmt = conn.prepareStatement(query);
		myStmt.setInt(1,  orderID);
		myStmt.setString(2, address);
		myStmt.executeUpdate();
		conn.close();
	}

	public static void addDinein(int orderID, int table) throws SQLException, IOException{
		connect_to_db();
		String query = "insert into dinein\n" +
				"values (?, ?);";
		PreparedStatement myStmt = conn.prepareStatement(query);
		myStmt.setInt(1,  orderID);
		myStmt.setInt(2,  table);
		myStmt.executeUpdate();
		conn.close();
	}

	public static void addPizza(Pizza p, int orderID) throws SQLException, IOException
	{
		connect_to_db();
		/*
		 * Add the code needed to insert the pizza into into the database.
		 * Keep in mind adding pizza discounts to that bridge table and 
		 * instance of topping usage to that bridge table if you have't accounted
		 * for that somewhere else.
		 */
		String query = "insert into pizza(" +
				"PizzaID, PizzaSize, PizzaCrust, PizzaCost, PizzaPrice, OrderID, PizzaDate)\n" +
				"values(?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement myStmt = conn.prepareStatement(query);
		myStmt.setInt(1,  p.getPizzaID());
		myStmt.setString(2, p.getSize());
		myStmt.setString(3, p.getCrustType());
		myStmt.setDouble(4, p.getBusPrice());
		myStmt.setDouble(5, p.getCustPrice());
		myStmt.setInt(6, orderID);
		myStmt.setString(7, p.getPizzaDate());
		myStmt.executeUpdate();
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}
	
	public static int getMaxPizzaID() throws SQLException, IOException
	{
		/*
		 * A function I needed because I forgot to make my pizzas auto increment in my DB.
		 * It goes and fetches the largest PizzaID in the pizza table.
		 * You won't need this function if you didn't forget to do that
		 */
		connect_to_db();
		String ret = "";
		String query = "select max(PizzaID) from pizza;";
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);
		while(rset.next())
		{
			ret = rset.getString(1);
		}
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
		if (ret == null) return 0;
		return Integer.parseInt(ret);
	}

	public static String getCustomerName(int CustID) throws SQLException, IOException
	{
		/*
		 *This is a helper function I used to fetch the name of a customer
		 *based on a customer ID. It actually gets called in the Order class
		 *so I'll keep the implementation here. You're welcome to change
		 *how the order print statements work so that you don't need this function.
		 */
		connect_to_db();
		String ret = "";
		String query = "Select CustomerFirstName, CustomerLastName From customer WHERE CustomerID = ?;";
		PreparedStatement myStmt = conn.prepareStatement(query);
		myStmt.setInt(1, CustID);
		ResultSet rset =  myStmt.executeQuery();

		while(rset.next())
		{
			ret = rset.getString(1) + " " + rset.getString(2);
		}
		conn.close();
		return ret;
	}
	public static int getMaxOrderID() throws SQLException, IOException
	{
		/*
		 * A function I needed because I forgot to make my pizzas auto increment in my DB.
		 * It goes and fetches the largest PizzaID in the pizza table.
		 * You won't need this function if you didn't forget to do that
		 */
		connect_to_db();
		String ret = "";
		String query = "select max(OrderID) from orderinfo;";
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);
		while(rset.next())
		{
			ret = rset.getString(1);
		}
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
		if (ret == null) return 0;
		return Integer.parseInt(ret);
	}

	public static Topping getTopping(int ID) throws SQLException, IOException{
		connect_to_db();
		String query = "select * from topping where ToppingID = ?;";
		PreparedStatement myStmt = conn.prepareStatement(query);
		myStmt.setInt(1, ID);
		ResultSet rset =  myStmt.executeQuery();

		String name = "";
		double perAMT = 0;
		double medAMT = 0;
		double lgAMT = 0;
		double xLAMT = 0;
		double custPice = 0;
		double busPrice = 0;
		int minIN = 0;
		int curIN = 0;
		while(rset.next())
		{
			name = rset.getString(2);
			perAMT = Double.parseDouble(rset.getString(3));
			medAMT = Double.parseDouble(rset.getString(4));
			lgAMT = Double.parseDouble(rset.getString(5));
			xLAMT = Double.parseDouble(rset.getString(6));
			custPice = Double.parseDouble(rset.getString(7));
			busPrice = Double.parseDouble(rset.getString(8));
			minIN = Integer.parseInt(rset.getString(9));
			curIN = Integer.parseInt(rset.getString(10));
		}
		Topping t = new Topping(ID, name, perAMT, medAMT, lgAMT, xLAMT, custPice, busPrice, minIN, curIN);
		conn.close();
		return t;
	}

	public static void useTopping(Pizza p, Topping t, int isDoubled) throws SQLException, IOException //this function will update toppings inventory in SQL and add entities to the Pizzatops table. Pass in the p pizza that is using t topping
	{

		/*
		 * This function should 2 two things.
		 * We need to update the topping inventory every time we use t topping (accounting for extra toppings as well)
		 * and we need to add that instance of topping usage to the pizza-topping bridge if we haven't done that elsewhere
		 * Ideally, you should't let toppings go negative. If someone tries to use toppings that you don't have, just print
		 * that you've run out of that topping.
		 */
		double sizeUsed = t.getPerAMT();
		if(p.getSize().equals("medium")) sizeUsed = t.getMedAMT();
		if(p.getSize().equals("large")) sizeUsed = t.getLgAMT();
		if(p.getSize().equals("x-large")) sizeUsed = t.getXLAMT();

		connect_to_db();

		String query = "insert into addtopping\n" +
				"values (?, ?, ?, ?, ?);";
		PreparedStatement myStmt = conn.prepareStatement(query);
		myStmt.setInt(1, t.getTopID());
		myStmt.setInt(2, p.getPizzaID());
		myStmt.setDouble(3, t.getCustPrice()*isDoubled);
		myStmt.setDouble(4, t.getBusPrice()*isDoubled*sizeUsed );
		myStmt.setInt(5, isDoubled);
		myStmt.executeUpdate();
		// update topping inventory
		query = "update topping set ToppingInventory = ToppingInventory - ? where toppingID = ?;";
		myStmt = conn.prepareStatement(query);
		myStmt.setInt(1,  isDoubled);
		myStmt.setInt(2,  t.getTopID());
		myStmt.executeUpdate();
		conn.close();
	}
	
	public static void usePizzaDiscount(Pizza p, Discount d) throws SQLException, IOException
	{
		/*
		 * Helper function I used to update the pizza-discount bridge table. 
		 * You might use this, you might not depending on where / how to want to update
		 * this table
		 */
		connect_to_db();
		String query = "insert into discount_pizza \n" +
				"values( ?, ?);";
		PreparedStatement myStmt = conn.prepareStatement(query);
		myStmt.setInt(1, d.getDiscountID());
		myStmt.setInt(2, p.getPizzaID());
		myStmt.executeUpdate();
		conn.close();
	}
	
	public static void useOrderDiscount(Order o, Discount d) throws SQLException, IOException
	{
		/*
		 * Helper function I used to update the pizza-discount bridge table. 
		 * You might use this, you might not depending on where / how to want to update
		 * this table
		 */
		connect_to_db();
		String query = "insert into discount_order \n" +
				"values(?, ?);";
		PreparedStatement myStmt = conn.prepareStatement(query);
		myStmt.setInt(1, d.getDiscountID());
		myStmt.setInt(2, o.getOrderID());
		myStmt.executeUpdate();
		conn.close();
	}
	
	public static void addCustomer(Customer c) throws SQLException, IOException {

		/*
		 * This should add a customer to the database
		 */
		connect_to_db();
		String query = "insert into customer(CustomerFirstName, CustomerLastName, CustomerPhoneNumber) \n" +
				"values (?, ?, ?);";
		PreparedStatement myStmt = conn.prepareStatement(query);
		myStmt.setString(1, c.getFName());
		myStmt.setString(2, c.getLName());
		myStmt.setString(3, c.getPhone());
		myStmt.executeUpdate();
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}

	public static void CompleteOrder(int orderID) throws SQLException, IOException {
		/*
		 * add code to mark an order as complete in the DB. You may have a boolean field
		 * for this, or maybe a completed time timestamp. However, you have it.
		 */
		connect_to_db();
		String query = "update orderinfo\n" +
				"set OrderStatus = 1 "  +
				"where OrderID = ?;";

		PreparedStatement myStmt = conn.prepareStatement(query);
		myStmt.setInt(1, orderID);
		myStmt.executeUpdate();
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}

	public static void AddToInventory(Topping t, double toAdd) throws SQLException, IOException {
		/*
		 * Adds toAdd amount of topping to topping t.
		 */
		connect_to_db();
		String query = "update topping \n" +
				"set ToppingInventory = ToppingInventory + ?\n" +
				"where ToppingID = ?;";
		PreparedStatement myStmt = conn.prepareStatement(query);
		myStmt.setDouble(1, toAdd);
		myStmt.setInt(2, t.getTopID());
		myStmt.executeUpdate();

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}

	public static void printInventory() throws SQLException, IOException {
		/*
		 * I used this function to PRINT (not return) the inventory list.
		 * When you print the inventory (either here or somewhere else)
		 * be sure that you print it in a way that is readable.
		 * The topping list should also print in alphabetical order
		 */
		ArrayList<Topping> toppings = getInventoryByName();

		Formatter fmt = new Formatter();
		fmt.format("%7s %18s %8s\n",
				"TopID", "TopName", "CurInventory");
		for(int i = 0; i < toppings.size(); ++i){
			Topping t = toppings.get(i);
			fmt.format("%7s %18s %8s\n",
					t.getTopID(), t.getTopName(), t.getCurINVT());
		}

		System.out.println(fmt);

	}
	public static void printToppingList() throws SQLException, IOException {
		/*
		 * I used this function to PRINT (not return) the inventory list.
		 * When you print the inventory (either here or somewhere else)
		 * be sure that you print it in a way that is readable.
		 * The topping list should also print in alphabetical order
		 */
		ArrayList<Topping> toppings = getInventoryByName();
		Formatter fmt = new Formatter();
		fmt.format("%7s %18s\n",
				"TopID", "TopName");
		for(int i = 0; i < toppings.size(); ++i){
			Topping t = toppings.get(i);
			fmt.format("%7s %18s\n",
					t.getTopID(), t.getTopName(), t.getCurINVT());
		}

		System.out.println(fmt);

	}
	public static double getToppingCost(Topping t, String size) throws SQLException, IOException {
		if(size == "small") return t.getBusPrice()*t.getPerAMT();
		else if(size == "medium") return t.getBusPrice()*t.getMedAMT();
		else if (size == "large") return t.getBusPrice()*t.getLgAMT();
		return t.getBusPrice()*t.getXLAMT();
	}
	public static ArrayList<Topping> getInventoryByName() throws SQLException, IOException {
		connect_to_db();
		/*
		 * This function actually returns the toppings. The toppings
		 * should be returned in alphabetical order if you don't
		 * plan on using a printInventory function
		 */
		ArrayList<Topping> toppings = new ArrayList<Topping>();
		connect_to_db();
		String query = "SELECT * FROM TOPPING order by ToppingName;";
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);
		while(rset.next())
		{
			int id = Integer.parseInt(rset.getString(1));
			String name = rset.getString(2);
			double perAMT = Double.parseDouble(rset.getString(3));
			double medAMT = Double.parseDouble(rset.getString(4));
			double lgAMT = Double.parseDouble(rset.getString(5));
			double xLAMT = Double.parseDouble(rset.getString(6));
			double price = Double.parseDouble(rset.getString(7));
			double cost = Double.parseDouble(rset.getString(8));
			int minINVT = Integer.parseInt(rset.getString(9));
			int curINVT = Integer.parseInt(rset.getString(10));
			Topping t = new Topping(id, name, perAMT, medAMT, lgAMT, xLAMT, price, cost, minINVT, curINVT);
			toppings.add(t);
		}
		conn.close();
		return toppings;
	}
	public static ArrayList<Topping> getInventory() throws SQLException, IOException {
		connect_to_db();
		/*
		 * This function actually returns the toppings. The toppings
		 * should be returned in alphabetical order if you don't
		 * plan on using a printInventory function
		 */
		ArrayList<Topping> toppings = new ArrayList<Topping>();
		connect_to_db();
		String query = "SELECT * FROM TOPPING;";
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);
		while(rset.next())
		{
			int id = Integer.parseInt(rset.getString(1));
			String name = rset.getString(2);
			double perAMT = Double.parseDouble(rset.getString(3));
			double medAMT = Double.parseDouble(rset.getString(4));
			double lgAMT = Double.parseDouble(rset.getString(5));
			double xLAMT = Double.parseDouble(rset.getString(6));
			double price = Double.parseDouble(rset.getString(7));
			double cost = Double.parseDouble(rset.getString(8));
			int minINVT = Integer.parseInt(rset.getString(9));
			int curINVT = Integer.parseInt(rset.getString(10));
			Topping t = new Topping(id, name, perAMT, medAMT, lgAMT, xLAMT, price, cost, minINVT, curINVT);
			toppings.add(t);
		}
		conn.close();
		return toppings;
	}


	public static ArrayList<Order> getCurrentOrders() throws SQLException, IOException {
		connect_to_db();
		/*
		 * This function should return an arraylist of all of the orders.
		 * Remember that in Java, we account for supertypes and subtypes
		 * which means that when we create an arrayList of orders, that really
		 * means we have an arrayList of dineinOrders, deliveryOrders, and pickupOrders.
		 * 
		 * Also, like toppings, whenever we print out the orders using menu function 4 and 5
		 * these orders should print in order from newest to oldest.
		 */
		ArrayList<Order> orders = new ArrayList<Order>();
		connect_to_db();
		String query = "select * from orderinfo order by OrderTimestamp DESC;";
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);
		while(rset.next())
		{
			int orderID = Integer.parseInt(rset.getString(1));
			int custID = Integer.parseInt(rset.getString(2));
			String type = rset.getString(3);
			String date = rset.getString(4);
			double custPrice = Double.parseDouble(rset.getString(5));
			double busPrice = Double.parseDouble(rset.getString(6));
			int isComplete = Integer.parseInt(rset.getString(7));
			Order o = new Order(orderID, custID, type, date, custPrice, busPrice, isComplete);
			orders.add(o);
		}
		conn.close();
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		return orders;
	}
	public static Order getOrder(int orderID) throws SQLException, IOException {
		connect_to_db();
		int custID = -1;
		String type = "";
		String date = "22-11-11";
		double custPrice = 0;
		double busPrice = 0;
		int isComplete = 0;
		ArrayList<Order> orders = new ArrayList<>();
		String query = "select * from orderinfo where OrderID = ? ;";
		PreparedStatement myStmt = conn.prepareStatement(query);
		myStmt.setInt(1, orderID);
		ResultSet rset = myStmt.executeQuery();
		Order o = null;
		while(rset.next())
		{
			orderID = Integer.parseInt(rset.getString(1));
			custID = Integer.parseInt(rset.getString(2));
			type = rset.getString(3);
			date = rset.getString(4);
			custPrice = Double.parseDouble(rset.getString(5));
			busPrice = Double.parseDouble(rset.getString(6));
			isComplete = Integer.parseInt(rset.getString(7));
			o = new Order(orderID, custID, type, date, custPrice, busPrice, isComplete);
			orders.add(o);
		}
		return o;
	}
	public static void printOpenOrder() throws SQLException, IOException {
		connect_to_db();
		int custID = -1;
		String type = "";
		String date = "22-11-11";
		double custPrice = 0;
		double busPrice = 0;
		int isComplete = 0;
		ArrayList<Order> orders = new ArrayList<>();
		String query = "select * from orderinfo where OrderStatus = ? order by OrderTimestamp DESC;";
		PreparedStatement myStmt = conn.prepareStatement(query);
		myStmt.setInt(1, 0);
		ResultSet rset = myStmt.executeQuery();
		Order o;
		while(rset.next())
		{
			int orderID = Integer.parseInt(rset.getString(1));
			custID = Integer.parseInt(rset.getString(2));
			type = rset.getString(3);
			date = rset.getString(4);
			custPrice = Double.parseDouble(rset.getString(5));
			busPrice = Double.parseDouble(rset.getString(6));
			isComplete = Integer.parseInt(rset.getString(7));
			o = new Order(orderID, custID, type, date, custPrice, busPrice, isComplete);
			orders.add(o);
		}
		conn.close();
		printOrderList(orders);

	}


	public static ArrayList<Order> getOrdersSinceDate(String d) throws SQLException, IOException {
		/*
		 * I used this function to PRINT (not return) the inventory list.
		 * When you print the inventory (either here or somewhere else)
		 * be sure that you print it in a way that is readable.
		 * The topping list should also print in alphabetical order
		 */

		ArrayList<Order> orders = new ArrayList<Order>();
		connect_to_db();
		String query = "select * from orderinfo where \n" +
				"OrderTimeStamp >= ? order by OrderTimestamp DESC;";
		PreparedStatement myStmt = conn.prepareStatement(query);
		myStmt.setString(1, d);
		ResultSet rset = myStmt.executeQuery();
		while(rset.next())
		{
			int orderID = Integer.parseInt(rset.getString(1));
			int custID = Integer.parseInt(rset.getString(2));
			String type = rset.getString(3);
			String date = rset.getString(4);
			double custPrice = Double.parseDouble(rset.getString(5));
			double busPrice = Double.parseDouble(rset.getString(6));
			int isComplete = Integer.parseInt(rset.getString(7));
			Order o = new Order(orderID, custID, type, date, custPrice, busPrice, isComplete);
			orders.add(o);
		}
		conn.close();
		return orders;
	}

	public static void printOrderList(ArrayList<Order> orders) throws SQLException, IOException {
		Formatter fmt = new Formatter();
		fmt.format("%8s %8s %8s %24s %8s %8s %8s\n",
				"OrderID", "CustID", "Type", "Date", "Price",
				"Cost", "Status");
		for(int i = 0; i < orders.size(); ++i){
			Order o = orders.get(i);
			fmt.format("%8s %8s %8s %24s %8s %8s %8s\n",
					o.getOrderID(), o.getCustID(), o.getOrderType(), o.getDate(),
					o.getCustPrice(), o.getBusPrice(), o.getIsComplete());
		}
		System.out.println(fmt);
	}

	public static double getBaseCustPrice(String size, String crust) throws SQLException, IOException {
		// add code to get the base price (for the customer) for that size and crust pizza Depending on how
		// you store size & crust in your database, you may have to do a conversion
		double bp = 0.0;
		connect_to_db();
		String query = "select BasePrice from baseprice\n" +
				"where PizzaSize = ? and PizzaCrust = ?;";

		String ret = "";
		PreparedStatement myStmt = conn.prepareStatement(query);
		myStmt.setString(1, size);
		myStmt.setString(2, crust);
		ResultSet rset = myStmt.executeQuery();
		while(rset.next())
		{
			ret = rset.getString(1);
		}
		bp = Double.parseDouble(ret);
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
		return bp;
	}

	public static double getBaseBusPrice(String size, String crust) throws SQLException, IOException {
		connect_to_db();
		String query = "select BaseCost from baseprice\n" +
				"where PizzaSize = ? and PizzaCrust = ?;";
		String ret = "";
		PreparedStatement myStmt = conn.prepareStatement(query);
		myStmt.setString(1, size);
		myStmt.setString(2, crust);
		ResultSet rset = myStmt.executeQuery();
		while(rset.next())
		{
			ret = rset.getString(1);
		}
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
		return Double.parseDouble(ret);
	}

	
	public static ArrayList<Discount> getDiscountList() throws SQLException, IOException {
		//returns a list of all the discounts.
		ArrayList<Discount> discs = new ArrayList<Discount>();
		connect_to_db();
		String query = "select * from discount;";
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);
		while(rset.next())
		{
			int id = Integer.parseInt(rset.getString(1));
			String name = rset.getString(2);
			double amount = Double.parseDouble(rset.getString(3));
			boolean isPercent = Integer.parseInt(rset.getString(4)) != 1;
			Discount d = new Discount(id,name,amount, isPercent);
			discs.add(d);
		}
		conn.close();
		return discs;
	}


	public static ArrayList<Customer> getCustomerList() throws SQLException, IOException {

		/*
		 * return an arrayList of all the custotops.add(DBNinja.getTopping(topID));mers. These customers should
		 *print in alphabetical order, so account for that as you see fit.
		*/
		ArrayList<Customer> custs = new ArrayList<Customer>();
		connect_to_db();
		Statement stmt = conn.createStatement();
		String query = "select * from customer order by CustomerFirstName, CustomerLastName, CustomerPhoneNumber;";
		ResultSet rset = stmt.executeQuery(query);

		while(rset.next())
		{
			int id = Integer.parseInt(rset.getString(1));
			String fName = rset.getString(2);
			String lName = rset.getString(3);
			String phone = rset.getString(4);
			Customer c = new Customer(id, fName, lName, phone);
			custs.add(c);
		}
		conn.close();
		return custs;
	}

	public  static void printCustomer() throws SQLException, IOException{
		ArrayList<Customer> customers = getCustomerList();
		Formatter fmt = new Formatter();
		fmt.format("%8s %15s %15s %15s\n",
				"CustID", "FirstName", "LastName", "Phone");
		for(int i = 0; i < customers.size(); ++i){
			Customer c = customers.get(i);
			fmt.format("%8s %15s %15s %15s\n", c.getCustID(), c.getFName(), c.getLName(), c.getPhone());
		}
		System.out.println("CUSTOMER LIST");
		System.out.println(fmt);
	}

	public static int getMaxCustomerID() throws SQLException, IOException{
		connect_to_db();
		String ret = "";
		String query = "select max(CustomerID) from customer;";
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);
		while(rset.next())
		{
			ret = rset.getString(1);
		}
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
		if (ret == null) return 0;
		return Integer.parseInt(ret);
	}

	public static void printToppingPopReport() throws SQLException, IOException
	{

		/*
		 * Prints the ToppingPopularity view. Remember that these views
		 * need to exist in your DB, so be sure you've run your createViews.sql
		 * files on your testing DB if you haven't already.
		 * 
		 * I'm not picky about how they print (other than that it should
		 * be in alphabetical order by name), just make sure it's readable.
		 */

		connect_to_db();
		String ret = "";
		String query = "select ToppingName, ToppingCount from \n" +
				"(select ToppingID, sum(IsDouble) as ToppingCount\n" +
				"from addtopping\n" +
				"group by ToppingID ) S right join " +
				"topping on S.ToppingID = topping.ToppingID order by ToppingCount DESC;\n";
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);
		ArrayList<String> topName = new ArrayList<String>();
		ArrayList<String> topCount = new ArrayList<String>();
		while(rset.next())
		{
			topName.add(rset.getString(1)) ;
			topCount.add(rset.getString(2)) ;
		}
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
		Formatter fmt = new Formatter();
		fmt.format("%20s %8s\n",
				"ToppingName", "ToppingCount");
		for(int i = 0; i < topName.size(); ++i){
			fmt.format("%20s %8s\n", topName.get(i), topCount.get(i));
		}
		System.out.println(fmt);

	}
	
	public static void printProfitByPizzaReport() throws SQLException, IOException
	{

		/*
		 * Prints the ProfitByPizza view. Remember that these views
		 * need to exist in your DB, so be sure you've run your createViews.sql
		 * files on your testing DB if you haven't already.
		 * 
		 * I'm not picky about how they print, just make sure it's readable.
		 */
		connect_to_db();
		String query = "select PizzaSize, PizzaCrust, sum(PizzaPrice - PizzaCost) as Profit," +
				" max(PizzaDate) as LastOrderDate\n" +
				"from pizza\n" +
				"group by PizzaSize, PizzaCrust\n" +
				"order by Profit DESC;\n";
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);
		ArrayList<String> sizes = new ArrayList<String>();
		ArrayList<String> crust = new ArrayList<String>();
		ArrayList<String> profit = new ArrayList<String>();
		ArrayList<String> dates = new ArrayList<String>();
		while(rset.next())
		{
			sizes.add(rset.getString(1)) ;
			crust.add(rset.getString(2)) ;
			profit.add(rset.getString(3)) ;
			dates.add(rset.getString(4)) ;
		}
		Formatter fmt = new Formatter();
		fmt.format("%20s %15s %15s %30s\n",
				"PizzaSize", "PizzaCrust","Profit", "LastOrderDate");

		for(int i = 0; i < sizes.size(); ++i){
			fmt.format("%20s %15s %15s %30s\n",sizes.get(i), crust.get(i), profit.get(i), dates.get(i));
		}
		System.out.println(fmt);
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
	}
	
	public static void printProfitByOrderType() throws SQLException, IOException
	{
		/*
		 * Prints the ProfitByOrderType view. Remember that these views
		 * need to exist in your DB, so be sure you've run your createViews.sql
		 * files on your testing DB if you haven't already.
		 * 
		 * I'm not picky about how they print, just make sure it's readable.
		 */
		connect_to_db();
		String query = "select * from\n" +
				"(select OrderType, DATE_FORMAT(OrderTimeStamp, \"%Y %M\") as OrderDate," +
				"sum(OrderPrice) as TotalOrderPrice, sum(OrderCost) as TotalOrderCost, " +
				"sum(OrderPrice - OrderCost) as Profit from orderinfo\n" +
				"group by OrderType, OrderDate) a union all\n" +
				"select \"\", \"Grand Total\", sum(TotalOrderPrice), sum(TotalOrderCost), " +
				"sum(Profit) from (select OrderType, DATE_FORMAT(OrderTimeStamp, \"%Y %M\") as OrderDate," +
				"sum(OrderPrice) as TotalOrderPrice, sum(OrderCost) as TotalOrderCost, " +
				"sum(OrderPrice - OrderCost) as Profit from orderinfo\n" +
				"group by OrderType, OrderDate) b;";
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);

		ArrayList<String> types = new ArrayList<String>();
		ArrayList<String> dates = new ArrayList<String>();
		ArrayList<String> prices = new ArrayList<String>();
		ArrayList<String> cost = new ArrayList<String>();
		ArrayList<String> profit = new ArrayList<String>();
		while(rset.next())
		{
			types.add(rset.getString(1)) ;
			dates.add(rset.getString(2)) ;
			prices.add(rset.getString(3)) ;
			cost.add(rset.getString(4)) ;
			profit.add(rset.getString(5)) ;
		}

		Formatter fmt = new Formatter();
		fmt.format("%20s %20s %20s %20s %20s\n",
				"OrderType", "OrderDate","TotalOrderPrice", "TotalOrderCost", "Profit");

		for(int i = 0; i < types.size(); ++i){
			fmt.format("%20s %20s %20s %20s %20s\n",types.get(i), dates.get(i), prices.get(i), cost.get(i), profit.get(i));
		}
		System.out.println(fmt);
		conn.close();
	}
}
