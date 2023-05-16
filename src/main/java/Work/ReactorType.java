package Work;

import java.util.Objects;

public class ReactorType {
    private String name;
    private double burnup;
    private double kpd;
    private double enrichment;
    private int termal_capacity;
    private double electrical_capacity;
    private int life_time;
    private double first_load;
    private String from;

    public ReactorType(String name, double burnup, double kpd, double enrichment, int termal_capacity, double electrical_capacity, int life_time, double first_load) {
        this.name = name;
        this.burnup = burnup;
        this.kpd = kpd;
        this.enrichment = enrichment;
        this.termal_capacity = termal_capacity;
        this.electrical_capacity = electrical_capacity;
        this.life_time = life_time;
        this.first_load = first_load;
    }
    
    public String getName() {
        return name;
    }

    public double getBurnup() {
        return burnup;
    }

    public double getKpd() {
        return kpd;
    }

    public double getEnrichment() {
        return enrichment;
    }

    public int getTermal_capacity() {
        return termal_capacity;
    }

    public double getElectrical_capacity() {
        return electrical_capacity;
    }

    public int getLife_time() {
        return life_time;
    }

    public double getFirst_load() {
        return first_load;
    }

    public String getFrom() {
        return from;
    }
    
    

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String toString() {
        return "ReactorType{" +
                "name='" + name + '\'' +
                ", burnup=" + burnup +
                ", kpd=" + kpd +
                ", enrichment=" + enrichment +
                ", termal_capacity=" + termal_capacity +
                ", electrical_capacity=" + electrical_capacity +
                ", life_time=" + life_time +
                ", first_load=" + first_load +
                ", from=" + from +
                '}';
    }
}
