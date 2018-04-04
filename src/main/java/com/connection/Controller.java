package com.connection;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {


    @RequestMapping("/connected")
    public String connected(@RequestParam(value="origin") String origin,@RequestParam(value="destination") String destination ) {
        return Connector.connected(origin, destination);
    }
}
