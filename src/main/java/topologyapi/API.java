package topologyapi;

import java.io.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.*;

public class API {
    //the usage of Hashmap just to increase the search speed assuming topology id is unique
    private final HashMap<String, Topology> topologies = new HashMap<>();

    /**
     * Read a topology from a given JSON file and store it in the memory.
     * the method uses JSON parser to parse a json file then calls topology
     * methods to handle each appropriate component
     *
     * @param fileName the relative path the file we wish to read
     * @return the JSON object of the read file
     * @see JSONObject
     * @see Topology
     * @see Device
     */
    public JSONObject readJSON(String fileName) {
        //declarations
        JSONParser jsonParser = new JSONParser();
        FileReader topologiesFile = null;
        JSONObject topologyJSON = null;
        try {
            topologiesFile = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            //parse file to java object
            topologyJSON = (JSONObject) jsonParser.parse(topologiesFile);
            //call the readJSON method in Topology class then insert the value to topologies Map
            topologies.put((String) topologyJSON.get("id"), new Topology().readJSON(topologyJSON));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return topologyJSON;
    }

    /**
     * Write a given topology from the memory to a JSON file.
     * the method uses hashmap properties to search for a topology id
     * then write the file to the folder "written/'id'.json"
     *
     * @param topologyID the id of the topology you wish to write as json file
     * @return the JSON object of the read file
     * @see JSONObject
     * @see Topology
     * @see Device
     * @see HashMap
     */
    public JSONObject writeJSON(String topologyID) {
        Topology topology = topologies.get(topologyID);
        //return null if the topology doesn't exist
        if (topology == null)
            return new JSONObject();
        JSONObject topologyJSON = topology.writeJSON();
        //Write JSON file
        try (FileWriter file = new FileWriter("." + File.separator + "written" + File.separator + topology.getId() + ".json")) {
            file.write(topologyJSON.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return topologyJSON;
    }

    /**
     * Query about which topologies are currently in the memory.
     *
     * @return a list of all currently available topologies
     * @see List
     */
    public List<Topology> queryTopologies() {
        return new ArrayList<>(topologies.values());
    }

    /**
     * Delete a given topology from memory.
     *
     * @param topologyID the id of the topology you wish to delete
     * @return true if the topology is found and deleted else returns false
     * @see List
     */
    public boolean deleteTopology(String topologyID) {
        return topologies.remove(topologyID) != null;
    }

    /**
     * Query about which devices are in a given topology.
     * invokes queryDevices method in topology which returns its components list
     *
     * @param topologyID the id of the topology you wish to query its devices
     * @return a list of all currently available devices in the topology returns empty list if topology not found
     * @see List
     * @see Topology
     */
    public List<Component> queryDevices(String topologyID) {
        Topology topology = topologies.get(topologyID);
        if (topology == null)
            return Collections.emptyList();
        return topology.queryDevices();
    }

    /**
     * Query about which devices are connected to a given netlist node in a given topology.
     *
     * @param topologyID    the id of the topology you wish to query its devices
     * @param netlistNodeID the node id you wish to query all devices connected to it
     * @return a list of all currently available topologies with the netlistNodeID
     * @see List
     * @see Topology
     */
    public List<Component> queryDevicesWithNetlistNode(String topologyID, String netlistNodeID) {
        Topology topology = topologies.get(topologyID);
        if (topology == null)
            return Collections.emptyList();
        return topology.queryDevicesWithNetlistNode(netlistNodeID);
    }
}
