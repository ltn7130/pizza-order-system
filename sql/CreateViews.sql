-- Le Nguyen Shivam Patel
select ToppingName, ToppingCount from 
(select ToppingID, sum(IsDouble) as ToppingCount
from addtopping
group by ToppingID ) S left join topping on S.ToppingID = topping.ToppingID order by ToppingCount DESC;

select PizzaSize, PizzaCrust, sum(PizzaPrice - PizzaCost) as Profit, max(PizzaDate) as LastOrderDate
from pizza
group by PizzaSize, PizzaCrust
order by Profit DESC;

select * from
(select OrderType, DATE_FORMAT(OrderTimeStamp, "%Y %M") as OrderDate,sum(OrderPrice) as TotalOrderPrice, sum(OrderCost) as TotalOrderCost, sum(OrderPrice - OrderCost) as Profit from orderinfo
group by OrderType, OrderDate) a union all
select "", "Grand Total", sum(TotalOrderPrice), sum(TotalOrderCost), sum(Profit) from (select OrderType, DATE_FORMAT(OrderTimeStamp, "%Y %M") as OrderDate,sum(OrderPrice) as TotalOrderPrice, sum(OrderCost) as TotalOrderCost, sum(OrderPrice - OrderCost) as Profit from orderinfo
group by OrderType, OrderDate) b;
