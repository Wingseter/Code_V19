package codev19;

import codev19.model.Analyze;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.naming.ldap.Control;
import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Analyze> AnalyzeData = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception{
        // primaryStage 저장
        this.primaryStage = primaryStage;

        // 타이틀 설정
        primaryStage.setTitle("CODE-V19");

        // 사이즈 변경 막기
        primaryStage.setResizable(false);

        // 래이아웃 불러오기
        initRootLayout();

        // 오버레이 레이아웃 불러오기
        showAnalyzeOverview();

        AnalyzeData.add(new Analyze("경기도", "성남시", "분당구", "1보건소", 10, 5));
    }

    public void initRootLayout() {
        try {
            // 레이아웃 파일 불러오기
            rootLayout = FXMLLoader.load(getClass().getResource("RootLayout.fxml"));

            // 레이아웃 적용
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // 화면 띄우기
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAnalyzeOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("codev19.fxml"));
            AnchorPane analyzeOverview = (AnchorPane) loader.load();

            rootLayout.setCenter(analyzeOverview);

            codev19Controller controller = loader.getController();
            controller.setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // primaryStage 반환
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    //
    public ObservableList<Analyze> getAnalyzeData(){
        return AnalyzeData;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
