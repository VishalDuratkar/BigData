weblog = load '/home/hduser/weblog.txt' using PigStorage() as (name,dept,claim:double);

getway = load '/home/hduser/gateway.txt' using PigStorage() as (bank,rate:double);


joined = join weblog by $1,getway by $0;


--dump joined;

datatable = foreach joined generate $0 as name,$1 as bank,$4 as rate;

--dump datatable;

groupdata = group datatable by name;

--dump groupdata;

result = foreach groupdata generate group , ROUND_TO(AVG(datatable.rate),2) as avgrate;



--dump result;

result2 = filter result by avgrate > 90;


dump result2;


