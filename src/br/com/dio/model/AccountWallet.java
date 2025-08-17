package br.com.dio.model;

import static br.com.dio.model.BankService.ACCOUNT;

import java.util.List;

public class AccountWallet extends Wallet {

    private final List<String> pix;

    public AccountWallet(final List<String> pix) {
        super(ACCOUNT);
        this.pix = pix;
    }

    public AccountWallet(final long amount, final List<String> pix) {
        super(ACCOUNT);
        this.pix = pix;
        String descricaoInicial = "valor de criação da conta";
        addMoney(amount, descricaoInicial);
    }

    public void addMoney(final long amount, final String descripition) {
        List<Money> money = generateMoney(amount, descripition);
        this.money.addAll(money);
    }

    public List<String> getPix() {
        return this.pix;
    }
}
