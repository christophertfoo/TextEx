var Book = function() {

  function trim(string) {
    return string.replace(/^\s+|\s+$/g, '');
  }

  function grabCurrentValue(sourceId, destId) {
    var value = $("#" + sourceId).text()
    $("#" + destId + " > input").val(trim(value.substr(value.indexOf(":") + 1)));
  }

  function setBookFields() {
    grabCurrentValue("currentIsbn", "editIsbnGroup")
    grabCurrentValue("currentAuthors", "editAuthorsGroup")
    grabCurrentValue("currentEdition", "editEditionGroup")
    grabCurrentValue("currentBasePrice", "editBasePriceGroup")
  }

  function setContactInfo(event) {
    var targetId = event.target.id;
    var splitId = targetId.split("_");
    var table = splitId[0];
    var row = splitId[2];
    $("#contactInfo").html("<b>To: </b>" + $("#" + table + "_name_" + row).text() + " (" + $("#" + table + "_email_" + row).val() + ")")
  }
  
  function iconClicked(event) {
	    $(event.target).parent().click();
	    event.stopPropagation();
	  }

  return {
    iconClicked: iconClicked,
    setBookFields: setBookFields,
    setContactInfo: setContactInfo
  }
}();