CREATE DATABASE document_service encoding='UTF8';

CREATE SCHEMA document_service_storage;

CREATE TABLE document_service_storage.document (
  id BIGSERIAL PRIMARY KEY,
  title CHARACTER VARYING(256) UNIQUE NOT NULL,
  type CHARACTER VARYING(256) NOT NULL,
  upload_date DATE,
  url CHARACTER VARYING(256) UNIQUE NOT NULL
);