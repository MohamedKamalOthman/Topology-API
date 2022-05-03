package topologyapi;

import junit.framework.TestCase;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.File;
import java.util.ArrayList;

public class APITest extends TestCase {
    API api = new API();
    String filename0 ="." + File.separator + "topologies" + File.separator + "0.json";
    String filename1 ="." + File.separator + "topologies" + File.separator + "1.json";
    String filename2 ="." + File.separator + "topologies" + File.separator + "2.json";
    String filename3 ="." + File.separator + "topologies" + File.separator + "3.json";
    String filename4 ="." + File.separator + "topologies" + File.separator + "4.json";
    String filename5 ="." + File.separator + "topologies" + File.separator + "topology.json";

    @Test
    public void testReadWriteJSON() throws JSONException {
        //if the both are working fine they should pass the test
        //important to note that it's best practise to separate each function to test unit
        JSONAssert.assertEquals(api.readJSON(filename0).toJSONString(), api.writeJSON("top1").toJSONString(), true);
        JSONAssert.assertEquals(api.readJSON(filename1).toJSONString(), api.writeJSON("top2").toJSONString(), true);
        JSONAssert.assertEquals(api.readJSON(filename2).toJSONString(), api.writeJSON("top3").toJSONString(), true);
        JSONAssert.assertEquals(api.readJSON(filename3).toJSONString(), api.writeJSON("top4").toJSONString(), true);
        JSONAssert.assertEquals(api.readJSON(filename4).toJSONString(), api.writeJSON("top5").toJSONString(), true);
        JSONAssert.assertEquals(api.readJSON(filename5).toJSONString(), api.writeJSON("top6").toJSONString(), true);
    }

    @Test
    public void testQueryTopologies() {
        assertEquals(new ArrayList<>(),api.queryTopologies());
        api.readJSON(filename0);
        assertNotNull(api.queryTopologies());
    }

    @Test
    public void testDeleteTopology() {
        assertFalse(api.deleteTopology("top1"));
        api.readJSON(filename0);
        assertTrue(api.deleteTopology("top1"));
    }

    @Test
    public void testQueryDevices() {
        assertEquals(new ArrayList<>(),api.queryDevices("top1"));
        api.readJSON(filename0);
        assertNotNull(api.queryTopologies());
    }

    @Test
    public void testQueryDevicesWithNetlistNode() {
        assertEquals(new ArrayList<>(),api.queryDevicesWithNetlistNode("top5","vdd"));
        api.readJSON(filename4);
        assertNotNull(api.queryTopologies());
    }
}