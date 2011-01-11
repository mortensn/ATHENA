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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.fracturedatlas.athena.client.AthenaComponent;
import org.fracturedatlas.athena.client.audit.PublicAuditMessage;
import org.fracturedatlas.athena.web.JsonAthenaComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author fintan
 */
//@Component
public class AuditFilter implements ContainerRequestFilter {

    private FilterConfig filterConfig = null;
 //   @Autowired
    private JsonAthenaComponent athenaAudit = new JsonAthenaComponent("localhost", "8080", "audit");

       public JsonAthenaComponent getAthenaAudit() {
        return athenaAudit;
    }

    public void setAthenaAudit(JsonAthenaComponent athenaAudit) {
        this.athenaAudit = athenaAudit;
    }

    public void init(FilterConfig filterConfig)
            throws ServletException {
        this.filterConfig = filterConfig;
//        ApplicationContext context = new ClassPathXmlApplicationContext("testApplicationContext.xml");
//        athenaAudit = (JsonAthenaComponent)context.getBean("athenaAudit");

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
            System.out.println(request.getAttributeNames());
            System.out.println(request.getParameterNames());
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
            athenaAudit.recordRequest(pam);

        } catch (Exception ex) {
            Logger.getLogger(AuditFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        chain.doFilter(request, response);
    }

    @Override
    public ContainerRequest filter(ContainerRequest request) {
               try {

            String user = request.getUserPrincipal() + ":" ;
            //Action
            System.out.println(request.getRequestHeaders());
            System.out.println(request);
            String action = "Restful request";
            //Resource
            String resource = request.getMethod() + ":" + request.getPath();
            //Message
            InputStream is = request.getEntityInputStream();
            String message = is.toString();
            //DateTime
            Long dateTime = System.currentTimeMillis();

            PublicAuditMessage pam = new PublicAuditMessage(user, action, resource, message.toString());
            athenaAudit.recordRequest(pam);

        } catch (Exception ex) {
            Logger.getLogger(AuditFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
               return request;
    }
}
