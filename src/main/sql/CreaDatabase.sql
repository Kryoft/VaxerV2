create table CENTRI_VACCINALI(
	Codice SERIAL,
    Nome varchar(80) NOT NULL,
	Comune varchar(100),
	Sigla char(2),
	Tipologia varchar(15), 
	Qualificatore varchar(10), 
	Nome_via varchar(50),
     	Num_civico smallint,
    	Cap char(5),
	primary key (Codice)
);

create table VACCINATI(
	Cod_Fiscale char(16),
	Nome varchar(30),
	Cognome varchar(30),
	Data DATE,
	Identificativo SERIAL PRIMARY KEY,
	Vaccino varchar(15),
	Cod_Centro int,
	foreign key (Cod_Centro) references CENTRI_VACCINALI(Codice)
		on delete cascade
		on update cascade
);


create table CITTADINI_REGISTRATI(
	Email varchar(30),
	Username varchar(20) UNIQUE,
	Password varchar(30),
	Cod_Fiscale char(16) primary key
);



create table LOG_EVENTI(

	Cod_Centro Integer,
	Cod_Fiscale varchar(30),
	Nome_Evento varchar(80),
	Indice smallint CHECK(indice between 0 AND 5),
	Note varchar(256),
	Cod Evento SERIAL primary key

	foreign key (Cod_Fiscale) references CITTADINI_REGISTRATI(Cod_Fiscale)
		on update cascade
		on delete set null,

	foreign key(Cod_Centro) references CENTRI_VACCINALI(codice)
		on update cascade
		on delete no action,
	
	Constraint Log UNIQUE(Cod_Fiscale,Nome_Evento)
);


INSERT into CENTRI_VACCINALI(nome,comune,tipologia,sigla,qualificatore,nome_via,num_civico,cap)
VALUES ('Mangioni','Lecco','OSPEDALIERO','LC','PIAZZA','Vecchia','12','22022');
INSERT into CENTRI_VACCINALI(nome,comune,tipologia,sigla,qualificatore,nome_via,num_civico,cap)
VALUES ('Valduce','Como','HUB','CO','VIA','Napoli','13','22012');
INSERT into CENTRI_VACCINALI(nome,comune,tipologia,sigla,qualificatore,nome_via,num_civico,cap)
VALUES ('H hospital','Erba','AZIENDALE','ER','VIA','Alserio','12','22036');
INSERT into CENTRI_VACCINALI(nome,comune,tipologia,sigla,qualificatore,nome_via,num_civico,cap)
VALUES ('Manzoni','Lecco','OSPEDALIERO','LC','VIA','Cesarea','10','22022');
INSERT into CENTRI_VACCINALI(nome,comune,tipologia,sigla,qualificatore,nome_via,num_civico,cap)
VALUES ('Pizzeria Pinello','Napoli','AZIENDALE','NA','VIA','spaccaNapoli','10','23422');
