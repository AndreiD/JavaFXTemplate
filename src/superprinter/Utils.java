package superprinter;


import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileChannel;


public class Utils {
    public static boolean check_directory(String directory1, String printer_Command) {
        File dir = new File(directory1);

        if (!dir.exists()) {
            System.out.println("directory doesn't exist. Creating: " + directory1);
            if (!dir.mkdir()) {
                System.out.println("FAILED TO CREATE DIRECTORY " + directory1);
            }
        }

        //are there files in the directory
        if (dir.listFiles().length < 1) {
            return false;
        }

        //files are present. print them
        File[] listFiles = dir.listFiles();
        for (File theFile : listFiles) {
            if (theFile.length() > 0) {
                print_file(printer_Command);
                return true;
            } else {
                return false;
            }
        }


        return false;
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
    }

    static void reencode_file(String encode_to_windows_1251_command) {
        try {
            Process p = Runtime.getRuntime().exec(encode_to_windows_1251_command);
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

    public static void delete_file(String file_path) {
        if (!new File(file_path).delete()) {
            System.out.println("CANNOT DELETE FILE " + file_path);
        }
    }


    public static void copyFile(File sourceFile, File destFile)
            throws IOException {
        if (!sourceFile.exists()) {
            return;
        }
        if (!destFile.exists()) {
            destFile.createNewFile();
        }
        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }

    }

    public static void Sleep_for(int seconds){
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}