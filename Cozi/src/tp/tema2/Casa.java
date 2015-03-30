package tp.tema2;

import java.util.*;
public class Casa extends Thread
{
	private Client clientServit;
	private LinkedList<Client> listaClienti;
	
	public Casa()
	{
		this.clientServit = null;
		listaClienti = new LinkedList<Client>();
	}
	public Client getClientServit()
	{
		return clientServit;
	}
	public LinkedList<Client> getClienti()
	{
		return listaClienti;
	}
	public void adaugaClient(Client c)
	{
		listaClienti.add(c);
	}
	
	public Client terminaServitClient()
	{
		Client c = listaClienti.peek();
		if(! listaClienti.isEmpty())
		{
			listaClienti.remove();
		}
		c.setTimpServire(c.getTimpServireInitial());
		return c;
	}
	
	public int servesteClient()
	{
		Client c = listaClienti.peek();
		int timp = c.getTimpServire();
		if(timp > 0)
		{
			c.setTimpServire(timp - 1);
		}
		
		return timp - 1;
	}
	
	public boolean esteLibera()
	{
		return listaClienti.isEmpty();
	}
	
	public int getTimpServireCasa()
	{
		if(esteLibera())
		{
			return 0;
		}
		
		int timp = 0;
		int i=0;
		
		for(i=0 ;i<getLungimeCasa() ; i++)
		{
			timp += listaClienti.get(i).getTimpServire();
		}
		return timp;
	}
	
	public String[] afisare()
	{
		String[] sir = new String[listaClienti.size()];
		int i=0;
		ListIterator<Client> iterator = listaClienti.listIterator();
		while(iterator.hasNext())
		{
			sir[i] = iterator.next().toString();
			i++;
		}
		return sir;
	}
	
	public int getLungimeCasa()
	{
		return listaClienti.size();
	}
	
	
	@Override
	public void run() 
	{
		while(!esteLibera())
		{
			try
			{
				int timpRamas = servesteClient();
				if(timpRamas == 0)
				{
					clientServit = terminaServitClient();
				}
				else
				{
					clientServit = null;
				}
				Thread.sleep(1000);
			}
			catch(InterruptedException e)
			{
				System.err.println(e.getMessage());
			}
		}
		clientServit = null;
	}

}
