BrowseBooks = function() {
  var resultButtonIds = new Array("resultsIsbn", "resultsTitle", "resultsAuthors", "resultsEdition", "resultsBasePrice", "resultsPublisher");

  function sortClicked(event) {
    var clickedId = event.target.id;
    var clicked = $("#" + clickedId);
    var clickedIcon = $("#" + clickedId + " > i");
    if (clicked.hasClass("btn-primary")) {
      if (clickedIcon.hasClass("icon-arrow-down")) {
        clickedIcon.removeClass("icon-arrow-down");
        clickedIcon.addClass("icon-arrow-up");
      } else {
        clickedIcon.removeClass("icon-arrow-up");
        clickedIcon.addClass("icon-arrow-down");
      }
    } else {
      clicked.addClass("btn-primary");
      clickedIcon.addClass("icon-white")
      var numCols = resultButtonIds.length;
      for (var i = 0; i < numCols; i++) {
        var currentButton = $("#" + resultButtonIds[i]);
        if (currentButton.hasClass("btn-primary") && resultButtonIds[i] != clickedId) {
          currentButton.removeClass("btn-primary")
          var currentButtonIcon = $("#" + resultButtonIds[i] + "> i");
          currentButtonIcon.removeClass("icon-white")
        }
      }
    }
  }

  function sortIconClicked(event) {
    $(event.target).parent().click();
    event.stopPropagation();
  }

  return {
    sortClicked: sortClicked,
    sortIconClicked: sortIconClicked
  }
}();