/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fracturedatlas.athena.payments.filter;

import com.sun.jersey.spi.container.ContainerRequest;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import org.fracturedatlas.athena.client.audit.PublicAuditMessage;
import com.sun.jersey.api.client.WebResource;
import org.fracturedatlas.athena.filter.AuditFilter;
import org.fracturedatlas.athena.payments.model.AuthorizationRequest;
import org.fracturedatlas.athena.payments.model.CreditCard;
import org.fracturedatlas.athena.payments.model.Customer;

/**
 *
 *
 * @author fintan
 */
public class PaymentsFilter extends AuditFilter {

    @Override
    public ContainerRequest filter(ContainerRequest request) {
        PublicAuditMessage pam = null;

        try {
            String user = request.getUserPrincipal() + ":";
            String action = request.getMethod();
            String resource = request.getRequestUri().toString();
            String message = request.getEntity(String.class);
            pam = new PublicAuditMessage(user, action, resource, message);
            String path = "audit/";
            String recordJson = gson.toJson(pam);
            component.path(path).type("application/json").post(String.class, recordJson);

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return request;
    }
}
