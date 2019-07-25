create table resume
(
    uuid      char(36) not null primary key,
    full_name text     not null
);

alter table resume
    owner to postgres;

create table contact
(
    id          serial,
    resume_uuid char(36) not null references resume (uuid) on delete cascade,
    type        text     not null,
    value       text     not null

);

alter table contact
    owner to postgres;
create unique index contact_uuid_type_index on contact (resume_uuid, type);



