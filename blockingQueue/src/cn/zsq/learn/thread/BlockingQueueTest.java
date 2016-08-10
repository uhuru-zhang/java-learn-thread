package cn.zsq.learn.thread;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueTest {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter base directory (e.g. D:/test)");
		String directory = in.nextLine();
		System.out.print("Enter keyword:");
		String keyword = in.nextLine();
		final int FILE_QUEUE_SIZE = 10;
		final int SEARCH_THREDS = 100;
		
		BlockingQueue<File> queue = new ArrayBlockingQueue<File>(FILE_QUEUE_SIZE);
		FileEnumerationTask enumerator = new FileEnumerationTask
				(queue, new File(directory));
		new Thread(enumerator).start();
		for(int i = 0; i < SEARCH_THREDS + 1; i ++){
			new Thread(new SearchTask(queue, keyword)).start();
		}
		
	}
}
