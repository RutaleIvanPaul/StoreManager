package com.kotlin.ivanpaulrutale.storemanager.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.kotlin.ivanpaulrutale.storemanager.models.ReportItem;
import com.kotlin.ivanpaulrutale.storemanager.models.SummarisedArtNoReportItem;
import com.kotlin.ivanpaulrutale.storemanager.models.SummarisedReportItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static androidx.core.content.ContextCompat.checkSelfPermission;

public class PdfManager {

    public static void createPdf(Activity activity, ArrayList<ReportItem> reportItemsList, String fileName) {

        String targetPdf = "/sdcard/" + fileName + ".pdf";

        Document document = new Document();
        BaseColor mColorAccent = new BaseColor(0, 153, 204, 255);
        float mHeadingFontSize = 20.0f;
        float mValueFontSize = 26.0f;

        LineSeparator lineSeparator = new LineSeparator();
        lineSeparator.setLineColor(new BaseColor(0, 0, 0, 68));


        // write the document content

        File filePath;
        filePath = new File(targetPdf);
        try {

            if (isWriteStoragePermissionGranted(activity) && isReadStoragePermissionGranted(activity)) {

                BaseFont urName = BaseFont.createFont(BaseFont.HELVETICA, "UTF-8", BaseFont.EMBEDDED);
                PdfWriter.getInstance(document, new FileOutputStream(filePath));
                document.open();
                document.setPageSize(PageSize.A4);
                document.addCreationDate();
                document.addAuthor("Store Manager");


                // Adding Title....
                Font ReportDetailsTitleFont = new Font(urName, 36.0f, Font.NORMAL, BaseColor.BLACK);
                // Creating Chunk
                Chunk ReportDetailsTitleChunk = new Chunk("Store Manager Report\n\n", ReportDetailsTitleFont);
                ReportDetailsTitleChunk.setUnderline(0.1f, -2f);
                // Creating Paragraph to add...
                Paragraph ReportDetailsTitleParagraph = new Paragraph(ReportDetailsTitleChunk);
                // Setting Alignment for Heading
                ReportDetailsTitleParagraph.setAlignment(Element.ALIGN_CENTER);
                // Finally Adding that Chunk
                document.add(ReportDetailsTitleParagraph);

                document.add(storeManagerReportTable(reportItemsList));

                document.add(new Chunk(lineSeparator));

                Chunk SummarisedReportDetailsTitleChunk = new Chunk("Summarised Report\n\n", ReportDetailsTitleFont);
                SummarisedReportDetailsTitleChunk.setUnderline(0.1f, -2f);
                // Creating Paragraph to add...
                Paragraph SummarisedReportDetailsTitleParagraph = new Paragraph(SummarisedReportDetailsTitleChunk);
                // Setting Alignment for Heading
                SummarisedReportDetailsTitleParagraph.setAlignment(Element.ALIGN_CENTER);
                // Finally Adding that Chunk
                document.add(SummarisedReportDetailsTitleParagraph);

                ArrayList<SummarisedReportItem> summarisedReportList = summariseReport(reportItemsList);

                document.add(summarisedTable(summarisedReportList));


                document.add(new Chunk(lineSeparator));

                Chunk SummarisedArtNoReportDetailsTitleChunk = new Chunk("Summarised Art Numbers\n\n", ReportDetailsTitleFont);
                SummarisedArtNoReportDetailsTitleChunk.setUnderline(0.1f, -2f);
                // Creating Paragraph to add...
                Paragraph SummarisedArtNoReportDetailsTitleParagraph = new Paragraph(SummarisedArtNoReportDetailsTitleChunk);
                // Setting Alignment for Heading
                SummarisedArtNoReportDetailsTitleParagraph.setAlignment(Element.ALIGN_CENTER);
                // Finally Adding that Chunk
                document.add(SummarisedArtNoReportDetailsTitleParagraph);

                ArrayList<SummarisedArtNoReportItem> summarisedArtNumbersReportList = summarisedArtNumbersReport(reportItemsList);

                document.add(summarisedArtNumberTable(summarisedArtNumbersReportList));

                document.close();

                Toast.makeText(activity, "PDF is created!!!", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(activity, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        } catch (DocumentException e) {
            e.printStackTrace();
            Toast.makeText(activity, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();

        openAndSaveGeneratedPDF(activity, targetPdf);

    }

    private static Element summarisedArtNumberTable(ArrayList<SummarisedArtNoReportItem> summarisedArtNumbersReportList) throws DocumentException {
        PdfPTable summarised_art_number_table = new PdfPTable(2);
        summarised_art_number_table.setTotalWidth(new float[]{200, 200});
        summarised_art_number_table.setLockedWidth(true);
        summarised_art_number_table.addCell("Art No.");
        summarised_art_number_table.addCell("Total Quantity");

        for (int x = 0; x < summarisedArtNumbersReportList.size(); x++) {
            SummarisedArtNoReportItem summarisedArtNoReportItem = summarisedArtNumbersReportList.get(x);
            summarised_art_number_table.addCell(summarisedArtNoReportItem.getArt_number());
            summarised_art_number_table.addCell(summarisedArtNoReportItem.getTotal_quantity());

        }

        return summarised_art_number_table;
    }

    private static Element summarisedTable(ArrayList<SummarisedReportItem> summarisedReportList) throws DocumentException {
        PdfPTable summarised_table = new PdfPTable(4);
        summarised_table.setTotalWidth(new float[]{100, 100, 100, 100});
        summarised_table.setLockedWidth(true);
        summarised_table.addCell("Art No.");
        summarised_table.addCell("Color");
        summarised_table.addCell("Description");
        summarised_table.addCell("Total Quantity");

        for (int x = 0; x < summarisedReportList.size(); x++) {
            SummarisedReportItem summarisedReportItem = summarisedReportList.get(x);
            summarised_table.addCell(summarisedReportItem.getArt_number());
            summarised_table.addCell(summarisedReportItem.getColor());
            summarised_table.addCell(summarisedReportItem.getDescription());
            summarised_table.addCell(summarisedReportItem.getTotal_quantity());

        }

        return summarised_table;
    }

    private static Element storeManagerReportTable(ArrayList<ReportItem> reportItemsList) throws DocumentException {
        //Table
        PdfPTable table = new PdfPTable(7);

        table.setTotalWidth(new float[]{80, 60, 100, 60, 60, 80, 60});
        table.setLockedWidth(true);
        table.addCell("Art No.");
        table.addCell("Color");
        table.addCell("Description");
        table.addCell("Quantity");
        table.addCell("Store");
        table.addCell("Checkout-time");
        table.addCell("Collector");

        for (int x = 0; x < reportItemsList.size(); x++) {
            ReportItem item = reportItemsList.get(x);
            table.addCell(item.getArtNumber());
            table.addCell(item.getColor());
            table.addCell(item.getDescription());
            table.addCell(item.getItemQuantity());
            table.addCell(item.getStore());
            table.addCell(item.getCheckoutTime());
            table.addCell(item.getCollector());
        }

        return table;
    }

    private static ArrayList<SummarisedArtNoReportItem> summarisedArtNumbersReport(ArrayList<ReportItem> reportItemsList) {

        ArrayList<String> non_unique_art_numbers = new ArrayList();

        for (ReportItem item : reportItemsList) {
            non_unique_art_numbers.add(item.getArtNumber());
        }

        Set<String> unique_art_numbers = new HashSet<String>(non_unique_art_numbers);

        ArrayList<SummarisedArtNoReportItem> summarisedArtNoReportItemList = new ArrayList();

        for (String art_number : unique_art_numbers) {

            summarisedArtNoReportItemList.add(
                    new SummarisedArtNoReportItem(art_number, "0")
            );
        }

        for (SummarisedArtNoReportItem summarisedArtNoReportItem : summarisedArtNoReportItemList) {
            for (ReportItem reportItem : reportItemsList) {
                if (summarisedArtNoReportItem.getArt_number().equalsIgnoreCase(reportItem.getArtNumber())) {
                    int total_quantity = Integer.parseInt(reportItem.getItemQuantity()) + Integer.parseInt(summarisedArtNoReportItem.getTotal_quantity());
                    summarisedArtNoReportItem.setTotal_quantity(
                            Integer.toString(total_quantity)
                    );
                }
            }
        }

        return summarisedArtNoReportItemList;
    }


    private static ArrayList<SummarisedReportItem> summariseReport(ArrayList<ReportItem> reportItemsList) {
        ArrayList<SummarisedReportItem> summarisedReportItemList = new ArrayList();
        SummarisedReportItem summarisedReportItem_first = new SummarisedReportItem(
                reportItemsList.get(0).getArtNumber(),
                reportItemsList.get(0).getColor(),
                reportItemsList.get(0).getDescription(),
                reportItemsList.get(0).getItemQuantity()
        );
        summarisedReportItemList.add(summarisedReportItem_first);

        for (int x = 1; x < reportItemsList.size(); x++) {
            Boolean match_boolean_art_number = false;
            Boolean match_boolean_color = false;
            Boolean match_boolean_description = false;
            ReportItem item = reportItemsList.get(x);

            for (int y = 0; y < summarisedReportItemList.size(); y++) {
                SummarisedReportItem summarisedReportItem = summarisedReportItemList.get(y);

                if (item.getArtNumber().equalsIgnoreCase(summarisedReportItem.getArt_number())) {
                    match_boolean_art_number = true;
                    if (item.getColor().equalsIgnoreCase(summarisedReportItem.getColor())) {
                        match_boolean_color = true;
                        if (item.getDescription().equalsIgnoreCase(summarisedReportItem.getDescription())) {
                            match_boolean_description = true;
                            int total_quantity = Integer.parseInt(item.getItemQuantity()) + Integer.parseInt(summarisedReportItem.getTotal_quantity());
                            summarisedReportItem.setTotal_quantity(
                                    Integer.toString(total_quantity)
                            );
                        } else {
                            continue;
                        }

                    } else {
                        continue;
                    }

                } else {
                    continue;
                }
            }

            if (match_boolean_art_number == false) {
                summarisedReportItemList.add(new SummarisedReportItem(
                                reportItemsList.get(x).getArtNumber(),
                                reportItemsList.get(x).getColor(),
                                reportItemsList.get(x).getDescription(),
                                reportItemsList.get(x).getItemQuantity()
                        )
                );
                match_boolean_color = true;
                match_boolean_description = true;
            }

            if (match_boolean_color == false) {
                summarisedReportItemList.add(new SummarisedReportItem(
                                reportItemsList.get(x).getArtNumber(),
                                reportItemsList.get(x).getColor(),
                                reportItemsList.get(x).getDescription(),
                                reportItemsList.get(x).getItemQuantity()
                        )
                );
                match_boolean_description = true;
            }

            if (match_boolean_description == false) {
                summarisedReportItemList.add(new SummarisedReportItem(
                                reportItemsList.get(x).getArtNumber(),
                                reportItemsList.get(x).getColor(),
                                reportItemsList.get(x).getDescription(),
                                reportItemsList.get(x).getItemQuantity()
                        )
                );
            }

        }


        return summarisedReportItemList;
    }


    public static void openAndSaveGeneratedPDF(Activity activity, String targetPdf) {
        File file = new File(targetPdf);
        if (file.exists() && isReadStoragePermissionGranted(activity)) {
            Intent intent = new Intent(Intent.ACTION_VIEW);

            Uri uri = FileProvider.getUriForFile(
                    activity,
                    activity.getApplicationContext()
                            .getPackageName() + ".provider", file);
            intent.setDataAndType(uri, "application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            try {
                activity.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(activity, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(activity, "Please Grant Permissions!", Toast.LENGTH_LONG).show();
        }
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    }

    public static boolean isReadStoragePermissionGranted(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted1");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked1");
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted1");
            return true;
        }
    }

    public static boolean isWriteStoragePermissionGranted(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted2");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked2");
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted2");
            return true;
        }
    }

}