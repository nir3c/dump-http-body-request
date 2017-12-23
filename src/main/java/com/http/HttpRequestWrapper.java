package com.http;

import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;


/**
 * Created by Nir.
 */
public class HttpRequestWrapper extends HttpServletRequestWrapper {

    private byte[] rawData;
    private InputStreamWrapper servletStream;

    public HttpRequestWrapper(HttpServletRequest request) {
        super(request);
    }


    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (rawData == null)
            rawData = IOUtils.toByteArray(this.getRequest().getReader());
        resetInputStream();
        return servletStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        if (rawData == null)
            rawData = IOUtils.toByteArray(this.getRequest().getReader());
        resetInputStream();
        return new BufferedReader(new InputStreamReader(servletStream));
    }

    private void resetInputStream() {
        servletStream = new InputStreamWrapper(new ByteArrayInputStream(rawData));
    }

    private class InputStreamWrapper extends ServletInputStream {

        private ByteArrayInputStream stream;
        InputStreamWrapper(ByteArrayInputStream stream) {
            this.stream = stream;
        }

        @Override
        public int read() throws IOException {
            return stream.read();
        }

        @Override
        public boolean isFinished() {
            return stream.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            throw new UnsupportedOperationException("Not implemented");
        }
    }

}
