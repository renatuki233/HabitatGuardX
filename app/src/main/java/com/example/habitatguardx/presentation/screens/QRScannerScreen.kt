
package com.example.habitatguardx.presentation.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.habitatguardx.data.scanner.QRAnalyzer
import java.util.concurrent.Executors

@Composable
fun QRScannerScreen(
    onVolver: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var permisoConcedido by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    var codigoDetectado by remember {
        mutableStateOf<String?>(null)
    }

    val solicitarPermiso = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        permisoConcedido = concedido
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Escáner QR",
            style = MaterialTheme.typography.headlineMedium
        )

        if (permisoConcedido && codigoDetectado == null) {

            val cameraExecutor = remember {
                Executors.newSingleThreadExecutor()
            }

            val analyzer = remember {
                QRAnalyzer { codigo ->
                    codigoDetectado = codigo
                }
            }

            DisposableEffect(Unit) {
                onDispose {
                    analyzer.close()
                    cameraExecutor.shutdown()
                }
            }

            AndroidView(
                factory = { ctx ->
                    val previewView = PreviewView(ctx)

                    val cameraProviderFuture =
                        ProcessCameraProvider.getInstance(ctx)

                    cameraProviderFuture.addListener({

                        val cameraProvider =
                            cameraProviderFuture.get()

                        val preview = Preview.Builder()
                            .build()
                            .also {
                                it.surfaceProvider =
                                    previewView.surfaceProvider
                            }

                        val imageAnalysis =
                            ImageAnalysis.Builder()
                                .setBackpressureStrategy(
                                    ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
                                )
                                .build()
                                .also {
                                    it.setAnalyzer(
                                        cameraExecutor,
                                        analyzer
                                    )
                                }

                        try {
                            cameraProvider.unbindAll()

                            cameraProvider.bindToLifecycle(
                                lifecycleOwner,
                                CameraSelector.DEFAULT_BACK_CAMERA,
                                preview,
                                imageAnalysis
                            )
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }, ContextCompat.getMainExecutor(ctx))

                    previewView
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
            )

            Text("Apunta la cámara hacia un código QR.")

        } else if (!permisoConcedido) {

            Text("Necesitamos permiso para utilizar la cámara.")

            Button(
                onClick = {
                    solicitarPermiso.launch(
                        Manifest.permission.CAMERA
                    )
                }
            ) {
                Text("Permitir cámara")
            }

        } else {

            Text(
                text = "Código QR detectado",
                style = MaterialTheme.typography.titleLarge
            )

            ElevatedCard {
                Text(
                    text = codigoDetectado.orEmpty(),
                    modifier = Modifier.padding(20.dp)
                )
            }
        }

        OutlinedButton(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al Dashboard")
        }
    }
}
