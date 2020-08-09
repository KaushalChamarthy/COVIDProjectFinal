import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Timer
{
    static int oldWidth;
    static int oldHeight;
    long time;
    long diff;


    public static void display() throws FileNotFoundException
    {
        Stage initial_stage = new Stage();

        FileInputStream input = new FileInputStream("red_cross.png");
        Image image = new Image(input);
        ImageView red_cross = new ImageView(image);

        red_cross.setLayoutX(100);
        red_cross.setLayoutY(100);
        ImageTools.scale(red_cross, 40, 40);
        oldWidth = (int) red_cross.getFitWidth();
        oldHeight = (int) red_cross.getFitHeight();

        // Creates the timer
        Clock timer = new Clock(red_cross);

        // Creates the screen
        Pane screen = new Pane();
        screen.getChildren().add(red_cross);
        screen.getChildren().add(timer);

        Scene scene = new Scene (screen, 600, 700);

        initial_stage.setTitle("Timer");
        initial_stage.setScene(scene);
        initial_stage.show();


        if (timer.change())
        {
            System.out.println("CHANGED");
            red_cross = ImageTools.scale(red_cross, oldWidth += 2, oldHeight += 2);
            screen.getChildren().add(red_cross);
        }
    }
}