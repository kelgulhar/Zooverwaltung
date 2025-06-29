package at.fhburgenland.sample;

import jakarta.persistence.*;

@Entity(name="Person")
@Table(name="person")
public class Person {
    // TODO Fields of Person
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pnr", updatable = false, nullable = false)
    private int pnr;

    @Column(name="vorname", nullable = false, length = 32)
    private String vorname;

    @Column(name="nachname", nullable = false, length = 32)
    private String nachname;

    @Column(name="gehalt", nullable = false)
    private int gehalt;

    public Person() {
        // TODO Initialization of fields of Person
    }

    public Person(String vorname, String nachname, int gehalt){
        this.vorname = vorname;
        this.nachname = nachname;
        this.gehalt = gehalt;
    }

    // TODO Implement body of Person

    public int getPnr() {
        return pnr;
    }

    public void setPnr(int pnr) {
        this.pnr = pnr;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public int getGehalt() {
        return gehalt;
    }

    public void setGehalt(int gehalt) {
        this.gehalt = gehalt;
    }

    @Override
    public String toString() {
        return "Person{" +
                "pnr=" + pnr +
                ", vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", gehalt=" + gehalt +
                '}';
    }
}
