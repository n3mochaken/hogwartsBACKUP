CREATE TABLE Person (
                        person_id SERIAL PRIMARY KEY,
                        name VARCHAR(100),
                        age INTEGER,
                        has_license BOOLEAN
);

CREATE TABLE Car (
                     car_id SERIAL PRIMARY KEY,
                     brand VARCHAR(100),
                     model VARCHAR(100),
                     price DECIMAL(10, 2)
);

CREATE TABLE Person_Car (
                            person_id INTEGER REFERENCES Person(person_id),
                            car_id INTEGER REFERENCES Car(car_id),
                            PRIMARY KEY (person_id, car_id)
);