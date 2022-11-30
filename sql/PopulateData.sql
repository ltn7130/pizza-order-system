-- Le Nguyen Shivam Patel

-- insert topping
insert into topping 
( ToppingName, ToppingPrice, ToppingCost, ToppingInventory, ToppingSmallUsed, ToppingMediumUsed, ToppingLargeUsed, ToppingXLargeUsed)
values 
("Pepperoni", 1.25, 0.2, 100, 2, 2.75, 3.5, 4.5),
("Sausage",1.25,0.15,100,2.5 ,3,3.5 ,4.25),
("Ham",1.5,0.15,78,2,2.5,3.25 ,4),
("Chicken",1.75,0.25,56,1.5,2,2.25,3),
("Green Pepper",0.5,0.02 ,79 ,1 ,1.5 ,2 ,2.5),
("Onion",0.5 ,0.02,85 ,1 ,1.5 ,2 ,2.75),
("Roma Tomato",0.75,0.03 ,86 ,2 ,3 ,3.5 ,4.5),
("Mushrooms",0.75, 0.1, 52, 1.5, 2, 2.5, 3),
("Black Olives",0.6, 0.1, 39, 0.75, 1, 1.5, 2),
("Pineapple",1, 0.25, 5, 1, 1.25, 1.75, 2),
("Jalapenos", 0.5, 0.05, 64, 0.5, 0.75, 1.25, 1.75),
("Banana Peppers", 0.5, 0.05, 36, 0.6, 1, 1.3, 1.75),
("Regular Cheese", 1.5, 0.12, 250, 2, 3.5, 5, 7),
("Four Cheese Blend", 2, 0.15, 150, 2, 3.5, 5, 7),
("Feta Cheese", 2, 0.18, 75, 1.75, 3, 4, 5.5),
("Goat Cheese",2, 0.2, 54, 1.6, 2.75, 4, 5.5),
("Bacon", 1.5, 0.25, 89, 1, 1.5, 2, 3);


-- insert discount
insert into discount 
(DiscountName,DiscountAmount, IsPercent)
values
("Employee",15, 0),
("Lunch Special Medium",1, 1),
("Lunch Special Large",2, 1),
("Specialty Pizza",1.5, 1),
("Gameday Special",20, 0);


-- insert base price
insert into baseprice
(PizzaSize, PizzaCrust, BasePrice, BaseCost)
values
("small", "Thin", 3, 0.5),
("small", "Original", 3, 0.75),
("small", "Pan", 3.5, 1),
("small", "Gluten-Free", 4, 2),
("medium", "Thin", 5, 1),
("medium", "Original", 5, 1.5),
("medium", "Pan", 6, 2.25),
("medium", "Gluten-free", 6.25, 3),
("large", "Thin", 8, 1.25),
("large", "Original", 8, 2),
("large", "Pan", 9, 3),
("large", "Gluten-Free", 9.5, 4),
("x-large", "Thin", 10, 2),
("x-large", "Original", 10, 3),
("x-large", "Pan", 11.5, 4.5),
("x-large", "Gluten-Free", 12.5, 6);

-- insert order 1
insert into orderinfo
values(
1,-1,"dinein","2022-03-05 12:03:36.285",11.5,3.68,0);
insert into pizza(PizzaID, PizzaSize, PizzaCrust, PizzaCost, PizzaPrice, OrderID, PizzaDate)
values(1,"large", "Thin", 3.68,11.5,1,"2022-03-05 12:03:36.285");
insert into addtopping
values (13, 1, 3.0, 1.2, 2);
update topping set ToppingInventory = ToppingInventory -2
where toppingID = 13;
insert into addtopping
values (1, 1, 1.25, 0.7, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 1;
insert into addtopping
values (2, 1, 1.25, 0.525, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 2;
insert into discount_pizza 
values(3,1);
insert into dinein
values (1, 14);

-- insert order 2

insert into orderinfo
values(
2,-1,"dinein","2022-04-03 12:05:49.939",14.85,4.625,0);
insert into pizza(PizzaID, PizzaSize, PizzaCrust, PizzaCost, PizzaPrice, OrderID, PizzaDate)
values(2,"medium", "Pan", 3.23,8.1,2,"2022-04-03 12:05:49.939" );
insert into addtopping
values (15, 2, 2.0, 0.54, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 15;
insert into addtopping
values (9, 2, 0.6, 0.1, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 9;
insert into addtopping
values (7, 2, 0.75, 0.09, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 7;
insert into addtopping
values (8, 2, 0.75, 0.2, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 8;
insert into addtopping
values (12, 2, 0.5, 0.05, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 12;
insert into discount_pizza 
values(2,2);
insert into discount_pizza 
values(4,2);
insert into pizza(PizzaID, PizzaSize, PizzaCrust, PizzaCost, PizzaPrice, OrderID, PizzaDate)
values(3,"small", "Original", 1.395,6.75,2,"2022-04-03 12:05:49.939" );
insert into addtopping
values (13, 3, 1.5, 0.24, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 13;
insert into addtopping
values (4, 3, 1.75, 0.375, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 4;
insert into addtopping
values (12, 3, 0.5, 0.03, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 12;
insert into dinein
values (2, 4);

-- insert to order 3
insert into customer(CustomerFirstName, CustomerLastName, CustomerPhoneNumber) 
values ("Andrew", "Wilkes-Krier", "864-254- 5861");

insert into orderinfo
values(
3,1,"pickup","2022-03-03 21:30:31.376",64.6,19.8,0);

insert into pizza(PizzaID, PizzaSize, PizzaCrust, PizzaCost, PizzaPrice, OrderID, PizzaDate)
values(4,"large", "Original", 3.3,10.75,3,"2022-03-03 21:30:31.376" );
insert into addtopping
values (13, 4, 1.5, 0.6, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 13;
insert into addtopping
values (1, 4, 1.25, 0.7, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 1;

insert into pizza(PizzaID, PizzaSize, PizzaCrust, PizzaCost, PizzaPrice, OrderID, PizzaDate)
values(5,"large", "Original", 3.3,10.75,3,"2022-03-03 21:30:31.376");
insert into addtopping
values (13, 5, 1.5, 0.6, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 13;
insert into addtopping
values (1, 5, 1.25, 0.7, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 1;

insert into pizza(PizzaID, PizzaSize, PizzaCrust, PizzaCost, PizzaPrice, OrderID, PizzaDate)
values(6,"large", "Original", 3.3,10.75,3,"2022-03-03 21:30:31.376" );
insert into addtopping
values (13, 6, 1.5, 0.6, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 13;
insert into addtopping
values (1, 6, 1.25, 0.7, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 1;

insert into pizza(PizzaID, PizzaSize, PizzaCrust, PizzaCost, PizzaPrice, OrderID, PizzaDate)
values(7,"large", "Original", 3.3,10.75,3,"2022-03-03 21:30:31.376" );
insert into addtopping
values (13, 7, 1.5, 0.6, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 13;
insert into addtopping
values (1, 7, 1.25, 0.7, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 1;

insert into pizza(PizzaID, PizzaSize, PizzaCrust, PizzaCost, PizzaPrice, OrderID, PizzaDate)
values(8,"large", "Original", 3.3,10.75,3,"2022-03-03 21:30:31.376" );
insert into addtopping
values (13, 8, 1.5, 0.6, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 13;
insert into addtopping
values (1, 8, 1.25, 0.7, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 1;

insert into pizza(PizzaID, PizzaSize, PizzaCrust, PizzaCost, PizzaPrice, OrderID, PizzaDate)
values(9,"large", "Original", 3.3,10.75,3,"2022-03-03 21:30:31.376");
insert into addtopping
values (13, 9, 1.5, 0.6, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 13;
insert into addtopping
values (1, 9, 1.25, 0.7, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 1;


insert into pickup
values (3);



-- insert order 4
insert into orderinfo
values(
4,1,"delivery","2022-04-20 19:11:24.768",35.2,16.72,0);
insert into pizza(PizzaID, PizzaSize, PizzaCrust, PizzaCost, PizzaPrice, OrderID, PizzaDate)
values(10,"x-large", "Original", 5.59,14.5,4,"2022-04-20 19:11:24.768");
insert into addtopping
values (1, 10, 1.25, 0.9, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 1;
insert into addtopping
values (2, 10, 1.25, 0.64, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 2;
insert into addtopping
values (14, 10, 2.0, 1.05, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 14;
insert into pizza(PizzaID, PizzaSize, PizzaCrust, PizzaCost, PizzaPrice, OrderID, PizzaDate)
values(11,"x-large", "Original", 6.25,15.5,4,"2022-04-20 19:11:24.768");
insert into addtopping
values (3, 11, 3.0, 1.2, 2);
update topping set ToppingInventory = ToppingInventory -2
where toppingID = 3;
insert into addtopping
values (10, 11, 2.0, 1.0, 2);
update topping set ToppingInventory = ToppingInventory -2
where toppingID = 10;
insert into addtopping
values (14, 11, 2.0, 1.05, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 14;
insert into discount_pizza 
values(4,11);
insert into pizza(PizzaID, PizzaSize, PizzaCrust, PizzaCost, PizzaPrice, OrderID, PizzaDate)
values(12,"x-large", "Original", 4.89,14.0,4,"2022-04-20 19:11:24.768");
insert into addtopping
values (11, 12, 0.5, 0.088, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 11;
insert into addtopping
values (17, 12, 1.5, 0.75, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 17;
insert into addtopping
values (14, 12, 2.0, 1.05, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 14;
insert into discount_order 
values(5,4);
insert into delivery
values (4, " 115 Party Blvd, Anderson SC 29621");

-- insert to order 5
insert into customer(CustomerFirstName, CustomerLastName, CustomerPhoneNumber) 
values ("Matt", "Engers", "864-474-9953");
insert into orderinfo
values(
5,2,"pickup","2022-03-02 17:30:57.992",16.1,7.84,0);
insert into pizza(PizzaID, PizzaSize, PizzaCrust, PizzaCost, PizzaPrice, OrderID, PizzaDate)
values(13,"x-large", "Gluten-Free", 7.84,16.1,5,"2022-03-02 17:30:57.992" );
insert into addtopping
values (5, 13, 0.5, 0.05, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 5;
insert into addtopping
values (6, 13, 0.5, 0.06, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 6;
insert into addtopping
values (7, 13, 0.75, 0.14, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 7;
insert into addtopping
values (8, 13, 0.75, 0.3, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 8;
insert into addtopping
values (9, 13, 0.6, 0.2, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 9;
insert into addtopping
values (16, 13, 2.0, 1.1, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 16;
insert into discount_pizza 
values(4,13);
insert into pickup
values (5);

-- insert to oreder 6

insert into customer(CustomerFirstName, CustomerLastName, CustomerPhoneNumber) 
values ("Frank", "Turner", "864-232-8944");
insert into orderinfo
values(
6,3,"delivery","2022-03-02 18:17:16.818",15.5,3.64,0);
insert into pizza(PizzaID, PizzaSize, PizzaCrust, PizzaCost, PizzaPrice, OrderID, PizzaDate)
values(14,"large", "Thin", 3.64,15.5,6,"2022-03-02 18:17:16.818");
insert into addtopping
values (4, 14, 1.75, 0.56, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 4;
insert into addtopping
values (5, 14, 0.5, 0.04, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 5;
insert into addtopping
values (6, 14, 0.5, 0.04, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 6;
insert into addtopping
values (8, 14, 0.75, 0.25, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 8;
insert into addtopping
values (14, 14, 4.0, 1.5, 2);
update topping set ToppingInventory = ToppingInventory -2
where toppingID = 14;
insert into delivery
values (6, "6745 Wessex St Anderson SC 29621");

-- insert order 7 
insert into customer(CustomerFirstName, CustomerLastName, CustomerPhoneNumber) 
values ("Milo", "Auckerman", "864-878-5679");
insert into orderinfo
values(
7,4,"delivery","2022-04-13 20:32:55.949",20.4,6.0,0);
insert into pizza(PizzaID, PizzaSize, PizzaCrust, PizzaCost, PizzaPrice, OrderID, PizzaDate)
values(15,"large", "Thin", 2.75,12.0,7,"2022-04-13 20:32:55.949");
insert into addtopping
values (14, 15, 4.0, 1.5, 2);
update topping set ToppingInventory = ToppingInventory -2
where toppingID = 14;
insert into pizza(PizzaID, PizzaSize, PizzaCrust, PizzaCost, PizzaPrice, OrderID, PizzaDate)
values(16,"large", "Thin", 3.25,12.0,7,"2022-04-13 20:32:55.949");
insert into addtopping
values (13, 16, 1.5, 0.6, 1);
update topping set ToppingInventory = ToppingInventory -1
where toppingID = 13;
insert into addtopping
values (1, 16, 2.5, 1.40, 2);
update topping set ToppingInventory = ToppingInventory -2
where toppingID = 1;
insert into discount_order 
values(1,7);
insert into delivery
values (7, "8879 Suburban Home, Anderson, SC 29621");