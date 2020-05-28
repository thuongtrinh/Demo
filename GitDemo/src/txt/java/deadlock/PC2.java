package txt.java.deadlock;

//An incorrect implementation of a producer and consumer.
class Q2 {
	int n;
	boolean valueSet = false;

	synchronized int get() {
		while (!valueSet)
			try {
				System.out.println("get is first");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		System.out.println("Got: " + n);
		valueSet = false;
		notify();
		return n;
	}

	synchronized void put(int n) {
		while (valueSet)
			try {
				System.out.println("put is first");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		this.n = n;
		valueSet = true;
		System.out.println("Put: " + n);
		notify();
	}
}

class Producer2 implements Runnable {
	Q2 q;

	Producer2(Q2 q) {
		this.q = q;
		new Thread(this, "Producer").start();
	}

	public void run() {
		int i = 0;
		while (true) {
			q.put(i++);
			if (i == 10)
				break;
		}
	}
}

class Consumer2 implements Runnable {
	Q2 q;

	Consumer2(Q2 q) {
		this.q = q;
		new Thread(this, "Consumer").start();
	}

	public void run() {
		int c = 0;
		while (true) {
			q.get();
			c++;
			if (c > 10) {
				System.out.println("number = " + c);
				break;
			}
		}
	}
}

public class PC2 {
	public static void main(String[] args) {
		Q2 q = new Q2();
		new Producer2(q);
		new Consumer2(q);
		System.out.println("Press Control-C to stop.");
	}
}
