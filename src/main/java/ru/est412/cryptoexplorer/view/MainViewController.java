package ru.est412.cryptoexplorer.view;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import ru.est412.cryptoexplorer.App;
import ru.est412.cryptoexplorer.db.Filter;
import ru.est412.cryptoexplorer.model.*;

import java.sql.SQLException;

public class MainViewController {
	// TODO пересчитывать таблицы при перемещении клавиатурой а не только клике мышкой

	@FXML
	private TableView<Providers.Entity> tblProviders;
    @FXML
    private TableColumn<Providers.Entity, String> colProviderName;
    @FXML
    private TableColumn<Providers.Entity, String> colProviderVersion;
    @FXML
    private TableColumn<Providers.Entity, String> colProviderClass;

    @FXML
    private TableView<Engines.Entity> tblEngines;
    @FXML
    private TableColumn<Engines.Entity, String> colEngineName;

    @FXML
    private TableView<Algorithms.Entity> tblAlgorithms;
    @FXML
    private TableColumn<Algorithms.Entity, String> colAlgorithmName;

    @FXML
    private TableView<ServiceClasses.Entity> tblServiceClasses;
    @FXML
    private TableColumn<ServiceClasses.Entity, String> colServiceClass;

    @FXML
    private Label labProvider;
    @FXML
    private Button btnProviderClear;

    @FXML
    private Label labEngine;
    @FXML
    private Button btnEngineClear;

    @FXML
    private Label labAlgorithm;
    @FXML
    private Button btnAlgorithmClear;

    @FXML
    private Label labService;
    @FXML
    private Button btnServiceClear;

    private App app;
    
    public MainViewController() {    	
    }
    
    @FXML
    private void initialize() {
    	//tblProviders.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	//для деселекта ранее выбранной строки
        // TODO переделать в универсальный код лля нескольких таблиц и проверить как присобачить к OnClick
    	//код взят отсюда http://stackoverflow.com/questions/19490868/how-to-unselect-a-selected-table-row-upon-second-click-selection-in-javafx
        tblProviders.setRowFactory(new Callback<TableView<Providers.Entity>, TableRow<Providers.Entity>>() {
            public TableRow<Providers.Entity> call(TableView<Providers.Entity> tableView2) {
                final TableRow<Providers.Entity> row = new TableRow<>();
                row.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {  
                    public void handle(MouseEvent event) {  
                        final int index = row.getIndex();  
                        if (index >= 0 && index < tblProviders.getItems().size() && tblProviders.getSelectionModel().isSelected(index)) {
                        	tblProviders.getSelectionModel().clearSelection(index);
                            event.consume();  
                        }
                    }  
                });  
                return row;  
            }  
        });
        colProviderName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colProviderVersion.setCellValueFactory(new PropertyValueFactory<>("version"));
        colProviderClass.setCellValueFactory(new PropertyValueFactory<>("className"));

        //tblEngines.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //для деселекта ранее выбранной строки
        //код взят отсюда http://stackoverflow.com/questions/19490868/how-to-unselect-a-selected-table-row-upon-second-click-selection-in-javafx
        tblEngines.setRowFactory(new Callback<TableView<Engines.Entity>, TableRow<Engines.Entity>>() {
            public TableRow<Engines.Entity> call(TableView<Engines.Entity> tableView2) {
                final TableRow<Engines.Entity> row = new TableRow<>();
                row.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        final int index = row.getIndex();
                        if (index >= 0 && index < tblEngines.getItems().size() && tblEngines.getSelectionModel().isSelected(index)) {
                            tblEngines.getSelectionModel().clearSelection(index);
                            event.consume();
                        }
                    }
                });
                return row;
            }
        });
        colEngineName.setCellValueFactory(new PropertyValueFactory<>("name"));

        //tblEngines.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //для деселекта ранее выбранной строки
        //код взят отсюда http://stackoverflow.com/questions/19490868/how-to-unselect-a-selected-table-row-upon-second-click-selection-in-javafx
        tblAlgorithms.setRowFactory(new Callback<TableView<Algorithms.Entity>, TableRow<Algorithms.Entity>>() {
            public TableRow<Algorithms.Entity> call(TableView<Algorithms.Entity> tableView2) {
                final TableRow<Algorithms.Entity> row = new TableRow<>();
                row.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        final int index = row.getIndex();
                        if (index >= 0 && index < tblAlgorithms.getItems().size() && tblAlgorithms.getSelectionModel().isSelected(index)) {
                            tblAlgorithms.getSelectionModel().clearSelection(index);
                            event.consume();
                        }
                    }
                });
                return row;
            }
        });
        colAlgorithmName.setCellValueFactory(new PropertyValueFactory<>("name"));


        //tblEngines.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //для деселекта ранее выбранной строки
        //код взят отсюда http://stackoverflow.com/questions/19490868/how-to-unselect-a-selected-table-row-upon-second-click-selection-in-javafx
        tblServiceClasses.setRowFactory(new Callback<TableView<ServiceClasses.Entity>, TableRow<ServiceClasses.Entity>>() {
            public TableRow<ServiceClasses.Entity> call(TableView<ServiceClasses.Entity> tableView2) {
                final TableRow<ServiceClasses.Entity> row = new TableRow<>();
                row.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        final int index = row.getIndex();
                        if (index >= 0 && index < tblServiceClasses.getItems().size() && tblServiceClasses.getSelectionModel().isSelected(index)) {
                            tblServiceClasses.getSelectionModel().clearSelection(index);
                            event.consume();
                        }
                    }
                });
                return row;
            }
        });
        colServiceClass.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    // TODO объединить
    @FXML
    private void onClickEnginesTable() throws SQLException {
        Engines.Entity entity = tblEngines.getSelectionModel().getSelectedItem();
        if (entity == null) {
            Filter.setEngineId(-1);
            labEngine.textProperty().set(null);
            btnEngineClear.disableProperty().set(true);
        } else {
            Filter.setEngineId(entity.getId());
            labEngine.textProperty().set(entity.getName());
            btnEngineClear.disableProperty().set(false);
        }
        refreshTables();
        if (Filter.getEngineId() != -1) {
            tblEngines.getSelectionModel().selectFirst();
        }
    }

    @FXML
    private void onClickAlgorithmsTable() throws SQLException {
        Algorithms.Entity entity = tblAlgorithms.getSelectionModel().getSelectedItem();
        if (entity == null) {
            Filter.setAlgorithmId(-1);
            labAlgorithm.textProperty().set(null);
            btnAlgorithmClear.disableProperty().set(true);
        } else {
            Filter.setAlgorithmId(entity.getId());
            labAlgorithm.textProperty().set(entity.getName());
            btnAlgorithmClear.disableProperty().set(false);
        }
        //refreshTables(tblAlgorithms);
        refreshTables();
        if (Filter.getAlgorithmId() != -1) {
            tblAlgorithms.getSelectionModel().selectFirst();
        }
    }
    
    @FXML
    private void onClickProvidersTable() throws SQLException {
        Providers.Entity entity = tblProviders.getSelectionModel().getSelectedItem();
    	if (entity == null) {
            Filter.setProviderId(-1);
            labProvider.textProperty().set(null);
            btnProviderClear.disableProperty().set(true);
    	} else {
            Filter.setProviderId(entity.getId());
            labProvider.textProperty().set(entity.getName()+" v."+entity.getVersion()+" ("+entity.getClassName()+")");
            btnProviderClear.disableProperty().set(false);
        }
        //refreshTables(tblProviders);
    	refreshTables();
    	if (Filter.getProviderId() != -1) {
            tblProviders.getSelectionModel().selectFirst();
        }
    }

    // TODO переделать фильтр и передавать туда объект
    @FXML
    private void onClickServiceClassesTable() throws SQLException {
        ServiceClasses.Entity entity = tblServiceClasses.getSelectionModel().getSelectedItem();
        if (entity == null) {
            Filter.setServiceClassId(-1);
            labService.textProperty().set(null);
            btnServiceClear.disableProperty().set(true);
        } else {
            Filter.setServiceClassId(entity.getId());
            labService.textProperty().set(entity.getName());
            btnServiceClear.disableProperty().set(false);
        }
        refreshTables();
        if (Filter.getServiceClassId() != -1) {
            tblServiceClasses.getSelectionModel().selectFirst();
        }
    }

    @FXML
    private void onActBtnProviderClear() throws SQLException {
        tblProviders.getSelectionModel().clearSelection();
        onClickProvidersTable();
    }

    @FXML
    private void onActBtnEngineClear() throws SQLException {
        tblEngines.getSelectionModel().clearSelection();
        onClickEnginesTable();
    }

    @FXML
    private void onActBtnAlgorithmClear() throws SQLException {
        tblAlgorithms.getSelectionModel().clearSelection();
        onClickAlgorithmsTable();
    }

    @FXML
    private void onActBtnServiceClear() throws SQLException {
        tblServiceClasses.getSelectionModel().clearSelection();
        onClickServiceClassesTable();
    }

    public void refreshTables() throws SQLException {
        refreshTables(null);
    }

    public void refreshTables(TableView except) throws SQLException {
        if (except != tblProviders) tblProviders.setItems(FXCollections.observableArrayList(Providers.getEntities()));
        if (except != tblEngines) tblEngines.setItems(FXCollections.observableArrayList(Engines.getEntities()));
        if (except != tblAlgorithms) tblAlgorithms.setItems(FXCollections.observableArrayList(Algorithms.getEntities()));
        if (except != tblServiceClasses) tblServiceClasses.setItems(FXCollections.observableArrayList(ServiceClasses.getEntities()));
    }

    
}
