package com.example.retroshooter

import com.example.retroshooter.graphics.TimeOfDay
import org.junit.Assert.assertEquals
import org.junit.Test

class TimeOfDayTest {
    @Test
    fun hoursMapToTheRightLook() {
        assertEquals(TimeOfDay.NIGHT, TimeOfDay.forHour(0))
        assertEquals(TimeOfDay.NIGHT, TimeOfDay.forHour(4))
        assertEquals(TimeOfDay.DAWN, TimeOfDay.forHour(5))
        assertEquals(TimeOfDay.DAWN, TimeOfDay.forHour(7))
        assertEquals(TimeOfDay.DAY, TimeOfDay.forHour(8))
        assertEquals(TimeOfDay.DAY, TimeOfDay.forHour(16))
        assertEquals(TimeOfDay.EVENING, TimeOfDay.forHour(17))
        assertEquals(TimeOfDay.EVENING, TimeOfDay.forHour(19))
        assertEquals(TimeOfDay.NIGHT, TimeOfDay.forHour(20))
        assertEquals(TimeOfDay.NIGHT, TimeOfDay.forHour(23))
    }
}
