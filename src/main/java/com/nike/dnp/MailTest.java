package com.nike.dnp;

public class MailTest {

    static void main(String[] args) {

        System.out.println("Hello World");
    }


//
//    private static final Logger LOGGER = Logger.getAnonymousLogger();
//
//    private static final String SERVIDOR_SMTP = "smtp.office365.com";
//    private static final int PORTA_SERVIDOR_SMTP = 587;
//    private static final String CONTA_PADRAO = "mail@mail.com.br";
//    private static final String SENHA_CONTA_PADRAO = "password*";
//
//    private final String from = "mail@mail.com.br";
//    private final String to = "mail@mail.com";
//
//    private final String subject = "Teste";
//    private final String messageContent = "Teste de Mensagem";
//
//    public void sendEmail() {
//        final Session session = Session.getInstance(this.getEmailProperties(), new Authenticator() {
//
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(CONTA_PADRAO, SENHA_CONTA_PADRAO);
//            }
//
//        });
//
//        try {
//            final Message message = new MimeMessage(session);
//            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
//            message.setFrom(new InternetAddress(from));
//            message.setSubject(subject);
//            message.setText(messageContent);
//            message.setSentDate(new Date());
//            Transport.send(message);
//        } catch (final MessagingException ex) {
//            LOGGER.log(Level.WARNING, "Erro ao enviar mensagem: " + ex.getMessage(), ex);
//        }
//    }
//
//    public Properties getEmailProperties() {
//        final Properties config = new Properties();
//        config.put("mail.smtp.auth", "true");
//        config.put("mail.smtp.starttls.enable", "true");
//        config.put("mail.smtp.host", SERVIDOR_SMTP);
//        config.put("mail.smtp.port", PORTA_SERVIDOR_SMTP);
//        return config;
//    }
}
    