package topologyapi;

import org.json.simple.JSONObject;

public interface Component {

    /**
     * export the topology itself to a JSON object(return value)
     *
     * @return topology as JSON object
     */
    JSONObject writeComponent();

    /**
     * receives a JSON object
     *
     * @param obj
     * @return the corresponding right component
     */
    Device readComponent(JSONObject obj);

    /**
     * @param id
     * @return true if the given id exist in the component netlist
     */
    boolean netlistExist(String id);

    //setters and getters the most common

    /**
     * @return id value
     */
    String getId();

    /**
     * sets id value
     *
     * @param id
     */
    void setId(String id);

    /**
     * @return defaultVal
     */
    Number getDefaultVal();

    /**
     * sets defaultVal
     *
     * @param defaultVal
     */
    void setDefaultVal(Number defaultVal);

    /**
     * @return minVal
     */
    Number getMinVal();

    /**
     * sets minVal
     *
     * @param minVal
     */
    void setMinVal(Number minVal);

    /**
     * @return maxVal
     */
    Number getMaxVal();

    /**
     * sets maxVal
     *
     * @param maxVal
     */
    void setMaxVal(Number maxVal);

}
