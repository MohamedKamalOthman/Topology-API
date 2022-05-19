package topologyapi;

public class Nmos extends Transistor {
    /**
     * gets the Type of mos to avoid the use of "instanceof" to determine the type
     *
     * @return Type of Transistor
     */
    @Override
    public String getType() {
        return "nmos";
    }
}
