//package com.example.tyfserver.common.config.replication;
//
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
//@Profile({"prod", "performance"})
//@Configuration
//@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
//@EnableConfigurationProperties(MasterDataSourceProperties.class)
//public class DataSourceConfiguration {
//
//    private final MasterDataSourceProperties dataSourceProperties;
//    private final JpaProperties jpaProperties;
//
//    public DataSourceConfiguration(
//            MasterDataSourceProperties dataSourceProperties,
//            JpaProperties jpaProperties
//    ) {
//        this.dataSourceProperties = dataSourceProperties;
//        this.jpaProperties = jpaProperties;
//    }
//
//    @Bean //1)번 부분
//    public DataSource routingDataSource() {
//        DataSource master = createDataSource(
//                dataSourceProperties.getUrl(),
//                dataSourceProperties.getUsername(),
//                dataSourceProperties.getPassword()
//        );
//
//        Map<Object, Object> dataSources = new HashMap<>();
//        dataSources.put("master", master);
//        dataSourceProperties.getSlave().forEach((key, value) ->
//                dataSources.put(value.getName(), createDataSource(
//                        value.getUrl(), value.getUsername(), value.getPassword()
//                ))
//        );
//
//        ReplicationRoutingDataSource replicationRoutingDataSource = new ReplicationRoutingDataSource();
//        replicationRoutingDataSource.setDefaultTargetDataSource(dataSources.get("master"));
//        replicationRoutingDataSource.setTargetDataSources(dataSources);
//
//        return replicationRoutingDataSource;
//    }
//
//    private DataSource createDataSource(String url, String username, String password) {
//        return DataSourceBuilder.create()
//                .type(HikariDataSource.class)
//                .url(url)
//                .driverClassName("com.mysql.cj.jdbc.Driver")
//                .username(username)
//                .password(password)
//                .build();
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        EntityManagerFactoryBuilder entityManagerFactoryBuilder =
//                createEntityManagerFactoryBuilder(jpaProperties);
//
//        return entityManagerFactoryBuilder.dataSource(dataSource())
//                .packages("com.example.tyfserver")
//                .build();
//    }
//
//    private EntityManagerFactoryBuilder createEntityManagerFactoryBuilder(
//            JpaProperties jpaProperties
//    ) {
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        return new EntityManagerFactoryBuilder(vendorAdapter, jpaProperties.getProperties(), null);
//    }
//
//    private DataSource dataSource() {
//        return new LazyConnectionDataSourceProxy(routingDataSource());
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager(
//            EntityManagerFactory entityManagerFactory
//    ) {
//        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
//        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
//        return jpaTransactionManager;
//    }
//}
