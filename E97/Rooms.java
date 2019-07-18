import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Rooms {

    private final Lock lock = new ReentrantLock();

    private final Condition notEmpty = lock.newCondition();
	private final Condition handlerRunning = lock.newCondition();

	public interface Handler {
		void onEmpty();
 	}

  	private int nRooms;
  	private int ocpRooms = 0; // número de ocupantes no quarto atual
	private occpRoom = -1;
	private boolean isHandlerRunning = false;
	private boolean[] hasHandler;

  	public Rooms(int m) {
		this.nRooms = m;
		this.hasHandler = new boolean[m];
  	};

	void enter(int i) {
		lock.lock();
		// se não há nenhum quarto ocupado
		while (isHandlerRunning)
			handlerRunning.await();

		try {
			while (i  != occpRoom || occpRoom != -1)
				notEmpty.await();

			occpRoom = i;
			ocpRooms += 1;

		} catch (InterruptedException e) { }
		finally {
			lock.unlock();
		}
  	}

	boolean exit() {
		lock.lock();
		try {

			ocpRooms--;
			if (ocpRooms == 0 && hasHandler[occpRoom]) {
				isHandlerRunning = true;
				this.Handler.onEmpty();
			}

		} catch(Exception e) {}
		finally {
			isHandlerRunning = false;
			lock.unlock();
		}
	}

	public void setExitHandler(int i, Rooms.Handler h) {
		lock.lock();
		h.onEmpty();
		handlerRunning.signalAll();
		lock.unlock();
	}
}
