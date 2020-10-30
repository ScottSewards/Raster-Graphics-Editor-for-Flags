
import java.awt.*;
import java.util.stream.*;
import javax.swing.*;

public class DrawFlags{
    int ratioX, ratioY, offsetX, offsetY, sizeX, sizeY, scale; //USED FOR CALCULATING SHAPE POSITIONS
    
    public void setRatio(JFrame frame, Dimension ratio) {
        this.ratioX = (int)ratio.getWidth(); //SET BASE RATIO SIZE OF THE FLAG
        this.ratioY = (int)ratio.getHeight();
        offsetX = frame.getContentPane().getWidth() / 2; //OFFSET THE FLAG FROM THE CENTRE
        offsetY = frame.getContentPane().getHeight() / 2; 
        //SET SIZE OF FLAG IN FRAME BASED ON WINDOW AND RATIO SIZE
        sizeX = (int)Math.round(ratioX * 30 * scale);
        sizeY = (int)Math.round(ratioY * 30 * scale);
        //UNUSED CALCULATION FOR SIZE BASED ON DIFFERENT RATIO SIZES
        //sizeX = ratioX == 2? (int)Math.round(ratioX * 30 * scale) : ratioX == 3 ? (int)Math.round(ratioX * 15 * scale) : ratioX == 5 ? (int)Math.round(ratioX * 10 * scale) : ratioX == 1 ? (int)Math.round(ratioX * 30 * scale) : 0;
        //sizeY = ratioY == 1? (int)Math.round(ratioY * 30 * scale) : ratioY == 2 ? (int)Math.round(ratioY * 15 * scale) : ratioY == 3 ? (int)Math.round(ratioY * 10 * scale) : ratioY == 1 ? (int)Math.round(ratioY * 30 * scale) : 0;
        scale = offsetX > offsetY ? offsetY / 30 : offsetX / 30; //SET SCALE OF FLAG RELATIVE TO FRAME
    }
    
    public void drawBackground(Graphics2D g, Color c) { //FILL FLAG WITH A BACKGROUND
        g.setColor(c);
        g.fillRect(offsetX - (sizeX / 2), offsetY - (sizeY / 2), sizeX, sizeY);  
    }

    public void drawBandHorizontal(Graphics2D g, Color[] colours, int[] parts) {
        int total = sizeX / IntStream.of(parts).sum(); //ADD UP ALL PARTS OF THE BANDS AND DIVIDE THEM EQUALLY TO CALCULATE POSITIONS
        int part = 0; //ACTS AS AN INDEX
        
        for(int i = 0; i < parts.length; i++) { //LOOP THROUGH PARTS
            g.setColor(colours[i]);
            g.fillRect(offsetX - (sizeX / 2) + (total * part), offsetY - (sizeY / 2), total * parts[i], sizeY); //DRAWS A BAND
            part += parts[i];
        }
    }
    
    public void drawBandVertical(Graphics2D g, Color[] colours, int[] parts) { //IDENTICAL TO DRAWBANDHORIZONTAL BUT THIS METHOD DRAWS VERTICAL
        int total = sizeX / IntStream.of(parts).sum();
        int part = 0;
                        
        for(int i = 0; i < parts.length; i++) {
            g.setColor(colours[i]);
            g.fillRect(offsetX - (sizeX / 2), offsetY - (sizeY / 2) + (total * part / 2), sizeX, total * parts[i] / 2);
            part += parts[i];
        }
    }
    
    /* UNUSED METHOD INTENDED TO HIDE SHAPES OUT OF BOUNDS
    public void drawBorder(Graphics2D g) {
        int[] pointsX = {
            0, offsetX * 2, offsetX * 2, 0, offsetX - (sizeX / 2)
        };
        int[] pointsY = {
            0, 0, offsetY * 2, offsetY * 2, offsetY - (sizeY / 2)
        };
                
        g.setColor(Color.RED);
        //g.drawPolygon(pointsX, pointsY, 5);
    }
    */
    
    public void drawCircle(Graphics2D g, Color c, int radius, int offsetW, int offsetZ) { //THESE METHODS ARE SELF-EXPLANITORY
        radius *= scale; //RADIUS IS RELATIVE TO SCALE
        g.setColor(c); //SET COLOUR
        g.fillOval((int)Math.round(offsetX - (radius / 2) + (scale * offsetW)), (int)Math.round(offsetY - (radius / 2) + (scale * offsetZ)), radius, radius); //DRAW CIRCLE USING OVAL
    } 
    
    public void drawCrossHorizontal(Graphics2D g, Color c, int width, int offset) { //DIRIVIATIVE OF DRAWBAND WITHOUT MULTIPLE BANDS BUT ONE
        width *= scale;
        offset *= scale;
        g.setColor(c);
        g.fillRect(offsetX - (sizeX / 2), offsetY - (width / 2) + offset, sizeX, width); //DRAW HORIZONTAL STRIPE
    }

    public void drawCrossVertical(Graphics2D g, Color c, int width, int offset) {
        width *= scale;
        offset *= scale;
        g.setColor(c);
        g.fillRect(offsetX - (width / 2) + offset, offsetY - (sizeY / 2), width, sizeY); //DRAW VERTICAL STRIPE
    }
    
    /*UNUSED METHOD WOULD DRAW AN ENSIGN (SMALLER FLAG OVER THIS FLAG)
    public void drawEnsign(Graphics2D g, Color c, int width, int height) { //DRAW SMALLER UNION JACK OVER FLAG
        width *= scale;
        height *= scale;
        g.setColor(c);
        g.fillRect(offsetX - (sizeX / 2), offsetY - (sizeY / 2), width, height);
    }
    */
    
    public void drawGrid(Graphics2D g, Color c, int size) {
        g.setColor(c);
                
        for(int i = 0; i < 60 / size + 1; i++) {
            int placement = (offsetX - (sizeX / 2)) + (size * i * scale);
            g.drawLine(placement, offsetY - (sizeY / 2), placement, offsetY + (sizeY / 2));
        }

        for(int i = 0; i < ratioY * 30 / size + 1; i++) {
            int placement = (offsetY - (sizeY / 2)) + (size * i * scale);
            g.drawLine(offsetX - (sizeX / 2), placement, offsetX + (sizeX / 2), placement);
        }
    }
        
    public void drawSaltireRightToLeft(Graphics2D g, Color c, int rangeX, int rangeY){
        int[] pointsX = { //STORES POINTS ON THE X-AXIS FOR THE POLYGON
            (offsetX - (sizeX / 2)) + (scale * rangeX),
            offsetX - (sizeX / 2),
            offsetX - (sizeX / 2),
            (offsetX + (sizeX / 2)) - (scale * rangeX),
            offsetX + (sizeX / 2),
            offsetX + (sizeX / 2),
        };
        int[] pointsY = { //STORES POINTS ON Y-AXIS
            offsetY - (sizeY / 2), 
            offsetY - (sizeY / 2),
            (offsetY - (sizeY / 2)) + (scale * rangeY),
            offsetY + (sizeY / 2), 
            offsetY + (sizeY / 2),
            (offsetY + (sizeY / 2)) - (scale * rangeY)
        };

        g.setColor(c);
        g.fillPolygon(pointsX, pointsY, 6); //DRAW POLYGON
    }

    public void drawSaltireLeftToRight(Graphics2D g, Color c, int rangeX, int rangeY){ //IDENTICAL TO ABOVE BUT POINTS ARE INVERTED
        int[] pointsX = {
            (offsetX - (sizeX / 2)) + (scale * rangeX),
            offsetX - (sizeX / 2),
            offsetX - (sizeX / 2),
            (offsetX + (sizeX / 2)) - (scale * rangeX),
            offsetX + (sizeX / 2),
            offsetX + (sizeX / 2),
        };
        int[] pointsY = {
            offsetY + (sizeY / 2), 
            offsetY + (sizeY / 2),
            (offsetY + (sizeY / 2)) - (scale * rangeY),
            offsetY - (sizeY / 2), 
            offsetY - (sizeY / 2),
            (offsetY - (sizeY / 2)) + (scale * rangeY)
        };

        g.setColor(c);
        g.fillPolygon(pointsX, pointsY, 6);
    }
    
    public void drawSaltireHalf(Graphics2D g, Color c, int topLeft, int topRight, int bottomRight, int bottomLeft, boolean xPositive, boolean yPositive) { //IDENTICAL TO DRAWSALTIRE
        int[] pointsX = {
            !xPositive ? (offsetX - (sizeX / 2)) + (scale * topRight) : (offsetX + (sizeX / 2)) - (scale * topRight), //HOWEVER, THERE IS MORE LOGIC REQUIRED TO REUSE THIS ON 4 ROTATIONS
            !xPositive ? offsetX - (sizeX / 2) : offsetX + (sizeX / 2), //POSITIVE AND NEGATIVE VALUES ARE REQUIRED TO DETERMINE IF A POINT GOES LEFT, OR RIGHT
            !xPositive ? offsetX - (sizeX / 2) : offsetX + (sizeX / 2),
            !xPositive ? offsetX - (scale * bottomLeft) : offsetX + (scale * bottomLeft), 
            offsetX,
            offsetX,
        };
        int[] pointsY = {
            !yPositive ? offsetY - (sizeY / 2) : offsetY + (sizeY / 2), 
            !yPositive ? offsetY - (sizeY / 2) : offsetY + (sizeY / 2),
            !yPositive ? (offsetY - (sizeY / 2)) + (scale * topLeft) : (offsetY + (sizeY / 2)) - (scale * topLeft),
            offsetY, 
            offsetY,
            !yPositive ? offsetY - (scale * bottomRight) : offsetY + (scale * bottomRight)
        };

        g.setColor(c);
        g.fillPolygon(pointsX, pointsY, 6);
    }

    public void drawShevron(Graphics2D g, Color c, int reach) { //DRAW TRIANGLE WHERE ONE POINT CAN BE MOVED
        reach *= scale;
        int x[] = new int[] {
            offsetX - (sizeX / 2),
            (offsetX - (sizeX / 2)) + reach, 
            offsetX - (sizeX / 2)
        };
        int y[] = new int[] {
            offsetY - (sizeY / 2),
            offsetY, 
            offsetY + (sizeY / 2)
        };
        
        g.setColor(c);
        g.fillPolygon(x, y, 3); //FILL PRIMARY TRIANGLE USING VERTEX POSITIONS ASSIGNED IN ABOVE LOOP
    }
    
    public void drawStar(Graphics2D g, Color c, int vertices, double outerRadius, double innerRadius, double offsetA, double offsetB, double angle) {
        double increment = 0;
        int x[] = new int[vertices * 2], y[] = new int[vertices * 2]; //ARRAY TO STORE THE POSITION OF EACH VERTEX, LENGTH IS DYNAMICALLY SET BASED ON VALUE OF VERTICES
        int parts = 5;
        double or = (sizeY / parts) * outerRadius / 2, ir = (sizeY / parts) * innerRadius / 2;
        
        for (int i = 0; i < vertices * 2; i++) { //LOOP TO CALCULATE POSITIONS
            if (i % 2 == 0) {
                x[i] = (int)(offsetX + (or * Math.cos(increment + Math.toRadians(angle))) + (scale * offsetA)); //CALCULATE X-AXIS POSITION OF VERTEX[INDEX]
                y[i] = (int)(offsetY + (or * Math.sin(increment + Math.toRadians(angle))) + (scale * offsetB)); //CALCULATE Y-AXIS POSITION            
            }
            else {
                x[i] = (int)(offsetX + (ir * Math.cos(increment + Math.toRadians(angle))) + (scale * offsetA)); //CALCULATE X-AXIS POSITION OF VERTEX[INDEX]
                y[i] = (int)(offsetY + (ir * Math.sin(increment + Math.toRadians(angle))) + (scale * offsetB)); //CALCULATE Y-AXIS POSITION
            }
            
            increment += Math.toRadians(360) / (vertices * 2); //INCREMENTS EACH LOOP TO INCREASE ANGLE AND THUS POSITION OF EACH VERTEX ON THE CIRCUMFERENCE
        }
        
        g.setColor(c);
        g.fillPolygon(x, y, vertices * 2); //FILL PRIMARY TRIANGLE USING VERTEX POSITIONS ASSIGNED IN ABOVE LOOP
    }
}