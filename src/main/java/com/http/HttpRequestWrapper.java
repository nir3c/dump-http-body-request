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

    private File file;
    private InputStreamWrapper servletStream;

    public HttpRequestWrapper(HttpServletRequest servletRequest, File file) throws IOException {
        super(servletRequest);
        this.file = file;
    }


    @Override
    public ServletInputStream getInputStream() throws IOException {
        resetInputStream();
        return servletStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        resetInputStream();
        return new BufferedReader(new InputStreamReader(servletStream));
    }

    private void resetInputStream() throws FileNotFoundException {
        servletStream = new InputStreamWrapper(new FileInputStream(file));
    }

    private class InputStreamWrapper extends ServletInputStream {

        private InputStream stream;

        InputStreamWrapper(InputStream stream) {
            this.stream = stream;
        }

        @Override
        public int read() throws IOException {
            return stream.read();
        }

        @Override
        public boolean isFinished() {
            try {
                return stream.available() == 0;
            } catch (Exception e) {
                System.out.println("[InputStreamWrapper.isFinished] ERROR: ");
                e.printStackTrace();
                try {
                    stream.close();
                }catch (Exception ignored){}
                return true;
            }
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override
        public void close() throws IOException {
            try {
                stream.close();
            } catch (Exception e){
                System.out.println("[InputStreamWrapper.close] ERROR: ");
                e.printStackTrace();
            }
        }
    }

}
