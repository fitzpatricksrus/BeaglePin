package us.cownet.testing;

public interface Test {
	public void setup();

	public void loop();

	public default boolean isDone() {
		return false;
	}

	public static void assertTrue(boolean condition) {
		if (!condition) {
			System.out.println("Fail");
		}
	}

	public static void assertTrue(boolean condition, String message) {
		if (!condition) {
			System.out.println(message);
		}
	}
}
