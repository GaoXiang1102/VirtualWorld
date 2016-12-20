package app;

import controller.WorldController;
import exception.MyException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainProgram extends Application {

    /**
     * The start of the JavaFX
     * @param stage
     */
	public void start(Stage stage) {

		try {
            // load fxml file and add background
			FXMLLoader fxmlLoader = new FXMLLoader();
			String viewerFxml = "/WorldViewer.fxml";
			AnchorPane pane = (AnchorPane) fxmlLoader.load(this.getClass().getResource(viewerFxml).openStream());
			pane.getStyleClass().add("background");

            // add CSS file
			Scene scene = new Scene(pane);
			scene.getStylesheets().add("/MyStyle.css");

            // get the controller, initialise and visualise
			WorldController controller = (WorldController) fxmlLoader.getController();
			initialise(controller);
			show_viewer(controller);

			stage.setScene(scene);
			stage.show();
		} catch (IOException ex) {
		   Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		   System.exit(1);
		} catch (MyException e) {
			System.out.println(e.getMessage());
		}
	}

    /**
     * show all the elements of the APP
     * @param controller
     */
	public void show_viewer(WorldController controller) {
		controller.show_photo();
		controller.show_map();
        controller.show_position_label();
		controller.show_arrow();
		controller.show_button();
		controller.show_items();
		controller.show_picker();
		controller.show_logo();
	}

    /**
     * initialise all the elements in the APP that need to be initialised
     * @param controller
     * @throws MyException
     */
	public void initialise(WorldController controller) throws MyException {
		controller.init_location();
		controller.init_item_list();
		controller.initialise_items_action();
		controller.initialise_picker_action();
	}

    /**
     * the entrance of the APP
     * @param args
     */
    public static void main(String args[]) {
     	launch(args);
     	System.exit(0);
    }
}
