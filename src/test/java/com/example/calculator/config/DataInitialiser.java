package com.example.calculator.config;

import com.example.calculator.entity.UnitConversionType;
import com.example.calculator.entity.Unit;
import com.example.calculator.entity.UnitCategory;
import com.example.calculator.repository.UnitConversionTypeRepository;
import com.example.calculator.repository.UnitCategoryRepository;
import com.example.calculator.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Initialises the database with default data on application startup.
 * Once we have enough data we can externalise this to a data.sql file and set the spring.jpa.hibernate.ddl-auto
 * property to 'validate'.
 */

@Component
@Profile("test")
@RequiredArgsConstructor
public class DataInitialiser implements CommandLineRunner {

  // The CommandLineRunner interface indicates that a bean should run when it is contained within a SpringApplication.

  private final UnitConversionTypeRepository unitConversionTypeRepository;
  private final UnitCategoryRepository unitCategoryRepository;
  private final UnitRepository unitRepository;

  @Override
  public void run(String... args) throws Exception {
    // Only initialise if database is empty
    if (unitConversionTypeRepository.count() > 0) {
      return;
    }

    // Create conversion type affine
    UnitConversionType affine = new UnitConversionType();
    affine.setName("affine");
    affine.setDescription("Linear conversion using factor and offset. Conversion to base unit: value * factor + offset");
    unitConversionTypeRepository.save(affine);

    // Create unit categories temperature and length
    UnitCategory temperature = new UnitCategory();
    temperature.setName("temperature");
    unitCategoryRepository.save(temperature);
    UnitCategory length = new UnitCategory();
    length.setName("length");
    unitCategoryRepository.save(length);

    // Create units for temperature
    // Kelvin (base unit)
    Unit kelvin = new Unit();
    kelvin.setName("kelvin");
    kelvin.setSymbol("K");
    kelvin.setUnitConversionType(affine);
    kelvin.setBaseUnit(true);
    kelvin.setConversionToBaseFactor(BigDecimal.ONE);
    kelvin.setConversionToBaseOffset(BigDecimal.ZERO);
    kelvin.setUnitCategory(temperature);
    kelvin.setNotes("The SI unit for temperature. Zero represents absolute zero.");
    unitRepository.save(kelvin);

    // Celsius
    Unit celsius = new Unit();
    celsius.setName("celsius");
    celsius.setSymbol("°C");
    celsius.setUnitConversionType(affine);
    celsius.setBaseUnit(false);
    celsius.setConversionToBaseFactor(BigDecimal.valueOf(1.0));
    celsius.setConversionToBaseOffset(BigDecimal.valueOf(273.15));
    celsius.setUnitCategory(temperature);
    celsius.setNotes("Celsius is based off the freezing point of water = 0.");
    unitRepository.save(celsius);

    // Fahrenheit
    Unit fahrenheit = new Unit();
    fahrenheit.setName("fahrenheit");
    fahrenheit.setSymbol("°F");
    fahrenheit.setUnitConversionType(affine);
    fahrenheit.setBaseUnit(false);
    fahrenheit.setConversionToBaseFactor(new BigDecimal("5").divide(new BigDecimal("9"), 10, RoundingMode.HALF_UP));
    fahrenheit.setConversionToBaseOffset(new BigDecimal("459.67").multiply(new BigDecimal("5")).divide(new BigDecimal("9"), 10,
            RoundingMode.HALF_UP));
    fahrenheit.setUnitCategory(temperature);
    fahrenheit.setNotes("Fahrenheit is based off the freezing point of salt water = 0.");
    unitRepository.save(fahrenheit);

    // Create units for length
    // Meter (base unit)
    Unit meter = new Unit();
    meter.setName("meter");
    meter.setSymbol("m");
    meter.setUnitConversionType(affine);
    meter.setBaseUnit(true);
    meter.setConversionToBaseFactor(BigDecimal.ONE);
    meter.setConversionToBaseOffset(BigDecimal.ZERO);
    meter.setUnitCategory(length);
    meter.setNotes("The SI unit for length.");
    unitRepository.save(meter);

    // Kilometre
    Unit kilometre = new Unit();
    kilometre.setName("kilometre");
    kilometre.setSymbol("km");
    kilometre.setUnitConversionType(affine);
    kilometre.setBaseUnit(false);
    kilometre.setConversionToBaseFactor(BigDecimal.valueOf(1000.0));
    kilometre.setConversionToBaseOffset(BigDecimal.ZERO);
    kilometre.setUnitCategory(length);
    kilometre.setNotes("1 kilometre = 1000 metres.");
    unitRepository.save(kilometre);

    // Mile
    Unit mile = new Unit();
    mile.setName("mile");
    mile.setSymbol("mi");
    mile.setUnitConversionType(affine);
    mile.setBaseUnit(false);
    mile.setConversionToBaseFactor(BigDecimal.valueOf(1609.344));
    mile.setConversionToBaseOffset(BigDecimal.ZERO);
    mile.setUnitCategory(length);
    mile.setNotes("1 mile = 1609.344 metres.");
    unitRepository.save(mile);

    System.out.println("Data initialisation complete.");
  }
}
