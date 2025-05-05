insert into tab_user (id, name, email, password, active, created, modified) values ('b3f2ebd3-4434-47b0-95ed-1a3f48825069', 'jmfragueiro', 'jmfragueiro@hotmail.com', '$2a$10$O5s2/MhWhF.pLRdnHzSGMO5g.6jZCngNGoh0RsPIY1.CNPdQPuzvG', true, current_timestamp, current_timestamp);
insert into tab_phone (id, user_id, number, citycode, countrycode, created, modified) values (random_uuid(), 'b3f2ebd3-4434-47b0-95ed-1a3f48825069', 4363177, 376, 54, current_timestamp, current_timestamp);
commit;
