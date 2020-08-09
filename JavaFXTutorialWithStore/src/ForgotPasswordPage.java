import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.mail.Session;
import javax.mail.Transport;

public class ForgotPasswordPage {
    static Stage window;

    static TextInputDialog tid_emailConf, tid_newPassword;
    //static

    public static void display(HashMap<String, Person> users)
    {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Forgot Password");
        window.setWidth(300);
        window.setHeight(200);

        Label lb_userName = new Label("Username: ");
        Label lb_email = new Label("Email: ");
        lb_userName.setPrefSize(100,25);
        lb_email.setPrefSize(100,25);


        TextField tf_userName = new TextField();
        tf_userName.setPromptText("username");

        TextField tf_email = new TextField();
        tf_email.setPromptText("email");

        tf_email.setPrefSize(100,25);
        tf_userName.setPrefSize(100,25);

        Button btn_Next = new Button("Next");
        btn_Next.setPrefSize(125, 25);
        btn_Next.setAlignment(Pos.CENTER);

        HBox hb_userName = new HBox();
        hb_userName.getChildren().addAll(lb_userName, tf_userName);
        hb_userName.setSpacing(10);
        hb_userName.setAlignment(Pos.CENTER);

        HBox hb_email = new HBox();
        hb_email.getChildren().addAll(lb_email, tf_email);
        hb_email.setSpacing(10);
        hb_email.setAlignment(Pos.CENTER);

        VBox vb_forgot1 = new VBox();
        vb_forgot1.getChildren().addAll(hb_userName,hb_email, btn_Next);
        vb_forgot1.setSpacing(20);
        vb_forgot1.setAlignment(Pos.CENTER);

        btn_Next.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(users!=null&&!users.isEmpty()&&users.containsKey(tf_userName.getText())&&users.get(tf_userName.getText()).getEmail().equals(tf_email.getText()))
                {

                    //https://kodejava.org/how-do-i-validate-email-address-using-regular-expression/ was used for regex String

                    String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

                    if(!tf_email.getText().matches(regex))
                    {
                        new Alert(Alert.AlertType.ERROR, "Incorrect Email Format.").showAndWait();
                    }
                    else
                    {
                        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz !@#$%^&*() 1234567890";

                        StringBuilder pass = new StringBuilder();

                        for (int i = 0; i < 10; i++) {
                            pass.append(chars.charAt((int)(Math.random()*74)));
                        }

                        //https://www.geeksforgeeks.org/send-email-using-java-program/ was used for messaging NOTE: email was used in different places, but all are from this

                        String recipient = tf_email.getText();

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

                            message.setSubject("COVID Application Password Reset Code");

                            message.setText("Your password reset code is: "+pass.toString());

                            Transport.send(message);
                            System.out.println("Mail successfully sent");
                        }
                        catch (MessagingException m)
                        {
                            m.printStackTrace();
                        }

                        tid_emailConf = new TextInputDialog();
                        tid_emailConf.setTitle("Verify Email");
                        tid_emailConf.setHeaderText("Please Enter confirmation code sent to your email.");
                        tid_emailConf.setContentText("Enter confirmation code here: ");


                        Optional<String> result = tid_emailConf.showAndWait();
                        if(result.isPresent()&&result.get().equals(pass.toString())) {
                            System.out.println("correct");
                            tid_emailConf.setResult("");
                            tid_emailConf.close();

                            tid_newPassword = new TextInputDialog();
                            tid_newPassword.setTitle("New Password");
                            tid_newPassword.setHeaderText("Please Enter new Password");
                            tid_newPassword.setContentText("New Password: ");
                            Optional<String> newPass = tid_newPassword.showAndWait();

                            if(newPass.isPresent())
                            {
                                System.out.println("inside");
                                users.get(tf_userName.getText()).setPassword(newPass.get());
                                tid_newPassword.close();
                                tid_emailConf.setResult("");
                                tid_emailConf.close();
                                window.close();
                            }
                            else
                            {
                                System.out.println("incorrect pass check");
                                pass = new StringBuilder();
                                tid_newPassword.setResult("");
                                tid_newPassword.close();
                                tid_emailConf.setResult("");
                                tid_emailConf.close();
                                window.close();
                            }

                        }
                        else
                        {
                            System.out.println("incorrect pass check");
                            pass = new StringBuilder();
                            tid_emailConf.setResult("");
                            tid_emailConf.close();
                            window.close();
                        }
                    }
                }
                else
                {
                    new Alert(Alert.AlertType.ERROR, "Account not registered").showAndWait();
                }
            }
        });

        Scene sc_forgot1 = new Scene(vb_forgot1, 300, 200);
        window.setScene(sc_forgot1);
        window.showAndWait();

    }
}
