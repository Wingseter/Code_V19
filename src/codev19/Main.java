package codev19;

import codev19.database.myDB;
import codev19.model.Analyze;
import codev19.config.sqlConfig;
import codev19.utils.CovidCounter;
import codev19.utils.Popup;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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

    // 팝업
    private Popup alert;

    // 분석기
    private CovidCounter counter;

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

        // 팝업 생성기
        alert = new Popup();

        // 분석기
        counter = new CovidCounter();

        // db 생성
        db = new myDB();
        // db 설정 불러오기
        sqlConfig config = new sqlConfig();

        // db 연결하기
        if (!db.connectDB(config.server, config.dbName, config.user, config.password)){
            alert.show("ERROR", "db connection error","connectDB has an ERROR");
        }

        loadData();
    }

    // db에서 데이터 불러오기
    public void loadData() {
        if(!db.execQuery("SELECT * FROM load_covid_result()")){
            // 목록 불러오는 query 실행
            alert.show("ERROR", "Query Error","cannot execute query");
            return;
        }

        while(db.next()){
            while(counter.inputData(db.getResult(1), db.getResult(2),  db.getResult(3), db.getResult(4)
                    , db.getResult(5), Boolean.parseBoolean(db.getResult(6)) ? 1 : 0)){
                // 분석 결과 추가
               AnalyzeData.add(counter.outputAnalyze());
            }
            // 사람 하나 추가
            AnalyzeData.add(counter.outputData());
        }

        // 전체 종합 결과 추가
        AnalyzeData.add(counter.totalAnalyze());
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
