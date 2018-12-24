// Copyright © 2018 Andy Goryachev <andy@goryachev.com>
package goryachev.bugs.fx;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PopupControl;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


/**
 * Dual Focus.
 * 
 * Start the application, press SPACE.  Notice that both the text field and the check box
 * have focus.  Pressing SPACE adds a space in the text field as well as toggles the check box.
 *  
 * Only one component is expected to have focus.
 */
public class DualFocus
	extends Application
{
	protected FxPopup popup;
	protected PopupBox popupBox;
	protected TextField textField;
	
	
	public DualFocus()
	{
	}


	public void start(Stage stage) throws Exception
	{
		textField = new TextField();
		textField.focusedProperty().addListener((s,p,c) -> handleFocus(c));
		
		TextArea textArea = new TextArea();
		textArea.setEditable(false);
		textArea.setText
		(
			"\n\n\n" +
			"Notice that both the text field and the check box have focus.\n" +
			"Pressing SPACE adds a space in the text field as well as toggles the check box.\n" +
			"\n" +
			"Only one component is expected to have focus."
		);
		
		BorderPane root = new BorderPane();
		root.setPrefSize(700, 300);
		root.setTop(textField);
		root.setCenter(textArea);
		
		Scene sc = new Scene(root);
		stage.setScene(sc);
		stage.setTitle("Dual Focus");
		stage.show();
	}
	
	protected void handleFocus(boolean on)
	{
		if(on)
		{
			showPopup();
		}
		else
		{
			hidePopup();
		}
	}
	
	
	protected void showPopup()
	{
		if(popup == null)
		{
			popupBox = new PopupBox();
			popupBox.setManaged(false);
			
			popup = new FxPopup(popupBox);
			popup.setAutoHide(false);
			popup.showRelative(textField, textField.getLayoutX(), textField.getLayoutY() + textField.getHeight());
		}
	}
	
	
	protected void hidePopup()
	{
		if(popup != null)
		{
			popup.hide();
			popup = null;
		}
		
		if(popupBox != null)
		{
			popupBox = null;
		}
	}
	
	
	//
	
	
	public static class FxPopup extends PopupControl
	{
		public FxPopup(Parent content)
		{
			getScene().setRoot(content);
	
			setConsumeAutoHidingEvents(false);
			setAutoHide(true);
	        setAutoFix(true);
	        setHideOnEscape(true);        
		}
		
		
		public void showRelative(Node owner, double dx, double dy)
		{
			Point2D p = owner.localToScreen(0, 0);
			show(owner, p.getX() + dx, p.getY() + dy);
		}
	}
	
	
	//
	
	
	public static class PopupBox extends BorderPane
	{
		protected final CheckBox checkBox;
		
		
		public PopupBox()
		{
			checkBox = new CheckBox("fail");
			setLeft(checkBox);
		}
	}
}
