package com.controller;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Nir.
 */
@RestController
public class AfterDumpBodyRestController {

    @RequestMapping(value = {"/dumpbody", "/dumpbody/"}, method = RequestMethod.POST)
    public String dump(@RequestBody StringValClass body) {
        return "after dump body - val field: " + body.val;
    }

    @NoArgsConstructor
    @Setter
    public static class StringValClass{
        String val;
    }
}
