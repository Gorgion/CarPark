/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.config;

//import cz.muni.fi.pa165.carpark.config.ServiceCoreConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Web core configuration.
 *
 * @author Tomas Svoboda
 */
@Configuration
//@EnableAsync
@ComponentScan("cz.muni.fi.pa165.carpark")
@Import(ServiceCoreConfig.class)
public class CoreConfig
{
    
}
