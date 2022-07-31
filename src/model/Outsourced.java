package model;

public class Outsourced extends Part{

    private String companyName;

    /**
     * @param id part id
     * @param name part name
     * @param price part price
     * @param stock part stock size
     * @param min part min size
     * @param max part max size
     * @param companyName company name
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     *
     * @return company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     *
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
