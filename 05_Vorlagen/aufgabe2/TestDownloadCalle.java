package aufgabe2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class TestDownloadCalle {
    private static final String[] LINKS = new String[]{
            "https://www.google.com",
            "https://www.bing.com",
            "https://www.yahoo.com",
            "https://www.microsoft.com",
            "https://www.oracle.com"
    };

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        long startTime = System.currentTimeMillis();
        var downloader = new WebDownload();
        ArrayList<CompletableFuture> downloads = new ArrayList<>();
        for (String link : LINKS) {
            var download = downloader.asyncDownloadUrl(link).thenAccept(result -> {
                System.out.println(String.format("%s downloaded (%d characters)", link, result.length()));
            });
            downloads.add(download);
        }
		for (var download : downloads) {
			download.join();
		}
		long endTime = System.currentTimeMillis();
        System.out.println(String.format("total time: %d ms", endTime - startTime));
    }
}
