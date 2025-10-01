package com.example.lttle_lemon_app.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.lttle_lemon_app.MenuItemRoom
import com.example.lttle_lemon_app.R
import com.example.lttle_lemon_app.components.TopBar
import com.example.lttle_lemon_app.components.homeTopBar
import com.example.lttle_lemon_app.ui.theme.LLColor
import kotlinx.collections.immutable.persistentListOf
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeRoute(
    openDrawer: () -> Unit,
    onNavigateCart: () -> Unit,
    onOpenDish: (Int) -> Unit,
    cartCount: Int,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        cartCount = cartCount,
        openDrawer = openDrawer,
        onNavigateCart = onNavigateCart,
        onSearchChange = viewModel::onSearchChange,
        onCategorySelected = viewModel::onCategoryChange,
        onOpenDish = onOpenDish
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    cartCount: Int,
    openDrawer: () -> Unit,
    onNavigateCart: () -> Unit,
    onSearchChange: (String) -> Unit,
    onCategorySelected: (Category) -> Unit,
    onOpenDish: (Int) -> Unit,
) {
    val openDrawerCb by rememberUpdatedState(openDrawer)
    val onCartCb by rememberUpdatedState(onNavigateCart)
    val onOpenDishCb by rememberUpdatedState(onOpenDish)
    val onSearchCb by rememberUpdatedState(onSearchChange)
    val onCategoryCb by rememberUpdatedState(onCategorySelected)

    val listState = rememberLazyListState()

    val categories = remember {
        listOf(Category.Starters, Category.Mains, Category.Desserts, Category.Drinks, Category.All)
    }

    Scaffold(
        topBar = {
            TopBar(
                state = homeTopBar(cartCount),
                onMenuClick = openDrawerCb,
                onBackClick = {},
                onCartClick = onCartCb
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
        ) {
            UpperPanel(
                searchPhrase = uiState.search,
                onSearchPhraseChange = onSearchCb
            )
            LowerPanel(
                categories = categories,
                selected = uiState.selected,
                onCategorySelected = onCategoryCb,
                menuItems = uiState.menuItems,
                listState = listState,
                onOpenDish = onOpenDishCb
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpperPanel(
    searchPhrase: String,
    onSearchPhraseChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = LLColor.green)
            .padding(12.dp)
    ) {
        HeroHeader()
        Spacer(Modifier.height(12.dp))
        TextField(
            value = searchPhrase,
            onValueChange = onSearchPhraseChange,
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            placeholder = { Text(stringResource(R.string.enter_search_phrase)) },
            singleLine = true,
            shape = shapes.small,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(onSearch = {   focusManager.clearFocus() }),
        )
    }
}

@Composable
private fun HeroHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(Modifier.weight(1f)) {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineLarge
            )
            Row {
                Column {
                    Text(
                        text = stringResource(id = R.string.chicago),
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = stringResource(R.string.description_restaurant),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth(0.6f)
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.hero_image),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .aspectRatio(0.8f)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LowerPanel(
    categories: List<Category>,
    selected: Category,
    onCategorySelected: (Category) -> Unit,
    menuItems: List<MenuItemRoom>,
    listState: LazyListState,
    onOpenDish: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp)
        ) {
            Text(
                text = stringResource(id = R.string.order_for_delivery),
                style = MaterialTheme.typography.titleMedium
            )
            Icon(
                painter = painterResource(id = R.drawable.delivery_van),
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
        }

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(
                items = categories,
                key = { it.ordinal },
                contentType = { "chip" }
            ) { category ->
                val isSelected = category == selected
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        onCategorySelected(category)
                    },
                    label = { Text(category.name) }
                )
            }
        }

        Spacer(Modifier.height(4.dp))

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            items(
                items = menuItems,
                key = { it.id },
                contentType = { "menuItem" }
            ) { menuItem ->
                MenuItemCard(
                    menuItem = menuItem,
                    onItemClicked = { onOpenDish(menuItem.id) }
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterialApi::class)
@Composable
fun MenuItemCard(
    menuItem: MenuItemRoom,
    onItemClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        onClick = onItemClicked
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = menuItem.title,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = menuItem.description,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "$${menuItem.price}",
                    style = MaterialTheme.typography.titleSmall
                )
            }
            GlideImage(
                model = menuItem.image,
                contentDescription = null,
                modifier = Modifier
                    .size(84.dp)
                    .align(Alignment.CenterVertically),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun HomeScreen_Preview() {
    val fakeItems = listOf(
        MenuItemRoom(
            id = 1,
            title = "Greek Salad",
            description = "Crisp lettuce, peppers, olives, feta cheese.",
            price = 12.5,
            image = "https://picsum.photos/200?1",
            category = "Starters"
        ),
        MenuItemRoom(
            id = 2,
            title = "Lemon Dessert",
            description = "Sweet and sour classic with lemon zest.",
            price = 7.0,
            image = "https://picsum.photos/200?2",
            category = "Desserts"
        )
    )

    val ui = HomeUiState(
        search = "",
        selected = Category.Mains,
        menuItems = persistentListOf(
            *fakeItems.toTypedArray()
        ),
        allMenuItems = persistentListOf(
            *fakeItems.toTypedArray()
        )
    )

    MaterialTheme {
        HomeScreen(
            uiState = ui,
            cartCount = 2,
            openDrawer = {},
            onNavigateCart = {},
            onSearchChange = {},
            onCategorySelected = {},
            onOpenDish = {}
        )
    }
}


