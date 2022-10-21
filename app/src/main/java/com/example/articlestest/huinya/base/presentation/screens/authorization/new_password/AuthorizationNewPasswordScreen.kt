package com.example.articlestest.huinya.base.presentation.screens.authorization.new_password


//@OptIn(ExperimentalComposeUiApi::class)
//@Composable
//fun AuthorizationNewPasswordScreen(
//    onAuthPasswordContinueClick: () -> Unit
//) {
//
//    val context = LocalContext.current
//
//    var password = remember { mutableStateOf("") } //testtest
//    val keyboardController = LocalSoftwareKeyboardController.current
//
////    val uiState by viewModel.uiState.collectAsState(BaseViewState.Loading)
//
////    when (uiState) {
////        is BaseViewState.Data -> {
////            navController.graph.clear()
////            context.launchActivity<MainActivity> { }
////        }
////        BaseViewState.Empty -> {}
////        is BaseViewState.Error -> {}
////        BaseViewState.Loading -> {}
////    }
//
////    Log.d("asdasdasd", uiState.toString())
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(PaddingValues(start = 20.dp, end = 20.dp, top = 35.dp, bottom = 24.dp)),
//        verticalArrangement = Arrangement.SpaceBetween
//    ) {
//        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//            Log.d("asdasd", "password_screen")
//
////            LogoAndBack(navController)
//
//            Text(
//                text = stringResource(id = R.string.enter_password),
//                fontFamily = FontFamily(Font(R.font.gilroy_medium)),
//            )
//
//            Text(
//                text = stringResource(id = R.string.password),
//                fontFamily = FontFamily(Font(R.font.gilroy_medium)),
//                modifier = Modifier.padding(PaddingValues(top = 35.dp, bottom = 8.dp))
//            )
//
//            OutlinedTextField(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(56.dp),
//                shape = RoundedCornerShape(8.dp),
//                colors = TextFieldDefaults.textFieldColors(
//                    textColor = Grey900,
//                    backgroundColor = Color.White,
//                    focusedIndicatorColor = Grey300,
//                    unfocusedIndicatorColor = Grey300,
//                    cursorColor = Color.Black
//                ),
//                keyboardOptions = KeyboardOptions.Default.copy(
//                    keyboardType = KeyboardType.Text,
//                    imeAction = ImeAction.Done
//                ),
//                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
//                value = password.value,
//                textStyle = LocalTextStyle.current.copy(
//                    fontFamily = FontFamily(Font(R.font.gilroy_regular)),
//                    fontSize = 20.sp,
//                ),
//                onValueChange = { password.value = it }
//            )
//
////            if (uiState is BaseViewState.Error) {
////                Log.d("asd", "BaseViewState.Error")
////                IncorrectPassword()
////            }
//        }
//
//
//        Column(
//            modifier = Modifier.weight(1f, false),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//
//        ) {
//            Button(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(44.dp),
//                shape = RoundedCornerShape(37.dp),
//                colors = ButtonDefaults.buttonColors(backgroundColor = Pink),
//                onClick = onAuthPasswordContinueClick
////                {
////                    viewModel.onTriggerEvent(
////                        eventType = AuthorizationPasswordContract.Event(
////                            username = "89279442890",
////                            password = "testtest"
////                        )// testtest
////                    )
////                }
//            ) {
//                Text(
//                    text = stringResource(id = R.string.continue_button),
//                    fontFamily = FontFamily(Font(R.font.gilroy_semibold)),
//                    fontSize = 17.sp,
//                    color = Color.White
//                )
//            }
//        }
//    }
//}
