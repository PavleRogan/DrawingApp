package strategy;

public class SavingManager implements Saving {
	
	public Saving saving;

	
	public SavingManager(Saving saving) {
		this.saving=saving;
	}
	
	@Override
	public void save(String fileAddress) {
		// TODO Auto-generated method stub
		saving.save(fileAddress);

	}

}
