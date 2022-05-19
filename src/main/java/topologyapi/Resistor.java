package topologyapi;

import org.json.simple.JSONObject;

public class Resistor extends Device {
    private String t1;
    private String t2;

    /**
     * export the topology itself to a JSON object(return value)
     *
     * @return topology as JSON object
     */
    @Override
    public JSONObject writeComponent() {
        //declarations
        JSONObject resistor = new JSONObject();
        JSONObject resistance = new JSONObject();
        JSONObject netlist = new JSONObject();
        //assignments
        resistor.put("type", "resistor");
        resistor.put("id", getId());
        resistance.put("default", getDefaultVal());
        resistance.put("min", getMinVal());
        resistance.put("max", getMaxVal());
        resistor.put("resistance", resistance);
        netlist.put("t1", t1);
        netlist.put("t2", t2);
        resistor.put("netlist", netlist);
        //return value
        return resistor;
    }

    /**
     * receives a JSON object
     *
     * @param resistor
     * @return the corresponding right component
     */
    @Override
    public Resistor readComponent(JSONObject resistor) {
        //declarations
        JSONObject resistance = (JSONObject) resistor.get("resistance");
        JSONObject netlist = (JSONObject) resistor.get("netlist");
        //assign values
        setId((String) resistor.get("id"));
        setDefaultVal((Number) resistance.get("default"));
        setMinVal((Number) resistance.get("min"));
        setMaxVal((Number) resistance.get("max"));
        setT1((String) netlist.get("t1"));
        setT2((String) netlist.get("t2"));
        //return value
        return this;
    }

    /**
     * @param netlistNodeID
     * @return true if the given id exist in the component netlist
     */
    @Override
    public boolean netlistExist(String netlistNodeID) {
        return netlistNodeID.equals(getT1()) || netlistNodeID.equals(getT2());
    }

    //setters and getters

    /**
     * @return t1 value
     */
    public String getT1() {
        return t1;
    }

    /**
     * sets t1 value
     *
     * @param t1
     */
    public void setT1(String t1) {
        this.t1 = t1;
    }

    /**
     * @return t2 value
     */
    public String getT2() {
        return t2;
    }

    /**
     * sets t2 value
     *
     * @param t2
     */
    public void setT2(String t2) {
        this.t2 = t2;
    }
}
