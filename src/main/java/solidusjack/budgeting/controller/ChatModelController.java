package solidusjack.budgeting.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import solidusjack.budgeting.config.TransactionTools;

@RestController
@RequestMapping("/api")
public class ChatModelController {

    private final ChatClient chatClient;

    public ChatModelController(ChatClient.Builder builder, TransactionTools transactionTools) {
        this.chatClient = builder
                .defaultSystem("Você é um assistente financeiro direto. Seu objetivo é extrair o valor e a categoria informados pelo usuário e registrar o gasto. IMPORTANTE: Ao usar ferramentas, você DEVE retornar os argumentos em formato JSON puro e estrito, sem formatação markdown, sem crases e sem caracteres especiais.")
                .defaultTools(transactionTools) 
                .build(); 
    }

    @GetMapping("/chat-model")
    public String chat(@RequestParam String prompt) {
        return this.chatClient
                .prompt(prompt)
                .call()
                .content();
    }
}