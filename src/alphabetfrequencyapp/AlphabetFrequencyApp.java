/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alphabetfrequencyapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javafx.application.Platform;
import javafx.stage.WindowEvent;

/**
 *
 * @author dav
 */
public class AlphabetFrequencyApp extends Application {
    static JFileChooser fileOpen = new JFileChooser();
    final int defaultValue = 0;
    static int total = 0;
    static HashMap<Integer, Integer> hash = new HashMap<>();
    
    @Override
    public void start(Stage stage) {             
        stage.setTitle("Alphabet Frequency App");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = new BarChart<>(xAxis,yAxis);
        bc.setTitle("Frequency of alphabets");
        xAxis.setLabel("Alphabets");       
        yAxis.setLabel("Frequency");
        
        XYChart.Series series = new XYChart.Series();
        series.setName("Total = " + Integer.toString(total));
        for(int key : hash.keySet()){
            int num = hash.get(key);
            series.getData().add(new XYChart.Data(Character.toString((char) key) + " (" + num + ")", num));
        }
        
        Scene scene  = new Scene(bc,800,600);
        bc.getData().addAll(series);
        stage.setScene(scene);
        stage.show();
        
        stage.setOnCloseRequest((WindowEvent t) -> {
            Platform.exit();
            System.exit(0);
        });
    }   
    
    // select and load file
    public static void selectFile(){
        fileOpen.showOpenDialog(new JFrame());
	loadFile(fileOpen.getSelectedFile());
    }
    
    // check if character is an alphabet
    private static boolean isAlphabet(char c){
        int lowerBound = 65;
        int upperBound = 90;
        int LowerBound = 97;
        int UpperBound = 122;
        int value = (int) c;
        return (value >= lowerBound && value <= upperBound) || (value >= LowerBound && value <= UpperBound);
    }
    
    // count alphabets in line and add to hashmap
    private static void countChar(String line){
        for(int i = 0; i < line.length(); i++){
            char c = line.charAt(i);
            if(isAlphabet(c)){
                c = Character.toUpperCase(c);
                int val = hash.getOrDefault((int) c, 0);
                hash.put((int) c, val + 1);
                total++;
            }
        }
    }
    
    // load file
    public static void loadFile(File file){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            while((line = reader.readLine()) != null){
                countChar(line);
            }
        } catch (Exception ex){
            System.out.println("Error: couldn't read file!");
        }
        
        //test
        for (int key : hash.keySet()) {
	    System.out.println((char) key + ": " + hash.get(key));
	}
        System.out.println("Total: " + total);
        //
    }    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        selectFile();
        launch(args);
    }
        
}
