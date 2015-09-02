package us.cownet.lamps.tests;

import java.awt.*;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.JPanel;
import us.cownet.lamps.PinballOutputController;
import us.cownet.timers.TimerUtil;

public class DutyCycleDebugPinballOutputController extends Canvas implements PinballOutputController {
	private final int rowCount;
	private final int colCount;
	private final int dutyCycleSampleSize;
	private final DutyCycleCalculator dutyCycle[][];
	private final int mask[];
	private boolean isInited;
	private int currentColumns;
	private int currentRows;

	private static Color greys[] = new Color[101];

	static {
		for (int i = 0; i <= 100; i++) {
			int value = 258 * i / 101;
			System.out.println("" + i + "  " + value);
			greys[i] = new Color(value, value, value);
		}
	}

	public DutyCycleDebugPinballOutputController(int cols, int rows) {
		this(cols, rows, 255);
	}

	public DutyCycleDebugPinballOutputController(int cols, int rows, int bufferSize) {
		this.colCount = cols;
		this.rowCount = rows;
		dutyCycleSampleSize = cols * bufferSize * 2;
		dutyCycle = new DutyCycleCalculator[cols][rows];
		mask = new int[Math.max(rows, cols)];

		for (int col = 0; col < cols; col++) {
			for (int row = 0; row < rows; row++) {
				dutyCycle[col][row] = new DutyCycleCalculator(dutyCycleSampleSize, cols);
			}
		}

		for (int i = 0; i < Math.max(rows, cols); i++) {
			mask[i] = (1 << i);
		}
	}

	@Override
	public int getColumnCount() {
		return colCount;
	}

	@Override
	public void write(PinballOutputController.Register signal, byte value) {
		if (!isInited) {
			init();
		}
		switch (signal) {
			case LAMP_ROW:
				currentRows = value;
				break;
			case LAMP_COL:
				currentColumns = value;
				break;
			default:
		}
	}

	public void init() {
		isInited = true;
		TimerUtil.INSTANCE.attachTickerCallback(
				() -> advanceDutyCycles(),
				0);
		(new DutyCycleDisplay()).init();
	}

	private void advanceDutyCycles() {
		for (int col = 0; col < colCount; col++) {
			boolean columnIsOn = (currentColumns & mask[col]) != 0;
			for (int row = 0; row < rowCount; row++) {
				if (columnIsOn) {
					dutyCycle[col][row].addSample((currentRows & mask[row]) != 0);
				} else {
					dutyCycle[col][row].addSample(false);
				}
			}
		}
	}

	private class DutyCycleDisplay extends Canvas {
		private final Stroke stroke = new BasicStroke();
		private final int DOT_SIZE = 40;
		private final Rectangle squares[][] = new Rectangle[colCount][rowCount];
		private BufferStrategy strategy;

		public void init() {
			for (int col = 0; col < colCount; col++) {
				for (int row = 0; row < rowCount; row++) {
					squares[col][row] = new Rectangle(
							col * DOT_SIZE,
							row * DOT_SIZE,
							DOT_SIZE,
							DOT_SIZE);
					squares[col][row].grow(-1, -1);
				}
			}

			JFrame container = new JFrame("Lamp Matrix");
			container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			// get hold the content of the frame and set up the
			// resolution of the game
			JPanel panel = (JPanel)container.getContentPane();
			panel.setPreferredSize(new Dimension(colCount * DOT_SIZE + 1, rowCount * DOT_SIZE + 1));
			panel.setLayout(null);

			// setup our canvas size and put it into the content of the frame
			setBounds(0, 0, colCount * DOT_SIZE + 1, rowCount * DOT_SIZE + 1);
			panel.add(this);

			// Tell AWT not to bother repainting our canvas since we're
			// going to do that our self in accelerated mode
			setIgnoreRepaint(true);

			// finally make the window visible
			container.pack();
			container.setResizable(false);
			container.setVisible(true);

			// create the buffering strategy which will allow AWT
			// to manage our accelerated graphics
			createBufferStrategy(2);
			strategy = getBufferStrategy();

			updateDisplay();

			Thread t = new Thread() {
				@Override
				public void run() {
					while (true) {
						updateDisplay();
						try {
							Thread.sleep(50);
						} catch (Exception e) {
							return;
						}
					}
				}
			};
			t.setDaemon(true);
			t.start();
		}

		public void updateDisplay() {
			Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
			for (int col = 0; col < colCount; col++) {
				for (int row = 0; row < rowCount; row++) {
					drawLamp(g, col, row, (currentRows & mask[row]) != 0);
				}
			}
			g.dispose();
			strategy.show();
		}

		private void drawLamp(Graphics2D g2d, int col, int row, boolean on) {
			Rectangle box = squares[col][row];
//			g2d.setPaint(Color.WHITE);
			g2d.setPaint(greys[(int)dutyCycle[col][row].getDutyCycle()]);
			g2d.fill(box);
			g2d.setStroke(stroke);
			g2d.setPaint(Color.ORANGE);
			g2d.draw(box);
			g2d.setPaint(Color.BLACK);
			g2d.drawString("" + dutyCycle[col][row], box.x + 6, box.y + 16);
			g2d.setPaint(Color.ORANGE);
			g2d.drawString("" + dutyCycle[col][row], box.x + 5, box.y + 15);
		}

	}
}
