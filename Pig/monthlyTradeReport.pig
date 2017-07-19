bag2000 = load '/home/hduser/Downloads/Pig Retail/2000.txt' using PigStorage(',') as (id, name, jan:double, feb:double, mar:double, apr:double, may:double, jun:double, jul:double, aug:double, sep:double, oct:double, nov:double, dec:double);

bag2001 = load '/home/hduser/Downloads/Pig Retail/2001.txt' using PigStorage(',') as (id, name, jan:double, feb:double, mar:double, apr:double, may:double, jun:double, jul:double, aug:double, sep:double, oct:double, nov:double, dec:double);

bag2002 = load '/home/hduser/Downloads/Pig Retail/2002.txt' using PigStorage(',') as (id, name, jan:double, feb:double, mar:double, apr:double, may:double, jun:double, jul:double, aug:double, sep:double, oct:double, nov:double, dec:double);

bag2000 = FOREACH bag2000 generate $0,$1,($2+$3+$4+$5+$6+$7+$8+$9+$10+$11+$12+$13);
bag2001 = FOREACH bag2001 generat $0,$1,($2+$3+$4+$5+$6+$7+$8+$9+$10+$11+$12+$13);
bag2002 = FOREACH bag2002 generate $0,$1,($2+$3+$4+$5+$6+$7+$8+$9+$10+$11+$12+$13);

finalbag = join bag2000 by $0, bag2001 by $0, bag2002 by $0;
final = FOREACH finalbag generate $0, $1, $2, $5, $8; 
growth = FOREACH final generate $0,$1,$2,$3,$4,ROUND_TO(($3-$2)*100/$2,2) as f_cycle, ROUND_TO(($4-$3)*100/$3,2) as s_cycle;
growthfinal = FOREACH growth generate $0,$1,$2,$3,$4,$5,$6, ROUND_TO(($5 + $6)/2,2) as avggrowthrate;
