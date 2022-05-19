package topologyapi;

import org.json.simple.JSONObject;

public abstract class Transistor extends Device {
    private String drain;
    private String gate;
    private String source;

    /**
     * export the topology itself to a JSON object(return value)
     *
     * @return topology as JSON object
     */
    @Override
    public JSONObject writeComponent() {
        //declarations
        JSONObject mos = new JSONObject();
        JSONObject ml = new JSONObject();
        JSONObject netlist = new JSONObject();
        //assign values
        mos.put("type", getType());
        mos.put("id", getId());
        ml.put("default", getDefaultVal());
        ml.put("min", getMinVal());
        ml.put("max", getMaxVal());
        mos.put("m(l)", ml);
        netlist.put("drain", getDrain());
        netlist.put("gate", getGate());
        netlist.put("source", getSource());
        mos.put("netlist", netlist);
        //return value
        return mos;
    }

    /**
     * receives a JSON object
     *
     * @param mos
     * @return the corresponding right component
     */
    @Override
    public Transistor readComponent(JSONObject mos) {
        //declarations
        JSONObject ml = (JSONObject) mos.get("m(l)");
        JSONObject netlist = (JSONObject) mos.get("netlist");
        //assign values
        setId((String) mos.get("id"));
        setDefaultVal((Number) ml.get("default"));
        setMinVal((Number) ml.get("min"));
        setMaxVal((Number) ml.get("max"));
        setDrain((String) netlist.get("drain"));
        setGate((String) netlist.get("gate"));
        setSource((String) netlist.get("source"));
        //return value
        return this;
    }

    /**
     * @param netlistNodeID
     * @return true if the given id exist in the component netlist
     */
    @Override
    public boolean netlistExist(String netlistNodeID) {
        return netlistNodeID.equals(getSource()) ||
                netlistNodeID.equals(getGate()) ||
                netlistNodeID.equals(getDrain());
    }


    // setters and getters

    /**
     * @return drain
     */
    public String getDrain() {
        return drain;
    }

    /**
     * sets drain
     *
     * @param drain
     */
    public void setDrain(String drain) {
        this.drain = drain;
    }

    /**
     * @return gate
     */
    public String getGate() {
        return gate;
    }

    /**
     * sets gate
     *
     * @param gate
     */
    public void setGate(String gate) {
        this.gate = gate;
    }

    /**
     * @return source
     */
    public String getSource() {
        return source;
    }

    /**
     * sets source
     *
     * @param source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * gets the Type of mos to avoid the use of "instanceof" to determine the type
     *
     * @return Type of Transistor
     */
    public abstract String getType();
}
