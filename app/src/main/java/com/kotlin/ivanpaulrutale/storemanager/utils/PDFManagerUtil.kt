package com.kotlin.ivanpaulrutale.storemanager.utils

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.itextpdf.text.*
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.pdf.draw.LineSeparator
import com.kotlin.ivanpaulrutale.storemanager.models.ReportItem
import com.kotlin.ivanpaulrutale.storemanager.models.SummarisedArtNoReportItem
import com.kotlin.ivanpaulrutale.storemanager.models.SummarisedReportItem
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import androidx.core.content.ContextCompat.checkSelfPermission

/**
 * Created by Derick W on 21,January,2020
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
class PDFManagerUtil {

    companion object {

        const val TAG = "PDFManager"

        fun createPdf(activity: Activity, reportItemsList:List<ReportItem>, fileName:String) {

            val targetPdf = "/sdcard/$fileName.pdf"

            val document = Document()
            val mColorAccent = BaseColor(0, 153, 204, 255)
            val mHeadingFontSize = 20.0f
            val mValueFontSize = 26.0f

            val lineSeparator = LineSeparator()
            lineSeparator.lineColor = BaseColor(0, 0, 0, 68)


            // write the document content

            val filePath: File
            filePath = File(targetPdf)
            try
            {

                if (isWriteStoragePermissionGranted(activity) && isReadStoragePermissionGranted(activity))
                {

                    val urName = BaseFont.createFont(BaseFont.HELVETICA, "UTF-8", BaseFont.EMBEDDED)
                    PdfWriter.getInstance(document, FileOutputStream(filePath))
                    document.open()
                    document.pageSize = PageSize.A4
                    document.addCreationDate()
                    document.addAuthor("Store Manager")


                    // Adding Title....
                    val ReportDetailsTitleFont = Font(urName, 36.0f, Font.NORMAL, BaseColor.BLACK)
                    // Creating Chunk
                    val ReportDetailsTitleChunk = Chunk("Store Manager Report\n\n", ReportDetailsTitleFont)
                    ReportDetailsTitleChunk.setUnderline(0.1f, -2f)
                    // Creating Paragraph to add...
                    val ReportDetailsTitleParagraph = Paragraph(ReportDetailsTitleChunk)
                    // Setting Alignment for Heading
                    ReportDetailsTitleParagraph.alignment = Element.ALIGN_CENTER
                    // Finally Adding that Chunk
                    document.add(ReportDetailsTitleParagraph)

                    document.add(storeManagerReportTable(reportItemsList))

                    document.add(Chunk(lineSeparator))

                    val SummarisedReportDetailsTitleChunk = Chunk("Summarised Report\n\n", ReportDetailsTitleFont)
                    SummarisedReportDetailsTitleChunk.setUnderline(0.1f, -2f)
                    // Creating Paragraph to add...
                    val SummarisedReportDetailsTitleParagraph = Paragraph(SummarisedReportDetailsTitleChunk)
                    // Setting Alignment for Heading
                    SummarisedReportDetailsTitleParagraph.alignment = Element.ALIGN_CENTER
                    // Finally Adding that Chunk
                    document.add(SummarisedReportDetailsTitleParagraph)

                    val summarisedReportList = summariseReport(reportItemsList)

                    document.add(summarisedTable(summarisedReportList))


                    document.add(Chunk(lineSeparator))

                    val SummarisedArtNoReportDetailsTitleChunk = Chunk("Summarised Art Numbers\n\n", ReportDetailsTitleFont)
                    SummarisedArtNoReportDetailsTitleChunk.setUnderline(0.1f, -2f)
                    // Creating Paragraph to add...
                    val SummarisedArtNoReportDetailsTitleParagraph = Paragraph(SummarisedArtNoReportDetailsTitleChunk)
                    // Setting Alignment for Heading
                    SummarisedArtNoReportDetailsTitleParagraph.alignment = Element.ALIGN_CENTER
                    // Finally Adding that Chunk
                    document.add(SummarisedArtNoReportDetailsTitleParagraph)

                    val summarisedArtNumbersReportList = summarisedArtNumbersReport(reportItemsList)

                    document.add(summarisedArtNumberTable(summarisedArtNumbersReportList))

                    document.close()

                    Toast.makeText(activity, "PDF is created!!!", Toast.LENGTH_SHORT).show()
                }

            }
            catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(activity, "Something wrong: $e", Toast.LENGTH_LONG).show()
            }
            catch (e: DocumentException) {
                e.printStackTrace()
                Toast.makeText(activity, "Something wrong: $e", Toast.LENGTH_LONG).show()
            }

            // close the document
            document.close()

            openAndSaveGeneratedPDF(activity, targetPdf)

        }

        @Throws(DocumentException::class)
        private fun summarisedArtNumberTable(summarisedArtNumbersReportList:List<SummarisedArtNoReportItem>): Element {
            val summarised_art_number_table = PdfPTable(2)
            summarised_art_number_table.setTotalWidth(floatArrayOf(200f, 200f))
            summarised_art_number_table.isLockedWidth = true
            summarised_art_number_table.addCell("Art No.")
            summarised_art_number_table.addCell("Total Quantity")

            for (x in summarisedArtNumbersReportList.indices)
            {
                val summarisedArtNoReportItem = summarisedArtNumbersReportList[x]
                summarised_art_number_table.addCell(summarisedArtNoReportItem.art_number)
                summarised_art_number_table.addCell(summarisedArtNoReportItem.total_quantity)

            }

            return summarised_art_number_table
        }

        @Throws(DocumentException::class)
        private fun summarisedTable(summarisedReportList:List<SummarisedReportItem>): Element {
            val summarised_table = PdfPTable(4)
            summarised_table.setTotalWidth(floatArrayOf(100f, 100f, 100f, 100f))
            summarised_table.isLockedWidth = true
            summarised_table.addCell("Art No.")
            summarised_table.addCell("Color")
            summarised_table.addCell("Description")
            summarised_table.addCell("Total Quantity")

            for (x in summarisedReportList.indices)
            {
                val summarisedReportItem = summarisedReportList[x]
                summarised_table.addCell(summarisedReportItem.art_number)
                summarised_table.addCell(summarisedReportItem.color)
                summarised_table.addCell(summarisedReportItem.description)
                summarised_table.addCell(summarisedReportItem.total_quantity)

            }

            return summarised_table
        }


        @Throws(DocumentException::class)
        private fun storeManagerReportTable(reportItemsList:List<ReportItem>): Element {
            //Table
            val table = PdfPTable(7)

            table.setTotalWidth(floatArrayOf(80f, 60f, 100f, 60f, 60f, 80f, 60f))
            table.isLockedWidth = true
            table.addCell("Art No.")
            table.addCell("Color")
            table.addCell("Description")
            table.addCell("Quantity")
            table.addCell("Store")
            table.addCell("Checkout-time")
            table.addCell("Collector")

            for (x in reportItemsList.indices)
            {
                val item = reportItemsList[x]
                table.addCell(item.artNumber)
                table.addCell(item.color)
                table.addCell(item.description)
                table.addCell(item.itemQuantity)
                table.addCell(item.store)
                table.addCell(item.checkoutTime)
                table.addCell(item.collector)
            }

            return table
        }

        private fun summarisedArtNumbersReport(reportItemsList:List<ReportItem>):List<SummarisedArtNoReportItem> {

            val non_unique_art_numbers : MutableList<String> = mutableListOf()

            for (item in reportItemsList)
            {
                non_unique_art_numbers.add(item.artNumber)
            }

            val unique_art_numbers = HashSet<String>(non_unique_art_numbers)

            val summarisedArtNoReportItemList : MutableList<SummarisedArtNoReportItem> = mutableListOf()

            for (art_number in unique_art_numbers)
            {

                summarisedArtNoReportItemList.add(
                    SummarisedArtNoReportItem(art_number, "0")
                )
            }

            for (summarisedArtNoReportItem in summarisedArtNoReportItemList)
            {
                for (reportItem in reportItemsList)
                {
                    if (summarisedArtNoReportItem.art_number.equals(reportItem.artNumber, ignoreCase = true))
                    {
                        val total_quantity = Integer.parseInt(reportItem.itemQuantity) + Integer.parseInt(summarisedArtNoReportItem.total_quantity)
                        summarisedArtNoReportItem.total_quantity = Integer.toString(total_quantity)
                    }
                }
            }

            return summarisedArtNoReportItemList
        }


        private fun summariseReport(reportItemsList:List<ReportItem>):List<SummarisedReportItem> {
            val summarisedReportItemList : MutableList<SummarisedReportItem> = mutableListOf()
            val summarisedReportItem_first = SummarisedReportItem(
                reportItemsList[0].artNumber,
                reportItemsList[0].color,
                reportItemsList[0].description,
                reportItemsList[0].itemQuantity
            )
            summarisedReportItemList.add(summarisedReportItem_first)

            for (x in 1 until reportItemsList.size)
            {
                var match_boolean_art_number:Boolean? = false
                var match_boolean_color:Boolean? = false
                var match_boolean_description:Boolean? = false
                val item = reportItemsList[x]

                for (y in summarisedReportItemList.indices)
                {
                    val summarisedReportItem = summarisedReportItemList.get(y)

                    if (item.artNumber.equals(summarisedReportItem.art_number, ignoreCase = true))
                    {
                        match_boolean_art_number = true
                        if (item.color.equals(summarisedReportItem.color, ignoreCase = true))
                        {
                            match_boolean_color = true
                            if (item.description.equals(summarisedReportItem.description, ignoreCase = true))
                            {
                                match_boolean_description = true
                                val total_quantity = Integer.parseInt(item.itemQuantity) + Integer.parseInt(summarisedReportItem.total_quantity)
                                summarisedReportItem.total_quantity = Integer.toString(total_quantity)
                            }
                            else
                            {
                                continue
                            }

                        }
                        else
                        {
                            continue
                        }

                    }
                    else
                    {
                        continue
                    }
                }

                if (match_boolean_art_number == false)
                {
                    summarisedReportItemList.add(
                        SummarisedReportItem(
                            reportItemsList[x].artNumber,
                            reportItemsList[x].color,
                            reportItemsList[x].description,
                            reportItemsList[x].itemQuantity
                        )
                    )
                    match_boolean_color = true
                    match_boolean_description = true
                }

                if (match_boolean_color == false)
                {
                    summarisedReportItemList.add(
                        SummarisedReportItem(
                            reportItemsList[x].artNumber,
                            reportItemsList[x].color,
                            reportItemsList[x].description,
                            reportItemsList[x].itemQuantity
                        )
                    )
                    match_boolean_description = true
                }

                if (match_boolean_description == false)
                {
                    summarisedReportItemList.add(
                        SummarisedReportItem(
                            reportItemsList[x].artNumber,
                            reportItemsList[x].color,
                            reportItemsList[x].description,
                            reportItemsList[x].itemQuantity
                        )
                    )
                }

            }


            return summarisedReportItemList
        }


        fun openAndSaveGeneratedPDF(activity: Activity, targetPdf:String) {
            val file = File(targetPdf)
            if (file.exists() && isReadStoragePermissionGranted(activity))
            {
                val intent = Intent(Intent.ACTION_VIEW)

                val uri = FileProvider.getUriForFile(
                    activity,
                    activity.applicationContext
                        .packageName + ".provider", file)
                intent.setDataAndType(uri, "application/pdf")
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

                try
                {
                    activity.startActivity(intent)
                }
                catch (e: ActivityNotFoundException) {
                    Toast.makeText(activity, "No Application available to view pdf", Toast.LENGTH_LONG).show()
                }

            }
            else
            {
                Toast.makeText(activity, "Please Grant Permissions!", Toast.LENGTH_LONG).show()
            }
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        }

        fun isReadStoragePermissionGranted(activity: Activity):Boolean {
            if (Build.VERSION.SDK_INT >= 23)
            {
                return if ((checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                    Log.v(TAG, "Permission is granted1")
                    true
                } else {

                    Log.v(TAG, "Permission is revoked1")
                    ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 3)
                    false
                }
            }
            else
            { //permission is automatically granted on sdk<23 upon installation
                Log.v(TAG, "Permission is granted1")
                return true
            }
        }

        fun isWriteStoragePermissionGranted(activity: Activity):Boolean {
            if (Build.VERSION.SDK_INT >= 23)
            {
                return if ((checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                    Log.v(TAG, "Permission is granted2")
                    true
                } else {

                    Log.v(TAG, "Permission is revoked2")
                    ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 2)
                    false
                }
            }
            else
            { //permission is automatically granted on sdk<23 upon installation
                Log.v(TAG, "Permission is granted2")
                return true
            }
        }

    }

}
