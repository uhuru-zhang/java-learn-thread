package cn.zsq.learn.thread;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 运用递归的思想，查询出该目录下包含keyword的文件个数
 * @author Zhang Siqi
 *
 */
public class MatchConter implements Callable<Integer>{
	private File dir;
	private String keyword;
	private int count;
	
	public MatchConter(File dir, String keyword) {
		this.dir = dir;
		this.keyword = keyword;
	}
	
	@Override
	public Integer call() throws Exception {
		count = 0;
		try {
			File[] files = dir.listFiles();
			List<Future<Integer>> results = new ArrayList<>();
			
			for(File file: files){
				if(file.isDirectory()){
					/**
					 * 如果当前文件是一个目录，那么久继续构造另外一个Future所构成的集合，来启用此方法。
					 */
					MatchConter counter = new MatchConter(file, keyword);
					FutureTask<Integer> task = new FutureTask<>(counter);
					results.add(task);
					Thread t = new Thread(task);
					t.start();
				}else{
					if(search(file)){
						count ++;
					}
				}
				/**
				 * 获得每一个子线程的文件个数之和
				 */
				for(Future<Integer> result: results){
					try{
						count += result.get();
					}catch(ExecutionException e){
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
		}
		
		return count;
	}

	private boolean search(File file) {
		try {
			try(Scanner in = new Scanner(file)) {
				boolean found = false;
				while(!found && in.hasNextLine()){
					String line = in.nextLine();
					if(line.contains(keyword)){
						found = true;
					}
				}
				return found;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
}
