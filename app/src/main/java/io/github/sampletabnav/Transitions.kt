package io.github.sampletabnav

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry

fun topLevelMaterial3(): DefaultTransitionAnimations {
    val transitionDuration = 75
    val enterTransitionDelay = 75
    return DefaultTransitionAnimations(
        enterTransition = {
            fadeIn(animationSpec = tween(transitionDuration, enterTransitionDelay, LinearEasing))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(transitionDuration, delayMillis = 0, LinearEasing))
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(transitionDuration, enterTransitionDelay, LinearEasing))
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(transitionDuration, delayMillis = 0, LinearEasing))
        },
    )
}

@Composable
fun forwardBackMaterial3(): DefaultTransitionAnimations {
    val transitionDurationFadeDelay = 105
    val transitionDuration = 300
    val offset = with(LocalDensity.current) { 30.dp.roundToPx() }
    return DefaultTransitionAnimations(
        enterTransition = {
            fadeIn(
                tween(
                    durationMillis = transitionDuration,
                    delayMillis = transitionDurationFadeDelay
                )
            ) +
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(transitionDuration),
                        initialOffset = { offset }
                    )
        },
        exitTransition = {
            fadeOut(
                tween(
                    durationMillis = transitionDuration - transitionDurationFadeDelay
                )
            ) +
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(transitionDuration),
                        targetOffset = { -offset }
                    )
        },
        popEnterTransition = {
            fadeIn(
                tween(
                    durationMillis = transitionDuration,
                    delayMillis = transitionDurationFadeDelay
                )
            ) +
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(transitionDuration),
                        initialOffset = { -offset }
                    )
        },

        popExitTransition = {
            fadeOut(
                tween(
                    durationMillis = transitionDuration - transitionDurationFadeDelay
                )
            ) +
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(transitionDuration),
                        targetOffset = { offset }
                    )
        },

    )

}

data class DefaultTransitionAnimations(
    val enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition),
    val exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition),
    val popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition),
    val popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition)
)
