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

public val ChessGamesIcons.Pieces.BlackKing: ImageVector
    get() {
        if (blackKing != null) {
            return blackKing!!
        }
        blackKing =
            Builder(
                name = "BlackKing",
                defaultWidth = 636.0.dp,
                defaultHeight = 765.0.dp,
                viewportWidth = 636.0f,
                viewportHeight = 765.0f,
            ).apply {
                path(
                    fill =
                        linearGradient(
                            0.0f to Color(0xFF1A1A1A),
                            1.0f to Color(0xFF0D0D0D),
                            start =
                                Offset(320.22f, 356.3f),
                            end = Offset(320.22f, 747.74f),
                        ),
                    stroke = null,
                    strokeLineWidth = 0.0f,
                    strokeLineCap = Butt,
                    strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f,
                    pathFillType = NonZero,
                ) {
                    moveTo(480.71f, 747.74f)
                    horizontalLineTo(159.73f)
                    verticalLineTo(656.4f)
                    lineTo(188.43f, 635.53f)
                    verticalLineTo(614.65f)
                    curveTo(188.43f, 614.65f, 193.87f, 608.7f, 201.84f, 598.99f)
                    curveTo(220.07f, 576.78f, 251.57f, 534.93f, 261.5f, 499.83f)
                    curveTo(275.77f, 449.38f, 256.28f, 356.3f, 256.28f, 356.3f)
                    horizontalLineTo(391.98f)
                    curveTo(391.98f, 356.3f, 369.82f, 433.72f, 381.54f, 484.17f)
                    curveTo(390.06f, 520.85f, 422.06f, 573.16f, 438.89f, 598.99f)
                    curveTo(445.21f, 608.69f, 449.39f, 614.65f, 449.39f, 614.65f)
                    verticalLineTo(635.53f)
                    lineTo(480.71f, 656.4f)
                    verticalLineTo(747.74f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0x00000000)),
                    stroke = SolidColor(Color(0xFFffffff)),
                    strokeLineWidth = 18.4296f,
                    strokeLineCap = Butt,
                    strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f,
                    pathFillType = NonZero,
                ) {
                    moveTo(188.43f, 635.53f)
                    lineTo(159.73f, 656.4f)
                    verticalLineTo(747.74f)
                    horizontalLineTo(480.71f)
                    verticalLineTo(656.4f)
                    lineTo(449.39f, 635.53f)
                    moveTo(188.43f, 635.53f)
                    verticalLineTo(614.65f)
                    curveTo(188.43f, 614.65f, 193.87f, 608.7f, 201.84f, 598.99f)
                    moveTo(188.43f, 635.53f)
                    horizontalLineTo(449.39f)
                    moveTo(449.39f, 635.53f)
                    verticalLineTo(614.65f)
                    curveTo(449.39f, 614.65f, 445.21f, 608.69f, 438.89f, 598.99f)
                    moveTo(201.84f, 598.99f)
                    curveTo(220.07f, 576.78f, 251.57f, 534.93f, 261.5f, 499.83f)
                    curveTo(275.77f, 449.38f, 256.28f, 356.3f, 256.28f, 356.3f)
                    horizontalLineTo(391.98f)
                    curveTo(391.98f, 356.3f, 369.82f, 433.72f, 381.54f, 484.17f)
                    curveTo(390.06f, 520.85f, 422.06f, 573.16f, 438.89f, 598.99f)
                    moveTo(201.84f, 598.99f)
                    horizontalLineTo(438.89f)
                }
                path(
                    fill =
                        linearGradient(
                            0.0f to Color(0xFF1A1A1A),
                            1.0f to Color(0xFF0D0D0D),
                            start =
                                Offset(324.12f, 9.75f),
                            end = Offset(324.12f, 372.71f),
                        ),
                    stroke = null,
                    strokeLineWidth = 0.0f,
                    strokeLineCap = Butt,
                    strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f,
                    pathFillType = NonZero,
                ) {
                    moveTo(308.6f, 104.61f)
                    verticalLineTo(69.45f)
                    horizontalLineTo(272.78f)
                    verticalLineTo(45.57f)
                    horizontalLineTo(306.21f)
                    verticalLineTo(9.75f)
                    horizontalLineTo(332.48f)
                    verticalLineTo(45.57f)
                    horizontalLineTo(365.91f)
                    verticalLineTo(69.45f)
                    horizontalLineTo(332.48f)
                    verticalLineTo(104.35f)
                    curveTo(349.03f, 108.68f, 358.74f, 122.15f, 358.74f, 138.7f)
                    curveTo(358.74f, 150.64f, 356.54f, 158.5f, 345.8f, 166.31f)
                    lineTo(449.48f, 267.64f)
                    lineTo(392.17f, 317.79f)
                    horizontalLineTo(406.5f)
                    verticalLineTo(372.71f)
                    horizontalLineTo(234.57f)
                    verticalLineTo(317.79f)
                    horizontalLineTo(251.29f)
                    lineTo(198.75f, 267.64f)
                    lineTo(291.88f, 164.96f)
                    horizontalLineTo(293.52f)
                    curveTo(283.78f, 157.09f, 282.33f, 145.86f, 282.33f, 138.7f)
                    curveTo(282.33f, 122.72f, 293.16f, 109.26f, 308.6f, 104.61f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0x00000000)),
                    stroke = SolidColor(Color(0xFFffffff)),
                    strokeLineWidth = 18.4296f,
                    strokeLineCap = Butt,
                    strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f,
                    pathFillType = NonZero,
                ) {
                    moveTo(392.17f, 317.79f)
                    lineTo(449.48f, 267.64f)
                    lineTo(345.8f, 166.31f)
                    curveTo(356.54f, 158.5f, 358.74f, 150.64f, 358.74f, 138.7f)
                    curveTo(358.74f, 122.15f, 349.03f, 108.68f, 332.48f, 104.35f)
                    verticalLineTo(69.45f)
                    horizontalLineTo(365.91f)
                    verticalLineTo(45.57f)
                    horizontalLineTo(332.48f)
                    verticalLineTo(9.75f)
                    horizontalLineTo(306.21f)
                    verticalLineTo(45.57f)
                    horizontalLineTo(272.78f)
                    verticalLineTo(69.45f)
                    horizontalLineTo(308.6f)
                    verticalLineTo(104.61f)
                    curveTo(293.16f, 109.26f, 282.33f, 122.72f, 282.33f, 138.7f)
                    curveTo(282.33f, 145.86f, 283.78f, 157.09f, 293.52f, 164.96f)
                    horizontalLineTo(291.88f)
                    lineTo(198.75f, 267.64f)
                    lineTo(251.29f, 317.79f)
                    moveTo(392.17f, 317.79f)
                    horizontalLineTo(406.5f)
                    verticalLineTo(372.71f)
                    horizontalLineTo(234.57f)
                    verticalLineTo(317.79f)
                    horizontalLineTo(251.29f)
                    moveTo(392.17f, 317.79f)
                    horizontalLineTo(251.29f)
                }
            }.build()
        return blackKing!!
    }

private var blackKing: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = ChessGamesIcons.Pieces.BlackKing, contentDescription = "")
    }
}
