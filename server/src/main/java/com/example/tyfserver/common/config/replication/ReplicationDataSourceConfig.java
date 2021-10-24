package com.example.tyfserver.common.config.replication;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.tyfserver.common.config.replication.ReplicationDataSourceProperties.*;

@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@Profile({"prod", "performance"})
@EnableConfigurationProperties(ReplicationDataSourceProperties.class)
@Configuration
public class ReplicationDataSourceConfig {

    private final ReplicationDataSourceProperties dataSourceProperties;
    private final JpaProperties jpaProperties;

    public ReplicationDataSourceConfig(ReplicationDataSourceProperties dataSourceProperties,
                                       JpaProperties jpaProperties) {
        this.dataSourceProperties = dataSourceProperties;
        this.jpaProperties = jpaProperties;
    }

    // DataSourceProperties 클래스를 통해 yml 파일에서 읽어들인 DataSource 설정들을 실제 DataSource 객체로 생성 후 ReplicationRoutingDataSource에 등록
    @Bean
    public DataSource routingDataSource() {
        DataSource masterDataSource = createDataSource(
                dataSourceProperties.getDriverClassName(),
                dataSourceProperties.getUrl(),
                dataSourceProperties.getUsername(),
                dataSourceProperties.getPassword()
        );

        Map<Object, Object> dataSources = new LinkedHashMap<>();
        dataSources.put("master", masterDataSource);

        for (Slave slave : dataSourceProperties.getSlaves().values()) {
            DataSource slaveDatSource = createDataSource(
                    slave.getDriverClassName(),
                    slave.getUrl(),
                    slave.getUsername(),
                    slave.getPassword()
            );

            dataSources.put(slave.getName(), slaveDatSource);
        }

        ReplicationRoutingDataSource replicationRoutingDataSource = new ReplicationRoutingDataSource();
        replicationRoutingDataSource.setDefaultTargetDataSource(masterDataSource);
        replicationRoutingDataSource.setTargetDataSources(dataSources);

        return replicationRoutingDataSource;
    }

    // DataSource 생성
    public DataSource createDataSource(String driverClassName, String url, String username, String password) {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .url(url)
                .driverClassName(driverClassName)
                .username(username)
                .password(password)
                .build();
    }

    // 매 쿼리 수행마다 Connection 연결
    @Bean
    public DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(routingDataSource());
    }

    // JPA에서 사용하는 EntityManagerFactory 설정. hibernate 설정을 직접 주입한다.
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        EntityManagerFactoryBuilder entityManagerFactoryBuilder = createEntityManagerFactoryBuilder(jpaProperties);
        return entityManagerFactoryBuilder.dataSource(dataSource())
                .packages("com.example.tyfserver")
                .build();
    }

    private EntityManagerFactoryBuilder createEntityManagerFactoryBuilder(JpaProperties jpaProperties) {
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        return new EntityManagerFactoryBuilder(vendorAdapter, jpaProperties.getProperties(), null);
    }

    // JPA에서 사용할 TransactionManager 설정
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(entityManagerFactory);
        return tm;
    }
}
