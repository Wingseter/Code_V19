package codev19;

import codev19.model.Analyze;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class codev19Controller {
    @FXML
    private TableView<Analyze> AnalyzeTable;

    @FXML
    private TableColumn<Analyze, String> StateColumn;

    @FXML
    private TableColumn<Analyze, String> CityColumn;

    @FXML
    private TableColumn<Analyze, String> StreetColumn;

    @FXML
    private TableColumn<Analyze, String> ClinicColumn;

    @FXML
    private TableColumn<Analyze, Integer> TotalColumn;

    @FXML
    private TableColumn<Analyze, Integer> PositiveColumn;

    @FXML
    private TableColumn<Analyze, Double> PersentageColumn;

    private Main Main;

    @FXML
    private void initialize() {
        StateColumn.setCellValueFactory(cellData -> cellData.getValue().stateProperty());
        CityColumn.setCellValueFactory(cellData -> cellData.getValue().cityProperty());
        StreetColumn.setCellValueFactory(cellData -> cellData.getValue().streetProperty());
        ClinicColumn.setCellValueFactory(cellData -> cellData.getValue().clinicProperty());
        TotalColumn.setCellValueFactory(cellData -> cellData.getValue().totalProperty().asObject());
        PositiveColumn.setCellValueFactory(cellData -> cellData.getValue().positivitiyProperty().asObject());
        PersentageColumn.setCellValueFactory(cellData -> cellData.getValue().persentageProperty().asObject());
    }

    public void setMain(Main main) {
        this.Main = main;
        AnalyzeTable.setItems(main.getAnalyzeData());
    }
}
