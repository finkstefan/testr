INSERT INTO "fakultet"("id", "naziv", "sediste")
VALUES (nextval('fakultet_seq'), 'FTN','NS');
INSERT INTO "fakultet"("id", "naziv", "sediste")
VALUES (nextval('fakultet_seq'), 'PMF','NS');
INSERT INTO "fakultet"("id", "naziv", "sediste")
VALUES (nextval('fakultet_seq'), 'Filozofski fakultet','NS');
INSERT INTO "fakultet"("id", "naziv", "sediste")
VALUES (nextval('fakultet_seq'), 'FON','BG');
INSERT INTO "fakultet"("id", "naziv", "sediste")
VALUES (nextval('fakultet_seq'), 'ETF','BG');

INSERT INTO "fakultet"("id", "naziv", "sediste")
VALUES (-100, 'TestNaz','TestSed');


INSERT INTO "status"("id", "naziv", "oznaka")
VALUES (nextval('status_seq'), 'Osnovne Akademske Studije','OAS');
INSERT INTO "status"("id", "naziv", "oznaka")
VALUES (nextval('status_seq'), 'Master','MAS');
INSERT INTO "status"("id", "naziv", "oznaka")
VALUES (nextval('status_seq'), 'Doktorske Studije','DAS');
INSERT INTO "status"("id", "naziv", "oznaka")
VALUES (nextval('status_seq'), 'Asistent-Master','AM');
INSERT INTO "status"("id", "naziv", "oznaka")
VALUES (nextval('status_seq'), 'Asistent-Doktorant','AD');

INSERT INTO "status"("id", "naziv", "oznaka")
VALUES (-100, 'TestNaz','TestOzn');

INSERT INTO "departman"("id", "naziv", "oznaka","fakultet")
VALUES (nextval('departman_seq'), 'Industrijsko inzenjerstvo i menadzment','DIIM',1);
INSERT INTO "departman"("id", "naziv", "oznaka","fakultet")
VALUES (nextval('departman_seq'), 'Arhitektura i urbanizam','DAU',1);
INSERT INTO "departman"("id", "naziv", "oznaka","fakultet")
VALUES (nextval('departman_seq'), 'Biologija i ekologija','DBE',2);
INSERT INTO "departman"("id", "naziv", "oznaka","fakultet")
VALUES (nextval('departman_seq'), 'Menadzment ljudskih resursa','DLJR',4);
INSERT INTO "departman"("id", "naziv", "oznaka","fakultet")
VALUES (nextval('departman_seq'), 'Komparativna knjizevnost','DKK',3);

INSERT INTO "departman"("id", "naziv", "oznaka","fakultet")
VALUES (-100, 'TestNaz','TestOzn',3);

INSERT INTO "student"("id", "ime", "prezime","broj_indeksa","status","departman")
VALUES (nextval('student_seq'), 'Ivan','Ivanovic','432424',1,1);
INSERT INTO "student"("id", "ime", "prezime","broj_indeksa","status","departman")
VALUES (nextval('student_seq'), 'Bojan','Boskovic','13122',5,1);
INSERT INTO "student"("id", "ime", "prezime","broj_indeksa","status","departman")
VALUES (nextval('student_seq'), 'Goran','Ostojic','411112',1,4);
INSERT INTO "student"("id", "ime", "prezime","broj_indeksa","status","departman")
VALUES (nextval('student_seq'), 'Anja','Jankovic','42299',3,2);
INSERT INTO "student"("id", "ime", "prezime","broj_indeksa","status","departman")
VALUES (nextval('student_seq'), 'Milana','Miljkovic','777668',4,3);

INSERT INTO "student"("id", "ime", "prezime","broj_indeksa","status","departman")
VALUES (-100, 'TestIme','TestPrez','777668',4,3);