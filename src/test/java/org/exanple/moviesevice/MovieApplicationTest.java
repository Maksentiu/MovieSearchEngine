package org.exanple.moviesevice;

import org.example.moviservice.MovieApplication;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

import static org.mockito.Mockito.mockStatic;

class MovieApplicationTest {

    @Test
    void testMain() {
        // Проверяем, что SpringApplication.run вызывается с правильными аргументами
        try (MockedStatic<SpringApplication> springApplicationMockedStatic = mockStatic(SpringApplication.class)) {
            MovieApplication.main(new String[]{});
            springApplicationMockedStatic.verify(() -> SpringApplication.run(MovieApplication.class, new String[]{}));
        }
    }

    @Test
    void testMainWithCustomArgs() {
        // Проверяем, что SpringApplication.run вызывается с правильными аргументами при наличии пользовательских аргументов
        String[] args = {"--customArg=test"};
        try (MockedStatic<SpringApplication> springApplicationMockedStatic = mockStatic(SpringApplication.class)) {
            MovieApplication.main(args);
            springApplicationMockedStatic.verify(() -> SpringApplication.run(MovieApplication.class, args));
        }
    }
}