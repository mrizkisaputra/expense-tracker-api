insert into s_roles(id, name)
values ('2c945a11-e3a7-4078-94c9-c8cbfd518672', 'USER'),
       ('03719fb3-e5b6-4747-9a5f-c6add790a6c1', 'ADMIN');

insert into s_users(id, username, id_role)
values ('e0c5a933-9bc6-4edf-88c0-bec1a2f03967', 'user1@gmail.com', '2c945a11-e3a7-4078-94c9-c8cbfd518672'),
       ('eb288dbc-4ac3-4130-bef4-578f42fdede4', 'user2@gmail.com', '2c945a11-e3a7-4078-94c9-c8cbfd518672');

insert into s_password(id_user, password)
values ('e0c5a933-9bc6-4edf-88c0-bec1a2f03967', '$2a$12$T6jv1AbmQtWaGPxuU1CS5uE8wTteIHfrOmD91kY/VmaxhBDA/rUy.'),
       ('eb288dbc-4ac3-4130-bef4-578f42fdede4', '$2a$12$F2PPgTjasYi5VGcitz3QOOX0Qhle6VGiBY5hwDaAJD3opktCnknSa');