/*
Le Nguyen
 */

package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
/*
 * This file is where the front end magic happens.
 * 
 * You will have to write the functionality of each of these menu options' respective functions.
 * 
 * This file should need to access your DB at all, it should make calls to the DBNinja that will do all the connections.
 * 
 * You can add and remove functions as you see necessary. But you MUST have all 8 menu functions (9 including exit)
 * 
 * Simply removing menu functions because you don't know how to implement it will result in a major error penalty (akin to your program crashing)
 * 
 * Speaking of crashing. Your program shouldn't do it. Use exceptions, or if statements, or whatever it is you need to do to keep your program from breaking.
 * 
 * 
 */



public class Menu {
	//Use this global inventory to dynamically track current inventory levels
	public static ArrayList<Topping> inventories;
	static {
		try {
			inventories = DBNinja.getInventory();
		} catch (SQLException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) throws SQLException, IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Could not load the driver");

			System.out.println("Message     : " + e.getMessage());
		}


		int menu_option = -1;
		// present a menu of options and take their selection
		while (menu_option != 9) {
			PrintMenu();
			try {
				menu_option = Integer.parseInt(reader.readLine());
			}catch (Exception e){
				System.out.println("Only choose from 1 to 9");
				menu_option = -1;
			}

			switch (menu_option) {
			case 1:// enter order
				EnterOrder();
				break;
			case 2:// view customers
				viewCustomers();
				break;
			case 3:// enter customer
				EnterCustomer();
				break;
			case 4:// view order
				// open/closed/date
				ViewOrders();
				break;
			case 5:// mark order as complete
				MarkOrderAsComplete();
				break;
			case 6:// view inventory levels
				ViewInventoryLevels();
				break;
			case 7:// add to inventory
				AddInventory();
				break;
			case 8:// view reports
				PrintReports();
				break;
			}
		}
	}

	public static void PrintMenu() {
		System.out.println("\n\nPlease enter a menu option:");
		System.out.println("1. Enter a new order");
		System.out.println("2. View Customers ");
		System.out.println("3. Enter a new Customer ");
		System.out.println("4. View orders");
		System.out.println("5. Mark an order as completed");
		System.out.println("6. View Inventory Levels");
		System.out.println("7. Add Inventory");
		System.out.println("8. View Reports");
		System.out.println("9. Exit\n\n");
		System.out.println("Enter your option: ");
	}

	// allow for a new order to be placed
	public static void EnterOrder() throws SQLException, IOException 
	{
		/*
		 * EnterOrder should do the following:
		 * Ask if the order is for an existing customer -> If yes, select the customer. If no -> create the customer (as if the menu option 2 was selected).
		 *
		 * Ask if the order is delivery, pickup, or dinein (ask for orderType specific information when needed)
		 * 
		 * Build the pizza (there's a function for this)
		 * 
		 * ask if more pizzas should be be created. if yes, go back to building your pizza. 
		 * 
		 * Apply order discounts as needed (including to the DB)
		 * 
		 * apply the pizza to the order (including to the DB)
		 * 
		 * return to menu
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int custID = -1;
		int orderID = DBNinja.getMaxOrderID() + 1;
		Date date = new Date();
		String orderDate =  (new Timestamp(date.getTime())).toString();

		// type of order
		int orderType = 0;
		String[] orderTypeList = {"delivery", "pickup", "dinein"};
		while(orderType != 1 && orderType != 2 && orderType != 3){
			System.out.println("1. delivery   2. pickup   3. dinein\n");
			try{
				orderType = Integer.parseInt(reader.readLine());
			} catch (Exception e){
				System.out.println("Wrong format");
			}
			if(orderType != 1 && orderType != 2 && orderType != 3) System.out.println("Only choose 1, 2 or 3");
		}

		// get customer info if delivery or pickup

		if(orderType == 2 || orderType == 1){
			int existing_customer = 0;
			while(existing_customer != 1 && existing_customer != 2){
				System.out.println("1. Existing customer   2. Not existing customer\n");
				try {
					existing_customer = Integer.parseInt(reader.readLine());
				} catch (Exception e){
					System.out.println("Wrong format, only enter 1 or 2");
				}
			}

			//if existing customer, get id
			if(existing_customer == 1){
				while (custID  <= 0){
					System.out.println("What is your customer ID? It should be larger than 0");
					try{
						custID = Integer.parseInt(reader.readLine());
					} catch (Exception e){
						System.out.println("Wrong format, only enter an integer larger than 0");
					}
					if(custID > DBNinja.getMaxCustomerID()){
						custID = -1;
						System.out.println("Could not find customer's information in the System, enter customer ID again.");
					}
				}
			}

			// Not existing customer, add new customer
			if (existing_customer == 2){
				custID = DBNinja.getMaxCustomerID() + 1;
				EnterCustomer();
			}
		}


		//build pizza
		ArrayList<Pizza> pizzas = new ArrayList<Pizza>() ;
		String morePizza = "2";
		while(!morePizza.equals("1")){
			pizzas.add(buildPizza(orderID));
			System.out.println(" Press 1 to finish my order       Any key to build more pizza");
			morePizza = reader.readLine();
		}

		// get discount
		String moreDiscount = "";
		System.out.println("1: I don't have discount for order   Any other keys:Add Discount for order");
		ArrayList<Discount> discountList = DBNinja.getDiscountList();
		int num_discount = discountList.size();
		ArrayList<Discount> discounts = new ArrayList<Discount>();
		moreDiscount = reader.readLine();
		while (!moreDiscount.equals("1")){
			System.out.println("Enter your discount for order\n 1.Employee 2.Lunch special Medium " +
					"3.Lunch special large 4.Specialty Pizza 5. Gameday Special");
			int discountID = -1;
			while (discountID < 1 || discountID > num_discount){
				try	{
					discountID = Integer.parseInt(reader.readLine());
				} catch (Exception e){
					System.out.println("Wrong Format");
				}
				if(discountID < 1 || discountID > num_discount)
					System.out.println("Only choose discount ID from the list");
			}
			Discount curDiscount =  discountList.get(discountID - 1);
			discounts.add(curDiscount);
			// add discount to pizza
			System.out.println("Press to 1 finish adding discount for order or any key to apply more discount for order");
			moreDiscount = reader.readLine();
		}



		// calculate order total price and cost
		double orderBusPrice = 0;
		double orderCusPrice = 0;
		for(int i = 0; i < pizzas.size(); ++i){
			orderBusPrice += pizzas.get(i).getBusPrice();
			orderCusPrice += pizzas.get(i).getCustPrice();
		}

		// update price of order with discount
		Order order = new Order(orderID,custID, orderTypeList[orderType - 1], orderDate, orderCusPrice, orderBusPrice, 0);
		for(int i = 0; i < discounts.size(); ++i){
			order.addDiscount(discounts.get(i));
		}

		//add order to database
		DBNinja.addOrder(order);

		// add order and pizza to bridge table
		for(int i = 0; i < pizzas.size(); ++i){
			Pizza curPizza = pizzas.get(i);
			curPizza.setPizzaID(DBNinja.getMaxPizzaID() + 1);
			// ad the bridge table
			DBNinja.addPizza(curPizza, orderID);

			// add to topping pizza bridge table
			ArrayList<Topping> toppings = curPizza.getToppingList();
			ArrayList<Integer> curIsDoubles = curPizza.getIsToppingDouble();
			for(int j = 0;j < toppings.size(); ++j){
				DBNinja.useTopping(curPizza, toppings.get(j), curIsDoubles.get(j));
			}
			// add to pizza discount bigde table
			ArrayList<Discount> curDiscounts = curPizza.getDiscounts();
			for(int k  = 0; k < curDiscounts.size(); ++k){
				DBNinja.usePizzaDiscount(curPizza, curDiscounts.get(k));
			}
		}

		//add to order discount table
		for(int i = 0; i < discounts.size(); ++i){
			DBNinja.useOrderDiscount(order, discounts.get(i));
		}


		// Get pickup information
		if(orderType == 2){
			PickupOrder pickupOrder =
					new PickupOrder(orderID,custID,orderDate,orderCusPrice, orderBusPrice, 1, 0);
			DBNinja.addPickup(pickupOrder.getOrderID());
		}

		// delivery
		else if(orderType == 1){
			System.out.println("Enter delivery address");
			String address = reader.readLine();
			DeliveryOrder deliveryOrder =
					new DeliveryOrder(orderID, custID, orderDate, orderCusPrice, orderCusPrice, 0, address);
			DBNinja.addDelivery(orderID,deliveryOrder.getAddress());
		}

		//dinein
		else {
			System.out.println("Enter table number");
			int table = -1;
			while (table < 0){
				try {
					table = Integer.parseInt(reader.readLine());
				}catch (Exception e){
					System.out.println("Wrong format");
				}
				if( table < 0) System.out.println("table can not be negative");
			}
			DineinOrder dineinOrder =
					new DineinOrder(orderID, custID, orderDate, orderCusPrice, orderBusPrice, 0, table);
			DBNinja.addDinein(orderID, dineinOrder.getTableNum());

		}

		System.out.println("Finished adding order...Returning to menu...");
	}
	
	
	public static void viewCustomers() throws SQLException, IOException {
		/*
		 * Simply print out all the customers from the database.
		 */
		DBNinja.printCustomer();
	}
	

	// Enter a new customer in the database
	public static void EnterCustomer() throws SQLException, IOException 
	{
		/*
		 * Ask what the name of the customer is. YOU MUST TELL ME (the grader) HOW TO FORMAT THE FIRST NAME, LAST NAME, AND PHONE NUMBER.
		 * If you ask for first and last name one at a time, tell me to insert First name <enter> Last Name (or separate them by different print statements)
		 * If you want them in the same line, tell me (First Name <space> Last Name).
		 * 
		 * same with phone number. If there's hyphens, tell me XXX-XXX-XXXX. For spaces, XXX XXX XXXX. For nothing XXXXXXXXXXXX.
		 * 
		 * I don't care what the format is as long as you tell me what it is, but if I have to guess what your input is I will not be a happy grader
		 * 
		 * Once you get the name and phone number (and anything else your design might have) add it to the DB
		 */
		String fName = "";
		String lName = "";
		int ID = DBNinja.getMaxCustomerID() + 1;
		String phone = "";

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter your first name and press enter \n");
		fName = reader.readLine();
		System.out.println("Enter your last name and press enter \n");
		lName = reader.readLine();
		System.out.println("Enter your phone number in format ###-###-#### and press enter \n");
		phone = reader.readLine();
		Customer c = new Customer(ID, fName, lName, phone);
		DBNinja.addCustomer(c);
	}

	// View any orders that are not marked as completed
	public static void ViewOrders() throws SQLException, IOException 
	{
		/*
		 * This should be subdivided into two options: print all orders (using simplified view) and print all orders (using simplified view) since a specific date.
		 *
		 * Once you print the orders (using either sub option) you should then ask which order I want to see in detail
		 *
		 * When I enter the order, print out all the information about that order, not just the simplified view.
		 *
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int option = 0;
		// get view option
		while(option != 1 && option != 2) {
			System.out.println("1. View all order   2. View a order since a specific date \n");
			try{
				option = Integer.parseInt(reader.readLine());
			}catch (Exception e){
				System.out.println("Wrong format, choose only 1 or 2");
			}
		}

		//view all orders in simplify
		if(option == 1){
			ArrayList<Order> orders = DBNinja.getCurrentOrders();

			for (int i = 0; i < orders.size(); ++i){
				System.out.println(orders.get(i).toSimplePrint());
			}
		}
		//view orders since a date
		else {
			String date = "";
			while (date.equals("")){
				System.out.println("Enter date in format YYYY-MM-DD");
				date = reader.readLine();
				try {
					ArrayList<Order> orders = DBNinja.getOrdersSinceDate(date);
					for(int i = 0; i < orders.size(); ++i){
						System.out.println(orders.get(i).toSimplePrint());
					}
				}catch (Exception e){
					System.out.println("Wrong format");
					date = "";
				}
			}
		}

		// print a specific order
		int orderId = -1;
		while(orderId < 1 || orderId > DBNinja.getMaxOrderID()){
			System.out.println("Enter order ID you want to view in detail");
			try {
				orderId = Integer.parseInt(reader.readLine());
			}catch (Exception e){
				System.out.println("Wrong format");
			}
			if(orderId < 1 || orderId > DBNinja.getMaxOrderID()) System.out.println("Can not find your order");
		}
		System.out.println("Order Id is " + orderId );
		System.out.println(DBNinja.getOrder(orderId).toString());

	}

	
	// When an order is completed, we need to make sure it is marked as complete
	public static void MarkOrderAsComplete() throws SQLException, IOException 
	{
		/*All orders that are created through java (part 3, not the 7 orders from part 2) should start as incomplete
		 * 
		 * When this function is called, you should print all of the orders marked as complete 
		 * and allow the user to choose which of the incomplete orders they wish to mark as complete
		 * 
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		DBNinja.printOpenOrder();

		//get order ID to complete
		int orderID = -1;
		while(orderID <= 0 || orderID > DBNinja.getMaxOrderID()){
			System.out.println("Choose order ID you want to mark as completed");
			try {
				orderID = Integer.parseInt(reader.readLine());
			} catch (Exception e){
				System.out.println("Invalid order ID, enter order ID again");
			}
			if(orderID <= 0 || orderID > DBNinja.getMaxOrderID()) System.out.println("Order ID doesn't exist");
		}
		// complete order using orderID
		DBNinja.CompleteOrder(orderID);
	}

	// See the list of inventory and it's current level
	public static void ViewInventoryLevels() throws SQLException, IOException 
	{
		//print the inventory. I am really just concerned with the ID, the name, and the current inventory
		DBNinja.printInventory();
	}

	// Select an inventory item and add more to the inventory level to re-stock the
	// inventory
	public static void AddInventory() throws SQLException, IOException 
	{
		/*
		 * This should print the current inventory and then ask the user which topping they want to add more to and how much to add
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		//show current inventories
		DBNinja.printInventory();
		int id = 0;
		// get topping id that you want to add
		while(id <= 0 || id > DBNinja.getInventory().size()) {
			System.out.println("Enter inventory ID you want to add");
			try{
				id = Integer.parseInt(reader.readLine());
			}catch (Exception e) {
				System.out.println("Wrong format");
			}
		}
		// get amount we want to add
		int toAdd = 0;
		while(toAdd < 1) {
			System.out.println("Enter amount you want to add");
			try{
				toAdd = Integer.parseInt(reader.readLine());
			}catch (Exception e) {
				System.out.println("Wrong format");
			}
		}
		// Add inventory
		System.out.println("size of inventory is " + DBNinja.getInventory().size());
		Topping topping = DBNinja.getInventory().get(id - 1);
		DBNinja.AddToInventory(topping, toAdd);
		inventories.get(id - 1).setCurINVT(inventories.get(id - 1).getCurINVT() + toAdd);
	}

	// A function that builds a pizza. Used in our add new order function
	public static Pizza buildPizza(int orderID) throws SQLException, IOException 
	{
		
		/*
		 * This is a helper function for first menu option.
		 * 
		 * It should ask which size pizza the user wants and the crustType.
		 * 
		 * Once the pizza is created, it should be added to the DB.
		 * 
		 * We also need to add toppings to the pizza. (Which means we not only need to add toppings here, but also our bridge table)
		 * 
		 * We then need to add pizza discounts (again, to here and to the database)
		 * 
		 * Once the discounts are added, we can return the pizza
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		//input size
		String choice = "";
		String size = "";
		String[] pizzaSize = {"small", "medium", "large", "x-large"};
		while (!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4")){
			System.out.println("Choose Pizza Size \n 1.Small	2.Medium	3.Large		4.x-large");
			choice = reader.readLine();
			if (!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4"))
				System.out.println("Only choose 1, 2 , 3, or 4");
		}
		size = pizzaSize[Integer.parseInt(choice) - 1];

		// input crust
		choice = "";
		String[] pizzaCrust = {"Thin", "Original", "Pan", "Gluten-Free"};
		while (!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4")){
			System.out.println("Choose Pizza Crust \n 1.Thin		2.Original		3.Pan		4.Gluten-Free");
			choice = reader.readLine();
			if (!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4"))
				System.out.println("Only choose 1, 2 , 3, or 4");
		}
		String crustType = pizzaCrust[Integer.parseInt(choice) - 1];


		// add topping
		ArrayList<Topping> toppings = new ArrayList<Topping>();
		ArrayList<Integer> isDoubleList = new ArrayList<Integer>();

		String moreTopping = "2";
		while (!moreTopping.equals("1")){
			int topID = -1;
			while(topID < 1 || topID > 17){
				System.out.println("Choose Topping ID");
				DBNinja.printToppingList();
				try {
					topID = Integer.parseInt(reader.readLine());
				} catch (Exception e){
					System.out.println("Wrong format, enter an integer");
				}
				if(topID < 1 || topID > 17) System.out.println("Only choose topping ID from the list");
			}

			// ask for double topping
			System.out.println("1.Regular topping     2. double topping");
			int isDouble = -1;
			while (isDouble != 1 && isDouble != 2){
				try {
					isDouble = Integer.parseInt(reader.readLine());
				} catch (Exception e){
					System.out.println("Wrong format, enter an integer");
				}
				if(isDouble != 1 && isDouble != 2) System.out.println("Only choose 1 or 2");
			}
			Topping curTop = inventories.get(topID - 1);

			// check if enough topping
			if(curTop.getMinINVT() > curTop.getCurINVT() - isDouble){
				System.out.println("Out of topping, choose another topping");
				continue;
			}

			//update inventory
			inventories.get(topID - 1).setCurINVT(curTop.getCurINVT() - isDouble);
			// set isDouble
			isDoubleList.add(isDouble);
			// update topping list
			toppings.add(DBNinja.getTopping(topID));
			// ask for more topping
			System.out.println("Press 1 to finish adding topping         Any key to add more topping");
			moreTopping = reader.readLine();
		}


		// make pizza
		int pizzaID = DBNinja.getMaxPizzaID() + 1;
		String pizzaState = "done";
		Date date = new Date();
		String pizzaDate =  (new Timestamp(date.getTime())).toString();
		//calculate pizza price with toppings
		double pizzaCost = DBNinja.getBaseBusPrice(size, crustType);
		double pizzaPrice = DBNinja.getBaseCustPrice(size, crustType);
		System.out.println("base price " + pizzaPrice + "   base cost: "+ pizzaCost);
		for(int i = 0; i <toppings.size(); ++i){
			pizzaCost += DBNinja.getToppingCost(toppings.get(i),size)*isDoubleList.get(i);
			pizzaPrice += toppings.get(i).getCustPrice()*isDoubleList.get(i);
			toppings.get(i).setIsDouble(isDoubleList.get(i));
		}

		Pizza p = new Pizza(pizzaID, size, crustType, orderID, pizzaState, pizzaDate, pizzaPrice, pizzaCost);
		// add topping list to pizza
		p.setToppings(toppings);
		p.setIsToppingDoubled(isDoubleList);

		// get discount for pizza
		ArrayList<Discount> discountList = DBNinja.getDiscountList();
		ArrayList<Discount> discounts = new ArrayList<Discount>();
		System.out.println("Press 1 to skip discount        Any key to apply discount");
		String moreDiscount = reader.readLine();
		while (!moreDiscount.equals("1")){
			System.out.println("Enter your discount for pizza\n 1.Employee 2.Lunch special Medium 3.Lunch special large 4.Specialty Pizza 5. Gameday Special");
			int discountID = - 1;
			while (discountID < 1 || discountID > 5){
				try {
					discountID = Integer.parseInt(reader.readLine());
				}catch (Exception e){
					System.out.println("Wrong format, please enter an integer");
				}
				if(discountID < 1 || discountID > 5)
					System.out.println("Only choose discount ID from the list");
			}
			Discount curDiscount =  discountList.get(discountID - 1);
			System.out.println(curDiscount.toString());
			discounts.add(curDiscount);
			// add discount to pizza
			p.addDiscounts(curDiscount);
			System.out.println("Press 1 to finish applying discount        Any key to apply more discount");
			moreDiscount = reader.readLine();
		}

		// add discount list to pizza
		p.setDiscounts(discounts);

		return p;
	}

	
	public static void PrintReports() throws SQLException, NumberFormatException, IOException
	{
		/*
		 * This function calls the DBNinja functions to print the three reports.
		 * 
		 * You should ask the user which report to print
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String option = "";
		// get type of report
		while (!option.equals("1") && !option.equals("2") && !option.equals("3")){
			System.out.println("1. Print popular toppings report" +
					"    2. Print profit by pizza size and crust" +
					"    3. Print profit by order types");
			option = reader.readLine();
			if(!option.equals("1") && !option.equals("2") && !option.equals("3"))
				System.out.println("Only choose 1, 2 or 3");
		}
		// print popular topping
		if(option.equals("1")) DBNinja.printToppingPopReport();
		// print profit by pizza
		else if (option.equals("2")) DBNinja.printProfitByPizzaReport();
		// print profit by order types
		else DBNinja.printProfitByOrderType();
	}

}
