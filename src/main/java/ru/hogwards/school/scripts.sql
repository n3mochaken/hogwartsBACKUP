select * from student where age>20 and age<200;
select name from student;
select * from student where name like'%s%';
select * from student where age <200;
select * from student order by age ASC;

select faculty.color, student."name" from faculty, student
where student.faculty_id = faculty.id
and faculty.id = 3;
