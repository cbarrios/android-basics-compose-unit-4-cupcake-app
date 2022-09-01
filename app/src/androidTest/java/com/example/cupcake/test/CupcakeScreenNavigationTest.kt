package com.example.cupcake.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.cupcake.CupcakeApp
import com.example.cupcake.CupcakeScreen
import com.example.cupcake.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class CupcakeScreenNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupCupcakeNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            CupcakeApp(navController = navController)
        }
    }

    // Verify the start destination
    @Test
    fun cupcakeNavHost_verifyStartDestination() {
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    // Verify that the Start screen doesn't have an Up button
    @Test
    fun cupcakeNavHost_verifyBackNavigationNotShownOnStartOrderScreen() {
        val backText = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).assertDoesNotExist()
    }

    // Verify navigation to the Flavor screen
    @Test
    fun cupcakeNavHost_clickOneCupcake_navigatesToSelectFlavorScreen() {
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.one_cupcake))
            .performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Flavor.name)
    }

    // Navigating to the Pickup screen
    @Test
    fun cupcakeNavHost_clickNextOnFlavorScreen_navigatesToPickupScreen() {
        navigateToFlavorScreen()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.next))
            .performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Pickup.name)
    }

    // Navigating to the Start screen by clicking the Up button from the Flavor screen
    @Test
    fun cupcakeNavHost_clickBackOnFlavorScreen_navigatesToStartOrderScreen() {
        navigateToFlavorScreen()
        performNavigateUp()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    // Navigating to the Start screen by clicking the Cancel button from the Flavor screen
    @Test
    fun cupcakeNavHost_clickCancelOnFlavorScreen_navigatesToStartOrderScreen() {
        navigateToFlavorScreen()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.cancel))
            .performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    // Navigating to the Summary screen
    @Test
    fun cupcakeNavHost_clickNextOnPickupScreen_navigatesToSummaryScreen() {
        navigateToPickupScreen()
        composeTestRule.onNodeWithText(getFormattedDate())
            .performClick()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.next))
            .performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Summary.name)
    }

    // Navigating to the Flavor screen by clicking the Up button from the Pickup screen
    @Test
    fun cupcakeNavHost_clickBackOnPickupScreen_navigatesToFlavorScreen() {
        navigateToPickupScreen()
        performNavigateUp()
        navController.assertCurrentRouteName(CupcakeScreen.Flavor.name)
    }

    // Navigating to the Start screen by clicking the Cancel button from the Pickup screen
    @Test
    fun cupcakeNavHost_clickCancelOnPickupScreen_navigatesToStartOrderScreen() {
        navigateToPickupScreen()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.cancel))
            .performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    // Navigating to the Start screen by clicking the Cancel button from the Summary screen
    @Test
    fun cupcakeNavHost_clickCancelOnSummaryScreen_navigatesToStartOrderScreen() {
        navigateToSummaryScreen()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.cancel))
            .performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    // Helper functions
    private fun navigateToFlavorScreen() {
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.one_cupcake))
            .performClick()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.chocolate))
            .performClick()
    }

    private fun navigateToPickupScreen() {
        navigateToFlavorScreen()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.next))
            .performClick()
    }

    private fun navigateToSummaryScreen() {
        navigateToPickupScreen()
        composeTestRule.onNodeWithText(getFormattedDate())
            .performClick()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.next))
            .performClick()
    }

    private fun performNavigateUp() {
        val backText = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).performClick()
    }

    private fun getFormattedDate(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, 1)
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        return formatter.format(calendar.time)
    }
}