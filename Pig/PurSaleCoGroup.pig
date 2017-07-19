purchase = load '/home/hduser/Downloads/purchase.txt' using PigStorage(',') as (product:int,pquant:int);

sales = load '/home/hduser/Downloads/sales.txt' using PigStorage(',') as (product:int,squant:int);

purchasesales = cogroup purchase by $0, sales by $0;

--describe purchasesales;
purchasesales: {group: int,purchase: {(product: int,pquant: int)},sales: {(product: int,squant: int)}}
/*
output :-
(101,	{(101,500),(101,200)},		{(101,200),(101,300)})
(102,	{(102,600),(102,300)},		{(102,300),(102,400)})
(103,	{(103,700),(103,400)},		{(103,600),(103,700)}) */

variances = foreach purchasesales generate group, SUM(purchase.pquant), SUM(sales.squant);
/*
(101,700,500)
(102,900,700)
(103,1100,1300) */

variances = foreach purchasesales generate group, SUM(purchase.pquant),COUNT(purchase), SUM(sales.squant), COUNT(sales);
/*
(101,700,2,500,2)
(102,900,2,700,2)
(103,1100,2,1300,2)
*/


final = foreach variances generate $0, $1 - $2;
/*
(101,200)
(102,200)
(103,-200)  */
