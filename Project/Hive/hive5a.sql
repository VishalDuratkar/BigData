INSERT OVERWRITE DIRECTORY '/project/project5a' ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t' STORED AS TEXTFILE
select year,job_title, total from (select t.year, t.job_title, t.Total, rank() over (partition by year order by total desc) as rank from (select year,job_title, count(*) as Total from partitionByYear group by year,job_title order by year, Total desc) t ) ranking where rank <=10;
