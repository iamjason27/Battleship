
public abstract class ship 
{
	private int length;
	private String name;
	private boolean[] isHit;
	private String direction;
	

	public ship(int length, String name)
	{
	 this.length = length;
	 this.name = name;
	 isHit = new boolean[length];
	 for(int i = 0; i < isHit.length;i++)
	 {
		 isHit[i] = false;
	 }
	}
	
	public int getLength()
	{
		return length;
	}
	
	public String getDirection()
	{
		return direction;
	}
	
	
	
	
	
	
}
