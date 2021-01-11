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

    // 생성자
    public Analyze(String state, String city, String street, String clinic, Integer total, Integer positivity) {
        this.State = new SimpleStringProperty(state);
        this.City = new SimpleStringProperty(city);
        this.Street = new SimpleStringProperty(street);
        this.Clinic = new SimpleStringProperty(clinic);
        this.Total = new SimpleIntegerProperty(total);
        this.Positivity = new SimpleIntegerProperty(positivity);
        this.Persentage = new SimpleDoubleProperty((double)positivity / (double)total);
    }

    // get method Colllection
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

    public String getSelection() {
        if(!getClinic().equals("")){
            return getClinic();
        }
        else if(!getStreet().equals("")){
            return getStreet();
        }
        else if(!getCity().equals("")){
            return getCity();
        }
        else if(!getState().equals("")){
            return getState();
        }
        else {
            return "";
        }
    }

    public Integer getTotal() {
        return Total.get();
    }

    public Integer getPositive() {
        return Positivity.get();
    }

    public Double getPersentage() { return Persentage.get(); }



    // set method Collection
    public void setState(String state){ State.set(state); }

    public void setCity(String city){ City.set(city); }

    public void setStreet(String street) {
        Street.set(street);
    }

    public void setClinic(String clinic) { Clinic.set(clinic); }

    public void setTotal(Integer total) {
        Total.set(total);
    }

    public void setPositivity(Integer positivity) {
        Positivity.set(positivity);
    }

    public void setPersentage(Double persentage) {
        Persentage.set(persentage);
    }

    // return properties
    public StringProperty stateProperty() {
        return State;
    }

    public StringProperty cityProperty(){
        return City;
    }

    public StringProperty streetProperty() {
        return Street;
    }

    public StringProperty clinicProperty() {
        return Clinic;
    }

    public IntegerProperty totalProperty() {
        return Total;
    }

    public IntegerProperty positivitiyProperty() {
        return Positivity;
    }

    public DoubleProperty persentageProperty() {
        return Persentage;
    }
}
