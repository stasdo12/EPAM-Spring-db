package com.epam.springcore.task.client;

import com.epam.springcore.task.dto.TrainingRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

//@FeignClient(name = "EPAMT1MICROSERVICE", url = "${feign.client.config.default.url}", fallback = MicroserviceFallback.class)
public interface MicroserviceClient {
    @PostMapping("/Workloads")
    void actionTraining(@RequestBody TrainingRequest trainingRequest,
                        @RequestHeader("Transaction-ID") String transactionId,
                        @RequestHeader("Authorization") String Authorization);
}
