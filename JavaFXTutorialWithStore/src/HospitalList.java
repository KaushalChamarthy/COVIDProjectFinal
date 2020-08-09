import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TableView;

import java.net.URI;
import java.util.ArrayList;
import java.util.Observable;

public class HospitalList {

    static Stage window;
    static TableView<Hospital> table;

    public static void display(String address, double optimizedRange) {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Stay Home");
        window.setWidth(600);
        window.setHeight(500);

        TableColumn<Hospital,String> nameColumn = new TableColumn<>("Names");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Hospital,String> addressColumn = new TableColumn<>("Address");
        addressColumn.setMinWidth(200);
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Hospital,String> phoneColumn = new TableColumn<>("Phone #");
        phoneColumn.setMinWidth(200);
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        table = new TableView<>();
        table.setItems(getHospitals(optimizedRange, address, 100));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getColumns().addAll(nameColumn,addressColumn,phoneColumn);

        Button btn_directions = new Button("Directions");
        btn_directions.setAlignment(Pos.CENTER);

        VBox vb_show = new VBox();
        vb_show.getChildren().addAll(table, btn_directions);

        btn_directions.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(table.getSelectionModel().getSelectedItem()!=null)
                {
                    try
                    {
                        StringBuilder uriLink = new StringBuilder("https://www.google.com/maps/dir/");

                        String[] myAddress = address.split(" ");

                        for (int i = 0; i < myAddress.length; i++) {
                            if(i< myAddress.length-1)
                                uriLink.append(myAddress[i]).append("+");
                            else
                                uriLink.append(myAddress[i]+"/");
                        }

                        String[] addressSplit= table.getSelectionModel().getSelectedItem().getAddress().split(" ");



                        for (int i = 0; i < addressSplit.length; i++) {
                            if(i< addressSplit.length-1)
                                uriLink.append(addressSplit[i]).append("+");
                            else
                                uriLink.append(addressSplit[i]);
                        }

                        System.out.println(uriLink.toString());

                        URI uri = new URI(uriLink.toString());
                        java.awt.Desktop.getDesktop().browse(uri);
                        System.out.println("Opened Directions");
                    }
                    catch (Exception e)
                    {
                        new Alert(Alert.AlertType.ERROR, "Failed to get directions.").showAndWait();
                    }
                }
            }
        });


        window.setScene(new Scene(vb_show));
        window.showAndWait();
    }

    public static ObservableList<Hospital> getHospitals(double optimalDistance, String address, int numberOfResults)
    {
        ObservableList<Hospital> hospitals = FXCollections.observableArrayList();
        ArrayList<ArrayList<String>> store;

        try {
            store = new HospitalLister().optimalHospitalLocator(optimalDistance,address,numberOfResults);
            for (int i = 0; i < store.get(0).size(); i++) {
                String nameEach = store.get(0).get(i);
                String addressEach = store.get(1).get(i);
                String phoneNumberEach = store.get(2).get(i);
                hospitals.add(new Hospital(nameEach, addressEach,phoneNumberEach));

            }
            return hospitals;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load Hospital").showAndWait();
        }

        return null;
    }
}
