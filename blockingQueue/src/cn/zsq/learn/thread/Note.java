package cn.zsq.learn.thread;

import java.util.concurrent.TimeUnit;

public class Note {
	/**
	 * 线程安全的集合 java.util.concurrent ConcurrentHashMap, ConcurrentSkipListMap,
	 * ConcurrentSkipListSet, ConcurrentLinkedQueue 确认集合大小通常要遍历。
	 * 并发的散列映射表，可以高效地支持大量的读者和一定量的写着。默认为16个写着同时进行，多余16个时，其他线程会被阻塞。
	 * 
	 * ConcurrentHashMap和ConcurrentSkipListMap类有相应的原子性的关联插入与关联删除
	 */

	/**
	 * Callable和Futrue Runnable
	 * 封装一个一部运行的任务，Callable和Futrue与其类似，但是有返回值。callable有一个参数化的接口， 只有一个方法call
	 * public interface Callable<V>{ V call() throws Exception; }
	 * Callable<Integer>表示一个最终返回Integer参数的异步计算
	 * 
	 * Future保存异步计算的结果。可以启动一个计算，将Future对象交给某个线程，然后忘记它，Future对象的所有者在计算好之后 就可以获得它。
	 * 二者接口如下
	 */
	public interface Callable<V> {
		V call() throws Exception;
	}

	public interface Future<V> {
		V get() throws Exception;

		V get(long timeout, TimeUnit unit) throws Exception;

		void cancel(boolean mayInterrupt);

		boolean isCancelled();

		boolean isDone();
	}
	/**
	 * 第一个get方法的调用被阻塞，直到计算完成。 如果在计算完成之前，第二个方法调用超时，抛出一个TimeOutException异常。
	 * 如果该计算的线程被中断，两个方法都将抛出InterruptedException。 如果计算已经完成，那么get方法会立刻返回。
	 * 
	 * 如果计算还在进行idDone返回false，否则返回true。
	 * 可以取消该计算（cancel）,如果计算还没有开始，它被取消且不再开始。如果计算处于运行之中，如果mayInterrupt为true，
	 * 他就被中断。
	 * 
	 * FutureTask包装器可以将Callable转换成Future或者Runnable，它同时实现二者的接口   
	 */
}
