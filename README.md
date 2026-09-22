# Sistema Delivery — Domínio de Logística (Equipe 4)

Domínio de **Logística** do sistema de delivery desenvolvido na disciplina de **Integração de Sistemas — 2026.2**.

Este repositório concentra **3 microsserviços independentes**, com bancos de dados isolados, conteinerizados e integrados aos demais domínios exclusivamente via **HTTP**.

---

## Links do Projeto

| Item | Link |
|------|------|
| Documento de Requisitos | <!-- COLE O LINK AQUI --> |
| Diagrama de Arquitetura / Integração | <!-- COLE O LINK AQUI --> |
| Swagger — Frota | http://localhost:8010/docs |
| Swagger — Tracking | http://localhost:8011/docs |
| Swagger — Despacho | http://localhost:8012/docs |

---

## Serviços do Domínio

| Serviço | Porta | Responsabilidade |
|---------|-------|------------------|
| **Frota** | 8010 | Cadastro e gestão de entregadores, status de disponibilidade (ativo/inativo/em entrega) |
| **Tracking** | 8011 | Rastreamento da entrega, registro e consulta de coordenadas, histórico de posições |
| **Despacho** | 8012 | Cálculo de frete e alocação do entregador ao pedido aprovado |

> As portas acima são a reserva da Equipe 4 na faixa `80xx`. Ajuste conforme o alinhamento final da turma.

---  

**Dependências de entrada (quem nos chama):**
- Equipe 3 (Checkout/Pedidos) → solicita cálculo de frete e despacho.

**Dependências de saída (quem chamamos):**
- Equipe 1 (Autenticação/Autorização) → validação de token JWT e permissões.
- Equipe 2 (Catálogo/Restaurantes) → endereço de origem do restaurante.
- Equipe 5 (Notificações) → aviso de "entregador a caminho" / "pedido entregue".


| HTTP | Quando usar |
|------|-------------|
| 400 | Payload inválido ou regra de negócio violada |
| 401 | Token ausente ou inválido |
| 403 | Token válido, mas sem permissão (RBAC) |
| 404 | Recurso inexistente |
| 422 | Validação de campos |
| 500 | Erro interno |
| 503 | Dependência externa indisponível (serviço de outra equipe fora do ar) |

---

## Endpoints Principais

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


## Testes

Cobertura mínima exigida: **75%** (unitários + integração).

## Estrutura do Repositório

```
Sistema_delivery_logistica/
├── frota/
│   ├── src/
│   ├── tests/
│   └── Dockerfile
├── tracking/
│   ├── src/
│   ├── tests/
│   └── Dockerfile
├── despacho/
│   ├── src/
│   ├── tests/
│   └── Dockerfile
├── docs/
│   ├── diagrama.png
│   └── requisitos.md
├── docker-compose.yml
├── .env.example
└── README.md
```

## Equipe

| Nome | Responsabilidade |
|------|------------------|
| <!-- NOME --> | Frota |
| <!-- NOME --> | Tracking |
| <!-- NOME --> | Despacho |

---

## Stack

- Linguagem/Framework: <!-- PREENCHER -->
- Banco de dados: <!-- PREENCHER -->
- Documentação: OpenAPI / Swagger
- Conteinerização: Docker + Docker Compose
