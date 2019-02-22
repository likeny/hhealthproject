package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Model.UserDailyWeight;
import Model.UserInfo;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	public Stage loginStage;
	private ObservableList<UserInfo> uiList = FXCollections.observableArrayList();
	public ArrayList<UserInfo> loginList = new ArrayList<>();
	public ArrayList<UserInfo> loginUserMan = new ArrayList<>();
	private String pass = null;
	public String userid;

	@FXML
	private TextField loginTextFiledID;
	@FXML
	private PasswordField loginPassWord;
	@FXML
	private Button loginBtnLogin;
	@FXML
	private Button loginBtnExit;
	@FXML
	private Button loginBtnFirst;
	@FXML
	private Label loginLabelHealth;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginLabelHealth.setOnMouseClicked(e -> {
			loginTextFiledID.setText("likeny");
			loginPassWord.setText("dkseho!wldy1");
		});

		// 1. 비밀번호 입력 후 엔터 버튼을 누르면 발생하는 이벤트
		loginPassWord.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER)) {
				handlerLoginBtnLoginAction();
			}
		});

		// 1-1. 로그인 버튼을 누르면 발생하는 함수(화면 전환)
		loginBtnLogin.setOnAction(e -> {
			handlerLoginBtnLoginAction();
		});

		// 0. 나가기 버튼을 누르면 발생하는 함수
		loginBtnExit.setOnAction(e -> {
			Platform.exit();
		});

		// 2. 처음이에요 버튼을 누르면 실행하는 함수(회원정보입력 창으로 전환)
		loginBtnFirst.setOnAction(e -> {
			handlerLoginBtnFirstAction();
		});
	}

	// 1. 로그인 버튼을 누르면 발생하는 함수(화면 전환)
	private void handlerLoginBtnLoginAction() {
		loginList = UserInfoDAO.getUserInfoData();
		for (UserInfo ui : loginList) {
			if (ui.getUserID().equals(loginTextFiledID.getText())) {
				userid = ui.getUserID();
				pass = ui.getPassword();
				loginUserMan.add(ui);
			}
		}
		if (pass != null && pass.equals(loginPassWord.getText())) {
			callAlert("로그인 성공!! :환영합니다" + loginTextFiledID.getText() + "님^^");
			if (UserDailyWeightDAO.getUserDailyWeightData(loginUserMan.get(0).getUserID(),
					LocalDate.now().toString()) == 0) {
				Stage beforeStage;
				Parent root1;
				try {
					FXMLLoader loader1 = new FXMLLoader(getClass().getResource("../View/before.fxml"));
					beforeStage = new Stage();
					beforeStage.initModality(Modality.WINDOW_MODAL);
					beforeStage.initOwner(loginStage);
					root1 = loader1.load();
					Scene scene1 = new Scene(root1);
					beforeStage.setScene(scene1);
					beforeStage.show();
					TextField beforeTxt = (TextField) root1.lookup("#beforeTxt");
					inputDecimalFormat(beforeTxt);
					Button beforeBtnYes = (Button) root1.lookup("#beforeBtnYes");
					Button beforeBtnNo = (Button) root1.lookup("#beforeBtnNo");

					beforeBtnYes.setOnAction(e -> {
						UserInfo ui = new UserInfo(loginUserMan.get(0).getUserID(), loginUserMan.get(0).getPassword(),
								loginUserMan.get(0).getGoal(), loginUserMan.get(0).getHopeControlWeight(),
								loginUserMan.get(0).getGender(), loginUserMan.get(0).getMoveLevel(),
								Integer.parseInt(beforeTxt.getText()), loginUserMan.get(0).getKeepCalorie());
						loginUserMan.set(0, ui);
						UserInfoDAO.updateUserCurrentWeightData(ui);
						UserDailyWeight udw = new UserDailyWeight(ui.getUserID(), Date.valueOf(LocalDate.now()),
								ui.getCurrentWeight());
						UserDailyWeightDAO.insertUserDailyWeightData(udw);
						callAlert("오늘의 체중 입력 완료: 다시 로그인해주세요");
						beforeStage.close();
					});

					beforeBtnNo.setOnAction(e -> {
						Platform.exit();
					});

				} catch (IOException e1) {
				}
			} else {
				Stage mainStage;
				Parent root;
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/main.fxml"));
					mainStage = new Stage();
					root = loader.load();
					MainController controller = (MainController) loader.getController();
					controller.mainStage = mainStage;
					controller.setLoginUserMan(loginUserMan);
					loginStage.close();
					Scene scene = new Scene(root);
					mainStage.setScene(scene);
					mainStage.show();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		} else {
			callAlert("로그인 실패!! :아이디와 비밀번호를 확인하세요.");
		}
	}

	// 2. 처음이에요 버튼을 누르면 실행하는 함수(회원정보입력 창으로 전환)
	private void handlerLoginBtnFirstAction() {
		Parent root;
		try {
			Stage firstStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/first.fxml"));
			root = loader.load();
			firstStage.initModality(Modality.WINDOW_MODAL);
			firstStage.initOwner(loginStage);

			AnchorPane firstAnchor7 = (AnchorPane) root.lookup("#firstAnchor7");
			AnchorPane firstAnchor6 = (AnchorPane) root.lookup("#firstAnchor6");
			AnchorPane firstAnchor5 = (AnchorPane) root.lookup("#firstAnchor5");
			AnchorPane firstAnchor4 = (AnchorPane) root.lookup("#firstAnchor4");
			AnchorPane firstAnchor3 = (AnchorPane) root.lookup("#firstAnchor3");
			AnchorPane firstAnchor2 = (AnchorPane) root.lookup("#firstAnchor2");
			AnchorPane firstAnchor1 = (AnchorPane) root.lookup("#firstAnchor1");

			TextField anchor7TextID = (TextField) root.lookup("#anchor7TextID");
			PasswordField anchor7Pass1 = (PasswordField) root.lookup("#anchor7Pass1");
			PasswordField anchor7Pass2 = (PasswordField) root.lookup("#anchor7Pass2");
			Button anchor7BtnSame = (Button) root.lookup("#anchor7BtnSame");
			Button anchor7BtnFinish = (Button) root.lookup("#anchor7BtnFinish");
			Button anchor7BtnBefore = (Button) root.lookup("#anchor7BtnBefore");
			anchor7Pass1.setDisable(true);
			anchor7Pass2.setDisable(true);

			TextField anchor6TextHeight = (TextField) root.lookup("#anchor6TextHeight");
			Button anchor6BtnNext = (Button) root.lookup("#anchor6BtnNext");
			Button anchor6BtnBefore = (Button) root.lookup("#anchor6BtnBefore");
			TextField anchor6TextWeight = (TextField) root.lookup("#anchor6TextWeight");
			inputDecimalFormat(anchor6TextHeight);
			inputDecimalFormat(anchor6TextWeight);

			Button anchor5BtnNext = (Button) root.lookup("#anchor5BtnNext");
			Button anchor5BtnBefore = (Button) root.lookup("#anchor5BtnBefore");
			RadioButton anchor5Rdo1 = (RadioButton) root.lookup("#anchor5Rdo1");
			RadioButton anchor5Rdo2 = (RadioButton) root.lookup("#anchor5Rdo2");
			RadioButton anchor5Rdo3 = (RadioButton) root.lookup("#anchor5Rdo3");
			RadioButton anchor5Rdo4 = (RadioButton) root.lookup("#anchor5Rdo4");
			ObservableMap<String, Object> map3 = loader.getNamespace();
			ToggleGroup group3 = (ToggleGroup) map3.get("group3");

			Button anchor4BtnNext = (Button) root.lookup("#anchor4BtnNext");
			Button anchor4BtnBefore = (Button) root.lookup("#anchor4BtnBefore");
			RadioButton anchor4Rdo1 = (RadioButton) root.lookup("#anchor4Rdo1");
			RadioButton anchor4Rdo2 = (RadioButton) root.lookup("#anchor4Rdo2");
			ObservableMap<String, Object> map2 = loader.getNamespace();
			ToggleGroup group2 = (ToggleGroup) map2.get("group2");

			Button anchor3BtnNext = (Button) root.lookup("#anchor3BtnNext");
			Button anchor3BtnBefore = (Button) root.lookup("#anchor3BtnBefore");
			TextField anchor3TextKG = (TextField) root.lookup("#anchor3TextKG");
			inputDecimalFormat(anchor3TextKG);

			Button anchor2BtnNext = (Button) root.lookup("#anchor2BtnNext");
			Button anchor2BtnBefore = (Button) root.lookup("#anchor2BtnBefore");
			RadioButton anchor2Rdo1 = (RadioButton) root.lookup("#anchor2Rdo1");
			RadioButton anchor2Rdo2 = (RadioButton) root.lookup("#anchor2Rdo2");
			RadioButton anchor2Rdo3 = (RadioButton) root.lookup("#anchor2Rdo3");
			ObservableMap<String, Object> map1 = loader.getNamespace();
			ToggleGroup group1 = (ToggleGroup) map1.get("group1");

			firstAnchor1.setOnMouseClicked(e -> {
				firstAnchor1.setVisible(false);
				firstAnchor2.setVisible(true);
				firstAnchor3.setVisible(false);
				firstAnchor4.setVisible(false);
				firstAnchor5.setVisible(false);
				firstAnchor6.setVisible(false);
				firstAnchor7.setVisible(false);
			});

			anchor2BtnNext.setOnAction(e -> {
				if (group1.getSelectedToggle() != null) {
					firstAnchor1.setVisible(false);
					firstAnchor2.setVisible(false);
					firstAnchor3.setVisible(true);
					firstAnchor4.setVisible(false);
					firstAnchor5.setVisible(false);
					firstAnchor6.setVisible(false);
					firstAnchor7.setVisible(false);
				} else {
					callAlert("아무것도 선택 안하셨네요 : 답변을 선택하시고 다음을 눌러주세요^^");
				}
			});

			anchor2BtnBefore.setOnAction(e -> {
				firstAnchor1.setVisible(true);
				firstAnchor2.setVisible(false);
				firstAnchor3.setVisible(false);
				firstAnchor4.setVisible(false);
				firstAnchor5.setVisible(false);
				firstAnchor6.setVisible(false);
				firstAnchor7.setVisible(false);
			});

			anchor3BtnNext.setOnAction(e -> {
				try {
					if (Integer.parseInt(anchor3TextKG.getText()) < 100) {
						firstAnchor1.setVisible(false);
						firstAnchor2.setVisible(false);
						firstAnchor3.setVisible(false);
						firstAnchor4.setVisible(true);
						firstAnchor5.setVisible(false);
						firstAnchor6.setVisible(false);
						firstAnchor7.setVisible(false);
					} else {
						callAlert("오잉?? : 100이하의 숫자로 다시 입력해주세요.");
					}
				} catch (Exception e1) {
					callAlert("오잉?? : 입력을 안하셨네요^^ 입력해주세요.");
				}
			});

			anchor3BtnBefore.setOnAction(e -> {
				firstAnchor1.setVisible(false);
				firstAnchor2.setVisible(true);
				firstAnchor3.setVisible(false);
				firstAnchor4.setVisible(false);
				firstAnchor5.setVisible(false);
				firstAnchor6.setVisible(false);
				firstAnchor7.setVisible(false);
			});

			anchor4BtnNext.setOnAction(e -> {
				if (group2.getSelectedToggle() != null) {
					firstAnchor1.setVisible(false);
					firstAnchor2.setVisible(false);
					firstAnchor3.setVisible(false);
					firstAnchor4.setVisible(false);
					firstAnchor5.setVisible(true);
					firstAnchor6.setVisible(false);
					firstAnchor7.setVisible(false);
				} else {
					callAlert("아무것도 선택 안하셨네요 : 답변을 선택하시고 다음을 눌러주세요^^");
				}
			});

			anchor4BtnBefore.setOnAction(e -> {
				firstAnchor1.setVisible(false);
				firstAnchor2.setVisible(false);
				firstAnchor3.setVisible(true);
				firstAnchor4.setVisible(false);
				firstAnchor5.setVisible(false);
				firstAnchor6.setVisible(false);
				firstAnchor7.setVisible(false);
			});

			anchor5BtnNext.setOnAction(e -> {
				if (group3.getSelectedToggle() != null) {
					firstAnchor1.setVisible(false);
					firstAnchor2.setVisible(false);
					firstAnchor3.setVisible(false);
					firstAnchor4.setVisible(false);
					firstAnchor5.setVisible(false);
					firstAnchor6.setVisible(true);
					firstAnchor7.setVisible(false);
				} else {
					callAlert("아무것도 선택 안하셨네요 : 답변을 선택하시고 다음을 눌러주세요^^");
				}
			});

			anchor5BtnBefore.setOnAction(e -> {
				firstAnchor1.setVisible(false);
				firstAnchor2.setVisible(false);
				firstAnchor3.setVisible(false);
				firstAnchor4.setVisible(true);
				firstAnchor5.setVisible(false);
				firstAnchor6.setVisible(false);
				firstAnchor7.setVisible(false);
			});

			anchor6BtnNext.setOnAction(e -> {
				try {
					if (Integer.parseInt(anchor6TextHeight.getText()) < 300
							&& Integer.parseInt(anchor6TextWeight.getText()) < 500) {
						firstAnchor1.setVisible(false);
						firstAnchor2.setVisible(false);
						firstAnchor3.setVisible(false);
						firstAnchor4.setVisible(false);
						firstAnchor5.setVisible(false);
						firstAnchor6.setVisible(false);
						firstAnchor7.setVisible(true);
					} else {
						callAlert("오잉?? : 현실적인 값을 입력하세요.");
					}
				} catch (Exception e1) {
					callAlert("오잉?? : 입력을 안하셨네요^^ 입력해주세요.");
				}
			});

			anchor6BtnBefore.setOnAction(e -> {
				firstAnchor1.setVisible(false);
				firstAnchor2.setVisible(false);
				firstAnchor3.setVisible(false);
				firstAnchor4.setVisible(false);
				firstAnchor5.setVisible(true);
				firstAnchor6.setVisible(false);
				firstAnchor7.setVisible(false);
			});

			anchor7BtnSame.setOnAction(e -> {
				String value = null;
				for (UserInfo ui : UserInfoDAO.getUserInfoData()) {
					if (ui.getUserID().contains(anchor7TextID.getText())) {
						value = ui.getUserID();
					}
				}
				if (value == null) {
					callAlert("중복안됨!!!!! :" + anchor7TextID.getText() + "는 사용 가능한 아이디입니다.^^");
					anchor7TextID.setDisable(true);
					anchor7Pass1.setDisable(false);
					anchor7Pass2.setDisable(false);
				} else {
					callAlert("중복중복!!!!! :" + anchor7TextID.getText() + "는 사용이 불가능합니다!!!!!");
					anchor7TextID.clear();
				}
			});

			anchor7BtnBefore.setOnAction(e -> {
				firstAnchor1.setVisible(false);
				firstAnchor2.setVisible(false);
				firstAnchor3.setVisible(false);
				firstAnchor4.setVisible(false);
				firstAnchor5.setVisible(false);
				firstAnchor6.setVisible(true);
				firstAnchor7.setVisible(false);
			});

			anchor7BtnFinish.setOnAction(e -> {
				if (anchor7TextID.getText() != null && anchor7Pass1.getText() != null
						&& anchor7Pass1.getText().equals(anchor7Pass2.getText())) {
					callAlert("가입을 축하합니다^^:" + anchor7TextID.getText() + "로 로그인 하세요!");
					int kc = 1;
					switch (group3.getSelectedToggle().getUserData().toString()) {
					case "1":
						kc = Integer.parseInt(anchor6TextWeight.getText()) * 31;
						break;
					case "2":
						kc = Integer.parseInt(anchor6TextWeight.getText()) * 34;
						break;
					case "3":
						kc = Integer.parseInt(anchor6TextWeight.getText()) * 39;
						break;
					case "4":
						kc = Integer.parseInt(anchor6TextWeight.getText()) * 44;
						break;
					}
					int goal = 0;
					switch (group1.getSelectedToggle().getUserData().toString()) {
					case "1":
						goal = Integer.parseInt(anchor6TextWeight.getText())
								- Integer.parseInt(anchor3TextKG.getText());
						break;
					case "2":
						goal = Integer.parseInt(anchor6TextWeight.getText());
						break;
					case "3":
						goal = Integer.parseInt(anchor6TextWeight.getText())
								+ Integer.parseInt(anchor3TextKG.getText());
						break;
					}
					UserInfo userInfo = new UserInfo(anchor7TextID.getText(), anchor7Pass1.getText(), goal,
							Integer.parseInt(anchor3TextKG.getText()),
							group2.getSelectedToggle().getUserData().toString(),
							Integer.parseInt(group3.getSelectedToggle().getUserData().toString()),
							Integer.parseInt(anchor6TextWeight.getText()), kc);
					uiList.add(userInfo);
					int count = UserInfoDAO.insertUserInfoData(userInfo);
					UserInfoDAO.dbArrayList.add(userInfo);
					firstStage.close();
				} else {
					callAlert("비밀번호 불일치: 확인바람!!");
				}
			});

			Scene scene = new Scene(root);
			firstStage.setScene(scene);
			firstStage.show();

		} catch (IOException e) {

		}

	}

	// 기타1:알림창(중간에 : 적어줄것) 예시 - "오류정보: 값을 제대로 입력해주세요"
	public static void callAlert(String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("알림!");
		alert.setHeaderText(contentText.substring(0, contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":") + 1));
		alert.showAndWait();
	}

	// 기타2: 입력값 필드 포맷설정 기능 : 숫자 세자리만 받는 기능을 셋팅함
	private void inputDecimalFormat(TextField textField) {
		// 숫자만 입력(정수만 입력받음), 실수입력받고싶으면new DecimalFormat("###.#");
		DecimalFormat format = new DecimalFormat("###");
		// 점수 입력시 길이 제한 이벤트 처리
		textField.setTextFormatter(new TextFormatter<>(event -> {
			// 입력받은 내용이 없으면 이벤트를 리턴함.
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			// 구문을 분석할 시작 위치를 지정함.
			ParsePosition parsePosition = new ParsePosition(0);
			// 입력받은 내용과 분석위치를 지정한지점부터 format 내용과 일치한지 분석함.
			Object object = format.parse(event.getControlNewText(), parsePosition);
			// 리턴값이 null 이거나,입력한길이와구문분석위치값이 적어버리면(다 분석하지못했음을 뜻함)거나,입력한길이가 4이면(3자리를 넘었음을 뜻함.)
			// 이면 null 리턴함.
			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 4) {
				return null;
			} else {
				return event;
			}
		}));
	}

	// 기타3: 입력값 필드 포맷설정 기능 : 숫자 두자리만 받는 기능을 셋팅함
	private void inputDecimalFormatTwoDigit(TextField textField) {
		// 숫자만 입력(정수만 입력받음), 실수입력받고싶으면new DecimalFormat("###.#");
		DecimalFormat format = new DecimalFormat("##");
		// 점수 입력시 길이 제한 이벤트 처리
		textField.setTextFormatter(new TextFormatter<>(event -> {
			// 입력받은 내용이 없으면 이벤트를 리턴함.
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			// 구문을 분석할 시작 위치를 지정함.
			ParsePosition parsePosition = new ParsePosition(0);
			// 입력받은 내용과 분석위치를 지정한지점부터 format 내용과 일치한지 분석함.
			Object object = format.parse(event.getControlNewText(), parsePosition);
			// 리턴값이 null 이거나,입력한길이와구문분석위치값이 적어버리면(다 분석하지못했음을 뜻함)거나,입력한길이가 4이면(3자리를 넘었음을 뜻함.)
			// 이면 null 리턴함.
			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 3) {
				return null;
			} else {
				return event;
			}
		}));
	}
}
