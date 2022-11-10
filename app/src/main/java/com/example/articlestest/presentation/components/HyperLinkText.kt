package com.example.articlestest.presentation.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import com.example.articlestest.presentation.theme.HyperLinkBlue


@Composable
fun HyperlinkText(
    modifier: Modifier,
    fullText: String,
    linkText: List<String>,
    boldText: String? = null,
    linkTextColor: Color = HyperLinkBlue,
    hyperlinks: List<String>,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontFamily: FontFamily,
    textAlign: TextAlign
) {
    val annotatedString = buildAnnotatedString {

        append(fullText)
        if (hyperlinks != null) {
            linkText.forEachIndexed { index, link ->
                val startIndex = fullText.indexOf(link)
                val endIndex = startIndex + link.length
                addStyle(
                    style = SpanStyle(
                        color = linkTextColor,
                        fontSize = fontSize,
                        fontFamily = fontFamily
                    ),
                    start = startIndex,
                    end = endIndex
                )
                addStringAnnotation(
                    tag = "URL",
                    annotation = hyperlinks[index],
                    start = startIndex,
                    end = endIndex
                )
            }
        }

        addStyle(
            style = SpanStyle(
                fontSize = fontSize,
                fontFamily = fontFamily
            ),
            start = 0,
            end = fullText.length
        )

        if (boldText != null) {
            fullText.forEachIndexed { _, _ ->
                val textStart = fullText.indexOf("«")
                val textEnd = fullText.indexOf("вы")

                addStyle(
                    style = SpanStyle(fontWeight = FontWeight.SemiBold),
                    start = textStart,
                    end = textEnd
                )
            }
        }
    }


    val uriHandler = LocalUriHandler.current

    ClickableText(
        modifier = modifier,
        text = annotatedString,
        style = TextStyle(textAlign = textAlign),
        onClick = {
            annotatedString
                .getStringAnnotations("URL", it, it)
                .firstOrNull()?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                }
        }
    )
}