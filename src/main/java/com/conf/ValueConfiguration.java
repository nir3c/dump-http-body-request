package com.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.Getter;
/**
 * Created by Nir.
 * This class manage all the configuration properties value I use on the app
 */
@Component
@Getter
public class ValueConfiguration {

    @Value("${files.json.dump.path:temp}")
    private String jsonDumpPath;
    @Value("${files.json.dump.dateformat:yyyy-MM-dd HH-mm-ss}")
    String dateFormat;

}
