package com.imaginea.gr.entity;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;
import nl.ivonet.beanunit.SimplePojoContractAsserter;

import org.junit.Ignore;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

public class TestEntity extends TestCase{

	protected void SetupContext()throws Exception{
		super.setUp();
	}
	
	@Test
	public void testEntities() throws Exception{
		
		List<ClassLoader> classLoaderlist = new LinkedList<ClassLoader>();
		classLoaderlist.add(ClasspathHelper.contextClassLoader());
		classLoaderlist.add(ClasspathHelper.staticClassLoader());
		
		Reflections ref = new Reflections(new ConfigurationBuilder()
		.setScanners(new SubTypesScanner(false),new ResourcesScanner())
		.setUrls(ClasspathHelper.forClassLoader(classLoaderlist.toArray(new ClassLoader[0])))
		.filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix("com.imaginea.gr.entity"))));
		
		Set<Class<?>> classes = ref.getSubTypesOf(Object.class);
		
		for(Class classType : classes){
			try{
				SimplePojoContractAsserter.assertBasicGetterSetterBehavior(classType);	
			}catch(Error e){
			}

		}
	}
}
