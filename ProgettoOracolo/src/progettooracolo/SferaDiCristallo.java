package progettooracolo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import javax.swing.JComponent;

/**
 * disegna una sfera di cristallo viola appoggiata su una base dorata
 */
public class SferaDiCristallo extends JComponent {

    /**
     * imposta la grandezza del disegno
     */
    public SferaDiCristallo() {
        setPreferredSize(new Dimension(80, 80));
    }

    /**
     * disegna la base la sfera e il riflesso di luce
     *
     * @param g lo strumento per disegnare
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // base dorata sotto la sfera
        g2.setColor(new Color(190, 150, 40));
        g2.fillRoundRect(16, 58, 48, 16, 10, 10);
        g2.setColor(new Color(235, 200, 80));
        g2.fillRoundRect(22, 56, 36, 8, 8, 8);

        // sfera con sfumatura dal chiaro allo scuro
        g2.setPaint(new RadialGradientPaint(new Point(30, 22), 45f,
                new float[] {0f, 0.5f, 1f},
                new Color[] {new Color(235, 225, 255), new Color(140, 90, 210),
                    new Color(45, 15, 90)}));
        g2.fillOval(9, 2, 62, 62);

        // piccolo riflesso bianco
        g2.setColor(new Color(255, 255, 255, 150));
        g2.fillOval(22, 12, 18, 10);

        g2.dispose();
    }
}
