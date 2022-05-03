package topologyapi;

import org.json.simple.JSONObject;

//an abstract class used for referencing children
public abstract class Component {

    //shared components attributes
    protected String id;
    protected Number defaultVal;
    protected Number minVal;
    protected Number maxVal;

    //the following are abstract because I want to ensure they get overridden in all children classes

    //export the topology itself to a JSON object(return value)
    public abstract JSONObject writeComponent();

    //receive a JSON object and return the corresponding right component
    public abstract Component readComponent(JSONObject obj);

    //return true if the given id exist in the component netlist
    public abstract boolean netlistExist(String id);

    //setters and getters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Number getDefaultVal() {
        return defaultVal;
    }

    public void setDefaultVal(Number defaultVal) {
        this.defaultVal = defaultVal;
    }

    public Number getMinVal() {
        return minVal;
    }

    public void setMinVal(Number minVal) {
        this.minVal = minVal;
    }

    public Number getMaxVal() {
        return maxVal;
    }

    public void setMaxVal(Number maxVal) {
        this.maxVal = maxVal;
    }

}
