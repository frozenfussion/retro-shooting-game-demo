package com.example.retroshooter.input

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

/**
 * Listens to the phone's accelerometer and reports steering values.
 *
 * Call [start] when the screen is visible and [stop] when it is not, so we
 * do not drain the battery in the background.
 *
 * @param onTilt called with a value from -1 (left) to +1 (right) many times a second
 */
class TiltSensor(context: Context, private val onTilt: (Float) -> Unit) : SensorEventListener {

    private val manager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer: Sensor? = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private val filter = TiltFilter()

    /** False on a phone with no accelerometer. */
    val isAvailable: Boolean get() = accelerometer != null

    fun start() {
        filter.reset()
        accelerometer?.let { manager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME) }
    }

    fun stop() {
        manager.unregisterListener(this)
        onTilt(0f)
    }

    override fun onSensorChanged(event: SensorEvent) {
        onTilt(filter.update(event.values[0]))
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not needed.
    }
}
