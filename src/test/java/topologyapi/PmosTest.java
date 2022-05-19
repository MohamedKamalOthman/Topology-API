package topologyapi;

import junit.framework.TestCase;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.skyscreamer.jsonassert.JSONAssert;

public class PmosTest extends TestCase {
    Pmos pmos = new Pmos();
    JSONParser parser = new JSONParser();
    double delta = 0.000001d;
    String object = "{\n" +
            "      \"type\": \"pmos\",\n" +
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
        JSONObject pmosObject = (JSONObject) parser.parse(object);
        pmos.readComponent(pmosObject);
        assertEquals("vin", pmos.getGate());
        assertEquals("n1", pmos.getDrain());
        assertEquals("vss", pmos.getSource());
        assertEquals("m1", pmos.getId());
        assertEquals("pmos", pmos.getType());
        assertEquals(1.5, pmos.getDefaultVal().doubleValue(), delta);
        assertEquals(2, pmos.getMaxVal().doubleValue(), delta);
        assertEquals(1, pmos.getMinVal().doubleValue(), delta);
    }

    public void testReadComponent() throws ParseException, JSONException {
        JSONObject pmosObject = (JSONObject) parser.parse(object);
        pmos.readComponent(pmosObject);
        JSONAssert.assertEquals(object, pmos.writeComponent().toJSONString(), true);
    }

    public void testNetlistExist() throws ParseException {
        JSONObject pmosObject = (JSONObject) parser.parse(object);
        pmos.readComponent(pmosObject);
        assertTrue(pmos.netlistExist("vss"));
        assertTrue(pmos.netlistExist("vin"));
        assertTrue(pmos.netlistExist("n1"));
        assertFalse(pmos.netlistExist("n2"));
    }
}