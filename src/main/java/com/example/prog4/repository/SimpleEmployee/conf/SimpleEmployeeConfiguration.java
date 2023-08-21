package com.example.prog4.repository.SimpleEmployee.conf;

import jakarta.persistence.EntityManagerFactory;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.MigrateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
@Component
@PropertySource({"application.properties"})
@EnableJpaRepositories(
        basePackages = "com.example.prog4.repository.SimpleEmployee",
        entityManagerFactoryRef = "employeeEntityManager",
        transactionManagerRef = "employeeTransactionManager")
public class SimpleEmployeeConfiguration {
    @Autowired
    Environment env;

    @Primary
    @Bean(name = "employees")
    public DataSource employeeDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        return dataSource;
    }

    @Primary
    @Bean(name = "employeeEntityManager")
    public LocalContainerEntityManagerFactoryBean employeeEntityFactoryManager(
            EntityManagerFactoryBuilder builder,
            @Qualifier("employees") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.example.prog4.repository.SimpleEmployee.entity")
                .persistenceUnit("employees")
                .build();
    }

    @Primary
    @Bean(name = "employeeTransactionManager")
    public PlatformTransactionManager employeeTransactionManager(
            @Qualifier("employeeEntityManager") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Primary
    public MigrateResult flywayDataSource1(@Qualifier("employees") DataSource dataSource) {
       return Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration/SimpleEmployee")
                .load()
               .migrate();
    }
}
