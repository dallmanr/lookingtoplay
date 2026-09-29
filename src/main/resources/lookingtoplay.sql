-- SQL Scripts generated from PgAdmin
-- backup database, SQL, plain, schema only, do not save owner

CREATE TABLE public.authorities (
    username character varying(50) NOT NULL,
    authority character varying(50) NOT NULL
);

CREATE TABLE public.games (
    game_id integer CONSTRAINT games_id_not_null NOT NULL,
    name character varying NOT NULL,
    summary character varying,
    url character varying,
    game_type integer,
    cover character varying,
    igdb_id integer DEFAULT 0 NOT NULL,
    first_release_date integer,
    rating double precision,
    cover_id integer,
    release_status integer
);


CREATE TABLE public.lobbies (
    id integer CONSTRAINT lobby_id_not_null NOT NULL,
    status character varying(20) DEFAULT 'OPEN'::character varying,
    lobby_info character varying(500),
    owner_id bigint CONSTRAINT lobby_owner_id_not_null NOT NULL,
    name character varying(50) CONSTRAINT lobby_name_not_null NOT NULL,
    game_id bigint CONSTRAINT lobby_game_id_not_null NOT NULL
);

CREATE TABLE public.lobby_users (
    lobby_id bigint CONSTRAINT lobby_user_lobby_id_not_null NOT NULL,
    user_id bigint CONSTRAINT lobby_user_user_id_not_null NOT NULL
);

CREATE TABLE public.platform_game (
    game_id bigint NOT NULL,
    platform_id bigint NOT NULL
);

CREATE TABLE public.platforms (
    id integer CONSTRAINT platform_id_not_null NOT NULL,
    igdb_platform_id integer,
    abbreviation character varying,
    platform_name character varying
);

CREATE TABLE public.reviews (
);

CREATE TABLE public.users (
    username character varying(50) NOT NULL,
    password character varying(100) NOT NULL,
    enabled integer NOT NULL,
    email_address character varying(100) CONSTRAINT user_email_address_not_null NOT NULL,
    user_id bigint CONSTRAINT user_user_id_not_null NOT NULL
);
