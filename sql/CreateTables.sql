-- Le Nguyen Shivam Patel
drop database if exists Pizzeria;
create database Pizzeria;
use Pizzeria;


CREATE TABLE baseprice(
	PizzaSize varchar(30) NOT NULL,
    PizzaCrust varchar(30) NOT NULL,
    BasePrice decimal(5,2) NOT NULL,
    BaseCost decimal(5,2) NOT NULL,
    PRIMARY KEY (PizzaSize, PizzaCrust)
);

CREATE TABLE customer(
CustomerID int NOT NULL AUTO_INCREMENT,
CustomerFirstName varchar(20),
CustomerLastName varchar(20),
CustomerPhoneNumber varchar(20),
PRIMARY KEY (CustomerID)
);

CREATE TABLE orderinfo(
	OrderID int NOT NULL AUTO_INCREMENT,
    CustomerID int,
    OrderType  varchar(20),
    OrderTimeStamp datetime NOT NULL default CURRENT_TIMESTAMP,
    OrderPrice decimal(5,2) NOT NULL default 0,
	OrderCost decimal(5,2) NOT NULL default 0,
    OrderStatus int Not null default 1,
    PRIMARY KEY (OrderID)
);


create table pizza(
	PizzaID INT NOT NULL AUTO_INCREMENT,
	PizzaSize varchar(30),
	PizzaCrust varchar(30),
	PizzaCost decimal(5,2) NOT NULL default 0,
	PizzaPrice decimal(5,2) NOT NULL default 0,
	OrderID int,
    PizzaDate datetime NOT NULL default CURRENT_TIMESTAMP,
	PRIMARY KEY (PizzaID),
    FOREIGN KEY (OrderID) REFERENCES orderinfo(OrderID)
);



CREATE TABLE topping(
	ToppingID int NOT NULL AUTO_INCREMENT,
    ToppingName varchar(30),
	ToppingSmallUsed decimal(5,2) NOT NULL DEFAULT 0,
    ToppingMediumUsed decimal(5,2) NOT NULL DEFAULT 0,
	ToppingLargeUsed decimal(5,2) NOT NULL DEFAULT 0,
    ToppingXLargeUsed decimal(5,2) NOT NULL DEFAULT 0,
    ToppingPrice decimal(5,2) NOT NULL DEFAULT 0,
    ToppingCost decimal(5,2) NOT NULL DEFAULT 0,
	MinInventory int NOT NULL DEFAULT 0,
    ToppingInventory int NOT NULL DEFAULT 0,
    PRIMARY KEY (ToppingID)
);

CREATE TABLE addtopping(
	ToppingID int NOT NULL,
    PizzaID int NOT NULL,
    ToppingTotalPrice decimal(5,2) NOT NULL,
    ToppingTotalCost decimal(5,2) NOT NULL,
    IsDouble int NOT NULL DEFAULT 1,
    PRIMARY KEY (PizzaID, ToppingID),
    FOREIGN KEY (PizzaID) REFERENCES pizza(PizzaID),
    FOREIGN KEY (ToppingID) REFERENCES topping(ToppingID)
);

CREATE TABLE discount(
	DiscountID int NOT NULL AUTO_INCREMENT,
    DiscountName varchar(30),
    DiscountAmount decimal(5,2) NOT NULL default 0,
    IsPercent int NOT NULL default 0,
    PRIMARY KEY (DiscountID)
    
);
CREATE TABLE discount_order(
	DiscountID int,
    OrderID int,
    PRIMARY KEY (DiscountID, OrderID),
    FOREIGN KEY (DiscountID) REFERENCES discount(DiscountID),
    FOREIGN KEY (OrderID) REFERENCES orderinfo(OrderID)
);

CREATE TABLE discount_pizza(
	DiscountID int,
    PizzaID int ,
    PRIMARY KEY (DiscountID, PizzaID),
    FOREIGN KEY (DiscountID) REFERENCES discount(DiscountID),
    FOREIGN KEY (PizzaID) REFERENCES pizza(PizzaID)
);

CREATE TABLE pickup(
	OrderID int NOT NULL,
    FOREIGN KEY (OrderID) REFERENCES orderinfo(OrderID)
);

CREATE TABLE dinein(
	OrderID int NOT NULL,
    DineinTableNumber int NOT NULL default 0,
    PRIMARY KEY (OrderID),
    FOREIGN KEY (OrderID) REFERENCES orderinfo(OrderID)
);

CREATE TABLE delivery(
	OrderID int NOT NULL,
    DeliveryAddress varchar(200) NOT NULL default "",
    PRIMARY KEY (OrderID),
    FOREIGN KEY (OrderID) REFERENCES orderinfo(OrderID)
);


