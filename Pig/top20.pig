txn = load '/home/hduser/Downloads/txns1.txt' using PigStorage(',') as (txnid,date,custid,amount:double,category,product,city,state,type);

--Grouping
groupbycustomer = GROUP txn by custid;

--Calculate total sale by each customer
salecount = FOREACH groupbycustomer GENERATE group AS custid, ROUND_TO( SUM(txn.amount),2) as Total_Sale;

--Ordering by desc
orderbysalecount = order salecount by $1 desc;

--Fetching top 20 Records
top20 = limit orderbysalecount 10;

Join customer and Transaction
Top20Cuatomer = JOIN top20 by $0, cust by $0;
top20 = FOREACH Top20Cuatomer GENERATE $0,$3,$4,$5,$6,$1;
