default: src/Customer.java src/DBConnector.java src/DBNinja.java src/DeliveryOrder.java src/DineinOrder.java src/Discount.java src/Menu.java src/Menu.java src/Order.java
	javac -cp src/mysql-connector-j-8.0.31.jar: src/Customer.java src/DBConnector.java src/DBNinja.java src/DeliveryOrder.java src/DineinOrder.java src/Discount.java src/Menu.java src/Menu.java src/Order.java

run: src/Menu.class
	java -cp  src/mysql-connector-j-8.0.31.jar: src.Menu

clean: 
	rm -f src/*.class
