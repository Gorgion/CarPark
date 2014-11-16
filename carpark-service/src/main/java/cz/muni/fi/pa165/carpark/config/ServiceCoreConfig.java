/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Service configuration.
 *
 * @author Tomas Svoboda
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages =
{
    "cz.muni.fi.pa165.carpark"
})
@Import(DaoCoreConfig.class)
public class ServiceCoreConfig
{
}
