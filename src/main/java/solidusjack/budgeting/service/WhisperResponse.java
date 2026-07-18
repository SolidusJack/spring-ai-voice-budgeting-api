package solidusjack.budgeting.service;

public class WhisperResponse {
    
    private String text;

    public WhisperResponse() {}

    public WhisperResponse(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}