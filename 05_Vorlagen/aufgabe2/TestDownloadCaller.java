package aufgabe2;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class TestDownloadCaller {
	private static final String[] LINKS = new String[] {
			"https://www.google.com", 
			"https://www.bing.com",
			"https://www.yahoo.com", 
			"https://www.microsoft.com",
			"https://www.oracle.com" 
	};

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		long startTime = System.currentTimeMillis();
		var downloader = new WebDownload();
		var results = new HashMap<String, CompletableFuture<String>>();
		for (int i = 0; i < LINKS.length; i++) {
			String link = LINKS[i];
			results.put(link, downloader.asyncDownloadUrl(link));
		}
		for (String key :results.keySet()) {
			System.out.println(String.format("%s downloaded (%d characters)", key, results.get(key).get().length()));
		}
		long endTime = System.currentTimeMillis();
		System.out.println(String.format("total time: %d ms", endTime - startTime));
	}
}
