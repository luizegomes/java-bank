package br.com.dio.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import java.util.stream.Stream;

import lombok.Getter;

public abstract class Wallet {

    @Getter
    private final BankService service;

    protected final List<Money> money;

    public Wallet(final BankService serviceType) {
        this.service = serviceType;
        this.money = new ArrayList<>();
    }

    protected List<Money> generateMoney(final long amount, final String description) {
        return Stream.generate(() -> {
            var history = new MoneyAudit(UUID.randomUUID(), service, description, OffsetDateTime.now());
            return new Money(history);
        }).limit(amount).toList();
    }

    public long getFounds() {
        return money.size();
    }

    @Override
    public String toString() {
        return "Wallet{"
                + "service=" + service
                + ", saldo=" + getFounds()
                + ", transações=" + getFinancialTransactions().size()
                + '}';
    }

    public void addMoney(final List<Money> money, final BankService service, final String description) {
        var history = new MoneyAudit(UUID.randomUUID(), service, description, OffsetDateTime.now());
        money.forEach(m -> m.addHistory(history));
        this.money.addAll(money);
    }

    public List<Money> reduceMoney(final long amount) {
        List<Money> toRemove = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            toRemove.add(this.money.remove(0));
        }
        return toRemove;
    }

    public List<MoneyAudit> getFinancialTransactions() {
        return money.stream().flatMap(m -> m.getHistory().stream()).toList();
    }
}
