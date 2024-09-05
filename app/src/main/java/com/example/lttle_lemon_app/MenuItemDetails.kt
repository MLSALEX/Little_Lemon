package com.example.lttle_lemon_app

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.lttle_lemon_app.components.TopAppBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MenuItemDetails(
    navController: NavHostController,
    id: Int,
    menuItemDao: MenuItemDao,
    cartViewModel: CartViewModel = viewModel(),
    openDrawer:() -> Unit
) {
    val menuItems by menuItemDao.getAll().observeAsState(initial = emptyList())

    val dish = menuItems.find { it.id == id }

    if (dish == null) {
        Text("Menu item not found", style = MaterialTheme.typography.labelMedium)
        return
    }
    var quantity by rememberSaveable { mutableIntStateOf(1) }

    var isAnimating by remember { mutableStateOf(false) }
    var showBadge by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(targetValue = if (isAnimating) 1.5f else 1f, label = "")
    val badgeOffset by animateDpAsState(targetValue = if (showBadge) 0.dp else (-20).dp)

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        TopAppBar(
            navController = navController,
            showCart = true,
            cartScale = scale,
            showBadge = showBadge,
            badgeOffset = badgeOffset,
            openDrawer = openDrawer
        )
        GlideImage(
            model = dish.image,
            contentDescription = "Dish image",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .weight(1f)
                .padding(start = 6.dp, end = 6.dp)
        ) {
            Text(
                text = dish.title,
                style = MaterialTheme.typography.displayMedium
            )
            Text(
                text = dish.description,
                style = MaterialTheme.typography.bodyLarge
            )
            Counter(onQuantityChange = { newQuantity -> quantity = newQuantity })
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    cartViewModel.addItemToCart(dish, quantity)
                    isAnimating = true
                    showBadge = true
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.add_for) + " $${dish.price}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
    LaunchedEffect(isAnimating) {
        if (isAnimating) {
            delay(300)
            isAnimating = false
            showBadge = false
        }
    }
}

@Composable
fun Counter(onQuantityChange: (Int) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        var counter by rememberSaveable {
            mutableIntStateOf(1)
        }
        TextButton(
            onClick = {
                if (counter > 1) {
                    counter--
                    onQuantityChange(counter)
                }
            }
        ) {
            Text(
                text = "-",
                style = MaterialTheme.typography.displaySmall
            )
        }
        Text(
            text = counter.toString(),
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(16.dp)
        )
        TextButton(
            onClick = {
                counter++
                onQuantityChange(counter)
            }
        ) {
            Text(
                text = "+",
                style = MaterialTheme.typography.displaySmall
            )
        }
    }
}