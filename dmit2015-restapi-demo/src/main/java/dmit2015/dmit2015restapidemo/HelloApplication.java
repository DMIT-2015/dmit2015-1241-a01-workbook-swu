package dmit2015.dmit2015restapidemo;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.auth.LoginConfig;

@ApplicationPath("/restapi")
@LoginConfig(authMethod = "MP-JWT", realmName = "dmit2015-realm")
public class HelloApplication extends Application {

}