package cn.zsq.learn.thread;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MathConterTest {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the dir:");
		String dir = in.nextLine();
		System.out.println("Enter the keyword:");
		String keyword = in.nextLine();
		
		MatchConter counter = new MatchConter(new File(dir), keyword);
		FutureTask<Integer> task = new FutureTask<>(counter);
		Thread t = new Thread(task);
		t.start();
		System.out.println(task.get() + " match files");
	}
}
