package com.example.pdfdownloader.HomePage

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pdfdownloader.R
import com.example.pdfdownloader.VMs.PDFDVM
import com.example.pdfdownloader.ui.theme.mainBlue
import com.example.pdfdownloader.ui.theme.mainGray
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pdfdownloader.VMs.MainViewModel
import com.example.pdfdownloader.VMs.Room.RoomModel
import com.example.pdfdownloader.ui.theme.disabledText
import com.example.pdfdownloader.ui.theme.enabledText
import com.example.pdfdownloader.ui.theme.mainAdminBGCColor
import com.example.pdfdownloader.ui.theme.mainBGCColor
import com.example.pdfdownloader.ui.theme.secondaryAdminBGCColor

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL


@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("ResourceType")
@Composable
fun HomePage(
    navController: NavController,
    model: PDFDVM,
    mainViewModel: MainViewModel,
    owner: LifecycleOwner
) {
    val writePermissionState =
        rememberPermissionState(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    val readPermissionState =
        rememberPermissionState(android.Manifest.permission.READ_EXTERNAL_STORAGE)

    LaunchedEffect(Unit) {
        // Check if the permissions are granted
        if (writePermissionState.status.isGranted && readPermissionState.status.isGranted) {
            //nothing really
        } else {
            // Request the permissions
            writePermissionState.launchPermissionRequest()
            readPermissionState.launchPermissionRequest()
        }
    }
    val selectedPDF = remember {
        mutableStateOf("")
    }
    val navControllerInner = rememberNavController()
    NavHost(navController = navControllerInner, startDestination = "homePage") {
        composable("homePage") {
            if (model.loggedInUser.value.role == "member") {
                val scrollState = rememberScrollState()
                Column(
                    Modifier
                        .fillMaxSize()

                        .background(mainBGCColor)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .clip(RoundedCornerShape(0, 0, 40, 40))
                            .background(mainGray)
                            .padding(end = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        /*
                        IconButton(onClick = { navController.navigate("loginPage") }) {
                            Icon(
                                painter = painterResource(id = R.drawable.back),
                                modifier = Modifier.size(23.dp),
                                contentDescription = "Icon",
                                tint = mainBlue
                            )
                        }

                         */

                        Row(
                            verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .padding(start = 5.dp, end = 5.dp)
                        ) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "${model.loggedInUser.value.firstname} ${model.loggedInUser.value.lastname}",
                                    fontWeight = FontWeight(600),
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center,
                                    color = Color.Black
                                )
                            }




                        }
                        Text(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { if (model.canContinue.value) model.LogoutFunctionallity() }
                                .padding(5.dp),
                            text = "Logout",
                            fontWeight = FontWeight(600),
                            fontSize = 17.sp,
                            textAlign = TextAlign.Center,
                            color = if (model.canContinue.value) enabledText else disabledText
                        )

                    }
                    Row (
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = 10.dp)){
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "all PDFs are added by admin .\n download to use them",
                            fontWeight = FontWeight(600),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(scrollState)
                    ) {
                        val PDFSList = remember {
                            mutableStateOf(emptyList<RoomModel>())
                        }
                        mainViewModel.getAllPDF()
                        mainViewModel.getAllPDFResponse.observe(owner, Observer { response ->
                            Log.d("TAG", "GetAllPDFFunctionallity:started ")
                            val listToSend = mutableListOf<RoomModel>()
                            response.forEach { model ->
                                listToSend.add(model)
                            }
                            PDFSList.value = listToSend
                        })
                        if(PDFSList.value.isNotEmpty()){
                            PDFSList.value.forEach { PDF ->
                                val userList = PDF.downloadedList.split("-")


                                DownloadFileScreen(
                                    selectedPDF = selectedPDF,
                                    navControllerInner = navControllerInner,
                                    fileUrl = "${PDF.url}",
                                    fileName = "${PDF.name}",
                                    downloadedFun = userList.contains(model.loggedInUser.value.name),
                                    PDF = PDF,
                                    model = model
                                )
                            }
                        }
                        else{
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "No Pdf added from admin Yet",
                                fontWeight = FontWeight(600),
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center,
                                color = Color.Black
                            )
                        }


                    }
                }

            }
            else {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(mainAdminBGCColor)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .clip(RoundedCornerShape(0, 0, 40, 40))
                            .background(secondaryAdminBGCColor)
                            .padding(end = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        /*
                        IconButton(onClick = { navController.navigate("loginPage") }) {
                            Icon(
                                painter = painterResource(id = R.drawable.back),
                                modifier = Modifier.size(23.dp),
                                contentDescription = "Icon",
                                tint = mainBlue
                            )
                        }

                         */

                        Row(
                            verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .padding(start = 5.dp, end = 5.dp)
                        ) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "ADMIN :${model.loggedInUser.value.firstname} ${model.loggedInUser.value.lastname}",
                                    fontWeight = FontWeight(600),
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center,
                                    color = Color.Black
                                )
                            }


                        }

                        Text(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { if (model.canContinue.value) model.LogoutFunctionallity() }
                                .padding(5.dp),
                            text = "Logout",
                            fontWeight = FontWeight(600),
                            fontSize = 17.sp,
                            textAlign = TextAlign.Center,
                            color = if (model.canContinue.value) enabledText else disabledText
                        )

                    }
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        text = "Tip : enter PDF's url completely",
                        fontWeight = FontWeight(700),
                        fontSize = 17.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                    TextField(
                        value = model.enteredURL.value,
                        onValueChange = { new ->
                            model.enteredURL.value = new
                            model.checkToContinue()
                        },
                        modifier = Modifier
                            .padding(0.dp)
                            .fillMaxWidth(),
                        textStyle = TextStyle(fontSize = 26.sp, fontWeight = FontWeight(500) , color = Color.Black),
                        placeholder = {
                            Text(
                                text = "enter URL",
                                fontWeight = FontWeight(300),
                                fontSize = 23.sp,
                                color = mainBlue

                            )
                        },

                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = mainBlue,
                            unfocusedContainerColor = Color.White,
                        ),
                        singleLine = true,
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),

                        )

                    //sep
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(disabledText)
                    )
                    //end sep

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        text = "Tip : PDF will be stored with this name and user access it with that name",
                        fontWeight = FontWeight(700),
                        fontSize = 17.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                    TextField(
                        value = model.enteredURLName.value,
                        onValueChange = { new ->
                            model.enteredURLName.value = new
                            model.checkToContinue()
                        },
                        modifier = Modifier
                            .padding(0.dp)
                            .fillMaxWidth(),
                        textStyle = TextStyle(fontSize = 26.sp, fontWeight = FontWeight(500), color = Color.Black),
                        placeholder = {
                            Text(
                                text = "enter name of file",
                                fontWeight = FontWeight(300),
                                fontSize = 23.sp,
                                color = mainBlue

                            )
                        },

                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = mainBlue,
                            unfocusedContainerColor = Color.White,
                        ),
                        singleLine = true,
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),

                        )

                    //sep
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(disabledText)
                    )
                    //end sep
                    
                    Spacer(modifier = Modifier.height(15.dp))
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp), contentAlignment = Alignment.Center) {
                        Text(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    model.InsertPDF()
                                    model.enteredURL.value = ""
                                    model.enteredURLName.value = ""
                                }
                                .background(secondaryAdminBGCColor)
                                .padding(start = 25.dp, end = 25.dp, top = 10.dp, bottom = 10.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            text = "Done",
                            fontWeight = FontWeight(600),
                            fontSize = 17.sp,
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    }

                }

            }
        }
        composable("specificPage") {
            SpecificPDF(filename = selectedPDF.value , navControllerInner)
        }
    }


    /*
    val pdfState = rememberVerticalPdfReaderState(
        resource = ResourceType.Remote("https://myreport.altervista.org/Lorem_Ipsum.pdf"),
        isZoomEnable = true
    )
    VerticalPDFReader(
        state = pdfState,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
    )

     */

    /*
    val pdfState = rememberVerticalPdfReaderState(
        resource = ResourceType.Asset(R.raw.p1),
        isZoomEnable = true
    )
    VerticalPDFReader(
        state = pdfState,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
    )

     */


}


fun downloadFile(fileUrl: String, fileName: String, onProgress: (Float) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            // Create the storage directory if it doesn't exist
            val storageDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            if (!storageDir.exists()) {
                storageDir.mkdirs()
            }

            // Construct the full file path
            val file = File(storageDir, "$fileName.pdf")

            // Open the connection and get the total bytes to download
            val url = URL(fileUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            val totalBytes = connection.contentLength

            // Download the file and save it to the external storage
            var bytesRead = 0
            val inputStream = connection.inputStream
            val outputStream = FileOutputStream(file)
            val buffer = ByteArray(4096)
            var bytes: Int

            while (inputStream.read(buffer).also { bytes = it } >= 0) {
                outputStream.write(buffer, 0, bytes)
                bytesRead += bytes
                onProgress(bytesRead.toFloat() / totalBytes)
            }

            inputStream.close()
            outputStream.flush()
            outputStream.close()
        } catch (e: Exception) {
            // Handle any errors that occur during the download
            Log.d("TAG", "downloadFile: eror $e")
        }
    }
}

@Composable
fun DownloadFileScreen(
    navControllerInner: NavController,
    selectedPDF: MutableState<String>,
    fileUrl: String,
    fileName: String,
    downloadedFun: Boolean,
    PDF: RoomModel,
    model: PDFDVM
) {
    var downloaded by remember {
        mutableStateOf(downloadedFun)
    }
    var downloadProgress by remember { mutableStateOf(0f) }
    var showCircle by remember {
        mutableStateOf(false)
    }

    Box(
        Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(100.dp)
            .clip(RoundedCornerShape(40, 8, 8, 40))
            .background(if (downloaded) mainGray else mainAdminBGCColor)
            .clickable {
                if (downloaded == false) {
                    showCircle = true
                    downloadFile(fileUrl, fileName) { progress ->
                        downloadProgress = progress
                        if (downloadProgress < 1f) {
                            showCircle = false
                            downloaded = true
                            PDF.downloadedList += "${model.loggedInUser.value.name}-"
                            model.UpdatePDF(PDF)
                        }
                    }
                } else {
                    selectedPDF.value = fileName
                    navControllerInner.navigate("specificPage")
                }

            }
            .padding(end = 10.dp)) {


        Row(Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                if (showCircle) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(80.dp),
                        strokeWidth = 5.dp,
                        color = mainBlue
                    )
                } else {
                    if (downloaded) {
                        Text(
                            text = "Downloaded",
                            fontWeight = FontWeight(600),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )
                    } else {
                        Text(
                            text = "Click To Download",
                            fontWeight = FontWeight(600),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    }

                }
            }

            Column(
                verticalArrangement = Arrangement.Center, modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, top = 5.dp, bottom = 5.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxHeight(0.5f),
                    text = "name : $fileName",
                    fontWeight = FontWeight(600),
                    fontSize = 16.sp,

                    color = if (downloaded) Color.Black else Color.White
                )
                Text(
                    text = "url : $fileUrl",
                    fontWeight = FontWeight(600),
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    color = if (downloaded) Color.Black else Color.White

                )
            }
        }


    }


}


