package cn.zsq.learn.thread;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolTest {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the directory: ");
		String directory = in.nextLine();
		System.out.println("Enter keyword: ");
		String keyword = in.nextLine();

		ExecutorService pool = Executors.newCachedThreadPool();

		MatchCounter counter = new MatchCounter(new File(directory), keyword,
				pool);
		Future<Integer> result = pool.submit(counter);
		
		System.out.println(result.get() + " match files");

		pool.shutdown();
		int largestPoolSize = ((ThreadPoolExecutor) pool).getLargestPoolSize();
		System.out.println("largest pool size = " + largestPoolSize);
	}

}

class MatchCounter implements Callable<Integer> {
	private File directory;
	private String keyword;
	private ExecutorService pool;
	private int count;

	public MatchCounter(File directory, String keyword, ExecutorService pool) {
		super();
		this.directory = directory;
		this.keyword = keyword;
		this.pool = pool;
		this.count = 0;
	}

	@Override
	public Integer call() throws Exception {
		count = 0;
		File[] files = directory.listFiles();
		List<Future<Integer>> results = new ArrayList<>();

		for (File file : files) {
			if (file.isDirectory()) {
				MatchCounter counter = new MatchCounter(file, keyword, pool);
				Future<Integer> result = pool.submit(counter);
				results.add(result);
			} else {
				if (search(file)) {
					count++;
				}
			}
		}

		for (Future<Integer> result : results) {
			count += result.get();
		}

		return count;
	}

	public boolean search(File file) {
		try (Scanner in = new Scanner(file)) {
			boolean found = false;
			while (!found && in.hasNextLine()) {
				String line = in.nextLine();
				if (line.contains(keyword)) {
					found = true;
				}
			}
			return found;
		} catch (FileNotFoundException e) {
			return false;
		}
	}
}
