package codev19;

import codev19.database.myDB;
import codev19.model.Analyze;
import codev19.config.sqlConfig;
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
    private final ObservableList<Analyze> AnalyzeData = FXCollections.observableArrayList();

    // 데이터배이스
    private myDB db;

    // 팝업
    private Popup alert;



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

        // db 생성
        db = new myDB();
        // db 설정 불러오기
        sqlConfig config = new sqlConfig();

        // db 연결하기
        if (!db.connectDB(config.server, config.dbName, config.user, config.password)){
            alert.show("ERROR", "db connection error","connectDB has an ERROR");
        }

        // 데이터 불러오기
        loadData();
    }

    // db에서 데이터 불러오기
    public void loadData() {
        if(!db.execQuery("SELECT * FROM load_covid_result()")){
            // 목록 불러오는 query 실행
            alert.show("ERROR", "Query Error","cannot execute query");
            return;
        }

        // 전체 개수 카운트
        int TotalCount = 0;
        int CityCount = 0;
        int StateCount = 0;
        int StreetCount = 0;
        int ClinicCount = 0;

        // 양성 환자 개수 카운트
        int VirusTotal = 0;
        int VirusState = 0;
        int VirusCity = 0;
        int VirusStreet = 0;
        int VirusClinic = 0;

        String state;   // 주
        String city;    // 도시
        String street;  // 로
        String clinic;  // 진료소
        String people;  // 사람


        Analyze PrevData = null;   // 이전 데이터
        Analyze NewData;    // 새로운 데이터

        int flag; // 데이터 변화 플레그

        while(db.next()){
            

            state = db.getResult(1);    // 주
            city = db.getResult(2);     // 도시
            street = db.getResult(3);   // 로
            clinic = db.getResult(4);   // 진료소
            people = db.getResult(5);   // 사람
            int result = Boolean.parseBoolean(db.getResult(6)) ? 1: 0;  // 양성 or 음성
            // 새로운 데이터로 테이블 ROW 생성
            NewData = new Analyze(state, city, street, clinic, people, 1, result);

            // 첫번째 데이터일 경우
            if(PrevData == null){
                PrevData = NewData;
            }

            // 검사 결과가 양성일 경우
            if(result == 1){
                VirusState++;
                VirusCity++;
                VirusStreet++;
                VirusClinic++;
                VirusTotal++;
            }
            
            // 플래그 초기화
            flag = 0;
            
            // State가 이전 데이터랑 다른 경우
            if(!PrevData.getState().equals(NewData.getState())){
                flag = 4;
            }
            // City가 이전 데이터랑 다른 경우
            else if(!PrevData.getCity().equals(NewData.getCity())){
                flag = 3;
            }
            // Street가 이전 데이터랑 다른 경우
            else if(!PrevData.getStreet().equals(NewData.getStreet())){
                flag = 2;
            }
            // Clinic가 이전 데이터랑 다른 경우
            else if(!PrevData.getClinic().equals(NewData.getClinic())){
                flag = 1;
            }

            if(flag > 0){
                AnalyzeData.add(new Analyze(PrevData.getState(), PrevData.getCity(), PrevData.getStreet(), PrevData.getClinic(), "", ClinicCount, VirusClinic));
                ClinicCount = 0;
                VirusClinic = 0;
            }
            if(flag > 1){
                AnalyzeData.add(new Analyze(PrevData.getState(), PrevData.getCity(), PrevData.getStreet(), "", "", StreetCount, VirusStreet));
                StreetCount = 0;
                VirusStreet = 0;
            }
            if(flag > 2){
                AnalyzeData.add(new Analyze(PrevData.getState(), PrevData.getCity(), "", "", "", CityCount, VirusCity));
                CityCount = 0;
                VirusCity = 0;
            }
            if(flag > 3){
                AnalyzeData.add(new Analyze(PrevData.getState(), "", "", "", "", StateCount, VirusState));
                StateCount = 0;
                VirusState = 0;
            }

            // 전체 1씩 증가
            TotalCount++;
            CityCount++;
            StateCount++;
            StreetCount++;
            ClinicCount++;

            // 사람 하나 추가
            AnalyzeData.add(new Analyze(state, city, street, clinic, people, 1, result));
            PrevData = NewData;
        }
        // 전체 종합 결과 추가
        AnalyzeData.add(new Analyze("전국", "", "", "", "", TotalCount, VirusTotal));
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
