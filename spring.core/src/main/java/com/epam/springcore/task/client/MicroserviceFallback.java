package com.epam.springcore.task.client;

import com.epam.springcore.task.dto.TrainingRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MicroserviceFallback implements MicroserviceClient{

    static Logger log = LoggerFactory.getLogger(MicroserviceFallback.class);
    @Override
    public void actionTraining(TrainingRequest trainingRequest, String transactionId, String Authorization) {
        log.debug("Error calling training workload service: TransactionID: {}", transactionId);

    }
}
