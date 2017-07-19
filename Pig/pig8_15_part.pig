app_details = load '/user/hive/warehouse/project.db/h1b_final/*' using PigStorage() as (s_no:int, case_status:chararray, employer_name:chararray, soc_name:chararray, job_title:chararray, full_time_position:chararray, prevailing_wage:int, year:int, worksite:chararray, longi:double, lati:double);
data_extract = FOREACH app_details generate $4 as job, $5 as shift, $6 as wage, $7 as year;
year15 = filter data_extract by year == 2015 and shift=='N';
groupByJob15 = GROUP year15 by job;
avg_cal15 = Foreach groupByJob15 generate group, AVG(year15.wage) as Average;
partTimeOrdered15 = Order avg_cal15 by Average desc;
Store partTimeOrdered15 into '/project/project8/2015/partTime' using PigStorage();
