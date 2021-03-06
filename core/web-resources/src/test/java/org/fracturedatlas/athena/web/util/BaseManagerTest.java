/*

ATHENA Project: Management Tools for the Cultural Sector
Copyright (C) 2010, Fractured Atlas

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/

*/

package org.fracturedatlas.athena.web.util;

import java.util.ArrayList;
import java.util.List;
import org.fracturedatlas.athena.apa.ApaAdapter;
import org.fracturedatlas.athena.apa.model.PropField;
import org.fracturedatlas.athena.apa.model.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoaderListener;

public abstract class BaseManagerTest {
    protected ApaAdapter apa;
    protected ApplicationContext context;

    protected List<Ticket> ticketsToDelete = new ArrayList<Ticket>();
    protected List<PropField> propFieldsToDelete = new ArrayList<PropField>();
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public BaseManagerTest() {
        context = new ClassPathXmlApplicationContext("testApplicationContext.xml");
        apa = (ApaAdapter)context.getBean("apa");
    }

    public void teardownTickets() {
        for (Ticket t : ticketsToDelete) {
            try {
                apa.deleteTicket(t);
            } catch (Exception ignored) {
                    logger.error(ignored.getMessage(), ignored);
            }
        }

        for (PropField pf : propFieldsToDelete) {
            try {
                apa.deletePropField(pf);
            } catch (Exception ignored) {
                     logger.error(ignored.getMessage(), ignored);
           }
        }
    }
}
