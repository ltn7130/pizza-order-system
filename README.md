# pizza-order-system
~~~
INSTRUCTION 
1. Modified src/DBConnector.java to create connection to your database
2. Run createTables.sql and PopulateData.sql to create table and populate data for pizza database system. 
3 .Use "make" to compile java files
4. Use "make run" to run java files
5. Use "make clean" to clean .class files
~~~

The most obvious thing that needs to be tracked as part of this database system is the information about each pizza. A pizza has a crust type (thin, original, pan, gluten free) and a size (personal, medium, large, x- large). A pizza has an associated price and cost to the company, both of which are determined by the size of the pizza and the toppings on the pizza. A pizza can be in two states: completed by the kitchen or still being processed by the kitchen. Each pizza can have multiple toppings. Each topping has a name, a price to the customer, a price to the business, an amount used for each pizza size, a minimum inventory level, and a current inventory level (which is updated whenever a pizza is ordered). The same topping can be on many pizzas (i.e., several pizzas can have pepperoni on them). A customer can request extra of any topping, which is always a double amount. Cheese counts as a topping (there is no free cheese in this organization).

Pizzas belong to orders. An order can be for dine in, pickup, or delivery. An order can have multiple pizzas on it. Each order has a total cost to the business, which is calculated by adding up the costs of each pizza. Each order should have a timestamp for when the food was ordered (so the kitchen can prioritize orders). Each order also has a total price to the customer, which is calculated by adding the prices of each pizza. If an order is for a dine in customer, then we need to know the table number. If an order is for pickup, then it needs to have a pickup customer associated with it. That customer must have a name and a phone number. If an order is for delivery, then it must have a delivery customer associated with it and include a name, phone number and address. A customer can have many orders, since the information is saved for the next time they order pizza. A customer could have some pickup orders, and some delivery orders. While other pizza places might allow a customer to save multiple addresses, Pizzas-R-Us only allows a customer to have one address.

Furthermore, Pizzas-R-Us offers discounts. Discounts can be applied to individual pizzas or an entire order. Discounts have a name and either a dollar amount off or a percentage off. A pizza or order can have multiple discounts applied to it, and a discount can be applied to many pizzas or orders.

The pizzeria also needs to track some base prices for their pizzas. Each pizza needs a base price, a crust type, a size, and a base cost. To compute the price of a pizza, you would look at the size and crust of the pizza and find the corresponding base price. Then to that you would add the price for each topping on the pizza. Finally, you would apply any discounts to the pizza. To find the total for the order, you would add up the price for each pizza, then apply any discounts that apply to the order. While the base prices or topping prices could change over time, those changes should not be reflected in past orders. So, a pizza’s price should be calculated once and saved. To find the cost of a pizza to the business, the same process is used, with costs instead of prices. Discounts do not lower the cost of the order to a business.

The pizzeria is under new management and will be very closing monitoring profitability. To make this easier to do, you will need to implement three views that support these reports. Management would like reports (aka views) on:
• Popular Toppings: rank order of all the toppings (accounting for extra toppings) from most popular to least popular
![image](https://user-images.githubusercontent.com/106266547/204679002-c73ca80e-cbe6-4b59-b80b-b46bc5cb6a9f.png)

• Profit by Pizza: a summary of the profit by pizza size and crust type over a selected time period ordered by profit from most profitable to least profitable
![image](https://user-images.githubusercontent.com/106266547/204679043-f72af3e9-3060-4878-9720-3f334205c6dd.png)

• Profit by Order Type: a summary of the profit for each of the three types of orders by month with a grand total over all the orders at the pizzeria
![image](https://user-images.githubusercontent.com/106266547/204679061-c216659d-a558-4022-a03f-7e971cf08e17.png)

Class relationship in Java files which take care of front-end, data processing, and conectivity to database.
 ![Screen Shot 2022-11-29 at 7 21 25 PM](https://user-images.githubusercontent.com/106266547/204677312-40933e2b-3936-4cb4-b1a0-f2b5755db68f.png)

Database Diagram

![AS1](https://user-images.githubusercontent.com/106266547/204678131-069f2519-c2d5-44e1-acec-cee1f6740f9a.jpg)
