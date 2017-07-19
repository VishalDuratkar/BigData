INSERT OVERWRITE DIRECTORY '/project/project7' ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t' STORED AS TEXTFILE select year, count(*) from h1b_final group by year order by year;
