app_details = load '/user/hive/warehouse/project.db/h1b_final/*' using PigStorage() as (s_no:int, case_status:chararray, employer_name:chararray, soc_name:chararray, job_title:chararray, full_time_position:chararray, prevailing_wage:int, year:int, worksite:chararray, longi:double, lati:double);
data_extract = FOREACH app_details generate $4 as job, $5 as shift, $6 as wage, $7 as year;
year11 = filter data_extract by year == 2011 and shift=='N';
groupByJob11 = GROUP year11 by job;
avg_cal11 = Foreach groupByJob11 generate group, AVG(year11.wage) as Average;
partTimeOrdered11 = Order avg_cal11 by Average desc;
Store partTimeOrdered11 into '/project/project8/2011/partTime' using PigStorage();
