-- Create sequence movies
CREATE SEQUENCE public.movies_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

-- Create table movies
CREATE TABLE public.movies (
	id numeric(20) NOT NULL DEFAULT nextval('movies_seq'::regclass),
	title varchar(200) NULL,
	description text NOT NULL,
	ranting numeric NULL,
	image text NULL,
	created_at timestamp NOT NULL,
	updated_at timestamp NULL,
	CONSTRAINT movies_pk PRIMARY KEY (id)
);