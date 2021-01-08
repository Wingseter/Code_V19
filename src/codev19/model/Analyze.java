package codev19.model;

import javafx.beans.property.*;

public class Analyze {
    private final StringProperty State;
    private final StringProperty City;
    private final StringProperty Street;
    private final StringProperty Clinic;
    private final IntegerProperty Total;
    private final IntegerProperty Positivity;
    private final DoubleProperty  Persentage;

    // default 생성자
    public Analyze() {
        this(null, null, null, null, 0, 0);
    }

    //
    public Analyze(String state, String city, String street, String clinic, Integer total, Integer positivity) {
        this.State = new SimpleStringProperty(state);
        this.City = new SimpleStringProperty(city);
        this.Street = new SimpleStringProperty(street);
        this.Clinic = new SimpleStringProperty(clinic);
        this.Total = new SimpleIntegerProperty(total);
        this.Positivity = new SimpleIntegerProperty(positivity);
        this.Persentage = new SimpleDoubleProperty(positivity / total);
    }

    // get method
    public String getState() {
        return State.get();
    }

    public String getCity() {
        return City.get();
    }

    public String getStreet() {
        return Street.get();
    }

    public String getClinic() {
        return Clinic.get();
    }

}
