package model;

public class RequestDish {

    private Long id;
    private String name;
    private double cost;
    private int units;
    private int timecost;
    private String activation_date;

    public RequestDish(Long id, String name, double cost, int units, int timecost, String activation_date) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.units = units;
        this.timecost = timecost;
        this.activation_date = activation_date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public int getTimecost() {
        return timecost;
    }

    public void setTimecost(int timecost) {
        this.timecost = timecost;
    }

    public String getActivation_date() {
        return activation_date;
    }

    public void setActivation_date(String activation_date) {
        this.activation_date = activation_date;
    }
}
