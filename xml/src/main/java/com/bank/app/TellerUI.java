package com.bank.app;

import static java.lang.System.err;
import static java.lang.System.out;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.util.StringUtils;

import com.bank.domain.TransferConfirmation;
import com.bank.service.TransferService;

public class TellerUI {
	
	private final TransferService transferService;

	public TellerUI(TransferService transferService) {
		this.transferService = transferService;
	}

	public void start() throws IOException {
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		
		while (true) {
			out.print("\nenter transfer [<amt>, <src>, <dest>], 'quit' to cancel: ");
			String line = StringUtils.trimAllWhitespace(console.readLine());
			
			if ("quit".equals(line))
				break;
			
			String[] input = StringUtils.commaDelimitedListToStringArray(line);
			
			try {
				double transferAmount = Double.valueOf(input[0]);
				String srcAcctId = input[1];
				String dstAcctId = input[2];
				
				TransferConfirmation conf = transferService.transfer(transferAmount, srcAcctId, dstAcctId);
				out.println();
				out.println(conf);
			} catch (Throwable t) {
				err.println();
				err.println(t);
			}
		}
	}
}
