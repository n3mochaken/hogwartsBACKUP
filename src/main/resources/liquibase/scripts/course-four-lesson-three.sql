-- liquibase formatted sql
-- changeset author:1

CREATE INDEX student_names ON student (name);
CREATE INDEX faculty_color_and_name ON faculty (name,color);