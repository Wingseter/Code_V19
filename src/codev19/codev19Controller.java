package codev19;

import codev19.Main;
import codev19.model.Analyze;
import codev19.utils.myPrint;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.event.MouseEvent;

public class codev19Controller {

    // 테이블 정의
    @FXML
    private TableView<Analyze> AnalyzeTable;    // 분석 전체 테이블


    // column 정의
    @FXML
    private TableColumn<Analyze, String> StateColumn;   // 시, 도 단위 column

    @FXML
    private TableColumn<Analyze, String> CityColumn;    // 도시 단위 column

    @FXML
    private TableColumn<Analyze, String> StreetColumn;  // 도로 단위  column

    @FXML
    private TableColumn<Analyze, String> ClinicColumn;  // 진료소 단위 column

    @FXML
    private TableColumn<Analyze, String> PeopleColumn;  // 사람 단위 column

    @FXML
    private TableColumn<Analyze, Integer> TotalColumn;  // 전체 검사 수 column

    @FXML
    private TableColumn<Analyze, Integer> PositiveColumn;   // 양성 반응 확진자 수 column

    @FXML
    private TableColumn<Analyze, Double> PersentageColumn;  // 비율 column


    // Label 정의
    @FXML
    private Label StateLabel;   // 시, 도 단위 라벨

    @FXML
    private Label CityLabel;    // 도시 단위 라벨

    @FXML
    private Label StreetLabel;  // 도로명 단위 라벨

    @FXML
    private Label ClinicLabel;  // 진료소 단위 라벨

    @FXML
    private Label PeopleLabel;  // 사람 단위 라벨벨

    @FXML
   private Label SelectLabel;   // 선택한 장소 라벨

    @FXML
    private Label TotalLabel;    // 전체 라벨

    @FXML
    private Label PositiveLabel;  // 양성 반응 라벨

    @FXML
    private Label NegativeLabel;  // 음성 반응 라벨

    @FXML
    private Label PersentageLabel;  // 확률 라벨

    private codev19.Main Main;  // 메인 윈도우

    private myPrint myprt;      // 프린터 클래스
    private Rectangle printRectangle;
    private Node nodeToPrint;

    @FXML
    private void initialize() {
        // 각각의 값들 테이블에 설정
        StateColumn.setCellValueFactory(cellData -> cellData.getValue().stateProperty());
        CityColumn.setCellValueFactory(cellData -> cellData.getValue().cityProperty());
        StreetColumn.setCellValueFactory(cellData -> cellData.getValue().streetProperty());
        ClinicColumn.setCellValueFactory(cellData -> cellData.getValue().clinicProperty());
        PeopleColumn.setCellValueFactory(cellData -> cellData.getValue().peopleProperty());
        TotalColumn.setCellValueFactory(cellData -> cellData.getValue().totalProperty().asObject());
        PositiveColumn.setCellValueFactory(cellData -> cellData.getValue().positivitiyProperty().asObject());
        PersentageColumn.setCellValueFactory(cellData -> cellData.getValue().persentageProperty().asObject());

        showAnalyzeDetails(null);

        AnalyzeTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showAnalyzeDetails(newValue));
    }

    public void setMain(Main main) {
        // 메인 윈도우 설정
        this.Main = main;
        AnalyzeTable.setItems(main.getAnalyzeData());
    }

    private void showAnalyzeDetails(Analyze analyze) {
        if (analyze != null) {
            // 선택된 데이터가 있을 경우
            StateLabel.setText(analyze.getState());
            CityLabel.setText(analyze.getCity());
            StreetLabel.setText(analyze.getStreet());
            ClinicLabel.setText(analyze.getClinic());
            PeopleLabel.setText(analyze.getPeople());
            SelectLabel.setText(analyze.getSelection());
            TotalLabel.setText(Integer.toString(analyze.getTotal()));
            PositiveLabel.setText(Integer.toString(analyze.getPositive()));
            NegativeLabel.setText(Integer.toString(analyze.getTotal() - analyze.getPositive()));
            PersentageLabel.setText(Double.toString(analyze.getPersentage()));
        }
        else {
            // 선택된 데이터가 없을 경우 모두 지운다.
            StateLabel.setText("");
            CityLabel.setText("");
            StreetLabel.setText("");
            ClinicLabel.setText("");
            PeopleLabel.setText("");
            SelectLabel.setText("");
            TotalLabel.setText("");
            PositiveLabel.setText("");
            NegativeLabel.setText("");
            PersentageLabel.setText("");
        }
    }

    public TableView<Analyze> getTable() {
        return AnalyzeTable;
    }

    // 프린트 버튼 클릭했을때
    public void ptrClicked(ActionEvent actionEvent) {
        myprt = new myPrint();

        PrinterJob job;

        job = PrinterJob.createPrinterJob();
        Group pane = new Group();
        pane.getChildren().addAll(getNodeToPrint(), getPrintRectangle());
        myprt.setScale(3);
        myprt.setPrintRectangle(getPrintRectangle());
        boolean success = myprt.print(job, true, getNodeToPrint());
        if (success) {
            job.endJob();
        }
    }

    private Rectangle getPrintRectangle() {
        if (printRectangle == null) {
            printRectangle = new Rectangle(600, 500, null);
            printRectangle.setStroke(Color.BLACK);
        }
        return printRectangle;
    }

    private Node getNodeToPrint() {
        if (nodeToPrint == null) {

            Group group = new Group();
            group.getChildren().addAll(
                    new Rectangle(200, 100, Color.RED),
                    new Rectangle(200,100, 200, 100),
                    new Rectangle(400, 200, 200, 100),
                    new Rectangle(600, 300, 200, 100),
                    new Rectangle(800, 400, 200, 100)
            );

            nodeToPrint = group;
        }
        return nodeToPrint;
    }
}
