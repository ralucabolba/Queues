package tp.tema2;

public class Client implements Comparable<Client>
{
	private int index;
	private int timpSosire;
	private int timpServire;
	private int timpServireInitial;
	
	public Client(int index, int timpSosire, int timpServire)
	{
		this.index = index;
		this.timpSosire = timpSosire;
		this.timpServire = timpServire;
		this.timpServireInitial = timpServire;
	}
	
	public int getTimpSosire()
	{
		return timpSosire;
	}
	
	public int getTimpServire()
	{
		return timpServire;
	}
	public int getTimpServireInitial()
	{
		return timpServireInitial;
	}
	public void setTimpServire(int timpServireNou)
	{
		this.timpServire = timpServireNou;
	}
	public void setIndex(int indexNou)
	{
		this.index = indexNou;
	}
	public String toString()
	{
		String sir = "";
		//sir = "Client cu timp de sosire: " + timpSosire + " si timp de servire: " + timpServire ;
		sir = "Client "+index+ " 	| tso: " + timpSosire + " 	| tser: " + timpServire;
		return sir;
	}

	@Override
	public int compareTo(Client arg0) {
		return new Integer(this.timpSosire).compareTo(new Integer(arg0.timpSosire));
	}
	
}
