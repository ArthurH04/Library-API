package io.github.library.libraryapi.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DatabaseConfiguration {

	@Value("${spring.datasource.url}")
	String url;
	@Value("${spring.datasource.username}")
	String username;
	@Value("${spring.datasource.password}")
	String password;
	@Value("${spring.datasource.driver-class-name}")
	String driver;
    
	@Bean
	public DataSource hikariDataSource() {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(url);
		config.setUsername(username);
		config.setPassword(password);
		config.setDriverClassName(driver);

		config.setMinimumIdle(1);
		config.setMaximumPoolSize(10);
		config.setPoolName("library-db-pool");
		config.setMaxLifetime(600000);
		config.setConnectionTimeout(100000);
		config.setConnectionTestQuery("SELECT 1");

		return new HikariDataSource(config);
	}
}
