package com.android.smcetransport.app.screens.view_pass.presentation

import android.graphics.Picture
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.allowHardware
import coil3.size.Size
import com.android.smcetransport.app.R
import com.android.smcetransport.app.ui.components.AppButton
import com.android.smcetransport.app.ui.components.AppToolBar
import com.android.smcetransport.app.ui.components.model.TitleDescModel
import com.android.smcetransport.app.ui.theme.theme.theme.mediumFont
import com.android.smcetransport.app.ui.theme.theme.theme.normalFont
import com.android.smcetransport.app.ui.theme.theme.theme.semiBoldFont

@Composable
fun ViewPassScreen(
    modifier: Modifier = Modifier,
    viewPassUiState: ViewPassUiState,
    onBackPressEvent: () -> Unit,
    onPrintPressEvent: (Picture) -> Unit
) {

    val percentRoundedCorner = RoundedCornerShape(5)
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val picture = remember { Picture() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.white))
    ) {
        AppToolBar(
            modifier = Modifier.fillMaxWidth(),
            toolBarText = stringResource(R.string.your_virtual_pass),
            onToolBarStartIconClick = {
                onBackPressEvent()
            },
            isShowLoading = viewPassUiState.isApiLoading
        )
        if (!viewPassUiState.isApiLoading) {
            if (viewPassUiState.titleDescList.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                shape = percentRoundedCorner,
                                color = colorResource(R.color.light_white)
                            )
                            .background(color = colorResource(R.color.white))
                            .clip(percentRoundedCorner)
                            .drawWithCache {
                                val width = this.size.width.toInt()
                                val height = this.size.height.toInt()
                                onDrawWithContent {
                                    val pictureCanvas =
                                        androidx.compose.ui.graphics.Canvas(
                                            picture.beginRecording(
                                                width,
                                                height
                                            )
                                        )
                                    draw(this, this.layoutDirection, pictureCanvas, this.size) {
                                        this@onDrawWithContent.drawContent()
                                    }
                                    picture.endRecording()
                                    drawIntoCanvas { canvas ->
                                        canvas.nativeCanvas.drawPicture(
                                            picture
                                        )
                                    }
                                }
                            }
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {

                            Image(
                                painter = painterResource(R.drawable.app_logo),
                                contentDescription = null,
                                modifier = Modifier.size(50.dp)
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = stringResource(R.string.college_name_text),
                                    textAlign = TextAlign.Center,
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(semiBoldFont),
                                    color = colorResource(R.color.black)
                                )
                                Text(
                                    text = stringResource(R.string.college_address_text),
                                    textAlign = TextAlign.Center,
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily(normalFont),
                                    color = colorResource(R.color.black)
                                )
                            }

                        }

                        Text(
                            text = stringResource(R.string.bus_pass_text),
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            fontFamily = FontFamily(normalFont),
                            modifier = Modifier.fillMaxWidth(),
                            color = colorResource(R.color.black)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                verticalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                viewPassUiState.titleDescList.forEach {
                                    Text(
                                        text = "${it.title}: ${it.descText}",
                                        fontSize = 12.sp,
                                        fontFamily = FontFamily(mediumFont),
                                        modifier = Modifier.fillMaxWidth(),
                                        color = colorResource(R.color.black)
                                    )
                                }
                            }


                            AsyncImage(
                                modifier = Modifier
                                    .width(screenWidth / 4)
                                    .aspectRatio(1f / 1.25f)
                                    .clip(percentRoundedCorner)
                                    .background(
                                        color = colorResource(R.color.app_main_color)
                                            .copy(0.15f)
                                    ),
                                contentDescription = null,
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(viewPassUiState.userImage)
                                    .allowHardware(false)
                                    .build(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {

                            Text(
                                text = stringResource(R.string.signature_of_text),
                                fontSize = 12.sp,
                                fontFamily = FontFamily(mediumFont),
                                color = colorResource(R.color.black)
                            )


                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Image(
                                    painter = painterResource(R.drawable.college_incharge),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1f / 0.35f),
                                    colorFilter = ColorFilter.tint(color = colorResource(R.color.black))
                                )


                                Text(
                                    text = stringResource(R.string.transport_in_charge_text),
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily(mediumFont),
                                    modifier = Modifier.fillMaxWidth(),
                                    color = colorResource(R.color.black),
                                    textAlign = TextAlign.Center
                                )

                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Image(
                                    painter = painterResource(R.drawable.college_principal),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1f / 0.35f),
                                    colorFilter = ColorFilter.tint(color = colorResource(R.color.black))
                                )


                                Text(
                                    text = stringResource(R.string.principal_text),
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily(mediumFont),
                                    modifier = Modifier.fillMaxWidth(),
                                    color = colorResource(R.color.black),
                                    textAlign = TextAlign.Center
                                )

                            }


                        }
                    }

                    AppButton(
                        modifier = Modifier.fillMaxWidth(),
                        buttonText = stringResource(R.string.print_text),
                        isButtonLoading = viewPassUiState.isPrintButtonLoading,
                        buttonClickEvent = {
                            onPrintPressEvent(picture)
                        }
                    )

                    AppButton(
                        modifier = Modifier.fillMaxWidth(),
                        buttonText = stringResource(R.string.home_text),
                        buttonBgColor = colorResource(android.R.color.transparent),
                        buttonStokeBorderColor = colorResource(R.color.app_main_color),
                        buttonTextAndSpinnerColor = colorResource(R.color.app_main_color),
                        buttonClickEvent = {
                            onBackPressEvent()
                        }
                    )

                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    val emptyStateText =
                        viewPassUiState.errorText ?: stringResource(R.string.no_a_pass_desc)
                    Text(
                        text = emptyStateText,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(mediumFont),
                        fontSize = 16.sp,
                        color = colorResource(R.color.black)
                    )
                }
            }
        }
    }

}

@Preview
@Composable
fun PreviewViewPassScreen() {
    ViewPassScreen(
        modifier = Modifier.fillMaxSize(),
        viewPassUiState = ViewPassUiState(
            isApiLoading = false,
            titleDescList = listOf(
                TitleDescModel(
                    id = "1",
                    title = "Bus No",
                    descText = "1"
                ),
                TitleDescModel(
                    id = "2",
                    title = "Name",
                    descText = "Suman Radhakrishnan"
                ),
                TitleDescModel(
                    id = "3",
                    title = "Department",
                    descText = "Mechanic"
                ),
                TitleDescModel(
                    id = "4",
                    title = "College Id",
                    descText = "12345678900987"
                ),
                TitleDescModel(
                    id = "5",
                    title = "From",
                    descText = "Thuckalay"
                ),
                TitleDescModel(
                    id = "6",
                    title = "Via Route",
                    descText = "Pammam, Marthandam"
                ),
                TitleDescModel(
                    id = "7",
                    title = "Amount",
                    descText = "22200"
                )
            ),
            errorText = null
        ),
        onBackPressEvent = {},
        onPrintPressEvent = {}
    )
}