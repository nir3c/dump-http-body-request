package com.filter;


import com.conf.ValueConfiguration;
import com.http.HttpRequestWrapper;
import com.util.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.rmi.UnexpectedException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Nir.
 */
@Component
public class RequestFilter implements Filter {

    private String jsonDumpPath;
    private SimpleDateFormat dateFormat;

    @Autowired
    public RequestFilter(ValueConfiguration conf) throws UnexpectedException {
        this.jsonDumpPath = conf.getJsonDumpPath();
        if(!FileUtils.ensureDirectoriesExists(jsonDumpPath))
            throw new UnexpectedException("Didn't succeeded to ensure json dump folder exists");
        this.dateFormat = new SimpleDateFormat(conf.getDateFormat());
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String folderName = jsonDumpPath + "/" + httpServletRequest.getRequestURI();
        if(!FileUtils.ensureDirectoriesExists(folderName))
            throw new UnexpectedException("Didn't success to ensure folder exists");
        File f = new File(String.format("%s/%s.dump", folderName, dateFormat.format(Calendar.getInstance().getTime())));
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(f));
        IOUtils.copy(servletRequest.getInputStream(), outputStreamWriter, "UTF-8");
        IOUtils.closeQuietly(outputStreamWriter);
        System.out.println(String.format("request dumped: %s", f.getPath()));
        filterChain.doFilter(new HttpRequestWrapper(httpServletRequest, f), servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}
