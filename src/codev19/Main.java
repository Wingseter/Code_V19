package codev19;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private AnchorPane rootLayout;

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

    }

    public void initRootLayout() {
        try {
            // 레이아웃 파일 불러오기
            Parent root = FXMLLoader.load(getClass().getResource("codev19.fxml"));

            // 레이아웃 적용
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);

            // 화면 띄우기
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return new Stage();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
