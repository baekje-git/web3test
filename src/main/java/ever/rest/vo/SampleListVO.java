package ever.rest.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
 
@XmlRootElement (name="samples")
@XmlAccessorType(XmlAccessType.FIELD)
public class SampleListVO implements Serializable 
{
    private static final long serialVersionUID = 1L;
     
    @XmlElement(name="sample")
    private List<SampleVO> samples = null;
 
    public List<SampleVO> getSamples() {
        return samples;
    }
 
    public void setSamples(List<SampleVO> samples) {
        this.samples = samples;
    }
}
