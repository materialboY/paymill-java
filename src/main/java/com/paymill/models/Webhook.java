package com.paymill.models;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * With webhooks we give you the possibility to react automatically to certain events which happen within our system. A webhook is
 * basically a URL where we send an HTTP POST request to, every time one of the events attached to that webhook is triggered.
 * Alternatively you can define an email address where we send the event’s information to You can manage your webhooks via the API
 * as explained below or you can use the web interface inside our cockpit.
 * @author Vassil Nikolov
 * @since 3.0.0
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public final class Webhook {

  private String              id;

  @Updateable( "url" )
  private String              url;

  @Updateable( "email" )
  private String              email;

  private Boolean             livemode;

  @JsonProperty( "created_at" )
  private Date                createdAt;

  @JsonProperty( "updated_at" )
  private Date                updatedAt;

  @JsonProperty( "event_types" )
  private Webhook.EventType[] eventTypes;

  @JsonProperty( "app_id" )
  private String              appId;

  public Webhook() {
    super();
  }

  public Webhook( String id ) {
    this.id = id;
  }

  /**
   * Returns unique identifier of this webhook.
   * @return {@link String}
   */
  public String getId() {
    return id;
  }

  /**
   * Sets unique identifier of this webhook.
   * @param id
   *          {@link String}
   */
  public void setId( String id ) {
    this.id = id;
  }

  /**
   * Returns the url of the webhook.
   * @return {@link String} or <code>null</code>.
   */
  public String getUrl() {
    return url;
  }

  /**
   * Sets the url of the webhook.
   * @param url
   *          {@link String}
   */
  public void setUrl( String url ) {
    this.url = url;
  }

  /**
   * Returns the email of the webhook.
   * @return {@link String} or <code>null</code>.
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets the email of the webhook.
   * @param email
   *          {@link String}
   */
  public void setEmail( String email ) {
    this.email = email;
  }

  /**
   * Returns if the webhook is for live or test mode.
   * @return {@link Boolean}
   */
  public Boolean getLivemode() {
    return this.livemode;
  }

  /**
   * You can create webhooks for livemode and testmode.
   * @param livemode
   *          {@link Boolean} <code>false</code> is default.
   */
  public void setLivemode( final Boolean livemode ) {
    this.livemode = livemode;
  }

  /**
   * Returns {@link List} of {@link Webhook.EventType}s.
   * @return {@link List}.
   */
  public Webhook.EventType[] getEventTypes() {
    return eventTypes;
  }

  /**
   * Sets {@link List} of {@link Webhook.EventType}s.
   * @param eventTypes
   *          {@link List} of {@link Webhook.EventType}s.
   */
  public void setEventTypes( Webhook.EventType[] eventTypes ) {
    this.eventTypes = eventTypes;
  }

  /**
   * Returns App (ID) that created this transaction or <code>null</code> if created by yourself.
   * @return {@link String} or <code>null</code>.
   */
  public String getAppId() {
    return this.appId;
  }

  /**
   * Sets App (ID) that created this transaction or <code>null</code> if created by yourself.
   * @param appId
   *          {@link String}
   */
  public void setAppId( final String appId ) {
    this.appId = appId;
  }

  /**
   * Returns the creation date.
   * @return {@link Date}
   */
  public Date getCreatedAt() {
    return this.createdAt;
  }

  /**
   * Set the creation date.
   * @param createdAt
   *          {@link Date}
   */
  @JsonIgnore
  public void setCreatedAt( final Date createdAt ) {
    this.createdAt = createdAt;
  }

  /**
   * Set the creation date.
   * @param seconds
   *          Creation date representation is seconds.
   */
  public void setCreatedAt( final long seconds ) {
    if( seconds > 0 )
      this.createdAt = new Date( seconds * 1000 );
  }

  /**
   * Returns the last update.
   * @return {@link Date}
   */
  public Date getUpdatedAt() {
    return this.updatedAt;
  }

  /**
   * Sets the last update.
   * @param updatedAt
   *          {@link Date}
   */
  @JsonIgnore
  public void setUpdatedAt( final Date updatedAt ) {
    this.updatedAt = updatedAt;
  }

  /**
   * Sets the last update.
   * @param seconds
   *          Last update representation is seconds.
   */
  public void setUpdatedAt( final long seconds ) {
    if( seconds > 0 )
      this.updatedAt = new Date( seconds * 1000 );
  }

  public static Webhook.Filter createFilter() {
    return new Webhook.Filter();
  }

  public static Webhook.Order createOrder() {
    return new Webhook.Order();
  }

  public final static class Filter {

    @SnakeCase( "url" )
    private String url;

    @SnakeCase( "email" )
    private String email;

    @SnakeCase( "created_at" )
    private String createdAt;

    private Filter() {
      super();
    }

    public Webhook.Filter byUrl( String url ) {
      this.url = url;
      return this;
    }

    public Webhook.Filter byEmail( String email ) {
      this.email = email;
      return this;
    }

    /**
     * Creates filter for createdAt date. If endDate is given the filter is set for range from date to endDate. If endDate is
     * <code>null</code> the filter search for exact match.
     * @param date
     *          Start or exact date
     * @param endDate
     *          End date for the period or <code>null</code>.
     * @throws IllegalArgumentException
     *           When date is <code>null</code>.
     * @return {@link Webhook.Filter} object with populated filter for createdAt.
     */
    public Webhook.Filter byCreatedAt( Date date, Date endDate ) {
      this.createdAt = DateRangeBuilder.execute( date, endDate );
      return this;
    }
  }

  public final static class Order {

    @SnakeCase( "url" )
    private boolean url;

    @SnakeCase( "email" )
    private boolean email;

    @SnakeCase( "created_at" )
    private boolean createdAt;

    @SnakeCase( value = "asc", order = true )
    private boolean asc;

    @SnakeCase( value = "desc", order = true )
    private boolean desc;

    private Order() {
      super();
    }

    public Webhook.Order asc() {
      this.asc = true;
      this.desc = false;
      return this;
    }

    public Webhook.Order desc() {
      this.asc = false;
      this.desc = true;
      return this;
    }

    public Webhook.Order byCreatedAt() {
      this.email = false;
      this.createdAt = true;
      this.url = false;
      return this;
    }

    public Webhook.Order byUrl() {
      this.email = false;
      this.createdAt = false;
      this.url = true;
      return this;
    }

    public Webhook.Order byEmail() {
      this.email = true;
      this.createdAt = false;
      this.url = false;
      return this;
    }

  }

  /**
   * There are a number of events you can react to. Each webhook can be configured to catch any kind of event individually, so you
   * can create different webhooks for different events. Each Webhook needs to be attached to at least one event.
   * @author Vassil Nikolov
   * @since 3.0.0
   */
  public enum EventType {

    /**
     * Returns a {@link Transaction} with state set to chargeback.
     */
    CHARGEBACK_EXECUTED("chargeback.executed"),

    /**
     * Returns a {@link Transaction}.
     */
    TRANSACTION_CREATED("transaction.created"),

    /**
     * Returns a {@link Transaction}.
     */
    TRANSACTION_SUCCEEDED("transaction.succeeded"),

    /**
     * returns a {@link Transaction}.
     */
    TRANSACTION_FAILED("transaction.failed"),

    /**
     * Returns a {@link Client} if a {@link Client} was updated.
     */
    CLIENT_UPDATED("client.updated"),

    /**
     * Returns a {@link Subscription}.
     */
    SUBSCRIPTION_CREATED("subscription.created"),

    /**
     * Returns a {@link Subscription}.
     */
    SUBSCRIPTION_UPDATED("subscription.updated"),

    /**
     * Returns a {@link Subscription}.
     */
    SUBSCRIPTION_DELETED("subscription.deleted"),

    /**
     * Returns a {@link Transaction} and a {@link Subscription}.
     */
    SUBSCRIPTION_SUCCEEDED("subscription.succeeded"),

    /**
     * Returns a {@link Transaction} and a {@link Subscription}.
     */
    SUBSCRIPTION_FAILED("subscription.failed"),

    /**
     * Returns a {@link Subscription}.
     */
    SUBSCRIPTION_EXPIRING("subscription.expiring"),

    /**
     * Returns a {@link Subscription}.
     */
    SUBSCRIPTION_DEACTIVATED("subscription.deactivated"),

    /**
     * Returns a {@link Subscription}.
     */
    SUBSCRIPTION_ACTIVATED("subscription.activated"),

    /**
     * Returns a {@link Subscription}.
     */
    SUBSCRIPTION_CANCELED("subscription.canceled"),

    /**
     * Returns a refund.
     */
    REFUND_CREATED("refund.created"),

    /**
     * Returns a {@link Refund}.
     */
    REFUND_SUCCEEDED("refund.succeeded"),

    /**
     * Returns a {@link Refund}.
     */
    REFUND_FAILED("refund.failed"),

    /**
     * Returns an invoice with the payout sum for the invoice period.
     */
    PAYOUT_TRANSFERRED("payout.transferred"),

    /**
     * Returns an invoice with the fees sum for the invoice period.
     */
    INVOICE_AVAILABLE("invoice.available"),

    /**
     * Returns a merchant if a connected merchant was activated.
     */
    APP_MERCHANT_ACTIVATED("app.merchant.activated"),

    /**
     * Returns a merchant if a connected merchant was deactivated.
     */
    APP_MERCHANT_DEACTIVATED("app.merchant.deactivated"),

    /**
     * Returns a merchant if a connected merchant was rejected.
     */
    APP_MERCHANT_REJECTED("app.merchant.rejected"),

    /**
     * Returns a {@link Merchant} if a connected merchant allows live requests
     */
    APP_MERCHANT_LIVE_REQUESTS_ALLOWED("app.merchant.live_requests_allowed"),

    /**
     * Returns a {@link Merchant} if a connected merchant denys live requests
     */
    APP_MERCHANT_LIVE_REQUESTS_NOT_ALLOWED("app.merchant.live_requests_not_allowed"),
    
    /**
     * Returns a {@link Merchant} if a connected merchant disabled your app
     */
    APP_MERCHANT_APP_DISABLED("app.merchant.app.disabled"),

    /**
     * Returns a {@link Payment} if if a creditcard is going to expire next month
     */
    PAYMENT_EXPIRED("payment.expired"),

    UNDEFINED("undefined");

    private String value;

    private EventType( String value ) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return this.value;
    }

    @JsonCreator
    public static EventType create( String value ) {
      for( EventType type : EventType.values() ) {
        if( type.getValue().equals( value ) ) {
          return type;
        }
      }
      return EventType.UNDEFINED;
    }

  }
}
