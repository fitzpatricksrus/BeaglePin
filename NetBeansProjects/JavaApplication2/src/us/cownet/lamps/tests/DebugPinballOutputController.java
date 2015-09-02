package us.cownet.lamps.tests;

import java.awt.*;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.JPanel;
import us.cownet.lamps.PinballOutputController;
import us.cownet.timers.TimerUtil;

public class DebugPinballOutputController extends Canvas implements PinballOutputController {
	private static final Stroke stroke = new BasicStroke();
	private static final int DOT_SIZE = 40;
	private static final int ROWS = 8;
	private static final int COLS = 8;
	private static final int DUTY_CYCLE_SAMPLE_SIZE = COLS * 256;
	private static final Rectangle squares[][] = new Rectangle[COLS][ROWS];
	private static final DutyCycleCalculator dutyCycle[][] = new DutyCycleCalculator[COLS][ROWS];
	private static final int MASK[] = new int[Math.max(ROWS, COLS)];
	private BufferStrategy strategy;
	private int currentColumn;
	private int currentRows;

	@Override
	public int getColumnCount() {
		return COLS;
	}

	@Override
	public void write(Register signal, byte value) {
		if (strategy == null) {
			init();
		}
		Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
		switch (signal) {
			case LAMP_ROW:
				if (value != currentRows) {
					currentRows = value;
					drawColumn(g, currentColumn, currentRows);
				}
				break;
			case LAMP_COL:
				int col = -1;
				for (byte i = 0; i < COLS; i++) {
					if ((value & MASK[i]) != 0) {
						col = i;
						break;
					}
				}
				if (col != currentColumn) {
					drawColumn(g, currentColumn, 0);
					currentColumn = col;
					drawColumn(g, currentColumn, currentRows);
				}
				break;
			default:
		}
		g.dispose();
		strategy.show();
	}

	public void init() {
		for (int col = 0; col < COLS; col++) {
			for (int row = 0; row < ROWS; row++) {
				squares[col][row] = new Rectangle(
						col * DOT_SIZE,
						row * DOT_SIZE,
						DOT_SIZE,
						DOT_SIZE);
				squares[col][row].grow(-1, -1);
				dutyCycle[col][row] = new DutyCycleCalculator(DUTY_CYCLE_SAMPLE_SIZE/*, COLS*/);
			}
		}
		for (int row = 0; row < ROWS; row++) {
			MASK[row] = (1 << row);
		}

		JFrame container = new JFrame("Lamp Matrix");
		container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// get hold the content of the frame and set up the
		// resolution of the game
		JPanel panel = (JPanel)container.getContentPane();
		panel.setPreferredSize(new Dimension(COLS * DOT_SIZE + 1, ROWS * DOT_SIZE + 1));
		panel.setLayout(null);

		// setup our canvas size and put it into the content of the frame
		setBounds(0, 0, COLS * DOT_SIZE + 1, ROWS * DOT_SIZE + 1);
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

		Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
		for (int col = 0; col < COLS; col++) {
			drawColumn(g, col, 0);
		}
		g.dispose();
		strategy.show();

		TimerUtil.INSTANCE.attachTickerCallback(
				() -> advanceDutyCycles(),
				0);
	}

	private void advanceDutyCycles() {
		for (int col = 0; col < COLS; col++) {
			for (int row = 0; row < ROWS; row++) {
				if (col == currentColumn) {
					dutyCycle[col][row].addSample((currentRows & MASK[row]) != 0);
				} else {
					dutyCycle[col][row].addSample(false);
				}
			}
		}
	}

	private void drawColumn(Graphics2D g2d, int col, int rows) {
		if (col == -1) {
			return;
		}
		for (int row = 0; row < ROWS; row++) {
			drawLamp(g2d, col, row, (rows & MASK[row]) != 0);
		}
	}

	private void drawLamp(Graphics2D g2d, int col, int row, boolean on) {
		Rectangle box = squares[col][row];
		if (on) {
			g2d.setPaint(Color.ORANGE);
			g2d.fill(box);
		} else {
			g2d.setPaint(Color.WHITE);
			g2d.fill(box);
			g2d.setPaint(Color.ORANGE);
			g2d.setStroke(stroke);
			g2d.draw(box);
		}
		g2d.drawString("" + dutyCycle[col][row], box.x + 5, box.y + 15);
	}

	public static void main(String[] args) {
		TimerUtil.INSTANCE.enableHackTicks(true);
		DebugPinballOutputController hardware = new DebugPinballOutputController();
		for (int i = 0; i < 40000; i++) {
			for (int col = 0; col < COLS; col++) {
				for (int row = 0; row < ROWS; row++) {
					// turn off all lights
					hardware.write(PinballOutputController.Register.LAMP_COL, (byte)0);
//                    delay();
					// set the row lamps
					hardware.write(PinballOutputController.Register.LAMP_ROW, (byte)(1 << row));
//                    delay();
					// turn the proper column back on
					hardware.write(PinballOutputController.Register.LAMP_COL, (byte)(1 << col));
					try {
//						Thread.sleep(100, 000);
					} catch (Exception e) {
					}
					TimerUtil.INSTANCE.tick();
				}
			}
		}
	}
}
