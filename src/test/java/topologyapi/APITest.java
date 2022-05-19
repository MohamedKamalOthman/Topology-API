package topologyapi;

import junit.framework.TestCase;
import org.json.JSONException;
import org.json.simple.parser.JSONParser;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class APITest extends TestCase {
    API api = new API();
    JSONParser parser = new JSONParser();
    double delta = 0.000001d;
    String filename0 = "." + File.separator + "topologies" + File.separator + "0.json";
    String filename1 = "." + File.separator + "topologies" + File.separator + "1.json";
    String filename2 = "." + File.separator + "topologies" + File.separator + "2.json";
    String filename3 = "." + File.separator + "topologies" + File.separator + "3.json";
    String filename4 = "." + File.separator + "topologies" + File.separator + "4.json";
    String filename5 = "." + File.separator + "topologies" + File.separator + "topology.json";
    String object1 = "{\n" +
            "  \"id\": \"top1\",\n" +
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
            "    }\n" +
            "  ]\n" +
            "}";

    String object2 = "{\n" +
            "  \"id\": \"top2\",\n" +
            "  \"components\": [\n" +
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
            "        \"gate\": \"vin\",\n" +
            "        \"source\": \"vss\"\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    @Test
    public void testReadJSON() throws JSONException {
        //testing adding a file with resistor
        api.readJSON(filename0);
        JSONAssert.assertEquals(object1, api.queryTopologies().get(0).writeJSON().toJSONString(), true);
        api.deleteTopology("top1");
        //testing adding a file with transistor
        api.readJSON(filename1);
        JSONAssert.assertEquals(object2, api.queryTopologies().get(0).writeJSON().toJSONString(), true);
        api.deleteTopology("top2");
    }

    public void testWriteJSON() throws JSONException {
        api.readJSON(filename0);
        api.readJSON(filename1);
        JSONAssert.assertEquals(object1, api.writeJSON("top1").toJSONString(), true);
        JSONAssert.assertEquals(object2, api.writeJSON("top2").toJSONString(), true);
    }

    @Test
    public void testReadWriteJSON() throws JSONException {
        //if the both are working fine they should pass the test
        JSONAssert.assertEquals(api.readJSON(filename0).toJSONString(), api.writeJSON("top1").toJSONString(), true);
        JSONAssert.assertEquals(api.readJSON(filename1).toJSONString(), api.writeJSON("top2").toJSONString(), true);
        JSONAssert.assertEquals(api.readJSON(filename2).toJSONString(), api.writeJSON("top3").toJSONString(), true);
        JSONAssert.assertEquals(api.readJSON(filename3).toJSONString(), api.writeJSON("top4").toJSONString(), true);
        JSONAssert.assertEquals(api.readJSON(filename4).toJSONString(), api.writeJSON("top5").toJSONString(), true);
        JSONAssert.assertEquals(api.readJSON(filename5).toJSONString(), api.writeJSON("top6").toJSONString(), true);
    }

    @Test
    public void testQueryTopologies() {
        assertEquals(new ArrayList<>(), api.queryDevices("top1"));
        api.readJSON(filename0);
        List<Topology> list = api.queryTopologies();
        assertNotNull(list);
        Topology topology = list.get(0);
        assertEquals("top1", topology.getId());
        assertFalse("top2".equals(topology.getId()));
    }

    @Test
    public void testDeleteTopology() {
        assertFalse(api.deleteTopology("top1"));
        assertFalse(api.deleteTopology("top2"));
        assertFalse(api.deleteTopology("top3"));
        api.readJSON(filename0);
        api.readJSON(filename1);
        api.readJSON(filename2);
        assertTrue(api.deleteTopology("top1"));
        assertTrue(api.deleteTopology("top2"));
        assertTrue(api.deleteTopology("top3"));
    }

    @Test
    public void testQueryDevices() {
        assertEquals(new ArrayList<>(), api.queryDevices("top1"));
        api.readJSON(filename0);
        List<Component> list = api.queryDevices("top1");
        assertNotNull(list);
        assertEquals(1, list.stream().count());
    }

    @Test
    public void testQueryDevicesWithNetlistNode() {
        assertEquals(new ArrayList<>(), api.queryDevicesWithNetlistNode("top5", "vdd"));
        api.readJSON(filename4);
        List<Component> list = api.queryDevicesWithNetlistNode("top5", "vdd");
        assertNotNull(list);
        assertEquals(7, list.stream().count());
        list = api.queryDevicesWithNetlistNode("top5", "vss");
        assertEquals(5, list.stream().count());
        list = api.queryDevicesWithNetlistNode("top5", "n2");
        assertEquals(1, list.stream().count());
        list = api.queryDevicesWithNetlistNode("top5", "n3");
        assertEquals(0, list.stream().count());
    }
}