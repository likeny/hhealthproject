package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class RootController implements Initializable {
	public Stage primaryStage;
	@FXML
	private ImageView liImageView;
	@FXML
	private Button liBtnStart;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 1. 스타트 버튼을 누르면 화면 전환
		liBtnStart.setOnAction(e -> {
			handlerLiBtnStartAction();
		});

		// 2. 이미지뷰를 7번 누르면 관리자창으로 전환
		liImageView.setOnMouseClicked(e -> {
			handlerLiImageViewClickedAction(e);
		});
	}

	// 1. 스타트 버튼을 누르면 화면 전환
	private void handlerLiBtnStartAction() {
		Stage loginStage;
		try {
			loginStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/login_root.fxml"));
			Parent root = loader.load();
			LoginController controller = loader.getController();
			controller.loginStage = loginStage;
			Scene scene = new Scene(root);
			loginStage.setScene(scene);
			primaryStage.close();
			loginStage.show();
		} catch (IOException e) {
			callAlert("화면 전환 오류 : 화면 전환에 오류가 있습니다. 확인하세요.");
		}
	}

	// 2. 이미지뷰를 7번 누르면 관리자창으로 전환
	private void handlerLiImageViewClickedAction(MouseEvent e) {
		if (e.getClickCount() == 7) {
			Stage adminStage;
			try {
				adminStage = new Stage();
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/admin.fxml"));
				Parent root = loader.load();
				AdminController controller = loader.getController();
				controller.adminStage = adminStage;
				Scene scene = new Scene(root);
				adminStage.setScene(scene);
				primaryStage.close();
				adminStage.show();
			} catch (IOException e1) {
				callAlert("화면 전환 오류 : 화면 전환에 오류가 있습니다. 확인하세요.");
			}
		}

	}

	// 기타:알림창(중간에 : 적어줄것) 예시 - "오류정보: 값을 제대로 입력해주세요"
	private void callAlert(String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("알림!");
		alert.setHeaderText(contentText.substring(0, contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":") + 1));
		alert.showAndWait();
	}

	/*
	 * private void handlerImageViewAction() { textId.setText("root");
	 * textPassWord.setText("123456"); }
	 */

	/*
	 * private void handlerBtnLoginAction() { if (!(textId.getText().equals("root")
	 * && textPassWord.getText().equals("123456"))) {
	 * callAlert("로그인실패 : 아이디, 패스워드가 맞지 않습니다."); textId.clear();
	 * textPassWord.clear(); return; } FXMLLoader loader = null; Parent root; try {
	 * Stage mainStage = new Stage(); loader = new
	 * FXMLLoader(getClass().getResource("../View/main.fxml")); root =
	 * loader.load(); MainController mainController = (MainController)
	 * loader.getController(); mainController.mainStage = mainStage; Scene scene =
	 * new Scene(root); mainStage.setScene(scene); primaryStage.close();
	 * mainStage.show(); callAlert("화면전환 성공 : 메인화면으로 전환되었습니다."); } catch
	 * (IOException e) { e.printStackTrace(); }
	 * 
	 * }
	 * 
	 * private void handlerBtnCloseAction() { Platform.exit(); }
	 */

}
