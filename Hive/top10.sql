select a.custno, b.firstname, b.lastname, b.age, b.profession, sum(a.amount) as amt
    from txnrecords a, customer b
    where a.custno = b.custno
    group by a.custno, b.firstname, b.lastname, b.age, b.profession
    order by amt desc limit 10; 
