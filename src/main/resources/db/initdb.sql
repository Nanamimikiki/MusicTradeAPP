-- Enable the pgcrypto extension for UUID generation
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Users Table
CREATE TABLE IF NOT EXISTS users (
                                     id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                     username VARCHAR(100) UNIQUE NOT NULL,
                                     password VARCHAR(255) NOT NULL,
                                     email VARCHAR(100) UNIQUE NOT NULL,
                                     created TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                     updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                     status VARCHAR(25) DEFAULT 'ACTIVE' NOT NULL
);

-- Roles Table
CREATE TABLE IF NOT EXISTS roles (
                                     id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                     name VARCHAR(50) UNIQUE NOT NULL
);

-- Audio Table
CREATE TABLE IF NOT EXISTS audio (
                                     id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                     title VARCHAR(100) NOT NULL,
                                     link VARCHAR(512) NOT NULL,
                                     thumbnail_link VARCHAR(512) NOT NULL,
                                     type VARCHAR(25) DEFAULT 'AUDIO' NOT NULL,
                                     artist VARCHAR(100) NOT NULL,
                                     genre VARCHAR(50) NOT NULL,
                                     description VARCHAR(512),
                                     created TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                     updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                     is_public BOOLEAN NOT NULL,
                                     tags VARCHAR(512) NOT NULL,
                                     owner UUID REFERENCES users(id)
);

-- Video Table
CREATE TABLE IF NOT EXISTS video (
                                     id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                     title VARCHAR(100) NOT NULL,
                                     type VARCHAR(25) DEFAULT 'VIDEO' NOT NULL,
                                     description VARCHAR(512),
                                     created TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                     updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                     is_public BOOLEAN NOT NULL,
                                     tags VARCHAR(512) NOT NULL,
                                     owner UUID REFERENCES users(id)
);

-- User Audio Items
CREATE TABLE IF NOT EXISTS user_audio_items (
                                                user_id UUID REFERENCES users(id) ON DELETE CASCADE,
                                                audio_item_id UUID REFERENCES audio(id) ON DELETE CASCADE,
                                                PRIMARY KEY (user_id, audio_item_id)
);

-- User Video Items
CREATE TABLE IF NOT EXISTS user_video_items (
                                                user_id UUID REFERENCES users(id) ON DELETE CASCADE,
                                                video_item_id UUID REFERENCES video(id) ON DELETE CASCADE,
                                                PRIMARY KEY (user_id, video_item_id)
);

-- User Roles
CREATE TABLE IF NOT EXISTS user_roles (
                                          user_id UUID REFERENCES users(id) ON DELETE CASCADE,
                                          role_id UUID REFERENCES roles(id) ON DELETE CASCADE,
                                          PRIMARY KEY (user_id, role_id)
);

-- Insert Roles if they don't exist
DO $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_USER') THEN
            INSERT INTO roles (name) VALUES ('ROLE_USER');
        END IF;
        IF NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_MODER') THEN
            INSERT INTO roles (name) VALUES ('ROLE_MODER');
        END IF;
        IF NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_ADMIN') THEN
            INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
        END IF;
    END $$;
