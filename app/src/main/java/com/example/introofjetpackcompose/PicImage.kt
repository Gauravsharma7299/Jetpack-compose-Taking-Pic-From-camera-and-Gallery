package com.example.introofjetpackcompose


import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder

import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp


@Composable
fun PicImageFromGallery(){
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    val launcher= rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()){
        imageUri=it

    }

    
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxSize()
        .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.End
    ) {
        Button( onClick = {
            launcher.launch("image/*")


        }) {
            Text(text = "Take pic from Gallery")
        }
       Spacer(modifier = Modifier.height(20.dp))

        imageUri?.let {
            bitmap=if (Build.VERSION.SDK_INT<28){
                MediaStore.Images.Media.getBitmap(context.contentResolver,it)
            }else{
                val source=ImageDecoder.createSource(context.contentResolver,it)
                ImageDecoder.decodeBitmap(source)
            }
            Image(bitmap = bitmap?.asImageBitmap()!!, contentDescription = "", modifier = Modifier.size(200.dp)
                )

        }
        
    }


}

@Composable
fun PicImagesFromCamera(){

    var bitmap by remember { mutableStateOf<Bitmap?>(null)}

    val launcher= rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()){
           bitmap=it
    }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 20.dp) ,
                horizontalAlignment = Alignment.Start
        ) {
            Button(
                onClick = {
                launcher.launch()
            }) {

                Text(text = "Take Pic from Camera")
            }
            Spacer(modifier = Modifier.height(20.dp))
            bitmap?.let {
                Image(bitmap = bitmap?.asImageBitmap()!! , contentDescription = "", modifier = Modifier.size(200.dp))

            }

        }
    }

