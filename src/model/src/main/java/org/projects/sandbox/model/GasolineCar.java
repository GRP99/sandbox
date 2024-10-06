package org.projects.sandbox.model;

import java.util.List;
import java.util.Objects;

public class GasolineCar extends Car {
    private final FuelType fuelType;

    protected GasolineCar(String id,
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
        super(id, make, model, year, color, mileage, price, transmissionType, engine, horsepower, features, owners);
        this.fuelType = FuelType.GASOLINE;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GasolineCar gasolineCar = (GasolineCar) o;
        return fuelType == gasolineCar.fuelType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fuelType);
    }

    @Override
    public String toString() {
        return "GasolineCar{" +
                "id='" + super.getId() + '\'' +
                ", make='" + super.getMake() + '\'' +
                ", model='" + super.getModel() + '\'' +
                ", year=" + super.getYear() +
                ", color='" + super.getColor() + '\'' +
                ", mileage=" + super.getMileage() +
                ", price=" + super.getPrice() +
                ", transmissionType=" + super.getTransmissionType() +
                ", engine='" + super.getEngine() + '\'' +
                ", horsepower=" + super.getHorsepower() +
                ", features=" + super.getFeatures() +
                ", owners=" + super.getOwners() +
                ", fuelType=" + fuelType +
                '}';
    }
}
