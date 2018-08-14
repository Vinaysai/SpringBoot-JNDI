package com.spring.boot.all;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jndi.JndiObjectFactoryBean;

@SpringBootApplication
@ComponentScan
public class SpringBootJndiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJndiApplication.class, args);
	}

	@Bean
	public TomcatEmbeddedServletContainerFactory tomcatFactory() { // if datasouce defind in tomcat xml configuration
																	// then no need to create this bean
		return new TomcatEmbeddedServletContainerFactory() {
			@Override
			protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(Tomcat tomcat) {
				tomcat.enableNaming();
				return super.getTomcatEmbeddedServletContainer(tomcat);
			}

			@Override // create JNDI resource
			protected void postProcessContext(Context context) {
				ContextResource resource = new ContextResource();
				resource.setName("jndiDataSource");
				resource.setType(DataSource.class.getName());
				resource.setProperty("factory", "org.apache.tomcat.jdbc.pool.DataSourceFactory");
				resource.setProperty("driverClassName", "com.mysql.jdbc.Driver");
				resource.setProperty("url", "jdbc:mysql://localhost:3306/spring");
				resource.setProperty("username", "root");
				resource.setProperty("password", "2837");
				context.getNamingResources().addResource(resource);
			}
		};
	}

	@Bean
	public DataSource jndiDataSource() throws IllegalArgumentException, NamingException {
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		// create JNDI data source
		bean.setJndiName("java:/comp/env/jndiDataSource");
		// jndiDataSource is name of JNDI data source
		bean.setProxyInterface(DataSource.class);
		bean.setLookupOnStartup(false);
		bean.afterPropertiesSet();
		return (DataSource) bean.getObject();
	}
}
