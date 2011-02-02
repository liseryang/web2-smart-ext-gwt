CREATE TABLE SIMPLE_TABLE (ID INTEGER NOT NULL, TTEXT VARCHAR(256), TINT INTEGER, TDATE TIMESTAMP, PRIMARY KEY (ID));
CREATE SEQUENCE SEQ_SIMPLE_TABLE;

INSERT INTO SIMPLE_TABLE (ID, TTEXT, TINT, TDATE) values (1, 'Text', 555, NOW());
