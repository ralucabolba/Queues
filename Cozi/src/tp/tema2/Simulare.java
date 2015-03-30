package tp.tema2;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import tp.tema2.Fereastra;

public class Simulare extends TimerTask
{
	private Client client;
	private Log log;
	
	private boolean sterge;
	private boolean adauga;
	
	private Fereastra f;
	
	private int timpTotal;
	private int nrClienti, nrCozi, durata, timpSosMin, timpSosMax, timpSerMin, timpSerMax;
	private int numarClientiMaxim = 0;
	private int numarClientiServiti = 0;
	private int numarCoziMaxim;
	private int oraVarf = 0;
	private int timpServireTotal = 0;
	private int timpAsteptare = 0;
	
	private Timer timer;
	private LinkedList<Client> listaClienti;
	
	private Vector<Casa> listaCase;
	
	public Simulare(Fereastra f, int nrClienti, int nrCozi, int durata, int timpSosMin, int timpSosMax, int timpSerMin, int timpSerMax)
	{
		this.f = f;
		this.nrClienti = nrClienti;
		this.nrCozi = nrCozi;
		this.durata = durata;
		this.timpSerMin = timpSerMin;
		this.timpSerMax = timpSerMax;
		this.timpSosMin = timpSosMin;
		this.timpSosMax = timpSosMax;
		
		this.numarCoziMaxim = nrCozi;
		timpTotal = durata;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		log = new Log();
		log.append("Simulare " + dateFormat.format(date));
		
		listaCase = new Vector<Casa>(nrCozi, 1);
		for(int i=0;i<nrCozi;i++)
		{
			listaCase.add(new Casa());
		}
		
		generareClienti();
		
		if(durata>0 && nrCozi>0)
		{
			timer = new Timer(false);
			timer.scheduleAtFixedRate(this, 0, 1000);
		}
		else
		{
			f.afiseazaMesajEroare("");
		}
        
	}
	
	
	public void generareClienti()
	{
		listaClienti = new LinkedList<Client>();
		
		Random nrRandom = new Random();
		int timpSos, timpSer;
		
		for(int i=1;i<=nrClienti;i++)
		{
			timpSos = nrRandom.nextInt(timpSosMax - timpSosMin + 1) + timpSosMin ;
			timpSer = nrRandom.nextInt(timpSerMax - timpSerMin + 1) + timpSerMin ;
			
			client = new Client(i, timpSos, timpSer);
			listaClienti.add(client);
		}
		Collections.sort(listaClienti);
		for(int i=0;i<nrClienti;i++)
		{
			listaClienti.get(i).setIndex(i+1);
		}
	}
	
	
	public int casaCeaMaiLibera()
	{
		int minim = 0;
		for(int i = 1 ; i < nrCozi ; i++)
		{
				if(listaCase.get(i).getTimpServireCasa() < listaCase.get(minim).getTimpServireCasa())
				{
					minim = i;
				}
		}
		return minim;
	}
	
	public int casaCeaMaiOcupata()
	{
		int maxim = 0;
		for(int i = 1 ; i < nrCozi ; i++)
		{
				if(listaCase.get(i).getTimpServireCasa() > listaCase.get(maxim).getTimpServireCasa())
				{
					maxim = i;
				}
		}
		return maxim;
	}

	public int numarClientiCozi()
	{
		int nr = 0, i;
		for(i=0 ; i<nrCozi ; i++)
		{
			nr+=listaCase.get(i).getLungimeCasa();
		}
		return nr;
	}
	
	public void repartizareClient(Client client)
	{
		if(client != null)
		{
			int index = casaCeaMaiLibera();
			listaCase.get(index).adaugaClient(client);
			log.append("La secunda " + (timpTotal - durata) + " :	" + client.toString() + " s-a asezat la coada " + (index+1));
		}
		
	}
	
	@Override
	public void run()
	{
		
		sterge = f.getSterge();
		if(sterge == true)
		{
			f.setSterge(false);
			int coadaStearsa = f.getCoadaStearsa() - 1;
			
			log.append("________________________________________________________________________________________________");
			log.append("La secunda " + (timpTotal - durata) + " :	" + "Coada " + (coadaStearsa+1) + " a fost inchisa.\nClientii asezati la aceasta casa au fost repartizati astfel:\n");
			LinkedList<Client> cl = listaCase.get(coadaStearsa).getClienti(); //luam clientii din acea coada
			
			listaCase.get(coadaStearsa).stop();//inchidem threadul ce se ocupa de acea coada
			
			for(int i=coadaStearsa ; i<nrCozi -1;i++)
			{
				listaCase.set(i, listaCase.get(i+1));
			}
			listaCase.set(nrCozi-1, null);
			nrCozi--;
			
			client = cl.peek();
			
			while(client!=null)
			{
				repartizareClient(client);
				cl.remove();
				client = cl.peek();
			}
			
			log.append("________________________________________________________________________________________________");
		}
		adauga = f.getAdauga();
		if(adauga == true)
		{
			numarCoziMaxim++;
			nrCozi++;
			
			log.append("________________________________________________________________________________________________");
			log.append("La secunda " + (timpTotal - durata) + " :	" + "S-a deschis o noua casa.\n");
			f.setAdauga(false);
			
			listaCase.add(nrCozi-1, new Casa());
			while(casaCeaMaiLibera() == nrCozi-1) //cat timp casa deschisa este cea mai "libera"
			{
				int indexCasa = casaCeaMaiOcupata();
				client = listaCase.get(indexCasa).getClienti().removeLast();
				listaCase.get(nrCozi-1).adaugaClient(client);
				
				log.append(client.toString() + " de la casa " + (indexCasa+1) + " a venit la noua casa deschisa.");
			}
			log.append("________________________________________________________________________________________________");
		}
		
		f.actualizareCoziSiTimer(listaCase, timpTotal - durata);
		
		client = listaClienti.peek();
		while(client!=null && client.getTimpSosire() == timpTotal - durata)
		{	
			timpServireTotal += client.getTimpServire();
			repartizareClient(client);
			listaClienti.remove();
			client = listaClienti.peek();
		}
		
		
		for(int i=0 ; i<nrCozi ; i++)
		{
			if(listaCase.get(i).esteLibera() == false)
			{
				if(listaCase.get(i).getState() == Thread.State.NEW)
				{
					listaCase.get(i).start();
				}
				else if(listaCase.get(i).getState() == Thread.State.TERMINATED)
				{
					LinkedList<Client> clienti2 = listaCase.get(i).getClienti();
					listaCase.set(i, new Casa());
					client = clienti2.peek();
					while(client != null)
					{
						listaCase.get(i).adaugaClient(client);
						clienti2.remove();
						client = clienti2.peek();
					}
					listaCase.get(i).start();
				}
			}
		}
		
		//vedem daca numarul de clienti la cozi este maxim
		if(numarClientiCozi() > numarClientiMaxim)
		{
			numarClientiMaxim = numarClientiCozi();
			oraVarf = timpTotal - durata;
		}
		
		//vedem daca a fost vreun client servit in fiecare coada, daca da, calculam timpul de asteptare pentru fiecare
		for(int i=0 ; i<nrCozi ; i++)
		{
			client = listaCase.get(i).getClientServit();
			if(client != null)
			{
				log.append("La secunda " + (timpTotal - durata) + " :	" + client.toString() + " a iesit din coada " + (i+1));
				timpAsteptare += (timpTotal-durata) - client.getTimpSosire() - client.getTimpServire();
				numarClientiServiti ++;
			}
		}
		
		if(durata == 0)
		{
			double timpAstM;
			
			if(numarClientiServiti == 0)
			{
				timpAstM = 0;
			}
			else
			{
				timpAstM = Math.round((double)timpAsteptare/numarClientiServiti * 100.0)/100.0;
			}
			f.setRezultate(oraVarf, timpAstM , Math.round((double)timpServireTotal/numarCoziMaxim * 100.0)/100.0);
			
			log.append("Simularea s-a incheiat!");
			log.append("________________________________________________________________________________________________");
			
			timer.cancel();
			for(int i = 0 ;i< nrCozi;i++)
			{
				listaCase.get(i).stop();
			}
		}
		
		durata --;
		
	}
		
}
