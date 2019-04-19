package common;

import api.MessageSender;
import impl.Email;
import impl.Fax;
import impl.MMS;
import impl.SMS;
import impl.SkypeVideo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class TestSender {

    private MessageSender sender = new DummyMessageSenderImpl();

    @Test
    public void testPreparedEmail() {
        Email actualEmail = prepareEmail();
        sender.send(actualEmail);

        Email expectedEmail = prepareEmail();
        assertEquals(expectedEmail.getType(), MessageType.EMAIL);
        assertEquals(expectedEmail.getRecipients(), actualEmail.getRecipients());
        assertEquals(expectedEmail.getSubject(), actualEmail.getSubject());
        assertEquals(expectedEmail.getMessageBody(), actualEmail.getMessageBody());
        assertEquals(expectedEmail.getCcRecipients(), actualEmail.getCcRecipients());
        assertEquals(expectedEmail.getBccRecipients(), actualEmail.getBccRecipients());
        assertEquals(expectedEmail, actualEmail);
        assertEquals(expectedEmail.hashCode(), actualEmail.hashCode());
    }

    @Test
    public void testPreparedSms() {
        SMS actualSms = prepareSms();
        sender.send(actualSms);

        SMS expectedSms = prepareSms();
        assertEquals(expectedSms.getType(), MessageType.SMS);
        assertEquals(expectedSms.getRecipients(), actualSms.getRecipients());
        assertEquals(expectedSms.getMessageBody(), actualSms.getMessageBody());
        assertEquals(expectedSms, actualSms);
        assertEquals(expectedSms.hashCode(), actualSms.hashCode());
    }

    @Test
    public void testPreparedMms() {
        MMS mms = prepareMms();
        sender.send(mms);

        MMS expectedMms = prepareMms();
        assertEquals(expectedMms.getType(), MessageType.MMS);
        assertEquals(expectedMms.getRecipients(), expectedMms.getRecipients());
        assertEquals(expectedMms.getMessageBody(), expectedMms.getMessageBody());
        assertEquals(expectedMms.getAttachments(), expectedMms.getAttachments());
        assertEquals(mms, prepareMms());
        assertEquals(mms.hashCode(), expectedMms.hashCode());
    }

    @Test
    public void testPreparedFax() {
        Fax actualFax = prepareFax();
        sender.send(actualFax);

        Fax expectedFax = prepareFax();
        assertEquals(expectedFax.getType(), MessageType.FAX);
        assertEquals(expectedFax.getRecipients(), actualFax.getRecipients());
        assertEquals(expectedFax.getCallbackFax(), actualFax.getCallbackFax());
        assertEquals(expectedFax.getCompanyName(), actualFax.getCompanyName());
        assertEquals(expectedFax.getSubject(), actualFax.getSubject());
        assertEquals(expectedFax.getMessageBody(), actualFax.getMessageBody());
        assertEquals(expectedFax, actualFax);
        assertEquals(expectedFax.hashCode(), actualFax.hashCode());
    }

    @Test
    public void testPreparedSkypeVideo() {
        SkypeVideo actualSkypeVideo = prepareSkypeVideo();
        sender.send(actualSkypeVideo);

        SkypeVideo expectedSkypeVideo = prepareSkypeVideo();
        assertEquals(expectedSkypeVideo.getType(), MessageType.VIDEO);
        assertEquals(expectedSkypeVideo.getRecipients(), actualSkypeVideo.getRecipients());
        assertEquals(expectedSkypeVideo.getMessageBody(), actualSkypeVideo.getMessageBody());
        assertEquals(expectedSkypeVideo.getAttachments(), actualSkypeVideo.getAttachments());
        assertEquals(expectedSkypeVideo, actualSkypeVideo);
        assertEquals(expectedSkypeVideo.hashCode(), actualSkypeVideo.hashCode());
    }

    private Email prepareEmail() {
        Email email = new Email("John_Doe@gmail.com");
        email.setSubject("Email subject");
        email.setMessageBody("Email body");
        List<Attachment> attachments = new ArrayList();
        attachments.add(new Attachment("Salary.xls"));
        attachments.add(new Attachment("Surcharge.xls"));
        email.setAttachments(attachments);
        email.getAttachments().add(new Attachment("Employee.xml"));

        List<String> ccRecipients = new ArrayList<>();
        ccRecipients.add("Donald_West@gmail.com");
        ccRecipients.add("Maxim_Kenneth@gmail.com");
        email.setCcRecipients(ccRecipients);
        email.getCcRecipients().add("Mark_Lourense@gmail.com");

        List<String> bccRecipients = new ArrayList<>();
        bccRecipients.add("Andrey_Ruth@gmail.com");
        bccRecipients.add("Christopher_Docker@gmail.com");
        email.setBccRecipients(bccRecipients);
        email.getBccRecipients().add("Linda_Wastley@gmail.com");
        return email;
    }

    private SMS prepareSms() {
        SMS sms = new SMS("(870) 105-7790");
        sms.setMessageBody("Sms body");
        return sms;
    }

    private MMS prepareMms() {
        MMS mms = new MMS("(809) 401-7151");
        mms.setMessageBody("Mms body");

        List<Attachment> attachments = new ArrayList();
        attachments.add(new Attachment("Photo.png"));
        attachments.add(new Attachment("MyPhoto.png"));
        mms.setAttachments(attachments);
        mms.getAttachments().add(new Attachment("Selfie.png"));
        return mms;
    }

    private Fax prepareFax() {
        Fax fax = new Fax("860-509-7295");
        fax.setCompanyName("EPAM");
        fax.setCallbackFax("904-345-0915");
        fax.setSubject("Fax subject");
        fax.setMessageBody("Fax impl body");
        return fax;
    }

    private SkypeVideo prepareSkypeVideo() {
        SkypeVideo video = new SkypeVideo("skype.bot");
        video.setMessageBody("Video body");

        List<Attachment> attachments = new ArrayList();
        attachments.add(new Attachment("MyVideo.avi"));
        attachments.add(new Attachment("CamVideo.avi"));
        video.setAttachments(attachments);
        video.getAttachments().add(new Attachment("WebVideo.avi"));
        return video;
    }
}
