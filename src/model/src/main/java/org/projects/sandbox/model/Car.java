package org.projects.sandbox.model;

import java.util.List;
import java.util.Objects;

public abstract class Car {
    private final String id;
    private final String make;
    private final String model;
    private final Integer year;
    private final String color;
    private final Integer mileage;
    private final Integer price;
    private final TransmissionType transmissionType;
    private final String engine;
    private final Integer horsepower;
    private final List<String> features;
    private final Integer owners;
    private Status status;

    protected Car(String id,
                  String make,
                  String model,
                  Integer year,
                  String color,
                  Integer mileage,
                  Integer price,
                  TransmissionType transmissionType,
                  String engine,
                  Integer horsepower,
                  List<String> features,
                  Integer owners) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.mileage = mileage;
        this.price = price;
        this.transmissionType = transmissionType;
        this.engine = engine;
        this.horsepower = horsepower;
        this.features = features;
        this.owners = owners;
        this.status = Status.AVAILABLE;
    }

    public String getId() {
        return id;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public Integer getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public Integer getMileage() {
        return mileage;
    }

    public Integer getPrice() {
        return price;
    }

    public TransmissionType getTransmissionType() {
        return transmissionType;
    }

    public String getEngine() {
        return engine;
    }

    public Integer getHorsepower() {
        return horsepower;
    }

    public List<String> getFeatures() {
        return features;
    }

    public Integer getOwners() {
        return owners;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(id, car.id)
                && Objects.equals(make, car.make)
                && Objects.equals(model, car.model)
                && Objects.equals(year, car.year)
                && Objects.equals(color, car.color)
                && Objects.equals(mileage, car.mileage)
                && Objects.equals(price, car.price)
                && transmissionType == car.transmissionType
                && Objects.equals(engine, car.engine)
                && Objects.equals(horsepower, car.horsepower)
                && Objects.equals(features, car.features)
                && Objects.equals(owners, car.owners);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                make,
                model,
                year,
                color,
                mileage,
                price,
                transmissionType,
                engine,
                horsepower,
                features,
                owners
        );
    }

    @Override
    public String toString() {
        return "Car{" +
                "id='" + id + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", color='" + color + '\'' +
                ", mileage=" + mileage +
                ", price=" + price +
                ", transmissionType=" + transmissionType +
                ", engine='" + engine + '\'' +
                ", horsepower=" + horsepower +
                ", features=" + features +
                ", owners=" + owners +
                ", status=" + status +
                '}';
    }
}
