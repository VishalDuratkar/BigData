app_details = load '/user/hive/warehouse/project.db/h1b_final/*' using PigStorage() as (s_no:int, case_status:chararray, employer_name:chararray, soc_name:chararray, job_title:chararray, full_time_position:chararray, prevailing_wage:int, year:int, worksite:chararray, longi:double, lati:double);
data_extract = FOREACH app_details generate $4 as job, $5 as shift, $6 as wage, $7 as year;
year13 = filter data_extract by year == 2013 and shift=='Y';
groupByJob13 = GROUP year13 by job;
avg_cal13 = Foreach groupByJob13 generate group, AVG(year13.wage) as Average;
fullTimeOrdered13 = Order avg_cal13 by Average desc;
Store fullTimeOrdered13 into '/project/project8/2013/fullTime' using PigStorage();
