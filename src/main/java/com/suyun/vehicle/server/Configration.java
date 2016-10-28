package com.suyun.vehicle.server;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * injection bean
 * Created by IT on 16/10/12.
 */
@org.springframework.context.annotation.Configuration
@PropertySource("classpath:vehicle-terminal-server.properties")
@MapperScan("com.suyun.vehicle")
public class Configration {

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.driver}")
    private String jdbcDriver;

    @Value("${jdbc.user}")
    private String jdbcUsername;

    @Value("${jdbc.password}")
    private String jdbcPassword;

    @Value("${elasticSearchURL}")
    private String elasticSearchURL;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(jdbcDriver);
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(jdbcUsername);
        dataSource.setPassword(jdbcPassword);
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setMapperLocations(applicationContext.getResources("classpath*:**/*Mapper.xml"));
//        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:**/*Mapper.xml"));
        factoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        return factoryBean.getObject();
    }

    @Bean
    public JestClient jestClient(){
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder(elasticSearchURL)
                .multiThreaded(true)
                .build());
        return factory.getObject();
    }

}
