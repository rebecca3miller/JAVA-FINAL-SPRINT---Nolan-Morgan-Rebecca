package database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Creates PostgreSQL connections from environment-based configuration. */
public final class DatabaseConnection {

	private static final String DEFAULT_URL = "jdbc:postgresql://localhost:5432/gym_management";

	private DatabaseConnection() {
	}

	/**
	 * Opens a connection using GYM_DB_URL, GYM_DB_USER, and GYM_DB_PASSWORD.
	 * The URL defaults to a local gym_management database.
	 *
	 * @return an open PostgreSQL connection
	 * @throws IOException when configuration is missing or the connection fails
	 */
	public static Connection getConnection() throws IOException {
		String url = environmentOrDefault("GYM_DB_URL", DEFAULT_URL);
		String username = System.getenv("GYM_DB_USER");
		String password = System.getenv("GYM_DB_PASSWORD");

		if (username == null || username.isBlank() || password == null) {
			throw new IOException("Set GYM_DB_USER and GYM_DB_PASSWORD before starting the application.");
		}

		try {
			return DriverManager.getConnection(url, username, password);
		} catch (SQLException exception) {
			throw new IOException("Unable to connect to PostgreSQL at " + url + ".", exception);
		}
	}

	private static String environmentOrDefault(String variableName, String defaultValue) {
		String value = System.getenv(variableName);
		return value == null || value.isBlank() ? defaultValue : value;
	}
}
