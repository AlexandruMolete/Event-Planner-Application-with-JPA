package server.model;

import java.time.LocalTime;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Reminder.class)
public abstract class Reminder_ {

	public static volatile SingularAttribute<Reminder, Integer> id;
	public static volatile SingularAttribute<Reminder, LocalTime> time;
	public static volatile SingularAttribute<Reminder, Event> event;

	public static final String ID = "id";
	public static final String TIME = "time";
	public static final String EVENT = "event";

}

