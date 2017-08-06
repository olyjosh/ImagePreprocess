package preprocessing.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author olyjosh
 * 
 * I only added this class to show how to store blob. However, you can store many dataTypes. Check Objectdb site for info on this. 
 */


@Entity
public class BlobClass {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private byte[] imagePhoto;  // I added this field to show how to add blob. 
    

    public BlobClass(String name, byte[] imagePhoto) {
        this.name = name;
        this.imagePhoto=imagePhoto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    
    //These below method are synonymous to concept of blob storing...
    public void setImagePhoto(byte[] imagePhoto) {
        this.imagePhoto = imagePhoto;
    }

    public byte[] getImagePhoto() {
        return imagePhoto;
    }

    
    
    public Long getId() {
        return id;
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
        if (!(object instanceof BlobClass)) {
            return false;
        }
        BlobClass other = (BlobClass) object;
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
