package org.exanple.moviesevice.services;

import org.example.moviservice.services.RequestCounterService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestCounterServiceTest {

    @Test
    void testIncrementAndGet() {
        // Arrange
        RequestCounterService counterService = new RequestCounterService();

        // Act
        int initialCount = counterService.incrementAndGet(); // первый вызов должен увеличить счетчик на 1
        int secondCount = counterService.incrementAndGet(); // второй вызов должен увеличить счетчик еще на 1

        // Assert
        assertEquals(1, initialCount); // проверяем, что первый вызов вернул 1
        assertEquals(2, secondCount); // проверяем, что второй вызов вернул 2
    }

    @Test
    void testThreadSafety() throws InterruptedException {
        // Arrange
        RequestCounterService counterService = new RequestCounterService();

        // Act
        Runnable incrementTask = () -> {
            for (int i = 0; i < 1000; i++) {
                counterService.incrementAndGet();
            }
        };

        // создаем два потока, которые одновременно вызывают incrementAndGet()
        Thread thread1 = new Thread(incrementTask);
        Thread thread2 = new Thread(incrementTask);

        thread1.start();
        thread2.start();

        // ждем завершения потоков
        thread1.join();
        thread2.join();

        // получаем текущее значение счетчика
        int totalCount = counterService.incrementAndGet();

        // Assert
        assertEquals(2001, totalCount); // сумма из 1000 инкрементаций из thread1 и 1000 из thread2, плюс первая инкрементация
    }
}

