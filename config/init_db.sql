-- auto-generated definition
create table resume
(
    uuid      char(36) not null
        constraint resume_pk
            primary key,
    full_name text
);

alter table resume
    owner to postgres;


-- auto-generated definition
create table contact
(
    id            serial   not null
        constraint contact_pk
            primary key,
    contact_type  text     not null,
    contact_value text     not null,
    resume_uuid   char(36) not null
        constraint contact_resume_uuid_fk
            references resume
            on delete cascade
);

alter table contact
    owner to postgres;

create unique index contact_uuid_type_index
    on contact (resume_uuid, contact_type);

-- auto-generated definition
create table section
(
    id            serial   not null,
    section_type  text     not null,
    section_value text     not null,
    resume_uuid   char(36) not null
        constraint section_resume_uuid_fk
            references resume
            on delete cascade
);

alter table section
    owner to postgres;




