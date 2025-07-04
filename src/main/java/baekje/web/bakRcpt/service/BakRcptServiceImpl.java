package baekje.web.bakRcpt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import baekje.web.bakRcpt.dao.BakRcptDao;
import ever.cmmn.dao.CmmnDao;
import ever.cmmn.service.CmmnService;
import ever.support.Gv;

@Service
public class BakRcptServiceImpl implements BakRcptService {

	@Autowired
	CmmnService cmmnService;

	@Autowired
	BakRcptDao bakRcptDao;

	@Autowired
	CmmnDao cmmnDao;

	@Override
	@Transactional
	public ResponseEntity<Object> deleteBakRcptBasket(HashMap<String, Object> paramMap) throws Exception {
		// 장바구니 한건 취소 MOVE
		cmmnService.insert("bakRcpt.deleteOneBakRcptBasketMove", paramMap);

		// 장바구니 한건 삭제
		cmmnService.delete("bakRcpt.deleteBakRcptBasket", paramMap);

		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
	}

	@Override
	@Transactional
	public int saveBakRcpt(List<HashMap<String, Object>> hMaps) throws Exception {

//		List<String> itemQtyList = hMap.stream().map(m -> m.get("ITEM_QTY").toString()).toList();
//		List<String> itemCdList = hMap.stream().map(m -> m.get("ITEM_CD").toString()).toList();
//		List<String> expDtList = hMap.stream().map(m -> m.get("EXP_DT").toString()).toList();
//		List<String> prodNoList = hMap.stream().map(m -> m.get("PROD_NO").toString()).toList();
//		List<String> brchCd = hMap.stream().map(m -> m.get("BRCH_CD").toString()).distinct().toList();
//		List<String> userId = hMap.stream().map(m -> m.get("USER_ID").toString()).distinct().toList();
//		List<String> returnCat = hMap.stream().map(m -> m.get("RETURN_CAT").toString()).toList();


//		List<Object> paramMaps = new ArrayList<>();
		List<HashMap<String, Object>> paramMaps = new ArrayList<>();

		for (HashMap<String, Object> hMap : hMaps) {
			HashMap<String, Object> paramMap = new HashMap<>();

			paramMap.put("ITEM_QTY", hMap.get("ITEM_QTY"));
			paramMap.put("PROD_NO", hMap.get("PROD_NO"));
			paramMap.put("EXP_DT", hMap.get("EXP_DT"));
			paramMap.put("ORD_MEMO", hMap.get("ORD_MEMO"));
			paramMap.put("USER_ID", hMap.get("USER_ID"));
			paramMap.put("CUST_CD", hMap.get("CUST_CD"));
			paramMap.put("ITEM_CD", hMap.get("ITEM_CD"));

			// paramMap.put("DLV_BRCH_CD", hMap.get("DLV_BRCH_CD"));
			// paramMap.put("BASKET_GB_CD", hMap.get("BASKET_GB_CD"));
			// paramMap.put("ITEM_GB_CD", hMap.get("ITEM_GB_CD"));

			paramMaps.add(paramMap);

			bakRcptDao.updateBakRcptBasketItemSave(paramMap);
		}

		// 반품 장바구니 선택항목 외 DEL_YN Y로 UPDATE(선택 항목만 반품등록하기 위해TO-BE 추가)
		List<String> itemCds = paramMaps.stream().map(m -> m.get("ITEM_CD").toString()).collect(Collectors.toList());

		HashMap<String, Object> updateParam = new HashMap<>();

		updateParam.put("CUST_CD", hMaps.get(0).get("CUST_CD"));
		updateParam.put("itemCds", itemCds);

		bakRcptDao.updateBasketDelYn(updateParam);

		// 프로시져 호출
		HashMap<String, Object> procMap = new HashMap<>();

		procMap.put("returnCat", hMaps.get(0).get("RETURN_CAT"));
		procMap.put("CUST_CD", hMaps.get(0).get("CUST_CD"));
		procMap.put("USER_ID", hMaps.get(0).get("USER_ID"));

		cmmnDao.callProcedure("bakRcpt.procSaleInsert", procMap);

		return 1;
	}
	@Override
	@Transactional
	public int saveBakRcptImsi(List<HashMap<String, Object>> hMaps) throws Exception {

//		List<String> itemQtyList = hMap.stream().map(m -> m.get("ITEM_QTY").toString()).toList();
//		List<String> itemCdList = hMap.stream().map(m -> m.get("ITEM_CD").toString()).toList();
//		List<String> expDtList = hMap.stream().map(m -> m.get("EXP_DT").toString()).toList();
//		List<String> prodNoList = hMap.stream().map(m -> m.get("PROD_NO").toString()).toList();
//		List<String> brchCd = hMap.stream().map(m -> m.get("BRCH_CD").toString()).distinct().toList();
//		List<String> userId = hMap.stream().map(m -> m.get("USER_ID").toString()).distinct().toList();
//		List<String> returnCat = hMap.stream().map(m -> m.get("RETURN_CAT").toString()).toList();


//		List<Object> paramMaps = new ArrayList<>();
		List<HashMap<String, Object>> paramMaps = new ArrayList<>();

		for (HashMap<String, Object> hMap : hMaps) {
			HashMap<String, Object> paramMap = new HashMap<>();

			paramMap.put("ITEM_QTY", hMap.get("ITEM_QTY"));
			paramMap.put("PROD_NO", hMap.get("PROD_NO"));
			paramMap.put("EXP_DT", hMap.get("EXP_DT"));
			paramMap.put("ORD_MEMO", hMap.get("ORD_MEMO"));
			paramMap.put("USER_ID", hMap.get("USER_ID"));
			paramMap.put("CUST_CD", hMap.get("CUST_CD"));
			paramMap.put("ITEM_CD", hMap.get("ITEM_CD"));

			// paramMap.put("DLV_BRCH_CD", hMap.get("DLV_BRCH_CD"));
			// paramMap.put("BASKET_GB_CD", hMap.get("BASKET_GB_CD"));
			// paramMap.put("ITEM_GB_CD", hMap.get("ITEM_GB_CD"));

			paramMaps.add(paramMap);

			bakRcptDao.updateBakRcptBasketItemImsiSave(paramMap);
		}

		return 1;
	}

}
