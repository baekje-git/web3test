/*
  UBIFORM
*/
// 샘플 공통호출 테스트
export function ubiReport2(data, frmName, paramList, _iframeName) {
  // eslint-disable-next-line no-underscore-dangle
  const _params = {
    projectName: "CHEM", // 프로젝트명
    formName: frmName, // 서식명
  };

  // eslint-disable-next-line no-restricted-syntax
  for (const i in data) {
    // eslint-disable-next-line guard-for-in
    _params[i] = encodeURIComponent(JSON.stringify([data[i]]));
    // _params[i] = encodeURIComponent(`[${JSON.stringify(data[i])}]`)
  }

  // eslint-disable-next-line no-restricted-syntax
  for (const j in paramList) {
    _params[j] = paramList[j];
  }

  console.log("ubiReport===>", _params);
  // eslint-disable-next-line no-underscore-dangle
  // const _url = "http://211.237.16.73:3366/UBIFORM/UView5/index.jsp";
  const _url = "http://localhost:9090/UBIFORM/UView5/index.jsp";
  // const _url = 'https://submit.kada-ad.or.kr/UBIFORM/UView5/index.jsp'
  // const _url = 'http://211.237.16.73:9282/UBIFORM/UView5/index.jsp'

  const form = document.createElement("form");

  form.setAttribute("method", "post");
  form.setAttribute("action", _url);
  // eslint-disable-next-line no-restricted-syntax
  for (const i in _params) {
    // eslint-disable-next-line no-prototype-builtins
    if (_params.hasOwnProperty(i)) {
      const param = document.createElement("input");
      param.type = "hidden";
      param.name = i;
      param.value = encodeURI(_params[i]);
      form.appendChild(param);
    }
  }
  /*
  if (_iframeName == undefined || _iframeName == null) {
    var d = new Date();
    var n = d.getTime();
    var name = "UBF_" + n;
    var windowoption =
      "location=0, directories=0,resizable=0,status=0,toolbar=0,menubar=0, width=" +
      screen.width +
      ",height=" +
      screen.height +
      ",left=0, top=0,scrollbars=0";
    form.target = name;
    window.open("", name, windowoption);
  } else {
}
*/
  form.target = _iframeName;

  document.body.appendChild(form);
  // form.setAttribute("target", "_blank");
  form.submit();
  document.body.removeChild(form);

  return false;
}

export function fn_eformCall_data(formId) {
  document.getElementById(formId).contentWindow.postMessage("getData", "*");
}
