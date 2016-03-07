package RecordCollectionGUI;



import com.udr.RCComponents.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by udr013 on 2-3-2016.
 */
public class MainFX extends Application

{
    File file;
    private Pane enterRecordScene;
    private VBox albumFields = new VBox(10);
    private HBox directoryFields = new HBox();
    private HBox fileTextDisplay = new HBox();
    private List loadedList = new LinkedList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

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
        Button sortArtist = new Button("Sort By Artist");
        Button sortAlbum = new Button("Sort By Album");
        Button sortYear = new Button("Sort By Year");
        Button sortLabel = new Button("Sort By Label");

        FileChooser fileChooser = new FileChooser();


        /*
         * adding fields to parents
         */
        directoryFields.getChildren().addAll(openFileButton, currentFileText);
        directoryFields.setPadding(new Insets(10, 30, 10, 20));
        directoryFields.setSpacing(20);
        directoryFields.setAlignment(Pos.TOP_LEFT);

        albumFields.getChildren().addAll(artist, album, year, label, submit, clearAll, sortArtist, sortAlbum, sortYear, sortLabel);
        albumFields.setAlignment(Pos.CENTER_RIGHT);

        fileTextDisplay.getChildren().add(displayText);
        fileTextDisplay.setStyle("-fx-background-color: burlywood");
        fileTextDisplay.setAlignment(Pos.CENTER);

        HBox back = new HBox();
        back.getChildren().add(directoryFields);
        back.getChildren().add(albumFields);
        VBox background = new VBox(back, fileTextDisplay);

        enterRecordScene = new Pane(background);


        primaryStage.setTitle("RecordCollectionDB");
        primaryStage.setScene(new Scene(enterRecordScene, 800, 800));
        primaryStage.show();



        /*
         *  Actions for buttons get defined here
         */

        //lees bestand in vanuit "verkenner"
        openFileButton.setOnAction(event2 -> {
            file = fileChooser.showOpenDialog(primaryStage);
            currentFileText.setText(getCurrentFileName(file.getName())); //display name of current loaded file
            displayText.setText(readFile());
//                  DIT WERKT DUS NIET!!
//            try {
//               Scanner contents = new Scanner(file);
//               displayText.setText(contents.nextLine());
//           }catch (FileNotFoundException e){
//               displayText.setText("FileNotFound");
//           }
        });


        //event for submit button
        submit.setOnAction(event -> {
                    if (file == null) {
                        currentFileText.setStyle("-fx-animation-fill-mode:both; -fx-font-style: oblique; -fx-font: bold");
                        currentFileText.setFill(Color.CRIMSON);
                    } else {
                        Record thatRecord = new Record(artist.getText(), album.getText(), Integer.parseInt(year.getText()), label.getText());

                        try (FileWriter writetoFile = new FileWriter(file, true)) { ///try with resources //FileWriter needed to write stringdata
                            writetoFile.write(thatRecord.toString());
                        } catch (FileNotFoundException e) {   //ja,ja, FileNotFoundExeption
                            e.printStackTrace();
                        } catch (IOException i) { //catch IOExeption thrown by autoclosable
                            i.printStackTrace();
                        }
                    }

                    artist.clear();
                    album.clear();
                    year.clear();
                    label.clear();
                    displayText.setText(readFile() + "\nend of file");
                }
        );


        clearAll.setOnAction(event1 -> {
            artist.clear();
            album.clear();
            year.clear();
            label.clear();

        });

        // events for sort Buttons
        sortArtist.setOnAction(event1 -> {
            ArtistCompare artistcompare = new ArtistCompare();
            Collections.sort(loadedList, artistcompare);
            displayText.setText(formattedList());

        });

        sortAlbum.setOnAction(event1 -> {
            AlbumTitleCompare albumcompare = new AlbumTitleCompare();
            Collections.sort(loadedList, albumcompare);
            displayText.setText(formattedList());

        });

        sortYear.setOnAction(event1 -> {
            YearCompare yearcompare = new YearCompare();
            Collections.sort(loadedList, yearcompare);
            displayText.setText(formattedList());

        });

        sortLabel.setOnAction(event1 -> {
            LabelCompare labelcompare = new LabelCompare();
            Collections.sort(loadedList, labelcompare);
            displayText.setText(formattedList());

        });

    }

    // method to display files of sorted linkedList called from various Compare classes
    private String formattedList() {
        String allRecords = "";
        for (Object x : loadedList) {
            String nextRecords = x.toString() + "\n";
            allRecords += nextRecords.replaceAll(":", " - ");
            allRecords = allRecords.replaceAll("#", "");
        }
        return allRecords;
    }

        //gets called when a file gets loaded from openFileButton event
    private String readFile() {
        String line = "";
        try {
            //FileReader bestand = new FileReader(file);
            BufferedReader br = new BufferedReader(new FileReader(file)); // sort for previous line parsing it directly
            line = br.readLine();
            line = formattedLine(line);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException f) {
            f.printStackTrace();
        }
        return line;
    }

    //method to display database correct when first loaded
    private String formattedLine(String line) {
        if (line == null) {
            return "";
        } else {
            String[] records = line.split("#");
            String allRecords = "";
            loadedList.clear();
            for (String x : records) {
                String[] fields = x.split(":");
                Record nextRecord = new Record(fields[0], fields[1], Integer.parseInt(fields[2]), fields[3]);
                loadedList.add(nextRecord);
                allRecords += nextRecord.toString() + "\n";
                allRecords = allRecords.replaceAll(":", " - ");
                allRecords = allRecords.replaceAll("#", "");
            }

            return allRecords;
        }
    }

        //returns name of the file loaded on openFileButton event
    private String getCurrentFileName(String filename) {
        return "Loaded: " + filename;
    }


}
