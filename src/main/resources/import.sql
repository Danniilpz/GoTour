-- 
-- El contenido de este fichero se cargará al arrancar la aplicación, suponiendo que uses
-- 		application-default ó application-externaldb en modo 'create'
--

-- Usuario de ejemplo con username = b y contraseña = aa  
-- INSERT INTO user(id,enabled,username,password,roles,first_name,last_name) VALUES (
-- 	1, 1, 'a', 
-- 	'{bcrypt}$2a$10$xLFtBIXGtYvAbRqM95JhcOaG23fHRpDoZIJrsF2cCff9xEHTTdK1u',
-- 	'USER,ADMIN',
-- 	'Abundio', 'Ejémplez'
-- );

-- -- Otro usuario de ejemplo con username = b y contraseña = aa  
INSERT INTO USER(Id,Apellidos,Caducidad_Tarjeta, Email,Enabled,Nombre,Num_Secreto,Num_Tarjeta,Num_Telefono,Password,Pregunta_Seguridad,Respuesta_Seguridad,Puntuacion,Roles,Username) VALUES (
	2,'Sanchez Granado','22/03','email', 1,'Jesus',12,87,333, 
	'{bcrypt}$2a$10$xLFtBIXGtYvAbRqM95JhcOaG23fHRpDoZIJrsF2cCff9xEHTTdK1u','Pregunta','Respuesta',4,
	'USER',
	'SPACEMARINE');
INSERT INTO USER(Id,Apellidos,Caducidad_Tarjeta, Email,Enabled,Nombre,Num_Secreto,Num_Tarjeta,Num_Telefono,Password,Pregunta_Seguridad,Respuesta_Seguridad,Puntuacion,Roles,Username) VALUES (
	3,'Garcia Tomasulo','22/03','email',1,'Sonia',12,87,333, 
	'{bcrypt}$2a$10$xLFtBIXGtYvAbRqM95JhcOaG23fHRpDoZIJrsF2cCff9xEHTTdK1u','Pregunta','Respuesta',4,
	'USER',
	'SONIA');
INSERT INTO USER(Id,Apellidos,Caducidad_Tarjeta, Email,Enabled,Nombre,Num_Secreto,Num_Tarjeta,Num_Telefono,Password,Pregunta_Seguridad,Respuesta_Seguridad,Puntuacion,Roles,Username) VALUES (
	1,'Garcia Tomasulo','22/03','email',1,'Sonia',12,87,333, 
	'{bcrypt}$2a$10$xLFtBIXGtYvAbRqM95JhcOaG23fHRpDoZIJrsF2cCff9xEHTTdK1u','Pregunta','Respuesta',4,
	'USER',
	'vicky');
INSERT INTO USER(Id,Apellidos,Caducidad_Tarjeta, Email,Enabled,Nombre,Num_Secreto,Num_Tarjeta,Num_Telefono,Password,Pregunta_Seguridad,Respuesta_Seguridad,Puntuacion,Roles,Username) VALUES (
	4,'Lopez Marques','22/03','email',1,'Dani',12,87,333, 
	'{bcrypt}$2a$10$xLFtBIXGtYvAbRqM95JhcOaG23fHRpDoZIJrsF2cCff9xEHTTdK1u','Pregunta','Respuesta',4,
	'ADMIN',
	'dani');
INSERT INTO TOUR_OFERTADO VALUES (1, 'Alcala', 'Esta es la descripcion del tour numero 1', 1, 'tour1', '/img/mapa1.jpg', 2, 'adas', '/img/tour1.jpg', 8, 'Tour1', 2);
INSERT INTO TOUR_OFERTADO VALUES (2, 'Alcala', 'Esta es la descripcion del tour numero 1', 1, 'tour1', '/img/mapa1.jpg', 2, 'adas', '/img/tour1.jpg', 8, 'Tour1', 1);
INSERT INTO TOUR_OFERTADO VALUES (3, 'Alcala', 'Esta es la descripcion del tour numero 1', 1, 'tour1', '/img/mapa1.jpg', 2, 'adas', '/img/tour1.jpg', 8, 'Tour1', 4);
INSERT INTO TOUR_OFERTADO VALUES (5, 'Alcala', 'Esta es la descripcion del tour numero 1', 1, 'tour1', '/img/mapa1.jpg', 2, 'adas', '/img/tour1.jpg', 8, 'Tour1', 2);
INSERT INTO TOUR_OFERTADO_ETIQUETAS  VALUES(1, 'museo');
INSERT INTO TOUR_OFERTADO_ETIQUETAS  VALUES(1, 'monumento');
INSERT INTO TOUR_OFERTADO_ETIQUETAS  VALUES(1, 'familia');
INSERT INTO TOUR_OFERTADO_ETIQUETAS  VALUES(1, 'historia');
INSERT INTO TOUR_OFERTADO_ETIQUETAS  VALUES(2, 'familia');
INSERT INTO TOUR_OFERTADO_ETIQUETAS  VALUES(2, 'historia');
--INSERT INTO TOUR_OFERTADO VALUES (2,'Madrid','Una visita por el centro de Madrid',TRUE,'2021-12-12 20:00:00', '2021-12-12 20:00:00','Centro','/img/mapa1.jpg',50,'España','/img/tour1.jpg',25,'Descubriendo Madrid',2);
INSERT INTO TOUR VALUES(1,0,'2021-04-18 20:00:00', '2021-04-12 20:00:00',1,1, 2);
INSERT INTO TOUR VALUES(2,0,'2021-04-30 20:00:00', '2021-05-28 20:00:00',2,2, 1);
INSERT INTO TOUR VALUES(3,0,'2021-04-18 20:00:00', '2021-04-12 20:00:00',3,3, 4);
INSERT INTO CHAT VALUES(1);
INSERT INTO USER_TOURS_ASISTIDOS VALUES(3,1);
INSERT INTO USER_TOURS_ASISTIDOS VALUES(1,1);
INSERT INTO USER_IDIOMAS_HABLADOS VALUES (2, 'Ingles');
INSERT INTO USER_IDIOMAS_HABLADOS VALUES (2, 'Frances');
INSERT INTO USER_IDIOMAS_HABLADOS VALUES (2, 'Aleman');
INSERT INTO USER_IDIOMAS_HABLADOS VALUES (1, 'Ingles');
INSERT INTO USER_IDIOMAS_HABLADOS VALUES (1, 'Frances');
INSERT INTO USER_IDIOMAS_HABLADOS VALUES (1, 'Aleman');
INSERT INTO USER_IDIOMAS_HABLADOS VALUES (4, 'Ingles');
INSERT INTO USER_IDIOMAS_HABLADOS VALUES (4, 'Frances');
INSERT INTO USER_IDIOMAS_HABLADOS VALUES (4, 'Aleman');
INSERT INTO REVIEW VALUES(1,3,'Buen tour',3,2,1);
INSERT INTO REVIEW VALUES(2,3,'Buen tour',3,1,1);
INSERT INTO REVIEW VALUES(3,3,'Buen tour',2,4,1);

-- -- Unos pocos auto-mensajes de prueba
-- INSERT INTO MESSAGE VALUES(1,NULL,'2020-03-23 10:48:11.074000','probando 1',1,1);
-- INSERT INTO MESSAGE VALUES(2,NULL,'2020-03-23 10:48:15.149000','probando 2',1,1);
-- INSERT INTO MESSAGE VALUES(3,NULL,'2020-03-23 10:48:18.005000','probando 3',1,1);
-- INSERT INTO MESSAGE VALUES(4,NULL,'2020-03-23 10:48:20.971000','probando 4',1,1);
-- INSERT INTO MESSAGE VALUES(5,NULL,'2020-03-23 10:48:22.926000','probando 5',1,1);


