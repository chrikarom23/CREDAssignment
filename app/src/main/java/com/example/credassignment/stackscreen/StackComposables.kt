package com.example.credassignment.stackscreen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.credassignment.R
import com.example.credassignment.network.ItemX

@Composable
fun StackScreen1(
    modifier: Modifier = Modifier,
    footer: String,
    value: Float,
    minValue: Float,
    maxValue: Float,
    header: String,
    description: String,
    onPositionChanged: (Float) -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = header, style = MaterialTheme.typography.titleLarge)
        Text(text = "₹${value.toInt().toString()}", style = MaterialTheme.typography.headlineLarge)
        Slider(
            value = value,
            onValueChange = onPositionChanged,
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.secondary,
                activeTrackColor = MaterialTheme.colorScheme.secondary,
                inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            valueRange = minValue..maxValue
        )
        Text(text = description, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(2.dp))
        Text(footer, style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
fun StackScreen2(modifier: Modifier = Modifier, items: List<ItemX>, updateItems: (String, String)->Unit, checkedItem: String) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LazyRow (contentPadding = PaddingValues(2.dp)){
            items(items){
                item ->
                Card (Modifier.size(200.dp), elevation = CardDefaults.elevatedCardElevation(), colors = CardDefaults.elevatedCardColors()){
                    Column(
                        Modifier
                            .padding(10.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center) {
                        RadioButton(selected = checkedItem==item.emi, onClick = { updateItems(item.emi, item.duration) })
                        Text(text = "₹${item.emi}", style = MaterialTheme.typography.titleLarge)
                        Text(text = item.duration, style = MaterialTheme.typography.titleMedium)
                        Text(text = item.tag, modifier=Modifier.padding(vertical = 6.dp), style = MaterialTheme.typography.labelLarge)
                    }
                }
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedButton(onClick = { /*TODO*/ }, modifier = Modifier
            .align(Alignment.Start)
            .padding(start = 4.dp)) {
            Text(text = "Create your own plan")
        }
    }
}

@Composable
fun StackScreen3(modifier: Modifier = Modifier, bank: String, accountNumber: Long,updateItems: (String)->Unit, checkedItem: String) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row (modifier = Modifier.fillMaxWidth()){
            Image(painter = painterResource(id = R.drawable.sbi_logo), contentDescription = null,
                Modifier
                    .size(55.dp)
                    .padding(end = 4.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(0.9f)) {
                Text(text = bank, style = MaterialTheme.typography.titleLarge)
                Text(text = accountNumber.toString(), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Thin)
            }
            RadioButton(selected = checkedItem==bank, onClick = { updateItems(bank) })
        }
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedButton(onClick = { /*TODO*/ }, modifier = Modifier
            .align(Alignment.Start)
            .padding(start = 4.dp)) {
            Text(text = "Create your own plan")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StackScreen1Preview() {
    var temp by remember {
        mutableFloatStateOf(500f)
    }
    StackScreen1(
        footer = "Stash is instant",
        value = temp,
        minValue = 500f,
        maxValue = 500000f,
        header = "Credit amount",
        description = "This is desc"
    ) { value ->
        temp = value
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun StackScreen2Preview() {
    val vm = StackViewModel()
    StackScreen2(items = vm.emiItems, updateItems = {s1,s2 -> }, checkedItem = vm.emiItems.get(0).emi)
}

@Preview
@Composable
private fun StackScreen3Preview() {
    StackScreen3(bank = "SBI bank", accountNumber = 120387968576, checkedItem = "", updateItems = {s1 -> })
}