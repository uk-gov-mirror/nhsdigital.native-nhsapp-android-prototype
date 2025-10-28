package com.prototype.demonhsapp.screens.onboarding

class OnboardingUtils(private val context: Any) {

    private var onBoardingComplete = false
    fun isOnboardingCompleted(): Boolean {
        return onBoardingComplete
    }

    fun setOnboardingCompleted(){
        onBoardingComplete = true
    }

}