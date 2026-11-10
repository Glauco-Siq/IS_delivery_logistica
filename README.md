# Sistema Delivery — Domínio de Logística (Equipe 4)

Domínio de **Logística** do sistema de delivery desenvolvido na disciplina de **Integração de Sistemas — 2026.2**.

Este repositório concentra **3 microsserviços independentes**, cada um com seu próprio projeto Spring Boot, `Dockerfile` e banco de dados, integrados aos demais domínios exclusivamente via **HTTP**.

---

## Links do Projeto

| Item | Link |
|------|------|
| Documento de requisitos, integração e diagrama de interação | [Google Docs](https://docs.google.com/document/d/1cTmyvEhYWWtLpTlg_qPy8_X4fBsfC15etOM6hV-Cf4g/edit?usp=drivesdk) |
| Swagger — Frota | http://localhost:8010/swagger-ui.html |
| Swagger — Tracking | http://localhost:8011/swagger-ui.html |
| Swagger — Despacho | http://localhost:8012/swagger-ui.html |

---

## Serviços do Domínio

| Serviço | Porta | Responsabilidade |
|---------|-------|------------------|
| **Frota** | 8010 | Cadastro e gestão de entregadores, status de disponibilidade (ativo/inativo/em entrega) |
| **Tracking** | 8011 | Rastreamento da entrega, registro e consulta de coordenadas, histórico de posições |
| **Despacho** | 8012 | Cálculo de frete e alocação do entregador ao pedido aprovado |

> As portas acima são a reserva da Equipe 4 na faixa `80xx`. Ajuste conforme o alinhamento final da turma.

### Fluxo de integração

```text
Checkout (Eq. 3) ──POST /despachos──▶ Despacho ──reserva entregador──▶ Frota
                                         │
                                         ├──endereço do restaurante──▶ Restaurantes (Eq. 2)
                                         │
                                         └──abre rastreio──▶ Tracking ──status──▶ Pedidos (Eq. 3)
                                                                     └──aviso───▶ Notificações (Eq. 5)

Todos os serviços validam o JWT emitido pela Autenticação (Eq. 1).
```

**Dependências de entrada (quem nos chama):**
- Equipe 3 (Checkout/Pedidos) → solicita cálculo de frete e despacho.

**Dependências de saída (quem chamamos):**
- Equipe 1 (Autenticação/Autorização) → validação de token JWT e permissões.
- Equipe 2 (Catálogo/Restaurantes) → endereço de origem do restaurante.
- Equipe 3 (Pedidos) → atualização de status da entrega.
- Equipe 5 (Notificações) → aviso de "entregador a caminho" / "pedido entregue".

---

## Padrões de Integração

Seguimos os padrões combinados com a turma:

- Rotas e JSON em português, sem acento; rotas no plural com prefixo `/api/v1`; campos em `snake_case`
- Erros sempre no formato `{"erro": true, "mensagem": "..."}` com o status HTTP correto
- Datas em ISO 8601 UTC (`2026-11-20T14:30:00Z`); dinheiro em centavos inteiros (`2590`)
- JWT no header `Authorization: Bearer <token>`
- Header `X-Correlation-Id` repassado em toda chamada entre serviços

| HTTP | Quando usar |
|------|-------------|
| 400 | JSON malformado ou campo com tipo/formato inválido |
| 401 | Token ausente, inválido ou expirado |
| 403 | Token válido, mas sem permissão (RBAC) |
| 404 | Recurso inexistente |
| 409 | Conflito de estado (ex.: entregador já está em entrega) |
| 422 | Regra de negócio violada (ex.: nenhum entregador disponível) |
| 500 | Erro interno |
| 503 | Serviço de outra equipe fora do ar |
| 504 | Serviço de outra equipe não respondeu a tempo |

---

## Endpoints Principais

Todas as rotas abaixo têm o prefixo `/api/v1`.

### Frota — `:8010`
| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/entregadores` | Cadastra entregador |
| GET | `/entregadores` | Lista entregadores (filtro `?status=disponivel`) |
| GET | `/entregadores/{id}` | Detalha entregador |
| PATCH | `/entregadores/{id}/status` | Altera disponibilidade |
| DELETE | `/entregadores/{id}` | Remove/inativa entregador |

### Tracking — `:8011`
| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/rastreios` | Abre rastreio para uma entrega |
| GET | `/rastreios/{id}` | Consulta posição atual e status |
| POST | `/rastreios/{id}/coordenadas` | Registra nova coordenada |
| GET | `/rastreios/{id}/historico` | Histórico de coordenadas |
| PATCH | `/rastreios/{id}/status` | Atualiza status da entrega |

### Despacho — `:8012`
| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/fretes/calcular` | Calcula frete (retorno em centavos) |
| POST | `/despachos` | Aloca entregador para um pedido aprovado |
| GET | `/despachos/{id}` | Consulta despacho |
| PATCH | `/despachos/{id}/cancelar` | Cancela e libera o entregador |

---

## Como Rodar

Pré-requisito: Docker instalado.

```bash
# uma vez por máquina: rede compartilhada com as outras equipes
docker network create delivery-net

# sobe os 3 serviços + bancos
docker compose up --build
```

Health check: `GET http://localhost:8010/actuator/health` (e 8011, 8012).

### Rodando um serviço fora do Docker

```bash
cd frota
./mvnw spring-boot:run
```

---

## Testes

Cobertura mínima exigida: **75%** (unitários + integração), medida com JaCoCo.

```bash
cd frota
./mvnw verify
# relatório em frota/target/site/jacoco/index.html
```

O CI do GitHub Actions roda os testes dos 3 serviços a cada Pull Request.

---

## Estrutura do Repositório

```text
IS_delivery_logistica/
├── frota/
│   ├── src/main/java/br/com/delivery/frota/   # código
│   ├── src/test/java/br/com/delivery/frota/   # testes
│   ├── pom.xml
│   └── Dockerfile
├── tracking/
│   ├── src/main/java/br/com/delivery/tracking/
│   ├── src/test/java/br/com/delivery/tracking/
│   ├── pom.xml
│   └── Dockerfile
├── despacho/
│   ├── src/main/java/br/com/delivery/despacho/
│   ├── src/test/java/br/com/delivery/despacho/
│   ├── pom.xml
│   └── Dockerfile
├── docs/                 # contratos OpenAPI, diagramas, requisitos
├── .github/              # CI e templates de PR e issue
├── docker-compose.yml
├── .env.example
├── CONTRIBUTING.md       # regras de branch, commit e PR
└── README.md
```

---

## Equipe

| Nome | Responsabilidade |
|------|------------------|
| Letícia | Frota |
| Glauco | Tracking |
| Gabriela | Despacho |

Como trabalhamos (branches, commits, Pull Requests): [CONTRIBUTING.md](CONTRIBUTING.md).

---

## Stack

- Linguagem/Framework: Java 21 + Spring Boot 4.1
- Build: Maven
- Banco de dados: PostgreSQL
- Documentação: OpenAPI / Swagger (springdoc)
- Testes: JUnit 5 + JaCoCo
- Conteinerização: Docker + Docker Compose
- CI: GitHub Actions
