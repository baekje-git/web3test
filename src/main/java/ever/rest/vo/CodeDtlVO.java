package ever.rest.vo;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="sample")
@XmlAccessorType(XmlAccessType.FIELD)
public class CodeDtlVO implements Serializable 
{
    private static final long serialVersionUID = 1L;

    private String mstCode;

    private String dtlCode;
    
    private String codeName;

	public String getMstCode() {
		return mstCode;
	}

	public void setMstCode(String mstCode) {
		this.mstCode = mstCode;
	}

	public String getDtlCode() {
		return dtlCode;
	}

	public void setDtlCode(String dtlCode) {
		this.dtlCode = dtlCode;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

    
    
    
}