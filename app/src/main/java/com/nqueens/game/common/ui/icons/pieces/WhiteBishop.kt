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

public val NQueensIcons.Pieces.WhiteBishop: ImageVector
    get() {
        if (_whiteBishop != null) {
            return _whiteBishop!!
        }
        _whiteBishop = Builder(name = "WhiteBishop", defaultWidth = 636.0.dp, defaultHeight =
                636.0.dp, viewportWidth = 636.0f, viewportHeight = 636.0f).apply {
            path(fill = linearGradient(0.0f to Color(0xFFF0F0F0), 1.0f to Color(0xFFFFFFFF), start =
                    Offset(318.68f,206.7f), end = Offset(318.68f,598.14f)), stroke = null,
                    strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(479.17f, 598.14f)
                horizontalLineTo(158.19f)
                verticalLineTo(506.8f)
                lineTo(186.89f, 485.93f)
                verticalLineTo(465.05f)
                curveTo(186.89f, 465.05f, 192.33f, 459.1f, 200.3f, 449.39f)
                curveTo(218.54f, 427.18f, 250.03f, 385.33f, 259.96f, 350.23f)
                curveTo(274.23f, 299.78f, 254.74f, 206.7f, 254.74f, 206.7f)
                horizontalLineTo(390.44f)
                curveTo(390.44f, 206.7f, 368.28f, 284.12f, 380.0f, 334.57f)
                curveTo(388.52f, 371.25f, 420.52f, 423.56f, 437.35f, 449.39f)
                curveTo(443.67f, 459.09f, 447.85f, 465.05f, 447.85f, 465.05f)
                verticalLineTo(485.93f)
                lineTo(479.17f, 506.8f)
                verticalLineTo(598.14f)
                close()
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 18.4296f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(186.89f, 485.93f)
                lineTo(158.19f, 506.8f)
                verticalLineTo(598.14f)
                horizontalLineTo(479.17f)
                verticalLineTo(506.8f)
                lineTo(447.85f, 485.93f)
                moveTo(186.89f, 485.93f)
                verticalLineTo(465.05f)
                curveTo(186.89f, 465.05f, 192.33f, 459.1f, 200.3f, 449.39f)
                moveTo(186.89f, 485.93f)
                horizontalLineTo(447.85f)
                moveTo(447.85f, 485.93f)
                verticalLineTo(465.05f)
                curveTo(447.85f, 465.05f, 443.67f, 459.09f, 437.35f, 449.39f)
                moveTo(200.3f, 449.39f)
                curveTo(218.54f, 427.18f, 250.03f, 385.33f, 259.96f, 350.23f)
                curveTo(274.23f, 299.78f, 254.74f, 206.7f, 254.74f, 206.7f)
                horizontalLineTo(390.44f)
                curveTo(390.44f, 206.7f, 368.28f, 284.12f, 380.0f, 334.57f)
                curveTo(388.52f, 371.25f, 420.52f, 423.56f, 437.35f, 449.39f)
                moveTo(200.3f, 449.39f)
                horizontalLineTo(437.35f)
            }
            path(fill = linearGradient(0.0f to Color(0xFFF0F0F0), 1.0f to Color(0xFFFFFFFF), start =
                    Offset(318.0f,24.46f), end = Offset(318.0f,247.67f)), stroke = null,
                    strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(345.17f, 87.41f)
                curveTo(354.72f, 80.9f, 360.81f, 71.03f, 360.81f, 59.97f)
                curveTo(360.81f, 40.36f, 341.64f, 24.46f, 318.0f, 24.46f)
                curveTo(294.36f, 24.46f, 275.19f, 40.36f, 275.19f, 59.97f)
                curveTo(275.19f, 71.05f, 281.3f, 80.94f, 290.88f, 87.45f)
                lineTo(217.1f, 146.21f)
                curveTo(217.1f, 146.21f, 201.81f, 160.74f, 201.81f, 174.12f)
                curveTo(201.81f, 187.49f, 210.98f, 214.7f, 210.98f, 214.7f)
                lineTo(217.1f, 247.67f)
                horizontalLineTo(412.79f)
                lineTo(421.96f, 214.7f)
                curveTo(421.96f, 214.7f, 434.19f, 187.49f, 434.19f, 174.12f)
                curveTo(434.19f, 160.74f, 421.96f, 146.21f, 421.96f, 146.21f)
                lineTo(345.17f, 87.41f)
                close()
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 18.4296f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(210.98f, 214.7f)
                curveTo(210.98f, 214.7f, 201.81f, 187.49f, 201.81f, 174.12f)
                curveTo(201.81f, 160.74f, 217.1f, 146.21f, 217.1f, 146.21f)
                lineTo(290.88f, 87.45f)
                curveTo(281.3f, 80.94f, 275.19f, 71.05f, 275.19f, 59.97f)
                curveTo(275.19f, 40.36f, 294.36f, 24.46f, 318.0f, 24.46f)
                curveTo(341.64f, 24.46f, 360.81f, 40.36f, 360.81f, 59.97f)
                curveTo(360.81f, 71.03f, 354.72f, 80.9f, 345.17f, 87.41f)
                lineTo(421.96f, 146.21f)
                curveTo(421.96f, 146.21f, 434.19f, 160.74f, 434.19f, 174.12f)
                curveTo(434.19f, 187.49f, 421.96f, 214.7f, 421.96f, 214.7f)
                moveTo(210.98f, 214.7f)
                lineTo(217.1f, 247.67f)
                horizontalLineTo(412.79f)
                lineTo(421.96f, 214.7f)
                moveTo(210.98f, 214.7f)
                horizontalLineTo(316.47f)
                horizontalLineTo(421.96f)
            }
        }
        .build()
        return _whiteBishop!!
    }

private var _whiteBishop: ImageVector? = null

@Preview
@Composable
private fun Preview(): Unit {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = NQueensIcons.Pieces.WhiteBishop, contentDescription = "")
    }
}
