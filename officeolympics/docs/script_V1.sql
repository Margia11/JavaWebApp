
create schema officeolympicsdb;


CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- elimino tutte le tabelle
-- DROP TABLE IF EXISTS companies CASCADE;

create table if not exists "api_log"
(
    id_api_log uuid not null default uuid_generate_v4(),
    span_id varchar(25) not null,
    trace_id varchar(25) not null,
    parent_id varchar(25),
    remote_address varchar(255),
    remote_host varchar(255),
    machine_name varchar(255),
    request_timestamp timestamp not null,
    request_header text,
    request_body text,
    request_uri varchar(2048),
    request_method varchar(25),
    response_status integer,
    response_timestamp timestamp,
    response_header text,
    response_body text,
    stack_trace text,
    CONSTRAINT pk_api_log PRIMARY KEY (id_api_log)
);

CREATE INDEX idx_span_id ON api_log(span_id);
CREATE INDEX idx_request_timestamp ON api_log(request_timestamp);



create table if not exists roles
(
    id_role          uuid         not null default uuid_generate_v4(),
    name             varchar(50)  not null unique,
    description      varchar(50)  not null,
    insert_timestamp      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_update_timestamp timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "operator"       varchar(100) NOT NULL DEFAULT 'system'::character varying,
    last_span_id varchar(25),
    CONSTRAINT pk_role PRIMARY KEY (id_role)
);


create table if not exists "users"
(
    id_user          uuid         not null default uuid_generate_v4(),
    id_role			 uuid 		  not null,				
    first_name       varchar(255) not null,
    last_name        varchar(255) not null,
    email            varchar(50)  not null unique,
    avatar                  text NULL DEFAULT NULL,
    insert_timestamp      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_update_timestamp timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "operator"       varchar(100) NOT NULL DEFAULT 'system'::character varying,
    last_span_id varchar(25),
    CONSTRAINT pk_user PRIMARY KEY (id_user),
    CONSTRAINT fk_role FOREIGN KEY (id_role) REFERENCES roles (id_role)
);


create table if not exists companies
(
    id_company            uuid          not null default uuid_generate_v4(),
    name                  varchar(100) not null,
    vat_number         	  varchar(1000),
    description           varchar(1000),
    logo                  text NULL DEFAULT NULL,
    insert_timestamp      timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_update_timestamp timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "operator"            varchar(100)  NOT NULL DEFAULT 'system'::character varying,
    last_span_id varchar(25),
    CONSTRAINT pk_company PRIMARY KEY (id_company)
);


create table if not exists users_companies
(
    id_user_company       uuid          not null default uuid_generate_v4(),
    id_user 			  UUID          not null,
    id_company 			  UUID          not null,
    insert_timestamp      timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_update_timestamp timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "operator"            varchar(100)  NOT NULL DEFAULT 'system'::character varying,
    last_span_id varchar(25),
    CONSTRAINT pk_user_company PRIMARY KEY (id_user_company),
    CONSTRAINT fk_user_company FOREIGN KEY (id_user) REFERENCES users (id_user),
    CONSTRAINT fk_company_user FOREIGN KEY (id_company) REFERENCES companies (id_company)
);

create table if not exists groups
(
    id_group              uuid          not null default uuid_generate_v4(),
    id_company            UUID          not null,
    type                  varchar(100) not null, -- enum --> default : EMPLOYEE
    name                  varchar(100) not null,
    description           varchar(1000),
    insert_timestamp      timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_update_timestamp timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "operator"            varchar(100)  NOT NULL DEFAULT 'system'::character varying,
    last_span_id varchar(25),
    CONSTRAINT pk_group PRIMARY KEY (id_group),
    CONSTRAINT fk_group_company FOREIGN KEY (id_company) REFERENCES companies (id_company)
);


create table if not exists users_groups
(
    id_user_group              uuid          not null default uuid_generate_v4(),
    id_user            UUID          not null,
    id_group           UUID          not null,
    insert_timestamp      timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_update_timestamp timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "operator"            varchar(100)  NOT NULL DEFAULT 'system'::character varying,
    last_span_id varchar(25),
    CONSTRAINT pk_user_group PRIMARY KEY (id_user_group),
    CONSTRAINT fk_user_group FOREIGN KEY (id_user) REFERENCES users (id_user),
    CONSTRAINT fk_group_user FOREIGN KEY (id_group) REFERENCES groups (id_group)
);


create table if not exists challenge
(
    id_challenge            uuid          not null default uuid_generate_v4(),
    id_user               UUID          not null, -- solo con ruolo MUST (board/dev)
    name                  varchar(100) not null,
    description           varchar(1000),
    insert_timestamp      timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_update_timestamp timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "operator"            varchar(100)  NOT NULL DEFAULT 'system'::character varying,
    last_span_id varchar(25),
    CONSTRAINT pk_challenge PRIMARY KEY (id_challenge),
    CONSTRAINT fk_challenge_user FOREIGN KEY (id_user) REFERENCES users (id_user)
);



create table if not exists challenge_groups
(
    id_challenge_group    uuid          not null default uuid_generate_v4(),
    id_challenge          UUID          not null, -- solo con ruolo MUST (board/dev)
    id_group              UUID not null,
    start_date      	  timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    end_date      	  	  timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    insert_timestamp      timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_update_timestamp timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "operator"            varchar(100)  NOT NULL DEFAULT 'system'::character varying,
    last_span_id varchar(25),
    CONSTRAINT pk_challenge_group PRIMARY KEY (id_challenge_group),
    CONSTRAINT fk_challenge_group FOREIGN KEY (id_challenge) REFERENCES challenge (id_challenge),
    CONSTRAINT fk_group_challenge FOREIGN KEY (id_group) REFERENCES groups (id_group)
);


create table if not exists challenge_stats -- registra punteggio per ogni giocatore
(
    id_challenge_stat     uuid          not null default uuid_generate_v4(),
    id_challenge_group    UUID          not null, -- solo con ruolo MUST (board/dev)
    id_user_group		  UUID          not null,
    status	              varchar(100) not null, -- enum : STATS_PARZIALI , STATS_DEFINITIVE (OPEN/CLOSE)
    points	              int not null DEFAULT 0, 
    insert_timestamp      timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_update_timestamp timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "operator"            varchar(100)  NOT NULL DEFAULT 'system'::character varying,
    last_span_id varchar(25),
    CONSTRAINT pk_challenge_stat PRIMARY KEY (id_challenge_stat),
    CONSTRAINT fk_challenge_stat FOREIGN KEY (id_challenge_group) REFERENCES challenge_groups (id_challenge_group),
    CONSTRAINT fk_stat_user FOREIGN KEY (id_user_group) REFERENCES users_groups (id_user_group)
);


INSERT INTO roles (name, description) VALUES
('DEVELOPER', 'Ruolo di sviluppatore'),
('BOARD', 'Board Company'),
('USER', 'Utente');

INSERT INTO "users" (id_role,first_name, last_name, email) VALUES
((select id_role from "roles" where name = 'DEVELOPER'), 'Paolo', 'Calafiore', 'paolo.calafiore@plantict.it'),
((select id_role from "roles" where name = 'DEVELOPER'), 'Flavia', 'Minelli', 'flavia.minelli@plantict.it');


commit;