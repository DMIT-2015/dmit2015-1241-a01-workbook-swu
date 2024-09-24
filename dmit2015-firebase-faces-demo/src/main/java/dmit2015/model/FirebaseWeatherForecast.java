package dmit2015.model;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

/**
 * This class tracks historical weather data for a city.
 *
 * @version 2024.09.13
 */
public class FirebaseWeatherForecast {

    private String name;

    @NotBlank(message = "City value is required.")
    private String city;

    private LocalDate date;

    private int temperatureCelsius;

    private String description;

    public int getTemperatureFahrenheit() {
        return (int) (32 + temperatureCelsius / 0.5556);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getTemperatureCelsius() {
        return temperatureCelsius;
    }

    public void setTemperatureCelsius(int temperatureCelsius) {
        this.temperatureCelsius = temperatureCelsius;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", date=" + date +
                ", temperatureCelsius=" + temperatureCelsius +
                ", description='" + description + '\'' +
                '}';
    }
}