/* Generated by Together */
/**
 * Pre-requered : - mail.jar - activation.jar
 */
package com.dimata.util.net;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.PasswordAuthentication;
import java.util.Vector;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import sun.misc.BASE64Encoder;

public class MailSender {

    /**
     * @return the vMailProcess
     */
    public static Vector<MailProcess> getvMailProcess() {
        return vMailProcess;
    }

    public static MailProcess getvMailProcess(long oid) {
        if (vMailProcess != null && vMailProcess.size() > 0) {
            for (int i = 0; i < vMailProcess.size(); i++) {
                MailProcess mp = vMailProcess.get(i);
                if (mp.getOID() == oid) {
                    return mp;
                }
            }
        }
        return null;
    }

    private String smtpHost = "";

    private String fromMail = "";
    private String fromName = "";
    boolean isMessageHTML=true ;
    private String to = "";

    private String subject = "";
    private String message = "";

    private static Vector<MailProcess> vMailProcess = new Vector(); // add by kartika : 2015-09-10 : to enable get of email status

    public MailSender() {
    }

    public void setSMTPHost(String host) {
        smtpHost = host;
    }

    public void setSenderAddr(String snd) {
        fromMail = snd;
    }

    public void setSender(String snd) {
        fromName = snd;
    }

    public void setReceiver(String rcv) {
        to = rcv;
    }

    public void setSubject(String sub) {
        subject = sub;
    }

    public void setMessage(String msg) {
        message = msg;
    }

    public void sendMail() throws Exception {

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", smtpHost);
        Session ses = Session.getInstance(properties, null);

        MimeMessage mmsg = new MimeMessage(ses);

        // create address fromName
        InternetAddress fromAddr = null;
        if (fromMail == null || fromMail.length() == 0) {
            fromAddr = new InternetAddress(fromName);
        } else {
            fromAddr = new InternetAddress(fromMail, fromName);
        }
        mmsg.setFrom(fromAddr);

        // create address to
        InternetAddress toAddr = new InternetAddress(to);
        mmsg.setRecipient(Message.RecipientType.TO, toAddr);

        mmsg.setSubject(subject);
        mmsg.setText(message);
        //update by satrya 2013-10-30
        mmsg.setContent(message, "text/html; charset=utf-8");

        Transport.send(mmsg);

    }

    /**
     * prosess encoding data image menjadi binary create by satrya 2013-11-04
     *
     * @param image
     * @param type
     * @return
     */
    public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }

    public static String[] toArray(Vector<String> vData) {
        String[] aString = (vData != null && vData.size() > 0) ? new String[vData.size()] : null;
        if (vData != null && vData.size() > 0) {
            for (int idx = 0; idx < vData.size(); idx++) {
                aString[idx] = vData.get(idx);
            }
        }
        return aString;
    }
    // Fungsi non attach data 
    public void postMail(Vector<String> recTo, Vector<String> recCC,
            Vector<String> recBCC, String subject, String txtMessage, String from,
            String host, int port, String username, String password, boolean SSL, String attachment, boolean configEmailWithImage) throws MessagingException {
        /**
         * real setting  access by leave *
         */

         String recipientsTo[] = recTo != null ? new String[recTo.size()] : null;
        if (recTo != null && recipientsTo != null) {
            recTo.toArray(recipientsTo);
        };
        String recipientsCC[] = recCC != null ? new String[recCC.size()] : null;
        if (recCC != null && recipientsCC != null) {
            recCC.toArray(recipientsCC);
        }
        String recipientsBCC[] = recBCC != null ? new String[recBCC.size()] : null;
        if (recBCC != null && recipientsBCC != null) {
            recBCC.toArray(recipientsBCC);
        }
        
        /**
         * for testing *
         */
        /**
         * String host = "aragorn.webappcabaret.net"; int port = 110; String
         * username = "marketing@dimata.com"; String password = "golden2u";
         *
         */
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.port", "" + port);
        Authenticator auth = new Authenticator(username, password);
        props.setProperty("mail.smtp.submitter", auth.getPasswordAuthentication().getUserName());

        if (SSL) {
            props.put("mail.smtp.socketFactory.port", port);
            props.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
        }

        Session session = Session.getInstance(props, auth);

        try {
            Message message = new MimeMessage(session);
            InternetAddress addressFrom = new InternetAddress(from);

            message.setFrom(addressFrom);
            if ((recipientsTo == null || recipientsTo.length < 1) && (recipientsCC == null || recipientsCC.length < 1) && (recipientsBCC == null || recipientsBCC.length < 1)) {
                throw new MessagingException("There is no email address to sent to");
            }

            if (recipientsTo != null && recipientsTo.length > 0) {
                InternetAddress[] addressTo = new InternetAddress[recipientsTo.length];
                for (int i = 0; i < recipientsTo.length; i++) {
                    addressTo[i] = new InternetAddress(recipientsTo[i]);
                }
                message.setRecipients(Message.RecipientType.TO, addressTo);
            }

            if (recipientsCC != null && recipientsCC.length > 0) {
                InternetAddress[] addressCC = new InternetAddress[recipientsCC.length];
                for (int i = 0; i < recipientsCC.length; i++) {
                    addressCC[i] = new InternetAddress(recipientsCC[i]);
                }
                message.setRecipients(Message.RecipientType.CC, addressCC);
            }

            if (recipientsBCC != null && recipientsBCC.length > 0) {
                InternetAddress[] addressBCC = new InternetAddress[recipientsBCC.length];
                for (int i = 0; i < recipientsBCC.length; i++) {
                    addressBCC[i] = new InternetAddress(recipientsBCC[i]);
                }
                message.setRecipients(Message.RecipientType.BCC, addressBCC);
            }

            //update by satrya 2013-10-30
            //update by satrya 2013-11-04
            if (configEmailWithImage && attachment != null && attachment.length() > 0) {
                BodyPart messageBodyPart = new MimeBodyPart();
                Multipart multipart = new MimeMultipart();

                messageBodyPart.setContent(txtMessage, "text/html; charset=utf-8");

                BodyPart imgPart = new MimeBodyPart();
                DataSource source = new FileDataSource(attachment);

                imgPart.setDataHandler(new DataHandler(source));
                imgPart.setHeader("Content-ID", "<logoimg>");
                imgPart.setFileName(source.getName());
                multipart.addBodyPart(imgPart);

                multipart.addBodyPart(messageBodyPart);

                message.setContent(multipart);
                //untuk membuat attment
                /*messageBodyPart = new MimeBodyPart();
                 DataSource source = new FileDataSource(attachment);
                 messageBodyPart.setDataHandler(new DataHandler(source));
                 messageBodyPart.setFileName(attachment);
                 multipart.addBodyPart(messageBodyPart);
                 // sementara di hidden karena KTI tidak ingin di tampilkan
                 message.setContent(txtMessage, "text/html; charset=utf-8");*/

            } else {
                message.setText(txtMessage);
            }

            // message.setRecipients(Message.RecipientType.BCC, addressBCC);
            message.setSubject(subject);

            //update by satrya 2013-11-04
            //message.setContent(multipart);
            //update by satrya 2013-11-14
            //message.setText(txtMessage);
            Transport transport = session.getTransport("smtp");
            System.out.println("Connect to email server : " + host + ":" + port + " user:" + username);
            transport.connect(host, port, username, password);

            Transport.send(message);
            //System.out.println("Send message to "+addressTo[0]+" "+ txtMessage);

            //System.out.println("Done");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void postMail(Vector<String> recTo, Vector<String> recCC,
            Vector<String> recBCC, String subject, String txtMessage, String from,
            String host, int port, String username, String password, boolean SSL, Vector<DataSource> attachment, boolean configEmailWithImage) throws MessagingException {

        postMail(recTo, recCC, recBCC, subject, txtMessage, from, host, port, username, password, SSL, attachment, null, configEmailWithImage);

    }

    public void postMail(Vector<String> recTo, Vector<String> recCC,
            Vector<String> recBCC, String subject, String txtMessage, String from,
            String host, int port, String username, String password, boolean SSL, Vector<DataSource> attachment, Vector<String> attachmentName, boolean configEmailWithImage) throws MessagingException {
        /**
         * real setting fungsi yang di akses Payroll *
         */

        String recipientsTo[] = recTo != null ? new String[recTo.size()] : null;
        if (recTo != null && recipientsTo != null) {
            recTo.toArray(recipientsTo);
        };
        String recipientsCC[] = recCC != null ? new String[recCC.size()] : null;
        if (recCC != null && recipientsCC != null) {
            recCC.toArray(recipientsCC);
        }
        String recipientsBCC[] = recBCC != null ? new String[recBCC.size()] : null;
        if (recBCC != null && recipientsBCC != null) {
            recBCC.toArray(recipientsBCC);
        }

        /**
         * for testing *
         */
        /**
         * String host = "aragorn.webappcabaret.net"; int port = 110; String
         * username = "marketing@dimata.com"; String password = "golden2u";
         *
         */
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.port", "" + port); 
        props.put("mail.smtp.ssl.trust", host);
        Authenticator auth = new Authenticator(username, password);
        props.setProperty("mail.smtp.submitter", auth.getPasswordAuthentication().getUserName());
        if (SSL) {
            props.put("mail.smtp.socketFactory.port", port);
            props.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
        }

        Session session = Session.getInstance(props, auth);

        try {
            Message message = new MimeMessage(session);
            InternetAddress addressFrom = new InternetAddress(from);
            
            
            message.setFrom(addressFrom);
            if (recipientsTo != null && recipientsTo.length > 0) {
                InternetAddress[] addressTo = new InternetAddress[recipientsTo.length];
                for (int i = 0; i < recipientsTo.length; i++) {
                    addressTo[i] = new InternetAddress(recipientsTo[i]);
                }
                message.setRecipients(Message.RecipientType.TO, addressTo);
            }

            if (recipientsCC != null && recipientsCC.length > 0) {
                InternetAddress[] addressCC = new InternetAddress[recipientsCC.length];
                for (int i = 0; i < recipientsCC.length; i++) {
                    addressCC[i] = new InternetAddress(recipientsCC[i]);
                }
                message.setRecipients(Message.RecipientType.CC, addressCC);
            }
            
            if (recipientsBCC != null && recipientsBCC.length > 0) {
                InternetAddress[] addressBCC = new InternetAddress[recipientsBCC.length];
                for (int i = 0; i < recipientsBCC.length; i++) {
                    addressBCC[i] = new InternetAddress(recipientsBCC[i]);
                }
                message.setRecipients(Message.RecipientType.BCC, addressBCC);
            }
            //update by satrya 2013-10-30
            //update by satrya 2013-11-04
            if (configEmailWithImage && attachment != null && attachment.size() > 0) {
                BodyPart messageBodyPart = new MimeBodyPart();
                Multipart multipart = new MimeMultipart();

                messageBodyPart.setContent(txtMessage, "text/html; charset=utf-8");

                BodyPart imgPart = new MimeBodyPart();
                if (attachment != null && attachment.size() > 0) {
                    for (int idx = 0; idx < attachment.size(); idx++) {
                        DataSource source = (DataSource) attachment.get(idx);
                        imgPart.setDataHandler(new DataHandler(source));
                        imgPart.setHeader("Content-ID", "<logoimg>");
                        if (attachmentName != null && attachmentName.size() > 0 && attachmentName.size() > idx) {
                            imgPart.setFileName(attachmentName.get(idx));
                        } else {
                            imgPart.setFileName(source.getName());
                        }
                        multipart.addBodyPart(imgPart);
                    }
                }
                multipart.addBodyPart(messageBodyPart);

                message.setContent(multipart);
                //untuk membuat attment
                /*messageBodyPart = new MimeBodyPart();
                 DataSource source = new FileDataSource(attachment);
                 messageBodyPart.setDataHandler(new DataHandler(source));
                 messageBodyPart.setFileName(attachment);
                 multipart.addBodyPart(messageBodyPart);
                 // sementara di hidden karena KTI tidak ingin di tampilkan
                 message.setContent(txtMessage, "text/html; charset=utf-8");*/

            } else {
               if( isMessageHTML )
                   message.setContent(txtMessage, "text/html; charset=utf-8");
               else 
                   message.setText(txtMessage);
            }

            
            message.setSubject(subject);

            //update by satrya 2013-11-04
            //message.setContent(multipart);
            //update by satrya 2013-11-14
            //message.setText(txtMessage);
            Transport transport = session.getTransport("smtp");
            System.out.println("Connect to email server : " + host + ":" + port + " user:" + username);
            transport.connect(host, port, username, password);

            Transport.send(message);
            //System.out.println("Send message to "+addressTo[0]+" "+ txtMessage);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private class Authenticator extends javax.mail.Authenticator {

        private PasswordAuthentication authentication;

        public Authenticator(String username, String password) {
            authentication = new PasswordAuthentication(username, password);
        }

        protected PasswordAuthentication getPasswordAuthentication() {
            return authentication;
        }
    }

    public static void postMailThread(Vector<String> recTo, Vector<String> recCC,
            Vector<String> recBCC, String subject, String txtMessage, String from,
            String host, int port, String username, String password, boolean SSL, String attacment, boolean configViewEmail) {
        try {
            MailProcess mp = new MailProcess(recTo, recCC, recBCC, subject, txtMessage, from, host, port, username, password, SSL, attacment, configViewEmail);
            getvMailProcess().add(mp);
            Thread thr = new Thread(mp);
            thr.setDaemon(false);
            thr.start();
        } catch (Exception exc) {
            System.out.println(exc);
        }
    }

    public static void postMailThread(Vector<String> recTo, Vector<String> recCC,
            Vector<String> recBCC, String subject, String txtMessage, String from,
            String host, int port, String username, String password, boolean SSL, Vector<DataSource> attacment, Vector<String> attacmentName, boolean configViewEmail) {
        try {
            MailProcess mp = new MailProcess(recTo, recCC, recBCC, subject, txtMessage, from, host, port, username, password, SSL, attacment, attacmentName, configViewEmail);
            getvMailProcess().add(mp);
            Thread thr = new Thread(mp);
            thr.setDaemon(false);
            thr.start();
        } catch (Exception exc) {
            System.out.println(exc);
        }
    }

} // end of MailSender
