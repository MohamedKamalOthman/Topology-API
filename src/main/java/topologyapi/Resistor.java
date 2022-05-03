package topologyapi;

import org.json.simple.JSONObject;

public class Resistor extends Component {
    private String t1;
    private String t2;

    @Override
    public JSONObject writeComponent() {
        //declarations
        JSONObject resistor = new JSONObject();
        JSONObject resistance = new JSONObject();
        JSONObject netlist = new JSONObject();
        //assignments
        resistor.put("type","resistor");
        resistor.put("id",id);
        resistance.put("default",defaultVal);
        resistance.put("min",minVal);
        resistance.put("max",maxVal);
        resistor.put("resistance",resistance);
        netlist.put("t1",t1);
        netlist.put("t2",t2);
        resistor.put("netlist",netlist);
        //return value
        return resistor;
    }

    @Override
    public Resistor readComponent(JSONObject resistor) {
        //declarations
        JSONObject resistance = (JSONObject)resistor.get("resistance");
        JSONObject netlist = (JSONObject)resistor.get("netlist");
        //assign values
        id = (String) resistor.get("id");
        defaultVal = (Number) resistance.get("default");
        minVal = (Number) resistance.get("min");
        maxVal = (Number) resistance.get("max");
        t1 = (String) netlist.get("t1");
        t2 = (String) netlist.get("t2");
        //return value
        return this;
    }

    @Override
    public boolean netlistExist(String netlistNodeID) {
        return netlistNodeID.equals(t1) || netlistNodeID.equals(t2);
    }

    //setters and getters

    public String getT1() {
        return t1;
    }

    public void setT1(String t1) {
        this.t1 = t1;
    }

    public String getT2() {
        return t2;
    }

    public void setT2(String t2) {
        this.t2 = t2;
    }
}
