insert into TIPOMONEDA(id, descripcion) values (1, 'Sol Peruano')
insert into TIPOMONEDA(id, descripcion) values (2, 'Peso Argentino')
insert into TIPOMONEDA(id, descripcion) values (3, 'Peso Mexicano')

insert into CAMBIO(id, id_origen, id_destino, tipo_cambio) values (1, 1, 2, 20.06)
insert into CAMBIO(id, id_origen, id_destino, tipo_cambio) values (2, 1, 3, 6.43)