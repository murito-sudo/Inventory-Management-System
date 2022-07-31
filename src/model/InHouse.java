package model;

public class InHouse extends Part{
    private int machineId;


    /**
     * @param id part id
     * @param name part name
     * @param price part price
     * @param stock part stock size
     * @param min part min size
     * @param max part max size
     * @param machineId machine id
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }


    /**
     *
     * @param machineId
     */

    public void setMachineId(int machineId){
        this.machineId = machineId;

    }

    /**
     * @return machine id
     */

    public int getMachineId(){
        return machineId;
    }
}
