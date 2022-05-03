package topologyapi;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.*;

public class Topology {
    private String id;
    //the usage of Hashmap just to increase the search speed assuming component id is unique
    private List<Component> components = new ArrayList<>();

    //reads a JSON object and converts it to topology(return value)
    public Topology readJSON(JSONObject topology) {
        //assign id value
        id = (String) (topology).get("id");
        //create JSON array to add components to it
        JSONArray componentsJSON = (JSONArray)topology.get("components");
        for (Object componentObj : componentsJSON) {
            JSONObject component = (JSONObject)componentObj;
            String type = (String)component.get("type");
            //pass the object to resistor
            if(type.equals("resistor"))
                components.add(new Resistor().readComponent(component));
            //pass the object to nmos
            if(type.equals("nmos"))
                components.add(new Nmos().readComponent(component));
        }
        //return the Topology
        return this;
    }
    //export the topology itself to a JSON object(return value)
    public JSONObject writeJSON() {
        JSONObject topologyJSON = new JSONObject();
        topologyJSON.put("id",id);
        JSONArray componentsJSON = new JSONArray();
        for (Component component : components) {
            componentsJSON.add(component.writeComponent());
        }
        topologyJSON.put("components",componentsJSON);
        return topologyJSON;
    }

    //export the list of components in the topology
    public List<Component> queryDevices(){
        return components;
    }

    //export a list of components that have nodeID in their netlist
    List<Component> queryDevicesWithNetlistNode(String netlistNodeID){
        List<Component> list= new ArrayList<>();
        for (Component component : components) {
            if(component.netlistExist(netlistNodeID))
                list.add(component);
        }
        return list;
    }

    //setters and getters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
