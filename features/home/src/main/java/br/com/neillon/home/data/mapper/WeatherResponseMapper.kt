package br.com.neillon.home.data.mapper

import br.com.neillon.core.abstractions.Mapper
import br.com.neillon.home.data.dto.CityWeatherResponse
import br.com.neillon.home.domain.entities.CityWeather
import br.com.neillon.home.domain.entities.Weather

interface WeatherResponseMapper : Mapper<CityWeatherResponse, CityWeather>