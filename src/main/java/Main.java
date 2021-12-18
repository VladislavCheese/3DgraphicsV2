import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    private static final JFrame frame = new JFrame("3D Graphics View");

    private static final Surface surface = new Surface();

    private static final double P0 = 15000;//Начальное значение ro
    private static double P1 = 0.3;//Начальное значение teta
    private static double P2 = 78;//Начальное значение fi

    public static void main(String[] args) {
        //нужно задать относительный путь к файлу с координатами и время просмотра
        startApplication("setup_Pyramid.txt", 1000);
    }

    private static void startApplication(String dataFilePath, int second) {
        surface.setViewPoint(P0, P1, P2);
        surface.load(dataFilePath);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(new Dimension(1600, 900));
        frame.setVisible(true);
        JPanel jp = new JPanel();
        frame.add(jp);
        frame.setVisible(true);
        drawImage(second);
    }

    private static void drawImage(int second) {
        JPanel jp;
        for (int i = 1; i < 50 * second; i++) {
            jp = new JPanel() {
                @Override
                public void paint(Graphics g) {
                    g.clearRect(0, 0, frame.getWidth(), frame.getHeight());
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
                    g.setColor(Color.RED);
                    surface.drawSurface(g);
                }
            };
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //изменение значения teta
            P1 += 2;
            //изменение значения fi
            P2 += 1;
            surface.setNewViewPoint(P0, P1, P2);
            frame.add(jp);
            frame.setVisible(true);
        }
    }
}

