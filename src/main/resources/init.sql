--
-- T a b l e s
--
DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    name     TEXT   UNIQUE NOT NULL,
    password TEXT   NOT NULL,
    role     TEXT   NOT NULL,
    email     TEXT   NOT NULL,
	CONSTRAINT name_not_empty CHECK (name <> ''),
	CONSTRAINT email_not_empty CHECK (email <> ''),
	CONSTRAINT password_not_empty CHECK (password <> '')
);

DROP TABLE IF EXISTS schedules CASCADE;
CREATE TABLE schedules
(
    id      SERIAL PRIMARY KEY,
    name    TEXT NOT NULL,
    user_id INTEGER,
    is_public BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

DROP TABLE IF EXISTS days CASCADE;
CREATE TABLE days
(
    id          SERIAL PRIMARY KEY,
    name        TEXT   NOT NULL,
    schedule_id INTEGER,
    FOREIGN KEY (schedule_id) REFERENCES schedules(id)
);

DROP TABLE IF EXISTS tasks CASCADE;
CREATE TABLE tasks
(
    id          SERIAL PRIMARY KEY,
    name        TEXT   NOT NULL,
    description TEXT,
    user_id     INTEGER,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

DROP TABLE IF EXISTS slots CASCADE;
CREATE TABLE slots
(
    id      SERIAL  PRIMARY KEY,
    time    INTEGER NOT NULL,
    task_id INTEGER,
    day_id  INTEGER,
    is_checked BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (task_id) REFERENCES tasks(id),
    FOREIGN KEY (day_id) REFERENCES days(id)
);

DROP TABLE IF EXISTS results CASCADE;
Create Table results
(
    user_id integer,
    task_id integer,
    is_done boolean,
    Foreign Key(user_id) References users(id),
    Foreign Key(task_id) References tasks(id)
);

DROP TABLE IF EXISTS inventories CASCADE;
CREATE TABLE inventories
(
    id SERIAL PRIMARY KEY,
    user_id INTEGER,
    FOREIGN KEY(user_id) REFERENCES users(id)
);



--
-- F u n c t i o n s
--
CREATE OR REPLACE FUNCTION createInventory() RETURNS TRIGGER AS '
BEGIN
    INSERT INTO inventories (user_id) VALUES (NEW.id);
    RETURN null;
END;
' LANGUAGE plpgsql;



--
-- T r i g g e r s
--
CREATE TRIGGER onRegister
    AFTER INSERT ON users
        FOR EACH ROW
            EXECUTE PROCEDURE createInventory();



--
-- D u m m y   d a t a
--
INSERT INTO users (name, password, role, email) VALUES
	('a', 'a', 'admin', 'admin@admin'),
    ('u', 'u', 'user', 'user@user'),
	('user1', 'user1', 'asd', 'user1@user1'),
	('user2', 'user2', 'sad', 'user1@user2'),
	('user3', 'user3', 'lul', 'user1@user3');

INSERT INTO tasks (name, description, user_id) VALUES
    ('kisfrocs', 'alap dolog', 1),
    ('nagyfrocs', 'csak ugy pls ne szolj be', 1),
    ('March in Place','Bring alternate knees up as high as you are comfortable in order to march', 3),
    ('Butt Kicker Rows', 'Bend at the knee to pull one foot up to your glutes as if you were trying to kick yourself in the butt, while pulling your arms in towards your chest', 3),
    ('Torso Twist + Side Reach + Arms & Knee Up', 'Twist at the torso while opening up your arms for a chest stretch. Come back to your starting position and lift arms overhead whil raising one knee', 3),
    ('Arm Swing Steps', 'Step side to side, arms out & back at small increments, moving arms up until overhead & back down (1 arm movement per step)', 3),
    ('Bow & Arrow Twists', ' Extend arms straight out to sides. Twist at the torso to cross one arm over to the other, then pull back like you would if you were using a bow & arrow. Reach back to that hand & return to the center to repeat on opposite side', 3);

INSERT INTO schedules (name, user_id, is_public) VALUES
    ('alap', 1, false),
    ('hard', 1, false),
    ('Low Impact Beginner Cardio Workout', 3, true);

INSERT INTO days (name, schedule_id) VALUES
    ('hetfu', '1'),
    ('ketto', '1'),
    ('First Day', 3),
    ('Second Day', 3);

INSERT INTO slots (time, day_id, task_id, is_checked ) VALUES
    (6, 1, 1, false),
    (7, 1, 2, false),
    (9, 3, 3, false),
    (10, 3, 4, false),
    (9, 4, 5, false),
    (10, 4, 6, false),
    (11, 4, 7, false);


