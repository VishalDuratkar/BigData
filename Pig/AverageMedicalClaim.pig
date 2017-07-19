medical = load '/home/hduser/Downloads/medical.txt' using PigStorage('\t') as (Name,Dept,claim:double);
groupbyname = GROUP medical by Name;
--(tim,{(tim,TS,2750.0),(tim,TS,3500.0),(tim,TS,4750.0)})
--(jack,{(jack,hr,7500.0)})
avgclaim = FOREACH groupbyname generate group, AVG(medical.claim);

