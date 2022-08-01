
INSERT INTO perfis(nome) VALUES('ROLE_USUARIO');
INSERT INTO perfis(nome) VALUES('ROLE_ADMIN');

INSERT INTO verificacao_email(uuid, data_criacao, data_verificacao, verificado) VALUES('1d33c32f-9e6e-4d4b-8b0d-2d52ac623b9c', '2019-05-05 18:00:00', '2019-05-05 18:00:00', TRUE);
INSERT INTO verificacao_email(uuid, data_criacao, data_verificacao, verificado) VALUES('e5574f59-61c5-41e0-bc2c-70eef094404f', '2019-05-05 18:00:00', '2019-05-05 18:00:00', TRUE);
INSERT INTO verificacao_email(uuid, data_criacao, data_verificacao, verificado) VALUES('76deaaaf-3cd9-4797-a9f5-3c0034079209', '2019-05-05 18:00:00', '2019-05-05 18:00:00', TRUE);
INSERT INTO verificacao_email(uuid, data_criacao, data_verificacao, verificado) VALUES('1009ae18-2db9-487b-bade-622f6521b596', '2019-05-05 18:00:00', '2019-05-05 18:00:00', TRUE);

INSERT INTO usuarios(nome, email, username, senha, verificacao_email_id) VALUES('Administrador', 'admin@email.com', 'admin', '$2a$10$..BbYZDEPCujwGSDt.DUOOKFvj6WPJL23X4rHOUuO1Rsdr./oUjiC', 1);
INSERT INTO usuarios(nome, email, username, senha, verificacao_email_id) VALUES('Usuario 01', 'usuario01@email.com', 'usuario01', '$2a$10$..BbYZDEPCujwGSDt.DUOOKFvj6WPJL23X4rHOUuO1Rsdr./oUjiC', 2);
INSERT INTO usuarios(nome, email, username, senha, verificacao_email_id) VALUES('Usuario 02', 'usuario02@email.com', 'usuario02', '$2a$10$..BbYZDEPCujwGSDt.DUOOKFvj6WPJL23X4rHOUuO1Rsdr./oUjiC', 3);
INSERT INTO usuarios(nome, email, username, senha, verificacao_email_id) VALUES('Eu', 'eu@email.com', 'eu', '$2a$10$..BbYZDEPCujwGSDt.DUOOKFvj6WPJL23X4rHOUuO1Rsdr./oUjiC', 4);

INSERT INTO usuarios_perfis(usuario_id, perfis_id) VALUES(1, 2);
INSERT INTO usuarios_perfis(usuario_id, perfis_id) VALUES(2, 1);
INSERT INTO usuarios_perfis(usuario_id, perfis_id) VALUES(3, 1);
INSERT INTO usuarios_perfis(usuario_id, perfis_id) VALUES(4, 1);

INSERT INTO topicos(data_criacao, mensagem, status, titulo, autor_id, tags) VALUES('2019-05-05 18:00:00', 'Estou com uma duvida em HTML 5', 'NAO_SOLUCIONADO', 'Duvida com HTML 5', 2, null);
INSERT INTO topicos(data_criacao, mensagem, status, titulo, autor_id, tags) VALUES('2019-05-05 19:00:00', 'Estou com uma duvida em spring boot', 'NAO_SOLUCIONADO', 'Duvida com Spring Boot', 2, 'Spring, Spring Boot, Java, Java 17');
INSERT INTO topicos(data_criacao, mensagem, status, titulo, autor_id, tags) VALUES('2019-05-05 20:00:00', 'Estou com uma duvida em Java 11', 'NAO_RESPONDIDO', 'Duvida com Java', 2, 'Java, Java 8');
INSERT INTO topicos(data_criacao, mensagem, status, titulo, autor_id, tags) VALUES('2022-06-17 22:03:28', 'Estou com uma duvida em node js', 'NAO_RESPONDIDO', 'Duvida com NodeJS', 2, 'Node, NodeJS');

INSERT INTO respostas(data_criacao, mensagem, solucao, autor_id, topico_id) VALUES('2019-05-06 18:00:00', 'Resposta 01', FALSE, 3, 1);
INSERT INTO respostas(data_criacao, mensagem, solucao, autor_id, topico_id) VALUES('2019-05-06 19:00:00', 'Resposta 02', FALSE, 3, 1);
INSERT INTO respostas(data_criacao, mensagem, solucao, autor_id, topico_id) VALUES('2019-05-06 19:00:00', 'Resposta 03', FALSE, 3, 2);
