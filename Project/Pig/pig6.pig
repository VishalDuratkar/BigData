app_details = load '/user/hive/warehouse/project.db/h1b_final/*' using PigStorage() as (s_no:int, case_status:chararray, employer_name:chararray, soc_name:chararray, job_title:chararray, full_time_position:chararray, prevailing_wage:int, year:int, worksite:chararray, longi:double, lati:double);

data_extract = FOREACH app_details generate $1 as case_status, $7 as Year;
groupByYear = GROUP data_extract by $1;
--Total app count by year
countByYear = FOREACH groupByYear generate group, COUNT(data_extract) as Total;  
groupByCaseYear = GROUP data_extract by (Year,case_status);
countgroupByCaseYear = FOREACH groupByCaseYear generate group, COUNT(data_extract);
sep_countgroupByCaseYear = FOREACH countgroupByCaseYear generate group.Year as Year, group.case_status as Case_Status, $1 as Total;
join_final = join sep_countgroupByCaseYear by $0, countByYear by $0;
Final_Output = FOREACH join_final generate $0,$1,$2,$4, ROUND_TO((double)($2 * 100 )/$4,2);
Store Final_Output into '/project/project6' using PigStorage('\t');
