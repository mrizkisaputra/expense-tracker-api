CREATE TABLE expenses
(
    id          character varying(255) NOT NULL,
    amount      numeric(38, 2)         NOT NULL,
    category    character varying(255) NOT NULL,
    created_at  bigint,
    description character varying(255) NOT NULL,
    updated_at  bigint,
    id_user     character varying(255)
);

CREATE TABLE s_password
(
    id_user  character varying(255) NOT NULL,
    password character varying(255)
);

CREATE TABLE s_roles
(
    id   character varying(255) NOT NULL,
    name character varying(255) NOT NULL
);
ALTER TABLE ONLY s_roles
    ADD CONSTRAINT uk5rwpfy9j06iisbgxvr7k3s96 UNIQUE (name);

CREATE TABLE s_users
(
    id                  character varying(255) NOT NULL,
    account_enabled     boolean,
    account_non_expired boolean,
    account_non_locked  boolean,
    created_at          bigint,
    email               character varying(100) NOT NULL,
    username            character varying(100) NOT NULL,
    id_role             character varying(255)
);
ALTER TABLE ONLY s_users
    ADD CONSTRAINT uk2lcv26kt28p27enwkw01c2s1g UNIQUE (email);
ALTER TABLE ONLY s_users
    ADD CONSTRAINT ukg6w3g55j7mm7jfji66cc4w16q UNIQUE (username);

--
-- ==============================================Constraint PK=========================================================
ALTER TABLE ONLY expenses
    ADD CONSTRAINT expenses_pkey PRIMARY KEY (id);
ALTER TABLE ONLY s_password
    ADD CONSTRAINT s_password_pkey PRIMARY KEY (id_user);
ALTER TABLE ONLY s_roles
    ADD CONSTRAINT s_roles_pkey PRIMARY KEY (id);
ALTER TABLE ONLY s_users
    ADD CONSTRAINT s_users_pkey PRIMARY KEY (id);

--
-- ===========================================Constraint FK============================================================
ALTER TABLE ONLY s_users
    ADD CONSTRAINT fk4k103cqcehdbobgrydgsa44gu FOREIGN KEY (id_role) REFERENCES s_roles(id);
ALTER TABLE ONLY s_password
    ADD CONSTRAINT fk7wur1l3rdr9u3msft1aluptkl FOREIGN KEY (id_user) REFERENCES s_users(id);
ALTER TABLE ONLY expenses
    ADD CONSTRAINT fkjkx5j0hurks850a07jgh7cy2a FOREIGN KEY (id_user) REFERENCES s_users(id);




