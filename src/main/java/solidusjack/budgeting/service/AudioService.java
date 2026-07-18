package solidusjack.budgeting.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AudioService {

    @Value("${app.audio.api-key}")
    private String apiKey;

    private final RestClient restClient;

    public AudioService() {
        this.restClient = RestClient.builder()
                .baseUrl("https://api.groq.com/openai/v1")
                .build();
    }

    public String transcreverAudio(MultipartFile arquivoAudio) throws IOException {
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        
        ByteArrayResource recursoArquivo = new ByteArrayResource(arquivoAudio.getBytes()) {
            @Override
            public String getFilename() {
                return arquivoAudio.getOriginalFilename(); 
            }
        };
        bodyBuilder.part("file", recursoArquivo, MediaType.parseMediaType(arquivoAudio.getContentType()));
        bodyBuilder.part("model", "whisper-large-v3");
        bodyBuilder.part("language", "pt");

        MultiValueMap<String, ?> body = bodyBuilder.build();
        
        WhisperResponse resposta = restClient.post()
                .uri("/audio/transcriptions")
                .header("Authorization", "Bearer " + apiKey)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(body)
                .retrieve()
                .body(WhisperResponse.class);

        return resposta != null ? resposta.getText() : "Não foi possível transcrever o áudio.";
}
}