package com.prototype.demonhsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.prototype.demonhsapp.screens.onboarding.OnboardingPager
import com.prototype.demonhsapp.screens.onboarding.OnboardingUtils
import com.prototype.demonhsapp.ui.theme.DemoNHSAppTheme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    private val onboardingUtils by lazy { OnboardingUtils(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            DemoNHSApp()

            if (onboardingUtils.isOnboardingCompleted()) {
                DemoNHSApp()
            } else {
                ShowOnboardingScreen()
            }

        }
    }

    // Composable to call the function to set onboarding screen to false
    @Composable
    private fun ShowOnboardingScreen(){
        val scope = rememberCoroutineScope()

        OnboardingPager(){
            onboardingUtils.setOnboardingCompleted()
            scope.launch {
                setContent {
                    DemoNHSApp()
                }
            }
        }
    }
}



//@Composable
//private fun ShowOnboardingScreen(){
//    val context = LocalContext.current
//
//    OnboardingCarousel(){
//        // Show toast message on click of done button
//        Toast.makeText(context, "Onboarding complete", Toast.LENGTH_SHORT).show()
//    }
//}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DemoNHSAppTheme {
        Greeting("Android")
    }
}