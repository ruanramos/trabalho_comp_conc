public class SavingsAccount_2 {

	private int balance = 0;
	private int prefs = 0;

	public SavingsAccount_2(int initialBalance) {
		if (initialBalance >= 0)
			this.balance = initialBalance;
	}

	public synchronized void deposit(int k) {
		this.balance += k;
		notifyAll();
	}

	public synchronized void ordWithdraw (int k) {
		while (this.balance < k && prefs > 0)
			wait();
		this.balance -= k;
	}

	public synchronized void prefWithdraw (int k) {
		this.prefs++;
		while (this.balance < k)
			wait();
		this.balance -= k;
		this.prefs--;
		notifyAll();
	}

}
