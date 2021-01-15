package codev19.utils;

import codev19.Main;
import codev19.codev19Controller;
import codev19.model.Analyze;
import javafx.beans.binding.Bindings;
import javafx.print.*;
import javafx.scene.control.TableView;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public class myPrint {
    Printer printer;
    PageLayout pageLayout;
    PrinterJob job;

    public myPrint(){
        printer = Printer.getDefaultPrinter();
        pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
        job = PrinterJob.createPrinterJob();
    }

    public boolean doPrint(TableView<Analyze> table){
        TableView<Analyze> tblevMain;
        tblevMain = table;

        if(job.showPrintDialog(tblevMain.getScene().getWindow())){
            double pagePrintableWidth = pageLayout.getPrintableWidth();
            double pagePrintableHeight = pageLayout.getPrintableHeight();

            tblevMain.prefHeightProperty().bind(Bindings.size(tblevMain.getItems()).multiply(35));
            tblevMain.minHeightProperty().bind(tblevMain.prefHeightProperty());
            tblevMain.maxHeightProperty().bind(tblevMain.prefHeightProperty());

            double scaleX = pagePrintableWidth / tblevMain.getBoundsInParent().getWidth();
            double scaleY = scaleX;
            double localScale = scaleX;


            double numberOfPages = Math.ceil((tblevMain.getPrefHeight() * localScale) / pagePrintableHeight);

            tblevMain.getTransforms().add(new Scale(scaleX, (scaleY)));
            tblevMain.getTransforms().add(new Translate(0, 0));

            Translate gridTransform = new Translate();
            tblevMain.getTransforms().add(gridTransform);

            for(int i = 0; i < numberOfPages; i++)
            {
                gridTransform.setY(-i * (pagePrintableHeight / localScale));
                job.printPage(pageLayout, tblevMain);
            }

            job.endJob();
        }

        return true;
    }

}
