package com.example.pdfdownloader

import android.os.Bundle
import android.os.StrictMode
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pdfdownloader.HomePage.HomePage
import com.example.pdfdownloader.VMs.MainViewModel
import com.example.pdfdownloader.VMs.PDFDVM
import com.example.pdfdownloader.VMs.Repository
import com.example.pdfdownloader.VMs.Room.db
import com.example.pdfdownloader.lsPage.LoginComp
import com.example.pdfdownloader.lsPage.SignupComp
import com.example.pdfdownloader.ui.theme.PDFDownloaderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StrictMode.allowThreadDiskWrites()
            PDFDownloaderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navStateBig = rememberNavController()
                    val owner = this
                    val contex = LocalContext.current
                    val db = db.getInstance(contex)
                    val repo = Repository(db)
                    val viewModel = MainViewModel(repo)
                    val model = PDFDVM(viewModel,this , navController = navStateBig )



                    Box(modifier = Modifier.fillMaxSize()){

                        NavHost(navController = navStateBig , startDestination = "signupPage" ){
                            composable("signupPage"){
                                SignupComp(navController = navStateBig, model = model )
                            }
                            composable("loginPage"){
                                LoginComp(navController = navStateBig, model = model )
                            }
                            composable("homePage"){
                                HomePage(navController = navStateBig, model = model , mainViewModel = viewModel, owner = owner)
                            }

                        }
                    }
                }
            }
        }
    }
}

