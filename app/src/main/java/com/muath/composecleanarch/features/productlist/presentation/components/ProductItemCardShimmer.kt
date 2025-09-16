package com.muath.composecleanarch.features.productlist.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// 👇 custom shimmer modifier
@Composable
fun Modifier.simpleShimmer(): Modifier {
    val transition = rememberInfiniteTransition(label = "shimmer")

    val xShimmer = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer-anim"
    )

    val shimmerBrush = Brush.linearGradient(
        colors = listOf(
            Color.LightGray.copy(alpha = 0.6f),
            Color.LightGray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.6f),
        ),
        start = Offset(xShimmer.value - 200f, 0f),
        end = Offset(xShimmer.value, 0f)
    )

    return this.drawWithContent {
        drawRect(shimmerBrush)
    }
}

@Composable
fun ProductItemCardShimmer() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "",
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(20.dp)
                        .simpleShimmer()
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "",
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .height(14.dp)
                        .simpleShimmer()
                )
            }
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .simpleShimmer()
            )
        }
    }
}
