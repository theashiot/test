package in.ashiot.examples;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Properties;

import javax.net.ssl.SSLContext;
import org.wildfly.security.auth.client.WildFlyElytronClientDefaultSSLContextProvider;

/**
 * Hello world!
 *
 */

//Run this with mvn compile exec:java -Dexec.cleanupDaemonThreads=false


public class App 
{
    public static void main( String[] args )
    {
    	String url = "https://127.0.0.1:8443/";
    	
        try {
        	        	
        	final Properties props = System.getProperties(); 
        	props.setProperty("jdk.internal.httpclient.disableHostnameVerification", Boolean.TRUE.toString());
        	
        	Security.insertProviderAt(new WildFlyElytronClientDefaultSSLContextProvider("src/wildfly-config-two-way-tls.xml"), 1);
        	HttpClient httpClient = HttpClient.newBuilder().sslContext(SSLContext.getDefault()).build();

			HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(url))
	                .GET()
	                .build();			
			
			HttpResponse<Void> httpRresponse = httpClient.send(request, BodyHandlers.discarding());
		
			String sslContext = SSLContext.getDefault().getProvider().getName();
			String elytronDefaultContext = WildFlyElytronClientDefaultSSLContextProvider.ELYTRON_CLIENT_DEFAULT_SSL_CONTEXT_PROVIDER_NAME;
		
			System.out.println ("\nSSL Default SSLContext is\t\t\t\t\t\t: " + sslContext);
			System.out.println ("\nName of the WildFly Elytron Client Default SSLContext provider is\t: " + elytronDefaultContext);			
						
		} catch (NoSuchAlgorithmException | IOException | InterruptedException e) {
			e.printStackTrace();
		} 
    }
}
