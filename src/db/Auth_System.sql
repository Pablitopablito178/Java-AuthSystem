drop database if exists auth_system;
create database if not exists auth_system;

use auth_system;

create table tbl_persona(
	idPersona varchar(50) not null,
    nombre varchar(50) not null,
    apellidos varchar(50) not null,
    telefono varchar(8),
    fecha_creacion datetime default current_timestamp,
    primary key PK_idPersona (idPersona)
);

create table tbl_usuario(
	id_usuario varchar(50) not null,
    email varchar(50) not null,
    contrasenia varchar(25) not null,
    idPersona varchar(50) not null,
    primary key PK_id_usuario (id_usuario),
    foreign key (idPersona) references tbl_persona(idPersona)
);

Delimiter $$
	create procedure sp_agregar_persona(in _nombre varchar(50), in _apellidos varchar(50), in _telefono varchar(8))
    Begin
		insert into tbl_persona (tbl_persona.id_persona, tbl_persona.nombre, tbl_persona.apellidos, tbl_persona.telenofo)
			values(uuid(), _nombre, _apellidos, _telefono);
	end$$
Delimiter ;

Delimiter $$
	create procedure sp_agregar_usuario(in _email varchar(50), in _contrasenia varchar(50), in _idPersona varchar(50))
    Begin
		insert into tbl_usuario(tbl_usuario.id_usuario, tbl_usuario.email, tbl_usuario.contrasenia, tbl_usuario.idPersona)
			values(uuid(), _email, _contrasenia, _idPersona);
    end$$
Delimiter ;

Delimiter $$
	create procedure sp_buscar_usuario(in _email varchar(50), in _contrasenia varchar(50))
    Begin
		select * from tbl_usuario U where U.email = _email and U.contrasenia = _contrasenia;
    End$$
Delimiter ;

Delimiter $$
	create procedure sp_buscar_persona(out _idPersona varchar(50))
    Begin
		select tbl_persona.idPersona
        into _idPersona
        from tbl_persona
        order by fecha_creacion desc limit 1;
    End$$
Delimiter ;

DELIMITER $$
	CREATE PROCEDURE sp_validar_usuario(
		IN _email VARCHAR(50),
		IN _contrasenia VARCHAR(50)
	)
	BEGIN
		IF NOT EXISTS (SELECT 1 FROM usuario WHERE email = _email) THEN
			SELECT 'NO_EMAIL' AS resultado;
		ELSEIF NOT EXISTS (SELECT 1 FROM usuario WHERE email = _email AND contrasenia = _contrasenia) THEN
			SELECT 'CONTRASENIA_INCORRECTA' AS resultado;
		ELSE
			SELECT 'OK' AS resultado, email FROM usuario WHERE email = _email;
		END IF;
	END$$
DELIMITER ;