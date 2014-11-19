/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.config;

import javax.annotation.Resource;
import javax.sql.DataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.annotation.PersistenceExceptionTranslationAdvisor;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;

/**
 * Dao core configuration.
 *
 * @author Tomas Svoboda
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages =
{
    "cz.muni.fi.pa165.carpark"
})
@PropertySource("classpath:config/database.properties")
public class DaoCoreConfig
{
    private static final String PROPERTY_NAME_DATABASE_DRIVER = "database.driver";
    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "database.password";
    private static final String PROPERTY_NAME_DATABASE_URL = "database.url";
    private static final String PROPERTY_NAME_DATABASE_USERNAME = "database.username";
    
    @Resource
    private Environment env;
    
    @Bean
    public JpaTransactionManager transactionManager()
    {
        return new JpaTransactionManager(jpaFactoryBean().getObject());
    }
    
    @Bean
    public LocalContainerEntityManagerFactoryBean jpaFactoryBean()
    {
        LocalContainerEntityManagerFactoryBean jpaFactoryBean = new LocalContainerEntityManagerFactoryBean();
        jpaFactoryBean.setDataSource(dataSource());
        jpaFactoryBean.setPersistenceUnitName("CarParkPU");
        jpaFactoryBean.setLoadTimeWeaver(instrumentationLoadTimeWeaver());
        jpaFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        return jpaFactoryBean;
    }

    @Bean
    public LoadTimeWeaver instrumentationLoadTimeWeaver()
    {
        return new InstrumentationLoadTimeWeaver();
    }

    @Bean
    public DataSource dataSource()
    {
//        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
//        EmbeddedDatabase dataSource = builder.setType(EmbeddedDatabaseType.DERBY).build();
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
//        dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
//        dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
//        dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
        
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
        dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
        dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
        dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
        
        return dataSource;
    }
    
    @Bean
    public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor()
    {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
