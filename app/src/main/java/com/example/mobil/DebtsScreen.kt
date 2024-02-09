package com.example.mobil

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@Composable
fun DebtsScreen(onNavigateBack: () -> Unit) {
    var borcMiktari by remember { mutableStateOf("") }
    var borcTarihi by remember { mutableStateOf("") }
    var borclarListesi by remember { mutableStateOf(emptyList<Borc>()) }
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
        // borç bilgileri girişi
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            OutlinedTextField(
                value = borcMiktari,
                onValueChange = { borcMiktari = it },
                label = { Text("Borç Miktarı") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = borcTarihi,
                onValueChange = { borcTarihi = it },
                label = { Text("Borç Tarihi (GG/AA/YYYY)") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            // para birimi
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

            //borç ekleme
            Button(
                onClick = {
                    if (borcMiktari.isNotBlank() && borcTarihi.isNotBlank()) {
                        val yeniBorc = Borc(borcMiktari.toFloat(), borcTarihi, secilenParaBirimi)
                        borclarListesi = borclarListesi + yeniBorc

                        //eklemeden sonra temizlemek için
                        borcMiktari = ""
                        borcTarihi = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Borç Ekle")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // borçları listeleme
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            item {
                // Borçlar Başlığı
                Text(
                    text = "Borçlar",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
            }

            items(borclarListesi) { borc ->
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Normal
                            )
                        ) {
                            append("Borç Miktarı: ")
                        }
                        append("${borc.miktar} ${borc.paraBirimi}, Tarih: ${borc.tarih}")
                    },
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

    }

}

data class Borc(val miktar: Float, val tarih: String, val paraBirimi: String)
