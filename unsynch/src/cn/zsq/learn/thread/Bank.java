package cn.zsq.learn.thread;

import java.util.Vector;

public class Bank {
	private final double[] accounts;
	/**
	 * use the keyword synchronized, the Lock will be replaced.
	 * private Lock bankLock;
	 * private Condition sufficientFunds;
	 */
	
	public Bank(int n, double initialBalance){
		accounts =  new double[n];
		for(int i = 0; i < accounts.length; i ++){
			accounts[i] = initialBalance;
		}
		/**
		 * bankLock = new ReentrantLock();
		 * sufficientFunds = bankLock.newCondition();
		 */
	}
	
	/**
	 * synchronized关键字，将保护整个方法
	 * @param from
	 * @param to
	 * @param amount
	 * @throws InterruptedException
	 * 
	 * 将静态方法声明为synchronized也是合法的。如果调用这种方法，该方法获得相关的类对象的内部锁。
	 * 如果某线程调用该类的静态同步方法，那么当该方法被调用的时候，静态方法所在的类对象（class）被锁住。
	 * 因此，没有一个线程可以调用同一个类的这个或者任何其他的同步静态方法。
	 * 
	 * synchronized的局限：
	 * 1. 不能中断一个正在试图获得所的线程。
	 * 2. 试图获得锁时不能设定超时。
	 * 3. 每个锁仅有单一的条件
	 * 最好不要使用Lock与Condition结合或者synchronized的方法。多数情况下，可以使用java.util.concurrent包中的机制。
	 * 其次将synchronized作为选择，最后再考虑Lock与Condition
	 */
	public synchronized void transfer(int from, int to, double amount) throws InterruptedException{
		/**
		 * 添加锁对象，以保证线程安全
		 * 如果使用锁，那么就不能使用带资源的try语句VGBB
		 * bankLock.lock();
		 */
		try{
			/**
			 * 添加条件对象，在账户余额小于amount的时，将进程放入等待集中
			 */
			while(accounts[from] < amount){
				/**
				 * 使用synchronized关键字时
				 * sufficientFunds.await();
				 * 被如下方法取代
				 */
				wait();
			}
			System.out.print(Thread.currentThread());
			accounts[from] -= amount;
			System.out.printf(": %10.2f form %d to %d", amount, from, to);
			accounts[to] += amount;
			System.out.printf(" Total Balance: %10.2f%n", getTotalBalance());
			System.out.println();
			/**
			 * 将等待集中的所有线程接触阻塞状态。
			 * sufficientFunds.signalAll();
			 * 使用synchronized关键字时
			 * 被如下方法取代
			 */
			notifyAll();
		}finally{
			/**
			 * bankLock.unlock();
			 */
		}
	}
	
	/**
	 * 内部锁
	 * 任何一个java对象都有一个锁。线程可以通过同步方法获得锁。嗨哟另外一种获得所得方法，通过进入一个同步阻塞，如下所示。
	 * 可以通过这种方法实现一个原子操作，这种方法称为客户端锁定。
	 */
	private Object obj = new Object();
	public void transferByInnerLock(int from, int to, double amount){
		synchronized (obj) {
			accounts[from] -= amount;
			accounts[to] += amount;
		}
	}
	/**
	 * 这种方式的客户端锁定要保证，具体操作都是用Vector的内部锁，而事实可能并非如此。
	 * 客户端锁定通常不推荐使用
	 * @param accountsVector
	 * @param from
	 * @param to
	 * @param amount
	 */
	public void transferByVector(Vector<Double>accountsVector, int from, int to, double amount){
		synchronized (accountsVector) {
			accountsVector.set(from, accountsVector.get(from) - amount);
			accountsVector.set(to, accountsVector.get(to) + amount);
		}
	}
	private double getTotalBalance() {
		double sum = 0;
		for(double a: accounts){
			sum += a;
		}
		return sum;
	}
	
	public int size(){
		return accounts.length;
	}
}
