package com.example.cupcake.test

import com.example.cupcake.ui.OrderViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class OrderViewModelTest {

    private val orderViewModel = OrderViewModel()

    @Test
    fun orderViewModel_setQuantity_UiStateUpdatedCorrectly() {
        // GIVEN
        val initialState = orderViewModel.uiState.value
        assertEquals(0, initialState.quantity)
        assertTrue(initialState.price.isEmpty())

        // WHEN
        orderViewModel.setQuantity(6)

        // THEN
        val currentState = orderViewModel.uiState.value
        assertEquals(6, currentState.quantity)
        assertEquals("$12.00", currentState.price)
    }

    @Test
    fun orderViewModel_setFlavor_UiStateUpdatedCorrectly() {
        // GIVEN
        val initialState = orderViewModel.uiState.value
        assertTrue(initialState.flavor.isEmpty())

        // WHEN
        orderViewModel.setFlavor("Chocolate")

        // THEN
        val currentState = orderViewModel.uiState.value
        assertEquals("Chocolate", currentState.flavor)
    }

    @Test
    fun orderViewModel_setDateToday_UiStateUpdatedCorrectly() {
        // GIVEN
        val initialState = orderViewModel.uiState.value
        assertTrue(initialState.date.isEmpty())
        assertTrue(initialState.price.isEmpty())

        // WHEN
        orderViewModel.setDate(initialState.pickupOptions[0])

        // THEN
        val currentState = orderViewModel.uiState.value
        assertEquals(initialState.pickupOptions[0], currentState.date)
        assertEquals("$3.00", currentState.price)
    }

    @Test
    fun orderViewModel_setDateTomorrow_UiStateUpdatedCorrectly() {
        // GIVEN
        val initialState = orderViewModel.uiState.value
        assertTrue(initialState.date.isEmpty())
        assertTrue(initialState.price.isEmpty())

        // WHEN
        orderViewModel.setDate(initialState.pickupOptions[1])

        // THEN
        val currentState = orderViewModel.uiState.value
        assertEquals(initialState.pickupOptions[1], currentState.date)
        assertEquals("$0.00", currentState.price)
    }

    @Test
    fun orderViewModel_setDateTodayWithQuantity_UiStateUpdatedCorrectly() {
        // GIVEN
        orderViewModel.setQuantity(12)
        assertTrue(orderViewModel.uiState.value.date.isEmpty())
        assertEquals("$24.00", orderViewModel.uiState.value.price)

        // WHEN
        val today = orderViewModel.uiState.value.pickupOptions.first()
        orderViewModel.setDate(today)

        // THEN
        val currentState = orderViewModel.uiState.value
        assertEquals(today, currentState.date)
        assertEquals("$27.00", currentState.price)
    }

    @Test
    fun orderViewModel_resetOrder_UiStateUpdatedCorrectly() {
        // GIVEN
        orderViewModel.setQuantity(12)
        orderViewModel.setFlavor("Vanilla")
        orderViewModel.setDate("Wed Aug 31")

        // WHEN
        orderViewModel.resetOrder()

        // THEN
        assertEquals(0, orderViewModel.uiState.value.quantity)
        assertEquals("", orderViewModel.uiState.value.flavor)
        assertEquals("", orderViewModel.uiState.value.date)
        assertEquals("", orderViewModel.uiState.value.price)
        assertTrue(orderViewModel.uiState.value.pickupOptions.isNotEmpty())
    }
}