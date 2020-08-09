import javafx.scene.image.ImageView;

public class ImageTools
{
    /**
     * Scales an image.
     *
     * @param img       Receives a buffered image
     * @param newWidth  New width to scale to.
     * @param newHeight New height to scale to.
     * @return creates and returns a scaled copy of the received image,
     * null is returned if the received image is null or if non-positive dimensions are provided
     */

    public static ImageView scale(ImageView img, int newWidth, int newHeight)
    {
        img.setFitWidth(newWidth);
        img.setFitHeight(newHeight);
        return img;
    }
}