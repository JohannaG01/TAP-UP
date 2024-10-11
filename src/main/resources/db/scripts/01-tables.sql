CREATE TABLE IF NOT EXISTS users
(
    id              BIGSERIAL PRIMARY KEY,
    uuid            UUID           NOT NULL,
    email           VARCHAR        NOT NULL,
    name            VARCHAR        NOT NULL,
    last_name       VARCHAR        NOT NULL,
    is_admin        BOOLEAN        NOT NULL,
    balance         NUMERIC(20, 4) NOT NULL,
    hashed_password VARCHAR        NOT NULL,
    created_at      TIMESTAMP      NOT NULL,
    created_by      BIGINT         NOT NULL REFERENCES users (id),
    updated_at      TIMESTAMP      NOT NULL,
    updated_by      BIGINT         NOT NULL REFERENCES users (id),
    CONSTRAINT cck_email_pattern CHECK (email ~ '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]+(;[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]+)*$'
) ,
    CONSTRAINT cck_balance_not_negative CHECK (balance >= 0),
    CONSTRAINT cck_name_not_empty CHECK (TRIM(name) <> ''),
    CONSTRAINT cck_last_name_not_empty CHECK (TRIM(last_name) <> '')
);

CREATE TABLE IF NOT EXISTS notifications
(
    id         BIGSERIAL PRIMARY KEY,
    uuid       UUID      NOT NULL,
    user_id    BIGINT    NOT NULL REFERENCES users (id),
    type       VARCHAR   NOT NULL,
    message    VARCHAR   NOT NULL,
    sent_at    TIMESTAMP NOT NULL,
    read       BOOLEAN   NOT NULL,
    created_at TIMESTAMP NOT NULL,
    created_by BIGINT    NOT NULL REFERENCES users (id),
    updated_at TIMESTAMP NOT NULL,
    updated_by BIGINT    NOT NULL REFERENCES users (id),
    CONSTRAINT cck_type CHECK (type in ('INFO', 'WARNING', 'ERROR', 'SUCCESS', 'REMINDER')),
    CONSTRAINT cck_message_not_empty CHECK (TRIM(message) <> ''),
    CONSTRAINT cck_sent_at_not_future CHECK (sent_at <= (NOW() AT TIME ZONE 'UTC' AT TIME ZONE 'America/Buenos_Aires'))
);

CREATE TABLE IF NOT EXISTS horse_races
(
    id         BIGSERIAL PRIMARY KEY,
    uuid       UUID      NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time   TIMESTAMP,
    state      VARCHAR   NOT NULL,
    created_at TIMESTAMP NOT NULL,
    created_by BIGINT    NOT NULL REFERENCES users (id),
    updated_at TIMESTAMP NOT NULL,
    updated_by BIGINT    NOT NULL REFERENCES users (id),
    CONSTRAINT cck_start_time_not_future CHECK (start_time <= (NOW() AT TIME ZONE 'UTC' AT TIME ZONE 'America/Buenos_Aires')),
    CONSTRAINT cck_end_time_not_future CHECK (end_time <= (NOW() AT TIME ZONE 'UTC' AT TIME ZONE 'America/Buenos_Aires')),
    CONSTRAINT cck_state CHECK (state in ('SCHEDULED', 'FINISHED', 'CANCELED'))
);

CREATE TABLE IF NOT EXISTS horses
(
    id         BIGSERIAL PRIMARY KEY,
    uuid       UUID      NOT NULL,
    code       VARCHAR   NOT NULL,
    name       VARCHAR   NOT NULL,
    breed      VARCHAR   NOT NULL,
    birth_date DATE      NOT NULL,
    sex        VARCHAR   NOT NULL,
    color      VARCHAR   NOT NULL,
    state      VARCHAR   NOT NULL,
    created_at TIMESTAMP NOT NULL,
    created_by BIGINT    NOT NULL REFERENCES users (id),
    updated_at TIMESTAMP NOT NULL,
    updated_by BIGINT    NOT NULL REFERENCES users (id),
    CONSTRAINT cck_code_not_empty CHECK (TRIM(code) <> ''),
    CONSTRAINT cck_name_not_empty CHECK (TRIM(name) <> ''),
    CONSTRAINT cck_breed_not_empty CHECK (TRIM(breed) <> ''),
    CONSTRAINT cck_birth_date_not_future CHECK (birth_date <= CURRENT_DATE),
    CONSTRAINT cck_sex CHECK (sex in ('MALE', 'FEMALE')),
    CONSTRAINT cck_color_not_empty CHECK (TRIM(color) <> ''),
    CONSTRAINT cck_state CHECK (state in ('ACTIVE', 'INACTIVE', 'TEMPORALLY_INACTIVE'))
);



CREATE TABLE IF NOT EXISTS participants
(
    id            BIGSERIAL PRIMARY KEY,
    uuid          UUID      NOT NULL,
    horse_race_id BIGINT    NOT NULL REFERENCES horse_races (id),
    horse_id      BIGINT    NOT NULL REFERENCES horses(id),
    placement     INT,
    time          TIME,
    created_at    TIMESTAMP NOT NULL,
    created_by    BIGINT    NOT NULL REFERENCES users (id),
    updated_at    TIMESTAMP NOT NULL,
    updated_by    BIGINT    NOT NULL REFERENCES users (id),
    CONSTRAINT placement CHECK (placement > 0)
);

CREATE TABLE IF NOT EXISTS bets
(
    id             BIGSERIAL PRIMARY KEY,
    uuid           UUID           NOT NULL,
    user_id        BIGINT         NOT NULL REFERENCES users (id),
    participant_id BIGINT         NOT NULL REFERENCES participants (id),
    amount         NUMERIC(20, 4) NOT NULL,
    state          VARCHAR        NOT NULL,
    created_at     TIMESTAMP      NOT NULL,
    created_by     BIGINT         NOT NULL REFERENCES users (id),
    updated_at     TIMESTAMP      NOT NULL,
    updated_by     BIGINT         NOT NULL REFERENCES users (id),
    CONSTRAINT cck_amount_not_negative CHECK (amount >= 0),
    CONSTRAINT cck_state CHECK (state in ('PENDING', 'PAID', 'REFUND', 'LOST'))
);