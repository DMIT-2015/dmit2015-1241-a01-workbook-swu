package dmit2015.config;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.annotation.sql.DataSourceDefinitions;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.annotation.FacesConfig;

@DataSourceDefinitions({

		@DataSourceDefinition(
				name = "java:app/datasources/H2DatabaseDS",
				className = "org.h2.jdbcx.JdbcDataSource",
				 url="jdbc:h2:file:~/jdk/databases/h2/DMIT2015CourseDB;",
//				url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;",
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
//		name="java:app/datasources/PostgreSQLDS",
//		className="org.postgresql.ds.PGConnectionPoolDataSource",
//		url="jdbc:postgresql://localhost/DMIT2015CourseDB",
//		user="user2015",
//		password="Password2015"),
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

@FacesConfig
@ApplicationScoped
public class ApplicationConfig {

}