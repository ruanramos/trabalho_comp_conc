public class Rooms {

	public interface Handler {
		void onEmpty();
 	}

  	private int nRooms;
  	private int ocpRooms = 0; // number of rooms currently occupied
	private occpRoom = -1;

  	public Rooms(int m) {
		this.nRooms = m;
  	};

	void synchronized enter(int i) {

		while (this.ocpRooms > 0 || i  != occpRoom) {
			try { wait(); } catch (InterruptedException e) { }
            finally { }
		}

		this.ocpRooms += 1; // increase the number of occupied
  	};

	boolean synchronized exit() {

		return true;
	};

	public void synchronized setExitHandler(int i, Rooms.Handler h) { };
}
