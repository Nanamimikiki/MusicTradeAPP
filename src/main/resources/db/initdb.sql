CREATE TABLE IF NOT EXISTS users
(
    id UUID PRIMARY KEY NOT NULL,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    status VARCHAR(25) DEFAULT 'ACTIVE' NOT NULL
);

CREATE TABLE IF NOT EXISTS roles
(
    id UUID PRIMARY KEY NOT NULL,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS audio
(
    id UUID PRIMARY KEY NOT NULL,
    title VARCHAR(100)          NOT NULL,
    link VARCHAR(512) NOT NULL,
    thumbnail_link VARCHAR(512) NOT NULL,
    type VARCHAR(25) DEFAULT 'AUDIO' NOT NULL,
    artist VARCHAR(100)          NOT NULL,
    genre VARCHAR(50)          NOT NULL,
    description VARCHAR(512),
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    is_public BOOLEAN NOT NULL,
    tags VARCHAR(512) NOT NULL,
    owner VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS video
(
    id  UUID PRIMARY KEY NOT NULL,
    title VARCHAR(100)          NOT NULL,
    type VARCHAR(25) DEFAULT 'VIDEO' NOT NULL,
    description VARCHAR(512),
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    is_public BOOLEAN NOT NULL,
    tags VARCHAR(512) NOT NULL,
    owner VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_audio_items (
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    audio_item_id UUID REFERENCES audio(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, audio_item_id)
);

CREATE TABLE IF NOT EXISTS user_video_items(
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    video_item_id UUID REFERENCES video(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, video_item_id)
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    role_id UUID REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

INSERT INTO roles VALUES (gen_random_uuid(), 'ROLE_USER');
INSERT INTO roles VALUES (gen_random_uuid(), 'ROLE_MODER');
INSERT INTO roles VALUES (gen_random_uuid(), 'ROLE_ADMIN');
