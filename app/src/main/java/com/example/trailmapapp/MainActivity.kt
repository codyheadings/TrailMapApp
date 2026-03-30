package com.example.trailmapapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trailmapapp.ui.theme.TrailMapAppTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.Polyline

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrailMapAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val trailPoints = listOf(
        LatLng(42.458053, -71.083101),
        LatLng(42.458370, -71.084785),
        LatLng(42.458342, -71.085767),
        LatLng(42.458156, -71.087231),
        LatLng(42.458255, -71.087778),
        LatLng(42.458528, -71.087569),
        LatLng(42.458839, -71.087757),
        LatLng(42.459179, -71.088465),
        LatLng(42.459472, -71.088672),
        LatLng(42.459757, -71.088714),
        LatLng(42.460400, -71.087803),
        LatLng(42.460784, -71.086767),
        LatLng(42.460004, -71.085174),
        LatLng(42.459375, -71.084782),
        LatLng(42.458372, -71.084786),
        LatLng(42.458053, -71.083101),
    )

    val reservationPoints = listOf(
        LatLng(42.442339, -71.072468),
        LatLng(42.437525, -71.079678),
        LatLng(42.441199, -71.088175),
        LatLng(42.440597, -71.091437),
        LatLng(42.442442, -71.093937),
        LatLng(42.438364, -71.098851),
        LatLng(42.431222, -71.103721),
        LatLng(42.432172, -71.112262),
        LatLng(42.425615, -71.113206),
        LatLng(42.429163, -71.117068),
        LatLng(42.426185, -71.123892),
        LatLng(42.432267, -71.124149),
        LatLng(42.431760, -71.127239),
        LatLng(42.434231, -71.128569),
        LatLng(42.439014, -71.125909),
        LatLng(42.439995, -71.122218),
        LatLng(42.452314, -71.127325),
        LatLng(42.459248, -71.124664),
        LatLng(42.458583, -71.119943),
        LatLng(42.464219, -71.120373),
        LatLng(42.468588, -71.117283),
        LatLng(42.466847, -71.109515),
        LatLng(42.468303, -71.106082),
        LatLng(42.465580, -71.095439),
        LatLng(42.474982, -71.090546),
        LatLng(42.467416, -71.088486),
        LatLng(42.465612, -71.085611),
        LatLng(42.466340, -71.080504),
        LatLng(42.464124, -71.080075),
        LatLng(42.461496, -71.079131),
        LatLng(42.456145, -71.083337),
        LatLng(42.453224, -71.080805),
        LatLng(42.448601, -71.075398),
        LatLng(42.442339, -71.072468)
    )

    val center = LatLng(42.458053, -71.083101)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.Builder()
            .target(center)
            .zoom(15f)
            .build()
    }

    var trailColor by remember { mutableStateOf(Color(0xFFF44336)) }
    var trailWidth by remember { mutableStateOf(12f) }
    var lineColor by remember { mutableStateOf(Color(0xFF4CAF50)) }
    var lineWidth by remember { mutableStateOf(8f) }

    var infoTitle by remember { mutableStateOf("") }
    var infoBody by remember { mutableStateOf("") }
    var showInfo by remember { mutableStateOf(false) }

    var showPanel by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(mapType = MapType.SATELLITE),
            uiSettings = MapUiSettings(zoomControlsEnabled = true),
            onMapClick = { showInfo = false }
        ) {
            Polygon(
                points = reservationPoints,
                fillColor = lineColor.copy(alpha = 0.25f),
                strokeColor = lineColor,
                strokeWidth = lineWidth,
                clickable = true,
                onClick = {
                    infoTitle = "Middlesex Fells Reservation"
                    infoBody  = "2,575-acre natural haven including hiking trails around " +
                            "a pond, plus areas for dog-walking & biking.\n"
                    showInfo  = true
                }
            )

            Polyline(
                points = trailPoints,
                color = trailColor,
                width = trailWidth,
                clickable = true,
                onClick = {
                    infoTitle = "Crystal Springs Trail"
                    infoBody  = "A small hiking trail through the Middlesex Fells Reservation."
                    showInfo  = true
                }
            )

            Marker(
                state = MarkerState(position = trailPoints.first()),
                title = "Crystal Springs Trail",
                snippet = "A small hiking trail through the Middlesex Fells Reservation."
            )
        }

        if (showPanel) {
            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(12.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text("Trail", style = MaterialTheme.typography.titleSmall)
                    Spacer(Modifier.height(4.dp))

                    Text("Width: ${trailWidth.toInt()} px", fontSize = 13.sp)
                    Slider(
                        value = trailWidth,
                        onValueChange = { trailWidth = it },
                        valueRange = 6f..48f
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Color:", fontSize = 13.sp)
                        Spacer(Modifier.width(8.dp))
                        ColorSelector(Color(0xFFF44336)) { trailColor = it }
                        ColorSelector(Color(0xFF03A9F4)) { trailColor = it }
                        ColorSelector(Color(0xFFFFEB3B)) { trailColor = it }
                        ColorSelector(Color(0xFFFFFFFF)) { trailColor = it }
                    }

                    Spacer(Modifier.height(12.dp))
                    HorizontalDivider()
                    Spacer(Modifier.height(12.dp))

                    Text("Preserve", style = MaterialTheme.typography.titleSmall)
                    Spacer(Modifier.height(4.dp))

                    Text("Line width: ${lineWidth.toInt()} px", fontSize = 13.sp)
                    Slider(
                        value = lineWidth,
                        onValueChange = { lineWidth = it },
                        valueRange = 4f..24f
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Color:", fontSize = 13.sp)
                        Spacer(Modifier.width(8.dp))
                        ColorSelector(Color(0xFF4CAF50)) { lineColor = it }
                        ColorSelector(Color(0xFF9C27B0)) { lineColor = it }
                        ColorSelector(Color(0xFFFF5722)) { lineColor = it }
                        ColorSelector(Color(0xFF00BCD4)) { lineColor = it }
                    }
                    Spacer(Modifier.height(52.dp))
                }
            }
        }

        Button(
            onClick = { showPanel = !showPanel },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(12.dp).padding(bottom = 16.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(if (showPanel) "Done" else "Customize")
        }

        if (showInfo) {
            ElevatedCard(modifier = Modifier.padding(top = 48.dp).padding(12.dp)) {
                Text(
                    text = infoTitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(8.dp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = infoBody,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun ColorSelector(color: Color, onClick: (Color) -> Unit) {
    Box(
        modifier = Modifier
            .padding(end = 6.dp)
            .size(28.dp)
            .clip(CircleShape)
            .background(color)
            .clickable { onClick(color) }
    )
}