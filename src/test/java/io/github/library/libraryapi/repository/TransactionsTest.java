package io.github.library.libraryapi.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.library.libraryapi.service.TransactionService;

@SpringBootTest
public class TransactionsTest {

	@Autowired
	TransactionService transactionService;
	
	@Test
	void simpleTransaction() {
		transactionService.executar();
	}
	
}
