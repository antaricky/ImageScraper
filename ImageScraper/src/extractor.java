import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.lang.Runnable;

public class extractor
{
    public static void main(String[] args)
    {

        String band_name,score_name,path_name,img_url,img_dest,score_url;
        String prefix = "http://guitarlist.net/bandscore/";
        String img = "img/";
        String extension = ".png";

        //GUI input
        JFrame frame = new JFrame("Guitarist Scraper");
        String url = JOptionPane.showInputDialog(frame,"URL?");

        //if user cancel
        if (url == null)
            System.exit(0);

        //start splitting the url from the http://
        String[] pre_split = url.split("://");

        if (pre_split.length == 1) {
            score_url = pre_split[0];
        } else {
            score_url = pre_split[1];
            System.out.println(score_url);
        }

        System.out.println("The parsed score URL is " + score_url);
        //start splitting out the score name and band name
        String[] split = score_url.split("/");
        band_name = split[2];
        score_name = split[3];

        System.out.println("The band name is:" + band_name);
        System.out.println("The score name is:" + score_name);

        path_name = "D:\\Pictures\\Tabs\\" + band_name + "\\" + score_name + "\\";   //save to this path in computer
        System.out.println("the path name is " + path_name);
        new File(path_name).mkdirs();   //create folders before writing to it

        //start executor service for multi-threading, nThread is the max concurrent thread number
        ExecutorService executor = Executors.newFixedThreadPool(100);

        //Tabs are almost always less than 100 Pages
        for (int i = 1;i<= 100;i++){
            //compose the artificial img url
            img_url = prefix + band_name + "/" + score_name + "/" + img + Integer.toString(i) + extension;
            img_dest = path_name + Integer.toString(i) + extension;

            Runnable worker = new Scraper_thread(img_url, img_dest);
            executor.execute(worker);
            System.out.println("the img_url is " + img_url);
            System.out.println("the img_dest is " + img_dest);
        }

        executor.shutdown();    //not accept the work anymore
        while (!executor.isTerminated()) {
            //wait until all thread finish
        }
        try {
                //open path in windows explorer
                Runtime.getRuntime().exec("explorer.exe /select," + path_name);

            }
        catch (IOException e)
            {
                e.getStackTrace();
            }
        System.out.println("All Done!");
        System.exit(0);
    }
}


