package ftn.uns.ac.rs.ncandrej.config;

import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SSLConfiguration {
    //@Value("${http.client.ssl.trust-store}")
    //private File trustStore;

    @Value("${http.client.ssl.trust-store-password}")
    private String trustStorePassword;

//    @Value("${http.client.ssl.key-store}")
//    private File keyStore;

    @Value("${http.client.ssl.key-store-password}")
    private String keyStorePassword;
    
    @Bean
    public RestTemplate restTemplate() throws Exception {
    	ClassLoader cl = this.getClass().getClassLoader();
    	InputStream inputStream = cl.getResourceAsStream("/keystore.jks");
    	KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
    	ks.load(inputStream, keyStorePassword.toCharArray());
    	SSLContext sslContext = new SSLContextBuilder()
    			.loadKeyMaterial(ks, keyStorePassword.toCharArray())
    			.loadTrustMaterial(ks, new TrustAllStrategy())
    			.build();
    	SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
        HttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(socketFactory)
                .build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(factory);
    }
    
}
