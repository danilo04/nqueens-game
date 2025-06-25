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

public val ChessGamesIcons.Pieces.BlackQueen: ImageVector
    get() {
        if (blackQueen != null) {
            return blackQueen!!
        }
        blackQueen =
            Builder(
                name = "BlackQueen",
                defaultWidth = 636.0.dp,
                defaultHeight =
                    636.0.dp,
                viewportWidth = 636.0f,
                viewportHeight = 636.0f,
            ).apply {
                path(
                    fill =
                        linearGradient(
                            0.0f to Color(0xFF1A1A1A),
                            1.0f to Color(0xFF0D0D0D),
                            start =
                                Offset(320.21f, 227.3f),
                            end = Offset(320.21f, 618.74f),
                        ),
                    stroke = null,
                    strokeLineWidth = 0.0f,
                    strokeLineCap = Butt,
                    strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f,
                    pathFillType = NonZero,
                ) {
                    moveTo(480.7f, 618.74f)
                    horizontalLineTo(159.72f)
                    verticalLineTo(527.41f)
                    lineTo(188.43f, 506.53f)
                    verticalLineTo(485.65f)
                    curveTo(188.43f, 485.65f, 193.87f, 479.7f, 201.84f, 469.99f)
                    curveTo(220.07f, 447.79f, 251.57f, 405.93f, 261.5f, 370.83f)
                    curveTo(275.77f, 320.38f, 256.28f, 227.3f, 256.28f, 227.3f)
                    horizontalLineTo(391.98f)
                    curveTo(391.98f, 227.3f, 369.82f, 304.72f, 381.54f, 355.17f)
                    curveTo(390.06f, 391.85f, 422.06f, 444.17f, 438.89f, 469.99f)
                    curveTo(445.21f, 479.69f, 449.39f, 485.65f, 449.39f, 485.65f)
                    verticalLineTo(506.53f)
                    lineTo(480.7f, 527.41f)
                    verticalLineTo(618.74f)
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
                    moveTo(188.43f, 506.53f)
                    lineTo(159.72f, 527.41f)
                    verticalLineTo(618.74f)
                    horizontalLineTo(480.7f)
                    verticalLineTo(527.41f)
                    lineTo(449.39f, 506.53f)
                    moveTo(188.43f, 506.53f)
                    verticalLineTo(485.65f)
                    curveTo(188.43f, 485.65f, 193.87f, 479.7f, 201.84f, 469.99f)
                    moveTo(188.43f, 506.53f)
                    horizontalLineTo(449.39f)
                    moveTo(449.39f, 506.53f)
                    verticalLineTo(485.65f)
                    curveTo(449.39f, 485.65f, 445.21f, 479.69f, 438.89f, 469.99f)
                    moveTo(201.84f, 469.99f)
                    curveTo(220.07f, 447.79f, 251.57f, 405.93f, 261.5f, 370.83f)
                    curveTo(275.77f, 320.38f, 256.28f, 227.3f, 256.28f, 227.3f)
                    horizontalLineTo(391.98f)
                    curveTo(391.98f, 227.3f, 369.82f, 304.72f, 381.54f, 355.17f)
                    curveTo(390.06f, 391.85f, 422.06f, 444.17f, 438.89f, 469.99f)
                    moveTo(201.84f, 469.99f)
                    horizontalLineTo(438.89f)
                }
                path(
                    fill =
                        linearGradient(
                            0.0f to Color(0xFF1A1A1A),
                            1.0f to Color(0xFF0D0D0D),
                            start =
                                Offset(322.98f, 27.64f),
                            end = Offset(322.98f, 287.79f),
                        ),
                    stroke = null,
                    strokeLineWidth = 0.0f,
                    strokeLineCap = Butt,
                    strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f,
                    pathFillType = NonZero,
                ) {
                    moveTo(344.02f, 102.76f)
                    curveTo(352.02f, 95.15f, 360.15f, 81.37f, 360.15f, 69.45f)
                    curveTo(360.15f, 46.36f, 341.43f, 27.64f, 318.34f, 27.64f)
                    curveTo(295.25f, 27.64f, 276.53f, 46.36f, 276.53f, 69.45f)
                    curveTo(276.53f, 83.05f, 287.89f, 95.13f, 297.94f, 102.76f)
                    lineTo(276.53f, 132.17f)
                    curveTo(276.53f, 132.17f, 244.93f, 128.99f, 225.43f, 132.17f)
                    curveTo(205.93f, 135.34f, 172.01f, 157.71f, 172.01f, 157.71f)
                    curveTo(172.01f, 157.71f, 212.28f, 174.81f, 225.43f, 190.23f)
                    curveTo(238.58f, 205.65f, 257.95f, 236.69f, 257.95f, 236.69f)
                    horizontalLineTo(241.69f)
                    verticalLineTo(287.79f)
                    horizontalLineTo(406.6f)
                    verticalLineTo(236.69f)
                    horizontalLineTo(392.66f)
                    curveTo(392.66f, 236.69f, 406.99f, 205.65f, 422.86f, 190.23f)
                    curveTo(438.73f, 174.81f, 473.96f, 157.71f, 473.96f, 157.71f)
                    curveTo(473.96f, 157.71f, 443.27f, 135.34f, 422.86f, 132.17f)
                    curveTo(402.45f, 128.99f, 369.44f, 132.17f, 369.44f, 132.17f)
                    lineTo(344.02f, 102.76f)
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
                    moveTo(257.95f, 236.69f)
                    curveTo(257.95f, 236.69f, 238.58f, 205.65f, 225.43f, 190.23f)
                    curveTo(212.28f, 174.81f, 172.01f, 157.71f, 172.01f, 157.71f)
                    curveTo(172.01f, 157.71f, 205.93f, 135.34f, 225.43f, 132.17f)
                    curveTo(244.93f, 128.99f, 276.53f, 132.17f, 276.53f, 132.17f)
                    lineTo(297.94f, 102.76f)
                    curveTo(287.89f, 95.13f, 276.53f, 83.05f, 276.53f, 69.45f)
                    curveTo(276.53f, 46.36f, 295.25f, 27.64f, 318.34f, 27.64f)
                    curveTo(341.43f, 27.64f, 360.15f, 46.36f, 360.15f, 69.45f)
                    curveTo(360.15f, 81.37f, 352.02f, 95.15f, 344.02f, 102.76f)
                    lineTo(369.44f, 132.17f)
                    curveTo(369.44f, 132.17f, 402.45f, 128.99f, 422.86f, 132.17f)
                    curveTo(443.27f, 135.34f, 473.96f, 157.71f, 473.96f, 157.71f)
                    curveTo(473.96f, 157.71f, 438.73f, 174.81f, 422.86f, 190.23f)
                    curveTo(406.99f, 205.65f, 392.66f, 236.69f, 392.66f, 236.69f)
                    moveTo(257.95f, 236.69f)
                    horizontalLineTo(241.69f)
                    verticalLineTo(287.79f)
                    horizontalLineTo(406.6f)
                    verticalLineTo(236.69f)
                    horizontalLineTo(392.66f)
                    moveTo(257.95f, 236.69f)
                    horizontalLineTo(392.66f)
                }
            }.build()
        return blackQueen!!
    }

private var blackQueen: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = ChessGamesIcons.Pieces.BlackQueen, contentDescription = "")
    }
}
