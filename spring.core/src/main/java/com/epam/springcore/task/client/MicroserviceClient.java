package com.epam.springcore.task.client;

import com.epam.springcore.task.dto.TrainingRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "microservice", url = "http://localhost:8081/", fallback = MicroserviceFallback.class)
@Component
public interface MicroserviceClient {
    @PostMapping("/training-work/accept")
    void actionTraining(@RequestBody TrainingRequest trainingRequest,
                        @RequestHeader("Transaction-ID") String transactionId,
                        @RequestHeader("Authorization") String Authorization);
}
