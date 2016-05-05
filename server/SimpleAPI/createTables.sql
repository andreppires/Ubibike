create table user
   (username 	varchar(255)	not null unique,
    password 	varchar(255)	not null,
    pontos int not null DEFAULT 0,
    primary key(username));

create table store(
      storeid varchar(255)	not null unique,
      latitude 	FLOAT(7,4)	not null,
      longitude 	FLOAT(7,4)	not null,
      primary key (storeid)
);

create table bicicleta(
      bikeid varchar(255) not null unique,
      status boolean not null ,
      localizacao varchar(255)	not null,
      primary key (bikeid),
      foreign key (localizacao) references store (storeid)
);

create table rotas
   (username 	varchar(255)	not null,
   rotaid int not null auto_increment,
   bikeid varchar(255) not null,
   primary key(rotaid),
   foreign key(username) references user(username),
   foreign key(bikeid) references bicicleta(bikeid)
 );

create table coordenadas(
  rota int not null,
  latitude 	FLOAT(7,4)	not null,
  longitude 	FLOAT(7,4)	not null,
  foreign key(rota) references rotas(rotaid)
);


create table amigos(
  username1 	varchar(255)	not null,
  username2 	varchar(255)	not null,
  foreign key(username1) references user(username),
  foreign key(username2) references user(username)
);


create table mensagem(
  username1 	varchar(255)	not null,
  username2 	varchar(255)	not null,
  conteudo 	varchar(1024)	not null,
  data TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  primary key (username1, username2, data),
  foreign key(username1) references user(username),
  foreign key(username2) references user(username)
);

-- Inserir elementos nas tabelas


INSERT INTO user (username, password) VALUES
('admin', 'adminadmin'),
('develop', 'test');

INSERT INTO store(storeid, latitude, longitude) VALUES
('Store1', 38.75322986, -9.20676827),
('Store2', 38.75077, -9.19113),
('Store3', 38.7601071, -9.18283225);

INSERT INTO bicicleta (bikeid, status, localizacao) VALUES
('0.0.0.0:1','FALSE','Store1'),
('0.0.0.0:2','FALSE','Store1'),
('0.0.0.0:3','FALSE','Store2'),
('0.0.0.0:4','FALSE','Store2');

INSERT INTO amigos (username1, username2) VALUES
('admin','develop');
