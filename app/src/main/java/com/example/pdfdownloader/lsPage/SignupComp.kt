package com.example.pdfdownloader.lsPage

import android.annotation.SuppressLint
import android.widget.Space
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pdfdownloader.R
import com.example.pdfdownloader.VMs.PDFDVM
import com.example.pdfdownloader.ui.theme.disabledText
import com.example.pdfdownloader.ui.theme.enabledText
import com.example.pdfdownloader.ui.theme.mainBGCColor
import com.example.pdfdownloader.ui.theme.mainBlue
import com.example.pdfdownloader.ui.theme.mainFontColor
import com.example.pdfdownloader.ui.theme.mainGray
import kotlinx.coroutines.delay

@SuppressLint("SuspiciousIndentation")
@Composable
fun SignupComp(navController: NavController, model: PDFDVM) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(mainBGCColor)
    ) {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clip(RoundedCornerShape(0 , 0  , 40,40))
                    .background(mainGray)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Signup",
                    fontWeight = FontWeight(600),
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center,
                    color = mainBlue

                    )

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 12.5.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { if (model.canContinue2.value) model.SignupFunctionallity() }
                            .padding(5.dp),
                        text = "Done",
                        fontWeight = FontWeight(600),
                        fontSize = 17.sp,
                        textAlign = TextAlign.Center,
                        color = if (model.canContinue2.value) enabledText else disabledText
                    )
                }


            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 38.dp, end = 38.dp, bottom = 20.dp, top = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Please enter your Informations to continue",
                    fontWeight = FontWeight(400),
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black

                )
            }

            //sep
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(disabledText)
            )
            //end sep
            Box(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = model.enteredName2.value,
                    onValueChange = { new ->
                        model.enteredName2.value = new
                        model.checkToContinue2()
                    },
                    modifier = Modifier
                        .padding(0.dp)
                        .fillMaxWidth(),
                    textStyle = TextStyle(fontSize = 26.sp, fontWeight = FontWeight(500), color=Color.Black),
                    placeholder = {
                        Text(
                            text = "username",
                            fontWeight = FontWeight(300),
                            fontSize = 23.sp,
                            color = disabledText

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
            }

            //sep
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(disabledText)
            )
            //end sep

            Spacer(modifier = Modifier.height(10.dp))

            //sep
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(disabledText)
            )
            //end sep

            Box(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = model.enteredPass2.value,
                    onValueChange = { new ->
                        model.enteredPass2.value = new
                        model.checkToContinue2()
                    },
                    modifier = Modifier
                        .padding(0.dp)
                        .fillMaxWidth(),
                    textStyle = TextStyle(fontSize = 26.sp, fontWeight = FontWeight(500), color=Color.Black),
                    placeholder = {
                        Text(
                            text = "password",
                            fontWeight = FontWeight(300),
                            fontSize = 23.sp,
                            color = disabledText

                        )
                    },

                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = mainBlue,
                        unfocusedContainerColor = Color.White,
                    ),
                    visualTransformation = if (model.passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {
                            model.passwordVisible.value = !model.passwordVisible.value
                        }) {
                            Icon(
                                painter = painterResource(id = if (model.passwordVisible.value) R.drawable.eye_slash else R.drawable.eye),
                                modifier = Modifier.size(19.dp),
                                contentDescription = " toggle pass word Icon",
                                tint = Color.Black
                            )
                        }
                    },
                    singleLine = true,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),

                    )
            }

            //sep
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(disabledText)
            )
            //end sep
            Box(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = model.enteredRePass.value,
                    onValueChange = { new ->
                        model.enteredRePass.value = new
                        model.checkToContinue2()
                    },
                    modifier = Modifier
                        .padding(0.dp)
                        .fillMaxWidth(),
                    textStyle = TextStyle(fontSize = 26.sp, fontWeight = FontWeight(500), color=Color.Black),
                    placeholder = {
                        Text(
                            text = "confirm password",
                            fontWeight = FontWeight(300),
                            fontSize = 23.sp,
                            color = disabledText

                        )
                    },

                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = mainBlue,
                        unfocusedContainerColor = Color.White,
                    ),
                    visualTransformation = if (model.passwordVisible2.value) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {
                            model.passwordVisible2.value = !model.passwordVisible2.value
                        }) {
                            Icon(
                                painter = painterResource(id = if (model.passwordVisible2.value) R.drawable.eye_slash else R.drawable.eye),
                                modifier = Modifier.size(19.dp),
                                contentDescription = " toggle pass word Icon",
                                tint = Color.Black
                            )
                        }
                    },
                    singleLine = true,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),

                    )
            }

            //sep
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(disabledText)
            )
            //end sep
            Spacer(modifier = Modifier.height(10.dp))

            //sep
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(disabledText)
            )
            //end sep

            Box(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = model.enteredfirstName.value,
                    onValueChange = { new ->
                        model.enteredfirstName.value = new
                        model.checkToContinue2()
                    },
                    modifier = Modifier
                        .padding(0.dp)
                        .fillMaxWidth(),
                    textStyle = TextStyle(fontSize = 26.sp, fontWeight = FontWeight(500), color=Color.Black),
                    placeholder = {
                        Text(
                            text = "firstname",
                            fontWeight = FontWeight(300),
                            fontSize = 23.sp,
                            color = disabledText

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
            }


            //sep
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(disabledText)
            )
            //end sep



            Box(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = model.enteredlastName.value,
                    onValueChange = { new ->
                        model.enteredlastName.value = new
                        model.checkToContinue2()
                    },
                    modifier = Modifier
                        .padding(0.dp)
                        .fillMaxWidth(),
                    textStyle = TextStyle(fontSize = 26.sp, fontWeight = FontWeight(500), color=Color.Black),
                    placeholder = {
                        Text(
                            text = "lastname",
                            fontWeight = FontWeight(300),
                            fontSize = 23.sp,
                            color = disabledText

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
            }


            //sep
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(disabledText)
            )
            //end sep

            Row (
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp))
            {
                Text(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { model.enteredRole.value = "admin" }
                        .padding(5.dp),
                    text = "Admin",
                    fontWeight = FontWeight(600),
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center,
                    color = if (model.enteredRole.value == "admin") enabledText else disabledText
                )

                Text(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { model.enteredRole.value = "member" }
                        .padding(5.dp),
                    text = "Member",
                    fontWeight = FontWeight(600),
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center,
                    color = if (model.enteredRole.value == "member") enabledText else disabledText
                )
            }

            Row (Modifier.fillMaxWidth().padding(10.dp)){
                Text(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { navController.navigate("loginPage") }
                        .padding(5.dp),
                    text = "already have account",
                    fontWeight = FontWeight(600),
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center,
                    color = mainBlue
                )

            }
        }

        val animateOffset by animateIntAsState(
            targetValue = if (model.userpassError.value) 0 else -50,
            animationSpec = tween(
                durationMillis = 500,
            ), label = "erroranimation"
        )

        val animateOffset2 by animateIntAsState(
            targetValue = if (model.passwordError.value) 0 else -50,
            animationSpec = tween(
                durationMillis = 500,
            ), label = "erroranimation"
        )

        val animateOffset3 by animateIntAsState(
            targetValue = if (model.userExsitanceError.value) 0 else -50,
            animationSpec = tween(
                durationMillis = 500,
            ), label = "erroranimation"
        )
        LaunchedEffect(model.userpassError.value) {
            delay(2500).apply {
                model.userpassError.value = false
            }

        }
        LaunchedEffect(model.passwordError.value) {
            delay(2500).apply {
                model.passwordError.value = false
            }

        }
        LaunchedEffect(model.userExsitanceError.value) {
            delay(2500).apply {
                model.userExsitanceError.value = false
            }

        }
        Row(
            Modifier
                .fillMaxWidth()
                .height(50.dp)
                .offset(y = -animateOffset.dp)
                .background(mainGray)
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "password or username invalid",
                fontWeight = FontWeight(600),
                fontSize = 17.sp,
                textAlign = TextAlign.Center,
                color = Color.Red,
            )
        }


        Row(
            Modifier
                .fillMaxWidth()
                .height(50.dp)
                .offset(y = -animateOffset2.dp)
                .background(mainGray)
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "password and confirm password are not the same",
                fontWeight = FontWeight(600),
                fontSize = 17.sp,
                textAlign = TextAlign.Center,
                color = Color.Red,
            )
        }


        Row(
            Modifier
                .fillMaxWidth()
                .height(50.dp)
                .offset(y = -animateOffset3.dp)
                .background(mainGray)
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "user already exist",
                fontWeight = FontWeight(600),
                fontSize = 17.sp,
                textAlign = TextAlign.Center,
                color = Color.Red,
            )
        }




    }
}