/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.web.util;

import cz.muni.fi.pa165.carpark.dto.CarDto;
import cz.muni.fi.pa165.carpark.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Tomas Svoboda
 */
@Component
public class StringToCarDtoConverter implements Converter<String, CarDto>
{

    @Autowired
    private CarService carService;

    @Override
    public CarDto convert(String source)
    {
        return carService.getCar(Long.valueOf(source));
    }
}
