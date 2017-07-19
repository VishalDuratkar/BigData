txn = load '/home/hduser/Downloads/txns1.txt' using PigStorage(',') as (txnid,date,custid,amount:double,category,product,city,state,type);
groupbytype = GROUP txn by type;
totalbytype = FOREACH groupbytype generate group as Type, SUM(txn.amount) as Total;
groupall = GROUP totalbytype all;
totalsale = FOREACH groupall generate ROUND_TO(SUM(totalbytype.Total),2) as TotalAmount;
final = FOREACH totalbytype generate $0, $1 , ROUND_TO(($1 * 100 )/totalsale.TotalAmount,2);
