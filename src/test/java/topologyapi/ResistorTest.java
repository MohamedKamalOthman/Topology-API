package topologyapi;

import com.sun.source.tree.AssertTree;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.skyscreamer.jsonassert.JSONAssert;

public class ResistorTest extends TestCase {
    Resistor resistor = new Resistor();
    JSONParser parser = new JSONParser();
    double delta = 0.000001d;
    String object = "{\n" +
            "      \"type\": \"resistor\",\n" +
            "      \"id\": \"res1\",\n" +
            "      \"resistance\": {\n" +
            "        \"default\": 100,\n" +
            "        \"min\": 10,\n" +
            "        \"max\": 1000\n" +
            "      },\n" +
            "      \"netlist\": {\n" +
            "        \"t1\": \"vdd\",\n" +
            "        \"t2\": \"n1\"\n" +
            "      }\n" +
            "    }";

    public void testReadComponent() throws ParseException {
        JSONObject resistorObject = (JSONObject) parser.parse(object);
        resistor.readComponent(resistorObject);
        assertEquals("vdd", resistor.getT1());
        assertEquals("n1", resistor.getT2());
        assertEquals("res1", resistor.getId());
        assertEquals(100, resistor.getDefaultVal().doubleValue(), delta);
        assertEquals(1000, resistor.getMaxVal().doubleValue(), delta);
        assertEquals(10, resistor.getMinVal().doubleValue(), delta);
    }

    public void testWriteComponent() throws ParseException, JSONException {
        JSONObject resistorObject = (JSONObject) parser.parse(object);
        resistor.readComponent(resistorObject);
        JSONAssert.assertEquals(object, resistor.writeComponent().toJSONString(), true);
    }

    public void testNetlistExist() throws ParseException {
        JSONObject resistorObject = (JSONObject) parser.parse(object);
        resistor.readComponent(resistorObject);
        assertTrue(resistor.netlistExist("vdd"));
        assertTrue(resistor.netlistExist("n1"));
        assertFalse(resistor.netlistExist("n2"));
    }
}