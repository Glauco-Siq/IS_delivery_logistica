# Como contribuir

Regras de trabalho da equipe. Curtas de propósito.

## Fluxo

1. Crie uma issue para a tarefa (ou pegue uma do quadro).
2. Crie uma branch a partir da `main` atualizada:
   ```bash
   git switch main && git pull
   git switch -c feat/frota-crud-entregadores
   ```
3. Faça commits pequenos seguindo o padrão abaixo.
4. Abra um Pull Request para a `main` e marque pelo menos uma pessoa da equipe como revisora.
5. O CI precisa passar e alguém da equipe precisa aprovar.
6. Merge com **Squash and merge**. Apague a branch depois.

Ninguém faz push direto na `main`.

## Nome das branches

`<tipo>/<servico>-<descricao-curta>`

| Tipo      | Quando usar                          | Exemplo                               |
|-----------|--------------------------------------|---------------------------------------|
| `feat`    | Funcionalidade nova                  | `feat/despacho-calculo-frete`         |
| `fix`     | Correção de bug                      | `fix/tracking-status-invalido`        |
| `test`    | Só testes                            | `test/frota-cobertura-service`        |
| `docs`    | Documentação, OpenAPI, README        | `docs/contrato-despacho`              |
| `chore`   | Docker, CI, dependências, config     | `chore/docker-compose-postgres`       |
| `refactor`| Muda código sem mudar comportamento  | `refactor/despacho-cliente-http`      |

## Mensagens de commit (Conventional Commits)

`<tipo>(<servico>): <o que mudou, no imperativo>`

```text
feat(frota): adiciona CRUD de entregadores
fix(tracking): impede transição de alocado para entregue
test(despacho): cobre cálculo de frete com distância zero
chore: adiciona healthcheck dos bancos no compose
docs(despacho): documenta POST /despachos no OpenAPI
```

## Padrões de código

- Pacotes por camada dentro de cada serviço:
  ```text
  br.com.delivery.<servico>
  ├── controller   # endpoints REST
  ├── service      # regras de negócio
  ├── repository   # Spring Data JPA
  ├── model        # entidades JPA
  ├── dto          # objetos de entrada e saída da API
  ├── client       # chamadas HTTP a outros serviços
  ├── config       # configurações (Jackson, segurança, etc.)
  └── exception    # exceções e @RestControllerAdvice
  ```
- Controller nunca devolve entidade JPA: sempre DTO.
- URLs de outros serviços vêm do `application.properties` / variáveis de ambiente, nunca fixas no código.
- Toda chamada a outro serviço tem timeout e loga antes e depois.
- Testes junto com o código, no mesmo PR. A cobertura não pode cair abaixo de 75%.

## Checklist antes de abrir o PR

- [ ] `./mvnw verify` passa localmente
- [ ] Contrato OpenAPI atualizado, se a API mudou
- [ ] Equipes afetadas avisadas, se o contrato mudou
