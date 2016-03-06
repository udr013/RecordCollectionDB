package RecordCollectionGUI;


import com.udr.RecCollection.Record;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by udr013 on 2-3-2016.
 */
public class MainFX extends Application

{
    private Pane enterRecordScene;
    private VBox albumFields = new VBox(10);
    private HBox directoryFields = new HBox();
    private HBox fileTextDisplay = new HBox();
    File file;

    @Override
    public void start(Stage primaryStage) throws Exception {

        List<Record> myCollection = new LinkedList<Record>();

        Text currentFileText = new Text("No CollectionDB Selected");
        currentFileText.setWrappingWidth(50);
        Text displayText = new Text("Database: ");
        displayText.setWrappingWidth(500);

        TextField artist = new TextField();
        artist.setPromptText("Artist");
        TextField album = new TextField();
        album.setPromptText("Album");
        TextField year = new TextField();
        year.setPromptText("Year");
        TextField label = new TextField();
        label.setPromptText("Label");

        Button submit = new Button("Submit");
        Button clearAll = new Button("Clear All");
        Button openFileButton = new Button("Load File");

        FileChooser fileChooser = new FileChooser();

        /*
         *  Actions for buttons get defined here
         */

        //LEES BESTAND IN EN GEEF OOK WEER IN VELD
        openFileButton.setOnAction(event2 -> {
            file = fileChooser.showOpenDialog(primaryStage);
            currentFileText.setText(getCurrentFileName(file.getName())); //display name of current loaded file
            displayText.setText(readFile() + "\nend of file");
//                  DIT WERKT DUS NIET!!
//            try {
//               Scanner contents = new Scanner(file);
//               displayText.setText(contents.nextLine());
//           }catch (FileNotFoundException e){
//               displayText.setText("FileNotFound");
//           }


        });


        clearAll.setOnAction(event1 -> {
            artist.clear();
            album.clear();
            year.clear();
            label.clear();

        });


        submit.setOnAction(event -> {
                    if (file == null) {
                        currentFileText.setStyle("-fx-animation-fill-mode:both; -fx-font-style: oblique; -fx-font: bold");
                        currentFileText.setFill(Color.CRIMSON);
                    } else {
                        Record thatRecord = new Record(artist.getText(), album.getText(), Integer.parseInt(year.getText()), label.getText());
                        myCollection.add(thatRecord);
                        //thatRecord.toString();

                        try (FileWriter writetoFile = new FileWriter(file, true)) { ///try with resources //FileWriter needed to write stringdata
                            writetoFile.write(thatRecord.toString());
                        } catch (FileNotFoundException e) {//ja,ja, FileNotFoundExeption
                            e.printStackTrace();
                        } catch (IOException i) { //catch IOExeption thrown by autoclosable
                            i.printStackTrace();
                        }
                    }

                    artist.clear();
                    album.clear();
                    year.clear();
                    label.clear();
                    //reload text from DBtextFile
                    displayText.setText(readFile() + "\nend of file");
                }
        );

        /*
         * adding fields to parents
         */
        directoryFields.getChildren().addAll(openFileButton, currentFileText);
        directoryFields.setPadding(new Insets(10, 30, 10, 20));
        directoryFields.setSpacing(20);
        directoryFields.setAlignment(Pos.TOP_LEFT);

        albumFields.getChildren().addAll(artist, album, year, label, submit, clearAll);
        albumFields.setAlignment(Pos.CENTER_RIGHT);

        fileTextDisplay.getChildren().add(displayText);
        fileTextDisplay.setStyle("-fx-background-color: burlywood");

        HBox back = new HBox();
        back.getChildren().add(directoryFields);
        back.getChildren().add(albumFields);
        VBox background = new VBox(back, fileTextDisplay);

        enterRecordScene = new Pane(background);


        primaryStage.setTitle("RecordCollectionDB");
        primaryStage.setScene(new Scene(enterRecordScene, 800, 800));
        primaryStage.show();


    }


    private String readFile() {
        String line = "";
        try {
            //FileReader bestand = new FileReader(file);
            BufferedReader br = new BufferedReader(new FileReader(file));
            line = br.readLine();
            line = formattedLine(line);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException f) {
            f.printStackTrace();
        }
        return line;
    }

    //DIT WERKT NIET!!
//    private String readFile() {
//        String line = "";
//        String collection = "";
//        try {
//            //FileReader bestand = new FileReader(file);
//            BufferedReader br = new BufferedReader(new FileReader(file));
//            line = br.readLine();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException f) {
//            f.printStackTrace();
//        }
//        while ((line != null)) {
//
//            collection += ("\n" + addRecord(line));
//        }
//        return collection;
//    }
//
//
//
//
    private String formattedLine(String line) {
        if (line == null) {
            return "";
        } else {
            String[] records = line.split("#");
            String allRecords = "";
            //songList.add(tokens[1]);
            for (String x : records) {
                String[] fields = x.split(":");
                Record nextRecord = new Record(fields[0], fields[1], Integer.parseInt(fields[2]), fields[3]);
                allRecords += nextRecord.toString() + "\n";
            }
            return allRecords;

        }
    }


    private String getCurrentFileName(String filename) {
        return "Loaded: " + filename;
    }

    public static void main(String[] args) {
        launch(args);
    }


}
