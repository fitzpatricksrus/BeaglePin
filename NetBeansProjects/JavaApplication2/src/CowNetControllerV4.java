
import org.bulldog.core.Signal;
import org.bulldog.core.gpio.DigitalOutput;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;

public class CowNetControllerV4 {

	private static final boolean RUN_ON_WPC = false;

	public static void main(String[] args) {
		String dataPinsIn[] = {
			"P8_46"
		};
		DigitalOutput[] dataPins = new DigitalOutput[dataPinsIn.length];
		Board board = Platform.createBoard();
		for (int i = 0; i < dataPins.length; i++) {
			dataPins[i] = board.getPin(dataPinsIn[i]).as(DigitalOutput.class);
			dataPins[i].write(Signal.Low);
		}

	}
}
