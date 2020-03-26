package aufgabe2;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class TestDownload {
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
		for (int i = 0; i < LINKS.length; i++) {
			String link = LINKS[i];
			String result = downloader.downloadUrl(link);
			System.out.println(String.format("%s downloaded (%d characters)", link, result.length()));
		}
		long endTime = System.currentTimeMillis();
		System.out.println(String.format("total time: %d ms", endTime - startTime));
	}
}
