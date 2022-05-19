package topologyapi;

import junit.framework.TestCase;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.skyscreamer.jsonassert.JSONAssert;

public class TopologyTest extends TestCase {
    Topology topology = new Topology();
    JSONParser parser = new JSONParser();
    String object = "{\n" +
            "  \"id\": \"top3\",\n" +
            "  \"components\": [\n" +
            "    {\n" +
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
            "    },\n" +
            "    {\n" +
            "      \"type\": \"nmos\",\n" +
            "      \"id\": \"m1\",\n" +
            "      \"m(l)\": {\n" +
            "        \"default\": 1.5,\n" +
            "        \"min\": 1,\n" +
            "        \"max\": 2\n" +
            "      },\n" +
            "      \"netlist\": {\n" +
            "        \"drain\": \"n1\",\n" +
            "        \"gate\": \"vdd\",\n" +
            "        \"source\": \"vss\"\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"type\": \"resistor\",\n" +
            "      \"id\": \"res2\",\n" +
            "      \"resistance\": {\n" +
            "        \"default\": 1500,\n" +
            "        \"min\": 0,\n" +
            "        \"max\": 1000000\n" +
            "      },\n" +
            "      \"netlist\": {\n" +
            "        \"t1\": \"vdd\",\n" +
            "        \"t2\": \"n1\"\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"type\": \"pmos\",\n" +
            "      \"id\": \"m2\",\n" +
            "      \"m(l)\": {\n" +
            "        \"default\": 1.75,\n" +
            "        \"min\": 0.75,\n" +
            "        \"max\": 1.9\n" +
            "      },\n" +
            "      \"netlist\": {\n" +
            "        \"drain\": \"n2\",\n" +
            "        \"gate\": \"vdd\",\n" +
            "        \"source\": \"vss\"\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    public void testReadJSON() throws ParseException {
        JSONObject topologyObject = (JSONObject) parser.parse(object);
        topology.readJSON(topologyObject);
        assertEquals("top3", topology.getId());
    }

    public void testWriteJSON() throws ParseException, JSONException {
        JSONObject topologyObject = (JSONObject) parser.parse(object);
        topology.readJSON(topologyObject);
        JSONAssert.assertEquals(object, topology.writeJSON().toJSONString(), true);
    }

    public void testQueryDevices() throws ParseException {
        JSONObject topologyObject = (JSONObject) parser.parse(object);
        topology.readJSON(topologyObject);
        assertEquals(4, topology.queryDevices().stream().count());
    }

    public void testQueryDevicesWithNetlistNode() throws ParseException {
        JSONObject topologyObject = (JSONObject) parser.parse(object);
        topology.readJSON(topologyObject);
        assertEquals(3, topology.queryDevicesWithNetlistNode("n1").stream().count());
        assertEquals(1, topology.queryDevicesWithNetlistNode("n2").stream().count());
        assertEquals(4, topology.queryDevicesWithNetlistNode("vdd").stream().count());
    }
}