package pro.jayeshseth.animations.core.ui.components
// TODO: migrate to kmp-palette
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.palette.graphics.Palette
//
//fun Palette.Swatch.toColor(): Color {
//    return Color(rgb)
//}
//
//@Composable
//fun PaletteSwatch(name: String, swatch: Palette.Swatch?) {
//    val color = if (swatch != null) Color(swatch.rgb) else Color.LightGray
//    val textColor = if (swatch != null) Color(swatch.bodyTextColor) else Color.Black
//    val hexString =
//        if (swatch != null) "#${Integer.toHexString(swatch.rgb).uppercase()}" else "N/A"
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp)
//            .background(Color.White, RoundedCornerShape(8.dp))
//            .padding(8.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        // Color Preview Box
//        Box(
//            modifier = Modifier
//                .size(60.dp)
//                .background(color, RoundedCornerShape(8.dp))
//        )
//
//        Spacer(modifier = Modifier.width(16.dp))
//
//        Column {
//            Text(text = name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
//            Text(text = hexString, fontSize = 14.sp, color = Color.Gray)
//            if (swatch != null) {
//                Text(
//                    text = "Population: ${swatch.population}",
//                    fontSize = 12.sp,
//                    color = Color.Gray
//                )
//            }
//        }
//    }
//}