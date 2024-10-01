package dmit2015.config;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.annotation.sql.DataSourceDefinitions;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Define Jakarta Transaction API (JTA) data source definitions for usage in a
 * development environment that can reference the `name` attribute of the `@DataSourceDefinition`
 * in `persistence.xml` using the `<jta-data-source>` element.
 * <p>
 * In a production environment where the data source definition are defined in operating system environment variables
 * the <a href="https://github.com/wildfly-extras/wildfly-datasources-galleon-pack">WildFly Datasources Galleon Feature-Pack</a>
 * are used as an alternative to these data source definitions.
 */
@DataSourceDefinitions({

        @DataSourceDefinition(
                name = "java:app/datasources/H2DatabaseDS",
                className = "org.h2.jdbcx.JdbcDataSource",
                // url="jdbc:h2:file:~/jdk/databases/h2/DMIT201CourseDB;",
                url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;",
                user = "user2015",
                password = "Password2015"),

//	@DataSourceDefinition(
//		name="java:app/datasources/MSSQLServerDS",
//		className="com.microsoft.sqlserver.jdbc.SQLServerDataSource",
//		url="jdbc:sqlserver://localhost;databaseName=DMIT2015CourseDB;TrustServerCertificate=true",
//		user="user2015",
//		password="Password2015"),
//
//	@DataSourceDefinition(
//		name="java:app/datasources/OracleDS",
//		className="oracle.jdbc.pool.OracleDataSource",
//		url="jdbc:oracle:thin:@localhost:1521/FREEPDB1",
//		user="user2015",
//		password="Password2015"),
//
//	@DataSourceDefinition(
//		name="java:app/datasources/OracleHrDS",
//		className="oracle.jdbc.xa.client.OracleXADataSource",
//		url="jdbc:oracle:thin:@localhost:1521/FREEPDB1",
//		user="HR",
//		password="Password2015"),
//
//	@DataSourceDefinition(
//		name="java:app/datasources/OracleCoDS",
//		className="oracle.jdbc.xa.client.OracleXADataSource",
//		url="jdbc:oracle:thin:@localhost:1521/FREEPDB1",
//		user="CO",
//		password="Password2015"),
//
	@DataSourceDefinition(
		name="java:app/datasources/PostgreSQLDS",
		className="org.postgresql.xa.PGXADataSource",
		url="jdbc:postgresql://localhost/DMIT2015CourseDB",
		user="user2015",
		password="Password2015"),
//
//	@DataSourceDefinition(
//		name="java:app/datasources/MySQLDS",
//		className="com.mysql.cj.jdbc.MysqlDataSourcee",
//		url="jdbc:mysql://localhost/DMIT2015CourseDB",
//		user="user2015",
//		password="Password2015"),
//
//	@DataSourceDefinition(
//		name="java:app/datasources/MariaDBDS",
//		className="org.mariadb.jdbc.MySQLDataSource",
//		url="jdbc:mariadb://localhost/DMIT2015CourseDB",
//		user="user2015",
//		password="Password2015"),

})

@ApplicationScoped
public class ApplicationConfig {

}