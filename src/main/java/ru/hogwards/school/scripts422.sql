CREATE TABLE Cars (
                     id INTEGER PRIMARY KEY,
                     brand VARCHAR(100),
                     model VARCHAR(100),
                     price DECIMAL(10, 2)
);
CREATE TABLE Person (
                        id INTEGER PRIMARY KEY,
                        name VARCHAR(100),
                        age INTEGER,
                        has_license BOOLEAN,
                        car_id INTEGER REFERENCES cars(id)
);

-- CREATE TABLE Person_Car (
--                             person_id INTEGER REFERENCES Person(id),
--                             car_id INTEGER REFERENCES Cars(id),
--                             PRIMARY KEY (person_id, car_id)
-- );