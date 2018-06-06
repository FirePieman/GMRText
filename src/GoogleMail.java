/*
 * Handles sending an email from whatever email account in order to text a player it is there turn.
 * Based on code from stack oveflow.
 */

import com.sun.mail.smtp.SMTPTransport;
import java.io.PrintStream;
import java.util.Date;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GoogleMail {
    private GoogleMail() {
    }

    public static void Send(String username, String password, String recipientEmail, String title, String message) throws AddressException, MessagingException {
        GoogleMail.Send(username, password, recipientEmail, "", title, message);
    }

    public static void Send(String username, String password, String recipientEmail, String ccEmail, String title, String message) throws AddressException, MessagingException {
        String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Properties props = System.getProperties();
        props.setProperty("mail.smtps.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtps.auth", "true");
        props.put("mail.smtps.quitwait", "false");
        Session session = Session.getInstance((Properties)props, (Authenticator)null);
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom((Address)new InternetAddress(String.valueOf(username) + "@gmail.com"));
        msg.setRecipients(Message.RecipientType.TO, (Address[])InternetAddress.parse((String)recipientEmail, (boolean)false));
        if (ccEmail.length() > 0) {
            msg.setRecipients(Message.RecipientType.CC, (Address[])InternetAddress.parse((String)ccEmail, (boolean)false));
        }
        msg.setSubject(title);
        msg.setText(message, "utf-8");
        msg.setSentDate(new Date());
        SMTPTransport t = (SMTPTransport)session.getTransport("smtps");
        System.out.println("stepb4connect");
        t.connect("smtp.gmail.com", username, password);
        System.out.println("stepa4connect");
        t.sendMessage((Message)msg, msg.getAllRecipients());
        t.close();
    }
}
