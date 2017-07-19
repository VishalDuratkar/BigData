book_info = load ‘Book_info.txt’ using PigStorage(‘,’) as (Book_Id:int, Price:int, Author_ID:int);

book_info_filtered = filter book_info by Price >200;

author_info = load ‘Author_info.txt’ using PigStorage(‘,’) as (Author_ID:int, Author_name);

book_info_filtered  =FILTER author_info by INDEXOF(Author_name,’J’,0)==0;

book_author_info = join book_info_filtered by  Author_ID, author_info_filtered by  Author_ID;

STORE book_author_info into ‘/store_info’ using PigStorage(‘,’);
