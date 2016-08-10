package cn.zsq.learn.thread;

public class Note {
	/**
	 * 线程池
	 * newCachedThreadPool 必要时创建新线程，空闲线程会被保留60秒
	 * newFixedThreadPool 该线程池包含固定数目的线程；空闲线程会一直被保留
	 * newSingleThreadPool 只有一个线程的“池”，该线程顺序执行每一个提交的任务。
	 * newScheduledThreadPool 用于预定而构建固定的线程池，代替java.util.Timer
	 * newSingleThreadScheduleExecutor 用于预定执行而构建的单线程“池”
	 * 当用完一个线程的时候，应该调用shutdown。该方法启动线程的关闭序列。被关闭的执行器不再接受新的任务。
	 * 当所有的任务完成之后，线程池中中的线程死亡。
	 * 另一种方法是调用shutdownNow。该先线程取消尚未开始的所有任务，并试图中断正在执行的线程。
	 */
	
	/**
	 * 执行器的使用
	 * 1. 调用Executors类中的静态方法（上一个注释的几个方法之一）
	 * 2. 调用submit提交Runnable或者Callable对象。
	 * 3. 如果想要取消一个任务，或者如果提交Callable对象，那就要保存好返回的Future对象。
	 * 4. 当不再提交任何对象的时候，调用shutdown
	 */
}
