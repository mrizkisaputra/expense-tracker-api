CREATE TABLE expenses
(
    id varchar(100) not null,
    id_user varchar(100) not null,
    description varchar(100) not null,
    category varchar(100) not null,
    amount DECIMAL(10, 2),
    created_at bigint,
    updated_at bigint,
    deleted_at TIMESTAMP default null
);

ALTER TABLE expenses
ADD CONSTRAINT expenses_pk_id PRIMARY KEY (id);

ALTER TABLE expenses
ADD CONSTRAINT expenses_fk_id_user FOREIGN KEY (id_user) REFERENCES users(id);