package com.nqueens.game.common.ui.icons.pieces

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
import com.nqueens.game.common.ui.icons.NQueensIcons

public val NQueensIcons.Pieces.WhiteKnight: ImageVector
    get() {
        if (_whiteKnight != null) {
            return _whiteKnight!!
        }
        _whiteKnight = Builder(name = "WhiteKnight", defaultWidth = 636.0.dp, defaultHeight =
                636.0.dp, viewportWidth = 636.0f, viewportHeight = 636.0f).apply {
            path(fill = linearGradient(0.0f to Color(0xFFF0F0F0), 1.0f to Color(0xFFFFFFFF), start =
                    Offset(317.24f,30.58f), end = Offset(317.24f,606.67f)), stroke = null,
                    strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(499.93f, 606.67f)
                horizontalLineTo(179.88f)
                verticalLineTo(513.32f)
                lineTo(209.22f, 489.32f)
                lineTo(227.89f, 449.31f)
                curveTo(227.89f, 449.31f, 221.1f, 415.35f, 231.89f, 379.97f)
                curveTo(247.65f, 328.28f, 307.9f, 233.28f, 307.9f, 233.28f)
                curveTo(307.9f, 233.28f, 287.9f, 262.62f, 243.89f, 251.95f)
                curveTo(199.88f, 241.28f, 155.88f, 273.28f, 155.88f, 273.28f)
                lineTo(134.54f, 209.27f)
                lineTo(209.22f, 163.93f)
                curveTo(209.22f, 163.93f, 208.62f, 138.51f, 227.89f, 123.93f)
                curveTo(247.16f, 109.34f, 307.9f, 89.25f, 307.9f, 89.25f)
                verticalLineTo(30.58f)
                curveTo(307.9f, 30.58f, 438.24f, 115.28f, 467.93f, 198.6f)
                curveTo(497.61f, 281.93f, 459.93f, 449.31f, 459.93f, 449.31f)
                lineTo(470.59f, 489.32f)
                lineTo(499.93f, 513.32f)
                verticalLineTo(606.67f)
                close()
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 18.4296f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(470.59f, 489.32f)
                lineTo(499.93f, 513.32f)
                verticalLineTo(606.67f)
                horizontalLineTo(179.88f)
                verticalLineTo(513.32f)
                lineTo(209.22f, 489.32f)
                moveTo(470.59f, 489.32f)
                lineTo(459.93f, 449.31f)
                moveTo(470.59f, 489.32f)
                horizontalLineTo(209.22f)
                moveTo(459.93f, 449.31f)
                curveTo(459.93f, 449.31f, 497.61f, 281.93f, 467.93f, 198.6f)
                curveTo(438.24f, 115.28f, 307.9f, 30.58f, 307.9f, 30.58f)
                verticalLineTo(89.25f)
                curveTo(307.9f, 89.25f, 247.16f, 109.34f, 227.89f, 123.93f)
                curveTo(208.62f, 138.51f, 209.22f, 163.93f, 209.22f, 163.93f)
                lineTo(134.54f, 209.27f)
                lineTo(155.88f, 273.28f)
                curveTo(155.88f, 273.28f, 199.88f, 241.28f, 243.89f, 251.95f)
                curveTo(287.9f, 262.62f, 307.9f, 233.28f, 307.9f, 233.28f)
                curveTo(307.9f, 233.28f, 247.65f, 328.28f, 231.89f, 379.97f)
                curveTo(221.1f, 415.35f, 227.89f, 449.31f, 227.89f, 449.31f)
                moveTo(459.93f, 449.31f)
                horizontalLineTo(227.89f)
                moveTo(227.89f, 449.31f)
                lineTo(209.22f, 489.32f)
            }
        }
        .build()
        return _whiteKnight!!
    }

private var _whiteKnight: ImageVector? = null

@Preview
@Composable
private fun Preview(): Unit {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = NQueensIcons.Pieces.WhiteKnight, contentDescription = "")
    }
}
