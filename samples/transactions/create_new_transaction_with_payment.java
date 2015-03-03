PaymentService paymentService = paymillContext.getPaymentService();
Payment payment = paymentService.createWithToken(
    "098f6bcd4621d373cade4e832627b4f6"
);
TransactionService transactionService = paymillContext.getTransactionService();
Transaction transaction = transactionService.createWithToken(
    payment,
    4200,
    "EUR",
    "Test Transaction"
);
