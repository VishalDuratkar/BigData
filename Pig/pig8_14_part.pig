app_details = load '/user/hive/warehouse/project.db/h1b_final/*' using PigStorage() as (s_no:int, case_status:chararray, employer_name:chararray, soc_name:chararray, job_title:chararray, full_time_position:chararray, prevailing_wage:int, year:int, worksite:chararray, longi:double, lati:double);
data_extract = FOREACH app_details generate $4 as job, $5 as shift, $6 as wage, $7 as year;
year14 = filter data_extract by year == 2014 and shift=='N';
groupByJob14 = GROUP year14 by job;
avg_cal14 = Foreach groupByJob14 generate group, AVG(year14.wage) as Average;
partTimeOrdered14 = Order avg_cal14 by Average desc;
Store partTimeOrdered14 into '/project/project8/2014/partTime' using PigStorage();
