DROP TABLE IF EXISTS merchandise CASCADE;
DROP TABLE IF EXISTS workout_classes CASCADE;
DROP TABLE IF EXISTS memberships CASCADE;
DROP TABLE IF EXISTS membership_plans CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users (
	id SERIAL PRIMARY KEY,
	username VARCHAR(50) NOT NULL UNIQUE,
	password VARCHAR(100) NOT NULL,
	email VARCHAR(255) NOT NULL,
	phone_number VARCHAR(30) NOT NULL,
	address VARCHAR(255) NOT NULL,
	role VARCHAR(10) NOT NULL CHECK (role IN ('Admin', 'Trainer', 'Member'))
);

CREATE TABLE memberships (
	membership_id SERIAL PRIMARY KEY,
	user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
	membership_type VARCHAR(50) NOT NULL,
	price NUMERIC(10, 2) NOT NULL CHECK (price >= 0),
	purchased_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE membership_plans (
	plan_id SERIAL PRIMARY KEY,
	membership_type VARCHAR(50) NOT NULL UNIQUE,
	price NUMERIC(10, 2) NOT NULL CHECK (price >= 0)
);

CREATE TABLE workout_classes (
	class_id SERIAL PRIMARY KEY,
	trainer_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
	description VARCHAR(255) NOT NULL,
	schedule VARCHAR(100) NOT NULL
);

CREATE TABLE merchandise (
	merchandise_id SERIAL PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	description VARCHAR(255) NOT NULL,
	item_type VARCHAR(20) NOT NULL CHECK (item_type IN ('Food', 'Drink', 'Gear')),
	price NUMERIC(10, 2) NOT NULL CHECK (price >= 0),
	stock INTEGER NOT NULL DEFAULT 0 CHECK (stock >= 0)
);

CREATE INDEX memberships_user_id_idx ON memberships(user_id);
CREATE INDEX workout_classes_trainer_id_idx ON workout_classes(trainer_id);

INSERT INTO membership_plans (membership_type, price) VALUES
	('Monthly', 49.99),
	('Annual', 499.99);

-- The sample users all use "password" as their initial password.
INSERT INTO users (username, password, email, phone_number, address, role) VALUES
	('admin', '$2a$10$B1jfp0pq4ehg.JwqcGTfPOTQOK6pbagrXDhNH.uvizTmmbFz.fCyG', 'admin@gym.local', '555-0100', '1 Main Street', 'Admin'),
	('trainer', '$2a$10$B1jfp0pq4ehg.JwqcGTfPOTQOK6pbagrXDhNH.uvizTmmbFz.fCyG', 'trainer@gym.local', '555-0101', '2 Main Street', 'Trainer'),
	('member', '$2a$10$B1jfp0pq4ehg.JwqcGTfPOTQOK6pbagrXDhNH.uvizTmmbFz.fCyG', 'member@gym.local', '555-0102', '3 Main Street', 'Member');

INSERT INTO memberships (user_id, membership_type, price, purchased_at) VALUES
	((SELECT id FROM users WHERE username = 'member'), 'Monthly', 49.99, CURRENT_TIMESTAMP),
	((SELECT id FROM users WHERE username = 'trainer'), 'Annual', 499.99, CURRENT_TIMESTAMP);

INSERT INTO workout_classes (trainer_id, description, schedule) VALUES
	((SELECT id FROM users WHERE username = 'trainer'), 'Strength training fundamentals', 'Monday 18:00');

INSERT INTO merchandise (name, description, item_type, price, stock) VALUES
	('Protein Bar', 'Chocolate protein bar', 'Food', 3.99, 25),
	('Electrolyte Drink', 'Zero-sugar sports drink', 'Drink', 2.49, 30),
	('Gym Towel', 'Quick-dry branded towel', 'Gear', 14.99, 15);
