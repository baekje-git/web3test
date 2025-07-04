package ever.rest.vo;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="sample")
@XmlAccessorType(XmlAccessType.FIELD)
public class CodeMstVO implements Serializable 
{
    private static final long serialVersionUID = 1L;

    private String mstCode;

    private String codeName;

    private String useYn;
    
    @XmlElement(name="dtlcodes")
    private List<CodeDtlVO> dtlcodes = null;

	public List<CodeDtlVO> getDtlcodes() {
		return dtlcodes;
	}

	public void setDtlcodes(List<CodeDtlVO> dtlcodes) {
		this.dtlcodes = dtlcodes;
	}

	public String getMstCode() {
		return mstCode;
	}

	public void setMstCode(String mstCode) {
		this.mstCode = mstCode;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	@Override
	public String toString() {
		return "CodeMstVO [mstCode=" + mstCode + ", codeName=" + codeName + ", useYn=" + useYn + "]";
	}
    
    
}