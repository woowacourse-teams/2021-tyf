package com.example.tyfserver.common.config.replication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class ReplicationRoutingDataSource extends AbstractRoutingDataSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReplicationRoutingDataSource.class);

    private SlaveNames slaveNames;

    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);

        List<String> replicas = targetDataSources.keySet().stream()
                .map(Object::toString)
                .filter(string -> string.contains("slave"))
                .collect(toList());

        this.slaveNames = new SlaveNames(replicas);
    }

    @Override
    protected String determineCurrentLookupKey() {
        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly(); //조회 쿼리인 경우
        if (isReadOnly) {
            String slaveName = slaveNames.getNextName(); //다음 slave 선택

            LOGGER.info("Slave DB name: {}", slaveName);

            return slaveName;
        }

        return "master";
    }


    public class SlaveNames {

        private final String[] value;
        private int counter = 0;

        public SlaveNames(List<String> slaveDataSourceProperties) {
            this(slaveDataSourceProperties.toArray(String[]::new));
        }

        public SlaveNames(String[] value) {
            this.value = value;
        }

        public String getNextName() {
            int index = counter;
            counter = (counter + 1) % value.length;
            return value[index];
        }
    }
}
