package progettooracolo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.util.Random;
import javax.swing.JPanel;

/**
 * pannello con lo sfondo di zoltar
 * cielo viola scuro con una luce magica al centro e tante stelle
 */
public class PannelloSfondo extends JPanel {

    /** quante stelle disegnare */
    private static final int NUMERO_STELLE = 90;

    /** posizione orizzontale delle stelle da zero a uno */
    private final double[] stelleX = new double[NUMERO_STELLE];

    /** posizione verticale delle stelle da zero a uno */
    private final double[] stelleY = new double[NUMERO_STELLE];

    /** grandezza di ogni stella */
    private final int[] stelleDimensione = new int[NUMERO_STELLE];

    /**
     * crea il pannello e decide una volta sola dove stanno le stelle
     */
    public PannelloSfondo() {
        // il numero fisso fa uscire sempre le stesse stelle e non lampeggiano
        Random random = new Random(7);
        for (int i = 0; i < NUMERO_STELLE; i++) {
            stelleX[i] = random.nextDouble();
            stelleY[i] = random.nextDouble();
            stelleDimensione[i] = 1 + random.nextInt(3);
        }
    }

    /**
     * disegna lo sfondo ogni volta che il pannello va ridisegnato
     *
     * @param g lo strumento per disegnare
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int larghezza = getWidth();
        int altezza = getHeight();

        // sfumatura dal blu notte in alto al viola in basso
        g2.setPaint(new GradientPaint(0, 0, new Color(15, 8, 40),
                0, altezza, new Color(75, 25, 110)));
        g2.fillRect(0, 0, larghezza, altezza);

        // alone di luce magica al centro
        float raggio = Math.max(larghezza, altezza) / 1.5f;
        g2.setPaint(new RadialGradientPaint(new Point(larghezza / 2, altezza / 3), raggio,
                new float[] {0f, 1f},
                new Color[] {new Color(150, 70, 200, 110), new Color(150, 70, 200, 0)}));
        g2.fillRect(0, 0, larghezza, altezza);

        // le stelle dorate
        g2.setColor(new Color(255, 240, 190, 200));
        for (int i = 0; i < NUMERO_STELLE; i++) {
            int x = (int) (stelleX[i] * larghezza);
            int y = (int) (stelleY[i] * altezza);
            g2.fillOval(x, y, stelleDimensione[i], stelleDimensione[i]);
        }

        g2.dispose();
    }
}
