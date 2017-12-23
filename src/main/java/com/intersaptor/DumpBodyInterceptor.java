package com.intersaptor;

import com.conf.ValueConfiguration;
import com.google.common.io.Files;

import com.util.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.rmi.UnexpectedException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Nir.
 */
@Component
public class DumpBodyInterceptor implements HandlerInterceptor {

    private String jsonDumpPath;
    private SimpleDateFormat dateFormat;

    @Autowired
    public DumpBodyInterceptor(ValueConfiguration conf) throws UnexpectedException {
        this.jsonDumpPath = conf.getJsonDumpPath();
        if(!FileUtils.ensureDirectoriesExists(jsonDumpPath))
            throw new UnexpectedException("Didn't succeeded to ensure json dump folder exists");
        this.dateFormat = new SimpleDateFormat(conf.getDateFormat());
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String folderName = jsonDumpPath + "/"+ httpServletRequest.getRequestURI();
        if(!FileUtils.ensureDirectoriesExists(folderName))
            throw new UnexpectedException("Didn't success to ensure folder exists");
        File f = new File(String.format("%s/%s.dump", folderName, dateFormat.format(Calendar.getInstance().getTime())));
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(f));
        IOUtils.copy(httpServletRequest.getInputStream(), outputStreamWriter, "UTF-8");
        IOUtils.closeQuietly(outputStreamWriter);
        System.out.println(String.format("request dumped: %s", f.getPath()));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {}

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {}
}
