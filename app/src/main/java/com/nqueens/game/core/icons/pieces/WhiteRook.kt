package com.nqueens.game.core.icons.pieces

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nqueens.game.core.icons.ChessGamesIcons

public val ChessGamesIcons.Pieces.WhiteRook: ImageVector
    get() {
        if (whiteRook != null) {
            return whiteRook!!
        }
        whiteRook =
            Builder(
                name = "WhiteRook",
                defaultWidth = 636.0.dp,
                defaultHeight = 636.0.dp,
                viewportWidth = 636.0f,
                viewportHeight = 636.0f,
            ).apply {
                path(
                    fill =
                        linearGradient(
                            0.0f to Color(0xFFF0F0F0),
                            1.0f to Color(0xFFFFFFFF),
                            start =
                                Offset(318.68f, 206.7f),
                            end = Offset(318.68f, 598.14f),
                        ),
                    stroke = null,
                    strokeLineWidth = 0.0f,
                    strokeLineCap = Butt,
                    strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f,
                    pathFillType = NonZero,
                ) {
                    moveTo(479.17f, 598.14f)
                    horizontalLineTo(158.19f)
                    verticalLineTo(506.8f)
                    lineTo(186.89f, 485.92f)
                    verticalLineTo(465.05f)
                    curveTo(186.89f, 465.05f, 192.33f, 459.1f, 200.3f, 449.39f)
                    curveTo(218.54f, 427.18f, 250.04f, 385.33f, 259.96f, 350.23f)
                    curveTo(274.23f, 299.78f, 254.74f, 206.7f, 254.74f, 206.7f)
                    horizontalLineTo(390.44f)
                    curveTo(390.44f, 206.7f, 368.29f, 284.12f, 380.01f, 334.57f)
                    curveTo(388.53f, 371.24f, 420.52f, 423.56f, 437.36f, 449.39f)
                    curveTo(443.67f, 459.09f, 447.86f, 465.05f, 447.86f, 465.05f)
                    verticalLineTo(485.92f)
                    lineTo(479.17f, 506.8f)
                    verticalLineTo(598.14f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0x00000000)),
                    stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 18.4296f,
                    strokeLineCap = Butt,
                    strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f,
                    pathFillType = NonZero,
                ) {
                    moveTo(186.89f, 485.92f)
                    lineTo(158.19f, 506.8f)
                    verticalLineTo(598.14f)
                    horizontalLineTo(479.17f)
                    verticalLineTo(506.8f)
                    lineTo(447.86f, 485.92f)
                    moveTo(186.89f, 485.92f)
                    verticalLineTo(465.05f)
                    curveTo(186.89f, 465.05f, 192.33f, 459.1f, 200.3f, 449.39f)
                    moveTo(186.89f, 485.92f)
                    horizontalLineTo(447.86f)
                    moveTo(447.86f, 485.92f)
                    verticalLineTo(465.05f)
                    curveTo(447.86f, 465.05f, 443.67f, 459.09f, 437.36f, 449.39f)
                    moveTo(200.3f, 449.39f)
                    curveTo(218.54f, 427.18f, 250.04f, 385.33f, 259.96f, 350.23f)
                    curveTo(274.23f, 299.78f, 254.74f, 206.7f, 254.74f, 206.7f)
                    horizontalLineTo(390.44f)
                    curveTo(390.44f, 206.7f, 368.29f, 284.12f, 380.01f, 334.57f)
                    curveTo(388.53f, 371.24f, 420.52f, 423.56f, 437.36f, 449.39f)
                    moveTo(200.3f, 449.39f)
                    horizontalLineTo(437.36f)
                }
                path(
                    fill =
                        linearGradient(
                            0.0f to Color(0xFFF0F0F0),
                            1.0f to Color(0xFFFFFFFF),
                            start =
                                Offset(319.22f, 30.71f),
                            end = Offset(319.22f, 236.51f),
                        ),
                    stroke = null,
                    strokeLineWidth = 0.0f,
                    strokeLineCap = Butt,
                    strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f,
                    pathFillType = NonZero,
                ) {
                    moveTo(159.72f, 30.71f)
                    horizontalLineTo(248.9f)
                    verticalLineTo(123.32f)
                    horizontalLineTo(279.77f)
                    verticalLineTo(30.71f)
                    horizontalLineTo(362.09f)
                    verticalLineTo(123.32f)
                    horizontalLineTo(392.96f)
                    verticalLineTo(30.71f)
                    horizontalLineTo(478.71f)
                    verticalLineTo(185.06f)
                    verticalLineTo(205.64f)
                    lineTo(451.27f, 236.51f)
                    horizontalLineTo(197.45f)
                    lineTo(159.72f, 205.64f)
                    verticalLineTo(185.06f)
                    verticalLineTo(30.71f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0x00000000)),
                    stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 18.4296f,
                    strokeLineCap = Butt,
                    strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f,
                    pathFillType = NonZero,
                ) {
                    moveTo(159.72f, 185.06f)
                    verticalLineTo(205.64f)
                    lineTo(197.45f, 236.51f)
                    horizontalLineTo(451.27f)
                    lineTo(478.71f, 205.64f)
                    verticalLineTo(185.06f)
                    moveTo(159.72f, 185.06f)
                    verticalLineTo(30.71f)
                    horizontalLineTo(248.9f)
                    verticalLineTo(123.32f)
                    horizontalLineTo(279.77f)
                    verticalLineTo(30.71f)
                    horizontalLineTo(362.09f)
                    verticalLineTo(123.32f)
                    horizontalLineTo(392.96f)
                    verticalLineTo(30.71f)
                    horizontalLineTo(478.71f)
                    verticalLineTo(185.06f)
                    moveTo(159.72f, 185.06f)
                    horizontalLineTo(478.71f)
                }
            }.build()
        return whiteRook!!
    }

private var whiteRook: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = ChessGamesIcons.Pieces.WhiteRook, contentDescription = "")
    }
}
