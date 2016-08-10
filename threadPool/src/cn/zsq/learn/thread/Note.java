package cn.zsq.learn.thread;

public class Note {
	/**
	 * 线程池 newCachedThreadPool 必要时创建新线程，空闲线程会被保留60秒 newFixedThreadPool
	 * 该线程池包含固定数目的线程；空闲线程会一直被保留 newSingleThreadPool 只有一个线程的“池”，该线程顺序执行每一个提交的任务。
	 * newScheduledThreadPool 用于预定而构建固定的线程池，代替java.util.Timer
	 * newSingleThreadScheduleExecutor 用于预定执行而构建的单线程“池”
	 * 当用完一个线程的时候，应该调用shutdown。该方法启动线程的关闭序列。被关闭的执行器不再接受新的任务。
	 * 当所有的任务完成之后，线程池中中的线程死亡。 另一种方法是调用shutdownNow。该先线程取消尚未开始的所有任务，并试图中断正在执行的线程。
	 */

	/**
	 * 执行器的使用： 1. 调用Executors类中的静态方法（上一个注释的几个方法之一） 2.
	 * 调用submit提交Runnable或者Callable对象。 3.
	 * 如果想要取消一个任务，或者如果提交Callable对象，那就要保存好返回的Future对象。 4. 当不再提交任何对象的时候，调用shutdown
	 */

	/**
	 * 预定执行 ScheduledExecutorService接口具有为预定执行或者重复执行任务而设定的方法。
	 * newScheduledThreadPool和newSingleThreadScheduledExcutor方法返回实现了ScheduledExecutorService
	 * 接口的对象
	 */
	
	/**
	 * 控制任务组
	 * invokeAny方法提交所有对象到一个Callable对象的集合中，并返回某个已经完成了的任务结果。
	 * 无法知道是哪一个任务的结果，也许是最先完成的那个任务的结果。
	 * invokeAll方法提交所有对象到一个Callable对象的集合中，并返回一个Future对象构成的列表，
	 * 代表所有任务的解决方案。如下所示:
	 * List<Callable<T>> tasks = new ArrayList<>();
	 * List<Future<T>> results = executor.invokeAll(tasks);
	 * 这种方法获得的结果按照可获的顺序保存起来更有实际意义。可以用ExecutorCompletionService来进行排序。
	 * 方法如下：
	 * ExecutorCompletionService service = new ExecutorCompletionService(executor);
	 * for(Callable<T> task: tasks){
	 * 		service.submit(task);
	 * }
	 * for(int i = 0; i < tasks.size(); i ++){
	 * 		service.take().get();
	 * }
	 */
}
