package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.InitialDataProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("SDUF", appName)
    }

    @Test
    fun `verify initial organization data`() {
        val leaders = InitialDataProvider.getInitialLeadership()
        assertTrue(leaders.isNotEmpty())
        val founder = leaders.firstOrNull { it.position.contains("Chairman") }
        assertNotNull(founder)
        assertEquals("Hidayatullah", founder?.name)

        val departments = InitialDataProvider.getInitialDepartments()
        assertTrue(departments.any { it.name.contains("Information") })

        val units = InitialDataProvider.getInitialUnits()
        assertTrue(units.any { it.name.contains("Central Secretariat") })
        assertTrue(units.any { it.name.contains("Tando Allahyar") })
    }
}
