package com.nevergetme.nevergetmeweb;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


@EnableCaching
@SpringBootApplication
public class NevergetmewebApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(NevergetmewebApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(NevergetmewebApplication.class, args);
	}
//	AbstractXmlApplicationContext
//	XmlBeanDefinitionReader
//	DefaultBeanDefinitionDocumentReader
//	DefaultListableBeanFactory
//	AbstractXmlApplicationContext
//	ClassPathXmlApplicationContext
//	AbstractAutowireCapableBeanFactory
//	DefaultAdvisorAutoProxyCreator
//	AnnotationAwareAspectJAutoProxyCreator
//	ReflectiveMethodInvocation
//	JdkDynamicAopProxy
//	DefaultAopProxyFactory
//	AfterReturningAdviceInterceptor
}
