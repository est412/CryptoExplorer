package ru.est412.cryptoexplorer.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
    private TextField txtFldProviderFilter;
    @FXML
    private Button btnProviderFilterClear;

    @FXML
    private Label labEngine;
    @FXML
    private Button btnEngineClear;

    @FXML
    private TextField txtFldEngineFilter;
    @FXML
    private Button btnEngineFilterClear;

    @FXML
    private Label labAlgorithm;
    @FXML
    private Button btnAlgorithmClear;

    @FXML
    private TextField txtFldAlgorithmFilter;
    @FXML
    private Button btnAlgorithmFilterClear;

    @FXML
    private Label labService;
    @FXML
    private Button btnServiceClear;

    @FXML
    private TextField txtFldServiceFilter;
    @FXML
    private Button btnServiceFilterClear;

    private App app;

    private ObservableList<Providers.Entity> providerEntities = FXCollections.observableArrayList();
    private ObservableList<Engines.Entity> engineEntities = FXCollections.observableArrayList();
    private ObservableList<Algorithms.Entity> algorithmEntities = FXCollections.observableArrayList();
    private ObservableList<ServiceClasses.Entity> serviceEntities = FXCollections.observableArrayList();
    
    public MainViewController() {    	
    }
    
    @FXML
    private void initialize() {
        initTblProviders();
        initTblEngines();
        initTblAlgorithms();
        initTblServiceClasses();
    }

    private void initTblServiceClasses() {
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

        //http://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<ServiceClasses.Entity> filteredData = new FilteredList<>(serviceEntities, p -> true);
        // 2. Set the filter Predicate whenever the filter changes.
        txtFldServiceFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                btnServiceFilterClear.disableProperty().set(true);
            } else if (btnServiceFilterClear.isDisabled()) {
                btnServiceFilterClear.disableProperty().set(false);
            }
            filteredData.setPredicate(serviceEntity -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (serviceEntity.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<ServiceClasses.Entity> sortedData = new SortedList<>(filteredData);
        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(tblServiceClasses.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        tblServiceClasses.setItems(sortedData);
    }

    private void initTblAlgorithms() {
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

        //http://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Algorithms.Entity> filteredData = new FilteredList<>(algorithmEntities, p -> true);
        // 2. Set the filter Predicate whenever the filter changes.
        txtFldAlgorithmFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                btnAlgorithmFilterClear.disableProperty().set(true);
            } else if (btnAlgorithmFilterClear.isDisabled()) {
                btnAlgorithmFilterClear.disableProperty().set(false);
            }
            filteredData.setPredicate(algorithmEntity -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (algorithmEntity.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Algorithms.Entity> sortedData = new SortedList<>(filteredData);
        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(tblAlgorithms.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        tblAlgorithms.setItems(sortedData);
    }

    private void initTblEngines() {
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

        //http://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Engines.Entity> filteredData = new FilteredList<>(engineEntities, p -> true);
        // 2. Set the filter Predicate whenever the filter changes.
        txtFldEngineFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                btnEngineFilterClear.disableProperty().set(true);
            } else if (btnEngineFilterClear.isDisabled()) {
                btnEngineFilterClear.disableProperty().set(false);
            }
            filteredData.setPredicate(engineEntity -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                    if (engineEntity.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Engines.Entity> sortedData = new SortedList<>(filteredData);
        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(tblEngines.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        tblEngines.setItems(sortedData);
    }

    private void initTblProviders() {
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

        //http://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Providers.Entity> filteredData = new FilteredList<>(providerEntities, p -> true);
        // 2. Set the filter Predicate whenever the filter changes.
        txtFldProviderFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                btnProviderFilterClear.disableProperty().set(true);
            } else if (btnProviderFilterClear.isDisabled()) {
                btnProviderFilterClear.disableProperty().set(false);
            }
            filteredData.setPredicate(providerEntity -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (providerEntity.getClassName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (providerEntity.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Providers.Entity> sortedData = new SortedList<>(filteredData);
        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(tblProviders.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        tblProviders.setItems(sortedData);
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
    private void onActBtnProviderFilterClear() {
        txtFldProviderFilter.clear();
    }

    @FXML
    private void onActBtnEngineClear() throws SQLException {
        tblEngines.getSelectionModel().clearSelection();
        onClickEnginesTable();
    }

    @FXML
    private void onActBtnEngineFilterClear() {
        txtFldEngineFilter.clear();
    }

    @FXML
    private void onActBtnAlgorithmClear() throws SQLException {
        tblAlgorithms.getSelectionModel().clearSelection();
        onClickAlgorithmsTable();
    }

    @FXML
    private void onActBtnAlgorithmFilterClear() {
        txtFldAlgorithmFilter.clear();
    }

    @FXML
    private void onActBtnServiceClear() throws SQLException {
        tblServiceClasses.getSelectionModel().clearSelection();
        onClickServiceClassesTable();
    }

    @FXML
    private void onActBtnServiceFilterClear() {
        txtFldServiceFilter.clear();
    }

    public void refreshTables() throws SQLException {
        //tblProviders.setItems(FXCollections.observableArrayList(Providers.getEntities()));
        providerEntities.clear();
        providerEntities.addAll(Providers.getEntities());
        //tblEngines.setItems(FXCollections.observableArrayList(Engines.getEntities()));
        engineEntities.clear();
        engineEntities.addAll(Engines.getEntities());
        //tblAlgorithms.setItems(FXCollections.observableArrayList(Algorithms.getEntities()));
        algorithmEntities.clear();
        algorithmEntities.addAll(Algorithms.getEntities());
        //tblServiceClasses.setItems(FXCollections.observableArrayList(ServiceClasses.getEntities()));
        serviceEntities.clear();
        serviceEntities.addAll(ServiceClasses.getEntities());
    }

}
