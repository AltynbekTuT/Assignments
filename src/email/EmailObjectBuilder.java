package src.email;

/**
 * Builder 1 создает Java-объект Email
 */
public class EmailObjectBuilder implements EmailBuilder {
    private String recipient, subject, body, attachment;

    public EmailObjectBuilder setRecipient(String r) { this.recipient = r; return this; }
    public EmailObjectBuilder setSubject(String s)   { this.subject = s; return this; }
    public EmailObjectBuilder setBody(String b)      { this.body = b; return this; }
    public EmailObjectBuilder setAttachment(String a){ this.attachment = a; return this; }

    public Email getResult() {
        if (recipient == null || subject == null) 
            throw new IllegalStateException("Recipient and Subject are required!");
        return new Email(recipient, subject, body, attachment);
    }
}