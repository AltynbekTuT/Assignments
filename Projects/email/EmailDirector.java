package Projects.email;

public class EmailDirector {
    public void makeWelcomeEmail(EmailBuilder b, String email, String name) {
        b.setRecipient(email).setSubject("Welcome!").setBody("Hello " + name + ", welcome aboard!");
    }

    public void makeResetEmail(EmailBuilder b, String email, String token) {
        b.setRecipient(email).setSubject("Reset Password").setBody("Token: " + token).setAttachment("notice.pdf");
    }
}