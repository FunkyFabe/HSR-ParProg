package aufgabe4;

class Room {
	private final int capacity;
	private int count;

	public Room(int capacity) {
		this.capacity = capacity;
		this.count = 0;
	}

	public synchronized void enter() {
		while (count == capacity) { // maybe the thread gets overtaken by another entering thread
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		count++;
	}

	public synchronized void exit() {
		count--;
		notify(); // maximum one arbitrary waiting thread may continue, not necessarily fair
	}
}

public class RoomOccupationTest {
	public static void main(String[] args) {
		var room = new Room(5);
		for (int i = 0; i < 10; i++) {
			new Thread(() -> {
				room.enter();
				System.out.println("Room entered " + Thread.currentThread().getName());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					throw new AssertionError();
				}
				room.exit();
				System.out.println("Room exited " + Thread.currentThread().getName());
			}).start();
		}
	}
}
