package codev19;

import codev19.model.Analyze;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class Controller {
    @FXML
    private TableView<Analyze> AnalyzeTable;
    private Main Main;
    @FXML
    private void initialize() {

    }

    public void setMain(Main main){
        this.Main = main;
        AnalyzeTable.setItems(main.getAnalyzeData());
    }

}
