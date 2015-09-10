drop table fe_gps;

create table fe_gps(
	upload_time timestamp with time zone, --Upload time2
	dataHora	timestamp,
	ordem	Char(6),
	linha	varchar(8),
	latitude	numeric,
	longitude	numeric,
	pos		geography(POINT, 4326),
	velocidade	numeric

);


comment on column fe_gps.upload_time is 'Time data was uploaded';
comment on column fe_gps.dataHora is 'Time of record';
comment on column fe_gps.ordem is 'Bus ID';
comment on column fe_gps.linha is 'Bus route';
comment on column fe_gps.velocidade is 'Speed ';