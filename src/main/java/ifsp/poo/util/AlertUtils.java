package ifsp.poo.util;

import javafx.scene.control.Alert;

public class AlertUtils {

    public static void showErrorAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(msg);
        alert.showAndWait();
    }

    public static void showWarningAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText(msg);
        alert.showAndWait();
    }

    public static void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(msg);
        alert.showAndWait();
    }
}
