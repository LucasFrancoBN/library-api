-- Inserção de clientes na tabela Client
INSERT INTO client (id, client_id, client_secret, redirect_uri, scope)
VALUES ('660e8400-e29b-41d4-a716-446655440000', 'meuClient', '$2a$10$/yS0vgwkCm3h6PCMAwMA6eKMeqEuyEuU9hWjjjUjKVwjcYQBOEh.y', 'http://localhost:8080/authorized', 'read write delete');
INSERT INTO client (id, client_id, client_secret, redirect_uri, scope)
VALUES ('660e8400-e29b-41d4-a716-446655440001', 'swagger', '$2a$10$Ly8ToK87xQ80dls2aFyO9OjcrQSLp/bLCroPwKAWwm73lHnnEJRE.', 'http://localhost:8080/swagger-ui/index.html', 'read write delete');

INSERT INTO usuario (id, login, senha, email, roles)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'admin', '$2a$10$0Kxhiicu5MeLLeTFA4WosOqstc.czwhYNLqheZsQNVMseDi6xXfN.', 'admin@example.com', ARRAY['GERENTE']);
