package server.model;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Event.class)
public abstract class Event_ {

	public static volatile SingularAttribute<Event, LocalDate> date;
	public static volatile CollectionAttribute<Event, Reminder> reminders;
	public static volatile SingularAttribute<Event, Integer> id;
	public static volatile SingularAttribute<Event, String> title;
	public static volatile SingularAttribute<Event, Account> account;
	public static volatile SingularAttribute<Event, LocalTime> timestamp;

	public static final String DATE = "date";
	public static final String REMINDERS = "reminders";
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String ACCOUNT = "account";
	public static final String TIMESTAMP = "timestamp";

}

