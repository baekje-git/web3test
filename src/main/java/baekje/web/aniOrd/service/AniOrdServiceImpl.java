package baekje.web.aniOrd.service;

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

import baekje.web.aniOrd.dao.AniOrdDao;
import ever.cmmn.dao.CmmnDao;
import ever.cmmn.service.CmmnService;
import ever.support.Gv;

@Service
public class AniOrdServiceImpl implements AniOrdService {

	@Autowired
	CmmnService cmmnService;

	@Autowired
	CmmnDao cmmnDao;

	@Autowired
	AniOrdDao aniOrdDao;

	@Override
    @Transactional
    public int insertSearchIpHistory_ani(HashMap<String, Object> paramMap) throws Exception {
        int retVal = 0;
        retVal = aniOrdDao.insertSearchIpHistory_ani(paramMap);
        return retVal;
    }

	@Override
	@Transactional
	public int deleteComOrdBasket_ani(HashMap<String, Object> paramMap) throws Exception {
		// 장바구니 한건 취소 MOVE
		cmmnService.insert("ani.deleteOneComOrdBasketMove_ani", paramMap);

        // 장바구니 한건 삭제
		cmmnService.delete("ani.deleteComOrdBasket_ani", paramMap);

		return 1;
	}

	@Override
	@Transactional
	public int saveComOrdWebgateList_ani(List<HashMap<String, Object>> hMap) throws Exception {

		List<String> snList = new ArrayList<String>();
		
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

		// 대표 ITEM_CD
        String itemCdMaster = "";

        // sn 채번
        String newSn="";
        String newSnAll="";
        String befBrchcd ="";
        String MstIn="Y";        

        // 장바구니 DETAIL  insert
        for (int i = 0; i < itemCdList.size() ; i++) {

            String itemCd = String.valueOf(itemCdList.get(i));
            String itemGbCd = String.valueOf(itemGbCdList.get(i));
            String itemQty = String.valueOf(itemQtyList.get(i));
            String itemDlvBrchCd = String.valueOf(itemDlvBrchCdList.get(i));

            if(i == 0) {
                newSn = cmmnService.getNextSn("WEB", itemDlvBrchCd , "", "");
                MstIn="N";
                befBrchcd= itemDlvBrchCd;
                itemCdMaster = itemCd;
                snList.add(newSn);
                newSnAll=newSnAll+newSn+",";
            } else {
            	if (!befBrchcd.equals(itemDlvBrchCd)) {
                    befBrchcd= itemDlvBrchCd;

                    itemCdMaster = itemCd;
                    // 대표 ITEM_CD 삽입.
                    paramMap.put("ITEM_CD", itemCdMaster);

                    // 장바구니 MASTER insert
                    // comOrdDao.saveComOrdWebgate(hMap);
                    cmmnService.insert("ani.insertWebgate_ani", paramMap);

                    newSn= cmmnService.getNextSn("WEB", itemDlvBrchCd , "", "");

                    snList.add(newSn);

                    newSnAll=newSnAll+newSn+",";
                    MstIn="N";
                }

                befBrchcd= itemDlvBrchCd;
            }


            //채번입력
            paramMap.put("SN", newSn);

            // 장바구니 주문등록 WEBGATE insert
            paramMap.put("SN", newSn);
            paramMap.put("ITEM_CD", itemCd);
            paramMap.put("ITEM_GB_CD", itemGbCd);
            paramMap.put("ITEM_QTY", itemQty);
            paramMap.put("DLV_BRCH_CD", itemDlvBrchCd);


            // 장바구니 주문등록 WEBGATE_DTL
            cmmnService.insert("ani.insertWebgateDtl_ani", paramMap);


            // 장바구니 MOVE
            cmmnService.insert("ani.moveComOrdBasket_ani", paramMap);

            // 장바구니 삭제
            paramMap.put("SAVE_ITEM_CD", itemCd);
            paramMap.put("SAVE_ITEM_GB_CD", itemGbCd);

            HashMap<String, Object> delMap = new HashMap<>();

            delMap.put("custCd", custCd.get(0));
            delMap.put("basketGbCd", "01");
            delMap.put("saveItemGbCd", itemGbCd);
            delMap.put("saveItemCd", itemCd);
            delMap.put("dlvBrchCd", itemDlvBrchCd);

            cmmnService.delete("ani.deleteComOrdBasket_ani", delMap);
        }

        if(MstIn != "Y") {
        	// 대표 ITEM_CD 삽입.
            paramMap.put("ITEM_CD", itemCdMaster);

            // 장바구니 MASTER insert
//            comOrdDao.saveComOrdWebgate(paramMap);
            cmmnService.insert("ani.insertWebgate_ani", paramMap);

        }

        // 프로시져 호출
        for (int j = 0; j < snList.size() ; j++) {
         	paramMap.put("SN", snList.get(j));
         	paramMap.put("USER_ID", userId.get(0));

//         	cmmnDao.callProc(paramMap, "comOrd.procItemStockUpdate", Gv.SAL1);
         	cmmnDao.callProcedure("ani.procItemStockUpdate_ani", paramMap);

         	String oVal = String.valueOf(paramMap.get("O_VAL"));
    		String oMsg = String.valueOf(paramMap.get("O_MSG"));

         	if (oVal.equals("ERROR")) {
         		throw new Exception(String.valueOf(oMsg) + " => O_VAL : [" + oVal + "]");
         	}
         }

		return 1;
	}

}
