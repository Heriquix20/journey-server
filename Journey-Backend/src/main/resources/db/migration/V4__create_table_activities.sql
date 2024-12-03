CREATE TABLE IF NOT EXISTS activities (
    id INTEGER PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    occurs_at TIMESTAMP NOT NULL,
    trip_id INTEGER,
    FOREIGN KEY (trip_id) REFERENCES trips(id) ON DELETE CASCADE
);
