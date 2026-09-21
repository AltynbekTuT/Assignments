
/*
 * Интерфейс Строителя 
 */
public interface EmailBuilder {
    EmailBuilder setRecipient(String recipient);
    EmailBuilder setSubject(String subject);
    EmailBuilder setBody(String body);
    EmailBuilder setAttachment(String attachment);
}
