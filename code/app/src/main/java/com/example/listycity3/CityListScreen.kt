package com.example.listycity3

import androidx.annotation.Nullable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.FloatingActionButton
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.foundation.clickable
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity3.ui.theme.ListyCity3Theme

@Composable
fun CityListScreen(
    cities: List<City>,
    onAddCity: (City) -> Unit,
    onUpdateCity: (Int,City) -> Unit,
    modifier: Modifier = Modifier
) {
    var showUpdateCityFields by remember { mutableStateOf(false) }
    var newCityName by remember { mutableStateOf("") }
    var newProvinceName by remember { mutableStateOf("")}
    var showAddCityFields by remember{ mutableStateOf(false)}
    var selectedCity by remember { mutableStateOf<Int?>(null) }
    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ){
            FloatingActionButton(
                modifier = Modifier.padding(25.dp),
                onClick = {showAddCityFields = !showAddCityFields}
            ) {
                Text("+")
            }
        }
        if(showUpdateCityFields){
            Row(modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)){

                OutlinedTextField(
                    value=newCityName,
                    onValueChange = {newCityName=it},
                    label = { Text("City Name")},
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value=newProvinceName,
                    onValueChange = {newProvinceName=it},
                    label = { Text("Province")},
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    modifier = Modifier.padding(vertical = 12.dp),
                    onClick = {
                        var index =  selectedCity
                        if(index!=null){
                            onUpdateCity(
                                index, City(
                                    name = newCityName,
                                    province = newProvinceName)
                            )
                        }
                        newCityName=""
                        newProvinceName=""
                        showUpdateCityFields=false
                    }
                ){
                    Text("Update City") }
            }


            }
        if(showAddCityFields){
            Row(modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)){

                OutlinedTextField(
                    value=newCityName,
                    onValueChange = {newCityName=it},
                    label = { Text("City Name")},
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value=newProvinceName,
                    onValueChange = {newProvinceName=it},
                    label = { Text("Province")},
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    modifier = Modifier.padding(vertical = 12.dp),
                    onClick = {
                        if(newCityName.isNotBlank() && newProvinceName.isNotBlank()) {
                            onAddCity(
                                City(
                                    name = newCityName,
                                    province = newProvinceName
                                )
                            )
                            newCityName=""
                            newProvinceName=""
                            showAddCityFields=false
                        }
                    }
                ){
                    Text("Add City")
                }
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(cities) { index, city ->
                CityRow(city = city,onClick={
                    selectedCity=index
                    newCityName=city.name
                    newProvinceName=city.province
                    showUpdateCityFields = true
                }
                )
                if (index < cities.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun CityRow(city: City,onClick:()->Unit) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = city.name,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = city.province,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CityListScreenPreview() {
    ListyCity3Theme {
        CityListScreen(
            cities = listOf(
                City("Edmonton", "AB"),
                City("Vancouver", "BC"),
                City("Calgary", "AB")
            ),
            onAddCity = {},
            onUpdateCity = {} as (Int, City) -> Unit
        )
    }
}