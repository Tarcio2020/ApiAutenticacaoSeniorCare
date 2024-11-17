# Chat Api WebSocket com Spring Framework

Este código implementa um servidor WebSocket utilizando o **Spring Framework**, permitindo a comunicação em tempo real entre usuários. Ele gerencia conexões de usuários e envia mensagens entre eles com base nos identificadores fornecidos.

## Funcionalidades

- **Gerenciamento de Conexões**: O servidor registra as conexões dos usuários quando eles se conectam e remove as sessões quando desconectam.
- **Envio de Mensagens**: Quando um usuário envia uma mensagem, o servidor localiza a sessão do destinatário e transmite a mensagem, caso a sessão esteja ativa.
- **Identificação do Usuário**: Cada usuário se conecta usando um identificador único (`userId`) passado na URL da conexão WebSocket.

## Como Funciona

1. **Conexão**: O usuário se conecta ao WebSocket fornecendo seu `userId` na URL de conexão.
2. **Envio de Mensagens**: O usuário envia uma mensagem para um destinatário especificado, que é recebida pelo servidor.
3. **Entrega de Mensagens**: O servidor busca a sessão ativa do destinatário e envia a mensagem para ele.
4. **Desconexão**: Quando o usuário desconecta, sua sessão é removida do servidor.

## Estrutura do Código

- **`MensagensChat`**: Classe que representa a mensagem enviada entre os usuários, contendo o `remetenteId`, `destinatarioId` e o `conteudo` da mensagem.
- **`WebSocketHandler`**: Classe responsável pelo gerenciamento das conexões WebSocket, envio e recebimento das mensagens, e manutenção das sessões dos usuários.

## Dependências

- Spring WebSocket
- Jackson (para conversão de objetos JSON)

## Como Rodar

1. Clone o repositório:
   ```bash
   git clone <url-do-repositório>
