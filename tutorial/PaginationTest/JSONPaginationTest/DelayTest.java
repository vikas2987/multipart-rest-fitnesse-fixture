import fit.Fixture;

public class DelayTest extends fit.Fixture
{
	public void setDelay(int d) throws InterruptedException {	
		Thread.sleep(d);
	}	
}
