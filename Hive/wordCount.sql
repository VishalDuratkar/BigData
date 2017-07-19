create table wordCount(line string);

load data local inpath '/home/hduser/Downloads/wordCount.txt' into table wordCount;

select split(line,' ') as word from wordcount;

select explode(split(line,' ')) as word from wordcount;

select word, count(*) from (select explode(split(line,' ')) as word from wordcount) a group by word;
