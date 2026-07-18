package solidusjack.budgeting.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import solidusjack.budgeting.config.TransactionTools;
import solidusjack.budgeting.service.AudioService;

@RestController
@RequestMapping("/api/audio")
public class AudioController {

    private final AudioService audioService;
    private final ChatClient chatClient; 


    public AudioController(AudioService audioService, ChatClient.Builder builder, TransactionTools transactionTools) {
        this.audioService = audioService;
   
        this.chatClient = builder
                .defaultSystem("Você é um assistente financeiro direto. Seu objetivo é extrair o valor e a categoria informados pelo usuário e registrar o gasto. IMPORTANTE: Ao usar ferramentas, você DEVE retornar os argumentos em formato JSON puro e estrito, sem formatação markdown, sem crases e sem caracteres especiais.")
                .defaultTools(transactionTools) 
                .build(); 
    }

    @PostMapping("/transcrever")
    public ResponseEntity<String> transcrever(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Por favor, envie um arquivo de áudio válido.");
            }

            String textoTranscrevido = audioService.transcreverAudio(file);
            System.out.println("Texto falado pelo usuário: " + textoTranscrevido);

            String respostaDaIa = this.chatClient
                    .prompt(textoTranscrevido)
                    .call()
                    .content();
            
            return ResponseEntity.ok(respostaDaIa);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao processar o fluxo de áudio: " + e.getMessage());
        }
    }
}