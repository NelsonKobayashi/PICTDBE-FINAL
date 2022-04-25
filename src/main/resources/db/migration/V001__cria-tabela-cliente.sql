create table cliente (
	cliente_id int not null AUTO_INCREMENT,
	cliente_nome varchar(30) not null,
	cliente_sobrenome varchar(30) not null,
  	email varchar(255) not null,
    telefone varchar(20),
    data_nascimento varchar(10),
    cpf varchar(15),
    cnh varchar(20),
    senha varchar(100) not null,
    funcao_id int not null,

    primary key (cliente_id)
);

alter table cliente add constraint fk_cliente_funcao
foreign key (funcao_id) references funcao (funcao_id);