package masterjava.web2.model;

import masterjava.common.model.ToJson;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * User: GKislin
 * Date: 10.12.2010
 */
@Table(name = "SIMPLE_TABLE")
@Entity
public class SimpleTableEntity {

    private long id;
    private String ttext;
    private int tint;
    private Timestamp tdate;

    public SimpleTableEntity() {
    }

    public SimpleTableEntity(String ttext, int tint, java.util.Date tdate) {
        this.ttext = ttext;
        this.tint = tint;
        this.tdate = new Timestamp(tdate.getTime());
    }

    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SIMPLE_TABLE")
    @SequenceGenerator(name = "SEQ_SIMPLE_TABLE", sequenceName = "SEQ_SIMPLE_TABLE")
    @Id
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "TTEXT")
    @Basic
    public String getTtext() {
        return ttext;
    }

    public void setTtext(String ttext) {
        this.ttext = ttext;
    }

    @Column(name = "TINT")
    @Basic
    public int getTint() {
        return tint;
    }

    public void setTint(int tint) {
        this.tint = tint;
    }

    @Column(name = "TDATE")
    @Basic
    public Timestamp getTdate() {
        return tdate;
    }

    public void setTdate(Timestamp tdate) {
        this.tdate = tdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleTableEntity that = (SimpleTableEntity) o;

        if (id != that.id) return false;
        if (tint != that.tint) return false;
        if (tdate != null ? !tdate.equals(that.tdate) : that.tdate != null) return false;
        if (ttext != null ? !ttext.equals(that.ttext) : that.ttext != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (ttext != null ? ttext.hashCode() : 0);
        result = 31 * result + tint;
        result = 31 * result + (tdate != null ? tdate.hashCode() : 0);
        return result;
    }
}
