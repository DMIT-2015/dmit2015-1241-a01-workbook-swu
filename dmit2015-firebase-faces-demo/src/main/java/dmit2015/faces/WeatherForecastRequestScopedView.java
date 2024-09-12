package dmit2015.faces;

import dmit2015.model.WeatherForecast;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

@Named("currentWeatherForecastRequestScopedView")
@RequestScoped  // create this object for one HTTP request and destroy after the HTTP response has been sent
public class WeatherForecastRequestScopedView {

    // Define and create a new List of WeatherForecast objects to manage.
    private List<WeatherForecast> forecasts = new ArrayList<>();

    @PostConstruct // This method is executed after DI is completed (fields with @Inject now have values)
    public void init() { // Use this method to initialized fields instead of a constructor
        // Set the startDate to the current date
        var startDate = LocalDate.now();
        // Create an array of summary data using array initializer syntax
        var summaries =  new String[] { "Freezing", "Bracing", "Chilly", "Cool", "Mild", "Warm", "Balmy", "Hot", "Sweltering", "Scorching" };
        // Generate 5 random WeatherForecast data and add the data to forecasts
        var rand = RandomGenerator.getDefault();
        for (int count = 1;  count <= 5; count++)
        {
            // Create a new Weatherforecast instance
            var currentWeatherForecast = new WeatherForecast();
            // Add 1 day to the startDate and set the date of currentWeatherForecast
            startDate = startDate.plusDays(1);
            currentWeatherForecast.setDate(startDate);
            // Assign a random integer value between -20 and 55 for the temperatureC of currentWeatherForecast
            currentWeatherForecast.setTemperatureC(rand.nextInt(-20,55));
            // Assign a random summary for the summary of currentWeatherForecast
            currentWeatherForecast.setSummary(summaries[rand.nextInt(summaries.length)]);
            // Add currentWeatherForecast to forecasts
            forecasts.add(currentWeatherForecast);
        }

    }

    public List<WeatherForecast> getForecasts() {
        return forecasts;
    }
}