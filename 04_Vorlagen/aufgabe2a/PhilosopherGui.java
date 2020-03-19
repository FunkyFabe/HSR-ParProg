package aufgabe2a;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

class ConsoleLogger {
	private static final String EATING_MESSAGE = "starts eating";
	private static final String THINKING_MESSAGE = "starts thinking";
	private static final String HUNGRY_MESSAGE = "is getting hungry";

	public ConsoleLogger(PhilosopherTable table) {
		table.addListener(event -> {
			var source = event.getSource();
			if (source == table) {
				System.out.println("Application starting");
			} else {
				Philosopher philosopher = (Philosopher) source;
				System.out.println("Philosopher: " + philosopher.getName() + " " + getStateString(philosopher));
			}
		});
	}

	private String getStateString(Philosopher philo) {
		switch (philo.getPhilosopherState()) {
		case EATING:
			return EATING_MESSAGE;
		case THINKING:
			return THINKING_MESSAGE;
		case HUNGRY:
			return HUNGRY_MESSAGE;
		}
		return "";
	}
}

class PhilosopherPanel extends JPanel {
	private static final long serialVersionUID = 5113281871592746242L;
	private final PhilosopherTable table;
	private final int philosopherCount;
	private final javax.swing.Timer timer;

	public PhilosopherPanel(PhilosopherTable table, int philosopherCount) {
		this.philosopherCount = philosopherCount;
		this.table = table;
		table.addListener(event -> repaint());
		timer = new Timer(100, event -> repaint());
		timer.start();
	}

	public void paint(Graphics graphics) {
		var insets = getInsets();
		var dim = getSize();
		int length = Math.min(dim.width, dim.height);
		double theta;
		double thetaIn;
		double phi = 2 * Math.PI / philosopherCount;
		int plateRadius = (int) (Math.sqrt(
				Math.pow(length / 2, 2.0) - Math.pow(Math.cos(phi) * (length / 2), 2.0) + Math.sin(phi) * (length / 2))
				* 0.25);
		int tableRadius = (int) (length / 2 - plateRadius) - 10;
		int halfStickLength = (int) (plateRadius * 1.25);
		int centerX = length / 2 + insets.left;
		int centerY = length / 2 + insets.top;
		super.paint(graphics);
		for (int index = 0; index < philosopherCount; index++) {
			int transCenterX = centerX - plateRadius;
			int transCenterY = centerY - plateRadius;
			theta = 0;
			switch (table.getPhilosopher(index).getPhilosopherState()) {
			case THINKING:
				graphics.setColor(Color.black);
				break;
			case HUNGRY:
				graphics.setColor(Color.red);
				break;
			case EATING:
				graphics.setColor(Color.yellow);
				break;
			}
			graphics.fillArc((int) Math.round(transCenterX + tableRadius * Math.cos(index * phi)),
					(int) Math.round(transCenterY + tableRadius * Math.sin(index * phi)), 2 * plateRadius, 2 * plateRadius,
					0, 360);
			graphics.setColor(Color.black);
			if (table.getPhilosopher(index).getPhilosopherState() == PhilosopherState.EATING) {
				theta = (-phi / 7);
			}
			if (table.getPhilosopher((index + 1) % philosopherCount).getPhilosopherState() == PhilosopherState.EATING) {
				theta = phi / 7;
			}
			thetaIn = theta * 1.75;
			graphics.drawLine(
					(int) Math.round(centerX + (tableRadius - halfStickLength) * Math.cos(index * phi + phi / 2 + thetaIn)),
					(int) Math.round(centerY + (tableRadius - halfStickLength) * Math.sin(index * phi + phi / 2 + thetaIn)),
					(int) Math.round(centerX + (tableRadius + halfStickLength) * Math.cos(index * phi + phi / 2 + theta)),
					(int) Math.round(centerY + (tableRadius + halfStickLength) * Math.sin(index * phi + phi / 2 + theta)));
		}
	}
}

public class PhilosopherGui extends JFrame {
	private static final long serialVersionUID = 1L;

	public PhilosopherGui(int philosopherCount) {
		setTitle("Philosopher");
		setVisible(true);
		setVisible(false);
		var insets = getInsets();
		setSize(insets.left + insets.right + 400, insets.top + insets.bottom + 400);
		var table = new PhilosopherTable(philosopherCount);
		var panel = new PhilosopherPanel(table, philosopherCount);
		new ConsoleLogger(table);
		table.start();
		setContentPane(panel);
		setVisible(true);
		repaint();
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public static void main(String args[]) {
		new PhilosopherGui(5);
	}
}
