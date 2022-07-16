
INSERT INTO perfis(nome) VALUES('ROLE_USUARIO');
INSERT INTO perfis(nome) VALUES('ROLE_ADMIN');

INSERT INTO usuarios(nome, username, email, senha) VALUES('Administrador', 'Admin_01', 'admin@email.com', 'admin01');
INSERT INTO usuarios(nome, username, email, senha) VALUES('Usuario 01', 'Usuario_01', 'usuario01@email.com', 'Usuario_01');
INSERT INTO usuarios(nome, username, email, senha) VALUES('Usuario 02', 'Usuario_02', 'usuario02@email.com', 'Usuario_02');
INSERT INTO usuarios(nome, username, email, senha) VALUES('Eu', 'Eu', 'eu@email.com', '12345');

INSERT INTO usuarios_perfis(usuario_id, perfis_id) VALUES(1, 1);
INSERT INTO usuarios_perfis(usuario_id, perfis_id) VALUES(2, 2);
INSERT INTO usuarios_perfis(usuario_id, perfis_id) VALUES(3, 2);
INSERT INTO usuarios_perfis(usuario_id, perfis_id) VALUES(4, 2);

INSERT INTO topicos(data_criacao, mensagem, status, titulo, autor_id) VALUES('2019-05-05 18:00:00', 'Estou com uma duvida em HTML 5', 'NAO_SOLUCIONADO', 'Duvida com HTML 5', 2);
INSERT INTO topicos(data_criacao, mensagem, status, titulo, autor_id) VALUES('2019-05-05 19:00:00', 'Estou com uma duvida em spring boot', 'NAO_SOLUCIONADO', 'Duvida com Sprng Boot', 2);
INSERT INTO topicos(data_criacao, mensagem, status, titulo, autor_id) VALUES('2019-05-05 20:00:00', 'Estou com uma duvida em Java 11', 'NAO_RESPONDIDO', 'Duvida com Java', 2);
INSERT INTO topicos(data_criacao, mensagem, status, titulo, autor_id) VALUES('2022-06-17 22:03:28', 'Estou com uma duvida em node js', 'NAO_RESPONDIDO', 'Duvida com NodeJS', 2);

INSERT INTO respostas(data_criacao, mensagem, solucao, autor_id, topico_id) VALUES('2019-05-06 18:00:00', 'Resposta 01', FALSE, 3, 1);
INSERT INTO respostas(data_criacao, mensagem, solucao, autor_id, topico_id) VALUES('2019-05-06 19:00:00', 'Resposta 02', FALSE, 3, 1);
INSERT INTO respostas(data_criacao, mensagem, solucao, autor_id, topico_id) VALUES('2019-05-06 19:00:00', 'Resposta 03', FALSE, 3, 2);
