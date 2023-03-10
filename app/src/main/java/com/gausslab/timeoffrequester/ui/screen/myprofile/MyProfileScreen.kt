package com.gausslab.timeoffrequester.ui.screen.myprofile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import coil.compose.SubcomposeAsyncImage
import com.baec23.ludwig.component.section.DisplaySection

const val myProfileScreenRoute = "myProfile_screen_route"

fun NavGraphBuilder.myProfileScreen() {
    composable(route = myProfileScreenRoute) {
        MyProfileScreen()
    }
}

fun NavController.navigateToMyProfileScreen(navOptions: NavOptions? = null) {
    this.navigate(route = myProfileScreenRoute, navOptions = navOptions)
}

@Composable
fun MyProfileScreen(
    viewModel: MyProfileViewModel = hiltViewModel()
) {

    val currUser = viewModel.currUser

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(modifier = Modifier) {
            DisplaySection(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp),
                headerText = "??? ??????"
            ) {
                Column() {
                    UserInfoCard(
                        userName = currUser.username,
                        userImageUrl = "https://picsum.photos/1200",
                        subText = "?????? ?????? ??? : ${currUser.remainingTimeOffRequests}",
                        subTextColor = Color.DarkGray,
                        onCardClick = { viewModel.onEvent(it) }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { viewModel.onEvent(MyProfileUiEvent.ChangePasswordButtonPressed) }
                    ) {
                        Text(text = "???????????? ??????")
                    }
                }

            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfoCard(
    modifier: Modifier = Modifier,
    userName: String,
    userImageUrl: String,
    subText: String? = null,
    cardHeight: Dp = 150.dp,
    cardElevation: Dp = 6.dp,
    cardBackgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
    cardBorderRadius: Dp = 6.dp,
    textColor: Color = Color.Unspecified,
    subTextColor: Color = Color.Unspecified,
    profileImageClipShape: Shape = CircleShape,
    onProfileImageClick: (() -> Unit)? = null,
    onEditDetailsClick: (() -> Unit)? = null,
    onCardClick: (MyProfileUiEvent) -> Unit,
) {
    Card(
        modifier = modifier
            .height(cardHeight),
        elevation = CardDefaults.cardElevation(defaultElevation = cardElevation),
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor),
        shape = RoundedCornerShape(cardBorderRadius),
        onClick = { onCardClick(MyProfileUiEvent.MyProfileDetailsPressed) }
    ) {
        Row(
            modifier = Modifier
                .padding(start = 0.dp, top = 8.dp, bottom = 8.dp, end = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .fillMaxHeight()
                    .run {
                        onProfileImageClick?.let { this.clickable { onProfileImageClick() } }
                            ?: this
                    }
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .clip(profileImageClipShape)
                        .fillMaxWidth(0.7f)
                        .align(Alignment.Center),
                    model = userImageUrl,
                    contentScale = ContentScale.Inside,
                    loading = {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                            }
                        }
                    },
                    contentDescription = userName
                )
            }
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxSize()
                    .padding(top = 24.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Box(modifier = Modifier) {
                    Column(modifier = Modifier) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "$userName ???",
                            color = textColor,
                            fontSize = 24.sp,
                        )
                        subText?.let {
                            Text(
                                text = subText,
                                color = subTextColor,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
                onEditDetailsClick?.let {
                    Text(
                        modifier = Modifier
                            .clickable { onEditDetailsClick() }
                            .align(Alignment.End),
                        text = ">> ??? ?????? ????????????",
                        color = textColor
                    )
                }
            }
        }
    }
}