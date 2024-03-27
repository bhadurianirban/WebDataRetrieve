/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package org.bhaduri.webdataretrieve;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bhaduri
 */
public class WebDataRetrieve {

    static Logger logger = Logger.getLogger(WebDataRetrieve.class.getName());
    private static String FILE_PATH = "/home/bhaduri/Downloads/";

    public static void main(String[] args) {
        if (args.length != 0) {
            String dirPath = args[0];
            FILE_PATH = dirPath;
        }
        logger.setLevel(Level.FINE);
        logger.addHandler(new ConsoleHandler());
        //adding custom handler

        try {
            //FileHandler file name with max size and number of log files limit
            Handler fileHandler = new FileHandler(FILE_PATH + "logger.log", 2000, 5);
            logger.addHandler(fileHandler);
        } catch (SecurityException | IOException e) {
            System.out.println("Cannot open log file");
        }

        String url = "https://economictimes.indiatimes.com/indices/nifty_50_companies";
        String downFileName = createFileName();
        boolean isRunnable = timeToRun();
        //DownloadWebPage(url, downFileName);
    }

    private static String createFileName() {
        String dirPath = FILE_PATH;
        String prefix = "nifty50_";
        Date currDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String timeCreate = formatter.format(currDate);
        String suffix = "_.txt";
        String totalFileName = dirPath + prefix + timeCreate + suffix;
        return totalFileName;
    }

    public static void DownloadWebPage(String webpage, String downFileName) {
        try {

            // Create URL object 
            URL url = new URL(webpage);
            BufferedWriter writer;
            // Enter filename in which you want to download
            try (BufferedReader readr = new BufferedReader(new InputStreamReader(url.openStream()))) {
                // Enter filename in which you want to download
                writer = new BufferedWriter(new FileWriter(downFileName));
                // read each line from stream till end
                String line;
                while ((line = readr.readLine()) != null) {
                    writer.write(line);
                }
                writer.close();
            } catch (IOException ie) {
                logger.log(Level.SEVERE, "IOException raised");
            } catch (Exception e) {
                logger.log(Level.SEVERE, e.getMessage());
            }

            logger.log(Level.INFO, "Successfully Downloaded.");
        } catch (MalformedURLException mue) {
            logger.log(Level.SEVERE, "Malformed URL Exception raised");
        }
    }

    public static boolean timeToRun() {
        LocalTime currentTime = LocalTime.now();
        LocalTime startTime = LocalTime.of(19, 0, 0, 0);
        if (currentTime.isAfter(startTime)) {
            System.out.println("after 9 am");
            return true;
        } else {
            System.out.println("before 9 am");
            return false;
        }
    }

}
