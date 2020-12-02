/**
 *    Copyright 2009-2019 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.vonchange.common.ibatis.session;



import com.vonchange.common.ibatis.logging.jdk14.Jdk14LoggingImpl;

import com.vonchange.common.ibatis.logging.nologging.NoLoggingImpl;
import com.vonchange.common.ibatis.logging.slf4j.Slf4jImpl;
import com.vonchange.common.ibatis.logging.stdout.StdOutImpl;
import com.vonchange.common.ibatis.mapping.VendorDatabaseIdProvider;
import com.vonchange.common.ibatis.reflection.DefaultReflectorFactory;
import com.vonchange.common.ibatis.reflection.MetaObject;
import com.vonchange.common.ibatis.reflection.ReflectorFactory;
import com.vonchange.common.ibatis.reflection.factory.DefaultObjectFactory;
import com.vonchange.common.ibatis.reflection.factory.ObjectFactory;
import com.vonchange.common.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import com.vonchange.common.ibatis.reflection.wrapper.ObjectWrapperFactory;
import com.vonchange.common.ibatis.scripting.LanguageDriverRegistry;
import com.vonchange.common.ibatis.scripting.defaults.RawLanguageDriver;
import com.vonchange.common.ibatis.scripting.xmltags.XMLLanguageDriver;
import com.vonchange.common.ibatis.type.TypeHandlerRegistry;
import com.vonchange.common.ibatis.type.TypeAliasRegistry;

import java.util.Properties;

/**
 * @author Clinton Begin
 */
public class Configuration {


  protected Properties variables = new Properties();
  protected ReflectorFactory reflectorFactory = new DefaultReflectorFactory();
  protected ObjectFactory objectFactory = new DefaultObjectFactory();
  protected ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();

  protected boolean lazyLoadingEnabled = false;


  protected String databaseId;


  protected final TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry();
  protected final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();
  protected final LanguageDriverRegistry languageRegistry = new LanguageDriverRegistry();




  public Configuration() {

    typeAliasRegistry.registerAlias("DB_VENDOR", VendorDatabaseIdProvider.class);

    typeAliasRegistry.registerAlias("XML", XMLLanguageDriver.class);
    typeAliasRegistry.registerAlias("RAW", RawLanguageDriver.class);
    typeAliasRegistry.registerAlias("SLF4J", Slf4jImpl.class);
    typeAliasRegistry.registerAlias("JDK_LOGGING", Jdk14LoggingImpl.class);
    typeAliasRegistry.registerAlias("STDOUT_LOGGING", StdOutImpl.class);
    typeAliasRegistry.registerAlias("NO_LOGGING", NoLoggingImpl.class);
    languageRegistry.setDefaultDriverClass(XMLLanguageDriver.class);
    languageRegistry.register(RawLanguageDriver.class);
  }


  public void setVariables(Properties variables) {
    this.variables = variables;
  }

  public String getDatabaseId() {
    return databaseId;
  }



  public boolean isLazyLoadingEnabled() {
    return lazyLoadingEnabled;
  }



  public Properties getVariables() {
    return variables;
  }


  public TypeHandlerRegistry getTypeHandlerRegistry() {
    return typeHandlerRegistry;
  }



  public TypeAliasRegistry getTypeAliasRegistry() {
    return typeAliasRegistry;
  }



  public ReflectorFactory getReflectorFactory() {
    return reflectorFactory;
  }



  public MetaObject newMetaObject(Object object) {
    return MetaObject.forObject(object, objectFactory, objectWrapperFactory, reflectorFactory);
  }

}
