INSERT OVERWRITE DIRECTORY '/project/project4' ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t' STORED AS TEXTFILE
select year,employer_name,Total from 
(
 select year,employer_name, rank() over (partition by year order by Total desc) as rank, Total 
 from ( select year, employer_name, count(*) as Total from partitionByYear group by year, employer_name order by year, Total Desc ) inner_table
) rankings where rankings.rank <=5;
