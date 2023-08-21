package com.example.prog4.repository.CnapsEmployee.conf;

import jakarta.persistence.EntityManagerFactory;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.MigrateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@PropertySource({"application.properties"})
@Component
@EnableJpaRepositories(
        basePackages = "com.example.prog4.repository.CnapsEmployee",
        entityManagerFactoryRef = "cnapsEmployeeEntityManager",
        transactionManagerRef = "cnapsEmployeeTransactionManager")
public class CnapsEmployeeConfiguration {

    @Autowired
    Environment env;

    @Bean(name = "cnaps_employees")
    public DataSource cnapsDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(env.getProperty("spring.secondary.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.secondary.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.secondary.datasource.password"));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean cnapsEmployeeEntityManager(
            EntityManagerFactoryBuilder builder,
            @Qualifier("cnaps_employees") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.example.prog4.repository.CnapsEmployee.entity")
                .persistenceUnit("cnaps_employees")
                .build();
    }

    @Bean(name = "cnapsEmployeeTransactionManager")
    public PlatformTransactionManager cnapsEmployeeTransactionManager(
            @Qualifier("cnapsEmployeeEntityManager") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public MigrateResult flywayDataSource2() {
       return  Flyway.configure()
                .dataSource(cnapsDataSource())
                .locations("classpath:db/migration/CnapsEmployee")
                .load()
                .migrate()
                ;
    }

}
