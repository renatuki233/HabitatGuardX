
package com.example.habitatguardx.data.scanner

import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.atomic.AtomicBoolean

class QRAnalyzer(
    private val onQRCodeDetected: (String) -> Unit
) : ImageAnalysis.Analyzer {

    private val processing = AtomicBoolean(false)
    private val detected = AtomicBoolean(false)

    private val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
        .build()

    private val scanner = BarcodeScanning.getClient(options)

    override fun analyze(imageProxy: ImageProxy) {

        Log.d("HABITAT_QR", "Fotograma recibido")

        if (detected.get() ||
            !processing.compareAndSet(false, true)
        ) {
            imageProxy.close()
            return
        }

        val mediaImage = imageProxy.image

        if (mediaImage == null) {
            Log.e("HABITAT_QR", "Imagen nula")
            processing.set(false)
            imageProxy.close()
            return
        }

        val image = InputImage.fromMediaImage(
            mediaImage,
            imageProxy.imageInfo.rotationDegrees
        )

        scanner.process(image)
            .addOnSuccessListener { barcodes ->

                Log.d(
                    "HABITAT_QR",
                    "Códigos encontrados: ${barcodes.size}"
                )

                val codigo = barcodes
                    .firstOrNull()
                    ?.rawValue

                if (!codigo.isNullOrBlank() &&
                    detected.compareAndSet(false, true)
                ) {
                    Log.d(
                        "HABITAT_QR",
                        "QR detectado: $codigo"
                    )

                    onQRCodeDetected(codigo)
                }
            }
            .addOnFailureListener { error ->
                Log.e(
                    "HABITAT_QR",
                    "Error de ML Kit",
                    error
                )
            }
            .addOnCompleteListener {
                processing.set(false)
                imageProxy.close()
            }
    }

    fun close() {
        scanner.close()
    }
}
