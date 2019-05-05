import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.SomeService;

public class Main extends Application {

    public static void main(String[] args) {

        //ApplicationContext context = new AnnotationConfigApplicationContext("service");
        //SomeService someService = context.getBean(SomeService.class);
        //someService.doSomething();

        launch(args);

    }

    public void start(Stage primaryStage) throws Exception {
        String fxmlFile = "/fxml/view.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
