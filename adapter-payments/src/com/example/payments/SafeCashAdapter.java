package com.example.payments;

// wraps SafeCashClient - their sdk takes (amount, user) which is reversed
public class SafeCashAdapter implements PaymentGateway {

    private final SafeCashClient client;

    public SafeCashAdapter(SafeCashClient client) {
        this.client = client;
    }

    @Override
    public String charge(String customerId, int amountCents) {
        // note: safecash wants amount first, then the user
        SafeCashPayment payment = client.createPayment(amountCents, customerId);
        return payment.confirm();
    }
}
