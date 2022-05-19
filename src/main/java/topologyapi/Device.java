package topologyapi;

import org.json.simple.JSONObject;

//an abstract class used for referencing children
//implements Component to ensure it follows our desired design of
//intended methods in order achieve abstraction
public abstract class Device implements Component {

    //shared Devices attributes
    //private in case we wanted to add validation later
    private String id;
    private Number defaultVal;
    private Number minVal;
    private Number maxVal;

    //the following are abstract because we want to ensure they get overridden in all children classes

    /**
     * export the topology itself to a JSON object(return value)
     *
     * @return topology as JSON object
     */
    public abstract JSONObject writeComponent();

    /**
     * receives a JSON object
     *
     * @param obj
     * @return the corresponding right component
     */
    public abstract Device readComponent(JSONObject obj);

    /**
     * @param id
     * @return true if the given id exist in the component netlist
     */
    public abstract boolean netlistExist(String id);


    //setters and getters

    /**
     * @return id value
     */
    public String getId() {
        return id;
    }

    /**
     * sets id value
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return defaultVal
     */
    public Number getDefaultVal() {
        return defaultVal;
    }

    /**
     * sets defaultVal
     *
     * @param defaultVal
     */
    public void setDefaultVal(Number defaultVal) {
        this.defaultVal = defaultVal;
    }

    /**
     * @return minVal
     */
    public Number getMinVal() {
        return minVal;
    }

    /**
     * sets minVal
     *
     * @param minVal
     */
    public void setMinVal(Number minVal) {
        this.minVal = minVal;
    }

    /**
     * @return maxVal
     */
    public Number getMaxVal() {
        return maxVal;
    }

    /**
     * sets maxVal
     *
     * @param maxVal
     */
    public void setMaxVal(Number maxVal) {
        this.maxVal = maxVal;
    }

}
