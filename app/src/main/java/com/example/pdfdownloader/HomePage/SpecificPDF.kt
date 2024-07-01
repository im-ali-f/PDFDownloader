package com.example.pdfdownloader.HomePage

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pdfdownloader.R
import com.example.pdfdownloader.ui.theme.mainBlue
import com.example.pdfdownloader.ui.theme.mainGray
import java.io.File

@Composable
fun SpecificPDF(filename : String , navController: NavController) {
    Column(Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(mainGray)
                .padding(end = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { navController.navigate("homePage") }) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    modifier = Modifier.size(23.dp),
                    contentDescription = "Icon",
                    tint = mainBlue
                )
            }


        }

        PdfRendererScreen(filename)
    }
}




@Composable
fun PdfRendererScreen(filename:String) {
    val pdfBitmaps = remember { mutableStateListOf<Bitmap>() }


    LaunchedEffect(Unit) {
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val pdfFile = File(downloadsDir, "$filename.pdf")
        if (pdfFile.exists()) {
            pdfBitmaps.clear()
            pdfBitmaps.addAll(renderPdf(pdfFile))
        } else {
            Log.d("TAG", "PdfRendererScreen: $filename . pdf not found in downloads directory")
        }
    }

    LazyColumn(Modifier.fillMaxSize()) {
        items(pdfBitmaps.size) { index ->
            Box(modifier = Modifier.fillMaxSize()){
                Image(modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop, bitmap = pdfBitmaps[index].asImageBitmap(), contentDescription = null)

            }
        }
    }
}

fun renderPdf(file: File): List<Bitmap> {
    val fileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
    val pdfRenderer = PdfRenderer(fileDescriptor)
    val bitmaps = mutableListOf<Bitmap>()

    for (i in 0 until pdfRenderer.pageCount) {
        val page = pdfRenderer.openPage(i)
        val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        bitmaps.add(bitmap)
        page.close()
    }

    pdfRenderer.close()
    return bitmaps
}
/*
@Composable
fun PdfRendererScreen() {
    val pdfBitmap = remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(Unit) {
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val pdfFile = File(downloadsDir, "test.pdf")
        if (pdfFile.exists()) {
            pdfBitmap.value = renderPdfPage(pdfFile, 0) // Render the first page
        } else {
            Log.d("TAG", "PdfRendererScreen: file not found")
        }
    }

    pdfBitmap.value?.let {
        Image(bitmap = it.asImageBitmap(), contentDescription = null)
    }
}

fun renderPdfPage(file: File, pageIndex: Int): Bitmap? {
    val fileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
    val pdfRenderer = PdfRenderer(fileDescriptor)
    val page = pdfRenderer.openPage(pageIndex)

    val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
    page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

    page.close()
    pdfRenderer.close()

    return bitmap
}

 */