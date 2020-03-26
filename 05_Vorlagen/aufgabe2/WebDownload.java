package aufgabe2;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class WebDownload {
    public String downloadUrl(String link) throws IOException {
        var url = new URL(link);
        var stringBuilder = new StringBuilder();
        try (Reader reader = new InputStreamReader(url.openStream())) {
            int i;
            while ((i = reader.read()) >= 0) {
                stringBuilder.append((char) i);
            }
        }
        return stringBuilder.toString();
    }

    public CompletableFuture<String> asyncDownloadUrl(String link) {
        return CompletableFuture.supplyAsync(() -> {
            var stringBuilder = new StringBuilder();
            try {
                var url = new URL(link);
                try (Reader reader = new InputStreamReader(url.openStream())) {
                    int i;
                    while ((i = reader.read()) >= 0) {
                        stringBuilder.append((char) i);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException();
            }
            return stringBuilder.toString();
        });
    }
}
