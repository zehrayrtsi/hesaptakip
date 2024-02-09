package com.example.mobil

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

@Composable
fun ReceivablesScreen(onNavigateBack: () -> Unit) {
    var alacakMiktari by remember { mutableStateOf("") }
    var alacakTarihi by remember { mutableStateOf("") }
    var alacaklarListesi by remember { mutableStateOf(emptyList<Alacak>()) }
    var secilenParaBirimi by remember { mutableStateOf("TL") }
    var menuExpanded by remember { mutableStateOf(false) }
    val paraBirimOptions = listOf("TL", "EUR", "USD")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        //geri butonu
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.Top
        ) {
            IconButton(
                onClick = {
                    onNavigateBack()
                }
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Geri Git")
            }
            Text(
                text = "",
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable { onNavigateBack() }
            )
        }
        // alacak bilgileri girişi
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            OutlinedTextField(
                value = alacakMiktari,
                onValueChange = { alacakMiktari = it },
                label = { Text("Alacak Miktarı") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = alacakTarihi,
                onValueChange = { alacakTarihi = it },
                label = { Text("Alacak Tarihi (GG/AA/YYYY)") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            //para birimi
            Text(text = "Para Birimi: ")
            Box(modifier = Modifier.fillMaxWidth())
            {
                Row(modifier = Modifier.clickable {
                    menuExpanded = !menuExpanded
                }) {
                    Text(text = secilenParaBirimi)
                    Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
                    DropdownMenu(expanded = menuExpanded, onDismissRequest = {
                        menuExpanded = false
                    }) {
                        paraBirimOptions.forEach {
                            DropdownMenuItem(text = { secilenParaBirimi }, onClick = {
                                secilenParaBirimi = it
                                menuExpanded = false
                            })
                            Text(text = it)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            //alacak ekleme
            Button(
                onClick = {
                    if (alacakMiktari.isNotBlank() && alacakTarihi.isNotBlank()) {
                        val yeniAlacak =
                            Alacak(alacakMiktari.toFloat(), alacakTarihi, secilenParaBirimi)
                        alacaklarListesi = alacaklarListesi + yeniAlacak

                        alacakMiktari = ""
                        alacakTarihi = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Alacak Ekle")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        //alacak listeleme
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            item {
                Text(
                    text = "Alacaklar",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
            }

            items(alacaklarListesi) { alacak ->
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Normal
                            )
                        ) {
                            append("Alacak Miktarı: ")
                        }
                        append("${alacak.miktar} ${alacak.paraBirimi}, Tarih: ${alacak.tarih}")
                    },
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

data class Alacak(val miktar: Float, val tarih: String, val paraBirimi: String)
