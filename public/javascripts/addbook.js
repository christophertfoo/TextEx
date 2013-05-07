var AddBook = function() {

  /**
   * Checks if the user entered something for the required field handled by the controls with the given groupId.
   * 
   * @param groupId The ID of the control group for the field to be checked.
   * 
   * @return true if the field is not empty, false if it is empty.
   */
  function checkRequired(groupId) {
    var idSelector = "#" + groupId;
    if ($(idSelector + " > input").val().length == 0) {
      $(idSelector).addClass("error");
      $(idSelector + " > span").text("Required.");
      return false;
    }
    return true;
  }

  /**
   * Clears the error indicators from a field.
   * 
   * @param groupId The ID of the field's control group.
   */
  function clearFieldError(groupId) {
    var idSelector = "#" + groupId;
    $(idSelector).removeClass("error");
    $(idSelector + "> span").text("");
  }

  function checkIsbn() {
    var valid = true;
    valid &= checkRequired("isbnGroup");
    if (valid) {
      clearFieldError("isbnGroup");
    }
    return valid;
  }

  function checkTitle() {
    var valid = true;
    valid &= checkRequired("titleGroup");
    if (valid) {
      clearFieldError("titleGroup");
    }
    return valid;
  }

  function checkAuthors() {
    var valid = true;
    valid &= checkRequired("authorsGroup");
    if (valid) {
      clearFieldError("authorsGroup");
    }
    return valid;
  }

  function checkPublisher() {
    var valid = true;
    valid &= checkRequired("publisherGroup");
    if (valid) {
      clearFieldError("publisherGroup");
    }
    return valid;
  }

  function checkEdition() {
    var valid = true;
    if (valid) {
      clearFieldError("editionGroup");
    }
    return valid;
  }

  function checkPrice() {
    var valid = true;
    valid &= checkRequired("priceGroup");
    if (valid) {
      clearFieldError("priceGroup");
    }
    return valid;
  }

  function success() {
    $("#alertDiv").addClass("alert-success");
    $("#alertDiv").html("<b>Success!</b> The book has been added to the database!");
  }

  function validate() {
    var valid = true;
    valid &= checkIsbn();
    valid &= checkTitle();
    valid &= checkAuthors();
    valid &= checkPublisher();
    valid &= checkEdition();
    valid &= checkPrice();
    if (!valid) {
      $("#alertDiv").addClass("alert-error");
      $("#alertDiv").html("<b>Error:</b> There are errors in the form.  Please fix them.");
      $("#alertDiv").removeClass("hidden");
      return valid;
    } else {
      $("#alertDiv").removeClass("alert-error");
      $("#alertDiv").removeClass("alert-success");
      $("#alertDiv").removeClass("hidden");
      //$("#alertDiv").html("<b>Working!</b> Please be patient as we process your request...");
      //setTimeout(success, 1000)
      return valid;
    }
  }

  return {
    validate: validate
  }
}();