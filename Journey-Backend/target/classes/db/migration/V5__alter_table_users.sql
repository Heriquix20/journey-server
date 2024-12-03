ALTER TABLE users
ADD COLUMN trip_id INTEGER;

ALTER TABLE users
ADD CONSTRAINT fk_trip_id FOREIGN KEY (trip_id) REFERENCES trips(id);