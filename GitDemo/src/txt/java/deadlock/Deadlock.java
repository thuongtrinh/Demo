package txt.java.deadlock;

//https://bliem.wordpress.com/2013/04/29/multithread-trong-java-phan-5/comment-page-1/
/*
 * An example of deadlock.
 */

class A {

	//class A
	public A() {
		System.out.println("Contructor A");
	}

	synchronized void foo(B b) {
		String name = Thread.currentThread().getName();
		System.out.println(name + " entered A.foo");
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(name + " trying to call B.last()");
		b.last();
	}

	synchronized void last() {
		System.out.println("Inside A.last");
	}
}


// Class B
class B {
	public B() {
		System.out.println("Contructor B");
	}
	synchronized void bar(A a) {
		String name = Thread.currentThread().getName();
		System.out.println(name + " entered B.bar");
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(name + " trying to call A.last()");
		a.last();
	}

	synchronized void last() {
		System.out.println("Inside A.last");
	}
}

public class Deadlock implements Runnable {

	A a = new A();
	B b = new B();

	Deadlock() {
		Thread.currentThread().setName("MainThread");
		Thread t = new Thread(this, "RacingThread");
		t.start();

		a.foo(b); // get lock on a in this thread.
		System.out.println("Back in main thread");
	}

	@Override
	public void run() {
		b.bar(a); // get lock on b in other thread.
		System.out.println("Back in other thread");
	}

	public static void main(String[] args) {
		new Deadlock();
	}
}
