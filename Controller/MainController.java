package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import Model.Exercise;
import Model.Food;
import Model.UserCal;
import Model.UserInfo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MainController implements Initializable {
	public Stage mainStage;
	private ObservableList<Food> foodTableList = FXCollections.observableArrayList();
	private ObservableList<Exercise> exerciseTableList = FXCollections.observableArrayList();
	private ObservableList<String> foodCmbKindList = FXCollections.observableArrayList();
	private ObservableList<String> exerCmbKindList = FXCollections.observableArrayList();
	private ObservableList<String> foodCmbKategorieList1 = FXCollections.observableArrayList();
	private ObservableList<UserCal> userMinusCalRecordList = FXCollections.observableArrayList();
	private ObservableList<UserCal> userCalRecordList = FXCollections.observableArrayList();

	public ArrayList<UserInfo> loginUserMan = new ArrayList<>();

	public void setLoginUserMan(ArrayList<UserInfo> loginUserMan) {
		this.loginUserMan = loginUserMan;
	}

	public ArrayList<UserInfo> getLoginUserMan() {
		return loginUserMan;
	}

	private ArrayList<Food> dbArrayList;
	private ArrayList<UserInfo> userInfodbArrayList;
	private ArrayList<Exercise> exercisedbArrayList;
	private ArrayList<UserCal> userCalArrayList;
	private ArrayList<UserCal> userMinusCalArrayList;

	private String firstPkno = null;
	private String secondPkno = null;
	// =======================오늘의 기록=======================
	private Food selectedFood;
	private int selectedFoodIndex;
	private Exercise selectedExercise;
	private int selectedExerciseIndex;
	@FXML
	private Label t1LabelLimit;
	@FXML
	private Label t1LabelEat;
	@FXML
	private TextField t1txtBrk1;
	@FXML
	private Button t1BtnBrk1;
	@FXML
	private TextField t1txtBrk2;
	@FXML
	private TextField t1txtBrk3;
	@FXML
	private Button t1BtnBrk3;
	@FXML
	private Button t1BtnBrk2;
	@FXML
	private Button t1BtnLun2;
	@FXML
	private Button t1BtnLun3;
	@FXML
	private TextField t1txtLun3;
	@FXML
	private TextField t1txtLun2;
	@FXML
	private Button t1BtnLun1;
	@FXML
	private TextField t1txtLun1;
	@FXML
	private TextField t1txtDin1;
	@FXML
	private Button t1BtnDin1;
	@FXML
	private TextField t1txtDin2;
	@FXML
	private TextField t1txtDin3;
	@FXML
	private Button t1BtnDin3;
	@FXML
	private Button t1BtnDin2;
	@FXML
	private Button t1BtnEtc2;
	@FXML
	private Button t1BtnEtc3;
	@FXML
	private TextField t1txtEtc3;
	@FXML
	private TextField t1txtEtc2;
	@FXML
	private Button t1BtnEtc1;
	@FXML
	private TextField t1txtEtc1;
	@FXML
	private Button t1BtnBrkMinus1;
	@FXML
	private Button t1BtnBrkMinus2;
	@FXML
	private Button t1BtnBrkMinus3;
	@FXML
	private Button t1BtnLunMinus3;
	@FXML
	private Button t1BtnLunMinus2;
	@FXML
	private Button t1BtnLunMinus1;
	@FXML
	private Button t1BtnDinMinus1;
	@FXML
	private Button t1BtnDinMinus2;
	@FXML
	private Button t1BtnDinMinus3;
	@FXML
	private Button t1BtnEtcMinus3;
	@FXML
	private Button t1BtnEtcMinus2;
	@FXML
	private Button t1BtnEtcMinus1;
	@FXML
	private DatePicker t1DatePicker;
	@FXML
	private Label t1LabelFire;
	@FXML
	private Label t1LabelCan;
	@FXML
	private Button t1BtnTodaySave;
	@FXML
	private Button t1BtnTodayCal;
	@FXML
	private Button t1BtnFoodRecord;
	@FXML
	private Button t1BtnExerRecord;

	// =======================칼로리리포트=======================
	@FXML
	private Tab t3Tab;
	@FXML
	private BarChart t3BarChart;
	@FXML
	private Label t3LabelLimit;
	@FXML
	private Label t3LabelComment;
	@FXML
	private Label t3LabelTotalCal;
	@FXML
	private Label t3LabelAvgCal;

	private XYChart.Series<String, Integer> series1 = new XYChart.Series<>();
	// =========================체중변화=========================
	@FXML
	private Tab t4Tab;
	@FXML
	private Label t4LabelWantWeight;
	@FXML
	private LineChart t4LineChart;
	@FXML
	private TextField t4txtTodayWeight;
	@FXML
	private Button t4BtnWeightSave;
	private Series series2 = new XYChart.Series<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 로그인시 안내멘트
		startMainInfoMent();

		// =========================오늘의 기록==========================

		// 첫 화면 아이템 셋팅
		itemSettingFirst();

		// 지금까지 식사기록 보기 버튼 눌렀을때 액션
		t1BtnFoodRecord.setOnAction(e -> {
			handlerT1BtnFoodRecordAction();
		});

		// 지금까지 운동기록 보기 버튼 눌렀을때 액션
		t1BtnExerRecord.setOnAction(e -> {
			handlerT1BtnExerRecordAction();
		});

		// 오늘의 기록 저장 버튼을 눌렀을때 액션
		t1BtnTodaySave.setOnAction(e -> {
			handlerT1BtnTodaySaveAction();
		});

		// 오늘의 칼로리 계산 버튼을 눌렀을때 액션
		t1BtnTodayCal.setOnAction(e -> {
			handlerT1BtnTodayCalAction();
		});

		// 첫번째 식사기록 +버튼을 눌렀을때의 액션
		t1BtnBrk1.setOnAction(e -> {
			handlerT1BtnBrk1Action(t1txtBrk1, t1txtBrk2, t1BtnBrk1, t1BtnBrkMinus1, t1BtnBrk2, t1BtnBrkMinus2);
		});

		// 첫번째 식사기록 -버튼을 눌렀을때의 액션
		t1BtnBrkMinus1.setOnAction(e -> {
			handlerT1BtnBrkMinusAction(t1txtBrk1, t1BtnBrk1, t1BtnBrkMinus1);
		});

		// 두번째 식사기록 +버튼을 눌렀을때의 액션
		t1BtnBrk2.setOnAction(e -> {
			handlerT1BtnBrk1Action(t1txtBrk2, t1txtBrk3, t1BtnBrk2, t1BtnBrkMinus2, t1BtnBrk3, t1BtnBrkMinus3);
		});

		// 두번째 식사기록 -버튼을 눌렀을때의 액션
		t1BtnBrkMinus2.setOnAction(e -> {
			handlerT1BtnBrkMinusAction(t1txtBrk2, t1BtnBrk2, t1BtnBrkMinus2);
		});

		// 세번째 식사기록 +버튼을 눌렀을때의 액션
		t1BtnBrk3.setOnAction(e -> {
			handlerT1BtnBrk1Action(t1txtBrk3, t1txtLun1, t1BtnBrk3, t1BtnBrkMinus3, t1BtnLun1, t1BtnLunMinus1);
		});

		// 세번째 식사기록 -버튼을 눌렀을때의 액션
		t1BtnBrkMinus3.setOnAction(e -> {
			handlerT1BtnBrkMinusAction(t1txtBrk3, t1BtnBrk3, t1BtnBrkMinus3);
		});

		// 네번째 식사기록 +버튼을 눌렀을때의 액션
		t1BtnLun1.setOnAction(e -> {
			handlerT1BtnBrk1Action(t1txtLun1, t1txtLun2, t1BtnLun1, t1BtnLunMinus1, t1BtnLun2, t1BtnLunMinus2);
		});

		// 네번째 식사기록 -버튼을 눌렀을때의 액션
		t1BtnLunMinus1.setOnAction(e -> {
			handlerT1BtnBrkMinusAction(t1txtLun1, t1BtnLun1, t1BtnLunMinus1);
		});

		// 다섯번째 식사기록 +버튼을 눌렀을때의 액션
		t1BtnLun2.setOnAction(e -> {
			handlerT1BtnBrk1Action(t1txtLun2, t1txtLun3, t1BtnLun2, t1BtnLunMinus2, t1BtnLun3, t1BtnLunMinus3);
		});

		// 다섯번째 식사기록 -버튼을 눌렀을때의 액션
		t1BtnLunMinus2.setOnAction(e -> {
			handlerT1BtnBrkMinusAction(t1txtLun2, t1BtnLun2, t1BtnLunMinus2);
		});

		// 여섯번째 식사기록 +버튼을 눌렀을때의 액션
		t1BtnLun3.setOnAction(e -> {
			handlerT1BtnLun3Action();
		});

		// 여섯번째 식사기록 -버튼을 눌렀을때의 액션
		t1BtnLunMinus3.setOnAction(e -> {
			handlerT1BtnBrkMinusAction(t1txtLun3, t1BtnLun3, t1BtnLunMinus3);
		});

		// 첫번째 운동기록 +버튼을 눌렀을때의 액션
		t1BtnDin1.setOnAction(e -> {
			handlerT1BtnDin1Action(t1txtDin1, t1txtDin2, t1BtnDin1, t1BtnDinMinus1, t1BtnDin2, t1BtnDinMinus2);
		});

		// 첫번째 운동기록 -버튼을 눌렀을때의 액션
		t1BtnDinMinus1.setOnAction(e -> {
			handlerT1BtnBrkMinusAction(t1txtDin1, t1BtnDin1, t1BtnDinMinus1);
		});

		// 두번째 운동기록 +버튼을 눌렀을때의 액션
		t1BtnDin2.setOnAction(e -> {
			handlerT1BtnDin1Action(t1txtDin2, t1txtDin3, t1BtnDin2, t1BtnDinMinus2, t1BtnDin3, t1BtnDinMinus3);
		});

		// 두번째 운동기록 -버튼을 눌렀을때의 액션
		t1BtnDinMinus2.setOnAction(e -> {
			handlerT1BtnBrkMinusAction(t1txtDin2, t1BtnDin2, t1BtnDinMinus2);
		});

		// 세번째 운동기록 +버튼을 눌렀을때의 액션
		t1BtnDin3.setOnAction(e -> {
			handlerT1BtnDin1Action(t1txtDin3, t1txtEtc1, t1BtnDin3, t1BtnDinMinus3, t1BtnEtc1, t1BtnEtcMinus1);
		});

		// 세번째 운동기록 -버튼을 눌렀을때의 액션
		t1BtnDinMinus3.setOnAction(e -> {
			handlerT1BtnBrkMinusAction(t1txtDin3, t1BtnDin3, t1BtnDinMinus3);
		});

		// 네번째 운동기록 +버튼을 눌렀을때의 액션
		t1BtnEtc1.setOnAction(e -> {
			handlerT1BtnDin1Action(t1txtEtc1, t1txtEtc2, t1BtnEtc1, t1BtnEtcMinus1, t1BtnEtc2, t1BtnEtcMinus2);
		});

		// 네번째 운동기록 -버튼을 눌렀을때의 액션
		t1BtnEtcMinus1.setOnAction(e -> {
			handlerT1BtnBrkMinusAction(t1txtEtc1, t1BtnEtc1, t1BtnEtcMinus1);
		});

		// 다섯번째 운동기록 +버튼을 눌렀을때의 액션
		t1BtnEtc2.setOnAction(e -> {
			handlerT1BtnDin1Action(t1txtEtc2, t1txtEtc3, t1BtnEtc2, t1BtnEtcMinus2, t1BtnEtc3, t1BtnEtcMinus3);
		});

		// 다섯번째 운동기록 -버튼을 눌렀을때의 액션
		t1BtnEtcMinus2.setOnAction(e -> {
			handlerT1BtnBrkMinusAction(t1txtEtc2, t1BtnEtc2, t1BtnEtcMinus2);
		});

		// 여섯번째 운동기록 +버튼을 눌렀을때의 액션
		t1BtnEtc3.setOnAction(e -> {
			handlerT1BtnEtc3Action();
		});

		// 여섯번째 운동기록 -버튼을 눌렀을때의 액션
		t1BtnEtcMinus3.setOnAction(e -> {
			handlerT1BtnBrkMinusAction(t1txtEtc3, t1BtnEtc3, t1BtnEtcMinus3);
		});

		// =========================칼로리리포트==========================

		// 칼로리리포트 탭을 클릭시 액션(바차트)
		t3Tab.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue) {
					handlerT3TabClickAction();
				}
			}
		});

		// =========================체중변화==========================

		// 체중 저장 버튼 액션
		t4BtnWeightSave.setOnAction(e -> {
			handlerT4BtnWeightSaveAction();
		});

		// 체중변화 탭을 클릭시 액션(라인차트)
		t4Tab.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue) {
					handlerT4TabClickAction();
				}
			}
		});

	}

//=============================모듈화=============================

	// 로그인시 안내멘트
	private void startMainInfoMent() {
		callAlert("본 프로그램은 총 3개의 탭으로 이루어져있습니다: ");
		callAlert(
				"첫번째 탭 - '오늘의 기록' :오늘의 식사와 운동에 대한 기록을 하여 칼로리를 계산합니다.\n우측의 '오늘의 칼로리 계산'을 통해 실시간으로 칼로리를 계산 가능하며\n하단의 '오늘의 기록 저장'을 통해 반영됩니다.\n****주의사항****\n'오늘의 기록 저장'을 누르지 않은채 종료하면\n기록이 저장되지 않으니 유의하세요!!!");
		callAlert(
				"두번째 탭 - '칼로리 리포트' :오늘을 기준으로 최근 일주일의 칼로리에 대한 리포트 입니다.\n항상 확인하여 섭취 및 운동을 통한 칼로리 섭취, 소모에 대하여\n인지하시고 목표를 향해 정진하세요!!");
		callAlert("세번째 탭 - '체중변화' :하루에 한번 체중을 기록하세요.\n오늘을 기준으로 최근 일주일의 체중 변화를 보여줍니다.\n목표 체중을 향해 화이팅!!");
	}

	// 첫 화면 아이템 셋팅
	private void itemSettingFirst() {
		// =========================오늘의 기록=======================
		t1BtnBrk1.setDisable(false);
		t1txtBrk1.setDisable(true);
		t1txtBrk1.clear();
		t1BtnBrkMinus1.setDisable(true);
		t1txtBrk2.setVisible(false);
		t1txtBrk2.clear();
		t1txtBrk3.setVisible(false);
		t1txtBrk3.clear();
		t1BtnBrk2.setVisible(false);
		t1BtnBrk3.setVisible(false);
		t1BtnBrkMinus2.setVisible(false);
		t1BtnBrkMinus3.setVisible(false);
		t1DatePicker.setDisable(true);
		
		t1txtLun1.setVisible(false);
		t1txtLun1.clear();
		t1txtLun2.setVisible(false);
		t1txtLun2.clear();
		t1txtLun3.setVisible(false);
		t1txtLun3.clear();
		t1BtnLun1.setVisible(false);
		t1BtnLun2.setVisible(false);
		t1BtnLun3.setVisible(false);
		t1BtnLunMinus1.setVisible(false);
		t1BtnLunMinus2.setVisible(false);
		t1BtnLunMinus3.setVisible(false);

		t1BtnDin1.setDisable(false);
		t1txtDin1.setDisable(true);
		t1txtDin1.clear();
		t1BtnDinMinus1.setDisable(true);
		t1txtDin2.setVisible(false);
		t1txtDin2.clear();
		t1txtDin3.setVisible(false);
		t1txtDin3.clear();
		t1BtnDin2.setVisible(false);
		t1BtnDin3.setVisible(false);
		t1BtnDinMinus2.setVisible(false);
		t1BtnDinMinus3.setVisible(false);

		t1txtEtc1.setVisible(false);
		t1txtEtc1.clear();
		t1txtEtc2.setVisible(false);
		t1txtEtc2.clear();
		t1txtEtc3.setVisible(false);
		t1txtEtc3.clear();
		t1BtnEtc1.setVisible(false);
		t1BtnEtc2.setVisible(false);
		t1BtnEtc3.setVisible(false);
		t1BtnEtcMinus1.setVisible(false);
		t1BtnEtcMinus2.setVisible(false);
		t1BtnEtcMinus3.setVisible(false);
		LocalDateTime ldt1 = LocalDateTime.now();
		t1DatePicker.setValue(ldt1.toLocalDate());

		// =========================칼로리리포트=======================

		// =========================체중변화=======================
		inputDecimalFormat(t4txtTodayWeight);
	}

	// 지금까지 식사기록 보기 버튼 눌렀을때 액션
	private void handlerT1BtnFoodRecordAction() {
		if (!userCalRecordList.isEmpty() && !userCalArrayList.isEmpty()) {
			userCalRecordList.clear();
			userCalArrayList.clear();
		}
		Stage recordStage;
		Parent root;
		try {
			recordStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/food_record.fxml"));
			root = loader.load();
			TableView<UserCal> frTableView = (TableView) root.lookup("#frTableView");

			TableColumn tcDay = frTableView.getColumns().get(0);
			tcDay.setCellValueFactory(new PropertyValueFactory<>("day"));
			tcDay.setStyle("-fx-alignment: CENTER;");

			TableColumn tcFoodname = frTableView.getColumns().get(1);
			tcFoodname.setCellValueFactory(new PropertyValueFactory<>("foodname"));
			tcFoodname.setStyle("-fx-alignment: CENTER;");

			TableColumn tcPluscal = frTableView.getColumns().get(2);
			tcPluscal.setCellValueFactory(new PropertyValueFactory<>("plusCal"));
			tcPluscal.setStyle("-fx-alignment: CENTER;");

			userCalArrayList = UserCalDAO.getUserPlusCalRecord(loginUserMan.get(0).getUserID());
			for (UserCal uc : userCalArrayList) {
				userCalRecordList.add(uc);
			}
			frTableView.setItems(userCalRecordList);

			Scene scene = new Scene(root);
			recordStage.setScene(scene);
			recordStage.show();

		} catch (Exception e) {

		}

	}

	// 지금까지 운동기록 보기 버튼 눌렀을때 액션
	private void handlerT1BtnExerRecordAction() {
		if (!userMinusCalRecordList.isEmpty() && !userMinusCalArrayList.isEmpty()) {
			userMinusCalRecordList.clear();
			userMinusCalArrayList.clear();
		}
		Stage recordStage;
		Parent root;
		try {
			recordStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/food_record.fxml"));
			root = loader.load();
			TableView<UserCal> frTableView = (TableView) root.lookup("#frTableView");

			TableColumn tcDay = frTableView.getColumns().get(0);
			tcDay.setCellValueFactory(new PropertyValueFactory<>("day"));
			tcDay.setStyle("-fx-alignment: CENTER;");

			TableColumn tcExername = frTableView.getColumns().get(1);
			tcExername.setCellValueFactory(new PropertyValueFactory<>("exername"));
			tcExername.setStyle("-fx-alignment: CENTER;");

			TableColumn tcMinuscal = frTableView.getColumns().get(2);
			tcMinuscal.setCellValueFactory(new PropertyValueFactory<>("minusCal"));
			tcMinuscal.setStyle("-fx-alignment: CENTER;");

			userMinusCalArrayList = UserCalDAO.getUserMinusCalRecord(loginUserMan.get(0).getUserID());
			for (UserCal uc : userMinusCalArrayList) {
				userMinusCalRecordList.add(uc);
			}
			frTableView.setItems(userMinusCalRecordList);

			Scene scene = new Scene(root);
			recordStage.setScene(scene);
			recordStage.show();
		} catch (Exception e) {

		}

	}

	// 오늘의 기록 저장 버튼 눌렀을때 액션
	private void handlerT1BtnTodaySaveAction() {
		Date day = Date.valueOf(t1DatePicker.getValue());
		if (t1BtnBrk1.isDisable()) {
			String[] split = t1txtBrk1.getText().split(":");
			String calValue = split[1];
			UserCal usercal = new UserCal(loginUserMan.get(0).getUserID(), day, t1txtBrk1.getText(), calValue, null,
					null);
			UserCalDAO.insertUserCalData(usercal);
			callAlert("오늘의 식사기록 저장완료!:");
		} else {
			callAlert("저장할 식사기록이 없습니다: 확인부탁드려요^^");
		}
		if (t1BtnBrk2.isDisable()) {
			String[] split = t1txtBrk2.getText().split(":");
			String calValue = split[1];
			UserCal usercal = new UserCal(loginUserMan.get(0).getUserID(), day, t1txtBrk2.getText(), calValue, null,
					null);
			UserCalDAO.insertUserCalData(usercal);
		}
		if (t1BtnBrk3.isDisable()) {
			String[] split = t1txtBrk3.getText().split(":");
			String calValue = split[1];
			UserCal usercal = new UserCal(loginUserMan.get(0).getUserID(), day, t1txtBrk3.getText(), calValue, null,
					null);
			UserCalDAO.insertUserCalData(usercal);
		}
		if (t1BtnLun1.isDisable()) {
			String[] split = t1txtLun1.getText().split(":");
			String calValue = split[1];
			UserCal usercal = new UserCal(loginUserMan.get(0).getUserID(), day, t1txtLun1.getText(), calValue, null,
					null);
			UserCalDAO.insertUserCalData(usercal);
		}
		if (t1BtnLun2.isDisable()) {
			String[] split = t1txtLun2.getText().split(":");
			String calValue = split[1];
			UserCal usercal = new UserCal(loginUserMan.get(0).getUserID(), day, t1txtLun2.getText(), calValue, null,
					null);
			UserCalDAO.insertUserCalData(usercal);
		}
		if (t1BtnLun3.isDisable()) {
			String[] split = t1txtLun3.getText().split(":");
			String calValue = split[1];
			UserCal usercal = new UserCal(loginUserMan.get(0).getUserID(), day, t1txtLun3.getText(), calValue, null,
					null);
			UserCalDAO.insertUserCalData(usercal);
		}

		if (t1BtnDin1.isDisable()) {
			String[] split = t1txtDin1.getText().split(":");
			String calValue = split[1];
			UserCal usercal = new UserCal(loginUserMan.get(0).getUserID(), day, null, null, t1txtDin1.getText(),
					calValue);
			UserCalDAO.insertUserCalData(usercal);
			callAlert("오늘의 운동기록 저장완료!:");
		} else {
			callAlert("저장할 운동기록이 없습니다: 확인부탁드려요^^");
		}
		if (t1BtnDin2.isDisable()) {
			String[] split = t1txtDin2.getText().split(":");
			String calValue = split[1];
			UserCal usercal = new UserCal(loginUserMan.get(0).getUserID(), day, null, null, t1txtDin2.getText(),
					calValue);
			UserCalDAO.insertUserCalData(usercal);
		}
		if (t1BtnDin3.isDisable()) {
			String[] split = t1txtDin3.getText().split(":");
			String calValue = split[1];
			UserCal usercal = new UserCal(loginUserMan.get(0).getUserID(), day, null, null, t1txtDin3.getText(),
					calValue);
			UserCalDAO.insertUserCalData(usercal);
		}
		if (t1BtnEtc1.isDisable()) {
			String[] split = t1txtEtc1.getText().split(":");
			String calValue = split[1];
			UserCal usercal = new UserCal(loginUserMan.get(0).getUserID(), day, null, null, t1txtEtc1.getText(),
					calValue);
			UserCalDAO.insertUserCalData(usercal);
		}
		if (t1BtnEtc2.isDisable()) {
			String[] split = t1txtEtc2.getText().split(":");
			String calValue = split[1];
			UserCal usercal = new UserCal(loginUserMan.get(0).getUserID(), day, null, null, t1txtEtc2.getText(),
					calValue);
			UserCalDAO.insertUserCalData(usercal);
		}
		if (t1BtnEtc3.isDisable()) {
			String[] split = t1txtEtc3.getText().split(":");
			String calValue = split[1];
			UserCal usercal = new UserCal(loginUserMan.get(0).getUserID(), day, null, null, t1txtEtc3.getText(),
					calValue);
			UserCalDAO.insertUserCalData(usercal);
		}
		itemSettingFirst();
	}

	// 오늘의 칼로리 계산 버튼 눌렀을때 액션
	private void handlerT1BtnTodayCalAction() {
		t1LabelLimit.setText(String.valueOf(loginUserMan.get(0).getKeepCalorie()));
		int sum = 0;
		int sum2 = 0;
		sum = UserCalDAO.getUserPlusCalData(loginUserMan.get(0).getUserID(), String.valueOf(t1DatePicker.getValue()));
		sum2 = UserCalDAO.getUserMinusCalData(loginUserMan.get(0).getUserID(), String.valueOf(t1DatePicker.getValue()));
		t1LabelFire.setText(String.valueOf(sum2));
		t1LabelEat.setText(String.valueOf(sum));
		t1LabelCan.setText(String.valueOf(loginUserMan.get(0).getKeepCalorie() - sum + sum2));
	}

	// 식사기록 +버튼을 눌렀을때의 액션
	private void handlerT1BtnBrk1Action(TextField t1, TextField t2, Button b1, Button b2, Button b3, Button b4) {
		if (!foodTableList.isEmpty() && !dbArrayList.isEmpty()) {
			foodTableList.clear();
			dbArrayList.clear();
		}
		Stage foodStage;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/eat_food.fxml"));
		Parent root;
		try {
			foodStage = new Stage();
			root = loader.load();

			// ===================ID등록================================
			TableView<Food> foodTableView = (TableView) root.lookup("#foodTableView");

			TextField foodTxtSearch = (TextField) root.lookup("#foodTxtSearch");
			Button foodBtnSearch = (Button) root.lookup("#foodBtnSearch");
			Button foodBtnCalTotalCal = (Button) root.lookup("#foodBtnCalTotalCal");
			Button foodBtnSave = (Button) root.lookup("#foodBtnSave");
			Button foodBtnDel = (Button) root.lookup("#foodBtnDel");
			Button foodBtnExit = (Button) root.lookup("#foodBtnExit");
			Button foodBtnRefresh = (Button) root.lookup("#foodBtnRefresh");

			ComboBox<String> foodCmbKind = (ComboBox) root.lookup("#foodCmbKind");
			ComboBox<String> foodCmbKategorie = (ComboBox) root.lookup("#foodCmbKategorie");

			TextField foodTxtNo = (TextField) root.lookup("#foodTxtNo");
			TextField foodTxtPkNo = (TextField) root.lookup("#foodTxtPkNo");
			TextField foodTxtName = (TextField) root.lookup("#foodTxtName");
			TextField foodTxtCal = (TextField) root.lookup("#foodTxtCal");
			inputDecimalFormat(foodTxtCal);
			TextField foodTxtVolume = (TextField) root.lookup("#foodTxtVolume");
			inputDecimalFormat(foodTxtVolume);
			TextField foodTxtTotalCal = (TextField) root.lookup("#foodTxtTotalCal");

			Button foodBtnSelect = (Button) root.lookup("#foodBtnSelect");
			Button foodBtnInput = (Button) root.lookup("#foodBtnInput");
			Button foodBtnCorInput = (Button) root.lookup("#foodBtnCorInput");
			foodBtnCorInput.setVisible(false);

			// ================테이블뷰 컬럼 세팅====================
			TableColumn tcKind = foodTableView.getColumns().get(0);
			tcKind.setCellValueFactory(new PropertyValueFactory<>("kind"));
			tcKind.setStyle("-fx-alignment: CENTER;");

			TableColumn tcKategorie = foodTableView.getColumns().get(1);
			tcKategorie.setCellValueFactory(new PropertyValueFactory<>("kategorie"));
			tcKind.setStyle("-fx-alignment: CENTER;");

			TableColumn tcNo = foodTableView.getColumns().get(2);
			tcNo.setCellValueFactory(new PropertyValueFactory<>("no"));
			tcNo.setStyle("-fx-alignment: CENTER;");

			TableColumn tcPkno = foodTableView.getColumns().get(3);
			tcPkno.setCellValueFactory(new PropertyValueFactory<>("pkno"));
			tcPkno.setStyle("-fx-alignment: CENTER;");

			TableColumn tcName = foodTableView.getColumns().get(4);
			tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
			tcName.setStyle("-fx-alignment: CENTER;");

			TableColumn tcCaloriePerGram = foodTableView.getColumns().get(5);
			tcCaloriePerGram.setCellValueFactory(new PropertyValueFactory<>("caloriePerGram"));
			tcCaloriePerGram.setStyle("-fx-alignment: CENTER;");

			TableColumn tcVolume = foodTableView.getColumns().get(6);
			tcVolume.setCellValueFactory(new PropertyValueFactory<>("volume"));
			tcVolume.setStyle("-fx-alignment: CENTER;");

			TableColumn tcTotalCal = foodTableView.getColumns().get(7);
			tcTotalCal.setCellValueFactory(new PropertyValueFactory<>("totalCal"));
			tcTotalCal.setStyle("-fx-alignment: CENTER;");

			dbArrayList = FoodDAO.getFoodData();
			for (Food food : dbArrayList) {
				foodTableList.add(food);
			}
			foodTableView.setItems(foodTableList);

			// ================처음화면 필드들 세팅======================
			foodBtnCalTotalCal.setDisable(true);
			foodTxtNo.setDisable(true);
			foodTxtPkNo.setDisable(true);
			foodTxtName.setDisable(true);
			foodTxtCal.setDisable(true);
			foodTxtTotalCal.setDisable(true);
			foodCmbKind.setDisable(true);
			foodCmbKategorie.setDisable(true);
			foodTxtVolume.setDisable(true);
			foodBtnSave.setDisable(true);

			// ================콤보박스 세팅===========================
			if(!foodCmbKindList.isEmpty()) {
				foodCmbKindList.clear();
			}
			foodCmbKindList.add("1)간식");
			foodCmbKindList.add("2)육류");
			foodCmbKindList.add("3)식사대용(빵, 시리얼 등)");
			foodCmbKindList.add("4)식사류");
			foodCmbKindList.add("5)기타");
			foodCmbKind.setItems(foodCmbKindList);

			if(!foodCmbKategorieList1.isEmpty()) {
				foodCmbKategorieList1.clear();
			}
			foodCmbKategorieList1.add("1)감자칩");
			foodCmbKategorieList1.add("2)그래놀라바");
			foodCmbKategorieList1.add("3)샌드위치");
			foodCmbKategorieList1.add("4)기타");
			foodCmbKategorie.setItems(foodCmbKategorieList1);
			foodCmbKind.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (newValue != null) {
						switch (newValue.toString()) {
						case "1)간식":
							firstPkno = "1";
							foodCmbKategorie.setItems(foodCmbKategorieList1);
							break;
						case "2)육류":
							ObservableList<String> foodCmbKategorieList2 = FXCollections.observableArrayList();
							firstPkno = "2";
							foodCmbKategorieList2.add("1)닭가슴살");
							foodCmbKategorieList2.add("2)돼지고기");
							foodCmbKategorieList2.add("3)쇠고기");
							foodCmbKategorieList2.add("4)기타");
							foodCmbKategorie.setItems(foodCmbKategorieList2);
							break;
						case "3)식사대용(빵, 시리얼 등)":
							ObservableList<String> foodCmbKategorieList3 = FXCollections.observableArrayList();
							firstPkno = "3";
							foodCmbKategorieList3.add("1)시리얼");
							foodCmbKategorieList3.add("2)토스트");
							foodCmbKategorieList3.add("3)빵");
							foodCmbKategorieList3.add("4)기타");
							foodCmbKategorie.setItems(foodCmbKategorieList3);
							break;
						case "4)식사류":
							ObservableList<String> foodCmbKategorieList4 = FXCollections.observableArrayList();
							firstPkno = "4";
							foodCmbKategorieList4.add("1)국수");
							foodCmbKategorieList4.add("2)파스타");
							foodCmbKategorieList4.add("3)쌀");
							foodCmbKategorieList4.add("4)기타");
							foodCmbKategorie.setItems(foodCmbKategorieList4);
							break;
						case "5)기타":
							ObservableList<String> foodCmbKategorieList5 = FXCollections.observableArrayList();
							firstPkno = "5";
							foodCmbKategorieList5.add("1)기타");
							foodCmbKategorie.setItems(foodCmbKategorieList5);
							break;
						}
					}
				}
			});

			// ================테이블뷰를 눌렀을때 세팅====================
			foodTableView.setOnMouseClicked(e -> {
				selectedFoodIndex = foodTableView.getSelectionModel().getSelectedIndex();
				selectedFood = foodTableView.getSelectionModel().getSelectedItem();
				foodTxtNo.setText(String.valueOf(selectedFood.getNo()));
				foodTxtNo.setDisable(true);
				foodTxtPkNo.setText(String.valueOf(selectedFood.getPkno()));
				foodTxtPkNo.setDisable(true);
				foodTxtName.setText(selectedFood.getName());
				foodTxtName.setDisable(true);
				foodTxtCal.setText(String.valueOf(selectedFood.getCaloriePerGram()));
				foodTxtCal.setDisable(true);
				foodTxtTotalCal.setDisable(true);
				foodTxtTotalCal.clear();
				foodCmbKind.setValue(selectedFood.getKind());
				foodCmbKind.setDisable(true);
				foodCmbKategorie.setValue(selectedFood.getKategorie());
				foodCmbKategorie.setDisable(true);
				foodBtnCalTotalCal.setDisable(false);
				foodTxtVolume.setDisable(false);
				foodBtnDel.setDisable(false);
			});

			// ================새로고침 버튼 클릭시====================
			foodBtnRefresh.setOnAction(e -> {
				foodTableView.refresh();
				foodTableView.setItems(foodTableList);
				foodBtnCalTotalCal.setDisable(true);
				foodTxtNo.setDisable(true);
				foodTxtNo.clear();
				foodTxtPkNo.setDisable(true);
				foodTxtPkNo.clear();
				foodTxtName.setDisable(true);
				foodTxtName.clear();
				foodTxtCal.setDisable(true);
				foodTxtCal.clear();
				foodTxtTotalCal.setDisable(true);
				foodTxtTotalCal.clear();
				foodCmbKind.setDisable(true);
				foodCmbKategorie.setDisable(true);
				foodTxtVolume.setDisable(true);
				foodTxtVolume.clear();
				foodBtnSave.setDisable(true);
				foodBtnDel.setDisable(true);
			});

			// ================칼로리 계산 안내 버튼 클릭시====================
			foodBtnSelect.setOnAction(e -> {
				callAlert("섭취한 음식의 총 칼로리를 계산합니다:음식을 선택한 후 섭취량을 g단위로 입력하시고" + "\n하단의 '총 칼로리 계산하기'버튼을 누르시면"
						+ "\n총 칼로리가 자동 계산되어 입력됩니다.\n" + "입력한 내용이 맞으시면 제일 하단의 " + "\n'저장' 버튼을 누르면 입력이 완료됩니다.");
				foodTableView.refresh();
				foodTableView.setItems(foodTableList);
				foodBtnCalTotalCal.setDisable(true);
				foodTxtNo.setDisable(true);
				foodTxtNo.clear();
				foodTxtPkNo.setDisable(true);
				foodTxtPkNo.clear();
				foodTxtName.setDisable(true);
				foodTxtName.clear();
				foodTxtCal.setDisable(true);
				foodTxtCal.clear();
				foodTxtTotalCal.setDisable(true);
				foodTxtTotalCal.clear();
				foodCmbKind.setDisable(true);
				foodCmbKategorie.setDisable(true);
				foodTxtVolume.setDisable(true);
				foodTxtVolume.clear();
				foodBtnSave.setDisable(true);
			});

			// ================음식 직접 입력 버튼 클릭시====================
			foodBtnInput.setOnAction(e -> {
				callAlert("섭취한 음식을 직접 입력합니다:섭취한 음식의 정보를 확인하신 후" + "\n해당 내용을 입력해주세요" + "\n정보를 모두 입력하시고 하단에\n"
						+ "'음식 저장' 버튼을 누르시면 입력이 완료됩니다. " + "\n저장한 음식을 다시 검색 후 선택한 뒤\n총 칼로리 계산을 진행해주세요.");
				foodTableView.refresh();
				foodTableView.setItems(foodTableList);
				foodCmbKind.setValue(foodCmbKindList.get(0));
				foodCmbKind.setDisable(false);
				foodBtnCorInput.setVisible(true);
				foodCmbKategorie.setDisable(false);
				foodTxtNo.clear();
				foodTxtNo.setDisable(true);
				foodTxtPkNo.clear();
				foodTxtPkNo.setDisable(true);
				foodTxtName.setDisable(false);
				foodTxtCal.clear();
				foodTxtCal.setDisable(false);
				foodTxtVolume.clear();
				foodTxtVolume.setDisable(true);
				foodBtnCalTotalCal.setDisable(true);
				foodTxtTotalCal.setDisable(true);
				foodTxtName.clear();
				foodBtnDel.setDisable(true);

				foodCmbKategorie.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						foodTxtPkNo.clear();
						foodTxtNo.clear();
						if (newValue != null) {
							switch (newValue.toString()) {
							case "1)감자칩":
							case "1)닭가슴살":
							case "1)시리얼":
							case "1)국수":
							case "1)기타":
								secondPkno = "1";
								break;
							case "2)그래놀라바":
							case "2)돼지고기":
							case "2)토스트":
							case "2)파스타":
								secondPkno = "2";
								break;
							case "3)샌드위치":
							case "3)쇠고기":
							case "3)빵":
							case "3)쌀":
								secondPkno = "3";
								break;
							case "4)기타":
								secondPkno = "4";
								break;
							}
							String pkno = null;
							int count = FoodDAO.getOneOneNo("'" + newValue.toString() + "'") + 1;
							foodTxtNo.setText(String.valueOf(count));
							if (foodTxtNo.getText().length() == 1) {
								pkno = firstPkno + secondPkno + "0" + "0" + foodTxtNo.getText();
								count = 0;
							} else {
								pkno = firstPkno + secondPkno + "0" + foodTxtNo.getText();
								count = 0;
							}
							foodTxtPkNo.setText(pkno);
						}
					}
				});
			});

			// ================음식 검색 기능====================
			foodBtnSearch.setOnAction(e -> {
				ObservableList<Food> foodTableListSearch = FXCollections.observableArrayList();
				foodTableListSearch.clear();
				for (Food food : foodTableList) {
					if (food.getKind().contains(foodTxtSearch.getText())
							|| food.getKategorie().contains(foodTxtSearch.getText())
							|| food.getName().contains(foodTxtSearch.getText())) {
						foodTableListSearch.add(food);
					}
				}
				foodTableView.setItems(foodTableListSearch);
				foodTxtSearch.clear();
			});

			// ================총 칼로리 계산 버튼 클릭시====================
			foodBtnCalTotalCal.setOnAction(e -> {
				if (foodTxtVolume.getText() != null) {
					double calValue = Integer.parseInt(foodTxtCal.getText()) / 100.0;
					double dValue = calValue * Double.parseDouble(foodTxtVolume.getText());
					int value = (int) Math.round(dValue);
					foodTxtVolume.setDisable(true);
					foodTxtTotalCal.setText(String.valueOf(value));
					foodBtnDel.setDisable(true);
					foodBtnSave.setDisable(false);
					callAlert("총 칼로리 계산 완료^^:입력하신 내용이 맞으시면 '저장'을 눌러주세요.\n내용을 잘못 입력하셨으면 '새로고침'을 눌러주세요.");
				} else {
					callAlert("섭취량을 입력 안하셨네요^^:섭취하신량을 대략적으로라도 입력해주세요!");
				}
			});

			// ================음식 저장 버튼 클릭시====================
			foodBtnCorInput.setOnAction(e -> {
				if (foodCmbKind.getSelectionModel().getSelectedItem() != null
						&& foodCmbKategorie.getSelectionModel().getSelectedItem() != null && foodTxtNo.getText() != null
						&& foodTxtPkNo.getText() != null && foodTxtName.getText() != null
						&& foodTxtCal.getText() != null) {
					Food food = new Food(foodCmbKind.getSelectionModel().getSelectedItem(),
							foodCmbKategorie.getSelectionModel().getSelectedItem(),
							Integer.parseInt(foodTxtNo.getText()), Integer.parseInt(foodTxtPkNo.getText()),
							foodTxtName.getText(), Integer.parseInt(foodTxtCal.getText()), 0, 0);
					FoodDAO.insertFoodData(food);
					foodTableList.add(food);
					callAlert("음식저장 성공^^:해당음식을 선택하셔서 총 칼로리를 계산해보세요!");
					foodBtnCorInput.setVisible(false);
				} else {
					callAlert("음식저장 실패!!:입력값을 확인해보세요");
				}
			});

			// ================저장 버튼 클릭시====================
			foodBtnSave.setOnAction(e -> {
				if (foodTxtName.getText() != null && foodTxtTotalCal.getText() != null) {
					callAlert("칼로리 저장이 완료되었습니다^^ :하단에 '오늘의 기록 저장'을 하셔야 '오늘의 칼로리 계산'이 가능합니다~");
					t1.setText(foodTxtName.getText() + ":" + foodTxtTotalCal.getText() + ":kcal");
					t2.setDisable(true);
					t2.setVisible(true);
					b1.setDisable(true);
					b2.setDisable(false);
					b3.setDisable(false);
					b3.setVisible(true);
					b4.setDisable(false);
					b4.setVisible(true);
					foodStage.close();
				} else {
					callAlert("항목이 빠졌습니다: 빠진 항목이 없는지 다시 체크해주세요^^");
				}
			});

			// ================삭제 버튼 클릭시====================
			foodBtnDel.setOnAction(e -> {
				selectedFoodIndex = foodTableView.getSelectionModel().getSelectedIndex();
				selectedFood = foodTableView.getSelectionModel().getSelectedItem();
				int value = FoodDAO.deleteFoodData(String.valueOf(selectedFood.getPkno()));
				if (value != 0) {
					foodTableList.remove(selectedFood);
					dbArrayList.remove(selectedFood);
					callAlert("삭제되었습니다^^:");
					foodTableView.refresh();
					foodTableView.setItems(foodTableList);
					foodBtnCalTotalCal.setDisable(true);
					foodTxtNo.setDisable(true);
					foodTxtNo.clear();
					foodTxtPkNo.setDisable(true);
					foodTxtPkNo.clear();
					foodTxtName.setDisable(true);
					foodTxtName.clear();
					foodTxtCal.setDisable(true);
					foodTxtCal.clear();
					foodTxtTotalCal.setDisable(true);
					foodTxtTotalCal.clear();
					foodCmbKind.setDisable(true);
					foodCmbKategorie.setDisable(true);
					foodTxtVolume.setDisable(true);
					foodTxtVolume.clear();
					foodBtnSave.setDisable(true);
					foodBtnDel.setDisable(true);
				} else {
					callAlert("삭제실패!!: ????????");
				}
			});

			// ================닫기 버튼 클릭시====================
			foodBtnExit.setOnAction(e -> foodStage.close());

			Scene scene = new Scene(root);
			foodStage.setScene(scene);
			foodStage.show();

			callAlert("섭취한 음식의 총 칼로리를 계산합니다:음식을 선택한 후 섭취량을 g단위로 입력하시고" + "\n하단의 '총 칼로리 계산하기'버튼을 누르시면"
					+ "\n총 칼로리가 자동 계산되어 입력됩니다.\n" + "입력한 내용이 맞으시면 제일 하단의 " + "\n'저장' 버튼을 누르면 입력이 완료됩니다.");

		} catch (IOException e) {
		}
	}

	// 식사기록 -버튼을 눌렀을때의 액션
	private void handlerT1BtnBrkMinusAction(TextField tf, Button plus, Button minus) {
		tf.clear();
		plus.setDisable(false);
		minus.setDisable(true);
		callAlert("해당기록이 삭제되었습니다:내용을 확인해주세요^^");
	}

	// 마지막 식사기록 +버튼을 눌렀을때의 액션
	private void handlerT1BtnLun3Action() {
		if (!foodTableList.isEmpty() && !dbArrayList.isEmpty()) {
			foodTableList.clear();
			dbArrayList.clear();
		}
		Stage foodStage;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/eat_food.fxml"));
		Parent root;
		try {
			foodStage = new Stage();
			root = loader.load();

			// ===================ID등록================================
			TableView<Food> foodTableView = (TableView) root.lookup("#foodTableView");

			TextField foodTxtSearch = (TextField) root.lookup("#foodTxtSearch");
			Button foodBtnSearch = (Button) root.lookup("#foodBtnSearch");
			Button foodBtnCalTotalCal = (Button) root.lookup("#foodBtnCalTotalCal");
			Button foodBtnSave = (Button) root.lookup("#foodBtnSave");
			Button foodBtnDel = (Button) root.lookup("#foodBtnDel");
			Button foodBtnExit = (Button) root.lookup("#foodBtnExit");
			Button foodBtnRefresh = (Button) root.lookup("#foodBtnRefresh");

			ComboBox<String> foodCmbKind = (ComboBox) root.lookup("#foodCmbKind");
			ComboBox<String> foodCmbKategorie = (ComboBox) root.lookup("#foodCmbKategorie");

			TextField foodTxtNo = (TextField) root.lookup("#foodTxtNo");
			TextField foodTxtPkNo = (TextField) root.lookup("#foodTxtPkNo");
			TextField foodTxtName = (TextField) root.lookup("#foodTxtName");
			TextField foodTxtCal = (TextField) root.lookup("#foodTxtCal");
			inputDecimalFormat(foodTxtCal);
			TextField foodTxtVolume = (TextField) root.lookup("#foodTxtVolume");
			inputDecimalFormat(foodTxtVolume);
			TextField foodTxtTotalCal = (TextField) root.lookup("#foodTxtTotalCal");

			Button foodBtnSelect = (Button) root.lookup("#foodBtnSelect");
			Button foodBtnInput = (Button) root.lookup("#foodBtnInput");
			Button foodBtnCorInput = (Button) root.lookup("#foodBtnCorInput");
			foodBtnCorInput.setVisible(false);

			// ================테이블뷰 컬럼 세팅====================
			TableColumn tcKind = foodTableView.getColumns().get(0);
			tcKind.setCellValueFactory(new PropertyValueFactory<>("kind"));
			tcKind.setStyle("-fx-alignment: CENTER;");

			TableColumn tcKategorie = foodTableView.getColumns().get(1);
			tcKategorie.setCellValueFactory(new PropertyValueFactory<>("kategorie"));
			tcKind.setStyle("-fx-alignment: CENTER;");

			TableColumn tcNo = foodTableView.getColumns().get(2);
			tcNo.setCellValueFactory(new PropertyValueFactory<>("no"));
			tcNo.setStyle("-fx-alignment: CENTER;");

			TableColumn tcPkno = foodTableView.getColumns().get(3);
			tcPkno.setCellValueFactory(new PropertyValueFactory<>("pkno"));
			tcPkno.setStyle("-fx-alignment: CENTER;");

			TableColumn tcName = foodTableView.getColumns().get(4);
			tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
			tcName.setStyle("-fx-alignment: CENTER;");

			TableColumn tcCaloriePerGram = foodTableView.getColumns().get(5);
			tcCaloriePerGram.setCellValueFactory(new PropertyValueFactory<>("caloriePerGram"));
			tcCaloriePerGram.setStyle("-fx-alignment: CENTER;");

			TableColumn tcVolume = foodTableView.getColumns().get(6);
			tcVolume.setCellValueFactory(new PropertyValueFactory<>("volume"));
			tcVolume.setStyle("-fx-alignment: CENTER;");

			TableColumn tcTotalCal = foodTableView.getColumns().get(7);
			tcTotalCal.setCellValueFactory(new PropertyValueFactory<>("totalCal"));
			tcTotalCal.setStyle("-fx-alignment: CENTER;");

			dbArrayList = FoodDAO.getFoodData();
			for (Food food : dbArrayList) {
				foodTableList.add(food);
			}
			foodTableView.setItems(foodTableList);

			// ================처음화면 필드들 세팅======================
			foodBtnCalTotalCal.setDisable(true);
			foodTxtNo.setDisable(true);
			foodTxtPkNo.setDisable(true);
			foodTxtName.setDisable(true);
			foodTxtCal.setDisable(true);
			foodTxtTotalCal.setDisable(true);
			foodCmbKind.setDisable(true);
			foodCmbKategorie.setDisable(true);
			foodTxtVolume.setDisable(true);
			foodBtnSave.setDisable(true);

			// ================콤보박스 세팅===========================
			if(!foodCmbKindList.isEmpty()) {
				foodCmbKindList.clear();
			}
			foodCmbKindList.add("1)간식");
			foodCmbKindList.add("2)육류");
			foodCmbKindList.add("3)식사대용(빵, 시리얼 등)");
			foodCmbKindList.add("4)식사류");
			foodCmbKindList.add("5)기타");
			foodCmbKind.setItems(foodCmbKindList);

			if(!foodCmbKategorieList1.isEmpty()) {
				foodCmbKategorieList1.clear();
			}
			foodCmbKategorieList1.add("1)감자칩");
			foodCmbKategorieList1.add("2)그래놀라바");
			foodCmbKategorieList1.add("3)샌드위치");
			foodCmbKategorieList1.add("4)기타");
			foodCmbKategorie.setItems(foodCmbKategorieList1);
			foodCmbKind.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (newValue != null) {
						switch (newValue.toString()) {
						case "1)간식":
							firstPkno = "1";
							foodCmbKategorie.setItems(foodCmbKategorieList1);
							break;
						case "2)육류":
							ObservableList<String> foodCmbKategorieList2 = FXCollections.observableArrayList();
							firstPkno = "2";
							foodCmbKategorieList2.add("1)닭가슴살");
							foodCmbKategorieList2.add("2)돼지고기");
							foodCmbKategorieList2.add("3)쇠고기");
							foodCmbKategorieList2.add("4)기타");
							foodCmbKategorie.setItems(foodCmbKategorieList2);
							break;
						case "3)식사대용(빵, 시리얼 등)":
							ObservableList<String> foodCmbKategorieList3 = FXCollections.observableArrayList();
							firstPkno = "3";
							foodCmbKategorieList3.add("1)시리얼");
							foodCmbKategorieList3.add("2)토스트");
							foodCmbKategorieList3.add("3)빵");
							foodCmbKategorieList3.add("4)기타");
							foodCmbKategorie.setItems(foodCmbKategorieList3);
							break;
						case "4)식사류":
							ObservableList<String> foodCmbKategorieList4 = FXCollections.observableArrayList();
							firstPkno = "4";
							foodCmbKategorieList4.add("1)국수");
							foodCmbKategorieList4.add("2)파스타");
							foodCmbKategorieList4.add("3)쌀");
							foodCmbKategorieList4.add("4)기타");
							foodCmbKategorie.setItems(foodCmbKategorieList4);
							break;
						case "5)기타":
							ObservableList<String> foodCmbKategorieList5 = FXCollections.observableArrayList();
							firstPkno = "5";
							foodCmbKategorieList5.add("1)기타");
							foodCmbKategorie.setItems(foodCmbKategorieList5);
							break;
						}
					}
				}
			});

			// ================테이블뷰를 눌렀을때 세팅====================
			foodTableView.setOnMouseClicked(e -> {
				selectedFoodIndex = foodTableView.getSelectionModel().getSelectedIndex();
				selectedFood = foodTableView.getSelectionModel().getSelectedItem();
				foodTxtNo.setText(String.valueOf(selectedFood.getNo()));
				foodTxtNo.setDisable(true);
				foodTxtPkNo.setText(String.valueOf(selectedFood.getPkno()));
				foodTxtPkNo.setDisable(true);
				foodTxtName.setText(selectedFood.getName());
				foodTxtName.setDisable(true);
				foodTxtCal.setText(String.valueOf(selectedFood.getCaloriePerGram()));
				foodTxtCal.setDisable(true);
				foodTxtTotalCal.setDisable(true);
				foodTxtTotalCal.clear();
				foodCmbKind.setValue(selectedFood.getKind());
				foodCmbKind.setDisable(true);
				foodCmbKategorie.setValue(selectedFood.getKategorie());
				foodCmbKategorie.setDisable(true);
				foodBtnCalTotalCal.setDisable(false);
				foodTxtVolume.setDisable(false);
				foodBtnDel.setDisable(false);
			});

			// ================새로고침 버튼 클릭시====================
			foodBtnRefresh.setOnAction(e -> {
				foodTableView.refresh();
				foodTableView.setItems(foodTableList);
				foodBtnCalTotalCal.setDisable(true);
				foodTxtNo.setDisable(true);
				foodTxtNo.clear();
				foodTxtPkNo.setDisable(true);
				foodTxtPkNo.clear();
				foodTxtName.setDisable(true);
				foodTxtName.clear();
				foodTxtCal.setDisable(true);
				foodTxtCal.clear();
				foodTxtTotalCal.setDisable(true);
				foodTxtTotalCal.clear();
				foodCmbKind.setDisable(true);
				foodCmbKategorie.setDisable(true);
				foodTxtVolume.setDisable(true);
				foodTxtVolume.clear();
				foodBtnSave.setDisable(true);
				foodBtnDel.setDisable(true);
			});

			// ================칼로리 계산 안내 버튼 클릭시====================
			foodBtnSelect.setOnAction(e -> {
				callAlert("섭취한 음식의 총 칼로리를 계산합니다:음식을 선택한 후 섭취량을 g단위로 입력하시고" + "\n하단의 '총 칼로리 계산하기'버튼을 누르시면"
						+ "\n총 칼로리가 자동 계산되어 입력됩니다.\n" + "입력한 내용이 맞으시면 제일 하단의 " + "\n'저장' 버튼을 누르면 입력이 완료됩니다.");
				foodTableView.refresh();
				foodTableView.setItems(foodTableList);
				foodBtnCalTotalCal.setDisable(true);
				foodTxtNo.setDisable(true);
				foodTxtNo.clear();
				foodTxtPkNo.setDisable(true);
				foodTxtPkNo.clear();
				foodTxtName.setDisable(true);
				foodTxtName.clear();
				foodTxtCal.setDisable(true);
				foodTxtCal.clear();
				foodTxtTotalCal.setDisable(true);
				foodTxtTotalCal.clear();
				foodCmbKind.setDisable(true);
				foodCmbKategorie.setDisable(true);
				foodTxtVolume.setDisable(true);
				foodTxtVolume.clear();
				foodBtnSave.setDisable(true);
			});

			// ================음식 직접 입력 버튼 클릭시====================
			foodBtnInput.setOnAction(e -> {
				callAlert("섭취한 음식을 직접 입력합니다:섭취한 음식의 정보를 확인하신 후" + "\n해당 내용을 입력해주세요" + "\n정보를 모두 입력하시고 하단에\n"
						+ "'음식 저장' 버튼을 누르시면 입력이 완료됩니다. " + "\n저장한 음식을 다시 검색 후 선택한 뒤\n총 칼로리 계산을 진행해주세요.");
				foodTableView.refresh();
				foodTableView.setItems(foodTableList);
				foodCmbKind.setValue(foodCmbKindList.get(0));
				foodCmbKind.setDisable(false);
				foodBtnCorInput.setVisible(true);
				foodCmbKategorie.setDisable(false);
				foodTxtNo.clear();
				foodTxtNo.setDisable(true);
				foodTxtPkNo.clear();
				foodTxtPkNo.setDisable(true);
				foodTxtName.setDisable(false);
				foodTxtCal.clear();
				foodTxtCal.setDisable(false);
				foodTxtVolume.clear();
				foodTxtVolume.setDisable(true);
				foodBtnCalTotalCal.setDisable(true);
				foodTxtTotalCal.setDisable(true);
				foodTxtName.clear();
				foodBtnDel.setDisable(true);

				foodCmbKategorie.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						foodTxtPkNo.clear();
						foodTxtNo.clear();
						if (newValue != null) {
							switch (newValue.toString()) {
							case "1)감자칩":
							case "1)닭가슴살":
							case "1)시리얼":
							case "1)국수":
							case "1)기타":
								secondPkno = "1";
								break;
							case "2)그래놀라바":
							case "2)돼지고기":
							case "2)토스트":
							case "2)파스타":
								secondPkno = "2";
								break;
							case "3)샌드위치":
							case "3)쇠고기":
							case "3)빵":
							case "3)쌀":
								secondPkno = "3";
								break;
							case "4)기타":
								secondPkno = "4";
								break;
							}
							String pkno = null;
							int count = FoodDAO.getOneOneNo("'" + newValue.toString() + "'") + 1;
							foodTxtNo.setText(String.valueOf(count));
							if (foodTxtNo.getText().length() == 1) {
								pkno = firstPkno + secondPkno + "0" + "0" + foodTxtNo.getText();
								count = 0;
							} else {
								pkno = firstPkno + secondPkno + "0" + foodTxtNo.getText();
								count = 0;
							}
							foodTxtPkNo.setText(pkno);
						}
					}
				});
			});

			// ================음식 검색 기능====================
			foodBtnSearch.setOnAction(e -> {
				ObservableList<Food> foodTableListSearch = FXCollections.observableArrayList();
				foodTableListSearch.clear();
				for (Food food : foodTableList) {
					if (food.getKind().contains(foodTxtSearch.getText())
							|| food.getKategorie().contains(foodTxtSearch.getText())
							|| food.getName().contains(foodTxtSearch.getText())) {
						foodTableListSearch.add(food);
					}
				}
				foodTableView.setItems(foodTableListSearch);
				foodTxtSearch.clear();
			});

			// ================총 칼로리 계산 버튼 클릭시====================
			foodBtnCalTotalCal.setOnAction(e -> {
				if (foodTxtVolume.getText() != null) {
					double calValue = Integer.parseInt(foodTxtCal.getText()) / 100.0;
					double dValue = calValue * Double.parseDouble(foodTxtVolume.getText());
					int value = (int) Math.round(dValue);
					foodTxtVolume.setDisable(true);
					foodTxtTotalCal.setText(String.valueOf(value));
					foodBtnDel.setDisable(true);
					foodBtnSave.setDisable(false);
					callAlert("총 칼로리 계산 완료^^:입력하신 내용이 맞으시면 '저장'을 눌러주세요.\n내용을 잘못 입력하셨으면 '새로고침'을 눌러주세요.");
				} else {
					callAlert("섭취량을 입력 안하셨네요^^:섭취하신량을 대략적으로라도 입력해주세요!");
				}
			});

			// ================음식 저장 버튼 클릭시====================
			foodBtnCorInput.setOnAction(e -> {
				if (foodCmbKind.getSelectionModel().getSelectedItem() != null
						&& foodCmbKategorie.getSelectionModel().getSelectedItem() != null && foodTxtNo.getText() != null
						&& foodTxtPkNo.getText() != null && foodTxtName.getText() != null
						&& foodTxtCal.getText() != null) {
					Food food = new Food(foodCmbKind.getSelectionModel().getSelectedItem(),
							foodCmbKategorie.getSelectionModel().getSelectedItem(),
							Integer.parseInt(foodTxtNo.getText()), Integer.parseInt(foodTxtPkNo.getText()),
							foodTxtName.getText(), Integer.parseInt(foodTxtCal.getText()), 0, 0);
					FoodDAO.insertFoodData(food);
					foodTableList.add(food);
					callAlert("음식저장 성공^^:해당음식을 선택하셔서 총 칼로리를 계산해보세요!");
				} else {
					callAlert("음식저장 실패!!:입력값을 확인해보세요");
				}
			});

			// ================저장 버튼 클릭시====================
			foodBtnSave.setOnAction(e -> {
				if (foodTxtName.getText() != null && foodTxtTotalCal.getText() != null) {
					callAlert("칼로리 저장이 완료되었습니다^^ :하단에 '오늘의 기록 저장'을 하셔야 '오늘의 칼로리 계산'이 가능합니다~");
					t1txtLun3.setText(foodTxtName.getText() + ":" + foodTxtTotalCal.getText() + ":kcal");
					foodStage.close();
				} else {
					callAlert("항목이 빠졌습니다: 빠진 항목이 없는지 다시 체크해주세요^^");
				}
			});

			// ================삭제 버튼 클릭시====================
			foodBtnDel.setOnAction(e -> {
				selectedFoodIndex = foodTableView.getSelectionModel().getSelectedIndex();
				selectedFood = foodTableView.getSelectionModel().getSelectedItem();
				int value = FoodDAO.deleteFoodData(String.valueOf(selectedFood.getPkno()));
				if (value != 0) {
					foodTableList.remove(selectedFood);
					dbArrayList.remove(selectedFood);
					callAlert("삭제되었습니다^^:");
					foodTableView.refresh();
					foodTableView.setItems(foodTableList);
					foodBtnCalTotalCal.setDisable(true);
					foodTxtNo.setDisable(true);
					foodTxtNo.clear();
					foodTxtPkNo.setDisable(true);
					foodTxtPkNo.clear();
					foodTxtName.setDisable(true);
					foodTxtName.clear();
					foodTxtCal.setDisable(true);
					foodTxtCal.clear();
					foodTxtTotalCal.setDisable(true);
					foodTxtTotalCal.clear();
					foodCmbKind.setDisable(true);
					foodCmbKategorie.setDisable(true);
					foodTxtVolume.setDisable(true);
					foodTxtVolume.clear();
					foodBtnSave.setDisable(true);
					foodBtnDel.setDisable(true);
				} else {
					callAlert("삭제실패!!: ????????");
				}
			});

			// ================닫기 버튼 클릭시====================
			foodBtnExit.setOnAction(e -> foodStage.close());

			Scene scene = new Scene(root);
			foodStage.setScene(scene);
			foodStage.show();

			callAlert("섭취한 음식의 총 칼로리를 계산합니다:음식을 선택한 후 섭취량을 g단위로 입력하시고" + "\n하단의 '총 칼로리 계산하기'버튼을 누르시면"
					+ "\n총 칼로리가 자동 계산되어 입력됩니다.\n" + "입력한 내용이 맞으시면 제일 하단의 " + "\n'저장' 버튼을 누르면 입력이 완료됩니다.");

		} catch (IOException e) {
		}
	}

	// 운동기록 +버튼을 눌렀을때의 액션
	private void handlerT1BtnDin1Action(TextField t1, TextField t2, Button b1, Button b2, Button b3, Button b4) {
		if (!exerciseTableList.isEmpty() && !exercisedbArrayList.isEmpty()) {
			exerciseTableList.clear();
			exercisedbArrayList.clear();
		}
		Stage exerStage;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/execute_exercise.fxml"));
		Parent root;
		try {
			exerStage = new Stage();
			root = loader.load();

			// ===================ID등록================================
			TableView<Exercise> exerTableView = (TableView) root.lookup("#exerTableView");
			TextField exerTxtSearch = (TextField) root.lookup("#exerTxtSearch");
			Button exerBtnSearch = (Button) root.lookup("#exerBtnSearch");
			Button exerBtnCalCal = (Button) root.lookup("#exerBtnCalCal");
			Button exerBtnSave = (Button) root.lookup("#exerBtnSave");
			Button exerBtnDel = (Button) root.lookup("#exerBtnDel");
			Button exerBtnExit = (Button) root.lookup("#exerBtnExit");
			ComboBox<String> exerCmbKind = (ComboBox) root.lookup("#exerCmbKind");
			TextField exerTxtNo = (TextField) root.lookup("#exerTxtNo");
			TextField exerTxtPkNo = (TextField) root.lookup("#exerTxtPkNo");
			TextField exerTxtName = (TextField) root.lookup("#exerTxtName");
			TextField exerTxtFireCal = (TextField) root.lookup("#exerTxtFireCal");
			TextField exerTxtTime = (TextField) root.lookup("#exerTxtTime");
			inputDecimalFormat(exerTxtTime);
			TextField exerTxtTotalCal = (TextField) root.lookup("#exerTxtTotalCal");
			Button exerBtnCalinfo = (Button) root.lookup("#exerBtnCalinfo");
			Button exerBtnInput = (Button) root.lookup("#exerBtnInput");
			Button exerBtnExerSave = (Button) root.lookup("#exerBtnExerSave");
			Button exerBtnRefresh = (Button) root.lookup("#exerBtnRefresh");
			ImageView exerImageView = (ImageView) root.lookup("#exerImageView");

			// ===================TableView세팅================================
			TableColumn tcKind = exerTableView.getColumns().get(0);
			tcKind.setCellValueFactory(new PropertyValueFactory<>("kind"));
			tcKind.setStyle("-fx-alignment: CENTER;");

			TableColumn tcNo = exerTableView.getColumns().get(1);
			tcNo.setCellValueFactory(new PropertyValueFactory<>("no"));
			tcNo.setStyle("-fx-alignment: CENTER;");

			TableColumn tcPkno = exerTableView.getColumns().get(2);
			tcPkno.setCellValueFactory(new PropertyValueFactory<>("pkno"));
			tcPkno.setStyle("-fx-alignment: CENTER;");

			TableColumn tcName = exerTableView.getColumns().get(3);
			tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
			tcName.setStyle("-fx-alignment: CENTER;");

			TableColumn tcFirecal = exerTableView.getColumns().get(4);
			tcFirecal.setCellValueFactory(new PropertyValueFactory<>("firecal"));
			tcFirecal.setStyle("-fx-alignment: CENTER;");

			TableColumn tcTime = exerTableView.getColumns().get(5);
			tcTime.setCellValueFactory(new PropertyValueFactory<>("time"));
			tcTime.setStyle("-fx-alignment: CENTER;");

			TableColumn tcTotalcal = exerTableView.getColumns().get(6);
			tcTotalcal.setCellValueFactory(new PropertyValueFactory<>("totalcal"));
			tcTotalcal.setStyle("-fx-alignment: CENTER;");

			TableColumn tcImages = exerTableView.getColumns().get(7);
			tcImages.setCellValueFactory(new PropertyValueFactory<>("images"));
			tcImages.setStyle("-fx-alignment: CENTER;");

			exercisedbArrayList = ExerciseDAO.getExerciseData();
			for (Exercise ec : exercisedbArrayList) {
				exerciseTableList.add(ec);
			}

			exerTableView.setItems(exerciseTableList);
			// ===================콤보박스 세팅================================
			if(!exerCmbKindList.isEmpty()) {
				exerCmbKindList.clear();
			}
			exerCmbKindList.add("1)유산소");
			exerCmbKindList.add("2)무산소");
			exerCmbKind.setItems(exerCmbKindList);

			// ===================첫화면 아이템 세팅================================

			exerBtnCalCal.setDisable(true);
			exerTxtNo.setDisable(true);
			exerTxtPkNo.setDisable(true);
			exerTxtName.setDisable(true);
			exerTxtFireCal.setDisable(true);
			exerTxtTotalCal.setDisable(true);
			exerCmbKind.setDisable(true);
			exerTxtTime.setDisable(true);
			exerBtnSave.setDisable(true);
			exerBtnExerSave.setVisible(false);
			exerBtnDel.setDisable(true);

			// ===================테이블뷰 클릭시 액션================================

			exerTableView.setOnMouseClicked(e -> {
				selectedExercise = exerTableView.getSelectionModel().getSelectedItem();
				selectedExerciseIndex = exerTableView.getSelectionModel().getSelectedIndex();
				exerTxtNo.setText(String.valueOf(selectedExercise.getNo()));
				exerTxtNo.setDisable(true);
				exerTxtPkNo.setText(String.valueOf(selectedExercise.getPkno()));
				exerTxtPkNo.setDisable(true);
				exerTxtName.setText(selectedExercise.getName());
				exerTxtName.setDisable(true);
				exerTxtFireCal.setText(String.valueOf(selectedExercise.getFirecal()));
				exerTxtFireCal.setDisable(true);
				exerTxtTotalCal.setDisable(true);
				exerTxtTotalCal.clear();
				exerCmbKind.setValue(selectedExercise.getKind());
				exerCmbKind.setDisable(true);
				exerBtnCalCal.setDisable(false);
				exerTxtTime.setDisable(false);
				exerBtnDel.setDisable(false);
				if (selectedExercise.getImages() != null) {
					exerImageView.setImage(
							new Image(getClass().getResource("../images/" + selectedExercise.getImages()).toString()));
				}
			});

			// ================새로고침 버튼 클릭시====================
			exerBtnRefresh.setOnAction(e -> {
				exerTableView.refresh();
				exerTableView.setItems(exerciseTableList);
				exerBtnCalCal.setDisable(true);
				exerTxtNo.setDisable(true);
				exerTxtNo.clear();
				exerTxtPkNo.setDisable(true);
				exerTxtPkNo.clear();
				exerTxtName.setDisable(true);
				exerTxtName.clear();
				exerTxtFireCal.setDisable(true);
				exerTxtFireCal.clear();
				exerTxtTotalCal.setDisable(true);
				exerTxtTotalCal.clear();
				exerCmbKind.setDisable(true);
				exerTxtTime.setDisable(true);
				exerTxtTime.clear();
				exerBtnSave.setDisable(true);
				exerBtnDel.setDisable(true);
			});

			// ================운동 검색 기능====================
			exerBtnSearch.setOnAction(e -> {
				ObservableList<Exercise> exerTableListSearch = FXCollections.observableArrayList();
				exerTableListSearch.clear();
				for (Exercise ec : exerciseTableList) {
					if (ec.getKind().contains(exerTxtSearch.getText())
							|| ec.getName().contains(exerTxtSearch.getText())) {
						exerTableListSearch.add(ec);
					}
				}
				exerTableView.setItems(exerTableListSearch);
				exerTxtSearch.clear();
			});

			// ================칼로리 계산 안내 버튼 클릭시====================
			exerBtnCalinfo.setOnAction(e -> {
				callAlert("실시한 운동의 총 칼로리를 계산합니다:운동을 선택한 후 실시시간을 '분'단위로 입력하시고" + "\n하단의 '총 칼로리 계산하기'버튼을 누르시면"
						+ "\n총 칼로리가 자동 계산되어 입력됩니다.\n" + "입력한 내용이 맞으시면 제일 하단의 " + "\n'저장' 버튼을 누르면 입력이 완료됩니다.");
				exerTableView.refresh();
				exerTableView.setItems(exerciseTableList);
				exerBtnCalCal.setDisable(true);
				exerTxtNo.setDisable(true);
				exerTxtNo.clear();
				exerTxtPkNo.setDisable(true);
				exerTxtPkNo.clear();
				exerTxtName.setDisable(true);
				exerTxtName.clear();
				exerTxtFireCal.setDisable(true);
				exerTxtFireCal.clear();
				exerTxtTotalCal.setDisable(true);
				exerTxtTotalCal.clear();
				exerCmbKind.setDisable(true);
				exerTxtTime.clear();
				exerBtnSave.setDisable(true);
			});

			// ================운동 직접 입력 버튼 클릭시====================
			exerBtnInput.setOnAction(e -> {
				callAlert("실시한 운동을 직접 입력합니다:실시한 운동의 정보를 확인하신 후" + "\n해당 내용을 입력해주세요" + "\n정보를 모두 입력하시고 하단에\n"
						+ "'음식 저장' 버튼을 누르시면 입력이 완료됩니다. " + "\n저장한 운동을 다시 검색 후 선택한 뒤\n총 칼로리 계산을 진행해주세요.");
				exerTableView.refresh();
				exerTableView.setItems(exerciseTableList);
				exerCmbKind.setValue(exerCmbKindList.get(0));
				exerCmbKind.setDisable(false);
				exerBtnExerSave.setVisible(true);
				exerTxtNo.clear();
				exerTxtNo.setDisable(true);
				exerTxtPkNo.clear();
				exerTxtPkNo.setDisable(true);
				exerTxtName.setDisable(false);
				exerTxtFireCal.clear();
				exerTxtFireCal.setDisable(false);
				exerTxtTime.clear();
				exerTxtTime.setDisable(true);
				exerBtnCalCal.setDisable(true);
				exerTxtTotalCal.setDisable(true);
				exerTxtName.clear();
				exerBtnDel.setDisable(true);

				exerCmbKind.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						exerTxtPkNo.clear();
						exerTxtNo.clear();
						if (newValue != null) {
							switch (newValue.toString()) {
							case "1)유산소":
								secondPkno = "1";
								break;
							case "2)무산소":
								secondPkno = "2";
								break;
							}
							String pkno = null;
							int count = ExerciseDAO.getExerNo("'" + newValue.toString() + "'") + 1;
							exerTxtNo.setText(String.valueOf(count));
							if (exerTxtNo.getText().length() == 1) {
								pkno = secondPkno + "0" + "0" + exerTxtNo.getText();
								count = 0;
							} else {
								pkno = secondPkno + "0" + exerTxtNo.getText();
								count = 0;
							}
							exerTxtPkNo.setText(pkno);
						}
					}
				});
			});

			// ================총 칼로리 계산 버튼 클릭시====================
			exerBtnCalCal.setOnAction(e -> {
				if (exerTxtTime.getText() != null) {
					double calValue = (Integer.parseInt(exerTxtFireCal.getText()) / 10.0 / 90.0)
							* (double) loginUserMan.get(0).getCurrentWeight();
					double dValue = calValue * Integer.parseInt(exerTxtTime.getText());
					int value = (int) Math.round(dValue);
					exerTxtTime.setDisable(true);
					exerTxtTotalCal.setText(String.valueOf(value));
					exerBtnDel.setDisable(true);
					exerBtnSave.setDisable(false);
					callAlert("총 칼로리 계산 완료^^:입력하신 내용이 맞으시면 '저장'을 눌러주세요.\n내용을 잘못 입력하셨으면 '새로고침'을 눌러주세요.");
				} else {
					callAlert("실시시간을 입력 안하셨네요^^:실시하신 시간을 대략적으로라도 입력해주세요!");
				}
			});

			// ================운동 저장 버튼 클릭시====================
			exerBtnExerSave.setOnAction(e -> {
				if (exerCmbKind.getSelectionModel().getSelectedItem() != null && exerTxtNo.getText() != null
						&& exerTxtPkNo.getText() != null && exerTxtName.getText() != null
						&& exerTxtFireCal.getText() != null) {
					Exercise ec = new Exercise(exerCmbKind.getSelectionModel().getSelectedItem(),
							Integer.parseInt(exerTxtNo.getText()), Integer.parseInt(exerTxtPkNo.getText()),
							exerTxtName.getText(), Integer.parseInt(exerTxtFireCal.getText()), 0, 0, null);
					ExerciseDAO.insertExerciseData(ec);
					exerciseTableList.add(ec);
					callAlert(" 운동저장 성공^^:해당운동을 선택하셔서 총 칼로리를 계산해보세요!");
					exerBtnInput.setVisible(false);
				} else {
					callAlert("운동저장 실패!!:입력값을 확인해보세요");
				}
			});

			// ================저장 버튼 클릭시====================
			exerBtnSave.setOnAction(e -> {
				if (exerTxtName.getText() != null && exerTxtTotalCal.getText() != null) {
					callAlert("칼로리 저장이 완료되었습니다^^ :하단에 '오늘의 기록 저장'을 하셔야 '오늘의 칼로리 계산'이 가능합니다~");
					t1.setText(exerTxtName.getText() + ":" + exerTxtTotalCal.getText() + ":kcal");
					exerStage.close();
					t2.setDisable(true);
					t2.setVisible(true);
					b1.setDisable(true);
					b2.setDisable(false);
					b3.setDisable(false);
					b3.setVisible(true);
					b4.setDisable(false);
					b4.setVisible(true);
				} else {
					callAlert("항목이 빠졌습니다: 빠진 항목이 없는지 다시 체크해주세요^^");
				}
			});

			// ================삭제 버튼 클릭시====================
			exerBtnDel.setOnAction(e -> {
				selectedExerciseIndex = exerTableView.getSelectionModel().getSelectedIndex();
				selectedExercise = exerTableView.getSelectionModel().getSelectedItem();
				int value = ExerciseDAO.deleteExerData(String.valueOf(selectedExercise.getPkno()));
				if (value != 0) {
					exerciseTableList.remove(selectedExercise);
					exercisedbArrayList.remove(selectedExercise);
					callAlert("삭제되었습니다^^:");
					exerTableView.refresh();
					exerTableView.setItems(exerciseTableList);
					exerBtnCalCal.setDisable(true);
					exerTxtNo.setDisable(true);
					exerTxtNo.clear();
					exerTxtPkNo.setDisable(true);
					exerTxtPkNo.clear();
					exerTxtName.setDisable(true);
					exerTxtName.clear();
					exerTxtFireCal.setDisable(true);
					exerTxtFireCal.clear();
					exerTxtTotalCal.setDisable(true);
					exerTxtTotalCal.clear();
					exerCmbKind.setDisable(true);
					exerTxtTime.setDisable(true);
					exerTxtTime.clear();
					exerBtnSave.setDisable(true);
					exerBtnDel.setDisable(true);
				} else {
					callAlert("삭제실패!!: ????????");
				}
			});

			// ===================닫기 버튼 액션================================
			exerBtnExit.setOnAction(e -> exerStage.close());

			Scene scene = new Scene(root);
			exerStage.setScene(scene);
			exerStage.show();
		} catch (Exception e) {
		}
	}

	// 마지막 운동기록 +버튼을 눌렀을때의 액션
	private void handlerT1BtnEtc3Action() {
		if (!exerciseTableList.isEmpty() && !exercisedbArrayList.isEmpty()) {
			exerciseTableList.clear();
			exercisedbArrayList.clear();
		}
		Stage exerStage;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/execute_exercise.fxml"));
		Parent root;
		try {
			exerStage = new Stage();
			root = loader.load();

			// ===================ID등록================================
			TableView<Exercise> exerTableView = (TableView) root.lookup("#exerTableView");
			TextField exerTxtSearch = (TextField) root.lookup("#exerTxtSearch");
			Button exerBtnSearch = (Button) root.lookup("#exerBtnSearch");
			Button exerBtnCalCal = (Button) root.lookup("#exerBtnCalCal");
			Button exerBtnSave = (Button) root.lookup("#exerBtnSave");
			Button exerBtnDel = (Button) root.lookup("#exerBtnDel");
			Button exerBtnExit = (Button) root.lookup("#exerBtnExit");
			ComboBox<String> exerCmbKind = (ComboBox) root.lookup("#exerCmbKind");
			TextField exerTxtNo = (TextField) root.lookup("#exerTxtNo");
			TextField exerTxtPkNo = (TextField) root.lookup("#exerTxtPkNo");
			TextField exerTxtName = (TextField) root.lookup("#exerTxtName");
			TextField exerTxtFireCal = (TextField) root.lookup("#exerTxtFireCal");
			TextField exerTxtTime = (TextField) root.lookup("#exerTxtTime");
			inputDecimalFormat(exerTxtTime);
			TextField exerTxtTotalCal = (TextField) root.lookup("#exerTxtTotalCal");
			Button exerBtnCalinfo = (Button) root.lookup("#exerBtnCalinfo");
			Button exerBtnInput = (Button) root.lookup("#exerBtnInput");
			Button exerBtnExerSave = (Button) root.lookup("#exerBtnExerSave");
			Button exerBtnRefresh = (Button) root.lookup("#exerBtnRefresh");
			ImageView exerImageView = (ImageView) root.lookup("#exerImageView");

			// ===================TableView세팅================================
			TableColumn tcKind = exerTableView.getColumns().get(0);
			tcKind.setCellValueFactory(new PropertyValueFactory<>("kind"));
			tcKind.setStyle("-fx-alignment: CENTER;");

			TableColumn tcNo = exerTableView.getColumns().get(1);
			tcNo.setCellValueFactory(new PropertyValueFactory<>("no"));
			tcNo.setStyle("-fx-alignment: CENTER;");

			TableColumn tcPkno = exerTableView.getColumns().get(2);
			tcPkno.setCellValueFactory(new PropertyValueFactory<>("pkno"));
			tcPkno.setStyle("-fx-alignment: CENTER;");

			TableColumn tcName = exerTableView.getColumns().get(3);
			tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
			tcName.setStyle("-fx-alignment: CENTER;");

			TableColumn tcFirecal = exerTableView.getColumns().get(4);
			tcFirecal.setCellValueFactory(new PropertyValueFactory<>("firecal"));
			tcFirecal.setStyle("-fx-alignment: CENTER;");

			TableColumn tcTime = exerTableView.getColumns().get(5);
			tcTime.setCellValueFactory(new PropertyValueFactory<>("time"));
			tcTime.setStyle("-fx-alignment: CENTER;");

			TableColumn tcTotalcal = exerTableView.getColumns().get(6);
			tcTotalcal.setCellValueFactory(new PropertyValueFactory<>("totalcal"));
			tcTotalcal.setStyle("-fx-alignment: CENTER;");

			TableColumn tcImages = exerTableView.getColumns().get(7);
			tcImages.setCellValueFactory(new PropertyValueFactory<>("images"));
			tcImages.setStyle("-fx-alignment: CENTER;");

			exercisedbArrayList = ExerciseDAO.getExerciseData();
			for (Exercise ec : exercisedbArrayList) {
				exerciseTableList.add(ec);
			}

			exerTableView.setItems(exerciseTableList);
			// ===================콤보박스 세팅================================
			if(!exerCmbKindList.isEmpty()) {
				exerCmbKindList.clear();
			}
			exerCmbKindList.add("1)유산소");
			exerCmbKindList.add("2)무산소");
			exerCmbKind.setItems(exerCmbKindList);

			// ===================첫화면 아이템 세팅================================

			exerBtnCalCal.setDisable(true);
			exerTxtNo.setDisable(true);
			exerTxtPkNo.setDisable(true);
			exerTxtName.setDisable(true);
			exerTxtFireCal.setDisable(true);
			exerTxtTotalCal.setDisable(true);
			exerCmbKind.setDisable(true);
			exerTxtTime.setDisable(true);
			exerBtnSave.setDisable(true);
			exerBtnExerSave.setVisible(false);
			exerBtnDel.setDisable(true);

			// ===================테이블뷰 클릭시 액션================================

			exerTableView.setOnMouseClicked(e -> {
				selectedExercise = exerTableView.getSelectionModel().getSelectedItem();
				selectedExerciseIndex = exerTableView.getSelectionModel().getSelectedIndex();
				exerTxtNo.setText(String.valueOf(selectedExercise.getNo()));
				exerTxtNo.setDisable(true);
				exerTxtPkNo.setText(String.valueOf(selectedExercise.getPkno()));
				exerTxtPkNo.setDisable(true);
				exerTxtName.setText(selectedExercise.getName());
				exerTxtName.setDisable(true);
				exerTxtFireCal.setText(String.valueOf(selectedExercise.getFirecal()));
				exerTxtFireCal.setDisable(true);
				exerTxtTotalCal.setDisable(true);
				exerTxtTotalCal.clear();
				exerCmbKind.setValue(selectedExercise.getKind());
				exerCmbKind.setDisable(true);
				exerBtnCalCal.setDisable(false);
				exerTxtTime.setDisable(false);
				exerBtnDel.setDisable(false);
				if (selectedExercise.getImages() != null) {
					exerImageView.setImage(
							new Image(getClass().getResource("../images/" + selectedExercise.getImages()).toString()));
				}
			});

			// ================새로고침 버튼 클릭시====================
			exerBtnRefresh.setOnAction(e -> {
				exerTableView.refresh();
				exerTableView.setItems(exerciseTableList);
				exerBtnCalCal.setDisable(true);
				exerTxtNo.setDisable(true);
				exerTxtNo.clear();
				exerTxtPkNo.setDisable(true);
				exerTxtPkNo.clear();
				exerTxtName.setDisable(true);
				exerTxtName.clear();
				exerTxtFireCal.setDisable(true);
				exerTxtFireCal.clear();
				exerTxtTotalCal.setDisable(true);
				exerTxtTotalCal.clear();
				exerCmbKind.setDisable(true);
				exerTxtTime.setDisable(true);
				exerTxtTime.clear();
				exerBtnSave.setDisable(true);
				exerBtnDel.setDisable(true);
			});

			// ================운동 검색 기능====================
			exerBtnSearch.setOnAction(e -> {
				ObservableList<Exercise> exerTableListSearch = FXCollections.observableArrayList();
				exerTableListSearch.clear();
				for (Exercise ec : exerciseTableList) {
					if (ec.getKind().contains(exerTxtSearch.getText())
							|| ec.getName().contains(exerTxtSearch.getText())) {
						exerTableListSearch.add(ec);
					}
				}
				exerTableView.setItems(exerTableListSearch);
				exerTxtSearch.clear();
			});

			// ================칼로리 계산 안내 버튼 클릭시====================
			exerBtnCalinfo.setOnAction(e -> {
				callAlert("실시한 운동의 총 칼로리를 계산합니다:운동을 선택한 후 실시시간을 '분'단위로 입력하시고" + "\n하단의 '총 칼로리 계산하기'버튼을 누르시면"
						+ "\n총 칼로리가 자동 계산되어 입력됩니다.\n" + "입력한 내용이 맞으시면 제일 하단의 " + "\n'저장' 버튼을 누르면 입력이 완료됩니다.");
				exerTableView.refresh();
				exerTableView.setItems(exerciseTableList);
				exerBtnCalCal.setDisable(true);
				exerTxtNo.setDisable(true);
				exerTxtNo.clear();
				exerTxtPkNo.setDisable(true);
				exerTxtPkNo.clear();
				exerTxtName.setDisable(true);
				exerTxtName.clear();
				exerTxtFireCal.setDisable(true);
				exerTxtFireCal.clear();
				exerTxtTotalCal.setDisable(true);
				exerTxtTotalCal.clear();
				exerCmbKind.setDisable(true);
				exerTxtTime.clear();
				exerBtnSave.setDisable(true);
			});

			// ================운동 직접 입력 버튼 클릭시====================
			exerBtnInput.setOnAction(e -> {
				callAlert("실시한 운동을 직접 입력합니다:실시한 운동의 정보를 확인하신 후" + "\n해당 내용을 입력해주세요" + "\n정보를 모두 입력하시고 하단에\n"
						+ "'음식 저장' 버튼을 누르시면 입력이 완료됩니다. " + "\n저장한 운동을 다시 검색 후 선택한 뒤\n총 칼로리 계산을 진행해주세요.");
				exerTableView.refresh();
				exerTableView.setItems(exerciseTableList);
				exerCmbKind.setValue(exerCmbKindList.get(0));
				exerCmbKind.setDisable(false);
				exerBtnExerSave.setVisible(true);
				exerTxtNo.clear();
				exerTxtNo.setDisable(true);
				exerTxtPkNo.clear();
				exerTxtPkNo.setDisable(true);
				exerTxtName.setDisable(false);
				exerTxtFireCal.clear();
				exerTxtFireCal.setDisable(false);
				exerTxtTime.clear();
				exerTxtTime.setDisable(true);
				exerBtnCalCal.setDisable(true);
				exerTxtTotalCal.setDisable(true);
				exerTxtName.clear();
				exerBtnDel.setDisable(true);

				exerCmbKind.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						exerTxtPkNo.clear();
						exerTxtNo.clear();
						if (newValue != null) {
							switch (newValue.toString()) {
							case "1)유산소":
								secondPkno = "1";
								break;
							case "2)무산소":
								secondPkno = "2";
								break;
							}
							String pkno = null;
							int count = ExerciseDAO.getExerNo("'" + newValue.toString() + "'") + 1;
							exerTxtNo.setText(String.valueOf(count));
							if (exerTxtNo.getText().length() == 1) {
								pkno = secondPkno + "0" + "0" + exerTxtNo.getText();
								count = 0;
							} else {
								pkno = secondPkno + "0" + exerTxtNo.getText();
								count = 0;
							}
							exerTxtPkNo.setText(pkno);
						}
					}
				});
			});

			// ================총 칼로리 계산 버튼 클릭시====================
			exerBtnCalCal.setOnAction(e -> {
				if (exerTxtTime.getText() != null) {
					double calValue = (Integer.parseInt(exerTxtFireCal.getText()) / 10.0 / 90.0)
							* (double) loginUserMan.get(0).getCurrentWeight();
					double dValue = calValue * Integer.parseInt(exerTxtTime.getText());
					int value = (int) Math.round(dValue);
					exerTxtTime.setDisable(true);
					exerTxtTotalCal.setText(String.valueOf(value));
					exerBtnDel.setDisable(true);
					exerBtnSave.setDisable(false);
					callAlert("총 칼로리 계산 완료^^:입력하신 내용이 맞으시면 '저장'을 눌러주세요.\n내용을 잘못 입력하셨으면 '새로고침'을 눌러주세요.");
				} else {
					callAlert("실시시간을 입력 안하셨네요^^:실시하신 시간을 대략적으로라도 입력해주세요!");
				}
			});

			// ================운동 저장 버튼 클릭시====================
			exerBtnExerSave.setOnAction(e -> {
				if (exerCmbKind.getSelectionModel().getSelectedItem() != null && exerTxtNo.getText() != null
						&& exerTxtPkNo.getText() != null && exerTxtName.getText() != null
						&& exerTxtFireCal.getText() != null) {
					Exercise ec = new Exercise(exerCmbKind.getSelectionModel().getSelectedItem(),
							Integer.parseInt(exerTxtNo.getText()), Integer.parseInt(exerTxtPkNo.getText()),
							exerTxtName.getText(), Integer.parseInt(exerTxtFireCal.getText()), 0, 0, null);
					ExerciseDAO.insertExerciseData(ec);
					exerciseTableList.add(ec);
					callAlert(" 운동저장 성공^^:해당운동을 선택하셔서 총 칼로리를 계산해보세요!");
					exerBtnInput.setVisible(false);
				} else {
					callAlert("운동저장 실패!!:입력값을 확인해보세요");
				}
			});

			// ================저장 버튼 클릭시====================
			exerBtnSave.setOnAction(e -> {
				if (exerTxtName.getText() != null && exerTxtTotalCal.getText() != null) {
					callAlert("칼로리 저장이 완료되었습니다^^ :하단에 '오늘의 기록 저장'을 하셔야 '오늘의 칼로리 계산'이 가능합니다~");
					t1txtEtc3.setText(exerTxtName.getText() + ":" + exerTxtTotalCal.getText() + ":kcal");
					exerStage.close();
				} else {
					callAlert("항목이 빠졌습니다: 빠진 항목이 없는지 다시 체크해주세요^^");
				}
			});

			// ================삭제 버튼 클릭시====================
			exerBtnDel.setOnAction(e -> {
				selectedExerciseIndex = exerTableView.getSelectionModel().getSelectedIndex();
				selectedExercise = exerTableView.getSelectionModel().getSelectedItem();
				int value = ExerciseDAO.deleteExerData(String.valueOf(selectedExercise.getPkno()));
				if (value != 0) {
					exerciseTableList.remove(selectedExercise);
					exercisedbArrayList.remove(selectedExercise);
					callAlert("삭제되었습니다^^:");
					exerTableView.refresh();
					exerTableView.setItems(exerciseTableList);
					exerBtnCalCal.setDisable(true);
					exerTxtNo.setDisable(true);
					exerTxtNo.clear();
					exerTxtPkNo.setDisable(true);
					exerTxtPkNo.clear();
					exerTxtName.setDisable(true);
					exerTxtName.clear();
					exerTxtFireCal.setDisable(true);
					exerTxtFireCal.clear();
					exerTxtTotalCal.setDisable(true);
					exerTxtTotalCal.clear();
					exerCmbKind.setDisable(true);
					exerTxtTime.setDisable(true);
					exerTxtTime.clear();
					exerBtnSave.setDisable(true);
					exerBtnDel.setDisable(true);
				} else {
					callAlert("삭제실패!!: ????????");
				}
			});

			// ===================닫기 버튼 액션================================
			exerBtnExit.setOnAction(e -> exerStage.close());

			Scene scene = new Scene(root);
			exerStage.setScene(scene);
			exerStage.show();
		} catch (Exception e) {
		}

	}

	// ============================칼로리리포트==========================

	// 칼로리리포트 탭 클릭 액션(차트)
	private void handlerT3TabClickAction() {
		series1.setName("Saving칼로리");
		ObservableList oblist1 = FXCollections.observableArrayList();
		if (!oblist1.isEmpty()) {
			oblist1.clear();
		}
		int year = t1DatePicker.getValue().getYear();
		int month = t1DatePicker.getValue().getMonthValue();
		int day = t1DatePicker.getValue().getDayOfMonth();
		int saveCal = 0;
		int sum = 0;
		for (int i = 6; i >= 0; i--) {
			saveCal = loginUserMan.get(0).getKeepCalorie()
					- UserCalDAO.getUserPlusCalData(loginUserMan.get(0).getUserID(),
							year + "-" + month + "-" + (day - i))
					+ UserCalDAO.getUserMinusCalData(loginUserMan.get(0).getUserID(),
							year + "-" + month + "-" + (day - i));
			oblist1.add(new XYChart.Data<>(month + "/" + (day - i) + "\n" + saveCal + "kcal", saveCal));
			sum += saveCal;
		}
		series1.setData(oblist1);
		if (t3BarChart.getData().isEmpty()) {
			t3BarChart.getData().add(series1);
		}
		t3BarChart.setAnimated(false);
		t3LabelTotalCal.setText(String.valueOf(sum));
		t3LabelAvgCal.setText(String.valueOf(sum / 7));
		t3LabelLimit.setText(String.valueOf(loginUserMan.get(0).getKeepCalorie()));
	}

	// ============================체중변화==========================
	
	// 목표 체중 변경 저장 버튼 액션
	private void handlerT4BtnWeightSaveAction() {
		UserInfo ui = new UserInfo(loginUserMan.get(0).getUserID(), loginUserMan.get(0).getPassword(),
				Integer.parseInt(t4txtTodayWeight.getText()), loginUserMan.get(0).getHopeControlWeight(),
				loginUserMan.get(0).getGender(), loginUserMan.get(0).getMoveLevel(),
				loginUserMan.get(0).getCurrentWeight(), loginUserMan.get(0).getKeepCalorie());
		loginUserMan.set(0, ui);
		UserInfoDAO.updateGoalData(ui);
		callAlert("목표체중 변경 완료!!: 목표를 위해 화이팅!!");
		t4LabelWantWeight.setText(String.valueOf(loginUserMan.get(0).getGoal()));
	}

	// 체중변화 탭을 클릭시 액션(라인차트)
	private void handlerT4TabClickAction() {
		t4LabelWantWeight.setText(String.valueOf(loginUserMan.get(0).getGoal()));
		series2.setName("최근 1주일 간 체중변화");
		ObservableList oblist1 = FXCollections.observableArrayList();
		int year = t1DatePicker.getValue().getYear();
		int month = t1DatePicker.getValue().getMonthValue();
		int day = t1DatePicker.getValue().getDayOfMonth();
		int currentWeight = 0;
		int sum = 0;
		for (int i = 6; i >= 0; i--) {
			currentWeight = UserDailyWeightDAO.getUserDailyWeightData(loginUserMan.get(0).getUserID(),
					year + "-" + month + "-" + (day - i));
			series2.getData()
					.add(new XYChart.Data<>(month + "월 " + (day - i) + "일\n" + currentWeight + "kg", currentWeight));
		}
		oblist1.addAll(series2);
		t4LineChart.setData(oblist1);
		t4LineChart.setAnimated(false);
	}

	// ============================체중변화==========================
	
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

	// 기타 : 알림창
	public static void callAlert(String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("알림!");
		alert.setHeaderText(contentText.substring(0, contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":") + 1));
		alert.showAndWait();
	}

	// 기타. 숫자 두자리만 받는 세팅 함수
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
