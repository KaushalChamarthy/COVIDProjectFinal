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

public class SignUpPage {

    static Stage window;

    static String firstName, lastName, email, userName, passWord;

    static TextField tf_firstName, tf_lastName, tf_email, tf_userName, tf_passWord;

    static TextField tf_address;

    static String address;

    static TextInputDialog tid_emailConf;

    public static Person display(HashMap<String, Person> users)
    {
        window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Sign-Up Page");
        window.setWidth(400);
        window.setHeight(400);

        Label lb_firstName = new Label("First Name: ");
        tf_firstName = new TextField();
        tf_firstName.setPromptText("First Name");

        lb_firstName.setPrefSize(100,25);
        tf_firstName.setPrefSize(100, 25);

        Label lb_address = new Label("Address: ");
        tf_address = new TextField();
        tf_address.setPromptText("Address");

        lb_address.setPrefSize(100,25);
        tf_address.setPrefSize(100,25);


        Label lb_lastName = new Label("Last Name: ");
        tf_lastName = new TextField();
        tf_lastName.setPromptText("Last Name");

        lb_lastName.setPrefSize(100,25);
        tf_lastName.setPrefSize(100, 25);

        Label lb_email = new Label("Email: ");
        tf_email = new TextField();
        tf_email.setPromptText("Email");

        lb_email.setPrefSize(100,25);
        tf_email.setPrefSize(100, 25);

        Label lb_userName = new Label("Username: ");
        tf_userName = new TextField();
        tf_userName.setPromptText("Username");

        lb_userName.setPrefSize(100,25);
        tf_userName.setPrefSize(100, 25);

        Label lb_passWord = new Label("Password: ");
        tf_passWord = new TextField();
        tf_passWord.setPromptText("Password");

        lb_passWord.setPrefSize(100,25);
        tf_passWord.setPrefSize(100, 25);

        VBox vb_text = new VBox();
        vb_text.getChildren().addAll(lb_firstName,lb_lastName,lb_email,lb_userName,lb_passWord, lb_address);
        vb_text.setSpacing(20);

        VBox vb_info = new VBox();
        vb_info.getChildren().addAll(tf_firstName,tf_lastName,tf_email,tf_userName,tf_passWord, tf_address);
        vb_info.setSpacing(20);

        HBox hb_allFields = new HBox();
        hb_allFields.getChildren().addAll(vb_text,vb_info);
        hb_allFields.setSpacing(20);
        hb_allFields.setAlignment(Pos.CENTER);

        Button btn_signUp = new Button("Sign up");
        btn_signUp.setPrefSize(100,25);

        VBox vb_alert = new VBox();
        vb_alert.getChildren().addAll(hb_allFields,btn_signUp);
        vb_alert.setSpacing(20);
        vb_alert.setAlignment(Pos.CENTER);

        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        btn_signUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!tf_firstName.getText().isEmpty()&&!tf_lastName.getText().isEmpty()&&!tf_email.getText().isEmpty()&&!tf_userName.getText().isEmpty()&&!tf_passWord.getText().isEmpty()&&!tf_address.getText().isEmpty())
                {
                    String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
                    if(!tf_email.getText().matches(regex))
                    {
                        new Alert(Alert.AlertType.ERROR, "Incorrect Email Format.").showAndWait();
                    }
                    else if(users.containsKey(tf_userName.getText()))
                    {
                        new Alert(Alert.AlertType.ERROR, "UserName Already Taken.").showAndWait();
                    }
                    else if(!checkEmail(users, tf_email.getText()))
                    {
                        new Alert(Alert.AlertType.ERROR, "Email is already used.").showAndWait();
                    }
                    else
                    {

                        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz !@#$%^&*() 1234567890";

                        StringBuilder pass = new StringBuilder();

                        for (int i = 0; i < 10; i++) {
                            pass.append(chars.charAt((int)(Math.random()*74)));
                        }

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

                            message.setSubject("COVID Application Email Confirmation Code");

                            message.setText("Your confirmation code is: "+pass.toString());

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
                            firstName = tf_firstName.getText();
                            lastName = tf_lastName.getText();
                            email = tf_email.getText();
                            userName = tf_userName.getText();
                            passWord = tf_passWord.getText();
                            address = tf_address.getText();
                            window.close();
                        }
                        else
                        {
                            System.out.println("incorrect pass check");
                            pass = new StringBuilder();
                            tid_emailConf.setResult("");
                            tid_emailConf.close();
                        }
                        tid_emailConf.setOnCloseRequest(e -> {
                            tid_emailConf.setResult("");
                            tid_emailConf.close();
                        });
                    }
                }
                else
                {
                    System.out.println("incorrect");
                    new Alert(Alert.AlertType.ERROR, "Not all fields filled.").showAndWait();
                }
            }
        });

        Scene sc_signUp = new Scene(vb_alert, 400,400);
        window.setScene(sc_signUp);
        window.showAndWait();

        Person person = new Person(firstName, lastName, email, userName, passWord, address);

        firstName = "";
        lastName = "";
        email = "";
        userName = "";
        passWord = "";
        address = "";

        tf_firstName.setText("");
        tf_lastName.setText("");
        tf_email.setText("");
        tf_userName.setText("");
        tf_passWord.setText("");
        tf_address.setText("");

        return person;
    }

    public static void closeProgram()
    {
        if(Confirm.display("Close?", "Sure, you want to exit?")) {
            firstName = null;
            lastName = null;
            email = null;
            userName = null;
            passWord = null;
            address = null;
            tf_firstName.setText("");
            tf_lastName.setText("");
            tf_email.setText("");
            tf_userName.setText("");
            tf_passWord.setText("");
            tf_address.setText("");
            window.close();
        }

    }

    public static void closeConfirmation()
    {
        if(Confirm.display("Close?", "Sure, you want to exit?")) {
            tid_emailConf.setResult("");
            tid_emailConf.close();
        }
    }

    public static boolean checkEmail(HashMap<String, Person> users, String email)
    {
//        Iterator<Person> userItr = users.values().iterator();
//        while (userItr.hasNext()) {
//            if(userItr.next().getEmail().equals(email)) return false;
//        }

        if( users != null) {
            Iterator<String> itr = users.keySet().iterator();
            while (itr.hasNext()) {
                String key = itr.next();
                Person usr = users.get(key);
                //System.out.println(usr);
                if(usr.getEmail().equals(email)) return false;
            }
        }
        return true;
    }
}
