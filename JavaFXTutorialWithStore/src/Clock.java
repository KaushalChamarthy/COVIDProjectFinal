import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

class Clock extends Pane
{
    Timeline animation;
    int temp_time = 60;
    int time_left;
    String s = "";
    String old_s = "";

    Label lbl = new Label("60");

    ImageView red_cross;


    public Clock(ImageView red_cross)
    {
        this.red_cross = red_cross;
        lbl.setFont(javafx.scene.text.Font.font(100));
        lbl.setTranslateX(250);
        lbl.setTranslateY(400);

        getChildren().add(lbl);
        animation = new Timeline(new KeyFrame(Duration.seconds(1), e -> time_label()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    public void time_label()
    {
        if (temp_time > 0)
        {
            ImageTools.scale(red_cross, (int) red_cross.getFitWidth() + 1,
                    (int) red_cross.getFitHeight() + 1);

            temp_time--;
        }
        s = temp_time + "";
        lbl.setText(s);
    }

    public Boolean change()
    {
        if (!old_s.equals(s))
        {
            old_s = s;
            return true;
        }
        else
        {
            return false;
        }
    }
}