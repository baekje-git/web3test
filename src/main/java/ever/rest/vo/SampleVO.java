package ever.rest.vo;

import java.io.Serializable;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="sample")
@XmlAccessorType(XmlAccessType.FIELD)
public class SampleVO implements Serializable 
{
    private static final long serialVersionUID = 1L;

    private Integer no;

    private String title;

    private String indate;
    
    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIndate() {
        return indate;
    }

    public void setIndate(String indate) {
        this.indate = indate;
    }

    @Override
    public String toString() {
        return "SampleVO [no=" + no + ", title=" + title+ ", indate=" + indate+ "]";
    }
}