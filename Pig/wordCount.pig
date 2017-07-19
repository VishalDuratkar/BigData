-- Command to run through local mode using parameter
-- pig -x local -p input=/home/hduser/Downloads/wordCount.txt wordcount.pig;
-- pig -x local -p input=/home/hduser/Downloads/wordCount.txt -f wordcount.pig;  
-- pig -x local -p input=/home/hduser/Downloads/wordCount.txt -p output=/home/hduser/Downloads/Count.txt wordcount.pig; 
-- pig -x local -p input=/home/hduser/Downloads/wordCount.txt -p myword=anita wordcount.pig; 

--command to run in hdfs mode
-- pig -p input=/home/hduser/Downloads/wordCount.txt -f wordcount.pig

--command to run in local mode with parameter file 
-- pig -x local -param_file /home/hduser/Downloads/parameter.txt wordcount.pig


word = load '$input' using TextLoader() as (lines:chararray);
--transform = foreach word generate TOKENIZE(lines) as word;
--({(Anita),(Hyderabad),(Smita)})
--transform: {word: {tuple_of_tokens: (token: chararray)}}
transform = foreach word generate FLATTEN(TOKENIZE(lines)) as word;
--(Anita)
--(Hyderabad)
--(Smita)
--transform: {word: chararray}

transform = foreach transform generate TRIM(LOWER(REPLACE(word, '[\\p{Punct},\\p{Cntrl}]',''))) as word;
--if need to find specific word
--transform = FILTER transform by word == '$myword';

groupbyword = group transform by word;
--(anita,{(anita),(anita),(anita),(anita),(anita)})
--groupbyword: {group: chararray,transform: {(word: chararray)}}

countofword = FOREACH groupbyword generate group, COUNT(transform);
--(anita,5)
--countofword: {group: chararray,long}
dump countofword;
