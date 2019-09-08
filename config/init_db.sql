-- auto-generated definition
create table resume
(
    uuid      char(36) not null
        constraint resume_pk
            primary key,
    full_name text     NOT NULL
);

-- auto-generated definition
create table contact
(
    id            serial   not null
        constraint contact_pk
            primary key,
    contact_type  text     not null,
    contact_value text     not null,
    resume_uuid   char(36) not null references resume (uuid)
        on delete cascade
);

create unique index contact_resume_uuid_index
    on contact (resume_uuid, contact_type);

--- auto-generated definition
create table section
(
    id            serial   not null
        constraint section_pk
            primary key,
    section_type  text     not null,
    section_value text     not null,
    resume_uuid   char(36) not null
        constraint section_resume_uuid_fk
            references resume
            on delete cascade
);

create unique index section_uuid_type_index
    on section (resume_uuid, section_type);





