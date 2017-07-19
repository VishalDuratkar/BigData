app_details = load '/user/hive/warehouse/project.db/h1b_final/*' using PigStorage() as (s_no:int, case_status:chararray, employer_name:chararray, soc_name:chararray, job_title:chararray, full_time_position:chararray, prevailing_wage:int, year:int, worksite:chararray, longi:double, lati:double);

data_extract = FOREACH app_details generate $1 as case_status, $7 as Year;
groupByYear = GROUP data_extract by $1;
countByYear = FOREACH groupByYear generate group, COUNT(data_extract) as Total; 
Store countByYear into '/project/project7' using PigStorage('\t'); 
