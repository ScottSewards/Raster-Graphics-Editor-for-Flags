
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class SewardsSEx2 extends JPanel {
    final JFrame frame = new JFrame("SewardsSEx2"); //INSTANTIATE FRAME FOR CONTENT
    static int frameSizeX = 1400, frameSizeY = 700; //INITIALISE FRAME SIZE
    JList list; //JLIST WILL LIST LAYERS
    JLabel p1 = new JLabel("p1"), p2 = new JLabel("p2"), p3 = new JLabel("p2"), p4 = new JLabel("p2"), p5 = new JLabel(), p6 = new JLabel();
    JSpinner p1i = new JSpinner(), p2i = new JSpinner(), p3i = new JSpinner(), p4i = new JSpinner(), p5i = new JSpinner(), p6i = new JSpinner();
    JButton specialButton = new JButton(""), setColour1Button = new JButton("Set Colour"), setColour2Button = new JButton("Set Second Colour"), 
            setColour3Button = new JButton("Set Third Colour"), setColour4Button = new JButton("Set Fouth Colour");

    int gridSize = 3; //HIDE = 0, SMALL = 1, MEDIUM = 2, LARGE = 3
    static int layerCount = 30; //LAYER COUNT
    static int layerParameters = 9; //NUMBER OF LAYER PARAMETERS
    int li = 0; //LAYER INDEX
    String[][] layers = new String[layerCount][layerParameters]; //STORES LAYERS AND LAYER PARAMETERS/0 = NAME, 1 = METHOD, 2 = COLOUR, 3-8 PARAMETERS 
    String[] layerList = {""}; //STORES LAYER NAMES FROM LAYERS FOR LISTING IN JLIST
    Color[] colour = {Color.BLACK, Color.WHITE, Color.BLACK, Color.BLACK}; //COLORS TO BE CONVERTED TO LAYER COLOUR STRINGS/1-3 FOR BANDS

    DrawFlags df = new DrawFlags(); //CREATE INSTANCE OF DRAW FLAG TO CALL METHODS TO DRAW SHAPES
    FileHandler fh = new FileHandler(layerCount, layerParameters); //INSTANCE OF FILE HANDLER TO MAKE NEW FLAGS, LOAD AND SAVE
    //BufferedImage ensign;
    
    public SewardsSEx2() {
        new Timer(100, (ActionEvent e) -> { //THIS TIMER IS CALLED EVERY 0.1 SECONDS TO REPAINT
            repaint(); //THIS METHOD REPAINTS
        }).start();
    }
    
    public void initiateWindow(SewardsSEx2 program) {
        //INSTANTIATE A CONTAINER FOR FRAME CONTENT
        Container container = frame.getContentPane();
        container.setLayout(new BorderLayout());

        //INSTANTIATE JMENU ITEMS FOR ADDING TO JMENU BAR
        JMenuItem newFlag = new JMenuItem("New Flag");
        JMenuItem openFlag = new JMenuItem("Open Flag");
        JMenuItem saveFlag = new JMenuItem("Save Flag");
        JMenuItem createLayer = new JMenuItem("Create Layer");
        JMenuItem renameLayer = new JMenuItem("Rename Layer");
        //JMenuItem duplicateLayer = new JMenuItem("Duplicate Layer");
        JMenuItem clearLayer = new JMenuItem("Clear Layer");
        JMenuItem deleteLayer = new JMenuItem("Delete Layer");
        JMenuItem background = new JMenuItem("Add Background");
        JMenuItem band = new JMenuItem("Add Band");
        JMenuItem circle = new JMenuItem("Add Circle");
        JMenuItem cross = new JMenuItem("Add Cross");
        //JMenuItem ensign = new JMenuItem("Add Ensign");
        JMenuItem halfSalt = new JMenuItem("Add Half Saltire");
        JMenuItem saltire = new JMenuItem("Add Saltire");
        JMenuItem shevron = new JMenuItem("Add Shevron");
        JMenuItem star = new JMenuItem("Add Star");
        //JMenuItem undo = new JMenuItem("Undo");
        //JMenuItem redo = new JMenuItem("Redo");

        //INSTANTIATE RADIO BUTTONS FOR GRID
        JRadioButton gridHide = new JRadioButton("Hide Grid");        
        JRadioButton gridSmall = new JRadioButton("Show Small Grid");
        JRadioButton gridMedium = new JRadioButton("Show Medium Grid");
        JRadioButton gridLarge = new JRadioButton("Show Large Grid");

        //INSTANTIATE RADIO BUTTONS FOR FIXED RATIO SIZES
        //JRadioButton ratio35 = new JRadioButton("3:5");        
        //JRadioButton ratio12 = new JRadioButton("1:2");
        //JCheckBoxMenuItem animate = new JCheckBoxMenuItem("Animate");

        //INSTANTIATE A BUTTON GROUP FOR ABOVE RADIO BUTTONS
        ButtonGroup gridGroup = new ButtonGroup();
        //ButtonGroup ratioGroup = new ButtonGroup();

        //INSTANTIATE MENU COMPONENTS FOR MENU BAR TO WHICH MENU ITEMS WILL BE ADDED
        JMenu file = new JMenu("File");
        //JMenu edit = new JMenu("Edit");
        JMenu layer = new JMenu("Layers");
        JMenu shape = new JMenu("Shapes");
        JMenu tools = new JMenu("Tools");
        JMenu grid = new JMenu("Grid");
        //JMenu ratio = new JMenu("Ratio");
        
        //ADD BUTTONS TO RESPECTIVE JMENUITEM
        file.add(newFlag);
        file.add(openFlag);
        file.add(saveFlag);
        //edit.add(undo);
        //edit.add(redo);
        layer.add(createLayer);
        layer.add(renameLayer);
        //layer.add(duplicateLayer);
        layer.add(new JSeparator());
        layer.add(clearLayer);
        layer.add(deleteLayer);
        shape.add(background);
        shape.add(band);
        shape.add(circle);
        shape.add(cross);
        //shape.add(ensign);
        shape.add(saltire);
        shape.add(halfSalt);
        shape.add(shevron);
        shape.add(star);
        //tools.add(animate);
        //tools.add(new JSeparator());
        tools.add(grid);
        grid.add(gridHide);
        grid.add(gridSmall);
        grid.add(gridMedium);
        grid.add(gridLarge);
        //ADD RADIO BUTTONS TO GROUP SO ONLY ONE IS SELECTED AT A TIME
        gridGroup.add(gridHide);
        gridGroup.add(gridSmall);
        gridGroup.add(gridMedium);
        gridGroup.add(gridLarge);
        
        JMenuBar menu = new JMenuBar();//CREATE JMENUBAR TO ADD JMENUITEMS
        menu.add(file);
        //menu.add(edit);
        menu.add(layer);
        menu.add(shape);
        menu.add(tools);

        //ACTIONLISTENERS AND CHANGELISTENERS TO JCOMPONENTS TO ADD FUNCTIONALITY
        newFlag.addActionListener((ActionEvent e) -> {layers = fh.newFile();});
        openFlag.addActionListener((ActionEvent e) -> {layers = fh.openFile("");});
        saveFlag.addActionListener((ActionEvent e) -> {fh.saveFile(frame, layers);});
        createLayer.addActionListener((ActionEvent e) -> {createLayer(false);});
        renameLayer.addActionListener((ActionEvent e) -> {nameLayer(false);});
        //duplicateLayer.addActionListener((ActionEvent e) -> {duplicateLayer();});
        clearLayer.addActionListener((ActionEvent e) -> {clearLayer();});
        deleteLayer.addActionListener((ActionEvent e) -> {deleteLayer();});
        background.addActionListener((ActionEvent e) -> {setMethod("background", new Color[] {this.colour[0]}, 0, 0, 0, 0, 0, 0);});
        band.addActionListener((ActionEvent e) -> {setMethod("bandHorizontal", this.colour, 1, 1, 1, 0, 0, 0);});
        circle.addActionListener((ActionEvent e) -> {setMethod("circle", new Color[] {this.colour[0]}, 5, 0, 0, 0, 0, 0);});
        cross.addActionListener((ActionEvent e) -> {setMethod("crossHorizontal", new Color[] {this.colour[0]}, 10, 0, 0, 0, 0, 0);});
        //ensign.addActionListener((ActionEvent e) -> {setMethod("ensign", new Color[] {this.colour[0]}, 10, 10, 0, 0, 0, 0);});
        saltire.addActionListener((ActionEvent e) -> {setMethod("saltireRightToLeft", new Color[] {this.colour[0]}, 5, 5, 0, 0, 0, 0);});
        halfSalt.addActionListener((ActionEvent e) -> {setMethod("saltireHalfTopLeft", new Color[] {this.colour[0]}, 2, 2, 1, 1, 0, 0);});
        shevron.addActionListener((ActionEvent e) -> {setMethod("shevron", new Color[] {this.colour[0]}, 10, 0, 0, 0, 0, 0);});
        star.addActionListener((ActionEvent e) -> {setMethod("star", new Color[] {this.colour[0]}, 5, 8, 4, 0, 0, 0);});
        //undo.addActionListener((ActionEvent e) -> {undo();});
        //redo.addActionListener((ActionEvent e) -> {redo();});
        gridHide.addActionListener((ActionEvent e) -> {gridSize = 0;});     
        gridSmall.addActionListener((ActionEvent e) -> {gridSize = 1;});
        gridMedium.addActionListener((ActionEvent e) -> {gridSize = 2;});
        gridLarge.addActionListener((ActionEvent e) -> {gridSize = 3;});
        
        JPanel panel = new JPanel(); //CREATE PANEL TO ADD BUTTONS AND LABELS
        panel.setLayout(new GridBagLayout()); //SET GRIDBAGLAYOUT
        GridBagConstraints gbc = new GridBagConstraints(); //CREATE CONSTRAINTS

        setMethod("circle", new Color[] {this.colour[1]}, 5, 0, 0, 0, 0, 0); //PREVENTS AN ERROR
        setParameterLabels("", "", "", "", "", "", ""); //PREVENTS AN ERROR

        setGrid(gbc, 0, 0, 1, 1); //SET GRID CONSTRAINTS SUCH AS X AND Y POSITIONS USING CUSTOM METHOD
        setColour1Button.addActionListener((ActionEvent e) -> { //ADD FUNCTIONALITY TO COLOUR BUTTON
            Color[] temp = this.colour; //CREATE TEMP COLOUR
            this.colour[0] = JColorChooser.showDialog(null, "Change Colour", this.colour[0]); //PROMPT COLOUR CHANGE
            if (this.colour[0] != null) setColour(this.colour); //IF COLOUR IS SELECTED NEW COLOUR OVERRIDES PREVIOUS
            else setColour(temp); //RESTORE ORIGINAL COLOUR IF CANCELLED
        });
        panel.add(setColour1Button, gbc); //ADD COMPONENT TO PANEL

        setGrid(gbc, 0, 1, 1, 1);
        setColour2Button.addActionListener((ActionEvent e) -> {
            Color[] temp = this.colour;
            this.colour[1] = JColorChooser.showDialog(null, "Change Colour", this.colour[1]);
            if (this.colour[1] != null) setColour(this.colour);
            else setColour(temp);
        });
        panel.add(setColour2Button, gbc);

        setGrid(gbc, 0, 2, 1, 1);
        setColour3Button.addActionListener((ActionEvent e) -> {
            Color[] temp = this.colour;
            this.colour[2] = JColorChooser.showDialog(null, "Change Colour", this.colour[2]);
            if (this.colour[2] != null) setColour(this.colour);
            else setColour(temp);
        });
        panel.add(setColour3Button, gbc);

        setGrid(gbc, 0, 3, 1, 1);
        setColour4Button.addActionListener((ActionEvent e) -> {
            Color[] temp = this.colour;
            this.colour[3] = JColorChooser.showDialog(null, "Change Colour", this.colour[3]);
            if (this.colour[3] != null) setColour(this.colour);
            else setColour(temp);
        });
        panel.add(setColour4Button, gbc);
        
        setGrid(gbc, 0, 4, 1, 1);
        specialButton.addActionListener((ActionEvent e) -> { //CHANGES DRAW METHOD WHEN SPECIAL BUTTON IS CLICKED SUCH AS ROTATING A SHAPE BY ASSIGNING A DIFFERENT DRAW METHOD
            switch (layers[li][1]) {
                case "bandHorizontal":
                    layers[li][1] = "bandVertical";
                    break;
                case "bandVertical":
                    layers[li][1] = "bandHorizontal";
                    break;
                case "crossHorizontal":
                    layers[li][1] = "crossVertical";
                    break;
                case "crossVertical":
                    layers[li][1] = "crossHorizontal";
                    break;
                case "saltireRightToLeft":
                    layers[li][1] = "saltireLeftToRight";
                    break;
                case "saltireLeftToRight":
                    layers[li][1] = "saltireRightToLeft";
                    break;
                case "saltireHalfTopLeft":
                    layers[li][1] = "saltireHalfTopRight";
                    break;
                case "saltireHalfTopRight":
                    layers[li][1] = "saltireHalfBottomRight";
                    break;
                case "saltireHalfBottomRight":
                    layers[li][1] = "saltireHalfBottomLeft";
                    break;
                case "saltireHalfBottomLeft":
                    layers[li][1] = "saltireHalfTopLeft";
                    break;
                }
        });
        panel.add(specialButton, gbc);

        setGrid(gbc, 0, 5, 1, 1);
        panel.add(p1, gbc);

        setGrid(gbc, 0, 6, 1, 1);
        p1i.addChangeListener((ChangeEvent e) -> {layers[li][3] = p1i.getValue().toString();});
        panel.add(p1i, gbc);

        setGrid(gbc, 0, 7, 1, 1);
        panel.add(p2, gbc);

        setGrid(gbc, 0, 8, 1, 1);
        p2i.addChangeListener((ChangeEvent e) -> {layers[li][4] = p2i.getValue().toString();});
        panel.add(p2i, gbc);

        setGrid(gbc, 0, 9, 1, 1);
        panel.add(p3, gbc);

        setGrid(gbc, 0, 10, 1, 1);
        p3i.addChangeListener((ChangeEvent e) -> {layers[li][5] = p3i.getValue().toString();});
        panel.add(p3i, gbc);

        setGrid(gbc, 0, 11, 1, 1);
        panel.add(p4, gbc);

        setGrid(gbc, 0, 12, 1, 1);
        p4i.addChangeListener((ChangeEvent e) -> {layers[li][6] = p4i.getValue().toString();});
        panel.add(p4i, gbc);

        setGrid(gbc, 0, 13, 1, 1);
        panel.add(p5, gbc);
        
        setGrid(gbc, 0, 14, 1, 1);
        p5i.addChangeListener((ChangeEvent e) -> {layers[li][7] = p5i.getValue().toString();});
        panel.add(p5i, gbc);
        
        setGrid(gbc, 0, 15, 1, 1);
        panel.add(p6, gbc);
        
        setGrid(gbc, 0, 16, 1, 1);
        p6i.addChangeListener((ChangeEvent e) -> {layers[li][8] = p6i.getValue().toString();});
        panel.add(p6i, gbc);
                
        setGrid(gbc, 0, 17, 1, 1);
        JButton moveUpButton = new JButton("Move Layer Up");
        panel.add(moveUpButton, gbc);
        moveUpButton.addActionListener((ActionEvent e) -> {moveLayer(Direction.UP);});

        setGrid(gbc, 0, 18, 1, 1);
        list = new JList(layerList); //LIST IS ASSIGNED LAYER NAMES
        list.addListSelectionListener((ListSelectionEvent e) -> { 
            li = list.getSelectedIndex(); //LIST INDEX IS ASSIGNED JLIST SELECTED ITEM
            if (li < 0) li = 0; //CLAMPS RANGE BELOW 0
            else if (li > list.getModel().getSize()) li = list.getModel().getSize(); //CLAMP RANGE ABOVE LIST SIZE
            updateParameters(); //UPDATE PARAMETER FIELDS WHEN A DIFFERENT LAYER IS SELECTED
        });
        list.setBorder(BorderFactory.createLineBorder(Color.black)); //ADD
        panel.add(list, gbc);

        setGrid(gbc, 0, 19, 1, 1);
        JButton moveDownButton = new JButton("Move Layer Down");
        panel.add(moveDownButton, gbc);
        moveDownButton.addActionListener((ActionEvent e) -> {moveLayer(Direction.DOWN);});
        
        setParameters(false, 0, 1);
        
        container.add(panel, BorderLayout.EAST);
        //INITIALISE FRAME USING VARIOUS METHODS
        frame.setJMenuBar(menu); //SET FRAME MENU BAR AS MENU
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //SET WINDOW BAR CLOSE TO CLOSE WINDOW UPON OPERATION            
        frame.setMinimumSize(new Dimension(frameSizeX, frameSizeY)); //SET MINIMUM FRAME SIZE SO THE FRAME CANNOT BE SMALLER THAN THE PASSED DIMENSION
        frame.setSize(frameSizeX, frameSizeY); //SET FRAME SIZE TO SHOW UPON FOR EXECITION
        frame.getContentPane().add(program); //ADD THIS PROGRAM (SEWARDSEX2) TO BE SHOW IN THE FRAME PANEL
        frame.setVisible(true); //SET WINDOW AS VISIBLE
        
        for (int i = 0; i < layerCount; i++) 
            for (int j = 0; j < layerParameters; j++) //SET EMPTY VALUES FOR EACH LAYER PARAMETER
                this.layers[i][j] = ""; //SET VALUE

        layers = fh.openFile("uj"); //AUTOMATICALLY LOAD UNION JACK
        updateLayerList();
        updateParameters();
    }
    
    public static void main(String[] args) {
        SewardsSEx2 program = new SewardsSEx2(); //CREATE AN INSTANCE OF THIS CLASS
        program.initiateWindow(program); //INITALISE WINDOW GUI
    }
        
    @Override
    public void paintComponent(Graphics g) { //DRAW A FLAG
        super.paintComponent(g); 
        Graphics2D gtd = (Graphics2D)g; //CONVERT GRAPHICS TO GRAPHICS2D
        df.setRatio(frame, new Dimension(2, 1)); //SET FLAG RATIO
        df.drawBackground(gtd, Color.WHITE); //DRAW WHITE BACKGROUND FOR CONTRAST
             
        for(int i = 0; i < layers.length; i++) { //LOOP THROUGH LAYERS TO DRAW EACH
            if(layers[i][1] == null || layers[i][1] == "") continue; //IF NO SHAPE METHOD IS SET CONTINUE NEXT LOOP
            
            String[] split = layers[i][2].split(fh.coldelim); //SPLIT CONCANTENATED COLOURS VALUES INTO SEPERATE VALUES
            Color[] colour = new Color[split.length]; //CREATE A COLOUR USING 3 COLOUR VALUES
            for(int j = 0; j < split.length / 3; j++) 
                colour[j] = new Color(Integer.valueOf(split[0 + (j * 3)]), Integer.valueOf(split[1 + (j * 3)]), Integer.valueOf(split[2 + (j * 3)])); //CONVERT COLOUR VALUES FROM STRING TO COLOR
            
            try { //PREVENT ERROR CAUSING PROGRAM TO STICK
                switch(layers[i][1]) { //CHECK LAYER SHAPE METHOD USING INDEX
                    case "background": //CHECK LAYER SHAPE METHOD IS THIS VALUE
                        df.drawBackground(gtd, colour[0]); //CALL RESPECTIVE METHOD IN DRAWFLAG
                        break;
                    case "bandHorizontal": //LAYERS ARE STORED AS STRINGS, THUS PARAMETERS MUST BE CONVERTED TO INTEGERS
                        df.drawBandHorizontal(gtd, colour, new int[] {Integer.valueOf(layers[i][3]), Integer.valueOf(layers[i][4]), Integer.valueOf(layers[i][5]), Integer.valueOf(layers[i][6])});
                        break;
                    case "bandVertical":
                        df.drawBandVertical(gtd, colour, new int[] {Integer.valueOf(layers[i][3]), Integer.valueOf(layers[i][4]), Integer.valueOf(layers[i][5]), Integer.valueOf(layers[i][6])}); 
                        break;
                    case "circle":
                        df.drawCircle(gtd, colour[0], Integer.valueOf(layers[i][3]), Integer.valueOf(layers[i][4]), Integer.valueOf(layers[i][5])); 
                        break;
                    case "crossHorizontal":
                        df.drawCrossHorizontal(gtd, colour[0], Integer.valueOf(layers[i][3]), Integer.valueOf(layers[i][4]));
                        break;
                    case "crossVertical":
                        df.drawCrossVertical(gtd, colour[0], Integer.valueOf(layers[i][3]), Integer.valueOf(layers[i][4])); 
                        break;
                    //case "ensign":
                        //df.drawEnsign(gtd, colour[0], Integer.valueOf(layers[i][3]), Integer.valueOf(layers[i][4]));
                        //break;
                    case "saltireRightToLeft":
                        df.drawSaltireRightToLeft(gtd, colour[0], Integer.valueOf(layers[i][3]), Integer.valueOf(layers[i][4]));
                        break;
                    case "saltireLeftToRight":
                        df.drawSaltireLeftToRight(gtd, colour[0], Integer.valueOf(layers[i][3]), Integer.valueOf(layers[i][4])); 
                        break;
                    case "saltireHalfTopLeft":
                        df.drawSaltireHalf(gtd, colour[0], Integer.valueOf(layers[i][3]), Integer.valueOf(layers[i][4]), Integer.valueOf(layers[i][5]), Integer.valueOf(layers[i][6]), false, false);
                        break;
                    case "saltireHalfTopRight":
                        df.drawSaltireHalf(gtd, colour[0], Integer.valueOf(layers[i][3]), Integer.valueOf(layers[i][4]), Integer.valueOf(layers[i][5]), Integer.valueOf(layers[i][6]), true, false);
                        break;
                    case "saltireHalfBottomRight":
                        df.drawSaltireHalf(gtd, colour[0], Integer.valueOf(layers[i][3]), Integer.valueOf(layers[i][4]), Integer.valueOf(layers[i][5]), Integer.valueOf(layers[i][6]), true, true);
                        break;
                    case "saltireHalfBottomLeft":
                        df.drawSaltireHalf(gtd, colour[0], Integer.valueOf(layers[i][3]), Integer.valueOf(layers[i][4]), Integer.valueOf(layers[i][5]), Integer.valueOf(layers[i][6]), false, true);
                        break;
                    case "shevron":
                        df.drawShevron(gtd, colour[0], Integer.valueOf(layers[i][3])); 
                        break;
                    case "star":
                        df.drawStar(gtd, colour[0], Integer.valueOf(layers[i][3]), Integer.valueOf(layers[i][4]), Integer.valueOf(layers[i][5]), Integer.valueOf(layers[i][6]), Integer.valueOf(layers[i][7]), 
                                Integer.valueOf(layers[i][8]));
                        break;
                    default: 
                        break;
                }            
            }
            catch(Exception e) {}
        }

        if(gridSize != 0) df.drawGrid(gtd, Color.BLACK, gridSize); //DRAWS A GRID
        //df.drawBorder(gtd);
    }

    public void clearLayer() {
        layers[li][1] = ""; //REMOVES SHAPE METHOD ASSIGNED SO NO SHAPE IS DRAWN  
        updateParameters(); //UPDATES PARAMETERS TO REFLET NO SHAPE METHOD IS AVAILABLE TO PASS TO
    }
    
    public void createLayer(boolean createByForce) {
        for(int i = 0; i < layers.length; i++){ //LOOP THROUGH LAYERS TO FIND NEXT EMPTY LAYER      
            if(layers[i][0] == null || layers[i][0] == "") { //LAYER IS EMPTY IF NAME IS NULL OR ""
                li = i; //NEXT LAYER INDEX IS FOR INDEX
                if (createByForce) nameLayer(true); //FORCE LAYER CREATION WITH DEFAULT NAME
                else nameLayer(false); //GIVE NEW LAYER CUSTOM NAME
                break;
            }
        }
    }
    
    public void deleteLayer() {
        layers[li][0] = ""; //REMOVES LAYER NAME SO IT CANNOT BE ADDED TO THE LIST WHEN UPDATED
        updateLayerList(); //CALL METHOD TO REMOVE THIS LAYER FROM THE LIST BECAUSE IT HAS NO SHAPE METHOD ASSIGNED
        updateParameters(); //UPDATES PARAMETERS TO REFLET NO SHAPE METHOD IS AVAILABLE TO PASS TO
    }
    
    /* UNUSED METHOD
    public void duplicateLayer() {
        String[] duplicate = new String[layerParameters]; //CREATE TEMPORARY LAYER
        for (int i = 0; i < layerParameters; i++) duplicate[i] = layers[li][i]; //ASSIGN SELECTED LAYER PARAMETERS TO TEMP LAYER
        createLayer(false);
        for (int i = 0; i < layerParameters; i++) layers[li][i] = duplicate[i]; //ASSIGN TEMP LAYER PARAMETERS TO TEMP LAYER        
    }
    */
    
    enum Direction {TOP, UP, DOWN, BOTTOM}
    public void moveLayer(Direction direction){
        String[] temp; //STORES A LAYER TEMPORARILY
        switch(direction) { 
            case TOP: 
                temp = layers[li]; //ASSIGN TEMP LAYER SELECTED LAYER
                layers[0] = layers[li]; //OVERRIDE TOP LAYER WITH SELECTED LAYER
                layers[li] = temp; //ASSIGN ORIGINAL SELECTED LAYER POSITION TEMP LAYER PARAMETERS
                break;
            case UP: 
                if (li == 0) return; //STOPS LAYER MOVING OUT OF INDEX
                temp = layers[li - 1];
                layers[li - 1] = layers[li]; //MOVE UP 1 POSITION
                layers[li] = temp;
                break;
            case DOWN: 
                if (li >= list.getModel().getSize() - 1) return; //STOP LAYER MOVING OUT OF INDEX
                temp = layers[li + 1];
                layers[li + 1] = layers[li]; //MOVE DOWN 1 POSITION
                layers[li] = temp;
                break;
            case BOTTOM:
                temp = layers[li];
                layers[list.getModel().getSize()] = layers[li]; //MOVE LAYER TO BOTTOM
                layers[li] = temp;
                break;
        }
        
        updateLayerList();
    }
    
    public void nameLayer(boolean useDefaultName) {
        if (useDefaultName) layers[li][0] = "layer-" + li; //DEFAULT NAME IS "LAYER" + LAYER INDEX
        else layers[li][0] = JOptionPane.showInputDialog(frame, "Enter a name for the layer:", layers[li][0] != null && layers[li][0] != "" ? layers[li][0] : "layer-" + li); //RENAME IF LAYER IS NAMED ELSE USE DEFAULT NAME
        updateLayerList(); //UPDATE LAYER LIST TO REFLECT LAYER NAMING
    }
     
    public void setColour(Color[] colour) {
        try { //PREVENTS ERROR
            layers[li][2] = ""; //RESET THE VARIABLE VALUE

            for (int i = 0; i < colour.length; i++) { //LOOP THROUGH COLOUR ARRAY FOR COLOUR VALUES
                if (i == colour.length - 1) layers[li][2] += colour[i].getRed() + fh.coldelim + colour[i].getGreen() + fh.coldelim + colour[i].getBlue(); //DONT ADD DELIMITER TO FINAL COLOUR                                                            
                else layers[li][2] += colour[i].getRed() + fh.coldelim + colour[i].getGreen() + fh.coldelim + colour[i].getBlue() + fh.coldelim; //ADD COLOUR VALUES AS STRINGS TO LAYER    
            }
        }
        catch (Exception e) {}
    }    
    
    public void setGrid(GridBagConstraints gbc, int x, int y, int width, int height) {
        gbc.fill = GridBagConstraints.HORIZONTAL; //ALIGN COMPONENTS IN GRIDBAGLAYOUT HORIZONTALLY
        gbc.anchor = GridBagConstraints.PAGE_END; //ANCHOR COMPONENTS TO FRAME BOTTOM
        gbc.insets = new Insets(5, 5, 5, 5); //ADD PADDING TO COMPONENTS
        gbc.gridx = x; //SET X-AXIS GRID CELL TO ADD COMPONENT INTO
        gbc.gridy = y; //SET Y-AXIS GRID CELL TO ADD COMPONENT INTO
        gbc.gridwidth = width; //SET WHETHER COMPONENT IS ADDED INTO MORE THAN ONE CELL ON Y-AXIS     
        gbc.gridheight = height; //SET WHETHER COMPONENT IS ADDED INTO MORE THAN ONE CELL ON Y-AXIS     
    }

    public void setMethod(String method, Color[] colour, int p1, int p2, int p3, int p4, int p5, int p6) {
        layers[li][1] = method; //SET DRAW SHAPE METHOD
        setColour(colour); //CALL METHOD TO CONVERT COLOURS TO STRING
        layers[li][3] = Integer.toString(p1); //SET PARAMETER 1, THIS COULD BE LENGTH OR OFFSET, THESE ARE GENERAL PURPOSE VARIABLES
        layers[li][4] = Integer.toString(p2); //SET PARAMETER 2
        layers[li][5] = Integer.toString(p3); //SET PARAMETER 3
        layers[li][6] = Integer.toString(p4); //SET PARAMETER 4
        layers[li][7] = Integer.toString(p5); //SET PARAMETER 4
        layers[li][8] = Integer.toString(p6); //SET PARAMETER 4
        updateParameters(); //SHOW RELEVANT PARAMETERS FOR ASSIGNED DRAW METHOD
    }

    private void setParameters(boolean button, int parameters, int colours) {
        try { //PREVENTS ERROR
            this.specialButton.setVisible(button);        
            this.p1.setEnabled(parameters > 0 ? true : false); //SET HOW MANY PARAMETERS LABELS TO SHOW UPTO 6 MAXIMUM
            this.p2.setEnabled(parameters > 1 ? true : false);
            this.p3.setEnabled(parameters > 2 ? true : false);
            this.p4.setEnabled(parameters > 3 ? true : false);
            this.p5.setEnabled(parameters > 4 ? true : false);
            this.p6.setEnabled(parameters > 5 ? true : false);
            this.p1i.setEnabled(parameters > 0 ? true : false); //SET HOW MANY PARAMETERS LABELS TO SHOW UPTO 6 MAXIMUM
            this.p2i.setEnabled(parameters > 1 ? true : false);
            this.p3i.setEnabled(parameters > 2 ? true : false);
            this.p4i.setEnabled(parameters > 3 ? true : false);
            this.p5i.setEnabled(parameters > 4 ? true : false);
            this.p6i.setEnabled(parameters > 5 ? true : false);
            this.p1i.setValue(layers[li][3] != null || layers[li][3] != "" ? Integer.valueOf(layers[li][3]) : 0); //SET VALUE OF LABELS FROM LAYER PARAMETERS
            this.p2i.setValue(layers[li][4] != null || layers[li][4] != "" ? Integer.valueOf(layers[li][4]) : 0);
            this.p3i.setValue(layers[li][5] != null || layers[li][5] != "" ? Integer.valueOf(layers[li][5]) : 0);
            this.p4i.setValue(layers[li][6] != null || layers[li][6] != "" ? Integer.valueOf(layers[li][6]) : 0);
            this.p5i.setValue(layers[li][7] != null || layers[li][7] != "" ? Integer.valueOf(layers[li][7]) : 0);
            this.p6i.setValue(layers[li][8] != null || layers[li][8] != "" ? Integer.valueOf(layers[li][8]) : 0);
            this.setColour1Button.setVisible(colours > 0 ? true : false); //SET HOW MANY COLOUR PARAMETERS TO SHOW UPTO 4 MAXIMUM, USED FOR BAND METHOD
            this.setColour2Button.setVisible(colours > 1 ? true : false);
            this.setColour3Button.setVisible(colours > 2 ? true : false);
            this.setColour4Button.setVisible(colours > 3 ? true : false);        
        }
        catch (Exception e) {}
    }
    
    private void setParameterLabels(String button, String p1, String p2, String p3, String p4, String p5, String p6) {
        this.specialButton.setText(button); //SET SPECIAL BUTTON TEXT  
        this.p1.setText(p1 == "" ? "Null" : p1); //SET PARAMETER LABEL TEXT
        this.p2.setText(p2 == "" ? "Null" : p2);
        this.p3.setText(p3 == "" ? "Null" : p3);
        this.p4.setText(p4 == "" ? "Null" : p4);
        this.p5.setText(p5 == "" ? "Null" : p5);
        this.p6.setText(p6 == "" ? "Null" : p6);
    }
    
    public void updateLayerList() {
        DefaultListModel dlm = new DefaultListModel(); //INSTANTIATE A NEW MODEL TO ADD LIST ELEMENTS
        for (int i = 0; i < layers.length; i++) if(layers[i][0] != null && layers[i][0] != "") dlm.addElement(layers[i][0]); //LOOP THROUGH LAYERS AND ADD LAYERS WITH NAMES TO MODEL
        list.setModel(dlm); //SET LIST MODEL TO NEW MODEL
        list.setSelectedIndex(li);
    }
    
    private void updateParameters() {
        try {
            switch (layers[li][1]) { //GET LAYER DRAW METHOD
                case "": //CHECK LAYER DRAW METHOD
                    setParameters(false, 0, 0); //SET ENABLED PARAMETERS
                    setParameterLabels("Null", "Null", "Null", "Null", "Null", "Null", "Null"); //SET PARAMETER LABELS
                    break;
                case "background":
                    setParameters(false, 0, 1);
                    setParameterLabels("Null", "Null", "Null", "Null", "Null", "Null", "Null");
                    break;
                case "bandHorizontal":
                case "bandVertical":
                    setParameters(true, 4, 4);
                    setParameterLabels("Rotate", "Band 1", "Band 2", "Band 3", "Band 4", "Null", "Null");
                    //setParameterLimiters(); //THIS WOULD HAVE ADDED LIMITS TO PARAMETER FIELDS
                    break;
                case "circle":
                    setParameters(false, 3, 1);
                    setParameterLabels("", "Radius", "Offset X", "Offset Y", "Null", "Null", "Null");
                    break;
                case "crossHorizontal":
                case "crossVertical":
                    setParameters(true, 2, 1);
                    setParameterLabels("Rotate", "Thickness", "Offset", "Null", "Null", "Null", "Null");
                    break;
                case "ensign":
                    setParameters(false, 2, 1);
                    setParameterLabels("", "Height", "Width", "Null", "Null", "Null", "Null");
                    break;
                case "saltireRightToLeft":
                case "saltireLeftToRight":
                    setParameters(true, 2, 1);
                    setParameterLabels("Rotate", "Thickness X", "Thickness Y", "Null", "Null", "Null", "Null");
                    break;
                case "saltireHalfTopLeft":
                case "saltireHalfTopRight":
                case "saltireHalfBottomRight":
                case "saltireHalfBottomLeft":
                    setParameters(true, 4, 1);
                    setParameterLabels("Rotate", "Thickness Top-Left", "Thickness Top-Right", "Thickness Bottom-Right", "Thickness Bottom-Left", "Null", "Null");
                    break;
                case "shevron":
                    setParameters(false, 1, 1);
                    setParameterLabels("Null", "Width", "Null", "Null", "Null", "Null", "Null");
                    break;
                case "star":
                    setParameters(false, 6, 1);
                    setParameterLabels("", "Points", "Outer Radius", "Inner Radius", "Offset X", "Offset Y", "Rotation");
                    break;
                }        
        }
        catch (Exception e) {
        
        }
    }
    
    /*UNUSED METHOD
    private void setParamterLimiters(int p1min, int p1max, int p2min, int p2max, int p3min, int p3max, int p4min, int p4max, int p5min, int p5max, int p6min, int p6max){
        p1i = new JSpinner(new SpinnerNumberModel(0, p1min, p1max, 1));
        p2i = new JSpinner(new SpinnerNumberModel(0, p2min, p2max, 1));
        p3i = new JSpinner(new SpinnerNumberModel(0, p3min, p3max, 1));
        p4i = new JSpinner(new SpinnerNumberModel(0, p4min, p4max, 1));
        p5i = new JSpinner(new SpinnerNumberModel(0, p5min, p5max, 1));
        p6i = new JSpinner(new SpinnerNumberModel(0, p6min, p6max, 1));
    }
    */
}