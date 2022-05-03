package topologyapi;

import org.json.simple.JSONObject;

public class Nmos extends Component{
    private String drain;
    private String gate;
    private String source;

    @Override
    public JSONObject writeComponent() {
        //declarations
        JSONObject nmos = new JSONObject();
        JSONObject ml = new JSONObject();
        JSONObject netlist = new JSONObject();
        //assign values
        nmos.put("type","nmos");
        nmos.put("id",id);
        ml.put("default",defaultVal);
        ml.put("min",minVal);
        ml.put("max",maxVal);
        nmos.put("m(l)",ml);
        netlist.put("drain",drain);
        netlist.put("gate",gate);
        netlist.put("source",source);
        nmos.put("netlist",netlist);
        //return value
        return nmos;
    }

    @Override
    public Nmos readComponent(JSONObject nmos) {
        //declarations
        JSONObject ml = (JSONObject)nmos.get("m(l)");
        JSONObject netlist = (JSONObject)nmos.get("netlist");
        //assign values
        id = (String) nmos.get("id");
        defaultVal = (Number) ml.get("default");
        minVal = (Number) ml.get("min");
        maxVal = (Number) ml.get("max");
        drain = (String) netlist.get("drain");
        gate = (String) netlist.get("gate");
        source = (String) netlist.get("source");
        //return value
        return this;
    }
    @Override
    public boolean netlistExist(String netlistNodeID) {
        return netlistNodeID.equals(source) || netlistNodeID.equals(gate) || netlistNodeID.equals(drain);
    }

    //setters and getters
    public String getDrain() {
        return drain;
    }

    public void setDrain(String drain) {
        this.drain = drain;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}
