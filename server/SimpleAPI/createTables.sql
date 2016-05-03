create table user
   (username 	varchar(255)	not null unique,
    password 	varchar(255)	not null,
    pontos int not null DEFAULT 0,
    primary key(username));

create table rotas
   (username 	varchar(255)	not null unique,
    lat_inicial 	FLOAT(7,4)	not null,
    lon_inicial 	FLOAT(7,4)	not null,
    lat_final 	FLOAT(7,4)	not null,
    lon_final 	FLOAT(7,4)	not null,
    primary key(username, lon_final, lon_inicial, lat_final, lat_inicial),
    foreign key (username) references user(username));

create table amigos(
  username1 	varchar(255)	not null,
  username2 	varchar(255)	not null,
  foreign key(username1) references user(username),
  foreign key(username2) references user(username)
);

create table store(
  storeid int not null auto_increment,
  latitude 	FLOAT(7,4)	not null,
  longitude 	FLOAT(7,4)	not null,
  primary key (storeid)
);

create table bicicleta(
  bikeid int not null auto_increment,
  status boolean not null ,
  localizacao int not null,
  primary key (bikeid),
  foreign key (localizacao) references store (storeid)
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

INSERT INTO store(latitude, longitude) VALUES
(38.738785, -9.300339);

INSERT INTO bicicleta (status, localizacao) VALUES
('FALSE',1);

INSERT INTO amigos (username1, username2) VALUES
('admin','develop');
