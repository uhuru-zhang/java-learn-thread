package cn.zsq.learn.thread;

import java.io.File;
import java.util.concurrent.BlockingQueue;

public class FileEnumerationTask implements Runnable {
	public static File DUMMY = new File("");
	private BlockingQueue<File> queue;
	private File startingDirectory;

	public FileEnumerationTask(BlockingQueue<File> queue, File startingDirectory) {
		super();
		this.queue = queue;
		this.startingDirectory = startingDirectory;
	}

	@Override
	public void run() {
		try {
			enumerate(startingDirectory);
			queue.put(DUMMY);
		} catch (InterruptedException e) {
			System.out.println();
		}
	}

	public void enumerate(File directory) throws InterruptedException{
		File[] files = directory.listFiles();
		for(File file: files){
			if(file.isDirectory()){
				enumerate(file);
			}else{
				queue.put(file);
			}
		}
	}
}
