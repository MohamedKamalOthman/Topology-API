package topologyapi;

import junit.framework.TestCase;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.skyscreamer.jsonassert.JSONAssert;

public class NmosTest extends TestCase {
    Nmos nmos = new Nmos();
    JSONParser parser = new JSONParser();
    double delta = 0.000001d;
    String object = "{\n" +
            "      \"type\": \"nmos\",\n" +
            "      \"id\": \"m1\",\n" +
            "      \"m(l)\": {\n" +
            "        \"default\": 1.5,\n" +
            "        \"min\": 1,\n" +
            "        \"max\": 2\n" +
            "      },\n" +
            "      \"netlist\": {\n" +
            "        \"drain\": \"n1\",\n" +
            "        \"gate\": \"vin\",\n" +
            "        \"source\": \"vss\"\n" +
            "      }\n" +
            "    }";

    public void testWriteComponent() throws ParseException {
        JSONObject nmosObject = (JSONObject) parser.parse(object);
        nmos.readComponent(nmosObject);
        assertEquals("vin", nmos.getGate());
        assertEquals("n1", nmos.getDrain());
        assertEquals("vss", nmos.getSource());
        assertEquals("m1", nmos.getId());
        assertEquals("nmos", nmos.getType());
        assertEquals(1.5, nmos.getDefaultVal().doubleValue(), delta);
        assertEquals(2, nmos.getMaxVal().doubleValue(), delta);
        assertEquals(1, nmos.getMinVal().doubleValue(), delta);
    }

    public void testReadComponent() throws ParseException, JSONException {
        JSONObject nmosObject = (JSONObject) parser.parse(object);
        nmos.readComponent(nmosObject);
        JSONAssert.assertEquals(object, nmos.writeComponent().toJSONString(), true);
    }

    public void testNetlistExist() throws ParseException {
        JSONObject nmosObject = (JSONObject) parser.parse(object);
        nmos.readComponent(nmosObject);
        assertTrue(nmos.netlistExist("vss"));
        assertTrue(nmos.netlistExist("vin"));
        assertTrue(nmos.netlistExist("n1"));
        assertFalse(nmos.netlistExist("n2"));
    }
}