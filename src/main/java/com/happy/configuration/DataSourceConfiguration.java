package com.happy.configuration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan("com.happy.database.mapper")
public class DataSourceConfiguration {

    @Primary
    @Bean(name = "batchDatasource")
    @ConfigurationProperties(prefix = "spring.hsql-datasource")
    public DataSource batchDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "oracleDatasource")
    @ConfigurationProperties(prefix = "spring.oracle-datasource")
    public DataSource oracleDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "oracleSessionFactory")
    public SqlSessionFactory oracleSessionFactory(@Qualifier("oracleDatasource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:myBatis/mapper/*.xml"));
        sqlSessionFactoryBean.setTypeHandlersPackage("com/happy/database/handler");
        sqlSessionFactoryBean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis/mybatis-config.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "oracleTransactionManager")
    public DataSourceTransactionManager oracleTransactionManager(@Qualifier("oracleDatasource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "oracleSessionTemplate")
    public SqlSessionTemplate oracleSessionTemplate(@Qualifier("oracleSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    BatchConfigurer configurer(@Qualifier("batchDatasource") DataSource dataSource) {
        return new DefaultBatchConfigurer(dataSource);
    }

}
