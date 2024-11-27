package com.example.bottomnavigationview.ui.weather

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.bottomnavigationview.R
import com.example.bottomnavigationview.databinding.FragmentWeatherBinding
import com.example.bottomnavigationview.utils.RetrofitInstance
import com.squareup.picasso.Picasso
import retrofit2.HttpException
import java.io.IOException

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Suppress("UNREACHABLE_CODE")
@OptIn(DelicateCoroutinesApi::class)
class WeatherFragment : Fragment() {

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
        getCurrentWeather()
    }

    @SuppressLint("SetTextI18n")
    @DelicateCoroutinesApi
    private fun getCurrentWeather() {
        GlobalScope.launch(Dispatchers.IO) {
            val response = try {
                RetrofitInstance.api.getCurrentWeather(
                    "Moscow",
                    "metric",
                    requireContext().getString(R.string.api_key)
                )
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "Ошибка ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
                return@launch
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Ошибка ${e.message}", Toast.LENGTH_LONG)
                        .show()
                }
                return@launch
            }
            if (response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) {
                    val data = response.body()
                    val deg = data!!.wind.deg
                    var degree = ""
                    when (deg) {
                        in 338..360 -> degree = "С"
                        in 0..22 -> degree = "С"
                        in 23..67 -> degree = "СВ"
                        in 68..112 -> degree = "З"
                        in 113..157 -> degree = "ЮВ"
                        in 158..202 -> degree = "Ю"
                        in 203..247 -> degree = "ЮЗ"
                        in 248..292 -> degree = "З"
                        in 293..337 -> degree = "СЗ"
                    }
                    binding.cityTV.text = data.name
                    binding.temperatureTV.text = "${data.main.temp}°"
                    binding.maxMinTV.text = "Макс: ${data.main.temp_max}°, Мин: ${data.main.temp_min}°"
                    binding.windDegreeTV.text = "${data.wind.deg}° $degree"
                    binding.windSpeedTV.text = "${data.wind.speed} м/с"
                    val convertPressure = (data.main.pressure / 1.33).toInt().toString()
                    binding.pressureTV.text = "$convertPressure мм рт.ст."
                    val iconId = data.weather[0].icon
                    val imageUrl = "https://openweathermap.org/img/wn/$iconId@4x.png"
                    Picasso.get().load(imageUrl).into(binding.weatherIV)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}