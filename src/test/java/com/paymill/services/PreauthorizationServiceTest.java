package com.paymill.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.paymill.context.PaymillContext;
import com.paymill.models.Payment;
import com.paymill.models.Preauthorization;
import com.paymill.models.Transaction;

public class PreauthorizationServiceTest {

  private String                  token       = "098f6bcd4621d373cade4e832627b4f6";
  private Integer                 amount      = 4202;
  private String                  currency    = "EUR";
  private String                  description = "shake the preauthorization";
  private Payment                 payment;

  private List<Preauthorization>  preauthorizations;

  private PreauthorizationService preauthorizationService;
  private PaymentService          paymentService;

  @BeforeClass
  public void setUp() {
    PaymillContext paymill = new PaymillContext( System.getProperty( "apiKey" ) );
    this.preauthorizationService = paymill.getPreauthorizationService();
    this.paymentService = paymill.getPaymentService();

    this.preauthorizations = new ArrayList<Preauthorization>();
    this.payment = this.paymentService.createWithToken( this.token );
  }

  @AfterClass
  public void tearDown() {
    for( Preauthorization transaction : preauthorizations )
      this.preauthorizationService.delete( transaction );
  }

  @Test
  public void testCreateWithToken_shouldSucceed() {
    Preauthorization preauthorization = this.preauthorizationService.createWithToken( this.token, this.amount, this.currency );
    this.validatePreauthorization( preauthorization );
    this.validateTransaction( preauthorization.getTransaction() );
    Assert.assertEquals( preauthorization.getTransaction().getDescription(), null );
    this.preauthorizations.add( preauthorization );
  }

  @Test
  public void testCreateWithTokenAndDescription_shouldSucceed() {
    Preauthorization preauthorization = this.preauthorizationService.createWithToken( this.token, this.amount, this.currency, this.description );
    this.validatePreauthorization( preauthorization );
    this.validateTransaction( preauthorization.getTransaction() );
    Assert.assertEquals( preauthorization.getDescription(), this.description );
    Assert.assertEquals( preauthorization.getTransaction().getDescription(), this.description );
    this.preauthorizations.add( preauthorization );
  }

  @Test( dependsOnMethods = "testCreateWithToken_shouldSucceed" )
  public void testCreateWithPayment_shouldSucceed() throws Exception {
    Thread.sleep( 1000 );
    Preauthorization preauthorization = this.preauthorizationService.createWithPayment( this.payment, this.amount, this.currency );
    this.validatePreauthorization( preauthorization );
    this.validateTransaction( preauthorization.getTransaction() );
    Assert.assertEquals( preauthorization.getDescription(), null );
    Assert.assertEquals( preauthorization.getTransaction().getDescription(), null );
    this.preauthorizations.add( preauthorization );
  }

  @Test( dependsOnMethods = "testCreateWithToken_shouldSucceed" )
  public void testCreateWithPaymentAndDescription_shouldSucceed() throws Exception {
    Thread.sleep( 1000 );
    Preauthorization preauthorization = this.preauthorizationService.createWithPayment( this.payment, this.amount, this.currency, this.description );
    this.validatePreauthorization( preauthorization );
    this.validateTransaction( preauthorization.getTransaction() );
    Assert.assertEquals( preauthorization.getDescription(), this.description );
    Assert.assertEquals( preauthorization.getTransaction().getDescription(), this.description );
    this.preauthorizations.add( preauthorization );
  }

  //@Test( dependsOnMethods = "testListOrderByFilterAmountLessThan" )
  public void testListOrderByOffer() {
    Preauthorization.Order orderDesc = Preauthorization.createOrder().byCreatedAt().desc();
    Preauthorization.Order orderAsc = Preauthorization.createOrder().byCreatedAt().asc();

    List<Preauthorization> preauthorizationDesc = this.preauthorizationService.list( null, orderDesc ).getData();

    List<Preauthorization> preauthorizationAsc = this.preauthorizationService.list( null, orderAsc ).getData();

    Assert.assertEquals( preauthorizationDesc.get( 0 ).getId(), preauthorizationAsc.get( preauthorizationAsc.size() - 1 ).getId() );
    Assert.assertEquals( preauthorizationDesc.get( preauthorizationDesc.size() - 1 ).getId(), preauthorizationAsc.get( 0 ).getId() );
  }

  @Test( dependsOnMethods = "testCreateWithPayment_shouldSucceed" )
  public void testListOrderByFilterAmountGreaterThan() {
    Preauthorization.Filter filter = Preauthorization.createFilter().byAmountGreaterThan( amount - 100 );
    List<Preauthorization> preauthorization = this.preauthorizationService.list( filter, null ).getData();
    Assert.assertFalse( preauthorization.isEmpty() );
  }

  @Test( dependsOnMethods = "testListOrderByFilterAmountGreaterThan" )
  public void testListOrderByFilterAmountLessThan() {
    Preauthorization.Filter filter = Preauthorization.createFilter().byAmountLessThan( amount + 100 );
    List<Preauthorization> preauthorizations = this.preauthorizationService.list( filter, null ).getData();
    Assert.assertFalse( preauthorizations.isEmpty() );
  }

  private void validatePreauthorization( final Preauthorization preauthorization ) {
    Assert.assertNotNull( preauthorization );
    Assert.assertNotNull( preauthorization.getId() );
    Assert.assertEquals( preauthorization.getAmount(), this.amount );
    Assert.assertEquals( preauthorization.getCurrency(), this.currency );
    Assert.assertEquals( preauthorization.getStatus(), Preauthorization.Status.CLOSED );
    Assert.assertTrue( BooleanUtils.isFalse( preauthorization.getLivemode() ) );
    Assert.assertNotNull( preauthorization.getPayment() );
    Assert.assertNotNull( preauthorization.getClient() );
  }

  private void validateTransaction( final Transaction transaction ) {
    Assert.assertNotNull( transaction );
    Assert.assertNotNull( transaction.getId() );
    Assert.assertEquals( transaction.getAmount(), this.amount );
    Assert.assertEquals( transaction.getOriginAmount(), Integer.valueOf( this.amount ) );
    Assert.assertEquals( transaction.getCurrency(), this.currency );
    Assert.assertEquals( transaction.getStatus(), Transaction.Status.PREAUTH );
    Assert.assertTrue( BooleanUtils.isFalse( transaction.getLivemode() ) );
    Assert.assertNull( transaction.getRefunds() );
    Assert.assertEquals( transaction.getCurrency(), this.currency );
    Assert.assertNotNull( transaction.getCreatedAt() );
    Assert.assertNotNull( transaction.getUpdatedAt() );
    Assert.assertTrue( BooleanUtils.isFalse( transaction.getFraud() ) );
    Assert.assertNotNull( transaction.getPayment() );
    Assert.assertNotNull( transaction.getClient() );
    Assert.assertNotNull( transaction.getPreauthorization() );
    Assert.assertTrue( transaction.getFees().isEmpty() );
    Assert.assertNull( transaction.getAppId() );
  }

}
