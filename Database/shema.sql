-- =========================================
-- TABLA ROLE
-- =========================================
CREATE TABLE role (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now()
);

-- =========================================
-- TABLA USER
-- =========================================
CREATE TABLE "user" (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    document VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    id_role INTEGER NOT NULL,
    image VARCHAR(255),
    estado BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now(),

    CONSTRAINT fk_role_id_user
        FOREIGN KEY (id_role)
        REFERENCES role(id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

-- =========================================
-- TABLA STATE_PROJECT_TASK
-- =========================================
CREATE TABLE state_project_task (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now()
);

-- =========================================
-- TABLA PRIORITY
-- =========================================
CREATE TABLE priority (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now()
);

-- =========================================
-- TABLA PROJECT
-- =========================================
CREATE TABLE project (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    state BOOLEAN DEFAULT TRUE,
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    deadline TIMESTAMP,
    id_state_project_task INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now(),

    CONSTRAINT fk_state_project_task_id_project
        FOREIGN KEY (id_state_project_task)
        REFERENCES state_project_task(id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

-- =========================================
-- TABLA TASK
-- =========================================
CREATE TABLE task (
    id SERIAL PRIMARY KEY,
    id_project INTEGER NOT NULL,
    id_priority INTEGER NOT NULL,
    id_state_project_task INTEGER NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    deadline TIMESTAMP,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now(),

    CONSTRAINT fk_project_id_task
        FOREIGN KEY (id_project)
        REFERENCES project(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_priority_id_task
        FOREIGN KEY (id_priority)
        REFERENCES priority(id)
        ON DELETE NO ACTION,

    CONSTRAINT fk_state_project_task_id_task
        FOREIGN KEY (id_state_project_task)
        REFERENCES state_project_task(id)
        ON DELETE NO ACTION
);

-- =========================================
-- TABLA USER_PROJECT (N:M)
-- =========================================
CREATE TABLE user_project (
    id SERIAL PRIMARY KEY,
    id_project INTEGER NOT NULL,
    id_user INTEGER NOT NULL,

    CONSTRAINT fk_project_id_user_project
        FOREIGN KEY (id_project)
        REFERENCES project(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_user_id_user_project
        FOREIGN KEY (id_user)
        REFERENCES "user"(id)
        ON DELETE CASCADE,

    CONSTRAINT unique_user_project UNIQUE (id_project, id_user)
);

-- =========================================
-- TABLA TASK_USER (N:M)
-- =========================================
CREATE TABLE task_user (
    id SERIAL PRIMARY KEY,
    id_user INTEGER NOT NULL,
    id_task INTEGER NOT NULL,

    CONSTRAINT fk_user_id_task_user
        FOREIGN KEY (id_user)
        REFERENCES "user"(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_task_id_task_user
        FOREIGN KEY (id_task)
        REFERENCES task(id)
        ON DELETE CASCADE,

    CONSTRAINT unique_task_user UNIQUE (id_task, id_user)
);

-- =========================================
-- TABLA COMMENT
-- =========================================
CREATE TABLE comment (
    id SERIAL PRIMARY KEY,
    id_task INTEGER NOT NULL,
    id_user INTEGER NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now(),

    CONSTRAINT fk_task_id_comment
        FOREIGN KEY (id_task)
        REFERENCES task(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_user_id_comment
        FOREIGN KEY (id_user)
        REFERENCES "user"(id)
        ON DELETE CASCADE
);

-- =========================================
-- ÍNDICES IMPORTANTES PARA RENDIMIENTO
-- =========================================
CREATE INDEX idx_task_project ON task(id_project);
CREATE INDEX idx_task_state ON task(id_state_project_task);
CREATE INDEX idx_user_role ON "user"(id_role);
CREATE INDEX idx_comment_task ON comment(id_task);