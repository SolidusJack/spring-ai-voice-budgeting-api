# 🎙️ Budgeting AI - Voice Expense Tracker

Uma API REST desenvolvida em **Spring Boot 3** que revoluciona a forma de registrar finanças pessoais.  
Em vez de preencher formulários chatos ou JSONs complexos, o usuário simplesmente envia um áudio gravado dizendo o que gastou (ex: **"Gastei 50 reais em um Gift Card do XBOX"**). O sistema processa a voz, entende o contexto com Inteligência Artificial e salva o registro automaticamente no banco de dados.

---

## 🚀 Tecnologias Utilizadas

- **Java 21** (aproveitando os recursos mais recentes da LTS)
- **Spring Boot 3**
- **Spring AI**: integração nativa e gerenciamento de modelos de IA
- **Groq API (Whisper Large V3)**: transcrição ultra-rápida de áudio para texto
- **OpenRouter API**: processamento de linguagem natural (LLM) e interpretação do texto
- **MySQL**: persistência dos gastos
- **Postman**: validação e testes de integração dos endpoints

---

## 🧠 Arquitetura do Fluxo

O sistema opera dividindo a responsabilidade em três pilares principais:

```text
[ Usuário ] -> ( Envia .mp3 ) -> [ AudioController ]
                                        |
    +-----------------------------------+
    |
    v
[ 1. O Ouvido: AudioService ] ----> ( API da Groq / Whisper ) -> Retorna Texto Puro
    |
    v
[ 2. O Cérebro: ChatClient ] -----> ( OpenRouter / LLM ) ------> Entende o contexto
    |                                      |
    |                                      v
    +--------------------------> [ 3. Os Braços: TransactionTools ] -> Grava no MySQL
```

### 1) O Ouvido (Speech-to-Text)
O `AudioService` recebe o arquivo de áudio e se comunica com a API da Groq usando o modelo `whisper-large-v3`, transformando o som em `String`.

### 2) O Cérebro (Natural Language Processing)
O `ChatClient` (Spring AI) recebe o texto transcrito e, através do OpenRouter, interpreta o valor, o produto e a categoria do gasto.

### 3) Os Braços (Function Calling)
A IA decide dinamicamente acionar o componente `TransactionTools`, que executa a lógica de persistência direto no repositório MySQL.

---

## 🔒 Segurança em Primeiro Lugar

O projeto foi estruturado seguindo boas práticas de segurança para evitar vazamento de chaves privadas no GitHub:

- As credenciais confidenciais (`AUDIO_API_KEY`) ficam armazenadas estritamente em variáveis de ambiente locais da máquina de desenvolvimento.
- O arquivo `application.properties` utiliza apenas referências dinâmicas:
  - `app.audio.api-key=${AUDIO_API_KEY}`
- Arquivos de configuração local sensíveis estão devidamente catalogados no `.gitignore`.

---

## 🛠️ Como Executar o Projeto Localmente

### Pré-requisitos

- JDK 21 instalado e configurado
- Instância do MySQL ativa
- Chaves de API da Groq e do OpenRouter

### Configuração do Ambiente no STS 4 (Spring Tool Suite)

1. Importe o projeto como **Existing Maven Project**.
2. Clique com o botão direito no projeto → **Run As** → **Spring Boot App** (ou configure via **Run Configurations...**).
3. Na janela de configuração de execução, acesse a aba **Environment**.
4. Adicione a sua chave secreta da Groq:
   - **Name:** `AUDIO_API_KEY`
   - **Value:** `gsk_sua_chave_aqui`
5. Aplique e execute o projeto.

---

## 🧪 Como Testar no Postman

Para testar a rota de processamento de voz por completo:

- **Método:** `POST`
- **URL:** `http://localhost:8080/api/audio/transcrever`
- **Body:** selecione `form-data`
- **Key:** `file` (mude o tipo do campo de **Text** para **File** no Postman)
- **Value:** selecione um arquivo de áudio curto (`.mp3`, `.m4a` ou `.wav`) contendo a frase do seu gasto

### Exemplo de Resposta (`200 OK`)

```json
"Perfeito! O gasto de R$ 50,00 com 'Gift Card Playstation' foi registrado com sucesso na categoria Entretenimento."
```

---

## 🔮 Próximos Passos (Roadmap de Evolução)

O objetivo principal de integração entre Áudio, IA e Banco de Dados foi atingido. As próximas melhorias mapeadas são:

- **Interface Web/Mobile (Frontend Interativo):** substituir o envio manual de arquivos `.mp3` no Postman por uma interface visual com botão de microfone (estilo WhatsApp), gravação em tempo real e disparo automático do fluxo.
- **Validação de MIME Types:** adicionar filtros rígidos no backend para rejeitar arquivos corrompidos ou formatos inválidos.
- **Filtro de Silêncio:** tratar requisições onde o áudio não possui fala capturada, evitando chamadas desnecessárias para o modelo.

---

## 🧑‍💻 Autor

Desenvolvido por **SolidusJack** — Estudante de Tecnologia.
