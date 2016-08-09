package cn.zsq.learn.thread;

public class Note {
	/**
	 * 监视器概念： 锁和条件并不满足面向对象的要求。
	 * 多年来，研究人眼一直在循环早一种解决方案，可以在程序员不需考虑如何加锁的情况下，就可以保证多线程的安全性。
	 * 最成功的解决方案就是监视器（monitor）。
	 * 
	 * 监视器特性： 1. 监视器是只包含私有域的类。 2. 每一个监视器类的对象有一个相关的锁。 3.
	 * 使用该锁对所有的方法进行加锁。如果客户端调用obj.method（），那么obj对象的锁是在方法调用开始的时候自动获得的，并且
	 * 当方法返回的时候自动释放该锁。因为所有的域都是私有的，这样的安排可以确保一个线程在对对象操作时，没有其他线程能访问该域。 4.
	 * 该锁可以有任意多个相关条件。
	 * 
	 * java设计者并没有完全采用监视器的概念，从而使线程安全性下降： 1. 域不是必须为private 2. 方法不是必须为synchronized
	 * 3. 内部锁对客户可用
	 */

	/**
	 * volatile关键字为实例域的同步访问提供了一种免锁机制：
	 * 如果声明一个域为volatile，那么编译器和虚拟机就知道该域可能被另一个线程并发更新。
	 * 例如，假定一个对象有一个布尔标记done，它的值被一个线程设置却被另一个线程查询，我们可以使用如下两种方式。
	 * 
	 */
	private boolean doneSync;

	public synchronized boolean idDone() {
		return doneSync;
	}

	public void setDoneSync(boolean doneSync) {
		this.doneSync = doneSync;
	}

	/**
	 * 使用内部锁不是一个好主意。如果另一个线程已经对该对象加锁，isDone和setDone方法可能会被阻塞。
	 * 如果想要达到这一效果，则此处合理否则以下方式更为合理
	 */
	private volatile boolean doneVol;

	public boolean isDone() {
		return doneVol;
	}

	public void setDoneVol(boolean doneVol) {
		this.doneVol = doneVol;
	}
	/**
	 * http://www.cnblogs.com/aigongsi/archive/2012/04/01/2429166.html 中抄录
	 * 在 java 垃圾回收整理一文中，描述了jvm运行时刻内存的分配。其中有一个内存区域是jvm虚拟机栈，每一个线程运行时都有一个线程栈，
	 * 线程栈保存了线程运行时候变量值信息。当线程访问某一个对象时候值的时候，首先通过对象的引用找到对应在堆内存的变量的值，然后把堆内存
	 * 变量的具体值load到线程本地内存中，建立一个变量副本，之后线程就不再和对象在堆内存变量值有任何关系，而是直接修改副本变量的值，
	 * 在修改完之后的某一个时刻（线程退出之前），自动把线程变量副本的值回写到对象在堆中变量。这样在堆中的对象的值就产生变化了。
	 * 
	 * read and load 从主存复制变量到当前工作内存 
	 * use and assign 执行代码，改变共享变量值 
	 * store and write用工作内存数据刷新主存相关内容
	 * 其中use and assign 可以多次出现
	 * 
	 * 但是这一些操作并不是原子性，也就是 在read,load之后，如果主内存count变量发生修改之后，
	 * 线程工作内存中的值由于已经加载，不会产生对应的变化，所以计算出来的结果会和预期不一样
	 * 对于volatile修饰的变量，jvm虚拟机只是保证从主内存加载到线程工作内存的值是最新的
	 * 例如假如线程1，线程2 在进行read,load 操作中，发现主内存中count的值都是5，那么都会加载这个最新的值
	 * 在线程1堆count进行修改之后，会write到主内存中，主内存中的count变量就会变为6
	 * 线程2由于已经进行read,load操作，在进行运算之后，也会更新主内存count的变量值为6
	 * 
	 * 所以两个线程及时用volatile关键字修改之后，还是会存在并发的情况。
	 * 
	 */
	
	/**
	 * volatile变量不提供原子性操作，所以如下操作不一定翻转域中的值
	 */
	private volatile boolean done;
	public void flipDone(){
		done = !done;
	}
	
	/**
	 * 锁测试与超时
	 * 线程在调用lock方法来获得另外一个线程所持有的锁的时候，很可能会发生阻塞。所以应该谨慎的申请锁。
	 * tryLock方法试图申请一个锁，在成功获得锁之后返回true，否则立即返回false。
	 * 使用tryLock的时候可以使用超时参数tryLock（100，TimeUnit.MILLISECONDS）
	 * lock方法不能被中断，中断线程在获得锁之前一直处于阻塞状态。如果出现死锁，那么lock方法就无法终止。
	 * 然而如果调用带有超时参数的tryLock那么线程在等待周期被中断，将抛出InterruptedException异常，
	 * 这是一个有用的异常，其允许程序打破死锁。
	 * 
	 * 中断描述blog：
	 * http://blog.csdn.net/u013475071/article/details/51131883
	 */
	
	/**
	 * 线程局部变量：
	 * ThreadLocal辅助类
	 */
	
	/**
	 * 弃用stop和suspend
	 */
}
