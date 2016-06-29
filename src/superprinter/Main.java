package superprinter;

import com.sun.imageio.plugins.common.ImageUtil;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.reactfx.util.FxTimer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Properties;
import java.util.TimeZone;

public class Main extends Application {

    private Label statusLabel;
    private TrayIcon trayIcon;
    private SystemTray tray;
    private String printer_1_ServerAddress;
    private String printer_2_ServerAddress;
    private String printer_3_ServerAddress;
    private String FileName1;
    private String FileName2;
    private String FileName3;
    private String FolderName1;
    private String FolderName2;
    private String FolderName3;
    private String printer_1_Command;
    private String printer_2_Command;

    @Override
    public void start(Stage primaryStage) throws Exception {

        loadConfiguration();

        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setTitle("Super Printer v 1.03");
        FlowPane rootNode = new FlowPane(10, 10);
        rootNode.setAlignment(Pos.CENTER);
        primaryStage.setScene(new Scene(rootNode, 300, 175));
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/resources/ic_launcher.png")));
        createTrayIcon();

        statusLabel = new Label("Program is running...");
        ProgressIndicator p1 = new ProgressIndicator();
        p1.setMaxHeight(20);
        rootNode.getChildren().addAll(statusLabel, p1);


        the_task(printer_1_ServerAddress, FolderName1, FileName1, printer_1_Command);
        the_task(printer_2_ServerAddress, FolderName2, FileName2, printer_2_Command);

        Platform.exit();
        System.exit(0);
//        FxTimer.runPeriodically(
//                Duration.ofSeconds(15),
//                () -> {
//                    the_task(printer_1_ServerAddress, FolderName1, FileName1, printer_2_Command);
//                    tray.getTrayIcons()[0].setToolTip("last checked @ " + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance(TimeZone.getDefault()).getTime()));
//                });
    }


    public static void main(String[] args) {
        launch(args);
    }

    private static void the_task(String printer_ServerAddress, String folderName, String fileName, String printer_Command) {

        System.out.println("+++++++++++++++  the_task: "+  fileName + "  +++++++++++++++");

        Utils.download_file(printer_ServerAddress, folderName + File.separator + fileName);

        Utils.check_directory(folderName, printer_Command);

        Utils.delete_file(folderName + File.separator + fileName);


    }


    private void createTrayIcon() {
        if (SystemTray.isSupported()) {
            // get the SystemTray instance
            tray = SystemTray.getSystemTray();

            java.awt.Image image = null;
            try {
                URL url = ImageUtil.class.getResource("/resources/ic_launcher.png");
                image = ImageIO.read(url);
            } catch (IOException ex) {
                System.out.println(ex);
            }

            trayIcon = new TrayIcon(image, "Super Printer");
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println(e);
            }

            trayIcon.displayMessage("Super Printer",
                    "Super printer is running",
                    TrayIcon.MessageType.INFO);
        }
    }


    public void loadConfiguration() {
        Properties props = new Properties();
        InputStream is;
        try {
            File f = new File("configuration.xml");
            is = new FileInputStream(f);
            props.loadFromXML(is);
        } catch (Exception e) {
            System.out.println("cannot load the configuration file!");
            return;
        }


        printer_1_ServerAddress = props.getProperty("printer_1_ServerAddress", "-1");
        printer_2_ServerAddress = props.getProperty("printer_2_ServerAddress", "-1");
        printer_3_ServerAddress = props.getProperty("printer_3_ServerAddress", "-1");
        FileName1 = props.getProperty("FileName1", "-1");
        FileName2 = props.getProperty("FileName2", "-1");
        FileName3 = props.getProperty("FileName3", "-1");
        FolderName1 = props.getProperty("FolderName1", "-1");
        FolderName2 = props.getProperty("FolderName2", "-1");
        FolderName3 = props.getProperty("FolderName3", "-1");
        printer_1_Command = props.getProperty("printer_1_Command", "-1");
        printer_2_Command = props.getProperty("printer_2_Command", "-1");

    }

}