package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        /*primaryStage.setTitle("四则运算生成器");
        primaryStage.getIcons().add(new Image("/images/Icon.png"));

        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();*/
        //文件选择器

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("/images/Icon.png"));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
