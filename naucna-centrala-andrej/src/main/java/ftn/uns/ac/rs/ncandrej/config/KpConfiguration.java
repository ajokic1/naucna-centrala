package ftn.uns.ac.rs.ncandrej.config;

import java.util.HashMap;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "kp")
public class KpConfiguration {
	private HashMap<String, String> urls = new HashMap<>();
}
