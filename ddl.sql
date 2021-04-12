create table code (
code_id CHAR(16) FOR BIT DATA not null,
created timestamp not null, 
length integer check (length<=20 AND length>=1), 
pool varchar(255) not null, 
code_text varchar(255) not null, 
primary key (code_id)
);

create index IDXgbgh4wcibfavyjxrg5hbrbdfq on code (created);
