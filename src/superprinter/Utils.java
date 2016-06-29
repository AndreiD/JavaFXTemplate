package superprinter;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


public class Utils {
    public static void check_directory(String directory1, String printer_Command) {
        File dir = new File(directory1);

        if (!dir.exists()) {
            System.out.println("directory doesn't exist. Creating: " + directory1);
            if (!dir.mkdir()) {
                System.out.println("FAILED TO CREATE DIRECTORY " + directory1);
            }
        }

        //are there files in the directory
        if (dir.listFiles().length < 1) {
            System.out.println("no files in " + directory1 + ".");
            return;
        }

        //files are present. print them
        File[] listFiles = dir.listFiles();
        for (File theFile : listFiles) {
            System.out.println("PRINTING >> " + theFile.getPath());
            print_file(printer_Command);
        }


    }

    private static void print_file(String printer_Command) {


        try {
            Process p = Runtime.getRuntime().exec(printer_Command);
            p.waitFor();
            InputStream is = p.getInputStream();

            int i;
            while ((i = is.read()) != -1) {
                System.out.print((char) i);
            }

        } catch (IOException ex) {
            System.out.println("the file can't be accessed (not enough permissions) " + ex.getMessage());

        } catch (InterruptedException ex) {
            System.out.println("the process is being stopped by some external situation: " + ex.getMessage());
        }
    }

    public static void download_file(String fileUrl1, String location) {
        try {
            URL url_file = new URL(fileUrl1);
            FileUtils.copyURLToFile(url_file, new File(location), 5000, 5000);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(fileUrl1 + " downloaded.");

    }

    public static void delete_file(String file_path) {
        if(!new File(file_path).delete()){
            System.out.println("CANNOT DELETE FILE "+file_path);
        }else{
            System.out.println("file deleted.");
        }

    }
}