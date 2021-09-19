package com.example.favourr

import java.io.Serializable

data class CityModel (val bounties: List<FavourrModel>, val message: String) : Serializable