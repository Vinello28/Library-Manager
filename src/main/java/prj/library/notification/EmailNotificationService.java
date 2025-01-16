package prj.library.notification;


import javax.mail.Session;
import prj.library.database.DAO.LendsDAO;
import prj.library.models.Lends;
import prj.library.utils.CLIUtils;
import prj.library.utils.ConfigLoader;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.*;
import javax.mail.PasswordAuthentication;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

/**
 * Service to send email notifications to customers
 */
public class EmailNotificationService {
    private final String username; //email address
    private final String password; //email password
    private final String smtpHost; //smtp host
    private final String smtpPort; //smtp port
    private final LendsDAO lendDAO; //DAO to access the database

    /**
     * Constructor
     * loads the email configuration from the properties file
     * @param lendDAO the DAO to access the database
     */
    public EmailNotificationService(LendsDAO lendDAO) {
        this.lendDAO = lendDAO;
        this.username = ConfigLoader.getProperty("email.username");
        this.password = ConfigLoader.getProperty("email.password");
        this.smtpHost = ConfigLoader.getProperty("email.smtp.host");
        this.smtpPort = ConfigLoader.getProperty("email.smtp.port");
    }

    /**
     * Create a new session to send emails
     * @return the session
     */
    private Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true"); //enable smtp authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable tls
        props.put("mail.smtp.host", smtpHost); //set smtp host
        props.put("mail.smtp.port", smtpPort); //set smtp port
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com"); //trust the smtp host


        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password); //authenticate the email
            }
        });
    }

    /**
     * Send an email notification to a customer
     * @param toEmail the email address of the customer
     * @param customerName the name of the customer
     * @param bookTitle the title of the book
     * @param dueDate the due date of the book
     */
    public void sendExpirationNotification(String toEmail, String customerName, String bookTitle, LocalDate dueDate) {
        try {
            Session session = createSession(); //create a new session
            Message message = new MimeMessage(session); //create a new message
            message.setFrom(new InternetAddress(username)); //set the sender
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail)); //set the recipient
            message.setSubject("Book return reminder"); //set the subject

            String content = String.format(
                    "Hi %s !,\n\n" +
                            "We remind you that your book's lend \"%s\" ended on %s.\n" +
                            "Please return the book as soon as possible.\n\n" +
                            "Have a nice day!,\nThe Library Team",
                    customerName, bookTitle, dueDate);

            message.setText(content);
            Transport.send(message);

        } catch (MessagingException e) {
            CLIUtils.serverCriticalError("Mail notification sending error: " + e.getMessage());
        }
    }

    /**
     * Check for expiring loans and send notifications
     */
    public void checkAndSendNotifications() {
        List<VirtualLend> expiringLends = lendDAO.getLateLendsNotification();

        for (VirtualLend lend : expiringLends) {
            sendExpirationNotification(
                    lend.getCustomerEmail(),
                    lend.getCustomerName(),
                    lend.getBookTitle(),
                    lend.getReturnDate()
            );
        }
    }
}
