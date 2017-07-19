--Load both tables
txn = load '/home/hduser/Downloads/txns1.txt' using PigStorage(',') as (txnid,date,custid,amount:double,category,product,city,state,type);

cust = load '/home/hduser/Downloads/custs.txt' using PigStorage(',') as (custid, firstname, lastname, age:long, profession);

groupbyid = GROUP txn by custid;
totalamt = FOREACH groupbyid generate group, ROUND_TO(SUM(txn.amount),2) as Total;
finaltxn = filter totalamt by Total > 500;
finalcust = filter cust by age < 50;
txnjoincust = JOIN finaltxn by $0, finalcust by $0;
final = FOREACH txnjoincust generate $0, $3, $4, $5, $6, $1;
store final into '/home/hduser/Downloads/custtxn' using PigStorage(',');
--store final into '/home/hduser/Downloads/custtxn' using BinStorage();
