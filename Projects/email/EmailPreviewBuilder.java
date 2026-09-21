package Projects.email;

/**
 * Builder 2 создает текстовую репрезентацию (Preview Sheet).
 */
public class EmailPreviewBuilder implements EmailBuilder {
    private String recipient, subject, body, attachment;

    @Override
    public EmailPreviewBuilder setRecipient(String r) { this.recipient = r; return this; }
    
    @Override
    public EmailPreviewBuilder setSubject(String s)   { this.subject = s; return this; }
    
    @Override
    public EmailPreviewBuilder setBody(String b)      { this.body = b; return this; }
    
    @Override
    public EmailPreviewBuilder setAttachment(String a){ this.attachment = a; return this; }

    public String getResult() {
        if (recipient == null || recipient.isBlank()) {
            throw new IllegalStateException("Recipient missing!");
        }
        
        String attachText = (attachment != null) ? attachment : "None";
        
        return "=== PREVIEW ===\n" +
               "TO: " + recipient + "\n" +
               "SUBJECT: " + subject + "\n" +
               "ATTACHMENT: " + attachText + "\n" +
               "BODY: " + body;
    }
}