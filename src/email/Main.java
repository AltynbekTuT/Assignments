package src.email;

public class Main {
    public static void main(String[] args) {
        EmailDirector director = new EmailDirector();

        EmailObjectBuilder objBuilder = new EmailObjectBuilder();
        director.makeWelcomeEmail(objBuilder, "user@test.com", "Altynbek");
        Email emailObj = objBuilder.getResult();

        EmailPreviewBuilder previewBuilder = new EmailPreviewBuilder();
        director.makeWelcomeEmail(previewBuilder, "user@test.com", "Altynbek");
        String textPreview = previewBuilder.getResult();

        System.out.println(emailObj);
        System.out.println(textPreview);
    }
}