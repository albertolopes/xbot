package com.avocado.xbot.integration.x;

import com.avocado.xbot.integration.x.dto.Status;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "xclient", url = "${api.x.url}", configuration = XClientConfig.class)
public interface XClient {

    @PostMapping("/tweets")
    ResponseEntity<Object> status(
            @RequestBody Status status
    );
}
