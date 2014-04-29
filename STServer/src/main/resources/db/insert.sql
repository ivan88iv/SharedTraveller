use sharedtravellerdb;

INSERT INTO COUNTRY(ID, NAME) VALUES (1, 'BULGARIA');

INSERT INTO CITY(ID, NAME, COUNTRY_ID) VALUES (1, 'Blagoevgrad', 1);
INSERT INTO CITY(ID, NAME, COUNTRY_ID) VALUES (3, 'Pleven', 1);
INSERT INTO CITY(ID, NAME, COUNTRY_ID) VALUES (4, 'Sofia', 1);
INSERT INTO CITY(ID, NAME, COUNTRY_ID) VALUES (5, 'Stara Zagora', 1);
INSERT INTO CITY(ID, NAME, COUNTRY_ID) VALUES (6, 'Ruse', 1);
INSERT INTO CITY(ID, NAME, COUNTRY_ID) VALUES (7, 'Bansko', 1);
INSERT INTO CITY(ID, NAME, COUNTRY_ID) VALUES (8, 'Burgas', 1);
INSERT INTO CITY(ID, NAME, COUNTRY_ID) VALUES (9, 'Varna', 1);
INSERT INTO CITY(ID, NAME, COUNTRY_ID) VALUES (10, 'Plovdiv', 1);

INSERT INTO GENERIC_NOTIFICATION(ID, TYPE, TEMPLATE)
VALUES(1, 'NEW_REQUEST', '{0} wants to travel with you from {1} to {2} on {3}.');
INSERT INTO GENERIC_NOTIFICATION(ID, TYPE, TEMPLATE)
VALUES(2, 'REQUEST_REJECTION', '{0} rejected your request for the travel from {1} to {2} on {3}.');
INSERT INTO GENERIC_NOTIFICATION(ID, TYPE, TEMPLATE)
VALUES(3, 'REQUEST_ACCEPTANCE', '{0} accepted your request for the travel from {1} to {2} on {3}.');
INSERT INTO GENERIC_NOTIFICATION(ID, TYPE, TEMPLATE)
VALUES(4, 'REQUEST_DECLINATION', '{0} declined his/her request for the travel from {1} to {2} on {3}.');
INSERT INTO GENERIC_NOTIFICATION(ID, TYPE, TEMPLATE)
VALUES(5, 'TRIP_CANCELLATION', '{0} cancelled the travel from {1} to {2} on {3}.');

-- ============================== DUMMY DATA ========================================
INSERT INTO VEHICLE(ID, TRAVELLER_ID, DISPLAY_NAME, MAKE, MODEL, YEAR_OF_PRODUCTION, REGISTRATION_NUMBER, COLOR, SEATS, DESCRIPTION, CCU, AIR_BAG)
VALUES(1, 1, "Audi", "Audi", "A3", '2003-00-00', "E 2030 AS", "green", 5, "very big", false, false),
(2, 1, "OPEL", "OPEL", "Kadet", '1993-00-00', "E 2230 AS", "yellow", 5, "very big", false, false),
(3, 1, "VW", "VW", "Golf", '1997-00-00', "E 2330 AS", "red", 5, "very big", false, false);

INSERT INTO TRAVELLER(ID, USERNAME, PASSWORD, EMAIL, PHONE_NUMBER, SMOKER, FIRST_NAME, LAST_NAME)
VALUES(1, 'temp', 'temp', 'temp@temp.com', '0888888888', 0, 'John', 'Johnson');
