SELECT student.name, student.age, faculty.name
FROM student
Join faculty ON student.faculty_id = faculty.id


SELECT student.name
FROM student
         JOIN avatar ON student.id = avatar.student_id;