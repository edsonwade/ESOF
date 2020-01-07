package ufp.esof.project.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

 @Data
 @Entity

 @ToString
 public class Appointment {


     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;


     @ManyToOne
     @JsonIgnore
     @ToString.Exclude
     private Student student;

     @ManyToOne
     @JsonIgnore
     @ToString.Exclude
     private Explainer explainer;

     @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     @JsonSerialize(using = LocalDateTimeSerializer.class)
     private LocalDateTime startTime;

     @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     @JsonSerialize(using = LocalDateTimeSerializer.class)
     private LocalDateTime expectedEndTime;

     public Appointment(LocalDateTime startTime, LocalDateTime end) {
         this.startTime = startTime;
         this.expectedEndTime = end;
     }

     public Appointment(Explainer explainer) {

         this.setExplainer(explainer);
     }

     public Appointment() {

     }

     public boolean overlaps(Appointment other) {
         return this.isBetween(other) || other.isBetween(this) || (this.startTime.equals(other.startTime) && this.expectedEndTime.equals(other.expectedEndTime));
     }

     private boolean isBetween(Appointment other) {
         LocalDateTime appointmentStartTime = other.getStartTime();
         LocalDateTime appointmentEndTime = other.getExpectedEndTime();
         return this.isBetween(appointmentStartTime) || this.isBetween(appointmentEndTime);
     }

     private boolean isBetween(LocalDateTime timeToCheck) {
         return this.startTime.isBefore(timeToCheck) && this.expectedEndTime.isAfter(timeToCheck);
     }


 }
