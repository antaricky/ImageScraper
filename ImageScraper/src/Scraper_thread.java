import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;


public class Scraper_thread implements Runnable {

    private final String url;
    private final String dest;
    Scraper_thread(String url,String dest) {
        this.url = url;
        this.dest = dest;
    }

    public void run() {

        try {
            URL url_stream = new URL(url);
            if (url_stream != null) {
                BufferedImage save_img = ImageIO.read(url_stream);
                if (save_img != null) {
                    System.out.println("Now writing " + dest);
                    ImageIO.write(save_img, "png", new File(dest));
                }
            }

        } catch (IOException E){
            E.getStackTrace();

        }

        System.out.println("URL:" + url + " finished");
    }
}