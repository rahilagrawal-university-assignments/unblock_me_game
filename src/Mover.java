import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

/**
 * class that interprets mouse clicks and moves selected vehicles
 */
public class Mover extends JComponent {
    private GridPanel panel;
    private Point mousePoint;
    private VehicleShape selectedVehicle;
    private GameRunner gameRunner;
    private boolean ifDragged = false;

	/**
	 * standard constructor, sets up mouse listeners
	 * @param panel panel where grid is displayed
	 * @param gameRunner gameRunner reference
	 */
	public Mover(GridPanel panel, GameRunner gameRunner){
        this.panel = panel;
        ArrayList<VehicleShape> shapes = panel.getVehicleShapes();
        addMouseListener(new MouseAdapter() {
                             public void mousePressed(MouseEvent event) {
                                 mousePoint = event.getPoint();
                                 double dx = mousePoint.getX();
                                 double dy = mousePoint.getY();
//                                 System.out.println("Last Mouse Press: (" + dx + ", " + dy + ")");

                                 boolean flag = false;
                                 for (VehicleShape v : shapes) {
                                     if (v.contains(mousePoint)) {
                                         flag = true;
                                         selectedVehicle = v;
                                         break;
                                     }
                                 }
                                 if (!flag) mousePoint = null;
                             }


                         });


        addMouseMotionListener(new MouseMotionAdapter() {
           public void mouseDragged(MouseEvent event) {

               if (mousePoint == null) {
                   ifDragged = false;
                   return;
               }
               Point lastMousePoint = mousePoint;
               mousePoint = event.getPoint();
               ifDragged = true;

//               double dx = mousePoint.getX() - lastMousePoint.getX();
//               double dy = mousePoint.getY() - lastMousePoint.getY();
               double dx = mousePoint.getX();
               double dy = mousePoint.getY();

//               System.out.println("Last Mouse Drag: (" + dx + ", " + dy + ")");
               selectedVehicle.translate((int) dx, (int) dy);
               repaint();
               if (gameRunner.isFinished()) {
                   gameRunner.incrementScore();
                   gameRunner.finishGame();
               }
           }
       });

        addMouseListener(new MouseAdapter() {
        	public void mouseReleased(MouseEvent event) {
				if (mousePoint == null || !ifDragged) return;
				gameRunner.incrementScore();
			}
		} );
    }

	/**
	 * method to draw vehicles
	 * @param g Graphics
	 */
	public void paintComponent(Graphics g) {
        panel.draw(g);
    }
}
