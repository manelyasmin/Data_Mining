package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import Algorithms.Clarans;
import Algorithms.Kmeans;
import Algorithms.Kmedoids;
import DataStructure.Thyroid;
import DataStructure.Data;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class MiningController implements Initializable {

    @FXML
    private TextField title;

    @FXML
    private Button dashbord;

    @FXML
    private Button statistic;

    @FXML
    private Button about;

    @FXML
    private Button pattern;

    @FXML
    private Button mining;

    @FXML
    private TextArea rules;

    @FXML
    private TextField time;

    @FXML
    private TextField param2;

    @FXML
    private TextField param1, param3;

    @FXML
    private Button btn;

    @FXML
    private ComboBox<String> method;

    @FXML
    private Text t1,t2, t3;

    String display = "";
    String miningMethod = "";

    @FXML
	public void staticAction(){
		Stage rootStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/FXML/static.fxml"));
		Parent roote;
		try {
			roote = loader.load();
			Scene scene = new Scene(roote);
			StaticController control = loader.getController();
			scene.getStylesheets().add("/FXML/Dashbord.css");
			rootStage.setScene(scene);
		    rootStage.show();
		    rootStage.setResizable(false);
		    dashbord.getScene().getWindow().hide();


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    @FXML
	public void aboutAction(){
		Stage rootStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/FXML/about.fxml"));
		Parent roote;
		try {
			roote = loader.load();
			AboutController control = loader.getController();
			Scene scene = new Scene(roote);
			scene.getStylesheets().add("/FXML/Dashbord.css");
			rootStage.setScene(scene);
		    rootStage.show();
		    rootStage.setResizable(false);
		    dashbord.getScene().getWindow().hide();


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    @FXML
	public void dashbordAction(){
		Stage rootStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/FXML/Dashbord.fxml"));
		Parent roote;
		try {
			roote = loader.load();
			DashbordController control = loader.getController();
			Scene scene = new Scene(roote);
			scene.getStylesheets().add("/FXML/Dashbord.css");
			rootStage.setScene(scene);
		    rootStage.show();
		    rootStage.setResizable(false);
		    dashbord.getScene().getWindow().hide();


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    @FXML
	public void patternAction(){
		Stage rootStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/FXML/FrequentPattern.fxml"));
		Parent roote;
		try {
			roote = loader.load();
			FrequentPatternController control = loader.getController();
			Scene scene = new Scene(roote);
			scene.getStylesheets().add("/FXML/Dashbord.css");
			rootStage.setScene(scene);
		    rootStage.show();
		    rootStage.setResizable(false);
		    dashbord.getScene().getWindow().hide();


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		method.setItems(FXCollections.observableArrayList("KMeans", "Clarans","Kmedoids"));
		t1.setText("");
		t2.setText("");
		t3.setText("");
		param1.setVisible(false);
		param2.setVisible(false);
		param3.setVisible(false);



	}

	@FXML
	public void activate(){
		miningMethod = method.getValue();

		if(miningMethod.equals("KMeans")){
			t1.setText("Max pop Length");
			t2.setText("Max iteration");
			t3.setText("");
			param1.setVisible(true);
			param2.setVisible(true);
			param3.setVisible(false);
		}else if (miningMethod.equals("Clarans")){
			t1.setText("Number of clusters");
			t2.setText("Max neighbors");
			t3.setText("Num local");
			param1.setVisible(true);
			param2.setVisible(true);
			param3.setVisible(true);
		}else if (miningMethod.equals("Kmedoids")){
			t1.setText("Number of clusters");
			t2.setText("");
			t3.setText("");
			param1.setVisible(true);
			param2.setVisible(false);
			param3.setVisible(false);
		}
	}

	@FXML
	public void extractAction(){
		Data dataset = new Data();
		if(miningMethod.equals("KMeans")){
			display = "";
			double t1 = System.currentTimeMillis();
			Kmeans kmeans = new Kmeans(dataset);

	        HashMap<Integer, ArrayList<Thyroid>> clusters = kmeans.fit(Integer.valueOf(this.param1.getText()), Integer.valueOf(this.param2.getText()));
	        double t2 = System.currentTimeMillis();
	        clusters.forEach((key, value) -> {
	        	display += "\n-------------------------- CLUSTER ----------------------------\n";
	            // Sorting the coordinates to see the most significant tags first.
	            display += key+"\n";
	            String members = String.join(", ", value.toString())+"\n";
	            display +=  "the cluster size = "+value.size()+"\n";
	            display += members+"\n";

	            System.out.println();
	            System.out.println();
	        });


	        display += "Fmeasure: "+kmeans.fmeasure()+"\n";
	        display += "Emeasure: "+kmeans.emeasure()+"\n";

	        this.rules.setText(display);

	        this.time.setText(String.valueOf((t2-t1)/1000)+" sec ");
		}else if(miningMethod.equals("Clarans")){
			display = "";
			double t1 = System.currentTimeMillis();
			Clarans k = new Clarans(dataset);

			HashMap<Integer, ArrayList<Integer>> clusters =k.Clarans(dataset,Integer.valueOf(this.param1.getText()),Integer.valueOf(this.param2.getText()),Integer.valueOf(this.param3.getText()));
			HashMap<Integer, ArrayList<Thyroid>> cl = k.clustersClarans(dataset, clusters);
	        double t2 = System.currentTimeMillis();
	        clusters.forEach((key, value) -> {
	        	display += "\n-------------------------- CLUSTER ----------------------------\n";
	            // Sorting the coordinates to see the most significant tags first.
	            display += key+"\n";
	            String members = String.join(", ", value.toString())+"\n";
	            display +=  "the cluster size = "+value.size()+"\n";
	            display += members+"\n";

	            System.out.println();
	            System.out.println();
	        });




	        this.rules.setText(display);

	        this.time.setText(String.valueOf((t2-t1)/1000)+" sec ");
		}else if(miningMethod.equals("Kmedoids")){
			display = "";
			double t1 = System.currentTimeMillis();
			Kmedoids k2 = new Kmedoids(dataset);
	        HashMap<Integer, ArrayList<Integer>> clusters = k2.fit(dataset, Integer.valueOf(this.param1.getText()));
	        double t2 = System.currentTimeMillis();
	        clusters.forEach((key, value) -> {
	        	display += "\n-------------------------- CLUSTER ----------------------------\n";
	            // Sorting the coordinates to see the most significant tags first.
	            display += key+"\n";
	            String members = String.join(", ", value.toString())+"\n";
	            display +=  "the cluster size = "+value.size()+"\n";
	            display += members+"\n";

	            System.out.println();
	            System.out.println();
	        });

	        this.rules.setText(display);

	        this.time.setText(String.valueOf((t2-t1)/1000)+" sec ");
		}




	}
}
