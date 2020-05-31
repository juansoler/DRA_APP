package ual.dra.rest;


import java.lang.reflect.Field;
import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class RestApplication {


	public static void main(String[] args) {
		SpringApplication.run(RestApplication.class, args);
	}
	
	@Component
	public class EntityExposingIdConfiguration extends RepositoryRestConfigurerAdapter {

	    @Override
	    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
	        try {
	            Field exposeIdsFor = RepositoryRestConfiguration.class.getDeclaredField("exposeIdsFor");
	            exposeIdsFor.setAccessible(true);
	            ReflectionUtils.setField(exposeIdsFor, config, new ListAlwaysContains());
	        } catch (NoSuchFieldException e) {
	            e.printStackTrace();
	        }
	    }

	    class ListAlwaysContains extends ArrayList {

	        @Override
	        public boolean contains(Object o) {
	            return true;
	        }
	    }
	}
}