package ftn.uns.ac.rs.ncandrej.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "file")
public class FileConfiguration {
	private String uploadDir;
}
