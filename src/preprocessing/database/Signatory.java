package preprocessing.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author olyjosh
 */
@Entity
public class Signatory {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String  sex;
    private short age;
    private long time=0;

    public Signatory(String name, String sex, short age, long time) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.time=time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Long getId() {
        return id;
    }

    public short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Signatory)) {
            return false;
        }
        Signatory other = (Signatory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "usman.Signatory[ id=" + id + " ]";
        return name;
    }
    
}
