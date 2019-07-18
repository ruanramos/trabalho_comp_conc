
public class SimpleReadWriteLock implements ReadWriteLock {
  int readers;
  boolean writer;
  Lock readLock, writeLock;

  public SimpleReadWriteLock() {
      writer = false;
      readers = 0;
      readLock = new ReadLock();
      writeLock = new WriteLock();
  }

  public Lock readLock()
  {
    return readLock;
  }

  public Lock writeLock()
  {
    return writeLock;
  }

  class ReadLock implements Lock {
    public synchronized void lock() {
      try {
        while (writer) {
          wait();
        }
        readers++;
      }
    }
    public synchronized void unlock() {
      try {
        readers--;
      if (readers == 0)
        notifyAll();
      }
    }
  }

  protected class WriteLock implements Lock {
    public synchronized void lock() {
     try {
      while (readers > 0) {
        wait();
      }
       writer = true;
      }
    }
   public void unlock() {
     writer = false;
     notifyAll();
   }
  }
}
