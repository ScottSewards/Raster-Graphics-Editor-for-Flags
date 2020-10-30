
import java.io.*;
import javax.swing.*;

public class FileHandler { 
    String extension = ".txt";
    String delimiter = "_"; //DELIMITER FOR SEPERATING LAYER PARAMETERS
    String coldelim = "-"; //DELIMITER FOR SEPERATING COLOURS
    String spliter = "/"; //SPLIT LAYERS
    String[][] layers; //CONTAIN ALL COMMANDS
    int layerCount;
    int layerParameters;
    
    public FileHandler(int layerCount, int layerParameters) {
        this.layerCount = layerCount; //GET LAYER COUNT FROM MAIN CLASS
        this.layerParameters = layerParameters; //GET PARAMETER COUNT FROM MAIN CLASS
    }
    
    public String[][] newFile() {
        String[][] temp = new String[layerCount][layerParameters]; //CONTAIN ALL COMMANDS

        for (int i = 0; i < layerCount; i++) 
            for (int j = 0; j < layerParameters; j++) //SET EMPTY VALUES FOR EACH LAYER PARAMETER
                temp[i][j] = ""; //SET VALUE

        return temp; //RETURN EMPTY LAYERS
    }
    
    public String[][] openFile(String fileToLoad) {
        String[][] temp = new String[layerCount][layerParameters]; //CONTAIN ALL COMMANDS
        File file = new File(fileToLoad);        
        JFileChooser jfc = new JFileChooser(); //CREATE JFILECHOOSER FOR CHOOSING FILES TO LOAD
        String unionJack = "bg_background_1-33-105-255-255-255-0-0-0-0-0-0_0_0_0_0_0_0/saltire-ltr_saltireLeftToRight_255-255-255-255-255-255-0-0-0-0-0-0_3_3_0_0_0_0/saltire-rtl_saltireRightToLeft_255-255-255-255-255-255-0-0-0-0-0-0_3_3_0_0_0_0/half-topleft_saltireHalfTopLeft_200-16-46_2_0_0_3_0_0/half-topright_saltireHalfTopRight_200-16-46_0_2_1_1_0_0/half-bottomright_saltireHalfBottomRight_200-16-46_2_0_0_3_0_0/half-bottomleft_saltireHalfBottomLeft_200-16-46_0_2_1_0_0_0/white-cross-h_crossHorizontal_255-255-255_10_0_0_0_0_0/white-cross-v_crossVertical_255-255-255_10_0_0_0_0_0/layer-8_crossVertical_200-16-46_6_0_0_0_0_0/layer-7_crossHorizontal_200-16-46-255-255-255-0-0-0-0-0-0_6_0_0_0_0_0/layer-9________/________/________/________/________/________/________/________/________/________/________/________/________/________/________/________/________/________/";
        int returnValue = 0;
        

        if (fileToLoad == null && fileToLoad == "") returnValue = jfc.showOpenDialog(null); //OPEN FILE CHOOSER IF NOT FILE IS PASSED

        if (returnValue == JFileChooser.APPROVE_OPTION) file = jfc.getSelectedFile(); //GET FILE FROM FILE CHOOSER

        try { //PREVENTS ERROR
            String[] layers = null;
            BufferedReader br = null; //CREATE READER TO READ FILES
            
            if (fileToLoad == "uj") layers = unionJack.split(spliter);
            else {
                br = new BufferedReader(new FileReader(file));
                layers = br.readLine().split(spliter); //GET STRING FROM FILE AND SPLIT INTO LAYERS            }
                br.close();
            }

            for (int i = 0; i < layers.length; i++) { //LOOP THROUGH LAYERS TO GET PARAMETER VALUES
                String[] parameters = layers[i].split(delimiter); //SPLIT LAYER INTO PARAMETERS
                for (int j = 0; j < layerParameters; j++) temp[i][j] = parameters[j] == null ? "" : parameters[j]; //ASSIGN TEMP LAYER PARAMETER VALUE
            }            
        }
        catch (Exception e) {}

        return temp; //RETURN LAYERS TO MAIN CLASS
    }
    
    public void saveFile(JFrame frame, String[][] layers) {
        String name = JOptionPane.showInputDialog(frame, "Enter a file name: "); //PROMPT NAME TO SAVE FILE

        try { //PREVENT ERROR
            FileWriter fw = new FileWriter(name + extension); //CREATE WRITER TO WRITE TO SAVE FILE
            
            for (int i = 0; i < layerCount; i++) //LOOP THROUGH LAYERS
                for (int j = 0; j < layerParameters; j ++) fw.write((layers[i][j] == "" || layers[i][j] == null ? "" : layers[i][j]) + (j == layerParameters - 1 ? spliter : delimiter)); //WRITE PARAMETER TO SAVE FILE
                
            fw.close();    
        } 
        catch(Exception e) {}
    }
}
