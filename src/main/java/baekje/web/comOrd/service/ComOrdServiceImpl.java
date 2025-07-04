package baekje.web.comOrd.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import baekje.web.comOrd.dao.ComOrdDao;
import baekje.web.exception.controller.OrderException;
import ever.cmmn.dao.CmmnDao;
import ever.cmmn.service.CmmnService;
import ever.support.Gv;
import ever.support.exception.AjaxMsgCustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ComOrdServiceImpl implements ComOrdService {
	private static final Logger logger = LoggerFactory.getLogger(ComOrdServiceImpl.class);
	@Autowired
	CmmnService cmmnService;

	@Autowired
	CmmnDao cmmnDao;

	@Autowired
	ComOrdDao comOrdDao;

	@Override
    @Transactional
    public int insertSearchIpHistory(HashMap<String, Object> paramMap) throws Exception {
        int retVal = 0;
        retVal = comOrdDao.insertSearchIpHistory(paramMap);
        return retVal;
    }

	@Override
	@Transactional
	public int deleteComOrdBasket(HashMap<String, Object> paramMap) throws Exception {
		// 장바구니 한건 취소 MOVE
		cmmnService.insert("comOrd.deleteOneComOrdBasketMove", paramMap);

        // 장바구니 한건 삭제
		cmmnService.delete("comOrd.deleteComOrdBasket", paramMap);

		return 1;
	}

	@Override
	@Transactional
	public int saveComOrdWebgateList(List<HashMap<String, Object>> hMap) throws Exception {

	    List<String> snList = new ArrayList<>();

	    List<String> itemQtyList = hMap.stream().map(m -> m.get("ITEM_QTY").toString()).collect(Collectors.toList());
	    List<String> itemCdList = hMap.stream().map(m -> m.get("ITEM_CD").toString()).collect(Collectors.toList());
	    List<String> itemGbCdList = hMap.stream().map(m -> m.get("ITEM_GB_CD").toString()).collect(Collectors.toList());
	    List<String> itemDlvBrchCdList = hMap.stream().map(m -> m.get("DLV_BRCH_CD").toString()).collect(Collectors.toList());
	    List<String> custCd = hMap.stream().map(m -> m.get("CUST_CD").toString()).distinct().collect(Collectors.toList());
	    List<String> userId = hMap.stream().map(m -> m.get("USER_ID").toString()).distinct().collect(Collectors.toList());
	    List<String> brchCd = hMap.stream().map(m -> m.get("BRCH_CD").toString()).distinct().collect(Collectors.toList());
	    List<String> ordMemo = hMap.stream().map(m -> m.get("ORD_MEMO").toString()).distinct().collect(Collectors.toList());
	    List<String> deptCd = hMap.stream().map(m -> m.get("DEPT_CD").toString()).distinct().collect(Collectors.toList());
	    List<String> empId = hMap.stream().map(m -> m.get("EMP_ID").toString()).distinct().collect(Collectors.toList());

	    HashMap<String, Object> paramMap = new HashMap<>();

	    paramMap.put("O_VAL", "");
	    paramMap.put("O_MSG", "");
	    paramMap.put("ITEM_CNT", itemQtyList.size());
	    paramMap.put("CUST_CD", custCd.get(0));
	    paramMap.put("BASKET_GB_CD", "01");
	    paramMap.put("USER_ID", userId.get(0));
	    paramMap.put("BRCH_CD", brchCd.get(0));
	    paramMap.put("ORD_MEMO", ordMemo.get(0));
	    paramMap.put("DEPT_CD", deptCd.get(0));
	    paramMap.put("EMP_ID", empId.get(0));

	    String itemCdMaster = "";
	    String newSn = "";
	    String newSnAll = "";
	    String befBrchcd = "";
	    String MstIn = "Y";

	    for (int i = 0; i < itemCdList.size(); i++) {
	        String itemCd = itemCdList.get(i);
	        String itemGbCd = itemGbCdList.get(i);
	        String itemQty = itemQtyList.get(i);
	        String itemDlvBrchCd = itemDlvBrchCdList.get(i);

	        if (i == 0) {
	            newSn = cmmnService.getNextSn("WEB", itemDlvBrchCd, "", "");
	            MstIn = "N";
	            befBrchcd = itemDlvBrchCd;
	            itemCdMaster = itemCd;
	            snList.add(newSn);
	            newSnAll += newSn + ",";
	        } else {
	            if (!befBrchcd.equals(itemDlvBrchCd)) {
	                befBrchcd = itemDlvBrchCd;
	                itemCdMaster = itemCd;
	                paramMap.put("ITEM_CD", itemCdMaster);
	                cmmnService.insert("comOrd.insertWebgate", paramMap);
	                newSn = cmmnService.getNextSn("WEB", itemDlvBrchCd, "", "");
	                snList.add(newSn);
	                newSnAll += newSn + ",";
	                MstIn = "N";
	            }
	            befBrchcd = itemDlvBrchCd;
	        }

	        paramMap.put("SN", newSn);
	        paramMap.put("ITEM_CD", itemCd);
	        paramMap.put("ITEM_GB_CD", itemGbCd);
	        paramMap.put("ITEM_QTY", itemQty);
	        paramMap.put("DLV_BRCH_CD", itemDlvBrchCd);

	        cmmnService.insert("comOrd.insertWebgateDtl", paramMap);
	        cmmnService.insert("comOrd.moveComOrdBasket", paramMap);

	        paramMap.put("SAVE_ITEM_CD", itemCd);
	        paramMap.put("SAVE_ITEM_GB_CD", itemGbCd);

	        HashMap<String, Object> delMap = new HashMap<>();
	        delMap.put("custCd", custCd.get(0));
	        delMap.put("basketGbCd", "01");
	        delMap.put("saveItemGbCd", itemGbCd);
	        delMap.put("saveItemCd", itemCd);
	        delMap.put("dlvBrchCd", itemDlvBrchCd);

	        cmmnService.delete("comOrd.deleteComOrdBasket", delMap);
	    }

	    if (!"Y".equals(MstIn)) {
	        paramMap.put("ITEM_CD", itemCdMaster);
	        cmmnService.insert("comOrd.insertWebgate", paramMap);
	    }

	    for (int j = 0; j < snList.size(); j++) {
	        paramMap.put("SN", snList.get(j));
	        paramMap.put("USER_ID", userId.get(0));

	        try {
	            cmmnDao.callProcedure("comOrd.procItemStockUpdate", paramMap);

	            String oVal = String.valueOf(paramMap.get("O_VAL"));
	            String oMsg = String.valueOf(paramMap.get("O_MSG"));

	            if ("ERROR".equals(oVal)) {

	                throw new OrderException(oMsg.replaceAll("<BR/>", "\n"));
	            }
	        }catch (Exception e) {
	            e.printStackTrace();
	            throw new OrderException(e.getMessage().replaceAll("<BR/>", "\n"));
	        }
	    }

	    return 1;
	}
    @Override
    public void saveBasketItem(HashMap<String, Object> hMap) throws Exception {
    	logger.info(">>> saveBasketItem 파라미터: {}", hMap);
        // 동일 품목 먼저 삭제
        cmmnService.update("comOrd.deleteComOrdBasket", hMap);

        // 새로 insert
        cmmnService.update("comOrd.insertBasketItem", hMap);
    }
}
