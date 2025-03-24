package com.android.smcetransport.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.smcetransport.app.R
import com.android.smcetransport.app.ui.theme.theme.theme.mediumFont

@Composable
fun DepartmentAndYearFilter(
    modifier: Modifier = Modifier,
    selectedDepartment : String,
    selectedYearText : String?,
    departmentClick : (Boolean) -> Unit,
    yearClick : (Boolean) -> Unit,
    applyFilterClick : () -> Unit,
) {

    val fivePercentageRoundShape = RoundedCornerShape(5)

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.app_main_color),
                    shape = fivePercentageRoundShape
                )
                .clip(fivePercentageRoundShape)
                .clickable {
                    departmentClick(false)
                }
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            val departmentTextValue = selectedDepartment.ifEmpty {
                stringResource(R.string.select_department_text)
            }
            val departmentTextColor = if (selectedDepartment.isNotEmpty()) {
                colorResource(R.color.app_main_color)
            } else {
                colorResource(R.color.gray_dark)
            }

            Text(
                text = departmentTextValue,
                color = departmentTextColor,
                fontFamily = FontFamily(mediumFont),
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth().weight(1f),
                textAlign = TextAlign.Center
            )

            if (selectedDepartment.isNotEmpty()) {
                Icon(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp).clickable {
                        departmentClick(true)
                    },
                    tint = departmentTextColor
                )
            }

        }

        if (selectedYearText != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .border(
                        width = 1.dp,
                        color = colorResource(R.color.app_main_color),
                        shape = fivePercentageRoundShape
                    )
                    .clip(fivePercentageRoundShape)
                    .clickable {
                        yearClick(false)
                    }
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                val yearTextValue = selectedYearText.ifEmpty {
                    stringResource(R.string.select_year_text)
                }
                val yearTextColor = if (selectedYearText.isNotEmpty()) {
                    colorResource(R.color.app_main_color)
                } else {
                    colorResource(R.color.gray_dark)
                }

                Text(
                    text = yearTextValue,
                    color = yearTextColor,
                    fontFamily = FontFamily(mediumFont),
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    textAlign = TextAlign.Center
                )

                if (selectedYearText.isNotEmpty()) {
                    Icon(
                        painter = painterResource(R.drawable.ic_close),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp).clickable {
                            yearClick(true)
                        },
                        tint = yearTextColor
                    )
                }

            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(fivePercentageRoundShape)
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.app_main_color),
                    shape = fivePercentageRoundShape
                )
                .background(colorResource(R.color.app_main_color))
                .clip(fivePercentageRoundShape)
                .clickable {
                    applyFilterClick()
                }
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = stringResource(R.string.apply_filter_text),
                color = colorResource(android.R.color.white),
                fontFamily = FontFamily(mediumFont),
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

        }

    }
}


@Preview
@Composable
fun PreviewDepartmentAndYearFilter() {
    DepartmentAndYearFilter(
        modifier = Modifier.fillMaxWidth(),
        selectedDepartment = "",
        selectedYearText = null,
        departmentClick = {

        },
        yearClick = {

        },
        applyFilterClick = {

        }
    )
}