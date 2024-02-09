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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

@Composable
fun AccountActivitiesScreen(onNavigateBack: () -> Unit) {
    var transactionType by remember { mutableStateOf("Gelir") }
    var transactionAmount by remember { mutableStateOf("") }
    var transactionDate by remember { mutableStateOf("") }
    var transactionDescription by remember { mutableStateOf("") }
    var typeMenuExpanded by remember { mutableStateOf(false) }
    var currencyMenuExpanded by remember { mutableStateOf(false) }
    val transactionTypes = listOf("Gelir", "Gider", "Transfer")
    var islemListesi by remember { mutableStateOf(emptyList<Islem>()) }
    val paraBirimOptions = listOf("TL", "EUR", "USD")
    var secilenParaBirimi by remember { mutableStateOf("TL") }

    //geri butonu
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        IconButton(
            onClick = { onNavigateBack() },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Geri")
        }
        Spacer(modifier = Modifier.height(8.dp))

        //işlem türü dropdown
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "İşlem Türü: ")
            Box(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.clickable { typeMenuExpanded = !typeMenuExpanded }) {
                    Text(text = transactionType)
                    Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
                }
                DropdownMenu(
                    expanded = typeMenuExpanded,
                    onDismissRequest = { typeMenuExpanded = false }) {
                    transactionTypes.forEach { type ->
                        DropdownMenuItem(text = { transactionType }, onClick = {
                            transactionType = type
                            typeMenuExpanded = false
                        })
                        Text(text = type)

                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        // para birimi
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Para Birimi: ")
            Box(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.clickable {
                    currencyMenuExpanded = !currencyMenuExpanded
                }) {
                    Text(text = secilenParaBirimi)
                    Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
                }
                DropdownMenu(
                    expanded = currencyMenuExpanded,
                    onDismissRequest = { currencyMenuExpanded = false }) {
                    paraBirimOptions.forEach { currency ->
                        DropdownMenuItem(text = { secilenParaBirimi }, onClick = {
                            secilenParaBirimi = currency
                            currencyMenuExpanded = false
                        })
                        Text(text = currency)

                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        // işlem bilgi girişleri
        OutlinedTextField(
            value = transactionAmount,
            onValueChange = { transactionAmount = it },
            label = { Text("İşlem Miktarı") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = transactionDate,
            onValueChange = { transactionDate = it },
            label = { Text("İşlem Tarihi (GG/AA/YYYY)") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = transactionDescription,
            onValueChange = { transactionDescription = it },
            label = { Text("Açıklama") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        //alacak ekleme
        Button(
            onClick = {
                if (transactionType.isNotBlank() && transactionAmount.isNotBlank() && secilenParaBirimi.isNotBlank() && transactionDate.isNotBlank() && transactionDescription.isNotBlank()) {
                    val yeniislem = Islem(
                        transactionAmount.toFloat(),
                        secilenParaBirimi,
                        transactionType,
                        transactionDate,
                        transactionDescription
                    )
                    islemListesi = islemListesi + yeniislem

                    transactionAmount = ""
                    transactionDate = ""
                    transactionDescription = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("İşlem Ekle")
        }
        Spacer(modifier = Modifier.height(16.dp))

        //alacak listeleme
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                Text(
                    text = "Hesap Hareketleri",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
            }

            items(islemListesi) { islem ->
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Normal
                            )
                        ) {
                        }
                        append("İşlem Türü:${islem.tur}, Miktar: ${islem.miktar}, ${islem.paraBirimi} Tarih: ${islem.tarih}, Açıklama: ${islem.aciklama}")
                    },
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

data class Islem(val miktar: Float, val paraBirimi: String, val tur: String, val tarih: String, val aciklama: String)
