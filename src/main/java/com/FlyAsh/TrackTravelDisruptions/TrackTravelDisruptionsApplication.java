package com.FlyAsh.TrackTravelDisruptions;


import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.sql.SQLException;

@SpringBootApplication
public class TrackTravelDisruptionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackTravelDisruptionsApplication.class, args);
	}


	/**
	 * Start internal H2 server so we can query the DB from IDE
	 *
	 * @return H2 Server instance
	 * @throws SQLException
	 */
	@Profile("dev")
	@Bean(initMethod = "start", destroyMethod = "stop")
	public Server h2Server() throws SQLException {
		return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
	}

}
