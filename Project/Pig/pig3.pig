app_details = load '/user/hive/warehouse/project.db/h1b_final/*' using PigStorage() as (s_no:int, case_status:chararray, employer_name:chararray, soc_name:chararray, job_title:chararray, full_time_position:chararray, prevailing_wage:int, year:int, worksite:chararray, longi:double, lati:double);
data_extract = FOREACH app_details generate $3 as soc_name, $4 as job_title; 
filterByJob = FILTER data_extract by job_title matches '.*DATA SCIENTIST*.';
groupBySoc = GROUP filterByJob by soc_name; 
countBySoc = FOREACH groupBySoc generate group, COUNT(filterByJob);
orderByCount = order countBySoc by $1 desc; 
Store orderByCount into '/project/project3' using PigStorage();
