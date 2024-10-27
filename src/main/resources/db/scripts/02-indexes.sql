--users
CREATE UNIQUE INDEX IF NOT EXISTS user_uuid_idx ON users(uuid);
CREATE UNIQUE INDEX IF NOT EXISTS user_email_idx ON users(email);

--notifications
CREATE UNIQUE INDEX IF NOT EXISTS notification_uuid_idx ON notifications(uuid);

--horse_races
CREATE UNIQUE INDEX IF NOT EXISTS horse_races_uuid_idx ON horse_races(uuid);
CREATE INDEX IF NOT EXISTS horse_races_start_time_idx ON horse_races(start_time);
CREATE INDEX IF NOT EXISTS horse_races_state_idx ON horse_races(state);

--horses
CREATE UNIQUE INDEX IF NOT EXISTS horse_uuid_idx ON horses(uuid);
CREATE UNIQUE INDEX IF NOT EXISTS horse_code_idx ON horses(code);
CREATE INDEX IF NOT EXISTS horse_state_idx ON horses(state);

--participants
CREATE UNIQUE INDEX IF NOT EXISTS participant_uuid_idx ON participants(uuid);

--bets
CREATE UNIQUE INDEX IF NOT EXISTS bet_uuid_idx ON bets(uuid);
CREATE INDEX IF NOT EXISTS bet_state_idx ON bets(state);