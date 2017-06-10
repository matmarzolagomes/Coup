# Script Coup

- ##### Inicia Servidor:
  - Solicita Nº da Porta ou Escolhe Automaticamente.
    - Verifica se a porta é válida.
  - Solicita Nº de Jogadores.
    - Verifica se: `2 <= NumPlayers <= LIMITE`.

- ##### Executa Cliente:
  - Solicita IP do Servidor e Porta de Acesso.
    - Verifica se conexão foi bem-sucedida.

>`Repita até que todos os jogadores tenham se conectado:`
- ##### Servidor:
  - Solicita o nome do jogador ao Cliente.
    - Verifica se: `name != NULL && 3 <= name <= LIMITE && !HashMap.contains(name)`.   
  - Insere o nome do jogador em um HashMap de nomes e índices.

- ##### Cliente:
  - Insere um nome.
    - `Caso seja válido, exibe a mensagem:` Aguardando demais jogadores.
>`Fim Loop.`

- ##### Servidor:
  - Instância a mecânica do jogo passando como argumento o `HashMap`.
  - `Busca o próximo jogador na HashMap enquanto houver mais de 1 elemento`:
    - [Envia ao jogador as suas opções de jogo.](https://github.com/matmarzolagomes/Coup/wiki/GameMechanics)


> OBS: Se o jogador demorar mais que o tempo limite ele será desconectado do jogo.
