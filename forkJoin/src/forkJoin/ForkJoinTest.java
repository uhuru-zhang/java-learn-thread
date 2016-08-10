package forkJoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 有些应用使用了大量线程，但是其中大多数都是空闲的。
 * 如果有些任务可以很自然地分解为子任务，如下面所示：
 * if（problemSize < threshold）{
 * 	solve problem directly
 * }else{
 * 	break problem into subproblems
 * 	recursively solve each subproblem
 * 	combine the results
 * }
 * 
 * 采用框架可用的一种方式来完成这种递归计算，需要提供一个扩展RecursiveTask<T>的类，
 * 或者扩展一个RecursiveAction的类（如果不产生任何结果）。在覆盖compute方法来生成并调用子任务，然后合并
 */
public class ForkJoinTest {

	public static void main(String[] args) {
		final int SIZE = 10000000;
		double[] numbers = new double[SIZE];
		for(int i = 0; i < SIZE; i ++){
			numbers[i] = Math.random();
		}
		Counter counter = new Counter(numbers, 0, numbers.length, new Filter() {
			
			@Override
			public boolean accept(double t) {
				return t > 0.5;
			}
		});
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(counter);
		System.out.println(counter.join());
	}
}

interface Filter{
	boolean accept(double t);
}

class Counter extends RecursiveTask<Integer>{
	public static final int THRESHOLD = 1000;
	private double[] values;
	private int from;
	private int to;
	private Filter filter;
	
	public Counter(double[] values, int from, int to, Filter filter){
		this.values = values;
		this.from = from;
		this.to = to;
		this.filter = filter;
	}

	@Override
	protected Integer compute() {
		if(to - from < THRESHOLD){
			int count = 0;
			for(int i = from; i < to; i ++){
				if(filter.accept(values[i])){
					count ++;
				}
			}
			return count;
		}else{
			int mid = (from + to) / 2;
			Counter first = new Counter(values, from, mid, filter);
			Counter second = new Counter(values, mid, to, filter);
			invokeAll(first, second);
			return first.join() + second.join();
		}
	}
}
/**
 * 同步器
 * java.util.concurrent包包含了几个能帮助人们管理相互合作的线程的线程集的类。这些机制具有为线程之间的
 * 共用集结点模式提供的“预置功能”。如果有一个相互合作的线程满足这些行为模式之一，那么应该直接重用合适的类库
 * 而不要试图提供手工的锁与条件的集合
 */