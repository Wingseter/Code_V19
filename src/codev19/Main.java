package codev19;

import codev19.database.myDB;
import codev19.model.Analyze;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Analyze> AnalyzeData = FXCollections.observableArrayList();

    // 데이터배이스
    private myDB db;

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

        // db 생성
        db = new myDB();
        sqlConfig config = new sqlConfig();
        if (!db.connectDB(config.server, config.dbName, config.user, config.password)){

        }

        AnalyzeData.add(new Analyze("경기도", "성남시", "판교로", "1진료소", 10, 5));
        AnalyzeData.add(new Analyze("경기도", "성남시", "판교로", "2진료소", 20, 10));
        AnalyzeData.add(new Analyze("경기도", "성남시", "판교로", "3진료소", 20, 5));
        AnalyzeData.add(new Analyze("경기도", "성남시", "판교로", "", 50, 20));
        AnalyzeData.add(new Analyze("경기도", "성남시", "성남대로", "이하진료소", 10, 5));
        AnalyzeData.add(new Analyze("경기도", "성남시", "성남대로", "이중진료소", 20, 10));
        AnalyzeData.add(new Analyze("경기도", "성남시", "성남대로", "이상진료소", 20, 5));
        AnalyzeData.add(new Analyze("경기도", "성남시", "성남대로", "", 50, 20));
        AnalyzeData.add(new Analyze("경기도", "성남시", "", "", 100, 40));
        AnalyzeData.add(new Analyze("경기도", "", "", "", 100, 50));
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
            // Fxml 파일 불러오기
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("codev19.fxml"));
            AnchorPane analyzeOverview = (AnchorPane) loader.load();

            rootLayout.setCenter(analyzeOverview);

            // 컨트롤러 연결하기
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

    // 데이터 가져오기
    public ObservableList<Analyze> getAnalyzeData(){
        return AnalyzeData;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
