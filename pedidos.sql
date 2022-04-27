create table pedido (
	pedido_id int not null AUTO_INCREMENT,
	data_retirada varchar(10) not null,
	horario_retirada varchar(15) not null,
	data_entrega varchar(10) not null,
	horario_entrega varchar(15) not null,
	local_retirada varchar(60) not null,
	local_entrega varchar(60) not null,
    valor_locacao double not null,
    valor_seguro double not null,
    periodo int not null,
	cliente_id int,
    cidades_id int,
    carro_id int,

	primary key (pedido_id)
);

alter table pedido add constraint fk_pedido_cliente
foreign key (cliente_id) references cliente (cliente_id);

alter table pedido add constraint fk_pedido_cidades_id
foreign key (cidades_id) references cidades (cidades_id);

alter table pedido add constraint fk_pedido_carro
foreign key (carro_id) references carro (carro_id);



INSERT INTO `dbalucar`.`pedido` (`pedido_id`,`data_retirada`, `horario_retirada`, `data_entrega`, `horario_entrega`, `local_retirada`, `local_entrega`, `valor_locacao`, `valor_seguro`, `periodo`, `cliente_id`, `cidades_id`, `carro_id`)
values	(1, "14/04/2022", "09:00", "17/04/2022", "20:00", "São Paulo", "São Paulo", 240, 60, "4", 5, 4, 1),
		(2, "18/04/2022", "09:00", "21/04/2022", "20:00", "São Paulo", "Rio Janeiro", 240, 60, "4", 2, 4, 1),
        (3, "20/04/2022", "9:00", "23/04/2022", "20:00", "Paraná", "São Paulo", 240, 60, "4", 3, 8, 2),
        (4, "27/05/2022", "09:00", "30/05/2022", "20:00", "Cuiabá", "Campo Grande", 240, 60, "4", 5, 11, 3);
        