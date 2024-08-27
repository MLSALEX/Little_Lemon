package com.example.lttle_lemon_app

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.lttle_lemon_app.components.Header
import com.example.lttle_lemon_app.ui.theme.LLColor

@Composable
fun Home(navController: NavHostController, database: AppDatabase) {
    var searchPhrase by rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    // General state for categories and selected category
    val categories = listOf("Starters", "Mains", "Desserts", "Drinks", "Remove Filter")
    var selectedCategory by rememberSaveable { mutableStateOf("") }

    // Fetch data from the database
    val databaseMenuItems by database.menuItemDao().getAll().observeAsState(emptyList())
    val menuItems = filterMenuItems(databaseMenuItems, searchPhrase, selectedCategory)

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
        UpperPanel(searchPhrase, onSearchPhraseChange = { searchPhrase = it }, focusManager)
        LowerPanel(
            categories = categories,
            onCategorySelected = { selectedCategory = it },
            menuItems = menuItems
        )
    }
}

@Composable
fun UpperPanel(
    searchPhrase: String,
    onSearchPhraseChange: (String) -> Unit,
    focusManager: FocusManager
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = LLColor.green)
            .padding(8.dp)
    ) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.little_lemon),
                style = MaterialTheme.typography.headlineLarge
            )

            Row {
                Column() {
                    Text(
                        text = stringResource(id = R.string.chicago),
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = stringResource(R.string.description_restaurant),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.width(240.dp)
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.hero_image),
                    contentDescription = "",
                    modifier = Modifier
                        .aspectRatio(0.9f)
                        .clip(shapes.medium),
                    contentScale = ContentScale.FillBounds
                )
            }
            TextField(
                value = searchPhrase,
                onValueChange = onSearchPhraseChange,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                },
                shape = shapes.small,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = stringResource(id = R.string.enter_search_phrase)) },
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                )
            )
        }
    }
}

@Composable
fun LowerPanel(
    categories: List<String>,
    onCategorySelected: (String) -> Unit,
    menuItems: List<MenuItemRoom>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
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
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(items = categories) { category ->
                OutlinedButton(
                    onClick = {
                        onCategorySelected(if (category == "Remove Filter") "" else category)
                    },
                    shape = shapes.medium
                ) {
                    Text(text = category, fontWeight = FontWeight.Bold)
                }
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(menuItems) { menuItem ->
                MenuItemCard(menuItem = menuItem)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItemCard(menuItem: MenuItemRoom) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = menuItem.title,
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = menuItem.description,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "$${menuItem.price.toString()}",
                    style = MaterialTheme.typography.labelLarge
                )
            }
            GlideImage(
                model = menuItem.image,
                contentDescription = "Menu dish",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(0.3f)
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
        }
    }
}

fun filterMenuItems(
    menuItems: List<MenuItemRoom>,
    searchPhrase: String,
    selectedCategory: String
): List<MenuItemRoom> {
    return if (searchPhrase.isNotEmpty()) {
        menuItems.filter { it.title.contains(searchPhrase, ignoreCase = true) }
    } else if (selectedCategory.isNotEmpty()) {
        menuItems.filter { it.category.equals(selectedCategory, ignoreCase = true) }
    } else {
        menuItems
    }
}
