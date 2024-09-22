package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Driver extends Application {
	private MartyrDateTree dateTree;
	private LocTree locationTree;
	private DisTree districtTree;
	private File file;
	private Pane PaneOfMartyrs;
	private Pane locationPane;
	private ObservableList<String> districts;
	private ObservableList<DisStat> districtsStat;
	private TableView<DisStat> districtTableView;
	private TableView<LocStat> locationTableVeiw;
	private ObservableList<String> locations;
	private ObservableList<LocStat> locationsStat;
	private TableView<Martyr> martyrTableView;
	private ObservableList<Martyr> martyrStat;
	private ObservableList<String> martyrs;
	private MDateNode dateNode;
	private boolean fileCreated;
	private int i = 0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage stg) throws Exception {
		// TODO Auto-generated method stub
		Button load = new Button("load file");
		Button createFile = new Button("create new File ");
		VBox vb = new VBox(20, load, createFile);
		vb.setAlignment(Pos.CENTER);
		load.setOnAction(e -> {
			FileChooser fch = new FileChooser();
			file = fch.showOpenDialog(stg);
			loadData();
			ShowDistrictScreen(vb, stg);
		});
		createFile.setOnAction(e -> {
			FileChooser fch = new FileChooser();
			fch.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
			file = fch.showSaveDialog(stg);
			if (file != null) {
				try {
					// Create the new file
					fileCreated = file.createNewFile();
					if (fileCreated) {
						System.out.println("New file created: " + file.getAbsolutePath());
						loadData();
						ShowDistrictScreen(vb, stg);

					} else {
						displayAlert("File already exists or could not be created.", "e");
						System.out.println("File already exists or could not be created.");
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}

		});
		Scene scene = new Scene(vb, 400, 400);
		stg.setScene(scene);
		stg.setTitle("Martyrs’ data structure");
		stg.show();
	}

	private void ShowDistrictScreen(VBox vb, Stage stg) {
		vb.setVisible(false);
		TabPane tabPane = new TabPane();

		Tab districtTab = new Tab("District Screen");
		districtTab.setClosable(false);

		Pane districtPane = new Pane();
		districtPane.setPrefHeight(400.0);
		districtPane.setPrefWidth(655.0);

		districtTableView = new TableView<>();
		districtTableView.setLayoutX(360.0);
		districtTableView.setLayoutY(38.0);
		districtTableView.setPrefHeight(242.0);
		districtTableView.setPrefWidth(221.0);

		TableColumn<DisStat, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setPrefWidth(92.0);
		TableColumn<DisStat, String> numberOfMartyrsColumn = new TableColumn<>("Number of Martyrs");
		numberOfMartyrsColumn.setPrefWidth(125.99996948242188);
		nameColumn.setCellValueFactory(new PropertyValueFactory<DisStat, String>("name"));
		numberOfMartyrsColumn.setCellValueFactory(new PropertyValueFactory<DisStat, String>("NoOfMartyrs"));
		districtTableView.getColumns().addAll(nameColumn, numberOfMartyrsColumn);
		// call districts .
		districtsStat = showDistricts();
		districtTableView.setItems(districtsStat);
		districtTableView.getSelectionModel().select(0);
		districtTableView.scrollTo(0);
		if (!fileCreated) {
			TableView.TableViewSelectionModel<DisStat> selectionModel1 = districtTableView.getSelectionModel();
			District d1 = new District(selectionModel1.getSelectedItem().getName());
			locationTree = districtTree.find(d1).getData().getLocationTree();
		}
		Label deleteDisResLabel = new Label();
		deleteDisResLabel.setLayoutX(274.0);
		deleteDisResLabel.setLayoutY(86.0);

		Label insertDisResLabel = new Label();
		insertDisResLabel.setLayoutX(274.0);
		insertDisResLabel.setLayoutY(142.0);

		Label updateDisResLabel = new Label();
		updateDisResLabel.setStyle("-fx-font-weight: bold; -fx-background-color: lightgray;");
		updateDisResLabel.setLayoutX(156.0);
		updateDisResLabel.setLayoutY(231.0);

		TextField inserttxt = new TextField();
		inserttxt.setLayoutX(95.0);
		inserttxt.setLayoutY(138.0);

		TextField updateDistxt = new TextField();
		updateDistxt.setLayoutX(213.0);
		updateDistxt.setLayoutY(188.0);
		updateDistxt.setPrefHeight(19.0);
		updateDistxt.setPrefWidth(89.0);

		Button updateButton = new Button("update");
		updateButton.setStyle("-fx-font-weight: bold; -fx-background-color: lightgray;");
		updateButton.setLayoutX(21.0);
		updateButton.setLayoutY(188.0);

		Button insertButton = new Button("insert");
		insertButton.setStyle("-fx-font-weight: bold; -fx-background-color: lightgray;");
		insertButton.setLayoutX(25.0);
		insertButton.setLayoutY(138.0);

		Label deletelbl = new Label("delete District");
		deletelbl.setStyle("-fx-font-weight: bold; -fx-background-color: lightgray;");
		deletelbl.setLayoutX(11);
		deletelbl.setLayoutY(86.0);

		Button nextButton = new Button("Next");
		nextButton.setStyle("-fx-font-weight: bold; -fx-background-color: lightgray;");
		nextButton.setLayoutX(487.0);
		nextButton.setLayoutY(318.0);
		nextButton.setPrefHeight(25.0);
		nextButton.setPrefWidth(51.0);

		Button previousButton = new Button("Previos");
		previousButton.setStyle("-fx-font-weight: bold; -fx-background-color: lightgray;");
		previousButton.setLayoutX(410.0);
		previousButton.setLayoutY(318.0);

		nextButton.setOnAction(e -> {
			TableView.TableViewSelectionModel<DisStat> selectionModel = districtTableView.getSelectionModel();
			selectionModel.selectNext();
			District d = new District(selectionModel.getSelectedItem().getName());
			locationTree = districtTree.find(d).getData().getLocationTree();
			locationTree.traverseInOrder();
		});
		previousButton.setOnAction(e -> {
			TableView.TableViewSelectionModel<DisStat> selectionModel = districtTableView.getSelectionModel();
			selectionModel.selectPrevious();
			District d = new District(selectionModel.getSelectedItem().getName());
			locationTree = districtTree.find(d).getData().getLocationTree();
			locationTree.traverseInOrder();
		});

		districts = showNamesOfDistricts(districtsStat);

		ComboBox<String> updateBox = new ComboBox<>();
		updateBox.setLayoutX(95.0);
		updateBox.setLayoutY(188.0);
		updateBox.setPrefHeight(25.0);
		updateBox.setPrefWidth(94.0);
		updateBox.setItems(districts);
		updateBox.setOnAction(e -> {

			districts = showNamesOfDistricts(districtsStat);
			updateBox.setItems(districts);
		});
		updateButton.setOnAction(e -> {
			String newName = updateDistxt.getText();
			if (!newName.isEmpty()) {

				if (updateBox.getValue() == null) {
					updateDisResLabel.setText("choose district you want to update!");
				} else {
					if (districtTree.find(new District(newName)) != null) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText(null);
						alert.setContentText(
								"You can not update an existing district name to an existing district name!!");
					} else {
						String dis = updateBox.getValue();
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Confirmation");
						alert.setHeaderText(null);
						alert.setContentText(
								"Are you sure you want to update the district name " + dis + " to " + newName + "?");

						Optional<ButtonType> result = alert.showAndWait();
						if (result.isPresent() && result.get() == ButtonType.OK) {
							// districtTree.delete(districtTree.find(new District(dis)).getData());

							District d = new District(newName);
							d.setLocationTree(districtTree.find(new District(dis)).getData().getLocationTree());
							districtTree.insert(d);
							districtTree.delete(new District(dis));
							districtsStat = showDistricts();
							districtTableView.setItems(districtsStat);
							displayAlert(dis + " District updated to " + newName, "i");
						}
					}
				}
			} else {
				updateDisResLabel.setText("please enter a name!");
			}
		});

		ComboBox<String> deleteBox = new ComboBox<>();
		deleteBox.setLayoutX(96.0);
		deleteBox.setLayoutY(82.0);
		deleteBox.setPrefHeight(25.0);
		deleteBox.setPrefWidth(148.0);
		deleteBox.setItems(districts);
		deleteBox.setOnAction(e -> {
			districts = showNamesOfDistricts(districtsStat);
			deleteBox.setItems(districts);
			String dis = deleteBox.getValue();
			if (dis != null) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation");
				alert.setHeaderText(null);
				alert.setContentText("Are you sure you want to delete the district: " + dis + "?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.isPresent() && result.get() == ButtonType.OK) {
					districtTree.delete(new District(dis));
					districtsStat = showDistricts();
					districtTableView.setItems(districtsStat);
					displayAlert(dis + " District deleted ", "i");
				}
			}

		});
		insertButton.setOnAction(e -> {
			String name = inserttxt.getText();
			if (!name.isEmpty()) {
				if (districtTree.find(new District(name)) != null) {
					displayAlert("You cant add an existing District !", "e");
				} else {
					District d = new District(name);
					districtTree.insert(d);
					LocTree loctree = new LocTree();
					districtTree.find(d).getData().setLocationTree(loctree);
					districtsStat = showDistricts();
					districtTableView.setItems(districtsStat);
					displayAlert(" District Added ", "i");
				}
			}
		});

		districtPane.getChildren().addAll(deleteBox, districtTableView, inserttxt, updateDistxt, updateButton,
				insertButton, deletelbl, nextButton, previousButton, updateBox, deleteDisResLabel, insertDisResLabel,
				updateDisResLabel);

		districtTab.setContent(districtPane);

		Tab locationTab = new Tab("Location Screen");
		locationTab.setClosable(false);
		locationPane = showLocationPane(locationTab);

		Tab martyrTab = new Tab("Martyr Screen");
		martyrTab.setClosable(false);
		PaneOfMartyrs = showMartyrPane(martyrTab);

		districtTab.setOnSelectionChanged(e -> {
			locationPane = showLocationPane(locationTab);
		});
		locationTab.setOnSelectionChanged(e -> {
			PaneOfMartyrs = showMartyrPane(martyrTab);
		});
		tabPane.getTabs().addAll(districtTab, locationTab, martyrTab);
		tabPane.setStyle("-fx-background-image: url('file:///C:/Users/fd/Desktop/gaza.jpg');"
				+ "-fx-background-size: cover;" + "-fx-background-repeat: no-repeat;");
		Scene scene = new Scene(tabPane, 660, 470);
		stg.setScene(scene);
		stg.setTitle("Martyr's Data Structure");
		stg.show();
	}

	private ObservableList<String> showNamesOfDistricts(ObservableList<DisStat> stat) {
		ObservableList<String> districts = FXCollections.observableArrayList();

		for (DisStat disStat : stat) {
			String districtName = disStat.getName();
			districts.add(districtName);
		}

		return districts;
	}

	private ObservableList<String> showNamesOfMartyrs(ObservableList<Martyr> stat) {
		ObservableList<String> martyrs = FXCollections.observableArrayList();
		System.out.println("hellloooo");
		for (Martyr m : stat) {
			System.out.println(123);
			String martyrName = m.getName();
			martyrs.add(martyrName);
		}
		return martyrs;
	}

	private ObservableList<DisStat> showDistricts() {
		ObservableList<DisStat> stat = FXCollections.observableArrayList();
		Stack stk = new Stack(200);
		DisNode curr = districtTree.getRoot();
		while (curr != null || !stk.isEmpty()) {

			while (curr != null) {
				stk.push(curr);
				curr = curr.getLeft();
			}
			curr = (DisNode) stk.pop();
			DisStat statistics = new DisStat(curr.getData().getName(), NoOfMartyrs(curr));
			stat.add(statistics);
			curr = curr.getRight();
		}
		return stat;
	}

	private Pane showMartyrPane(Tab martyrTab) {

		martyrTab.setOnSelectionChanged(e -> {
			i = 0;
			PaneOfMartyrs = new Pane();
			PaneOfMartyrs.setPrefSize(640, 400);

			Pane pane = new Pane();
			pane.setPrefSize(655, 400);

			martyrTableView = new TableView<>();
			martyrTableView.setAccessibleText("MartyrTableView");
			martyrTableView.setLayoutX(355);
			martyrTableView.setLayoutY(30);
			martyrTableView.setPrefSize(245, 242);

			TableColumn<Martyr, String> nameColumn = new TableColumn<>("Name");
			nameColumn.setPrefWidth(62);
			martyrTableView.getColumns().add(nameColumn);

			TableColumn<Martyr, String> ageColumn = new TableColumn<>("Age");
			ageColumn.setPrefWidth(76);
			martyrTableView.getColumns().add(ageColumn);

			TableColumn<Martyr, String> genderColumn = new TableColumn<>("Gender");
			genderColumn.setPrefWidth(76);
			martyrTableView.getColumns().add(genderColumn);

			nameColumn.setCellValueFactory(new PropertyValueFactory<Martyr, String>("name"));
			ageColumn.setCellValueFactory(new PropertyValueFactory<Martyr, String>("age"));
			genderColumn.setCellValueFactory(new PropertyValueFactory<Martyr, String>("gender"));

			pane.getChildren().add(martyrTableView);

			martyrStat = showMartyrs();
			martyrTableView.setItems(martyrStat);
			// martyrTableView.getSelectionModel().select(0);
			martyrTableView.scrollTo(0);
			TableView.TableViewSelectionModel<LocStat> selectionModel1 = locationTableVeiw.getSelectionModel();
			Location l = new Location(selectionModel1.getSelectedItem().getName());
			dateTree = locationTree.find(l).getData().getDateTree();
//			TextField insertTxt = new TextField();
//			insertTxt.setAccessibleText("inserttxt");
//			insertTxt.setLayoutX(114);
//			insertTxt.setLayoutY(138);
//			innerPane.getChildren().add(insertTxt);

			TextField updateTxt = new TextField();
			updateTxt.setAccessibleText("updatetxt");
			updateTxt.setLayoutX(213);
			updateTxt.setLayoutY(188);
			updateTxt.setPrefSize(89, 19);
			pane.getChildren().add(updateTxt);

			ComboBox<String> deleteBox = new ComboBox<>();
			deleteBox.setAccessibleText("deleteBox");
			deleteBox.setLayoutX(115);
			deleteBox.setLayoutY(82);
			deleteBox.setPrefSize(148, 25);
			pane.getChildren().add(deleteBox);
			martyrs = showNamesOfMartyrs(martyrStat);
			deleteBox.setItems(martyrs);
			deleteBox.setOnAction(e1 -> {
				martyrs = showNamesOfMartyrs(martyrStat);
				deleteBox.setItems(martyrs);
				String m = deleteBox.getValue();
				if (m != null) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Confirmation");
					alert.setHeaderText(null);
					alert.setContentText("Are you sure you want to delete the Martyr: " + m + "?");
					Optional<ButtonType> result = alert.showAndWait();
					if (result.isPresent() && result.get() == ButtonType.OK) {
						MDateNode node = dateTree.getRoot();
						Stack stk = new Stack(800);
						Martyr mar = new Martyr(m);
						System.out.println(mar);
						while (!stk.isEmpty() || node != null) {
							while (node != null) {
								stk.push(node);
								node = node.getLeft();
							}
							node = (MDateNode) stk.pop();
							if (node.getData().getList().find2(mar) != null) {
								Martyr mr = node.getData().getList().find2(mar).getData();
								if (mr.getName() == m) {
									System.out.println("test the find");
									dateNode = node;
									dateNode.getData().getList().delete(mr);
									break;
								}
							}
							node = node.getRight();
						}
						martyrStat = showMartyrs();
						martyrTableView.setItems(martyrStat);
						displayAlert(m + " Martyr deleted ", "i");
					}
				}
			});
			Button updateBtn = new Button("update");
			updateBtn.setAccessibleText("updatebtn");
			updateBtn.setLayoutX(21);
			updateBtn.setLayoutY(188);
			pane.getChildren().add(updateBtn);
			updateBtn.setStyle("-fx-font-weight: bold; -fx-background-color: lightgray;");

			Button insertBtn = new Button("insert Martyr Record");
			insertBtn.setAccessibleText("insertbtn");
			insertBtn.setLayoutX(116);
			insertBtn.setLayoutY(138);
			pane.getChildren().add(insertBtn);
			insertBtn.setStyle("-fx-font-weight: bold; -fx-background-color: lightgray;");

			Button navigateBtn = new Button("Navigate Throw Dates");
			navigateBtn.setAccessibleText("navigatebtn");
			navigateBtn.setLayoutX(411);
			navigateBtn.setLayoutY(317);
			pane.getChildren().add(navigateBtn);
			navigateBtn.setStyle("-fx-font-weight: bold; -fx-background-color: lightgray;");
			navigateBtn.setOnAction(event -> {

			});

			ComboBox<String> updateBox = new ComboBox<>();
			updateBox.setAccessibleText("updateBox");
			updateBox.setLayoutX(95);
			updateBox.setLayoutY(188);
			updateBox.setPrefSize(94, 25);
			pane.getChildren().add(updateBox);
			updateBox.setItems(martyrs);
			updateBox.setOnAction(e1 -> {
				martyrs = showNamesOfMartyrs(martyrStat);
				updateBox.setItems(martyrs);
			});

			Label deleteDisRes = new Label();
			deleteDisRes.setAccessibleText("deleteDisRes");
			deleteDisRes.setLayoutX(274);
			deleteDisRes.setLayoutY(86);
			pane.getChildren().add(deleteDisRes);

			Label insertDisRes = new Label();
			insertDisRes.setAccessibleText("insertDisRes");
			insertDisRes.setLayoutX(274);
			insertDisRes.setLayoutY(142);
			pane.getChildren().add(insertDisRes);

			Label updateDisRes = new Label();
			updateDisRes.setAccessibleText("updateDisRes");
			updateDisRes.setLayoutX(156);
			updateDisRes.setLayoutY(231);
			pane.getChildren().add(updateDisRes);

			Label deleteLbl = new Label("delete Martyr");
			deleteLbl.setAccessibleText("deletelbl");
			deleteLbl.setLayoutX(23);
			deleteLbl.setLayoutY(86);
			deleteLbl.setPrefSize(81, 17);
			pane.getChildren().add(deleteLbl);
			deleteLbl.setStyle("-fx-font-weight: bold; -fx-background-color: lightgray;");
			Button savebtn = new Button("save");
			savebtn.setStyle("-fx-font-weight: bold; -fx-background-color: lightgray;");
			Button saveAsbtn = new Button("save as");
			saveAsbtn.setStyle("-fx-font-weight: bold; -fx-background-color: lightgray;");
			saveAsbtn.setOnAction(e2 -> {
				Stage stg = new Stage();
				FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
				fileChooser.setTitle("Save Data");
				File file = fileChooser.showSaveDialog(stg);
				if (file != null) {
					saveDataToFile(file);
				}
			});
			savebtn.setOnAction(e2 -> {
				saveDataToFile(file);
			});
			HBox saveHb = new HBox(10, savebtn, saveAsbtn);
			saveHb.setLayoutX(250);
			saveHb.setLayoutY(370);
			pane.getChildren().add(saveHb);
			TextField searchTxt = new TextField();
			searchTxt.setAccessibleText("searchtxt");
			searchTxt.setLayoutX(115);
			searchTxt.setLayoutY(236);
			pane.getChildren().add(searchTxt);
			Button searchBtn = new Button("Search");
			searchBtn.setStyle("-fx-font-weight: bold; -fx-background-color: lightgray;");
			searchBtn.setAccessibleText("searchbtn");
			searchBtn.setLayoutX(25);
			searchBtn.setLayoutY(236);
			pane.getChildren().add(searchBtn);
			searchBtn.setOnAction(e2 -> {
				if (!searchTxt.getText().isEmpty()) {
					TableView<Martyr> table = new TableView<>();
					TableColumn<Martyr, String> nameColumn2 = new TableColumn<>("Name");
					TableColumn<Martyr, String> dateColumn = new TableColumn<>("Date");
					TableColumn<Martyr, Integer> ageColumn2 = new TableColumn<>("Age");
					TableColumn<Martyr, String> locationColumn = new TableColumn<>("Location");
					TableColumn<Martyr, String> districtColumn = new TableColumn<>("District");
					TableColumn<Martyr, String> genderColumn2 = new TableColumn<>("Gender");

					nameColumn2.setCellValueFactory(new PropertyValueFactory<Martyr, String>("name"));
					dateColumn.setCellValueFactory(new PropertyValueFactory<Martyr, String>("date"));
					ageColumn2.setCellValueFactory(new PropertyValueFactory<Martyr, Integer>("age"));
					locationColumn.setCellValueFactory(new PropertyValueFactory<Martyr, String>("location"));
					districtColumn.setCellValueFactory(new PropertyValueFactory<Martyr, String>("district"));
					genderColumn2.setCellValueFactory(new PropertyValueFactory<Martyr, String>("gender"));

					table.getColumns().addAll(nameColumn2, dateColumn, ageColumn2, locationColumn, districtColumn,
							genderColumn2);
					ObservableList<Martyr> martyrs = FXCollections.observableArrayList();
					Stack stk = new Stack(200);
					DisNode curr = districtTree.getRoot();
					while (curr != null || !stk.isEmpty()) {
						while (curr != null) {
							stk.push(curr);
							curr = curr.getLeft();
						}
						curr = (DisNode) stk.pop();
						LocTree lc = curr.getData().getLocationTree();
						Stack stk2 = new Stack(400);
						LocNode curr2 = lc.getRoot();
						while (curr2 != null || !stk2.isEmpty()) {

							while (curr2 != null) {
								stk2.push(curr2);
								curr2 = curr2.getLeft();
							}
							curr2 = (LocNode) stk2.pop();
							System.out.println(curr2.getData().getName());
							Stack stk3 = new Stack(800);
							MartyrDateTree mt = curr2.getData().getDateTree();
							MDateNode curr3 = mt.getRoot();
							while (curr3 != null || !stk3.isEmpty()) {

								while (curr3 != null) {
									stk3.push(curr3);
									curr3 = curr3.getLeft();
								}
								curr3 = (MDateNode) stk3.pop();
								System.out.println(curr3.getData().toString());
								Node mnode = curr3.getData().getList().head;
								while (mnode != null) {
									Martyr m = mnode.getData();
									if (m.getName().toLowerCase().contains(searchTxt.getText().toLowerCase())) {
										martyrs.add(m);
									}
									mnode = mnode.getNext();
								}
								curr3 = curr3.getRight();
							}
							curr2 = curr2.getRight();
						}
						curr = curr.getRight();
					}
					table.getItems().addAll(martyrs);
					Scene sc = new Scene(table, 650, 400);
					Stage stg = new Stage();
					stg.setScene(sc);
					stg.show();
				} else {

				}
			});
			PaneOfMartyrs.getChildren().add(pane);
			martyrTab.setContent(showMartyrPane(martyrTab));
		});
		return PaneOfMartyrs;
	}

	private void saveDataToFile(File file2) {
		try (PrintWriter pw = new PrintWriter(new FileWriter(file2))) {
			pw.println("Name,Date,Age,Location,District,gender");
			Stack stk = new Stack(200);
			DisNode currDis = districtTree.getRoot();
			Stack stk0 = new Stack(200);
			while (currDis != null || !stk0.isEmpty()) {
				while (currDis != null) {
					stk0.push(currDis);
					currDis = currDis.getLeft();
				}
				currDis = (DisNode) stk0.pop();
				LocNode currLoc = currDis.getData().getLocationTree().getRoot();
				while (currLoc != null || !stk.isEmpty()) {
					while (currLoc != null) {
						stk.push(currLoc);
						currLoc = currLoc.getLeft();
					}
					currLoc = (LocNode) stk.pop();
					Stack stk2 = new Stack(200);
					MDateNode currDate = currLoc.getData().getDateTree().getRoot();
					MLinkList list = currDate.getData().getList();
					Node node;
					while (currDate != null || !stk2.isEmpty()) {
						while (currDate != null) {
							stk2.push(currDate);
							currDate = currDate.getLeft();
						}
						currDate = (MDateNode) stk2.pop();
						list = currDate.getData().getList();
						node = list.head;
						while (node != null) {
							pw.println(node.getData().getName() + "," + node.getData().getDate() + ","
									+ node.getData().getAge() + "," + node.getData().getLocation() + ","
									+ node.getData().getDistrict() + "," + node.getData().getGender());
							node = node.getNext();
						}
						currDate = currDate.getRight();
						if (currDate != null) {
							list = currDate.getData().getList();
						}
					}
					currLoc = currLoc.getRight();
				}
				currDis = currDis.getRight();
			}
			displayAlert("Data Saved Successfully ", "i");

		} catch (IOException e) {
			displayAlert("Close the file please !", "e");
		}
	}

	private ObservableList<Martyr> showMartyrs() {
		ObservableList<Martyr> stat = FXCollections.observableArrayList();
		Stack stk2 = new Stack(200);
		MDateNode currDate = dateTree.getRoot();
		locationTree.traverseInOrder();
		dateTree.traverseInOrder();
		MLinkList list = currDate.getData().getList();
		Node node;
		while (currDate != null || !stk2.isEmpty()) {
			while (currDate != null) {
				stk2.push(currDate);
				// list = currDate.getData().getList();
				currDate = currDate.getLeft();
			}
			currDate = (MDateNode) stk2.pop();
			list = currDate.getData().getList();
			node = list.head;
			while (node != null) {
				Martyr m = node.getData();
				Martyr statistics = new Martyr(node.getData().getName(), node.getData().getAge(),
						node.getData().getGender());
				stat.add(m);
				node = node.getNext();
				System.out.println(m);
			}
			currDate = currDate.getRight();
		}
		return stat;
	}

	private Pane showLocationPane(Tab locationTab) {
		// Pane mainPane=null;
		locationTab.setOnSelectionChanged(e -> {

			locationPane = new Pane();
			locationPane.setPrefSize(640.0, 400.0);
			Pane subPane = new Pane();
			subPane.setPrefSize(655.0, 400.0);

			ComboBox<String> deleteBox = new ComboBox<>();
			deleteBox.setLayoutX(105);
			deleteBox.setLayoutY(82.0);
			deleteBox.setPrefSize(148.0, 25.0);

			locationTableVeiw = new TableView<>();
			locationTableVeiw.setLayoutX(311.0);
			locationTableVeiw.setLayoutY(38.0);
			locationTableVeiw.setPrefSize(307.0, 242.0);

			TableColumn<LocStat, String> nameColumn = new TableColumn<>("Name");
			nameColumn.setPrefWidth(62.0);
			TableColumn<LocStat, String> earliestDateColumn = new TableColumn<>("Earliest date");
			earliestDateColumn.setPrefWidth(76.0);
			TableColumn<LocStat, String> latestDateColumn = new TableColumn<>("Latest date");
			latestDateColumn.setPrefWidth(76.0);
			TableColumn<LocStat, String> dateOfMaxColumn = new TableColumn<>("Date of Max");
			dateOfMaxColumn.setPrefWidth(91.6667);
			nameColumn.setCellValueFactory(new PropertyValueFactory<LocStat, String>("name"));
			earliestDateColumn.setCellValueFactory(new PropertyValueFactory<LocStat, String>("earliestDate"));
			latestDateColumn.setCellValueFactory(new PropertyValueFactory<LocStat, String>("latestDate"));
			dateOfMaxColumn.setCellValueFactory(new PropertyValueFactory<LocStat, String>("dateOfMax"));
			locationTableVeiw.getColumns().addAll(nameColumn, earliestDateColumn, latestDateColumn, dateOfMaxColumn);
			locationsStat = showLocations();
			locationTableVeiw.setItems(locationsStat);
			locationTableVeiw.getSelectionModel().select(i);
			locationTableVeiw.scrollTo(0);

			TableView.TableViewSelectionModel<LocStat> selectionModel1 = locationTableVeiw.getSelectionModel();
//			Location l = new Location(selectionModel1.getSelectedItem().getName());
//			dateTree = locationTree.find(l).getData().getDateTree();
			if (selectionModel1.getSelectedItem() == null) {

			} else {
				dateTree = locationTree.find(new Location(selectionModel1.getSelectedItem().getName())).getData()
						.getDateTree();

				dateTree.traverseInOrder();
			}
			TextField insertTextField = new TextField();
			insertTextField.setLayoutX(95.0);
			insertTextField.setLayoutY(138.0);

			TextField updateTextField = new TextField();
			updateTextField.setLayoutX(213.0);
			updateTextField.setLayoutY(188.0);
			updateTextField.setPrefSize(89.0, 19.0);

			Button updateButton = new Button("update");
			updateButton.setLayoutX(21.0);
			updateButton.setLayoutY(188.0);
			updateButton.setStyle("-fx-font-weight: bold; -fx-background-color: lightgray;");

			Button insertButton = new Button("insert");
			insertButton.setLayoutX(25.0);
			insertButton.setLayoutY(138.0);
			insertButton.setStyle("-fx-font-weight: bold; -fx-background-color: lightgray;");

			Button nextButton = new Button("Next");
			nextButton.setLayoutX(487.0);
			nextButton.setLayoutY(318.0);
			nextButton.setPrefSize(51.0, 25.0);
			nextButton.setStyle("-fx-font-weight: bold; -fx-background-color: lightgray;");

			Button previousButton = new Button("Previous");
			previousButton.setLayoutX(410.0);
			previousButton.setLayoutY(318.0);
			previousButton.setStyle("-fx-font-weight: bold; -fx-background-color: lightgray;");

			nextButton.setOnAction(e1 -> {
				i++;
				TableView.TableViewSelectionModel<LocStat> selectionModel = locationTableVeiw.getSelectionModel();
				selectionModel.selectNext();
				Location loc = new Location(selectionModel.getSelectedItem().getName());
				dateTree = locationTree.find(loc).getData().getDateTree();
				dateTree.traverseInOrder();
			});
			previousButton.setOnAction(e1 -> {
				i--;
				TableView.TableViewSelectionModel<LocStat> selectionModel = locationTableVeiw.getSelectionModel();
				selectionModel.selectPrevious();
				Location loc = new Location(selectionModel.getSelectedItem().getName());
				dateTree = locationTree.find(loc).getData().getDateTree();
				dateTree.traverseInOrder();
			});
			ComboBox<String> updateBox = new ComboBox<>();
			updateBox.setLayoutX(95.0);
			updateBox.setLayoutY(188.0);
			updateBox.setPrefSize(94.0, 25.0);

			Label deleteDisResLabel = new Label();
			deleteDisResLabel.setLayoutX(274.0);
			deleteDisResLabel.setLayoutY(86.0);
			deleteDisResLabel.setStyle("-fx-font-weight: bold; -fx-background-color: lightgray;");

			Label insertDisResLabel = new Label();
			insertDisResLabel.setLayoutX(274.0);
			insertDisResLabel.setLayoutY(142.0);

			Label updateDisResLabel = new Label();
			updateDisResLabel.setLayoutX(156.0);
			updateDisResLabel.setLayoutY(231.0);

			Label deleteLabel = new Label("delete Location");
			deleteLabel.setLayoutX(6.0);
			deleteLabel.setLayoutY(86.0);
			deleteLabel.setPrefSize(89, 17.0);
			deleteLabel.setStyle("-fx-font-weight: bold; -fx-background-color: lightgray;");

			subPane.getChildren().addAll(deleteBox, locationTableVeiw, insertTextField, updateTextField, updateButton,
					insertButton, nextButton, previousButton, updateBox, deleteDisResLabel, insertDisResLabel,
					updateDisResLabel, deleteLabel);

			locationPane.getChildren().add(subPane);
			locationTab.setContent(showLocationPane(locationTab));
		});

		return locationPane;
	}

	private ObservableList<LocStat> showLocations() {
		ObservableList<LocStat> stat = FXCollections.observableArrayList();
		
		Stack stk = new Stack(200);
		LocNode curr = locationTree.getRoot();
//		if (curr==null) {
//			return stat;
//		}
//		Queue1 q =new Queue1(100);
//		//q.enqueue(curr);
//		while (!q.isEmpty()) {
//		//	LocNode loc =q.dequeue();
////			if(loc.getLeft()!=null) q.enQueue(loc.getLeft());
////			if(loc.getRight()!=null)q.enQueue(loc.getRight());
////			LocStat statistics = new LocStat(curr.getData().getName(), earliestDate(curr), latestDate(curr),
////					dateOfMax(curr));
////			stat.add(statistics);
//
//		}
		locationTree.traverseInOrder();
		while (curr != null || !stk.isEmpty()) {

			while (curr != null) {
				stk.push(curr);
				curr = curr.getLeft();
			}
			curr = (LocNode) stk.pop();

			LocStat statistics = new LocStat(curr.getData().getName(), earliestDate(curr), latestDate(curr),
					dateOfMax(curr));
			stat.add(statistics);
			curr = curr.getRight();
		}
		return stat;
	}

	private String earliestDate(LocNode curr) {
		String date = "";
		Stack stk = new Stack(200);
		LocNode currLoc = curr;
		MDateNode currDate = currLoc.getData().getDateTree().getRoot();
		while (currDate != null) {
			stk.push(currDate);
			currDate = currDate.getLeft();
		}
		currDate = (MDateNode) stk.pop();
		date = currDate.getData().getDate();
		return date;
	}

	private String latestDate(LocNode curr) {
		String date = "";
		Stack stk = new Stack(200);
		LocNode currLoc = curr;
		MDateNode currDate = currLoc.getData().getDateTree().getRoot();
		while (currDate != null) {
			stk.push(currDate);
			currDate = currDate.getRight();
		}
		currDate = (MDateNode) stk.pop();
		date = currDate.getData().getDate();
		return date;
	}

	private String dateOfMax(LocNode curr) {
		int maxCount = 0;
		LocNode currLoc = curr;
		MDateNode currDate = currLoc.getData().getDateTree().getRoot();
		String Maxdate = "";
		Stack stk = new Stack(400);
		while (currDate != null || !stk.isEmpty()) {
			while (currDate != null) {
				stk.push(currDate);
				currDate = currDate.getLeft();
			}
			currDate = (MDateNode) stk.pop();
			String date = currDate.getData().getDate();
			System.out.println(date);
			int count = totalMarInADate(currDate);
			if (count > maxCount) {
				maxCount = count;
				Maxdate = date;
				System.out.println("test count ");
			}
			currDate = currDate.getRight();
		}
		return Maxdate;
	}

	private int totalMarInADate(MDateNode curr) {
		int count = 0;
		Node currNode = curr.getData().getList().head;
		while (currNode != null) {
			count++;
			currNode = currNode.getNext();
		}
		return count;
	}

	private String NoOfMartyrs(DisNode curr) {
		// TODO Auto-generated method stub
		int count = 0;
		Stack stk = new Stack(200);
		DisNode currDis = curr;
		if (currDis.getData().getLocationTree() == null) {
			return "0";
		}
		LocNode currLoc = currDis.getData().getLocationTree().getRoot();
		locationTree.traverseInOrder();
		while (currLoc != null || !stk.isEmpty()) {

			while (currLoc != null) {
				stk.push(currLoc);
				currLoc = currLoc.getLeft();
			}
			currLoc = (LocNode) stk.pop();
			Stack stk2 = new Stack(200);
			MDateNode currDate = currLoc.getData().getDateTree().getRoot();
			MLinkList list = currDate.getData().getList();
			Node node;
			while (currDate != null || !stk2.isEmpty()) {

				while (currDate != null) {
					stk2.push(currDate);
					list = currDate.getData().getList();
					currDate = currDate.getLeft();
				}
				node = list.head;
				currDate = (MDateNode) stk2.pop();
				while (node != null) {
					count++;
					node = node.getNext();
				}
				currDate = currDate.getRight();
				if (currDate != null) {
					list = currDate.getData().getList();
				}
			}

			currLoc = currLoc.getRight();
		}
		return count + "";
	}

	private void displayAlert(String message, String type) {
		Alert alert;
		if (type == "i") {
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information");
		} else if (type == "e") {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
		} else {
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("CONFIRMATION");
		}

		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private void loadData() {
		districtTree = new DisTree();
		dateTree = new MartyrDateTree();
		locationTree = new LocTree();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			br.readLine();
			while (((line = br.readLine()) != null)) {
				String[] demo = line.split(",");
				String gender = demo[5].trim();
				String district = demo[4].trim().toLowerCase();
				String location = demo[3].trim().toLowerCase();
				int age;
				try {
					age = Integer.parseInt(demo[2]);
				} catch (Exception e) {
					age = 0;
				}
				String date = demo[1].trim();
				String Name = demo[0].trim();
				Martyr m = new Martyr(Name, date, age, location, district, gender);

				DateData datedata = new DateData(date);
				Location locdata = new Location(location);
				District disdata = new District(district);

				if (districtTree.find(disdata) == null) {
					districtTree.insert(disdata);
					LocTree ltree = new LocTree();
					ltree.insert(locdata);
					MartyrDateTree mtree = new MartyrDateTree();
					MLinkList list = new MLinkList();
					mtree.insert(datedata);
					mtree.find(datedata).getData().setList(list);
					list.insert(m);
					locdata.setDateTree(mtree);
					disdata.setLocationTree(ltree);

				} else {
					if (districtTree.find(disdata).getData().getLocationTree().find(locdata) == null) {
						districtTree.find(disdata).getData().getLocationTree().insert(locdata);
						MartyrDateTree mtree = new MartyrDateTree();
						MLinkList list = new MLinkList();
						mtree.insert(datedata);
						mtree.find(datedata).getData().setList(list);
						list.insert(m);
						locdata.setDateTree(mtree);
					} else {
						MartyrDateTree mtree = districtTree.find(disdata).getData().getLocationTree().find(locdata)
								.getData().getDateTree();
						if (mtree.find(datedata) == null) {
							MLinkList list = new MLinkList();
							mtree.insert(datedata);
							mtree.find(datedata).getData().setList(list);
							list.insert(m);
						} else {
							mtree.find(datedata).getData().getList().insert(m);
						}
					}
				}
			}

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
