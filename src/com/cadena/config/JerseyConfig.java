package com.cadena.config;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import com.cadena.service.LocationService;

@ApplicationPath("rest")
public class JerseyConfig extends Application {

	
	@Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<>();
        s.add(LocationService.class);
        return s;
    }
}
