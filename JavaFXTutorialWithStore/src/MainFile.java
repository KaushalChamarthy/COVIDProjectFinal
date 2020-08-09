//V2
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.*;

public class MainFile extends Application {

    HashMap<String, Person> users = new HashMap<String, Person>();

    String curUserName;
    Scene startPage, homePage, sc_mindful, sc_playful, sc_symptomsInfoPage, sc_settings;

    String useAddress;

    double distance;

    int patientScore;

    static final int THRESHOLD = 150;

    boolean playing;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {


        //region Background Template
        //ImageView blueGrad [change here with number] = new ImageView(new Image("File:images/blueGrad.jpg"));
        //blueGrad.fitWidthProperty().bind(homePage.widthProperty());
        //blueGrad.fitHeightProperty().bind(homePage.heightProperty());  MAKE SURE TO CHANGE BINDING SCENE
        //endregion


        boolean rslt = this.readUsersFromFile();
        if(rslt) {
            System.out.println("users.json file read successfully");
        }
        else
        {
            System.out.println("failed to read users.json file...");
        }

        primaryStage.setTitle("COVID Application");
        StackPane startStack = new StackPane();


        //region StartPage Code
        ImageView homeBackground = new ImageView(new Image("File:images/home.jpg"));
        homeBackground.fitWidthProperty().bind(startStack.widthProperty());
        homeBackground.fitHeightProperty().bind(startStack.heightProperty());

        VBox vb_startScreen = new VBox();
        vb_startScreen.setSpacing(20);
        vb_startScreen.setAlignment(Pos.CENTER);

        Label lb_welcome = new Label("Welcome to COVID Application");
        lb_welcome.setMinWidth(200);
        lb_welcome.setAlignment(Pos.CENTER);

        TextField tf_userName = new TextField();
        tf_userName.setMaxWidth(200);
        tf_userName.setPromptText("username");
        tf_userName.setMaxHeight(25);

        TextField tf_password = new TextField();
        tf_password.setMaxWidth(200);
        tf_password.setPromptText("password");
        tf_password.setMaxHeight(25);

        HBox hb_startOptions = new HBox();
        Button btn_login = new Button("login");
        btn_login.setMaxHeight(25); btn_login.setMaxWidth(75);


        Button btn_signUp = new Button("signUp");
        btn_signUp.setMaxHeight(25); btn_signUp.setMaxWidth(75);

        hb_startOptions.getChildren().addAll(btn_login,btn_signUp);
        hb_startOptions.setSpacing(50);
        hb_startOptions.setAlignment(Pos.CENTER);

        Button btn_forgotPassword = new Button("Forgot Password");
        btn_forgotPassword.setPrefSize(125, 25);
        btn_forgotPassword.setAlignment(Pos.CENTER);


        vb_startScreen.getChildren().addAll(lb_welcome,tf_userName,tf_password,hb_startOptions, btn_forgotPassword);
        startStack.getChildren().addAll(homeBackground, vb_startScreen);


        startPage = new Scene(startStack, 700, 600);
        //endregion

        //region SideBar Home
        StackPane sp_mindFull = new StackPane();
        ImageView mind = new ImageView(new Image("File:images/mind.jpg"));
        Button btn_mind = new Button("Mindfulness Exercise");
        btn_mind.setAlignment(Pos.CENTER);

        mind.setFitHeight(150);
        mind.setFitWidth(200);
        sp_mindFull.getChildren().addAll(mind, btn_mind);



        StackPane sp_playFull = new StackPane();
        ImageView play = new ImageView(new Image("File:images/dog.jpg"));
        Button btn_play = new Button("Featured Game");
        btn_play.setAlignment(Pos.CENTER);

        play.setFitHeight(150);
        play.setFitWidth(200);
        sp_playFull.getChildren().addAll(play,btn_play);



        StackPane sp_symptoms = new StackPane();
        ImageView symptoms = new ImageView(new Image("File:images/covid.png"));
        Button btn_symptoms = new Button("Symptom Assessment");
        btn_symptoms.setAlignment(Pos.CENTER);

        symptoms.setFitHeight(150);
        symptoms.setFitWidth(200);

        sp_symptoms.getChildren().addAll(symptoms,btn_symptoms);


        StackPane sp_settings = new StackPane();
        ImageView settings = new ImageView(new Image("File:images/Settings.PNG"));
        Button btn_settings = new Button("Settings");
        btn_settings.setAlignment(Pos.CENTER);

        settings.setFitWidth(200);
        settings.setFitHeight(150);

        sp_settings.getChildren().addAll(settings,btn_settings);

        VBox vb_sideBar = new VBox();
        vb_sideBar.getChildren().addAll(sp_mindFull, sp_playFull, sp_symptoms, sp_settings);
        //endregion

        //region HomePage Code
        ImageView blueGrad = new ImageView(new Image("File:images/blueGrad.jpg"));
        StackPane sp_homeBack = new StackPane();


        VBox vb_homeText = new VBox();
        Label lb_welcomeUser = new Label("Welcome "+curUserName+"!");
        Label lb_click = new Label("\t     Click on one of the\n\n\nLinks in the Sidebar to get Started!");


        lb_welcomeUser.setMaxWidth(Double.MAX_VALUE);
        lb_click.setMaxWidth(Double.MAX_VALUE);
        lb_click.setAlignment(Pos.CENTER);
        lb_welcomeUser.setAlignment(Pos.CENTER);

        vb_homeText.getChildren().addAll(lb_welcomeUser, lb_click);
        vb_homeText.setSpacing(75);
        vb_homeText.setAlignment(Pos.CENTER);


        HBox hb_homePage = new HBox();
        hb_homePage.getChildren().addAll(vb_sideBar, vb_homeText);


        sp_homeBack.getChildren().addAll(blueGrad, hb_homePage);

        vb_homeText.prefWidthProperty().bind(hb_homePage.widthProperty());
        vb_homeText.prefHeightProperty().bind(hb_homePage.heightProperty());

        homePage = new Scene(sp_homeBack,700,600);

        blueGrad.fitWidthProperty().bind(homePage.widthProperty());
        blueGrad.fitHeightProperty().bind(homePage.heightProperty());
        //endregion

        //region Settings Panel
        StackPane sp_settingsStack = new StackPane();

        Label lb_settings =new Label("Settings");
        lb_settings.setPrefSize(150, 50);

        Label lb_firstName = new Label("First Name: ");
        lb_firstName.setPrefSize(175, 50);

        Label lb_lastName = new Label("Last Name");
        lb_lastName.setPrefSize(175, 50);

        Label lb_email = new Label("Email");
        lb_email.setPrefSize(175, 50);

        Label lb_userName = new Label("UserName: ");
        lb_userName.setPrefSize(175,50);

        Label lb_password = new Label("Password: ");
        lb_password.setPrefSize(175,50);

        Label lb_address = new Label("Address: ");
        lb_address.setPrefSize(175, 50);


        TextField tf_firstName = new TextField();
        tf_firstName.setPromptText("first name");
        tf_firstName.setPrefSize(175, 50);

        TextField tf_lastName = new TextField();
        tf_lastName.setPromptText("last name");
        tf_lastName.setPrefSize(175,50);

        TextField tf_email = new TextField();
        tf_email.setPromptText("email");
        tf_email.setPrefSize(175,50);

        TextField tf_userNameSettings = new TextField();
        tf_userNameSettings.setPromptText("username");
        tf_userNameSettings.setPrefSize(175,50);

        TextField tf_passwordSettings = new TextField();
        tf_passwordSettings.setPromptText("password");
        tf_passwordSettings.setPrefSize(175,50);

        TextField tf_address = new TextField();
        tf_address.setPromptText("address");
        tf_address.setPrefSize(175, 50);

        VBox vb_settingLabels = new VBox();
        vb_settingLabels.setSpacing(20);
        vb_settingLabels.getChildren().addAll(lb_firstName, lb_lastName, lb_email, lb_userName, lb_password, lb_address);

        VBox vb_settingsText = new VBox();
        vb_settingsText.setSpacing(20);
        vb_settingsText.getChildren().addAll(tf_firstName, tf_lastName, tf_email,tf_userNameSettings, tf_passwordSettings,tf_address);

        HBox hb_settingsGrid = new HBox();
        hb_settingsGrid.setSpacing(100);
        hb_settingsGrid.getChildren().addAll(vb_settingLabels, vb_settingsText);



        Button btn_saveSettings = new Button("Save Changes");
        Button btn_logOut = new Button("Log out");

        HBox hb_settingsActions = new HBox();
        hb_settingsActions.getChildren().addAll(btn_saveSettings, btn_logOut);
        hb_settingsActions.setSpacing(75);
        hb_settingsActions.setAlignment(Pos.CENTER);
        hb_settingsActions.setPadding(new Insets(0,50,0,0));




        VBox vb_settingsPanel = new VBox();
        vb_settingsPanel.setSpacing(20);
        vb_settingsPanel.getChildren().addAll(lb_settings, hb_settingsGrid, hb_settingsActions);

        vb_settingsPanel.setPadding(new Insets(0,0,0,25));
        //endregion



        //region Settings Sidebar V4
        StackPane sp_mindFull4 = new StackPane();
        ImageView mind4 = new ImageView(new Image("File:images/mind.jpg"));
        Button btn_mind4 = new Button("Mindfulness Exercise");

        mind4.setFitHeight(150);
        mind4.setFitWidth(200);
        sp_mindFull4.getChildren().addAll(mind4, btn_mind4);



        StackPane sp_playFull4 = new StackPane();
        ImageView play4 = new ImageView(new Image("File:images/dog.jpg"));
        Button btn_play4 = new Button("Featured Game");

        play4.setFitHeight(150);
        play4.setFitWidth(200);
        sp_playFull4.getChildren().addAll(play4,btn_play4);



        StackPane sp_symptoms4 = new StackPane();
        ImageView symptoms4 = new ImageView(new Image("File:images/covid.png"));
        Button btn_symptoms4 = new Button("Symptom Assessment");

        symptoms4.setFitHeight(150);
        symptoms4.setFitWidth(200);

        sp_symptoms4.getChildren().addAll(symptoms4,btn_symptoms4);



        StackPane sp_settings4 = new StackPane();
        ImageView settings4 = new ImageView(new Image("File:images/Settings.PNG"));
        Button btn_settings4 = new Button("Settings");

        settings4.setFitWidth(200);
        settings4.setFitHeight(150);

        sp_settings4.getChildren().addAll(settings4,btn_settings4);

        VBox vb_sideBar4 = new VBox();
        vb_sideBar4.getChildren().addAll(sp_mindFull4, sp_playFull4, sp_symptoms4, sp_settings4);
        //endregion

        //region Settings Scene (Final)
        ImageView blueGradSettings = new ImageView(new Image("File:images/blueGrad.jpg"));
        blueGradSettings.setFitWidth(700);
        blueGradSettings.setFitHeight(600);

        HBox hb_settingsOverall = new HBox();
        hb_settingsOverall.getChildren().addAll(vb_sideBar4, vb_settingsPanel);

        sp_settingsStack.getChildren().addAll(blueGradSettings, hb_settingsOverall);


        sc_settings = new Scene(sp_settingsStack, 700, 600);
        blueGradSettings.fitWidthProperty().bind(sc_settings.widthProperty());
        blueGradSettings.fitHeightProperty().bind(sc_settings.heightProperty());
        //endregion


        //region Mindful Sidebar V1
        StackPane sp_mindFull1 = new StackPane();
        ImageView mind1 = new ImageView(new Image("File:images/mind.jpg"));
        Button btn_mind1 = new Button("Mindfulness Exercise");

        mind1.setFitHeight(150);
        mind1.setFitWidth(200);
        sp_mindFull1.getChildren().addAll(mind1, btn_mind1);



        StackPane sp_playFull1 = new StackPane();
        ImageView play1 = new ImageView(new Image("File:images/dog.jpg"));
        Button btn_play1 = new Button("Featured Game");

        play1.setFitHeight(150);
        play1.setFitWidth(200);
        sp_playFull1.getChildren().addAll(play1,btn_play1);



        StackPane sp_symptoms1 = new StackPane();
        ImageView symptoms1 = new ImageView(new Image("File:images/covid.png"));
        Button btn_symptoms1 = new Button("Symptom Assessment");

        symptoms1.setFitHeight(150);
        symptoms1.setFitWidth(200);

        sp_symptoms1.getChildren().addAll(symptoms1,btn_symptoms1);



        StackPane sp_settings1 = new StackPane();
        ImageView settings1 = new ImageView(new Image("File:images/Settings.PNG"));
        Button btn_settings1 = new Button("Settings");

        settings1.setFitWidth(200);
        settings1.setFitHeight(150);

        sp_settings1.getChildren().addAll(settings1,btn_settings1);

        VBox vb_sideBar1 = new VBox();
        vb_sideBar1.getChildren().addAll(sp_mindFull1, sp_playFull1, sp_symptoms1, sp_settings1);
        //endregion


        //https://docs.oracle.com/javase/8/javafx/api/javafx/scene/media/MediaView.html used for media player
        File f = new File("Videos/Mindfulness Video.mp4");

        //Converts media to string URL
        Media media = new Media(f.toURI().toURL().toString());
        javafx.scene.media.MediaPlayer player = new javafx.scene.media.MediaPlayer(media);
        MediaView viewer = new MediaView(player);
        viewer.maxWidth(200);


        viewer.setPreserveRatio(true);


        StackPane root = new StackPane();
        root.getChildren().add(viewer);

        playing = false;

        //player.play();
        //endregion

        //region MindFul Scene (Final)
        StackPane sp_mindFulOverall = new StackPane();
        ImageView blueGrad1 = new ImageView(new Image("File:images/blueGrad.jpg"));

        Label lb_titleMedia = new Label("Breath along to the video below, clearing your mind and focusing on your breath. \n    A moment of mindfulness can ease a stressful day. Click the video to start");
        lb_titleMedia.setAlignment(Pos.CENTER);

        VBox vb_mindfulWidgets = new VBox();
        vb_mindfulWidgets.setAlignment(Pos.CENTER);
        vb_mindfulWidgets.setSpacing(25);
        vb_mindfulWidgets.getChildren().addAll(lb_titleMedia, viewer);

        HBox hb_mindful = new HBox();
        hb_mindful.getChildren().addAll(vb_sideBar1, vb_mindfulWidgets);

        sp_mindFulOverall.getChildren().addAll(blueGrad1, hb_mindful);


        sc_mindful = new Scene(sp_mindFulOverall, 700, 600);

        viewer.fitHeightProperty().bind(sc_mindful.heightProperty().subtract(200));
        viewer.fitWidthProperty().bind(sc_mindful.widthProperty().subtract(200));

        blueGrad1.fitWidthProperty().bind(sc_mindful.widthProperty());
        blueGrad1.fitHeightProperty().bind(sc_mindful.heightProperty());


        //endregion

        viewer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!playing) {
                    player.play();
                    playing = true;
                }
                else {

                    player.pause();
                    playing = false;
                }
            }
        });


        //region Symptoms Sidebar V3
        StackPane sp_mindFull3 = new StackPane();
        ImageView mind3 = new ImageView(new Image("File:images/mind.jpg"));
        Button btn_mind3 = new Button("Mindfulness Exercise");

        mind3.setFitHeight(150);
        mind3.setFitWidth(200);
        sp_mindFull3.getChildren().addAll(mind3, btn_mind3);



        StackPane sp_playFull3 = new StackPane();
        ImageView play3 = new ImageView(new Image("File:images/dog.jpg"));
        Button btn_play3 = new Button("Featured Game");

        play3.setFitHeight(150);
        play3.setFitWidth(200);
        sp_playFull3.getChildren().addAll(play3,btn_play3);



        StackPane sp_symptoms3 = new StackPane();
        ImageView symptoms3 = new ImageView(new Image("File:images/covid.png"));
        Button btn_symptoms3 = new Button("Symptom Assessment");

        symptoms3.setFitHeight(150);
        symptoms3.setFitWidth(200);

        sp_symptoms3.getChildren().addAll(symptoms3,btn_symptoms3);



        StackPane sp_settings3 = new StackPane();
        ImageView settings3 = new ImageView(new Image("File:images/Settings.PNG"));
        Button btn_settings3 = new Button("Settings");

        settings3.setFitWidth(200);
        settings3.setFitHeight(150);

        sp_settings3.getChildren().addAll(settings3,btn_settings3);

        VBox vb_sideBar3 = new VBox();
        vb_sideBar3.getChildren().addAll(sp_mindFull3, sp_playFull3, sp_symptoms3, sp_settings3);
        //endregion

        StackPane sp_symptomsOverall = new StackPane();

        ImageView blueGrad3 = new ImageView(new Image("File:images/blueGrad.jpg"));

        // THIS IS THE OVERALL TITLE
        Label lb_SymptomsTitle = new Label("Report Symptoms");
        lb_SymptomsTitle.setAlignment(Pos.CENTER);
        lb_SymptomsTitle.setPadding(new Insets(0,0,0,190));

        Label lb_heartRate = new Label("Heart Rate(#bpm): ");
        TextField tf_heartRate = new TextField();
        tf_heartRate.setPromptText("heart rate: ");
        Button btn_heartRate = new Button("click here to measure");



        Label lb_Breathing = new Label("Breathing Score(Roth): ");
        TextField tf_breathing = new TextField();
        tf_breathing.setPromptText("max no. counted in one breath");
        Button btn_breathing = new Button("click here to measure");



        Label lb_bodyTemp = new Label("Body Temp(F): ");
        TextField tf_BodyTemp = new TextField();
        tf_BodyTemp.setPromptText("body temperature");



        Label lb_Age = new Label("Age: ");
        TextField tf_age = new TextField();
        tf_age.setPromptText("age");

        VBox vb_labels = new VBox();
        vb_labels.setSpacing(58);
        vb_labels.getChildren().addAll(lb_heartRate,lb_Breathing,lb_bodyTemp,lb_Age);

        VBox vb_texts = new VBox();
        vb_texts.setSpacing(50);
        vb_texts.getChildren().addAll(tf_heartRate,tf_breathing,tf_BodyTemp, tf_age);

        VBox vb_buttons = new VBox();
        vb_buttons.setSpacing(50);
        vb_buttons.getChildren().addAll(btn_heartRate,btn_breathing);

        HBox hb_inputSymptoms = new HBox();
        hb_inputSymptoms.setSpacing(25);
        hb_inputSymptoms.getChildren().addAll(vb_labels,vb_texts,vb_buttons);

        hb_inputSymptoms.setPadding(new Insets(0,0,0,50));



        CheckBox cb_Cough = new CheckBox("Cough");
        CheckBox cb_Fatigue = new CheckBox("Fatigue");
        CheckBox cb_BodyAche = new CheckBox("Body Aches");
        CheckBox cb_headAche = new CheckBox("Headache");
        CheckBox cb_loss = new CheckBox("Lost smell/taste");
        CheckBox cb_soreThroat = new CheckBox("Sore throat");
        CheckBox cb_congestion = new CheckBox("Congestion");
        CheckBox cb_naseau = new CheckBox("Naseau");
        CheckBox cb_diarhea = new CheckBox("Diarhea");
        CheckBox cb_chestPain = new CheckBox("Chest Pain");


        VBox vb_symptoms = new VBox();
        vb_symptoms.setSpacing(10);
        vb_symptoms.getChildren().addAll(cb_Cough, cb_Fatigue, cb_BodyAche,cb_headAche,cb_loss,cb_soreThroat,cb_congestion,cb_naseau,cb_diarhea,cb_chestPain);

        TitledPane tp_checkSymptoms = new TitledPane("Other Symptoms", vb_symptoms);
        tp_checkSymptoms.setCollapsible(false);
        tp_checkSymptoms.setMaxWidth(200);

        Button btn_submit = new Button("Submit");
        btn_submit.setAlignment(Pos.CENTER);

        HBox hb_bottomPanel = new HBox();
        hb_bottomPanel.setAlignment(Pos.CENTER);
        hb_bottomPanel.setSpacing(125);
        hb_bottomPanel.getChildren().addAll(tp_checkSymptoms, btn_submit);
        hb_bottomPanel.setPadding(new Insets(0,100,0,0));


        VBox hb_symptoms = new VBox();
        hb_symptoms.setSpacing(2);
        hb_symptoms.getChildren().addAll(hb_inputSymptoms, hb_bottomPanel);




        VBox vb_symptomsWidgets = new VBox();
        vb_symptomsWidgets.setSpacing(25);
        vb_symptomsWidgets.getChildren().addAll(lb_SymptomsTitle, hb_symptoms);

        HBox hb_symptomsWithPanel = new HBox();
        hb_symptomsWithPanel.getChildren().addAll(vb_sideBar3,vb_symptomsWidgets);

        sp_symptomsOverall.getChildren().addAll(blueGrad3, hb_symptomsWithPanel);


        sc_symptomsInfoPage = new Scene(sp_symptomsOverall, 700,600);
        blueGrad3.fitWidthProperty().bind(sc_symptomsInfoPage.widthProperty());
        blueGrad3.fitHeightProperty().bind(sc_symptomsInfoPage.heightProperty());
        hb_symptoms.prefWidthProperty().bind(sc_symptomsInfoPage.widthProperty());
        hb_symptoms.prefHeightProperty().bind(sc_symptomsInfoPage.heightProperty());



        //region Playful Sidebar V2
        StackPane sp_mindFull2 = new StackPane();
        ImageView mind2 = new ImageView(new Image("File:images/mind.jpg"));
        Button btn_mind2 = new Button("Mindfulness Exercise");

        mind2.setFitHeight(150);
        mind2.setFitWidth(200);
        sp_mindFull2.getChildren().addAll(mind2, btn_mind2);



        StackPane sp_playFull2 = new StackPane();
        ImageView play2 = new ImageView(new Image("File:images/dog.jpg"));
        Button btn_play2 = new Button("Featured Game");

        play2.setFitHeight(150);
        play2.setFitWidth(200);
        sp_playFull2.getChildren().addAll(play2,btn_play2);



        StackPane sp_symptoms2 = new StackPane();
        ImageView symptoms2 = new ImageView(new Image("File:images/covid.png"));
        Button btn_symptoms2 = new Button("Symptom Assessment");

        symptoms2.setFitHeight(150);
        symptoms2.setFitWidth(200);

        sp_symptoms2.getChildren().addAll(symptoms2,btn_symptoms2);



        StackPane sp_settings2 = new StackPane();
        ImageView settings2 = new ImageView(new Image("File:images/Settings.PNG"));
        Button btn_settings2 = new Button("Settings");

        settings2.setFitWidth(200);
        settings2.setFitHeight(150);

        sp_settings2.getChildren().addAll(settings2,btn_settings2);

        VBox vb_sideBar2 = new VBox();
        vb_sideBar2.getChildren().addAll(sp_mindFull2, sp_playFull2, sp_symptoms2, sp_settings2);
        //endregion

        //region PlayFul Scene (Final)
        StackPane sp_playfulOverall = new StackPane();

        ImageView blueGrad2 = new ImageView(new Image("File:images/blueGrad.jpg"));

        Label lb_Welcome = new Label("Welcome to the Featured Game: Matching Game!");

        Label lb_instructions1 = new Label("Instructions: Matching game is a card game you can play with 2 people,");
        Label lb_instructions2 = new Label("so go get a friend to play with! ");
        Label lb_instructions3 = new Label("To play, flip two cards, one at a time, ");
        Label lb_instructions4 = new Label("and check whether they are the same. If they are, keep the cards and continue ");
        Label lb_instructions5 = new Label("taking two cards, until none remain or two cards are ");
        Label lb_instructions6 = new Label("not the same. If the cards are not the same, it's the other ");
        Label lb_instructions7 = new Label("user's turn. Keep playing until all cards are removed, by matching them.");

        Label lb_restart = new Label("To restart right click, once the game is over! Have Fun!");

        lb_Welcome.setAlignment(Pos.CENTER);
        lb_instructions1.setAlignment(Pos.CENTER);
        lb_instructions2.setAlignment(Pos.CENTER);
        lb_instructions3.setAlignment(Pos.CENTER);
        lb_instructions4.setAlignment(Pos.CENTER);
        lb_instructions5.setAlignment(Pos.CENTER);
        lb_instructions6.setAlignment(Pos.CENTER);
        lb_instructions7.setAlignment(Pos.CENTER);
        lb_restart.setAlignment(Pos.CENTER);

        Button btn_playGame = new Button("Play Game.");
        btn_playGame.setAlignment(Pos.CENTER);

        VBox vb_playTexts = new VBox();
        vb_playTexts.setSpacing(20);
        vb_playTexts.getChildren().addAll(lb_Welcome, lb_instructions1, lb_instructions2,lb_instructions3, lb_instructions4, lb_instructions5, lb_instructions6,lb_instructions7, lb_restart, btn_playGame);
        vb_playTexts.setAlignment(Pos.CENTER);
        vb_playTexts.setPadding(new Insets(0,0,0,40));

        HBox hb_playPanel = new HBox();
        hb_playPanel.getChildren().addAll(vb_sideBar2, vb_playTexts);

        sp_playfulOverall.getChildren().addAll(blueGrad2, hb_playPanel);

        sc_playful = new Scene(sp_playfulOverall, 700, 600);

        blueGrad2.fitWidthProperty().bind(sc_playful.widthProperty());
        blueGrad2.fitHeightProperty().bind(sc_playful.heightProperty());
        //endregion

        btn_breathing.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                RothPopup.display();
                //Timer.display();
            }
        });
        btn_heartRate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    HeartRatePopup.display();
                    Timer.display();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                patientScore = 0;
                double heartRate = -1, breathingRate = -1, bodyTemp = -1, age = -1;

                boolean displayResults = true;

                try {
                    heartRate = Double.parseDouble(tf_heartRate.getText());
                    breathingRate = Double.parseDouble(tf_breathing.getText());
                    bodyTemp = Double.parseDouble(tf_BodyTemp.getText());
                    age = Double.parseDouble(tf_age.getText());
                }
                catch (Exception e)
                {
                    displayResults = false;
                    new Alert(Alert.AlertType.ERROR, "Please enter all fields as numbers.").showAndWait();
                }
                try {
                    if (heartRate != -1 && breathingRate != -1 && bodyTemp != -1 && age != -1) {
                        if(age>=0&&age<=1)
                        {
                            if(heartRate>=160)
                                patientScore+=100;
                            if(breathingRate>=20&&breathingRate<=30)
                                patientScore+=20;
                            else if(breathingRate<20)
                                patientScore+=100;
                            if(bodyTemp>=99)
                                patientScore+=88;
                        }
                        else if(age>1&&age<=10)
                        {
                            if(heartRate>=140)
                                patientScore+=100;
                            if(breathingRate>=20&&breathingRate<=30)
                                patientScore+=20;
                            else if(breathingRate<20)
                                patientScore+=100;
                            if(bodyTemp>=99)
                                patientScore+=88;
                        }
                        else if(age>10 && age<=20)
                        {
                            if(heartRate>=100)
                                patientScore+=100;
                            if(breathingRate>=20&&breathingRate<=30)
                                patientScore+=20;
                            else if(breathingRate<20)
                                patientScore+=100;
                            if(bodyTemp>=99)
                                patientScore+=88;
                        }
                        else if(age>20)
                        {
                            if(heartRate>=100)
                                patientScore+=100;
                            if(breathingRate>=20&&breathingRate<=30)
                                patientScore+=20;
                            else if(breathingRate<20)
                                patientScore+=100;
                            if(bodyTemp>=99)
                                patientScore+=88;
                        }
                        else
                        {
                            displayResults = false;
                            new Alert(Alert.AlertType.ERROR, "Please enter a valid age.").showAndWait();
                        }
                    }
                }
                catch (Exception e)
                {
                    new Alert(Alert.AlertType.ERROR, "Please enter all fields as numbers.").showAndWait();
                }
                if(displayResults) {

                    if(cb_Cough.isSelected())
                        patientScore+=20;
                    if(cb_Fatigue.isSelected())
                        patientScore+=15;
                    if(cb_BodyAche.isSelected())
                        patientScore+=15;
                    if(cb_headAche.isSelected())
                        patientScore+=15;
                    if(cb_loss.isSelected())
                        patientScore+=30;
                    if(cb_soreThroat.isSelected())
                        patientScore+=15;
                    if(cb_congestion.isSelected())
                        patientScore+=10;
                    if(cb_naseau.isSelected())
                        patientScore+=50;
                    if(cb_diarhea.isSelected())
                        patientScore+=15;
                    if(cb_chestPain.isSelected())
                        patientScore+=80;

                    if(patientScore>=THRESHOLD)
                    {
                        TextInputDialog tip_addressConf = new TextInputDialog(users.get(curUserName).getAddress());
                        tip_addressConf.setTitle("Address Confirmation");
                        tip_addressConf.setHeaderText("Please enter address to locate hospitals near you.");
                        tip_addressConf.setContentText("Confirm Address");

                        Optional<String> resultAddress = tip_addressConf.showAndWait();
                        if(resultAddress.isPresent())
                        {
                            useAddress = resultAddress.get();
                        }

                        TextInputDialog tip_mobility = new TextInputDialog();
                        tip_mobility.setTitle("Enter Mobility");
                        tip_mobility.setHeaderText("Please enter distance you are able to travel in meters.");
                        tip_mobility.setContentText("Mobility: ");

                        Optional<String> resultMobility = tip_mobility.showAndWait();
                        if(resultMobility.isPresent())
                        {
                            try {
                                distance = Double.parseDouble(resultMobility.get());
                            }
                            catch (Exception e)
                            {
                                new Alert(Alert.AlertType.ERROR, "Please enter a number").showAndWait();
                            }
                        }

                        HospitalList.display(useAddress, distance-patientScore);

                        tf_heartRate.setText("");
                        tf_breathing.setText("");
                        tf_BodyTemp.setText("");
                        tf_age.setText("");
                        cb_Cough.setSelected(false);
                        cb_Fatigue.setSelected(false);
                        cb_BodyAche.setSelected(false);
                        cb_headAche.setSelected(false);
                        cb_loss.setSelected(false);
                        cb_soreThroat.setSelected(false);
                        cb_congestion.setSelected(false);
                        cb_naseau.setSelected(false);
                        cb_diarhea.setSelected(false);
                        System.out.println("send");
                    }
                    else
                    {
                        ArrayList<String> symptoms = new ArrayList<String>();
                        //send before deletion
                        if(heartRate>=100)
                            symptoms.add("heartrate ("+heartRate+")");
                        if(breathingRate>=20)
                            symptoms.add("breathing rate ("+breathingRate+")");
                        if(bodyTemp>=99)
                            symptoms.add("body temperature ("+bodyTemp+")");
                        if (cb_Cough.isSelected())
                            symptoms.add("cough");
                        if (cb_Fatigue.isSelected())
                            symptoms.add("fatigue");
                        if (cb_BodyAche.isSelected())
                            symptoms.add("body ache");
                        if (cb_headAche.isSelected())
                            symptoms.add("head ache");
                        if (cb_loss.isSelected())
                            symptoms.add("loss of taste/smell");
                        if (cb_soreThroat.isSelected())
                            symptoms.add("sore throat");
                        if (cb_congestion.isSelected())
                            symptoms.add("congestion");
                        if (cb_naseau.isSelected())
                            symptoms.add("naseau");
                        if (cb_diarhea.isSelected())
                            symptoms.add("diarhea");
                        if (cb_headAche.isSelected())
                            symptoms.add("headache");

                        StayHome.display(symptoms);


                        tf_heartRate.setText("");
                        tf_breathing.setText("");
                        tf_BodyTemp.setText("");
                        tf_age.setText("");
                        cb_Cough.setSelected(false);
                        cb_Fatigue.setSelected(false);
                        cb_BodyAche.setSelected(false);
                        cb_headAche.setSelected(false);
                        cb_loss.setSelected(false);
                        cb_soreThroat.setSelected(false);
                        cb_congestion.setSelected(false);
                        cb_naseau.setSelected(false);
                        cb_diarhea.setSelected(false);
                        System.out.println("don't send");
                    }
                }

            }
        });

        //region Mind Action
        EventHandler<ActionEvent> mindOverallAction = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("mind");
                primaryStage.setScene(sc_mindful);
                player.pause();
            }
        };

        btn_mind.setOnAction(mindOverallAction);
        btn_mind1.setOnAction(mindOverallAction);
        btn_mind2.setOnAction(mindOverallAction);
        btn_mind3.setOnAction(mindOverallAction);
        btn_mind4.setOnAction(mindOverallAction);
        //endregion

        //region Play Action
        EventHandler<ActionEvent> playOverallAction = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("play");
                primaryStage.setScene(sc_playful);
                player.pause();
            }
        };

        btn_play.setOnAction(playOverallAction);
        btn_play1.setOnAction(playOverallAction);
        btn_play2.setOnAction(playOverallAction);
        btn_play3.setOnAction(playOverallAction);
        btn_play4.setOnAction(playOverallAction);
        //endregion

        //region Symptoms Action
        EventHandler<ActionEvent> symptomsOverallAction = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("symptoms");
                primaryStage.setScene(sc_symptomsInfoPage);
                player.pause();
            }
        };

        btn_symptoms.setOnAction(symptomsOverallAction);
        btn_symptoms1.setOnAction(symptomsOverallAction);
        btn_symptoms2.setOnAction(symptomsOverallAction);
        btn_symptoms3.setOnAction(symptomsOverallAction);
        btn_symptoms4.setOnAction(symptomsOverallAction);
        //endregion

        //region Settings Action
        EventHandler<ActionEvent> settingsOverallAction = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("settings");
                primaryStage.setScene(sc_settings);
                player.pause();
            }
        };

        btn_settings.setOnAction(settingsOverallAction);
        btn_settings1.setOnAction(settingsOverallAction);
        btn_settings2.setOnAction(settingsOverallAction);
        btn_settings3.setOnAction(settingsOverallAction);
        btn_settings4.setOnAction(settingsOverallAction);
        //endregion

        btn_playGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(sc_playful);
                main.display();
            }
        });

        btn_logOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(startPage);
                curUserName = new String("");
            }
        });

        btn_forgotPassword.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ForgotPasswordPage.display(users);
                //System.out.println(users.toString());
            }
        });


        btn_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(users!=null&&users.containsKey(tf_userName.getText())&&tf_password.getText().equals(users.get(tf_userName.getText()).getPassword()))
                {
                    System.out.println("Successful login");
                    curUserName = tf_userName.getText();
                    tf_password.setText("");
                    tf_userName.setText("");
                    primaryStage.setScene(homePage);
                    lb_welcomeUser.setText("Welcome "+curUserName+"!");
                    tf_firstName.setText(users.get(curUserName).getFirstName());
                    tf_lastName.setText(users.get(curUserName).getLastName());
                    tf_email.setText(users.get(curUserName).getEmail());
                    tf_userNameSettings.setText(curUserName);
                    tf_passwordSettings.setText(users.get(curUserName).getPassword());
                    tf_address.setText(users.get(curUserName).getAddress());
                }
                else
                {
                    new Alert(Alert.AlertType.ERROR, "Invalid userName and password.").showAndWait();
                }
            }
        });

        btn_saveSettings.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(users.containsKey(tf_userNameSettings.getText()))
                {
                    String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
                    if(!tf_email.getText().matches(regex))
                    {
                        new Alert(Alert.AlertType.ERROR, "Incorrect Email Format.").showAndWait();
                    }
                    else if(users.get(tf_userNameSettings.getText())!=users.get(curUserName))
                        new Alert(Alert.AlertType.ERROR, "Username already taken.");
                    else if(!checkEmail(users, tf_email.getText()))
                    {
                        new Alert(Alert.AlertType.ERROR, "Email is already used.").showAndWait();
                    }
                    else {

                        users.remove(curUserName);
                        Person usr = new Person(tf_firstName.getText(), tf_lastName.getText(), tf_email.getText(), tf_userNameSettings.getText(), tf_password.getText(), tf_address.getText());
                        users.put(tf_userNameSettings.getText(), usr);

                        curUserName = tf_userNameSettings.getText();
                        new Alert(Alert.AlertType.INFORMATION, "Settings Saved Successfully.").showAndWait();
                    }
                }
                else
                {
                    String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
                    if(!tf_email.getText().matches(regex))
                    {
                        new Alert(Alert.AlertType.ERROR, "Incorrect Email Format.").showAndWait();
                    }
                    else if(!checkEmail(users, tf_email.getText()))
                    {
                        new Alert(Alert.AlertType.ERROR, "Email is already used.").showAndWait();
                    }
                    else {
                        users.remove(curUserName);
                        Person usr = new Person(tf_firstName.getText(), tf_lastName.getText(), tf_email.getText(), tf_userNameSettings.getText(), tf_password.getText(), tf_address.getText());
                        users.put(tf_userNameSettings.getText(), usr);
                        //System.out.println(users.toString());

                        curUserName = tf_userNameSettings.getText();
                        new Alert(Alert.AlertType.INFORMATION, "Settings Saved Successfully.").showAndWait();
                    }
                }
            }
        });

        btn_signUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Person user = SignUpPage.display(users);
                if(user != null) {
                    users.put(user.getUserName(), user);
                    System.out.println("successfully added this user to the map.");
                }
                else
                    System.out.println("failed to add this user to the map.");

                //print all users as of now
//                if( users != null) {
//                    Iterator<String> itr = users.keySet().iterator();
//                    while (itr.hasNext()) {
//                        System.out.println("user: " + itr.next());
//                    }
//                }
            }
        });

        //Todo: Change Here
        primaryStage.setScene(startPage);
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> {
            writeUsersToFile(users);
        });
    }

    private boolean checkEmail(HashMap<String, Person> users, String email)
    {
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


    public boolean writeUsersToFile(HashMap<String, Person> users)
    {

        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(users);
//            System.out.println(json);

            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(new File("users.json"), users);

            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean readUsersFromFile()
    {
        try {
            // create object mapper instance
            ObjectMapper mapper = new ObjectMapper();

            // convert JSON file to map
            HashMap<?, ?> map = mapper.readValue(Paths.get("users.json").toFile(), HashMap.class);

            // print HashMap entries
            for (HashMap.Entry<?, ?> entry : map.entrySet()) {
                java.util.LinkedHashMap usrObj = (java.util.LinkedHashMap)entry.getValue();

                //debug purposes user data
                //System.out.println("from users.json - " + entry.getKey() + "=" + usrObj);


                Person usr = new Person(usrObj);

                users.put((String)entry.getKey(), usr);
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
