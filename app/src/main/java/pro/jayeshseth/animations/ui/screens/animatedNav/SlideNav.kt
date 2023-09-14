package pro.jayeshseth.animations.ui.screens.animatedNav

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import pro.jayeshseth.animations.R

@Composable
fun SlideNav() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        LottieCat2()
    }
}

@Composable
fun LottieCat2() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.cat_hey))
    LottieAnimation(
        composition,
        iterations = LottieConstants.IterateForever
    )
}