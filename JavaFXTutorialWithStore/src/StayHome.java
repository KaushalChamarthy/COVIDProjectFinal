import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.mail.Session;
import javax.mail.Transport;

public class StayHome {

    static Stage window;

    public static void display(ArrayList<String> symptoms)
    {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Stay Home");
        window.setWidth(600);
        window.setHeight(500);

        ImageView blueGrad = new ImageView(new Image("File:images/blueGrad.jpg"));
        blueGrad.fitWidthProperty().bind(window.widthProperty());
        blueGrad.fitHeightProperty().bind(window.heightProperty());

        Label lb_sent1 = new Label("Personally, we recommend that you stay at home and follow all safety guidelines");
        Label lb_sent2 = new Label("in your area. If you develop further symptoms, please take this survey again later.");
        Label lb_sent3 = new Label("If you would like to contact your doctor, you may also do so by clicking contact doctor.");

        lb_sent1.setAlignment(Pos.CENTER);
        lb_sent2.setAlignment(Pos.CENTER);
        lb_sent3.setAlignment(Pos.CENTER);


        Label lb_doctorEmail = new Label("Doctor Email");

        TextField tf_doctorEmail = new TextField();
        tf_doctorEmail.setPromptText("doctor email");

        TextArea ta_TextArea = new TextArea();
        ta_TextArea.setPromptText("Enter message here");
        ta_TextArea.setPrefSize(500,100);

        CheckBox cb_AttachCond = new CheckBox("Attach Conditions");
        cb_AttachCond.setAlignment(Pos.CENTER);

        Button btn_contactDoctor = new Button("Send Message");
        btn_contactDoctor.setAlignment(Pos.CENTER);

        HBox hb_doctor = new HBox();
        hb_doctor.setSpacing(50);
        hb_doctor.getChildren().addAll(lb_doctorEmail,tf_doctorEmail);
        hb_doctor.setAlignment(Pos.CENTER);

        VBox vb_screen = new VBox();
        vb_screen.setSpacing(50);
        vb_screen.getChildren().addAll(lb_sent1,lb_sent2,lb_sent3,hb_doctor,ta_TextArea,cb_AttachCond,btn_contactDoctor);
        vb_screen.setAlignment(Pos.CENTER);

        StackPane sp_overall = new StackPane();
        sp_overall.getChildren().addAll(blueGrad,vb_screen);

        Scene sc_show = new Scene(sp_overall, 600,500);
        window.setScene(sc_show);
        window.showAndWait();

        btn_contactDoctor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
                if(!tf_doctorEmail.getText().matches(regex))
                {
                    new Alert(Alert.AlertType.ERROR, "Incorrect Email Format.").showAndWait();
                }
                else
                {
                    if(cb_AttachCond.isSelected())
                    {
                        String recipient = tf_doctorEmail.getText();

                        // email ID of  Sender.
                        String sender = "runtimeterrorhack@gmail.com";

                        // Getting system properties
                        Properties properties = new Properties();

                        // Setting up mail server
                        properties.put("mail.smtp.auth", "true");
                        properties.put("mail.smtp.starttls.enable", "true");
                        properties.put("mail.smtp.host", "smtp.gmail.com");
                        properties.put("mail.smtp.port", 587);

                        // creating session object to get properties
                        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(sender, "hackterror");
                            }
                        });

                        try
                        {
                            MimeMessage message = new MimeMessage(session);

                            message.setFrom(new InternetAddress(sender));

                            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

                            message.setSubject("COVID Application Patient Message");


                            StringBuilder sypmtomsText = new StringBuilder("According to COVID Application, Symptoms also include: ");

                            for (int i = 0; i < symptoms.size(); i++) {
                                if(i<symptoms.size()-1)
                                    sypmtomsText.append(symptoms.get(i)+", ");
                                else
                                    sypmtomsText.append(symptoms.get(i)+".");
                            }

                            message.setText(ta_TextArea.getText()+"\n\n"+sypmtomsText.toString());


                            Transport.send(message);
                            System.out.println("Mail successfully sent");
                        }
                        catch (MessagingException m)
                        {
                            m.printStackTrace();
                            new Alert(Alert.AlertType.ERROR, "Mail failed to send.").showAndWait();
                        }
                    }
                    else
                    {
                        String recipient = tf_doctorEmail.getText();

                        // email ID of  Sender.
                        String sender = "runtimeterrorhack@gmail.com";

                        // Getting system properties
                        Properties properties = new Properties();

                        // Setting up mail server
                        properties.put("mail.smtp.auth", "true");
                        properties.put("mail.smtp.starttls.enable", "true");
                        properties.put("mail.smtp.host", "smtp.gmail.com");
                        properties.put("mail.smtp.port", 587);

                        // creating session object to get properties
                        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(sender, "hackterror");
                            }
                        });

                        try
                        {
                            MimeMessage message = new MimeMessage(session);

                            message.setFrom(new InternetAddress(sender));

                            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

                            message.setSubject("COVID Application Patient Message");

                            message.setText(ta_TextArea.getText());

                            Transport.send(message);
                            System.out.println("Mail successfully sent");
                        }
                        catch (MessagingException m)
                        {
                            m.printStackTrace();
                            new Alert(Alert.AlertType.ERROR, "Mail failed to send.").showAndWait();
                        }
                    }
                }
            }
        });

    }
}
