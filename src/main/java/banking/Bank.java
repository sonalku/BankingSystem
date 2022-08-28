package banking;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Private Variables:<br>
 * {@link #accounts}: List&lt;Long, Account&gt;
 */
public class Bank implements BankInterface {
	private LinkedHashMap<Long, Account> accounts;

	Bank() {
		accounts = new LinkedHashMap<>();
	}

	public synchronized Entry getLast(LinkedHashMap<Long, Account> accounts) {
		int count = 1;
		Entry<Long, Account> lastEntry = null;
		for (Map.Entry<Long, Account> it : accounts.entrySet()) {
			if (count == accounts.size()) {
				lastEntry = it;
			}
			count++;
		}
		return lastEntry;
	}

	private Account getAccount(Long accountNumber) {
		// complete the function
		if (accounts.containsKey(accountNumber))
			return accounts.get(accountNumber);
		else
			return null;
	}

	public Long openCommercialAccount(Company company, int pin, double startingDeposit) {
		// complete the function
		Long nextAccNumber = getAccountNumber();
		CommercialAccount newAccount = new CommercialAccount(company, nextAccNumber, pin, startingDeposit);
		accounts.put(nextAccNumber, newAccount);
		return nextAccNumber;
	}

	public Long openConsumerAccount(Person person, int pin, double startingDeposit) {
		Long nextAccNumber = getAccountNumber();
		ConsumerAccount newAccount = new ConsumerAccount(person, nextAccNumber, pin, startingDeposit);
		accounts.put(nextAccNumber, newAccount);
		return nextAccNumber;
	}

	public boolean authenticateUser(Long accountNumber, int pin) {
		// complete the function

		if (accounts.containsKey(accountNumber)) {
			Account account = accounts.get(accountNumber);
			return account.validatePin(pin);
		}
		return false;
	}

	public double getBalance(Long accountNumber) {
		if (accounts.containsKey(accountNumber)) {
			Account account = accounts.get(accountNumber);
			return account.getBalance();
		}
		return 0.0;
	}

	public void credit(Long accountNumber, double amount) {
		if (accounts.containsKey(accountNumber)) {
			Account account = accounts.get(accountNumber);
			account.creditAccount(amount);
			accounts.put(accountNumber, account);
		}
	}

	public boolean debit(Long accountNumber, double amount) {
		if (accounts.containsKey(accountNumber)) {
			Account account = accounts.get(accountNumber);
			if (account.debitAccount(amount)) {
				accounts.put(accountNumber, account);
				return true;
			}
			else
				return false;
		} else
			return false;
	}

	private Long getAccountNumber() {
		long accountNumber;
		if (accounts.isEmpty()) {
			accountNumber = System.currentTimeMillis();
		} else {
			Entry<Long, Account> lastEntry = getLast(accounts);
			Account lastAccount = lastEntry.getValue();
			Long lastUsedAccountNumber = lastAccount.getAccountNumber();
			accountNumber = lastUsedAccountNumber + 1;
		}
		return accountNumber;
	}
}
