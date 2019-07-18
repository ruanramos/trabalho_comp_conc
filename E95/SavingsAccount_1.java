public class SavingsAccount_1 {

	private int balance = 0;

	public SavingsAccount_1(int initialBalance) {
		if (initialBalance >= 0)
			this.balance = initialBalance;
	}

	public synchronized void deposit(int k) {
		this.balance += k;
		notifyAll();
	}

	public synchronized void withdraw (int k) {
		while (this.balance < k)
			wait();
		this.balance -= k;
	}

}
