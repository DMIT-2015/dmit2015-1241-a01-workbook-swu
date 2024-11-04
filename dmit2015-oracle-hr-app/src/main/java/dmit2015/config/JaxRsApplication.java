package dmit2015.config;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * This class enables Jakarta RESTful Web Services and identifies the application path that serves
 * as the base URI for all resource URIs provided by Path.
 */
@ApplicationPath("restapi")
public class JaxRsApplication extends Application {

}