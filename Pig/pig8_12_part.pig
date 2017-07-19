app_details = load '/user/hive/warehouse/project.db/h1b_final/*' using PigStorage() as (s_no:int, case_status:chararray, employer_name:chararray, soc_name:chararray, job_title:chararray, full_time_position:chararray, prevailing_wage:int, year:int, worksite:chararray, longi:double, lati:double);
data_extract = FOREACH app_details generate $4 as job, $5 as shift, $6 as wage, $7 as year;
year12 = filter data_extract by year == 2012 and shift=='N';
groupByJob12 = GROUP year12 by job;
avg_cal12 = Foreach groupByJob12 generate group, AVG(year12.wage) as Average;
partTimeOrdered12 = Order avg_cal12 by Average desc;
Store partTimeOrdered12 into '/project/project8/2012/partTime' using PigStorage();
