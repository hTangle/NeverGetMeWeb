package com.nevergetme.nevergetmeweb;

import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.framework.DefaultAopProxyFactory;
import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.aop.framework.adapter.AfterReturningAdviceInterceptor;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.DefaultBeanDefinitionDocumentReader;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class NevergetmewebApplication extends SpringBootServletInitializer {

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
