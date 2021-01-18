package codev19.utils;

import codev19.Main;
import codev19.codev19Controller;
import codev19.model.Analyze;
import javafx.beans.binding.Bindings;
import javafx.print.*;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Window;

import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.List;


public class myPrint {
    private static final double SCREEN_TO_PRINT_DPI = 72d / 96d;

    private double scale = 1.0f;

    // 인쇄 사각형
    private Rectangle printRectangle;

    // 입력한 노드 그대로 프린트해보자
    public boolean print(PrinterJob job, boolean showPrintDialog, Node node) {

        // 사용자가 프린트 할 수 있는 프린터를 불러온다.
        Window window = node.getScene() != null ? node.getScene().getWindow() : null;

        if (!showPrintDialog || job.showPrintDialog(window)) {

            // 페이지 레이아웃 설정
            PageLayout pageLayout = job.getJobSettings().getPageLayout();
            double pageWidth = pageLayout.getPrintableWidth();
            double pageHeight = pageLayout.getPrintableHeight();

            // 프린터 정보 설정
            PrintInfo printInfo = getPrintInfo(pageLayout);

            // 프린터 범위 지정
            double printRectX = this.printRectangle.getX();
            double printRectY = this.printRectangle.getY();
            double printRectWith = this.printRectangle.getWidth();
            double printRectHeight = this.printRectangle.getHeight();

            // 기존의 설정 백업
            Node oldClip = node.getClip();
            List<Transform> oldTransforms = new ArrayList<>(node.getTransforms());
            // 프린트 범위 지정
            node.setClip(new javafx.scene.shape.Rectangle(printRectX, printRectY,
                    printRectWith, printRectHeight));

            // 행, 렬 불러오기
            int columns = printInfo.getColumnCount();
            int rows = printInfo.getRowCount();

            // 크기 지정
            double localScale = printInfo.getScale();
            node.getTransforms().add(new Scale(localScale, localScale));
            // 0,0으로 옮기기
            node.getTransforms().add(new Translate(-printRectX, -printRectY));

            // 설정된 크기 기준으로
            Translate gridTransform = new Translate();
            node.getTransforms().add(gridTransform);

            // for each page, move the node into position by adjusting the transform
            // and call the print page method of the PrinterJob
            boolean success = true;
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < columns; col++) {
                    gridTransform.setX(-col * pageWidth / localScale);
                    gridTransform.setY(-row * pageHeight / localScale);

                    success &= job.printPage(pageLayout, node);
                }
            }
            // restore the original transformation and clip values
            node.getTransforms().clear();
            node.getTransforms().addAll(oldTransforms);
            node.setClip(oldClip);
            return success;
        }
        return false;
    }

    // 크기 get
    public double getScale() {
        return scale;
    }

    // 크기 set
    public void setScale(final double scale) {
        this.scale = scale;
    }

    // 프린트 영역 get
    public Rectangle getPrintRectangle() {
        return printRectangle;
    }

    // 프린트 영역 set
    public void setPrintRectangle(final Rectangle printRectangle) {
        this.printRectangle = printRectangle;
    }

    public PrintInfo getPrintInfo(final PageLayout pageLayout) {

        double contentWidth = pageLayout.getPrintableWidth();
        double contentHeight = pageLayout.getPrintableHeight();

        double localScale = getScale() * SCREEN_TO_PRINT_DPI;

        final Rectangle printRect = getPrintRectangle();
        final double width = printRect.getWidth() * localScale;
        final double height = printRect.getHeight() * localScale;

        // calculate how many pages we need dependent on the size of the content and the page.
        int cCount = (int) Math.ceil((width) / contentWidth);
        int rCount = (int) Math.ceil((height) / contentHeight);

        return new PrintInfo(localScale, rCount, cCount);
    }

    public static class PrintInfo {
        final double scale; // 크기
        final int rowCount; // 가로 개수
        final int columnCount;  // 세로 개수

        // 생성자
        public PrintInfo(final double scale, final int rowCount, final int columnCount) {
            this.scale = scale;
            this.rowCount = rowCount;
            this.columnCount = columnCount;
        }

        // get 메서드
        public double getScale() {
            return scale;
        }

        public int getRowCount() {
            return rowCount;
        }

        public int getColumnCount() {
            return columnCount;
        }
    }
}
