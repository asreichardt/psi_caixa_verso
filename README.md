#  API de Simulação de Investimentos

API RESTful desenvolvida em Java 21 com Spring Boot 3 para simulação e recomendação de investimentos, incluindo telemetria e autenticação JWT.

##  Funcionalidades

###  Simulação de Investimentos
- Simulação personalizada baseada em produtos cadastrados
- Cálculo automático de rentabilidade e valor final
- Validação de critérios (valor mínimo, prazo mínimo)
- Histórico de simulações por cliente

###  Motor de Recomendação
- Perfil de risco (Conservador, Moderado, Agressivo)
- Recomendações personalizadas baseadas em:
    - Volume de investimentos
    - Frequência de movimentações
    - Preferência por liquidez
- Produtos compatíveis com cada perfil

###  Telemetria e Métricas
- Monitoramento de performance dos serviços
- Métricas agregadas por período
- Tempo de resposta médio por endpoint
- Volume de chamadas por serviço

###  Segurança
- Autenticação JWT

##  Tecnologias

- Java 21 - Linguagem de programação
- Spring Boot 3.2.0 - Framework principal
- Spring Security - Autenticação e autorização
- JWT - Tokens de autenticação
- SQLite - Banco de dados embutido
- JPA/Hibernate - ORM e persistência
- Maven - Gerenciamento de dependências
- JUnit 5 - Testes unitários e integração
- Mockito - Mocking para testes

##  Pré-requisitos

- Java 21 ou superior
- Maven 3.6+
- SQLite (embutido)

##  Instalação e Execução

### 1. Clone o repositório
```bash
git clone https://github.com/seu-usuario/api-investimentos.git
cd api-investimentos
```

### 2. Configure o banco de dados
O SQLite é configurado automaticamente. O arquivo do banco será criado em:
```
api-investimento/db/investimento.db
```

### 3. Execute a aplicação
```bash
# Via Maven
mvn spring-boot:run

# Ou compile e execute
mvn clean package
java -jar target/api-investimentos-1.0.0.jar
```

### 4. Acesse a API
```
http://localhost:8080
```

##  Execução com Docker

```bash
# Build da imagem
docker build -t api-investimentos .

# Executar container
docker run -p 8080:8080 api-investimentos

# Ou com docker-compose
docker-compose up
```

##  Endpoints da API

###  Autenticação

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin"
}
```

Response:
```json
{
  "token": "eyJhbGciOiJIUz...",
  "username": "admin",
  "message": "Login realizado com sucesso"
}
```

###  Simulações

#### Simular Investimento
```http
POST /api/simular-investimento
Authorization: Bearer <token>
Content-Type: application/json

{
  "clienteId": 123,
  "valor": 10000.00,
  "prazoMeses": 12,
  "tipoProduto": "CDB"
}
```

#### Listar Todas Simulações
```http
GET /api/simulacoes
Authorization: Bearer <token>
```

#### Simulações por Cliente
```http
GET /api/simulacoes/{clienteId}
Authorization: Bearer <token>
```

###  Recomendações

#### Perfil de Risco
```http
GET /api/perfil-risco/{clienteId}
Authorization: Bearer <token>
```

#### Produtos Recomendados
```http
GET /api/produtos-recomendados/{perfil}
Authorization: Bearer <token>
```

###  Telemetria

#### Métricas de Performance
```http
GET /api/telemetria?inicio=2025-10-01&fim=2025-10-31
Authorization: Bearer <token>
```

##  Estrutura do Banco 

### Tabelas 

#### `produto`
- `id` - Identificador único
- `nome` - Nome do produto
- `tipo` - Tipo (CDB, Fundo, Ações, etc.)
- `rentabilidade` - Taxa de rentabilidade anual
- `risco` - Nível de risco (Baixo, Médio, Alto)
- `valor_minimo` - Valor mínimo de investimento
- `prazo_minimo_meses` - Prazo mínimo em meses
- `ativo` - Status do produto

#### `simulacao`
- `id` - Identificador único
- `cliente_id` - ID do cliente
- `produto` - Nome do produto simulado
- `valor_investido` - Valor inicial
- `valor_final` - Valor calculado
- `prazo_meses` - Prazo da simulação
- `data_simulacao` - Data/hora da simulação
- `tipo_produto` - Tipo do produto

#### `telemetria`
- `id` - Identificador único
- `nome_servico` - Nome do serviço monitorado
- `tempo_resposta_ms` - Tempo de resposta em ms
- `data_chamada` - Data/hora da chamada
- `sucesso` - Indica se a chamada foi bem-sucedida

##  Testes

### Executar Testes Unitários
```bash
mvn test
```

### Executar Testes de Integração
```bash
mvn test -Dtest="IntegrationTest"
```

### Executar Testes Específicos
```bash
# Testes do AuthController
mvn test -Dtest=AuthControllerTest

# Testes do SimulacaoController
mvn test -Dtest=SimulacaoControllerTest

# Testes de integração
mvn test -Dtest=SimulacaoControllerIntegrationTest
```

### Cobertura de Testes
- Testes Unitários: Lógica de negócio isolada
- Testes de Integração: Stack completa (Spring + Banco)

## Configuração

### application.properties
```properties
# Banco de Dados
spring.datasource.url=jdbc:sqlite:db/investimento.db
spring.jpa.hibernate.ddl-auto=update

# JWT
jwt.secret=chave_jwt
jwt.expiration=86400000

# Servidor
server.port=8080
```

### Variáveis de Ambiente
```bash
export SPRING_DATASOURCE_URL=jdbc:sqlite:/caminho/para/banco.db
export JWT_SECRET=chave-teste
export JWT_EXPIRATION=86400000
```


### Estrutura do Projeto
```
src/
├── main/
│   ├── java/com/investimento/
│   │   ├── controller/     # Controladores REST
│   │   ├── service/        # Lógica de negócio
│   │   ├── repository/     # Acesso a dados
│   │   ├── entity/         # Entidades JPA
│   │   ├── dto/           # Data Transfer Objects
│   │   └── security/      # Configuração de segurança
│   └── resources/
│       ├── application.properties
│       └── data.sql       # Dados iniciais
└── test/
    └── java/com/investimento/
        └── controller/    # Testes unitários e integração
```

### Padrões Adotados
- RESTful - Design de APIs
- MVC - Arquitetura da aplicação
- DTO - Transferência de dados
- Injeção de Dependência - Gerenciamento de beans
- Transações - Controle transacional


### Problemas Comuns

1. Erro de banco travado
    - Solução: Delete os arquivos `.db`, `.db-shm`, `.db-wal` na pasta db e reinicie

2. Erro 403 - Acesso negado
    - Verifique se o token JWT está sendo enviado
    - Confirme se o token não expirou

3. Erro na simulação
    - Verifique se existem produtos cadastrados
    - Confirme os critérios (valor mínimo, prazo mínimo)

