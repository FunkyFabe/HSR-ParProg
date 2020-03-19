package aufgabe3b;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;

import static java.time.Duration.*;

import org.junit.jupiter.api.Test;

class ReadWriteLockTest extends ConcurrentTest {
	@Test
	@DisplayName("Flat locks")
	void testSingleLocks() throws InterruptedException {
		assertTimeoutPreemptively(ofSeconds(1), () -> {
			var rwLock = new UpgradeableReadWriteLock();
			rwLock.readLock();
			rwLock.readUnlock();
			rwLock.upgradeableReadLock();
			rwLock.upgradeableReadUnlock();
			rwLock.writeLock();
			rwLock.writeUnlock();
		});
	}

	@Test
	@DisplayName("Read upgrade")
	void testUpgradeableNestedLocks() throws InterruptedException {
		assertTimeoutPreemptively(ofSeconds(1), () -> {
			var rwLock = new UpgradeableReadWriteLock();
			rwLock.upgradeableReadLock();
			rwLock.writeLock();
			rwLock.writeUnlock();
			rwLock.upgradeableReadUnlock();
		});
	}

	@Test
	@DisplayName("Read blocks write")
	void testReadWrite() throws InterruptedException {
		assertTimeoutPreemptively(ofSeconds(1), () -> {
			var rwLock = new UpgradeableReadWriteLock();
			rwLock.readLock();
			var thread2 = new Thread(() -> {
				try {
					rwLock.writeLock();
				} catch (InterruptedException e) {
					fail();
				}
			});
			thread2.start();
			thread2.join(100);
			assertTrue(thread2.isAlive(), "Write lock is not blocked by read lock");
			rwLock.readUnlock();
		});
	}

	@Test
	@DisplayName("Write blocks read")
	void testWriteRead() throws InterruptedException {
		assertTimeoutPreemptively(ofSeconds(1), () -> {
			var rwLock = new UpgradeableReadWriteLock();
			rwLock.writeLock();
			var thread2 = new Thread(() -> {
				try {
					rwLock.readLock();
				} catch (InterruptedException e) {
					fail();
				}
			});
			thread2.start();
			thread2.join(100);
			assertTrue(thread2.isAlive(), "Read lock is not blocked by write lock");
			rwLock.writeUnlock();
		});
	}

	@Test
	@DisplayName("Upgradeable read blocks write")
	void testUpgradeableReadWrite() throws InterruptedException {
		assertTimeoutPreemptively(ofSeconds(1), () -> {
			var rwLock = new UpgradeableReadWriteLock();
			rwLock.upgradeableReadLock();
			var thread2 = new Thread(() -> {
				try {
					rwLock.writeLock();
				} catch (InterruptedException e) {
					fail();
				}
			});
			thread2.start();
			thread2.join(100);
			assertTrue(thread2.isAlive(), "Write lock is not blocked by upgradeable read lock");
			rwLock.upgradeableReadUnlock();
		});
	}

	@Test
	@DisplayName("Write blocks upgradeable read")
	void testWriteUpgradeableRead() throws InterruptedException {
		assertTimeoutPreemptively(ofSeconds(1), () -> {
			var rwLock = new UpgradeableReadWriteLock();
			rwLock.writeLock();
			var thread2 = new Thread(() -> {
				try {
					rwLock.upgradeableReadLock();
				} catch (InterruptedException e) {
					fail();
				}
			});
			thread2.start();
			thread2.join(100);
			assertTrue(thread2.isAlive(), "Upgradeable read lock is not blocked by write lock");
			rwLock.writeUnlock();
		});
	}

	@Test
	@DisplayName("Upgradeable read blocks upgradeable read")
	void testMultipleUpgradeableRead() throws InterruptedException {
		assertTimeoutPreemptively(ofSeconds(1), () -> {
			var rwLock = new UpgradeableReadWriteLock();
			rwLock.upgradeableReadLock();
			var thread2 = new Thread(() -> {
				try {
					rwLock.upgradeableReadLock();
				} catch (InterruptedException e) {
					fail();
				}
			});
			thread2.start();
			thread2.join(100);
			assertTrue(thread2.isAlive(), "Upgradeable read lock do not mutually block");
			rwLock.upgradeableReadUnlock();
		});
	}

	@Test
	@DisplayName("Write blocks write")
	void testMultipleWrite() throws InterruptedException {
		assertTimeoutPreemptively(ofSeconds(1), () -> {
			var rwLock = new UpgradeableReadWriteLock();
			rwLock.writeLock();
			var thread2 = new Thread(() -> {
				try {
					rwLock.writeLock();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
			thread2.start();
			thread2.join(100);
			assertTrue(thread2.isAlive(), "Write locks do not mutually block");
			rwLock.writeUnlock();
		});
	}

	@Test
	@DisplayName("Parallel reads")
	void testReadRead() throws InterruptedException {
		assertTimeoutPreemptively(ofSeconds(1), () -> {
			var rwLock = new UpgradeableReadWriteLock();
			rwLock.readLock();
			var otherThreads = new Thread[10];
			for (int index = 0; index < otherThreads.length; index++) {
				otherThreads[index] = new Thread(() -> {
					try {
						rwLock.readLock();
						Thread.sleep(10);
						rwLock.readUnlock();
					} catch (InterruptedException e) {
						fail();
					}
				});
			}
			for (var thread : otherThreads) {
				thread.start();
			}
			for (var thread : otherThreads) {
				thread.join();
			}
			Thread.sleep(100);
			rwLock.readUnlock();
			rwLock.writeLock();
			rwLock.writeUnlock();
		});
	}

	@Test
	@DisplayName("Reads parallel to one upgradeable read")
	void testReadUpgradeableRead() throws InterruptedException {
		assertTimeoutPreemptively(ofSeconds(1), () -> {
			var rwLock = new UpgradeableReadWriteLock();
			rwLock.upgradeableReadLock();
			var otherThreads = new Thread[10];
			for (int index = 0; index < otherThreads.length; index++) {
				otherThreads[index] = new Thread(() -> {
					try {
						rwLock.readLock();
						Thread.sleep(10);
						rwLock.readUnlock();
					} catch (InterruptedException e) {
						fail();
					}
				});
			}
			for (var thread : otherThreads) {
				thread.start();
			}
			for (var thread : otherThreads) {
				thread.join();
			}
			Thread.sleep(100);
			rwLock.upgradeableReadUnlock();
			rwLock.writeLock();
			rwLock.writeUnlock();
		});
	}
}
