package masterjava.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.OperationBinding;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.data.fields.DataSourceDateTimeField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.*;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.viewer.DetailViewer;
import masterjava.shared.GreetingService;
import masterjava.shared.GreetingServiceAsync;

/**
 * User: GKislin
 * Date: 17.12.2010
 */
public class SmartGwtEntryPoint implements EntryPoint {
    /**
     * The message displayed to the user when the server cannot be reached or
     * returns an error.
     */
    private static final String SERVER_ERROR = "An error occurred while attempting to"
            + "contact the server. Please check your network connection and try again.";

    /**
     * Remote service proxy for the greeting service.
     */
    private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

    /**
     * Entry point method.
     */

    final ListGrid listGrid = new ListGrid();
    final DynamicForm dynamicForm = new DynamicForm();
    final DetailViewer detailViewer = new DetailViewer();

    final IButton deleteBtn = new IButton("Delete");
    final IButton updateBtn = new IButton("Add");
    final IButton clearBtn = new IButton("Clear");
//    final Label textbox = new Label();

    @Override
    public void onModuleLoad() {

        RestDataSource dataSource = new RestDataSource();
        dataSource.setDataFormat(DSDataFormat.JSON);

        OperationBinding fetch = new OperationBinding(DSOperationType.FETCH, "fetch.do");
        OperationBinding add = new OperationBinding(DSOperationType.ADD, "add.do");
        OperationBinding update = new OperationBinding(DSOperationType.UPDATE, "update.do");
        OperationBinding remove = new OperationBinding(DSOperationType.REMOVE, "remove.do");
        dataSource.setOperationBindings(fetch, add, update, remove);

        DataSourceIntegerField dsIdField = new DataSourceIntegerField("id");
        DataSourceTextField dsTextField = new DataSourceTextField("ttext", "Text");
        DataSourceIntegerField dsIntField = new DataSourceIntegerField("tint", "Integer");
        DataSourceDateTimeField dsDateField = new DataSourceDateTimeField("tdate", "Date");
        dsIdField.setPrimaryKey(true);
        dsIdField.setHidden(true);
        dataSource.setFields(dsIdField, dsTextField, dsIntField, dsDateField);

        listGrid.setWidth100();
        listGrid.setHeight(400);
        listGrid.setDataSource(dataSource);
        listGrid.setEmptyCellValue("--");
        listGrid.setSelectionType(SelectionStyle.SINGLE);
        listGrid.setCanEdit(true);
        listGrid.setAutoFetchData(true);

        dynamicForm.setDataSource(dataSource);
        dynamicForm.setAutoFocus(false);

        ToolStrip toolbar = new ToolStrip();
        toolbar.setMembersMargin(10);
        toolbar.setHeight(22);
        toolbar.setWidth(100);

        detailViewer.setDataSource(dataSource);
        detailViewer.hide();

        updateBtn.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                dynamicForm.saveData();
                if (!dynamicForm.hasErrors()) {
                    switchToNewRecord();
                }
            }
        });
        toolbar.addMember(updateBtn);

        clearBtn.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                switchToNewRecord();
            }
        });
        toolbar.addMember(clearBtn);

        deleteBtn.disable();
        deleteBtn.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                listGrid.removeSelectedData();
                switchToNewRecord();
            }
        });
        toolbar.addMember(deleteBtn);

        listGrid.addRecordClickHandler(new RecordClickHandler() {

            @Override
            public void onRecordClick(RecordClickEvent event) {
                switchToEditRecord(event.getRecord());
            }
        });

        VStack vStack = new VStack();
        vStack.setLeft(175);
        vStack.setTop(75);
        vStack.setWidth("70%");
        vStack.setMembersMargin(20);
        vStack.addMember(listGrid);
        vStack.addMember(dynamicForm);
        vStack.addMember(toolbar);
        vStack.addMember(detailViewer);

        // Message
//        textbox.setID("textbox");
//        textbox.setAlign(Alignment.CENTER);
//        textbox.setShowEdges(true);
//        textbox.setPadding(5);
//        textbox.setLeft(500);
//        textbox.setTop(500);
//        textbox.setParentElement(vStack);
//        textbox.setWidth(200);
//        textbox.setContents("The future has a way of arriving unannounced.");
//        textbox.setVisibility(Visibility.HIDDEN);

        DynamicForm textArea = new DynamicForm();
        final TextAreaItem textAreaItem = new TextAreaItem("TextArea");
        textArea.setFields(textAreaItem);

        final IButton greetButton = new IButton("Greet");
        greetButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                greetButton.disable();
                // Invoke greeting service
                greetingService.greet((String) textAreaItem.getValue(), new AsyncCallback<String>() {
                    public void onFailure(Throwable caught) {
                        SC.say(SERVER_ERROR);
                        greetButton.enable();
                    }

                    public void onSuccess(String result) {
                        SC.say(result);
                        greetButton.enable();
                    }
                });
            }
        });

//        final IButton rpcButton = new IButton("RPCRequest");
//        rpcButton.addClickHandler(new ClickHandler() {
//            public void onClick(ClickEvent event) {
//                rpcButton.disable();
//                RPCRequest req = new RPCRequest();
//                req.setActionURL("/smartRpc/greetingService");
//                req.setData((String) textAreaItem.getValue());
//                RPCManager.sendRequest(req, new RPCCallback() {
//                    public void execute(RPCResponse response, Object rawData, RPCRequest request) {
//                        SC.say("SmartRpc");
//                        rpcButton.enable();
//                    }
//                });
//            }
//        });
//
        vStack.addMember(textArea);
        vStack.addMember(greetButton);
//        vStack.addMember(rpcButton);
        vStack.draw();
    }

    private void switchToEditRecord(Record record) {
        dynamicForm.editRecord(record);
        detailViewer.viewSelectedData(listGrid);
        detailViewer.show();
        deleteBtn.enable();
        updateBtn.setTitle("Update");
    }

    private void switchToNewRecord() {
        listGrid.deselectAllRecords();
        dynamicForm.editNewRecord();
        detailViewer.hide();
        deleteBtn.disable();
        updateBtn.setTitle("Add");
    }
}

