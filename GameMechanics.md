# Mecânica do Jogo

Servidor solicita uma ação ao jogador.

### INCOME:
- Jogador solicita uma moeda.
- Servidor dá moeda ao jogador.

### FOREIGN:
- Jogador1 solicita ajuda externa.
- Servidor verifica se alguém deseja bloquear.
- Caso alguém deseje:
  - Servidor pergunta ao jogador1 se ele deseja contestar o bloqueio.
  - Caso ele deseje:
    - Servidor verifica se jogador2 tem carta para bloqueio.
    - Caso ele tenha:
      - Servidor solicita uma carta ao jogador1 de sua mão.
      - Servidor remove carta da mão do jogador1 e coloca no cemitério.
      - Servidor troca carta do jogador2 utilizada por uma aleatória do baralho.
    - Caso não:
      - Servidor solicita uma carta ao jogador2 de sua mão.
      - Servidor remove carta da mão do jogador2 e coloca no cemitério.
      - Servidor dá 2 moedas ao jogador1.			
- Do contrário:
  - Servidor dá 2 moedas ao jogador1.

### COUP:
- Jogador1 solicita golpe de estado no jogador2.
- Servidor solicita uma carta ao jogador2 de sua mão.
- Servidor remove carta da mão do jogador2 e coloca no cemitério. 	

### TAXES:
- Jogador1 solicita taxas.
- Servidor verifica se alguém deseja contestar.
- Caso alguém deseje:
  - Servidor verifica se jogador1 tem carta para taxas.
  - Caso ele tenha:
    - Servidor solicita uma carta ao jogador2 de sua mão.
    - Servidor remove carta da mão do jogador2 e coloca no cemintério.
    - Servidor dá taxas ao jogador1.
    - Servidor troca carta do jogador1 utilizada por uma aleatória do baralho.
  - Caso não:
    - Servidor solicita uma carta ao jogador1 de sua mão.	
    - Servidor remove carta da mão do jogador1 e coloca no cemintério.
- Do contrário:
  - Servidor dá taxas ao jogador1.

### ASSASSINATE:
- Jogador1 solicita assassinato no jogador2.
- Servidor remove 3 moedas do jogador1 e verifica se jogador2 deseja permitir, bloquear ou contestar.
- Caso ele deseje contestar:
  - Servidor verifica se jogador1 tem carta para assassinato.
  - Caso ele tenha:
    - Servidor remove as 2 cartas do jogador2, coloca no cemitério e o retira do jogo.
    - Servidor troca carta do jogador1 utilizada por uma aleatória do baralho.
  - Caso não:
    - Servidor solicita uma carta ao jogador1 de sua mão.
    - Servidor remove carta da mão do jogador1 e coloca no cemitério.
- Caso ele deseje bloquear:
  - Servidor verifica se jogador1 deseja contestar bloqueio.
  - Caso ele deseje:
    - Servidor verifica se jogador2 tem carta para bloqueio.
    - Caso tenha:
      - Servidor solicita uma carta ao jogador1 de sua mão.
      - Servidor remove carta da mão do jogador1 e coloca no cemitério.
      - Servidor troca carta do jogador2 utilizada por uma aleatória do baralho.
    - Caso não:
      - Servidor remove as 2 cartas do jogador2, coloca no cemitério e o retira do jogo. 		
- Caso ele deseje permitir:
  - Servidor solicita uma carta ao jogador2 de sua mão.
  - Servidor remove carta da mão do jogador2 e coloca no cemitério.
    
### STEAL:
- Jogador1 solicita extorquir no jogador2.
- Servidor verifica se jogador2 deseja permitir, bloquear ou contestar.
- Caso ele deseje contestar:
  - Servidor verifica se jogador1 tem carta para extorquir.
  - Caso ele tenha:
    - Servidor solicita uma carta ao jogador2 de sua mão.
    - Servidor remove carta da mão do jogador2 e coloca no cemitério.
    - Servidor transfere (duas ou uma moeda) do jogador2 para o jogador1.
    - Servidor troca carta do jogador1 utilizada por uma aleatória do baralho.
  - Caso não:
    - Servidor solicita uma carta ao jogador1 de sua mão.
    - Servidor remove carta da mão do jogador1 e coloca no cemitério.			
- Caso ele deseje bloquear:
  - Servidor verifica se jogador1 deseja contestar bloqueio.
  - Caso deseje:
    - Servidor verifica se jogador2 tem carta para bloqueio.
    - Caso tenha:
      - Servidor solicita uma carta ao jogador1 de sua mão.
      -	Servidor remove carta da mão do jogador1 e coloca no cemitério.
      - Servidor troca carta do jogador2 utilizada por uma aleatória do baralho.	 				
    - Caso não:
      - Servidor solicita uma carta ao jogador2 de sua mão.
      - Servidor remove carta da mão do jogador2 e coloca no cemitério. 
      - Servidor transfere (duas ou uma moeda) do jogador2 para o jogador1.
- Caso ele deseje permitir:
  - Servidor transfere (duas ou uma moeda) do jogador2 para o jogador1.

### SWAP:
- Jogador1 solicita trocar.
- Servidor verifica se alguém deseja contestar.
- Caso alguém deseje:
  - Servidor verifica se jogador1 tem carta para trocar.
  - Caso tenha:
    - Servidor solicita uma carta ao jogador2 de sua mão.
    - Servidor remove carta da mão do jogador2 e coloca no cemitério.
    - Servidor retira duas cartas do baralho e add na mão do jogador1.
    - Servidor solicita ao jogador 1 para escolher 2 cartas a serem removidas.
    - Servidor remove 2 cartas da mão do jogador1 escolhidas por ele e as reinsere no baralho.
  - Caso não:
    - Servidor solicita uma carta ao jogador1 de sua mão.
    - Servidor remove carta da mão do jogador1 e coloca no cemitério.
- Caso contrário:
  - Servidor retira duas cartas do baralho e add na mão do jogador1.
  - Servidor solicita ao jogador 1 para escolher 2 cartas a serem removidas.
  - Servidor remove 2 cartas da mão do jogador1 escolhidas por ele e as reinsere no baralho.
 	
