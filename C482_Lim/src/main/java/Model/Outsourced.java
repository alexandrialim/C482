package Model;

/**
 * This class sets up functions for the Outsourced option of the Parts form
 * @author Alexandria Lim
 */
public class Outsourced extends Part{

    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Sets inputted text as the company name associated with outsourced parts and products.
     * @param companyName sets a company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Gets the company name associated with outsourced parts and products.
     * @return the company name
     */
    public String getCompanyName() {
        return companyName;
    }
}
