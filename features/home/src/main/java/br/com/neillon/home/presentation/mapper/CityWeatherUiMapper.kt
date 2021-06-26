package br.com.neillon.home.presentation.mapper

import br.com.neillon.core.abstractions.Mapper
import br.com.neillon.home.domain.entities.CityWeather
import br.com.neillon.home.presentation.dto.CityWeatherUi

interface CityWeatherUiMapper : Mapper<CityWeather, CityWeatherUi>