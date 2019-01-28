import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.SynchronousQueue;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;


public class Main {
	public static String url = "http://localhost:8080/GuessServer/main/hash";
	
	public static void main(String[] args) {
		while(true) {
			//int i = 0;
			long startTime = System.currentTimeMillis();
			try {
				
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("GET");
				int responseCode = con.getResponseCode();
				System.out.println("Sending GET to: " + url);
				System.out.println("Response code: " + responseCode);
				
				BufferedReader in = new BufferedReader(
						new InputStreamReader(con.getInputStream()));
				
				String inputLine;
				StringBuffer response = new StringBuffer();
				
				while((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				
				in.close();
				
				int len = Integer.parseInt(
						response.toString().split(":")[2].replace("}", ""));
				byte[] digest = Base64.getDecoder().decode(response.
						toString().split("\"")[3].getBytes("UTF-8"));
				System.out.println("Length: " + len);
				
				while(true) {
					//i++;
					Random rand = new Random();
					byte [] guess = new byte[len];
					rand.nextBytes(guess);
					MessageDigest md = MessageDigest.getInstance("MD5");
					byte[] guessedDigest = md.digest(guess);
					
//					if(startTime - System.currentTimeMillis() == 1000) {
//						System.out.println(i);
//						return;
//					}
					if(Arrays.equals(digest, guessedDigest)) {
						HttpClient httpClient = HttpClientBuilder.create().build();
						HttpPost req = new HttpPost(url);
						StringEntity params = new StringEntity("{\"HASH\":\""+Base64.getEncoder().encodeToString(guessedDigest)+
								"\", \"INPUT\":\"" + Base64.getEncoder().encodeToString(guess) + "\"}");
						req.addHeader("content-type", "application/json");
						req.setEntity(params);
						HttpResponse resp = httpClient.execute(req);
						int code = resp.getStatusLine().getStatusCode();
						
						if(code == 200) {
							System.out.println("Successful guess");
							break;
						}else {
							System.out.println(code);
						}
					}
					
				}
			
			}catch(Exception e) {
				e.printStackTrace();
			}
			System.out.println(System.currentTimeMillis() - startTime);
		}
	}
}