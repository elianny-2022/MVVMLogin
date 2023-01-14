package com.example.mvvmlogin.ui.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mvvmlogin.R
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(viewModel: LoginViewModel){
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)){
        Login(Modifier.align(Alignment.Center), viewModel)
    }
}

@Composable
fun Login(modifier: Modifier, viewModel: LoginViewModel) {

    val email: String by viewModel.email.observeAsState(initial="")
    val password: String by viewModel.password.observeAsState(initial="")
    val loginEnable: Boolean by viewModel.loginEnable.observeAsState(initial= false)
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()

    if(isLoading){
        Box(Modifier.fillMaxSize()){
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }else{
        Column(modifier = modifier) {
            HeaderImagen(Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.padding(16.dp))
            EmailField(email) {viewModel.onLoginChanged(it, password)}
            Spacer(modifier = Modifier.padding(4.dp))
            PasswordField(password) {viewModel.onLoginChanged(email, it)}
            Spacer(modifier = Modifier.padding(8.dp))
            ForgotPassword(Modifier.align(Alignment.End))
            Spacer(modifier = Modifier.padding(16.dp))
            LogginButton(loginEnable)
            { coroutineScope.launch { viewModel.onLoginSelected() }
                }
        }
    }


}

@Composable
fun LogginButton(loginEnable: Boolean, onLoginSelected: () -> Unit) {
    Button(onClick = {onLoginSelected() },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFFF16538),
            disabledBackgroundColor = Color(0xFFF3794B),
            contentColor = Color.White,
            disabledContentColor = Color.White
        ), enabled = loginEnable
    )
    {
        Text(text = "Iniciar seccion")
    }
}

@Composable
fun ForgotPassword(modifier: Modifier) {
    Text(text = "Olvidaste la contraseña?",
        modifier = modifier.clickable { },
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFFF16538)
    )
}

@Composable
fun PasswordField(password:String, onTextFieldChanged:(String) -> Unit) {
    TextField(value = password, onValueChange = {onTextFieldChanged(it)},
    placeholder = { Text(text = "Password") },
    modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults .textFieldColors(
            textColor = Color(0xFF554D4D),
            backgroundColor = Color(0xFFE2D8D8),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent))
}


@Composable
fun EmailField(email:String, onTextFieldChanged:(String) -> Unit) {


    TextField(value = email, onValueChange = {onTextFieldChanged(it)},
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Email")
    },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults .textFieldColors(
            textColor = Color(0xFF554D4D),
            backgroundColor = Color(0xFFE2D8D8),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun HeaderImagen(modifier: Modifier) {
    Image(painter = painterResource(id = R.drawable.bici), contentDescription = "header",
    modifier = Modifier)
}
