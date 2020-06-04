package txt.java.deadlock;

/**
 * @comment class Q
 * @author thuongtx
 * popo
 */
class Q {

	int n;

	synchronized int get() {
		System.out.println("Got: " + n);
		return n;
	}

	synchronized void put(int n) {
		this.n = n;
		System.out.println("Put: " + n);
	}
}

class Producer implements Runnable {

	Q q;

	Producer(Q q) {
		this.q = q;
		new Thread(this, "Producer").start();
	}

	public void run() {
		int i = 0;
		while (true) {
			q.put(i++);
			if (i == 100)
				break;
		}
	}
}

class Consumer implements Runnable {

	Q q;

	Consumer(Q q) {
		this.q = q;
		new Thread(this, "Consumer").start();
	}

	public void run() {
		int c = 0;
		while (true) {
			q.get();
			c++;
			if (c > 300) {
				System.out.println("number = " + c);
				break;
			}
		}
	}
}

public class PC {

	public static void main(String[] args) {
		Q q = new Q();
		new Producer(q);
		new Consumer(q);
		System.out.println("Press Control-C to stop.");
	}
}
