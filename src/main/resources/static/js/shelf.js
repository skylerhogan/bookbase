
function addOption(){
     if (shelfSelect.value === "Select") {
        alert("Please select correct option");
     }
     else{
        document.getElementById("shelfForm").submit();
        shelfSelect.options[shelfSelect.selectedIndex].value;
     }
     document.getElementById('shelfSelect').value = "Select";
 }
