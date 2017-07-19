app_details = load '/user/hive/warehouse/project.db/h1b_final/*' using PigStorage() as (s_no:int, case_status:chararray, employer_name:chararray, soc_name:chararray, job_title:chararray, full_time_position:chararray, prevailing_wage:int, year:int, worksite:chararray, longi:double, lati:double);
data_extract = FOREACH app_details generate $4 as job, $5 as shift, $6 as wage, $7 as year;
year16 = filter data_extract by year == 2016 and shift=='N';
groupByJob16 = GROUP year16 by job;
avg_cal16 = Foreach groupByJob16 generate group, AVG(year16.wage) as Average;
partTimeOrdered16 = Order avg_cal16 by Average desc;
Store partTimeOrdered16 into '/project/project8/2016/partTime' using PigStorage();
