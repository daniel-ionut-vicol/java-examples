package com.pavelsklenar.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RestApiClientAuthApplicationTests {
	// Create a trust manager that does not validate certificate chains
    private static TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager()
    {
        public java.security.cert.X509Certificate[] getAcceptedIssuers()
        {
            return new java.security.cert.X509Certificate[] {};
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException
        {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException
        {
        }
    } };
    
    @LocalServerPort
    int randomPort;
    
	@Test
	public void contextLoads() throws IOException, UnrecoverableKeyException, KeyManagementException, KeyStoreException, NoSuchAlgorithmException, CertificateException {
		HttpsURLConnection.setDefaultHostnameVerifier ((hostname, session) -> true);
		URL url = new URL("https://localhost:"+randomPort+"/attachment");
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		if (urlConn instanceof HttpsURLConnection) {
			SSLSocketFactory sslContextFactory = getSSLSocketFactory();
			HttpsURLConnection.setDefaultSSLSocketFactory(sslContextFactory);
			if(sslContextFactory!=null){
                ((HttpsURLConnection)urlConn).setSSLSocketFactory(sslContextFactory);
            }
		}
		urlConn.setDoOutput(true);
		urlConn.setRequestMethod("POST");
		String auth = "daniel" + ":" + "daniel";
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.ISO_8859_1));
        //auth = "Basic " + new String(encodedAuth);
		//urlConn.setRequestProperty("Authorization", auth);
		urlConn.setRequestProperty("Accept", "application/json");
		urlConn.setRequestProperty("Content-Type", "application/json");
		
		urlConn.setRequestProperty("businessCode", "GCB");
		urlConn.setRequestProperty("countryCode", "TW");
		
		String requestBody = "{\"requestBody\":{\"imageRequest\":{\"country_Code\":\"TW\",\"image_ID\":\"09021b6680079d1a9\"}}}";
		urlConn.connect();
        if (requestBody != null)
        {
        	urlConn.getOutputStream().write(requestBody.getBytes("UTF-8"));
        }
        urlConn.getOutputStream().flush();
        int responseCode = urlConn.getResponseCode();
        byte[] responseBytes = fetchURLBytes(urlConn);
        String resp = new String(responseBytes);
        System.out.println(resp);
        
	}

	public SSLSocketFactory getSSLSocketFactory() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException, KeyManagementException {
		SSLSocketFactory sslContextFactory = null;
		KeyStore ks = null;

		ks = KeyStore.getInstance("JKS");
		InputStream certInputStream = RestApiClientAuthApplicationTests.class.getClassLoader()
				.getResourceAsStream("client.jks");
		ks.load(certInputStream, "changeit".toCharArray());

		// Get a KeyManager and initialize it
		KeyManagerFactory kmf = KeyManagerFactory.getInstance("sunx509");
		kmf.init(ks, "changeit".toCharArray());

		// Get a TrustManagerFactory and init with KeyStore
		TrustManagerFactory tmf = TrustManagerFactory.getInstance("sunx509");
		tmf.init(ks);

		// Get the SSLContext to help create SSLSocketFactory
		SSLContext sslc = SSLContext.getInstance("TLS");

		sslc.init(kmf.getKeyManagers(), trustAllCerts, null);
		sslContextFactory = sslc.getSocketFactory();
		return sslContextFactory;
	}
	
	public static byte[] fetchURLBytes(HttpURLConnection urlconn) throws IOException, MalformedURLException
	{
		byte[] buf = null;
		InputStream urlInputStream = null;

		int contentLength = urlconn.getContentLength();
		try
		{
			urlInputStream = urlconn.getInputStream();
			ByteArrayOutputStream baos = null;
			if (contentLength > 0)
			{
				baos = new ByteArrayOutputStream(contentLength);
			}
			else
			{
				baos = new ByteArrayOutputStream();
			}
			buf = new byte[512];
			while (true)
			{
				int cc = urlInputStream.read(buf, 0, 512);
				if (cc == -1)
				{
					break;
				}
				baos.write(buf, 0, cc);
			}
			buf = baos.toByteArray();
			return buf;
		}
		finally
		{
			if (urlInputStream != null)
			{
				try
				{
					urlInputStream.close();
				}
				catch (Exception x)
				{
				}
			}
		}
	}

}
