/**
 * jQuery
 */
jQuery.fn.serializeObject = function() {
    var obj = null;
    try {
        if (this[0].tagName && this[0].tagName.toLowerCase() == "form") {
            var disabled = this.find(':input:disabled').removeAttr('disabled');
            var arr = this.serializeArray();
            if (arr) {
                obj = {};
                jQuery.each(arr, function(i,v) {
                    obj[this.name] = this.value;
                });
            }
            disabled.attr('disabled','disabled');
        }
    } catch (e) {
        console.log('e : ' , e);
        alert(e.message);
    } finally {
        
    }
    return obj;
};

/**
 * JSON
 */
JSON.stringifyEnc = function(str){
    return encodeURIComponent(JSON.stringify(str));
}

JSON.parseDec = function(str){
    return JSON.parse(decodeURIComponent(str));
}

/**
 * String
 */
String.prototype.lpad = function(padLength , padString){
    var s = this.toString();
    while(s.length < padLength){
        s = padString + s;
    }
    return s;
};

String.prototype.rpad = function(padLength , padString){
    var s = this.toString();
    while(s.length < padLength){
        s += padString;
    }
    return s;
};

String.prototype.replaceAll = function(orgString, destString) {
    return this.split(orgString).join(destString);
};

String.prototype.addComma = function(){
    var parts=this.toString().split(".");
    return parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",") + (parts[1] ? "." + parts[1] : "");
};

String.prototype.delComma = function(){
    return this.toString().replace(/[^(-\d)]+/g, '');
};

String.prototype.toYmdFormat = function(){
    return this.toString().replace(/(\d{4})(\d{2})(\d{2})/g,'$1-$2-$3');
}

String.prototype.startsWith = function(str){
    if (this.length < str.length) { return false; }
    return this.indexOf(str) == 0;
}

String.prototype.endsWith = function(str){
    if (this.length < str.length) { return false; }
    return this.lastIndexOf(str) + str.length == this.length;
}


Date.prototype.yyyymmdd = function(){
    var yyyy = this.getFullYear().toString();
    var mm = (this.getMonth() + 1).toString();
    var dd = this.getDate().toString();
    return yyyy + (mm[1] ? mm : '0'+mm[0]) + (dd[1] ? dd : '0'+dd[0]);
}

