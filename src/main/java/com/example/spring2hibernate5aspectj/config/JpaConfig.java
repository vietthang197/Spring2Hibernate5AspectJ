package com.example.spring2hibernate5aspectj.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.aspectj.AnnotationTransactionAspect;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ, proxyTargetClass = true)
@RequiredArgsConstructor
public class JpaConfig {

    @Bean
    @Order(998)
    public AnnotationTransactionAspect annotationTransactionAspect(PlatformTransactionManager platformTransactionManager) {
        AnnotationTransactionAspect annotationTransactionAspect = AnnotationTransactionAspect.aspectOf();
        annotationTransactionAspect.setTransactionManager(platformTransactionManager);
        return annotationTransactionAspect;
    }

    @Bean("transactionManager")
    @Order(999)
    public PlatformTransactionManager transactionManager(DataSource dataSource, EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setDataSource(dataSource);
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        AnnotationTransactionAspect.aspectOf().setTransactionManager(jpaTransactionManager);
        return jpaTransactionManager;
    }
}
