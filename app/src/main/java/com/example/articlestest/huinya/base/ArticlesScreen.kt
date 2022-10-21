package com.example.articlestest.huinya.base

//import com.example.articlestest.huinya.base.presentation.navigation.NavItem


//@Composable
//fun ArticlesScreen(
//    navController: NavHostController
//) {
//    val articles = remember { }
//
//    LazyColumn(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        contentPadding = PaddingValues(start = 20.dp, end = 20.dp)
//    ) {
//
//        item {
//            Spacer(modifier = Modifier.height(50.dp))
//            Image(
//                painter = painterResource(id = R.drawable.ic_logo_small),
//                contentDescription = null,
//            )
//            Spacer(modifier = Modifier.height(9.dp))
//        }
//
//        items(articles) {
//            Article(it, onClick = {
//                navController.navigate(NavItem.ArticleDetails.screen_route + "/${it.title}/${it.imageId}")
//            })
//        }
//    }
//}
//
//@Composable
//fun Article(
//    article: Article,
//    onClick: () -> Unit
//) {
//    ConstraintLayout(
//        modifier = Modifier
//            .fillMaxSize()
//            .clickable(
//                interactionSource = remember { MutableInteractionSource() },
//                indication = null
//            ) { onClick() })
//    {
//        val (image, title, date, more) = createRefs()
//        Image(
//            painter = painterResource(id = article.imageId),
//            contentDescription = null,
//            modifier = Modifier
//                .constrainAs(image) {
//                    top.linkTo(parent.top, margin = 24.dp)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                }
//                .height(167.dp)
//                .fillMaxWidth()
//                .clip(RoundedCornerShape(28.dp)),
//            contentScale = ContentScale.Crop
//        )
//
//        Text(
//            text = article.title.uppercase(),
//            color = DarkGrey,
//            fontSize = 20.sp,
//            fontFamily = FontFamily(Font(R.font.foglihten_no_regular)),
//            fontWeight = FontWeight.Light,
//
//            modifier = Modifier
//                .constrainAs(title) {
//                    top.linkTo(image.bottom, margin = 12.dp)
//                    start.linkTo(parent.start)
//                }
//        )
//        Text(
//            text = article.date,
//            color = DarkGrey,
//            fontSize = 14.sp,
//            fontFamily = FontFamily(Font(R.font.gilroy_light)),
//            modifier = Modifier.constrainAs(date) {
//                top.linkTo(title.bottom, margin = 5.dp)
//                start.linkTo(parent.start)
//            })
//        Text(
//            text = stringResource(R.string.more_button),
//            color = Pink,
//            fontSize = 14.sp,
//            fontFamily = FontFamily(Font(R.font.gilroy_semibold)),
//            modifier = Modifier.constrainAs(more) {
//                top.linkTo(title.bottom, margin = 5.dp)
//                end.linkTo(parent.end)
//            })
//    }
//}