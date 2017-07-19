weblog = load '/home/hduser/Downloads/weblog.txt' using PigStorage('\t') as (user, wgateway, accesstime:float);

gateway = load '/home/hduser/Downloads/gateway.txt' using PigStorage('\t') as (ggatway, stime:float);

joinusinggateway = join weblog by wgateway, gateway by ggatway;

groupbyuser = GROUP joinusinggateway by user;

averate = FOREACH groupbyuser generate group, AVG(joinusinggateway.stime);

filterbyrate = filter averate by $1 > 90;

STORE filterbyrate into ‘/reliable’ using PigStorage(‘,’);
