import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HeartRatePopup {
    static Stage window;
    static TableView<Hospital> table;

    public static void display() {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Heart Rate Popup");
        window.setWidth(700);
        window.setHeight(400);

        //todo: add image here
        ImageView roth = new ImageView(new Image("File:images/HR.png"));
        roth.setFitHeight(317);
        roth.setFitWidth(647);

        Button btn_understand = new Button("I understand");
        btn_understand.setAlignment(Pos.CENTER);

        window.setOnCloseRequest(e -> {
            e.consume();
        });

        btn_understand.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.close();
            }
        });

        VBox vb_show = new VBox();
        vb_show.getChildren().addAll(roth, btn_understand);
        vb_show.setAlignment(Pos.CENTER);



        window.setScene(new Scene(vb_show));
        window.showAndWait();
    }
}
