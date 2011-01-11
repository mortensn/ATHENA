/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fracturedatlas.athena.filter;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import com.google.gson.Gson;
import com.sun.jersey.api.client.*;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.io.InputStreamReader;
import java.io.Reader;
import org.fracturedatlas.athena.client.audit.PublicAuditMessage;
import org.fracturedatlas.athena.web.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author fintan
 */
//@Component
public class AuditFilter implements ContainerRequestFilter {

    private FilterConfig filterConfig = null;
 //   @Autowired
    private String hostname ="localhost";
    private String port = "8080";
    private String componentName = "audit";

    WebResource component;
    Client c;
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    Gson gson = JsonUtil.getGson();
    String uri = "http://" + hostname + ":" + port + "/" + componentName + "/";

    public void init(FilterConfig filterConfig)
            throws ServletException {
        this.filterConfig = filterConfig;
        
        

    }

    public void destroy() {
        this.filterConfig = null;
    }

    public void doFilter(ServletRequest request,
            ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
       try {
            String user = request.getRemoteAddr() + ":" + request.getRemotePort();
            //Action
            logger.debug(request.getAttributeNames().toString());
            logger.debug(request.getParameterNames().toString());
            String action = "Restful request";
            //Resource
            String resource = request.getLocalAddr() + ":" + request.getLocalPort();
            //Message
            BufferedReader bf = request.getReader();
            StringBuffer message = new StringBuffer();
            while (bf.ready()) {
                message.append(bf.readLine());
            }
            //DateTime
            Long dateTime = System.currentTimeMillis();

            PublicAuditMessage pam = new PublicAuditMessage(user, action, resource, message.toString());
            String jsonResponse;
            String path = "audit/";
            String recordJson = gson.toJson(pam);
            component.path(path).type("application/json")
                                .post(String.class, recordJson);

 
        } catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
        }
        chain.doFilter(request, response);
    }

    @Override
    public ContainerRequest filter(ContainerRequest request) {
               try {

            String user = request.getUserPrincipal() + ":" ;
            //Action
            logger.debug(request.getRequestHeaders().toString());
            logger.debug(request.toString());
            logger.debug(request.getMethod());
            logger.debug(request.getPath());
            logger.debug(request.getEntityInputStream().toString());
            logger.debug(request.getProperties().toString());
            logger.debug(request.getQueryParameters().toString());
            logger.debug(request.getFormParameters().toString());
            logger.debug(request.getRequestUri().toString());
            logger.debug(request.CONTENT_ENCODING);
            String action = request.getMethod() + request.getAbsolutePath();
            //Resource
            String resource = request.getBaseUri()  + ":" + request.getPath();
            //Message
            InputStream is = request.getEntityInputStream();
            final char[] buffer = new char[0x10000];
            StringBuilder out = new StringBuilder();
            Reader in = new InputStreamReader(is, "UTF-8");
            int read;
            do {
                read = in.read(buffer, 0, buffer.length);
                if (read>0) {
                    out.append(buffer, 0, read);
                }
            } while (read>=0);
            String message = out.toString();
            //DateTime
            Long dateTime = System.currentTimeMillis();

            if (component==null) {
                ClientConfig cc = new DefaultClientConfig();
                c = Client.create(cc);
                component = c.resource(uri);
            }
            PublicAuditMessage pam = new PublicAuditMessage(user, action, resource, message.toString());
            String jsonResponse;
            String path = "audit/";
            String recordJson = gson.toJson(pam);
            component.path(path).type("application/json")
                                .post(String.class, recordJson);


  
        } catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
        }
               return request;
    }
}
