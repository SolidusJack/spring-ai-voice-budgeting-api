# 🎙️ Budgeting AI - Voice Expense Tracker

Uma API REST desenvolvida com **Java 21** e **Spring Boot 3** que permite registrar despesas por comando de voz.

Em vez de preencher formulários, o usuário envia um áudio descrevendo um gasto (por exemplo: *"Gastei 50 reais em um Gift Card do Xbox"*). A aplicação realiza a transcrição, interpreta a intenção utilizando um modelo de linguagem e registra automaticamente a transação no banco de dados.

> **Status:** ✅ Funcional
>
> Atualmente o envio do áudio é realizado via **Postman**. A interface web para gravação direta faz parte do roadmap do projeto.

---

# 🚀 Tecnologias Utilizadas

- **Java 21** — linguagem principal
- **Spring Boot 3** — desenvolvimento da API REST
- **Spring AI** — integração com modelos de IA
- **Groq API (Whisper Large V3)** — Speech-to-Text
- **OpenRouter API** — interpretação da linguagem natural e Function Calling
- **MySQL** — persistência dos dados
- **Postman** — testes dos endpoints

---

# ✨ Funcionalidades

- ✅ Upload de arquivos de áudio (.mp3, .wav, .m4a)
- ✅ Transcrição automática utilizando Whisper Large V3
- ✅ Interpretação do contexto usando LLM
- ✅ Registro automático de despesas no MySQL
- ✅ Consulta de gastos por linguagem natural
- ✅ Function Calling utilizando Spring AI
- ✅ Arquitetura em camadas

---

# 🏗️ Arquitetura

O fluxo da aplicação é dividido em três responsabilidades principais:

```text
[ Usuário ]
      |
      | Envia arquivo de áudio (.mp3)
      v
+----------------------+
|  AudioController     |
+----------------------+
          |
          v
+----------------------+
|  AudioService        |
| Speech-to-Text       |
+----------------------+
          |
          | Groq API (Whisper)
          v
Texto transcrito
          |
          v
+----------------------+
| Spring AI ChatClient |
| NLP + Function Call  |
+----------------------+
          |
          v
+----------------------+
| TransactionTools     |
| Persistência         |
+----------------------+
          |
          v
        MySQL
```

## 1. Speech-to-Text

O `AudioService` recebe o arquivo enviado pelo usuário e utiliza o modelo **Whisper Large V3**, através da API da Groq, para converter o áudio em texto.

---

## 2. Interpretação da Linguagem Natural

Após a transcrição, o texto é enviado ao `ChatClient` (Spring AI), que utiliza um modelo hospedado no **OpenRouter** para identificar informações como:

- Valor
- Produto
- Categoria da despesa

---

## 3. Function Calling

Depois da interpretação, a IA decide quando executar a ferramenta `TransactionTools`, responsável por persistir os dados no banco MySQL utilizando o repositório da aplicação.

---

# 📂 Estrutura do Projeto

```text
src
└── main
    ├── controller
    ├── service
    ├── model
    ├── repository
    ├── dto
    ├── config
    └── exception
```

---

# 🔒 Segurança

As credenciais da aplicação não ficam armazenadas no código-fonte.

As chaves de API são carregadas através de variáveis de ambiente.

Exemplo:

```properties
app.audio.api-key=${AUDIO_API_KEY}
```

Os arquivos locais contendo configurações sensíveis estão incluídos no `.gitignore`.

---

# ⚙️ Como Executar

## Pré-requisitos

- JDK 21
- Maven
- MySQL
- Chave da API Groq
- Chave da API OpenRouter

## Configuração

Clone o projeto:

```bash
git clone https://github.com/SolidusJack/spring-ai-voice-budgeting-api.git
```

Configure a variável de ambiente:

```text
AUDIO_API_KEY=sua_chave
```

Configure também as credenciais do banco de dados e da OpenRouter no arquivo `application.properties`.

Execute a aplicação:

```bash
mvn spring-boot:run
```

---

# 🧪 Testando a API

### Processamento de áudio

**POST**

```
http://localhost:8080/api/audio/transcrever
```

**Body**

```
form-data

Key:
file (File)

Value:
arquivo.mp3
```

### Exemplo de resposta

```json
{
    "message": "Perfeito! O gasto de R$ 50,00 com 'Gift Card XBOX' foi registrado com sucesso na categoria Entretenimento."
}
```

---

# 🔮 Roadmap

- Interface Web para gravação direta de áudio
- Integração com gravação em tempo real pelo navegador
- Validação de MIME Types
- Tratamento de arquivos inválidos
- Filtro para áudios sem fala
- Text-to-Speech (resposta por voz)

---

# 👨‍💻 Autor

Desenvolvido por **SolidusJack**

Estudante de Sistemas de Informação.
