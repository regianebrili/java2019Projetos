package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

public class DepartmentFormController implements Initializable{
	
	private Department entity;
	
	// declarar os atributos das duas caixas de texto da tela (Id e Name)
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	// label de mensagem de erro
	@FXML
	private Label labelErrorName;
	
	// botões
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	// instância do departament
	public void setDepartment(Department entity) {
		this.entity = entity;
	}
	
	// metodos para tratar as ações dos botões
	@FXML
	public void onBtSaveAction() {
		System.out.println("onBtSaveAction");
	}

	@FXML
	public void onBtCancelAction() {
		System.out.println("onBtCancelAction");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
	}
	
	// coloca nas caixas de texto da tela os dados quem vieram no entity
	public void updateFormDate() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));  // como o Id é número precisa ser convertido para string
		txtName.setText(entity.getName());
	}
}
