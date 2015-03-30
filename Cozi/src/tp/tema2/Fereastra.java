package tp.tema2;



import java.util.Vector;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Fereastra extends Application implements EventHandler<ActionEvent>
{
	private boolean restart = false;
	private Stage stage;
	private Scene scene, scene2;
	
	private Vector<ListView<String>> list;
	private Vector<Label> timpiServire;
	private Vector<VBox> boxs;
	
	private Button simulare;
	private Button rezultate;
	private Button adaugaCoada;
	private Button stergeCoada;
	private Button inapoi;
	
	private Label tam, tsm, ov;
	private Label timer;
	
	private TextField nrCoada;
	private TextField nrClienti;
	private TextField nrCozi;
	private TextField durata;
	private TextField tmpSosMin;
	private TextField tmpSosMax;
	private TextField tmpSerMin;
	private TextField tmpSerMax;
	
	private int numarCozi = 0;
	private int numarClienti = 0;
	private int durataSimulare = 0;
	private int coadaStearsa = 0;
	private int timpSosMin = 0;
	private int timpSosMax = 0;
	private int timpSerMin = 0;
	private int timpSerMax = 0;
	
	private int oraVarf;
	private double timpAsteptareMediu;
	private double timpServireMediu;
	
	private HBox bottomBox;
	
	
	private boolean adauga;
	private boolean sterge;
	
	public boolean getAdauga()
	{
		return adauga;
	}
	public boolean getSterge()
	{
		return sterge;
	}
	public int getCoadaStearsa()
	{
		return coadaStearsa;
	}
	public void setAdauga(boolean adauga)
	{
		this.adauga = adauga;
	}
	public void setSterge(boolean sterge)
	{
		this.sterge = sterge;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		stage = primaryStage;
		primaryStage.setTitle("Simulare coada");
			
		simulare = new Button("Simulare");
		rezultate = new Button("Rezultate simulare");
		adaugaCoada = new Button("Adauga o coada");
		stergeCoada = new Button("Sterge coada:");
		inapoi = new Button("Inapoi");
		
		Label timpSosire = new Label("Introduceti intervalul\npentru timpul de sosire\nal clientilor:");
		Label timpServire = new Label("Introduceti intervalul\npentru timpul de servire\nal clientilor:");
		Label labelNrClienti = new Label("Introduceti numarul\ntotal de clienti:");
		Label labelCozi = new Label("Introduceti numarul\nde cozi:");
		Label labelDurata = new Label("Introduceti durata\nsimularii (secunde):");
		timer = new Label("0");
		
		nrClienti = new TextField();
		nrClienti.setMaxWidth(50);
		nrCozi = new TextField();
		nrCozi.setMaxWidth(50);
		durata = new TextField();
		durata.setMaxWidth(50);
		tmpSosMin = new TextField();
		tmpSosMin.setMaxWidth(50);
		tmpSosMax = new TextField();
		tmpSosMax.setMaxWidth(50);
		tmpSerMin = new TextField();
		tmpSerMin.setMaxWidth(50);
		tmpSerMax = new TextField();
		tmpSerMax.setMaxWidth(50);
		nrCoada = new TextField();
		nrCoada.setMaxWidth(50);
		
		simulare.setOnAction(this);
		stergeCoada.setOnAction(this);
		adaugaCoada.setOnAction(this);
		rezultate.setOnAction(this);
		inapoi.setOnAction(e->stage.setScene(scene));
		
		
		VBox vbox = new VBox(20);
		bottomBox = new HBox(20);
		HBox topBox = new HBox(20);
		HBox lastBox = new HBox(50);
		
		VBox v0, v1, v2, v3, v4;
		v0= new VBox(10);
		v1 = new VBox(10);
		v2 = new VBox(10);
		v3 = new VBox(10);
		v4 = new VBox(10);
		
		VBox st = new VBox(20);
		VBox dr = new VBox(20);
		
		v0.getChildren().addAll(timpServire, tmpSerMin, tmpSerMax);
		v1.getChildren().addAll(timpSosire, tmpSosMin, tmpSosMax);
		v2.getChildren().addAll(labelNrClienti, nrClienti);
		v3.getChildren().addAll(labelCozi, nrCozi);
		v4.getChildren().addAll(labelDurata, durata);
		
		v0.setPadding(new Insets(10));
		v0.setPrefSize(200, 150);
		v0.setStyle("-fx-background-color: rgba(255,255,255,0.8);" + "-fx-background-radius: 10;");
		
		v1.setPadding(new Insets(10));
		v1.setPrefSize(200, 150);
		v1.setStyle("-fx-background-color: rgba(255,255,255,0.8);" + "-fx-background-radius: 10;");
		
		v2.setPadding(new Insets(10));
		v2.setPrefSize(200, 150);
		v2.setStyle("-fx-background-color: rgba(255,255,255,0.8);" + "-fx-background-radius: 10;");
		
		v3.setPadding(new Insets(10));
		v3.setPrefSize(200, 150);
		v3.setStyle("-fx-background-color: rgba(255,255,255,0.8);" + "-fx-background-radius: 10;");
		
		v4.setPadding(new Insets(10));
		v4.setPrefSize(200, 150);
		v4.setStyle("-fx-background-color: rgba(255,255,255,0.8);" + "-fx-background-radius: 10;");
		

		topBox.getChildren().addAll(v0, v1, v2, v3, v4);
		topBox.setAlignment(Pos.TOP_CENTER);
		
		bottomBox.setAlignment(Pos.BOTTOM_CENTER);
		bottomBox.setStyle("-fx-background-color: rgba(255,255,255,0.8); -fx-background-radius: 10;");
		
		bottomBox.setPrefSize(200, 200);
		bottomBox.setPadding(new Insets(10));
		
		
		lastBox.setAlignment(Pos.BOTTOM_CENTER);
		lastBox.setStyle("-fx-background-color: rgba(255,255,255,0.8); -fx-background-radius: 10;");
		lastBox.setPadding(new Insets(10));
		
		st.getChildren().addAll(simulare, rezultate);
		HBox hdr = new HBox(20);
		hdr.getChildren().addAll(stergeCoada, nrCoada);
		dr.getChildren().addAll(adaugaCoada, hdr);
		lastBox.getChildren().addAll(st, dr);
		

		vbox.getChildren().addAll(topBox, bottomBox, lastBox, timer);
		vbox.setPadding(new Insets(20));
		vbox.setAlignment(Pos.CENTER);
		
		
		String image = Fereastra.class.getResource("image.jpg").toExternalForm();
		vbox.setStyle("-fx-background-image: url('" + image + "'); -fx-background-position: center center; -fx-background-repeat: stretch;");
		
		////////////////////Scene2
		
		tam = new Label("Timpul de asteptare mediu/client :");
		tsm = new Label("Timpul de servire mediu/coada :");
		ov = new Label("Ora de varf :");
		
		VBox centralBox = new VBox(30);
		centralBox.setStyle("-fx-background-image: url('" + image + "'); -fx-background-position: center; -fx-backgounrd-repeat: stretch;");
		centralBox.setAlignment(Pos.CENTER);
		centralBox.getChildren().addAll(tam, tsm, ov, inapoi);
		
		scene2 = new Scene(centralBox, 400, 200);
		scene = new Scene(vbox, 900, 600);
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void handle(ActionEvent event) 
	{
		if(event.getSource() == simulare)
		{
			if(restart == true)
			{
				try {
					this.start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
				restart = false;
				tam.setText("Timpul de asteptare mediu/client :");
				tsm.setText("Timpul de servire mediu/coada :");
				ov.setText("Ora de varf :");
			}
			else
			{
				restart = true;
				try
				{
					numarClienti = Integer.parseInt(nrClienti.getText());
					numarCozi = Integer.parseInt(nrCozi.getText());
					durataSimulare = Integer.parseInt(durata.getText());
					timpSosMin = Integer.parseInt(tmpSosMin.getText());
					timpSosMax = Integer.parseInt(tmpSosMax.getText());
					timpSerMin = Integer.parseInt(tmpSerMin.getText());
					timpSerMax = Integer.parseInt(tmpSerMax.getText());
					
					if(timpSosMin > timpSosMax)
					{
						throw new NumberFormatException("Intervalul pentru timpii \nde sosire nu a fost \nintrodus corect!");
					}
					if(timpSerMin > timpSerMax)
					{
						throw new NumberFormatException("Intervalul pentru timpii \nde servire nu a fost \nintrodus corect!");
					}
					if(numarCozi <= 0)
					{
						throw new NumberFormatException("Numarul de cozi trebuie\nsa fie mai mare sau egal cu 1.");
					}
					else if(numarClienti <= 0)
					{
						throw new NumberFormatException("Numarul de clienti trebuie\nsa fie mai mare sau\n egal cu 1.");
					}
					else if(durataSimulare <= 0)
					{
						throw new NumberFormatException("Durata simularii trebuie\nsa fie mai mare sau\n egala cu 1.");
					}
					
					list = new Vector<ListView<String>>(numarCozi, 1);
					timpiServire = new Vector<Label>(numarCozi, 1);
					boxs = new Vector<VBox>(numarCozi, 1);
					
					for(int i=0;i<numarCozi;i++)
					{	
						list.add(i, new ListView<String>());
						timpiServire.add(i, new Label("Timpul de servire pe coada " + (i+1) + " este: "));
						boxs.add(i, new VBox(10));
						boxs.get(i).getChildren().addAll(list.get(i), timpiServire.get(i));
						bottomBox.getChildren().add(boxs.get(i));
						
					}
					
					new Simulare(this, numarClienti, numarCozi, durataSimulare, timpSosMin, timpSosMax, timpSerMin, timpSerMax);
					
				}
				catch(NumberFormatException e)
				{
					afiseazaMesajEroare("Datele introduse sunt invalide!\n" + e.getMessage());
				}
			}
		}
		else if(event.getSource() == stergeCoada)
		{
			try{
				coadaStearsa = Integer.parseInt(nrCoada.getText());
				bottomBox.getChildren().remove(coadaStearsa-1);
				
				for(int i=coadaStearsa-1 ; i<numarCozi-1; i++)
				{
					list.set(i, list.get(i+1));
					timpiServire.set(i, timpiServire.get(i+1));
					boxs.set(i, boxs.get(i+1));
				}
				list.set(numarCozi-1, null);
				timpiServire.set(numarCozi-1, null);
				boxs.set(numarCozi-1, null);
				
				numarCozi--;
				
				this.sterge = true;
			}
			catch(NumberFormatException e)
			{
				afiseazaMesajEroare("Numarul cozii de sters nu este bun.\n");
			}
			catch(ArrayIndexOutOfBoundsException e)
			{
				afiseazaMesajEroare("Coada pe care doriti sa o stergeti nu exista.\n");
			}
			catch(IndexOutOfBoundsException e)
			{
				afiseazaMesajEroare("Coada pe care doriti sa o stergeti nu exista.\n");
			}
		}
		else if(event.getSource() == adaugaCoada)
		{	
			list.add(numarCozi, new ListView<String>());
			timpiServire.add(numarCozi, new Label("Timpul de servire pe coada " + (numarCozi+1) + " este: "));
			boxs.add(numarCozi, new VBox(10));
			boxs.get(numarCozi).getChildren().addAll(list.get(numarCozi), timpiServire.get(numarCozi));
			bottomBox.getChildren().add(boxs.get(numarCozi));
			
			this.numarCozi ++ ;
			
			this.adauga = true;
			
		}
		else if(event.getSource() == rezultate)
		{
			tam.setText("Timpul de asteptare mediu/client :		" + String.valueOf(timpAsteptareMediu) + " secunde");
			tsm.setText("Timpul de servire mediu/coada :		" + String.valueOf(timpServireMediu) + " secunde");
			ov.setText("Ora de varf : 		" + String.valueOf(oraVarf) + " secunde");
			stage.setScene(scene2);
		}
		
	}
	
	public void actualizareCoziSiTimer(Vector<Casa> listaCozi, int timp)
	{
		Platform.runLater(new Runnable(){

			@Override
			public void run() 
			{
				timer.setText(String.valueOf(timp));
				
				int i, j;
				String[] sir;
				
	
				for(i=0 ; i<numarCozi ; i++)
				{
					sir = new String[listaCozi.get(i).getLungimeCasa()];
					sir = listaCozi.get(i).afisare();
					ObservableList<String> lista = FXCollections.observableArrayList() ;
					
					
					for(j = 0 ; j<sir.length ; j++)
					{
						lista.add(sir[j]);
					}
					
					list.get(i).setItems(lista);
					timpiServire.get(i).setText("Timpul de servire pe coada " + (i+1) + " este: " + listaCozi.get(i).getTimpServireCasa());
					
				}
			}
			
		});
	}
	
	public void setRezultate(int oraVarf, double timpAsteptareMediu, double timpServireMediu)
	{
		this.oraVarf = oraVarf;
		this.timpAsteptareMediu = timpAsteptareMediu;
		this.timpServireMediu = timpServireMediu;
	}
	
	public void afiseazaMesajEroare(String s)
	{
		VBox vtext, vbuton, vWnd;
		vtext = new VBox();
		vbuton = new VBox();
		vWnd = new VBox(20);
		
		Stage mesaj = new Stage();
		Button buton = new Button("Ok");
		Text text = new Text(s);
		text.setTextAlignment(TextAlignment.CENTER);
		Scene scene ;
		
		vtext.getChildren().add(text);
		vbuton.getChildren().add(buton);
		vWnd.getChildren().addAll(vtext, vbuton);
		
		vtext.setAlignment(Pos.CENTER);
		vbuton.setAlignment(Pos.CENTER);
		vWnd.setAlignment(Pos.CENTER);
		
		String image = Fereastra.class.getResource("image.jpg").toExternalForm();
		vWnd.setStyle("-fx-background-image: url('" + image + "'); -fx-background-position: center center; -fx-background-repeat: stretch;");
		
		scene = new Scene(vWnd, 400, 200);
		
		mesaj.setTitle("Eroare");
		mesaj.setScene(scene);
		
		mesaj.show();
		
		buton.setOnAction(new EventHandler<ActionEvent>()
				{
					@Override
					public void handle(ActionEvent arg0) {
						mesaj.hide();
					}
				});
	}

}
