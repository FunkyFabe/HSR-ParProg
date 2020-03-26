package aufgabe2;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

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
}
