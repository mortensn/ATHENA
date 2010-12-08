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

package org.fracturedatlas.athena.web.manager;

import com.google.gson.Gson;
import org.fracturedatlas.athena.apa.exception.ApaException;
import org.fracturedatlas.athena.client.PTicket;
import org.fracturedatlas.athena.apa.exception.InvalidValueException;
import org.fracturedatlas.athena.apa.model.IntegerTicketProp;
import org.fracturedatlas.athena.apa.model.PropField;
import org.fracturedatlas.athena.apa.model.PropValue;
import org.fracturedatlas.athena.apa.model.StrictType;
import org.fracturedatlas.athena.apa.model.StringTicketProp;
import org.fracturedatlas.athena.apa.model.Ticket;
import org.fracturedatlas.athena.apa.model.ValueType;
import org.fracturedatlas.athena.web.util.BaseManagerCallbackTest;
import org.fracturedatlas.athena.web.util.BaseManagerTest;
import org.fracturedatlas.athena.web.util.JsonUtil;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

public class BeforeSaveTest extends BaseManagerCallbackTest {
    RecordManager manager;
    Gson gson = JsonUtil.getGson();

    public BeforeSaveTest() throws Exception {
        super();
        manager = (RecordManager)context.getBean("recordManager");
    }

    @After
    public void tearDown() {
        teardownTickets();
    }

    @Test
    public void testMarkBooleanFieldStrict() throws Exception {
        Ticket t = new Ticket();
        t.setType("ticket");
        ticketsToDelete.add(t);
        PTicket pTicket = t.toClientTicket();
        manager.saveTicketFromClientRequest("ticket", pTicket);
    }
}
