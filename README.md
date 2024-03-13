# Projeto de Reserva de Salas ⭐

Este é um projeto simples de reserva de salas criado para fins de estudo onde os usuários podem criar reservas para salas disponíveis. O projeto possui três entidades principais:

### User 👤
- A entidade `User` representa os usuários do sistema.
- Cada usuário possui um nome, um e-mail, uma senha e um nível (`UserLevel`) que especifica seu status no sistema.
- O nível do usuário pode ser:
  - Bronze
  - Prata
  - Ouro

### Room 🏠
- A entidade `Room` representa as salas disponíveis para reserva.
- Cada sala tem um nome, uma descrição, um preço por dia e um status que indica se está disponível ou não para reserva.

### Reserve 📅
- A entidade `Reserve` representa uma reserva feita por um usuário para uma sala específica em um determinado período.
- Cada reserva contém o usuário que fez a reserva, a sala reservada, a quantidade de dias reservados, a data de início, a data de término e o total referente à quantidade de dias reservados.

<br>

## Documentação 📝

[Baixar documentação em PDF](https://github.com/mateuzu/reserve_system/files/14594518/Projeto.API.Reserve.pdf)

<br>

## Funcionalidades 🛠️

O projeto oferece as seguintes funcionalidades:

- **Criar Usuário**: Os usuários podem ser criados no sistema fornecendo um nome, um e-mail, uma senha e um nível (Bronze, Prata ou Ouro).
- **Listar Salas Disponíveis**: Os usuários podem ver uma lista das salas disponíveis para reserva.
- **Fazer Reserva**: Os usuários podem fazer uma reserva para uma sala específica, fornecendo o identificador do Usuário, a credencial da Sala e a quantidade de dias reservados.
- **Listar Reservas**: Os usuários podem ver uma lista de suas reservas atuais e passadas.
- **Resumo do Usuário**: Permite aos usuários ver um resumo de suas reservas, incluindo o total de gastos, a quantidade total de dias reservados e o desconto proporcional ao nível do usuário (Bronze: 5%, Prata: 8%, Ouro: 15%).

<br>

## Recursos 💡

O projeto implementa um tratamento de exceções personalizado para melhor identificação de erros.

- ObjectNotFoundException: Quando um recurso solicitado não é encontrado no banco de dados.

<br>

## Tecnologias utilizadas 💻
<img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="40" height="40" style="max-width: 100%;"><img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/spring/spring-original.svg" alt="spring" width="40" height="40" style="max-width: 100%;"><img src="https://github.com/devicons/devicon/blob/master/icons/maven/maven-original.svg" alt="maven" width="40" height="40" style="max-width: 100%;"><img src="https://github.com/devicons/devicon/blob/master/icons/postgresql/postgresql-original.svg" alt="postgresql" width="40" height="40" style="max-width: 100%;"><img src="https://github.com/devicons/devicon/blob/master/icons/insomnia/insomnia-original.svg" alt="insomnia" width="40" height="40" style="max-width: 100%;"><img src="https://github.com/devicons/devicon/blob/master/icons/swagger/swagger-original.svg" alt="swagger" width="40" height="40" style="max-width: 100%;">

<br>

## Como executar o projeto ⚙️
1. Clone o repositório para sua máquina local.
2. Configure as propriedades do banco de dados no arquivo application.properties.
3. Execute o projeto utilizando sua IDE ou executando o comando mvn spring-boot:run.
4. Os endpoints estarão disponíveis em http://localhost:8080.

<br>

## Conclusão 📊
Este é um projeto simples de reserva de salas, adequado para fins de aprendizado e prática com Spring Boot, APIs RESTful e PostgreSQL.
