package com.example.mobil

import MainScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobil.ui.theme.MobilTheme
import androidx.compose.ui.Alignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobilTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "loginScreen") {
        composable("loginScreen") {
            loginScreen(onLoginSuccess = {
                navController.navigate("mainScreen")
            })
        }
        composable("mainScreen") {
            MainScreen(onNavigateToDebts = {
                navController.navigate("debts")
            },
                onNavigateToReceivables = {
                    navController.navigate("receivables")
                },

                onNavigateToAccount = {
                    navController.navigate("account activities")
                }
            )
        }

        composable("debts") {
            DebtsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("receivables") {
            ReceivablesScreen(onNavigateBack = {
                navController.popBackStack()
            }
            )
        }
        composable("account activities") {
            AccountActivitiesScreen(onNavigateBack = {
                navController.popBackStack()
            })
        }
    }
}

@Composable
fun loginScreen(onLoginSuccess: () -> Unit) {
    var kullaniciNo by remember { mutableStateOf("123456") }
    var sifre by remember { mutableStateOf("zehra") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Cari Hesap Takip Uygulaması",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(32.dp))

        TextField(
            value = kullaniciNo,
            onValueChange = { kullaniciNo = it },
            label = { Text("Müşteri Numarası") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = sifre,
            onValueChange = { sifre = it },
            label = { Text("Şifre") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (kullaniciNo.isNotEmpty() && sifre.isNotEmpty()) {
                    onLoginSuccess()
                }
            },
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Text("Giriş Yap")
        }
    }
}

class PasswordVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val transformedText = AnnotatedString("*".repeat(text.length))
        return TransformedText(transformedText, object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int = offset
            override fun transformedToOriginal(offset: Int): Int = offset
        })
    }
}

@Preview(showBackground = true)
@Composable
fun GirisEkraniPreview() {
    MobilTheme {
        MyApp()
    }
}
