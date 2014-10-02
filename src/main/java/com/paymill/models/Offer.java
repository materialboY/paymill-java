package com.paymill.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An offer is a recurring plan which a user can subscribe to. You can create different offers with different plan attributes e.g.
 * a monthly or a yearly based paid offer/plan.
 * @author Vassil Nikolov
 * @since 3.0.0
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public final class Offer {

  public Offer() {
    super();
  }

  public Offer( String id ) {
    this.id = id;
  }

  private String                  id;

  @Updateable( "name" )
  private String                  name;

  @Updateable( "amount" )
  private Integer                 amount;

  @Updateable( "interval" )
  private Interval.Period         interval;

  @JsonProperty( "trial_period_days" )
  private Integer                 trialPeriodDays;

  @Updateable( "currency" )
  private String                  currency;

  @JsonProperty( "created_at" )
  private Date                    createdAt;

  @JsonProperty( "updated_at" )
  private Date                    updatedAt;

  @JsonProperty( "app_id" )
  private String                  appId;

  @JsonProperty( "subscription_count" )
  private Offer.SubscriptionCount subscriptionCount;

  public String getId() {
    return id;
  }

  public void setId( String id ) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName( String name ) {
    this.name = name;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount( Integer amount ) {
    this.amount = amount;
  }

  public Integer getTrialPeriodDays() {
    return trialPeriodDays;
  }

  public void setTrialPeriodDays( Integer trialPeriodDays ) {
    this.trialPeriodDays = trialPeriodDays;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency( String currency ) {
    this.currency = currency;
  }

  /**
   * Returns App (ID) that created this offer or <code>null</code> if created by yourself.
   * @return {@link String} or <code>null</code>.
   */
  public String getAppId() {
    return this.appId;
  }

  /**
   * Sets App (ID) that created this offer or <code>null</code> if created by yourself.
   * @param appId
   *          {@link String}
   */
  public void setAppId( String appId ) {
    this.appId = appId;
  }

  public Interval.Period getInterval() {
    return interval;
  }

  public void setInterval(Interval.Period interval) {
    this.interval = interval;
  }

  public Offer.SubscriptionCount getSubscriptionCount() {
    return this.subscriptionCount;
  }

  public void setSubscriptionCount( final Offer.SubscriptionCount subscriptionCount ) {
    this.subscriptionCount = subscriptionCount;
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
    this.updatedAt = new Date( seconds * 1000 );
  }

  public static Offer.Filter createFilter() {
    return new Offer.Filter();
  }

  public static Offer.Order createOrder() {
    return new Offer.Order();
  }

  @JsonIgnoreProperties( ignoreUnknown = true )
  public class SubscriptionCount {
    private String  active;
    private Integer inactive;

    public String getActive() {
      return this.active;
    }

    public void setActive( final String active ) {
      this.active = active;
    }

    public Integer getInactive() {
      return this.inactive;
    }

    public void setInactive( final Integer inactive ) {
      this.inactive = inactive;
    }

  }

  public final static class Filter {

    @SnakeCase( "name" )
    private String name;

    @SnakeCase( "trial_period_days" )
    private String trialPeriodDays;

    @SnakeCase( "amount" )
    private String amount;

    @SnakeCase( "created_at" )
    private String createdAt;

    @SnakeCase( "updated_at" )
    private String updatedAt;

    private Filter() {
      super();
    }

    public Offer.Filter byName( String name ) {
      this.name = name;
      return this;
    }

    public Offer.Filter byTrialPeriodDays( Integer trialPeriodDays ) {
      this.trialPeriodDays = String.valueOf( trialPeriodDays );
      return this;
    }

    public Offer.Filter byAmount( final int amount ) {
      this.amount = String.valueOf( amount );
      return this;
    }

    public Offer.Filter byAmountGreaterThan( final int amount ) {
      this.amount = ">" + String.valueOf( amount );
      return this;
    }

    public Offer.Filter byAmountLessThan( final int amount ) {
      this.amount = "<" + String.valueOf( amount );
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
     * @return {@link Offer.Filter} object with populated filter for createdAt.
     */
    public Offer.Filter byCreatedAt( final Date date, final Date endDate ) {
      this.createdAt = DateRangeBuilder.execute( date, endDate );
      return this;
    }

    /**
     * Creates filter for updatedAt date. If endDate is given the filter is set for range from date to endDate. If endDate is
     * <code>null</code> the filter search for exact match.
     * @param date
     *          Start or exact date
     * @param endDate
     *          End date for the period or <code>null</code>.
     * @throws IllegalArgumentException
     *           When date is <code>null</code>.
     * @return {@link Offer.Filter} object with populated filter for updatedAt.
     */
    public Offer.Filter byUpdatedAt( Date date, Date endDate ) {
      this.updatedAt = DateRangeBuilder.execute( date, endDate );
      return this;
    }
  }

  public final static class Order {

    @SnakeCase( "interval" )
    private boolean interval;

    @SnakeCase( "amount" )
    private boolean amount;

    @SnakeCase( "created_at" )
    private boolean createdAt;

    @SnakeCase( "trial_period_days" )
    private boolean trialPeriodDays;

    @SnakeCase( value = "asc", order = true )
    private boolean asc;

    @SnakeCase( value = "desc", order = true )
    private boolean desc;

    private Order() {
      super();
    }

    public Offer.Order asc() {
      this.asc = true;
      this.desc = false;
      return this;
    }

    public Offer.Order desc() {
      this.asc = false;
      this.desc = true;
      return this;
    }

    public Offer.Order byInterval() {
      this.interval = true;
      this.amount = false;
      this.createdAt = false;
      this.trialPeriodDays = false;
      return this;
    }

    public Offer.Order byAmount() {
      this.interval = false;
      this.amount = true;
      this.createdAt = false;
      this.trialPeriodDays = false;
      return this;
    }

    public Offer.Order byCreatedAt() {
      this.interval = false;
      this.amount = false;
      this.createdAt = true;
      this.trialPeriodDays = false;
      return this;
    }

    public Offer.Order byTrialPeriodDays() {
      this.interval = false;
      this.amount = false;
      this.createdAt = true;
      this.trialPeriodDays = false;
      return this;
    }
  }

}
