package com.thankyou_for;

import com.google.common.base.CaseFormat;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DatabaseCleanup implements InitializingBean {

    private final EntityManager entityManager;

    private List<String> tableNames;

    public DatabaseCleanup(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void afterPropertiesSet() {
        tableNames = entityManager.getMetamodel().getEntities().stream()
                .filter(entityType -> entityType.getJavaType().isAnnotationPresent(Entity.class))
                .map(entityType -> {
                    if (entityType.getJavaType().isAnnotationPresent(Table.class)) {
                        return entityType.getJavaType().getAnnotation(Table.class).name();
                    }
                    return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entityType.getName());
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void cleanUp() {
        entityManager.flush();

        List<String> cleanUpSql = new ArrayList<>();
        cleanUpSql.add("SET REFERENTIAL_INTEGRITY FALSE");

        for (String tableName : tableNames) {
            cleanUpSql.add("TRUNCATE TABLE " + tableName);
            cleanUpSql.add("ALTER TABLE " + tableName + " ALTER COLUMN ID RESTART WITH 1");
        }

        cleanUpSql.add("SET REFERENTIAL_INTEGRITY TRUE");

        entityManager.createNativeQuery(String.join("; ", cleanUpSql)).executeUpdate();
    }
}
