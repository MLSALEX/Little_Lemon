package com.example.lttle_lemon_app

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lttle_lemon_app.components.Header
import com.example.lttle_lemon_app.ui.theme.LLColor

@Composable
fun Home(navController: NavHostController) {

    var searchPhrase by rememberSaveable {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Header {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "profile image",
                modifier = Modifier
                    .width(50.dp)
                    .clickable { navController.navigate(Profile.route) }
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = LLColor.green)
                .padding(20.dp)
        ) {
            Column(
                Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.little_lemon),
                    style = MaterialTheme.typography.headlineLarge
                )
                Row {
                    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                        Text(
                            text = stringResource(id = R.string.chicago),
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Text(
                            text = stringResource(R.string.description_restaurant),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.width(230.dp)
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.hero_image),
                        contentDescription = "",
                        modifier = Modifier
                            .aspectRatio(0.8f)
                            .clip(shapes.medium),
                        contentScale = ContentScale.FillBounds
                    )
                }
                TextField(value = searchPhrase,
                    onValueChange = { searchPhrase = it },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon"
                        )
                    },
                    shape = shapes.small,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp),
                    placeholder = { Text(text = stringResource(id = R.string.enter_search_phrase)) }
                )
            }
        }
        LowerPanel()
    }
}

@Composable
fun LowerPanel() {

    val categories = mutableListOf(
        "Starters", "Mains", "Desserts", "Drinks", "Remove Filter"
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)

    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(80.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            Text(
                text = stringResource(id = R.string.order_for_delivery),
                style = MaterialTheme.typography.bodyLarge
            )
            Icon(
                painter = painterResource(id = R.drawable.delivery_van),
                contentDescription = "",
                modifier = Modifier.size(60.dp)
            )
        }

        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(
                items = categories,
                itemContent = { category ->
                    Row {
                        OutlinedButton(
                            onClick = {
//                            selectCategory = category.lowercase()
//                            if (category == "Remove Filter"){
//                                selectCategory
//                            }
                            }, shape = shapes.medium
                        ) {
                            Text(text = category, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            )
        }
        Divider(Modifier.padding(vertical = 10.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            items(MenuItemList) { menuItem ->
                MenuItemCard(menuItem)
                Divider()
            }
        }
    }
}

@Composable
fun MenuItemCard(menuItem: MenuItem) {
    Card {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column {
                Text(
                    text = menuItem.title,
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = menuItem.description,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.width(250.dp)
                )
                Text(
                    text = menuItem.price,
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 16.sp
                )
            }
            Image(
                painter = painterResource(id = R.drawable.greek_salad),
                contentDescription = "",
                Modifier.height(100.dp),
                contentScale = ContentScale.Crop
            )

        }
    }
}

data class MenuItem(
    val title: String,
    val description: String,
    val price: String,
    val category: String,
    @DrawableRes val image: Int
)

val MenuItemList = listOf(
    MenuItem(
        "Greek Salad",
        "The famous greek salad of crispy lettuce, peppers, olives and our Chicago...",
        "$12.99",
        "Starters",
        R.drawable.greek_salad
    ),
    MenuItem(
        "Greek Salad",
        "The famous greek salad of crispy lettuce, peppers, olives and our Chicago...",
        "$12.99",
        "Starters",
        R.drawable.greek_salad
    ),
    MenuItem(
        "Greek Salad",
        "The famous greek salad of crispy lettuce, peppers, olives and our Chicago...",
        "$12.99",
        "Starters",
        R.drawable.greek_salad
    ),
    MenuItem(
        "Greek Salad",
        "The famous greek salad of crispy lettuce, peppers, olives and our Chicago...",
        "$12.99",
        "Starters",
        R.drawable.greek_salad
    ),
    MenuItem(
        "Lemon Dessert",
        "This comes straight from grandma recipe book, every last ingredient has...",
        "$9.99",
        "Dessert",
        R.drawable.lemon_dessert
    ),
    MenuItem(
        "Lemon Dessert",
        "This comes straight from grandma recipe book, every last ingredient has...",
        "$9.99",
        "Dessert",
        R.drawable.lemon_dessert
    )
)