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
package org.fracturedatlas.athena.apa;

import java.util.Collection;
import org.fracturedatlas.athena.apa.exception.ApaException;
import org.fracturedatlas.athena.apa.model.BooleanTicketProp;
import org.fracturedatlas.athena.apa.model.DateTimeTicketProp;
import org.fracturedatlas.athena.apa.model.IntegerTicketProp;
import org.fracturedatlas.athena.apa.model.PropField;
import org.fracturedatlas.athena.apa.model.StrictType;
import org.fracturedatlas.athena.apa.model.StringTicketProp;
import org.fracturedatlas.athena.apa.model.Ticket;
import org.fracturedatlas.athena.apa.model.ValueType;
import org.fracturedatlas.athena.search.AthenaSearch;
import org.fracturedatlas.athena.search.Operator;
import org.fracturedatlas.athena.util.date.DateUtil;
import org.junit.*;
import static org.junit.Assert.*;

public class ApaAdapterComplexSearchTest extends BaseApaAdapterTest {

    AthenaSearch search = getSearcher();

    public ApaAdapterComplexSearchTest() {
        super();
    }

    public AthenaSearch getSearcher() {
        return new AthenaSearch.Builder().type("ticket").build();
    }

    @Test
    public void testFindTicketsOneStringProp() {

        search.addConstraint("SECTION", Operator.EQUALS, "A");
        Collection<Ticket> tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(5, tickets.size());

        search = getSearcher();
        search.addConstraint("SECTION", Operator.EQUALS, "B");
        tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(4, tickets.size());

        search = getSearcher();
        search.addConstraint("SECTION", Operator.EQUALS, "C");
        tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(1, tickets.size());

    }

    @Test
    public void testFindTicketsGreaterThan() {
        search = getSearcher();
        search.addConstraint("PRICE", Operator.GREATER_THAN, "0");
        Collection<Ticket> tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(10, tickets.size());

        search = getSearcher();
        search.addConstraint("PRICE", Operator.GREATER_THAN, "25");
        tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(8, tickets.size());

        search = getSearcher();
        search.addConstraint("PRICE", Operator.GREATER_THAN, "100");
        tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(2, tickets.size());

    }

    @Test
    public void testFindTicketsLessThan() {
        search = getSearcher();
        search.addConstraint("PRICE", Operator.LESS_THAN, "0");
        Collection<Ticket> tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(0, tickets.size());

        search = getSearcher();
        search.addConstraint("PRICE", Operator.LESS_THAN, "50");
        tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(2, tickets.size());

        search = getSearcher();
        search.addConstraint("PRICE", Operator.LESS_THAN, "200");
        tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(9, tickets.size());

    }

    @Test
    public void testFindTicketsRange() {

        search = getSearcher();
        search.addConstraint("PRICE", Operator.GREATER_THAN, "49");
        search.addConstraint("PRICE", Operator.LESS_THAN, "151");
        Collection<Ticket> tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(7, tickets.size());

    }

    @Test
    public void testFindTicketsOnSpecificDate() {

        search = getSearcher();
        search.addConstraint("PERFORMANCE", Operator.EQUALS, "2010-10-02T13:33:50-04:00");
        Collection<Ticket> tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(1, tickets.size());
    }
    @Test
    public void testFindTicketsGreaterThanDate() {

        search = getSearcher();
        search.addConstraint("PERFORMANCE", Operator.GREATER_THAN, "2010-10-05");
        Collection<Ticket> tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(6, tickets.size());

        search = getSearcher();
        search.addConstraint("PERFORMANCE", Operator.GREATER_THAN, "2010-10-06");
        tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(5, tickets.size());

        search = getSearcher();
        search.addConstraint("PERFORMANCE", Operator.GREATER_THAN, "2010-10-07");
        tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(4, tickets.size());

    }

    @Test
    public void testFindTicketsLessThanDate() {

        search = getSearcher();
        search.addConstraint("PERFORMANCE", Operator.LESS_THAN, "2010-10-05");
        Collection<Ticket> tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(4, tickets.size());

        search = getSearcher();
        search.addConstraint("PERFORMANCE", Operator.LESS_THAN, "2010-10-06");
        tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(5, tickets.size());

        search = getSearcher();
        search.addConstraint("PERFORMANCE", Operator.LESS_THAN, "2010-10-07");
        tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(6, tickets.size());

    }

    @Test
    public void testFindTicketsDateRange() {

        search = getSearcher();
        search.addConstraint("PERFORMANCE", Operator.LESS_THAN, "2010-10-07");
        search.addConstraint("PERFORMANCE", Operator.GREATER_THAN, "2010-10-05");
        Collection<Ticket> tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(2, tickets.size());

    }

    @Test
    public void testFindTicketsBadRange() {

        search = getSearcher();
        search.addConstraint("PRICE", Operator.GREATER_THAN, "0");
        Collection<Ticket> tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(10, tickets.size());

        search = getSearcher();
        search.addConstraint("PRICE", Operator.LESS_THAN, "1");
        tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(0, tickets.size());

    }

    @Test
    public void testFindTicketsGreaterThanNegativeValue() {

        search = getSearcher();
        search.addConstraint("PRICE", Operator.GREATER_THAN, "-40");
        Collection<Ticket> tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(10, tickets.size());

    }

    @Test
    public void testFindTicketsGreaterThanString() {


        search = getSearcher();
        search.addConstraint("PRICE", Operator.GREATER_THAN, "OHNOES");
        Collection<Ticket> tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(0, tickets.size());

    }

    @Test
    public void testFindTicketsBoolean() {

        search = getSearcher();
        search.addConstraint("LOCKED", Operator.EQUALS, "false");
        Collection<Ticket> tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(7, tickets.size());

        search = getSearcher();
        search.addConstraint("LOCKED", Operator.EQUALS, "true");
        tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(3, tickets.size());

        search = getSearcher();
        search.addConstraint("LOCKED", Operator.EQUALS, "FALSE");
        tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(7, tickets.size());

        search = getSearcher();
        search.addConstraint("LOCKED", Operator.EQUALS, "TRUE");
        tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(3, tickets.size());

    }

    @Test
    public void testFindTicketsWithLimit() {
        search = getSearcher();
        search.addConstraint("LOCKED", Operator.EQUALS, "false");
        search.setSearchModifier(AthenaSearch.LIMIT, "6");
        Collection<Ticket> tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(6, tickets.size());
    }

    @Test
    public void testFindTicketsWithLimitEqualToResult() {
        search = getSearcher();
        search.addConstraint("LOCKED", Operator.EQUALS, "false");
        search.setSearchModifier(AthenaSearch.LIMIT, "7");
        Collection<Ticket> tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(7, tickets.size());
    }

    @Test
    public void testFindTicketsWithLimitHigherThanResult() {
        search = getSearcher();
        search.addConstraint("LOCKED", Operator.EQUALS, "false");
        search.setSearchModifier(AthenaSearch.LIMIT, "8");
        Collection<Ticket> tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(7, tickets.size());
    }

    @Test
    public void testFindTicketsWithLimitofZero() {
        search = getSearcher();
        search.addConstraint("LOCKED", Operator.EQUALS, "false");
        search.setSearchModifier(AthenaSearch.LIMIT, "0");
        Collection<Ticket> tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(0, tickets.size());
    }

    @Test
    public void testFindTicketsWithLimitOfOne() {
        search = getSearcher();
        search.addConstraint("LOCKED", Operator.EQUALS, "false");
        search.setSearchModifier(AthenaSearch.LIMIT, "1");
        Collection<Ticket> tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(1, tickets.size());

    }

    @Test
    public void testFindTicketsWithInvalidLimit() {
        search = getSearcher();
        search.addConstraint("LOCKED", Operator.EQUALS, "false");
        search.setSearchModifier(AthenaSearch.LIMIT, "dog");
        Collection<Ticket> tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(7, tickets.size());
    }

    @Test
    public void testFindTicketsMultipleQueries() {
        search = getSearcher();
        search.addConstraint("PRICE", Operator.GREATER_THAN, "26");
        search.addConstraint("SOLD", Operator.EQUALS, "FALSE");
        Collection<Ticket> tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(3, tickets.size());
    }

    @Test
    public void testFindTicketsMultipleQueries2() {
        search = getSearcher();
        search.addConstraint("LOCKED", Operator.EQUALS, "FALSE");
        search.addConstraint("SOLD", Operator.EQUALS, "FALSE");
        Collection<Ticket> tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(4, tickets.size());
    }

    @Test
    public void testFindTicketsString() {

        search = getSearcher();
        search.addConstraint("TIER", Operator.EQUALS, "SILVER");
        Collection<Ticket> tickets = apa.findTickets(search);
        assertNotNull(tickets);
        assertEquals(2, tickets.size());

    }

//    @Test
    public void testFindTicketsWithoutSpecifyingType() {

        search = new AthenaSearch();
        search.addConstraint("TIER", Operator.EQUALS, "SILVER");
        try{
            Collection<Ticket> tickets = apa.findTickets(search);
            fail("Should have thrown ApaException");
        } catch (ApaException ae) {
            //pass
        }

    }

    @Before
    public void addTickets() throws Exception {
        Ticket t1 = new Ticket();
        Ticket t2 = new Ticket();
        Ticket t3 = new Ticket();
        Ticket t4 = new Ticket();
        Ticket t5 = new Ticket();
        Ticket t6 = new Ticket();
        Ticket t7 = new Ticket();
        Ticket t8 = new Ticket();
        Ticket t9 = new Ticket();
        Ticket t10 = new Ticket();

        t1.setType("ticket");
        t2.setType("ticket");
        t3.setType("ticket");
        t4.setType("ticket");
        t5.setType("ticket");
        t6.setType("ticket");
        t7.setType("ticket");
        t8.setType("ticket");
        t9.setType("ticket");
        t10.setType("ticket");

        PropField seatNumberProp = apa.savePropField(new PropField(ValueType.INTEGER, "SEAT_NUMBER", StrictType.NOT_STRICT));
        PropField sectionProp = apa.savePropField(new PropField(ValueType.STRING, "SECTION", StrictType.NOT_STRICT));
        PropField performanceProp = apa.savePropField(new PropField(ValueType.DATETIME, "PERFORMANCE", StrictType.NOT_STRICT));
        PropField tierProp = apa.savePropField(new PropField(ValueType.STRING, "TIER", StrictType.NOT_STRICT));
        PropField lockedProp = apa.savePropField(new PropField(ValueType.BOOLEAN, "LOCKED", StrictType.NOT_STRICT));
        PropField soldProp = apa.savePropField(new PropField(ValueType.BOOLEAN, "SOLD", StrictType.NOT_STRICT));
        PropField lockedByProp = apa.savePropField(new PropField(ValueType.STRING, "LOCKED_BY_API_KEY", StrictType.NOT_STRICT));
        PropField lockExpiresProp = apa.savePropField(new PropField(ValueType.DATETIME, "LOCK_EXPIRES", StrictType.NOT_STRICT));
        PropField priceProp = apa.savePropField(new PropField(ValueType.INTEGER, "PRICE", StrictType.NOT_STRICT));
        PropField halfPriceProp = apa.savePropField(new PropField(ValueType.BOOLEAN, "HALF_PRICE_AVAILABLE", StrictType.NOT_STRICT));

        propFieldsToDelete.add(seatNumberProp);
        propFieldsToDelete.add(sectionProp);
        propFieldsToDelete.add(performanceProp);
        propFieldsToDelete.add(tierProp);
        propFieldsToDelete.add(lockedProp);
        propFieldsToDelete.add(soldProp);
        propFieldsToDelete.add(lockedByProp);
        propFieldsToDelete.add(lockExpiresProp);
        propFieldsToDelete.add(priceProp);
        propFieldsToDelete.add(halfPriceProp);

        /*
         * SECTION, A=5, B=4, C=1
         * PRICE, 50 = 6, 25=2, 150=1, 250=1
         * LOCKED=3
         * SOLD=5
         * LOCKED & SOLD = 4
         * GOLD=7, SILVER =2, BRONZE=1
         *
         *
         */

        t1.addTicketProp(new IntegerTicketProp(seatNumberProp, 3));
        t1.addTicketProp(new StringTicketProp(sectionProp, "A"));
        t1.addTicketProp(new DateTimeTicketProp(performanceProp, DateUtil.parseDate("2010-10-01T13:33:50-04:00")));
        t1.addTicketProp(new StringTicketProp(tierProp, "SILVER"));
        t1.addTicketProp(new BooleanTicketProp(lockedProp, Boolean.FALSE));
        t1.addTicketProp(new BooleanTicketProp(soldProp, Boolean.TRUE));
        t1.addTicketProp(new StringTicketProp(lockedByProp, "SAMPLE_API_KEY"));
        t1.addTicketProp(new DateTimeTicketProp(lockExpiresProp, DateUtil.parseDate("2010-10-01T13:33:50-04:00")));
        t1.addTicketProp(new IntegerTicketProp(priceProp, 50));
        t1.addTicketProp(new BooleanTicketProp(halfPriceProp, Boolean.TRUE));

        t2.addTicketProp(new IntegerTicketProp(seatNumberProp, 3));
        t2.addTicketProp(new StringTicketProp(sectionProp, "A"));
        t2.addTicketProp(new DateTimeTicketProp(performanceProp, DateUtil.parseDate("2010-10-02T13:33:50-04:00")));
        t2.addTicketProp(new StringTicketProp(tierProp, "SILVER"));
        t2.addTicketProp(new BooleanTicketProp(lockedProp, Boolean.TRUE));
        t2.addTicketProp(new BooleanTicketProp(soldProp, Boolean.TRUE));
        t2.addTicketProp(new StringTicketProp(lockedByProp, "SAMPLE_API_KEY"));
        t2.addTicketProp(new DateTimeTicketProp(lockExpiresProp, DateUtil.parseDate("2010-10-02T13:33:50-04:00")));
        t2.addTicketProp(new IntegerTicketProp(priceProp, 50));
        t2.addTicketProp(new BooleanTicketProp(halfPriceProp, Boolean.TRUE));

        t3.addTicketProp(new IntegerTicketProp(seatNumberProp, 3));
        t3.addTicketProp(new StringTicketProp(sectionProp, "A"));
        t3.addTicketProp(new DateTimeTicketProp(performanceProp, DateUtil.parseDate("2010-10-03T13:33:50-04:00")));
        t3.addTicketProp(new StringTicketProp(tierProp, "BRONZE"));
        t3.addTicketProp(new BooleanTicketProp(lockedProp, Boolean.TRUE));
        t3.addTicketProp(new BooleanTicketProp(soldProp, Boolean.TRUE));
        t3.addTicketProp(new StringTicketProp(lockedByProp, "SAMPLE_API_KEY"));
        t3.addTicketProp(new DateTimeTicketProp(lockExpiresProp, DateUtil.parseDate("2010-10-03T13:33:50-04:00")));
        t3.addTicketProp(new IntegerTicketProp(priceProp, 50));
        t3.addTicketProp(new BooleanTicketProp(halfPriceProp, Boolean.TRUE));

        t4.addTicketProp(new IntegerTicketProp(seatNumberProp, 3));
        t4.addTicketProp(new StringTicketProp(sectionProp, "A"));
        t4.addTicketProp(new DateTimeTicketProp(performanceProp, DateUtil.parseDate("2010-10-04T13:33:50-04:00")));
        t4.addTicketProp(new StringTicketProp(tierProp, "GOLD"));
        t4.addTicketProp(new BooleanTicketProp(lockedProp, Boolean.FALSE));
        t4.addTicketProp(new BooleanTicketProp(soldProp, Boolean.TRUE));
        t4.addTicketProp(new StringTicketProp(lockedByProp, "SAMPLE_API_KEY"));
        t4.addTicketProp(new DateTimeTicketProp(lockExpiresProp, DateUtil.parseDate("2010-10-04T13:33:50-04:00")));
        t4.addTicketProp(new IntegerTicketProp(priceProp, 50));
        t4.addTicketProp(new BooleanTicketProp(halfPriceProp, Boolean.TRUE));

        t5.addTicketProp(new IntegerTicketProp(seatNumberProp, 3));
        t5.addTicketProp(new StringTicketProp(sectionProp, "A"));
        t5.addTicketProp(new DateTimeTicketProp(performanceProp, DateUtil.parseDate("2010-10-05T13:33:50-04:00")));
        t5.addTicketProp(new StringTicketProp(tierProp, "GOLD"));
        t5.addTicketProp(new BooleanTicketProp(lockedProp, Boolean.FALSE));
        t5.addTicketProp(new BooleanTicketProp(soldProp, Boolean.TRUE));
        t5.addTicketProp(new StringTicketProp(lockedByProp, "SAMPLE_API_KEY"));
        t5.addTicketProp(new DateTimeTicketProp(lockExpiresProp, DateUtil.parseDate("2010-10-05T13:33:50-04:00")));
        t5.addTicketProp(new IntegerTicketProp(priceProp, 50));
        t5.addTicketProp(new BooleanTicketProp(halfPriceProp, Boolean.TRUE));

        t6.addTicketProp(new IntegerTicketProp(seatNumberProp, 3));
        t6.addTicketProp(new StringTicketProp(sectionProp, "B"));
        t6.addTicketProp(new DateTimeTicketProp(performanceProp, DateUtil.parseDate("2010-10-06T13:33:50-04:00")));
        t6.addTicketProp(new StringTicketProp(tierProp, "GOLD"));
        t6.addTicketProp(new BooleanTicketProp(lockedProp, Boolean.TRUE));
        t6.addTicketProp(new BooleanTicketProp(soldProp, Boolean.FALSE));
        t6.addTicketProp(new StringTicketProp(lockedByProp, "SAMPLE_API_KEY"));
        t6.addTicketProp(new DateTimeTicketProp(lockExpiresProp, DateUtil.parseDate("2010-10-06T13:33:50-04:00")));
        t6.addTicketProp(new IntegerTicketProp(priceProp, 50));
        t6.addTicketProp(new BooleanTicketProp(halfPriceProp, Boolean.TRUE));

        t7.addTicketProp(new IntegerTicketProp(seatNumberProp, 3));
        t7.addTicketProp(new StringTicketProp(sectionProp, "B"));
        t7.addTicketProp(new DateTimeTicketProp(performanceProp, DateUtil.parseDate("2010-10-07T13:33:50-04:00")));
        t7.addTicketProp(new StringTicketProp(tierProp, "GOLD"));
        t7.addTicketProp(new BooleanTicketProp(lockedProp, Boolean.FALSE));
        t7.addTicketProp(new BooleanTicketProp(soldProp, Boolean.FALSE));
        t7.addTicketProp(new StringTicketProp(lockedByProp, "SAMPLE_API_KEY"));
        t7.addTicketProp(new DateTimeTicketProp(lockExpiresProp, DateUtil.parseDate("2010-10-07T13:33:50-04:00")));
        t7.addTicketProp(new IntegerTicketProp(priceProp, 250));
        t7.addTicketProp(new BooleanTicketProp(halfPriceProp, Boolean.TRUE));

        t8.addTicketProp(new IntegerTicketProp(seatNumberProp, 3));
        t8.addTicketProp(new StringTicketProp(sectionProp, "B"));
        t8.addTicketProp(new DateTimeTicketProp(performanceProp, DateUtil.parseDate("2010-10-08T13:33:50-04:00")));
        t8.addTicketProp(new StringTicketProp(tierProp, "GOLD"));
        t8.addTicketProp(new BooleanTicketProp(lockedProp, Boolean.FALSE));
        t8.addTicketProp(new BooleanTicketProp(soldProp, Boolean.FALSE));
        t8.addTicketProp(new StringTicketProp(lockedByProp, "SAMPLE_API_KEY"));
        t8.addTicketProp(new DateTimeTicketProp(lockExpiresProp, DateUtil.parseDate("2010-10-08T13:33:50-04:00")));
        t8.addTicketProp(new IntegerTicketProp(priceProp, 150));
        t8.addTicketProp(new BooleanTicketProp(halfPriceProp, Boolean.TRUE));

        t9.addTicketProp(new IntegerTicketProp(seatNumberProp, 3));
        t9.addTicketProp(new StringTicketProp(sectionProp, "B"));
        t9.addTicketProp(new DateTimeTicketProp(performanceProp, DateUtil.parseDate("2010-10-09T13:33:50-04:00")));
        t9.addTicketProp(new StringTicketProp(tierProp, "GOLD"));
        t9.addTicketProp(new BooleanTicketProp(lockedProp, Boolean.FALSE));
        t9.addTicketProp(new BooleanTicketProp(soldProp, Boolean.FALSE));
        t9.addTicketProp(new StringTicketProp(lockedByProp, "SAMPLE_API_KEY"));
        t9.addTicketProp(new DateTimeTicketProp(lockExpiresProp, DateUtil.parseDate("2010-10-09T13:33:50-04:00")));
        t9.addTicketProp(new IntegerTicketProp(priceProp, 25));
        t9.addTicketProp(new BooleanTicketProp(halfPriceProp, Boolean.TRUE));

        t10.addTicketProp(new IntegerTicketProp(seatNumberProp, 3));
        t10.addTicketProp(new StringTicketProp(sectionProp, "C"));
        t10.addTicketProp(new DateTimeTicketProp(performanceProp, DateUtil.parseDate("2010-10-10T13:33:50-04:00")));
        t10.addTicketProp(new StringTicketProp(tierProp, "GOLD"));
        t10.addTicketProp(new BooleanTicketProp(lockedProp, Boolean.FALSE));
        t10.addTicketProp(new BooleanTicketProp(soldProp, Boolean.FALSE));
        t10.addTicketProp(new StringTicketProp(lockedByProp, "SAMPLE_API_KEY"));
        t10.addTicketProp(new DateTimeTicketProp(lockExpiresProp, DateUtil.parseDate("2010-10-10T13:33:50-04:00")));
        t10.addTicketProp(new IntegerTicketProp(priceProp, 25));
        t10.addTicketProp(new BooleanTicketProp(halfPriceProp, Boolean.TRUE));

        t1 = apa.saveTicket(t1);
        t2 = apa.saveTicket(t2);
        t3 = apa.saveTicket(t3);
        t4 = apa.saveTicket(t4);
        t5 = apa.saveTicket(t5);
        t6 = apa.saveTicket(t6);
        t7 = apa.saveTicket(t7);
        t8 = apa.saveTicket(t8);
        t9 = apa.saveTicket(t9);
        t10 = apa.saveTicket(t10);

        ticketsToDelete.add(t1);
        ticketsToDelete.add(t2);
        ticketsToDelete.add(t3);
        ticketsToDelete.add(t4);
        ticketsToDelete.add(t5);
        ticketsToDelete.add(t6);
        ticketsToDelete.add(t7);
        ticketsToDelete.add(t8);
        ticketsToDelete.add(t9);
        ticketsToDelete.add(t10);
    }

    @After
    public void teardownTickets() {
        super.teardownTickets();
    }
}
